package fyi.dslab.car.maintenance.tracker.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Component
@ConfigurationProperties("car.maintenance.tracker.email")
public class EmailProperties {

    @NotNull
    @Email
    private String from;

}
