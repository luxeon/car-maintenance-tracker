package fyi.dslab.car.maintenance.tracker.users.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Component
@ConfigurationProperties("car.maintenance.tracker.security.auth-code")
public class UserAuthCodeProperties {

    @Min(1)
    @NotNull
    private Long timeToLiveSec;

}
