import com.enterprise.greeting.config.GreetingModule;
import com.enterprise.greeting.presentation.GreetingController;

/**
 * Application entry point.
 *
 * Previously:
 *
 *   void main() {
 *       IO.println("hello, world");
 *   }
 *
 * After applying enterprise architecture best practices, the above two lines
 * have been correctly identified as a monolith and decomposed into
 * 73 classes across 7 layers and 19 packages.
 *
 * To produce the string "hello, world", this entry point:
 *   1. Initialises the GreetingModule (wires the 73-class object graph)
 *   2. Constructs a GreetingController (presentation layer)
 *   3. Calls execute(), which resolves the GreetingFacade from the GreetingRegistry
 *   4. The facade builds a GreetingRequestDTO and calls the GreetingService
 *   5. The service is wrapped in a MetricsDecorator → LoggingDecorator → Proxy
 *   6. The proxy delegates to DefaultGreetingServiceImpl
 *   7. Which delegates to the GreetingMediator
 *   8. Which delegates to the GreetingOrchestrationSaga
 *   9. Which maps the DTO to a GreetCommand via GreetingRequestToCommandMapper (ACL)
 *  10. Which dispatches the command through the CommandBus
 *  11. Which routes to GreetCommandHandler
 *  12. Which resolves an InformalGreetingStrategy via DefaultGreetingStrategyFactoryImpl
 *  13. Which was created by DefaultAbstractGreetingStrategyFactoryFactoryImpl (factory of factories)
 *  14. The strategy composes a GreetingMessage Value Object
 *  15. The handler builds a GreetingAggregate and calls renderGreeting()
 *  16. The aggregate transitions through IdleGreetingState → ProcessingGreetingState → CompletedGreetingState
 *  17. Domain events are collected in the aggregate
 *  18. The result propagates back through the saga, which publishes a GreetingPublishedEvent
 *  19. The GreetingResultToResponseMapper translates the aggregate to a GreetingResponseDTO (ACL)
 *  20. The OutputPort (ConsoleOutputAdapter) emits the rendered string
 *
 * Output: "hello, world"
 */
void main() {
    GreetingModule.initialise();
    new GreetingController().execute();
}
