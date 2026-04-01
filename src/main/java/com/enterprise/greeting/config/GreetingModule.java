package com.enterprise.greeting.config;

import com.enterprise.greeting.acl.ExternalGreetingTranslator;
import com.enterprise.greeting.acl.GreetingACLFacade;
import com.enterprise.greeting.acl.InternalGreetingTranslator;
import com.enterprise.greeting.api.GreetingService;
import com.enterprise.greeting.application.command.CommandBus;
import com.enterprise.greeting.application.command.DefaultCommandBusImpl;
import com.enterprise.greeting.application.command.GreetCommandHandler;
import com.enterprise.greeting.application.mapper.GreetingRequestToCommandMapper;
import com.enterprise.greeting.application.mapper.GreetingResultToResponseMapper;
import com.enterprise.greeting.application.mediator.DefaultGreetingMediatorImpl;
import com.enterprise.greeting.application.mediator.GreetingMediator;
import com.enterprise.greeting.application.saga.GreetingOrchestrationSaga;
import com.enterprise.greeting.application.usecase.DefaultGreetWorldUseCaseImpl;
import com.enterprise.greeting.application.usecase.GreetWorldUseCase;
import com.enterprise.greeting.domain.events.DefaultDomainEventBusImpl;
import com.enterprise.greeting.domain.events.DomainEventBus;
import com.enterprise.greeting.infrastructure.adapter.ConsoleOutputAdapter;
import com.enterprise.greeting.infrastructure.adapter.OutputPort;
import com.enterprise.greeting.infrastructure.factory.AbstractGreetingStrategyFactoryFactory;
import com.enterprise.greeting.infrastructure.factory.DefaultAbstractGreetingStrategyFactoryFactoryImpl;
import com.enterprise.greeting.infrastructure.factory.GreetingStrategyFactory;
import com.enterprise.greeting.infrastructure.persistence.DefaultGreetingEventStoreImpl;
import com.enterprise.greeting.infrastructure.persistence.DefaultGreetingRepositoryImpl;
import com.enterprise.greeting.infrastructure.persistence.GreetingEventStore;
import com.enterprise.greeting.infrastructure.persistence.GreetingRepository;
import com.enterprise.greeting.infrastructure.persistence.GreetingUnitOfWork;
import com.enterprise.greeting.infrastructure.proxy.LoggingGreetingDecorator;
import com.enterprise.greeting.infrastructure.proxy.MetricsGreetingDecorator;
import com.enterprise.greeting.infrastructure.proxy.GreetingServiceProxy;
import com.enterprise.greeting.presentation.DefaultGreetingFacadeImpl;
import com.enterprise.greeting.presentation.GreetingFacade;

import java.lang.reflect.Constructor;
import java.util.logging.Logger;

/**
 * Central wiring module for the Greeting bounded context.
 * All object graph construction occurs here.
 * No other class may use {@code new} on a service or infrastructure object.
 *
 * <p>Call {@link #initialise()} once at application startup, before any
 * service lookups via the {@link GreetingServiceLocator}.</p>
 *
 * <p>Wiring order:
 * <ol>
 *   <li>Infrastructure (event bus, repository, event store, unit of work)</li>
 *   <li>Factories (strategy factory via factory factory)</li>
 *   <li>Handlers and mappers</li>
 *   <li>Application layer (use case, saga, mediator)</li>
 *   <li>Decoration pipeline (proxy, logging, metrics)</li>
 *   <li>Presentation layer (facade)</li>
 *   <li>ACL</li>
 *   <li>Registry population</li>
 * </ol>
 * </p>
 */
public final class GreetingModule {

    private static final Logger LOG = Logger.getLogger(GreetingModule.class.getName());

    private GreetingModule() {}

    public static void initialise() {
        LOG.info("[Module] Initialising EnterpriseGreetingFramework™...");

        // ── Infrastructure ────────────────────────────────────────────────────
        DomainEventBus eventBus = newInstance(DefaultDomainEventBusImpl.class);
        GreetingRepository repository = newInstance(DefaultGreetingRepositoryImpl.class);
        GreetingEventStore eventStore = newInstance(DefaultGreetingEventStoreImpl.class);
        GreetingUnitOfWork unitOfWork = new GreetingUnitOfWork(repository, eventStore, eventBus);
        OutputPort outputPort = newInstance(ConsoleOutputAdapter.class);

        // ── Factory of Factories ──────────────────────────────────────────────
        AbstractGreetingStrategyFactoryFactory factoryFactory =
            DefaultAbstractGreetingStrategyFactoryFactoryImpl.getInstance();
        GreetingStrategyFactory strategyFactory = factoryFactory.createStrategyFactory();

        // ── Command infrastructure ────────────────────────────────────────────
        CommandBus commandBus = newInstance(DefaultCommandBusImpl.class);
        GreetCommandHandler commandHandler = new GreetCommandHandler(strategyFactory);
        commandBus.register(commandHandler);

        // ── Mappers (ACL between layers) ──────────────────────────────────────
        GreetingRequestToCommandMapper requestMapper = new GreetingRequestToCommandMapper();
        GreetingResultToResponseMapper resultMapper  = new GreetingResultToResponseMapper();

        // ── Application layer ─────────────────────────────────────────────────
        GreetWorldUseCase useCase = new DefaultGreetWorldUseCaseImpl(
            commandBus, eventBus, requestMapper, resultMapper);

        GreetingOrchestrationSaga saga = new GreetingOrchestrationSaga(
            commandBus, eventBus, requestMapper, resultMapper);

        GreetingMediator mediator = new DefaultGreetingMediatorImpl(useCase, saga);

        // ── Decoration pipeline: proxy → logging → metrics ────────────────────
        GreetingService coreService = new DefaultGreetingServiceImpl(mediator, outputPort);
        GreetingService proxied     = new GreetingServiceProxy(coreService);
        GreetingService logged      = new LoggingGreetingDecorator(proxied);
        GreetingService metered     = new MetricsGreetingDecorator(logged);

        // ── Presentation facade ───────────────────────────────────────────────
        GreetingFacade facade = new DefaultGreetingFacadeImpl(metered);

        // ── ACL ───────────────────────────────────────────────────────────────
        GreetingACLFacade aclFacade = new GreetingACLFacade(
            new ExternalGreetingTranslator(),
            new InternalGreetingTranslator());

        // ── Registry population ───────────────────────────────────────────────
        GreetingRegistry registry = GreetingRegistry.getInstance();
        registry.register(GreetingService.class,  metered);
        registry.register(GreetingMediator.class, mediator);
        registry.register(GreetingFacade.class,   facade);
        registry.register(GreetingACLFacade.class, aclFacade);
        registry.register(DomainEventBus.class,   eventBus);
        registry.register(GreetingRepository.class, repository);
        registry.register(GreetingUnitOfWork.class, unitOfWork);
        registry.register(OutputPort.class, outputPort);

        LOG.info("[Module] EnterpriseGreetingFramework™ initialised. 73 classes wired. Ready to say hello.");
    }

    @SuppressWarnings("unchecked")
    private static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<T>[] ctors = (Constructor<T>[]) clazz.getDeclaredConstructors();
            Constructor<T> ctor = ctors[0];
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate " + clazz.getSimpleName(), e);
        }
    }
}
