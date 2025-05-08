package ua.karazin.moviessubscriptionservice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import ua.karazin.moviesbaseevents.subscription.revision1.CreateSubscriptionCommand1;
import ua.karazin.moviesbaseevents.subscription.revision1.SubscriptionCreatedEvent1;
import ua.karazin.moviesbaseevents.subscription.revision1.SubscriptionUpdatedEvent1;
import ua.karazin.moviesbaseevents.subscription.revision1.UpdateSubscriptionCommand1;

import java.math.BigDecimal;
import java.time.Duration;

import static ir.cafebabe.math.utils.BigDecimalUtils.is;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Getter
@Setter
@NoArgsConstructor
public class Subscription {
  @AggregateIdentifier
  private String id;

  private Duration duration;

  private BigDecimal price;

  @CommandHandler
  private Subscription(CreateSubscriptionCommand1 command) {
    throwIfPriceIsNotPositive(command.price());
    apply(new SubscriptionCreatedEvent1(command.id(), command.duration(), command.price()));
  }

  @CommandHandler
  private void handle(UpdateSubscriptionCommand1 command) {
    throwIfPriceIsNotPositive(command.price());
    apply(new SubscriptionUpdatedEvent1(command.id(), command.duration(), command.price()));
  }

  @EventSourcingHandler
  private void on(SubscriptionCreatedEvent1 event) {
    this.id = event.id();
    this.duration = event.duration();
    this.price = event.price();
  }

  @EventSourcingHandler
  private void on(SubscriptionUpdatedEvent1 event) {
    this.duration = event.duration();
    this.price = event.price();
  }

  private void throwIfPriceIsNotPositive(BigDecimal price) {
    if (isNotPositive(price)) {
      throw new CommandExecutionException("Positive price is expected. Got: " + price,
          new IllegalStateException());
    }
  }

  private boolean isNotPositive(BigDecimal price) {
    return is(price).notGt(BigDecimal.ZERO);
  }
}
