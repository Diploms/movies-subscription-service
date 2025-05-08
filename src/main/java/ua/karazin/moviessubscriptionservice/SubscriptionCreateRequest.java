package ua.karazin.moviessubscriptionservice;

import jakarta.validation.constraints.Positive;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Duration;

public record SubscriptionCreateRequest(@NonNull Duration duration, @NonNull @Positive BigDecimal price) {
}
