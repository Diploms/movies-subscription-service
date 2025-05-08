package ua.karazin.moviessubscriptionservice;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import ua.karazin.moviesbaseevents.subscription.revision1.CreateSubscriptionCommand1;
import ua.karazin.moviesbaseevents.subscription.revision1.UpdateSubscriptionCommand1;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
  private final CommandGateway commandGateway;

  public CompletableFuture<String> createSubscription(SubscriptionCreateRequest request) {
    return commandGateway.send(
        new CreateSubscriptionCommand1(UUID.randomUUID().toString(), request.duration(),
            request.price()));
  }

  public CompletableFuture<String> updateSubscription(String id, SubscriptionUpdateRequest request) {
    return commandGateway.send(new UpdateSubscriptionCommand1(id, request.duration(), request.price()));
  }
}
