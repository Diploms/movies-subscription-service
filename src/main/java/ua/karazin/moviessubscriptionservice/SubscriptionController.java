package ua.karazin.moviessubscriptionservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  @PostMapping
  public CompletableFuture<String> create(@RequestBody SubscriptionCreateRequest request) {
    return subscriptionService.createSubscription(request);
  }

  @PostMapping("/{id}")
  public void updateById(@PathVariable String id, @RequestBody SubscriptionUpdateRequest request) {
    subscriptionService.updateSubscription(id, request);
  }
}
