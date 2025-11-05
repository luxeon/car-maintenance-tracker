package fyi.dslab.car.maintenance.tracker.users.auth.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties("car.maintenance.tracker.security.jwt")
public class JwtProperties {

    @NotNull
    @Min(0)
    private Long expirationMs;

    @NotBlank
    private String secret;

}
