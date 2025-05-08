package ua.karazin.moviessubscriptionservice;

import jakarta.validation.constraints.Positive;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Duration;

public record SubscriptionUpdateRequest(@NonNull Duration duration, @Positive BigDecimal price) {
}
