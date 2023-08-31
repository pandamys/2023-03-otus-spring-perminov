package ru.otus.library.healthcheck;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class CheckDBAvailabilityHealthIndicator implements HealthIndicator {
    private final DataSource datasource;

    @Override
    public Health health() {
        boolean connectionValid;
        try {
            connectionValid = datasource.getConnection().isValid(3000);
        } catch (SQLException e) {
            connectionValid = false;
        }

        if (connectionValid) {
            return Health.up().withDetail("message", "DB connect is OK").build();
        } else {
            return Health.down().withDetail("message", "Error. No books found in the Library").build();
        }
    }
}