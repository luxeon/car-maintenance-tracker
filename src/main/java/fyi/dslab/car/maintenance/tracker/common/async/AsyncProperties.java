package fyi.dslab.car.maintenance.tracker.common.async;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Component
@ConfigurationProperties("car.maintenance.tracker.async")
public class AsyncProperties {

    @NotNull
    @Min(0)
    private Integer corePoolSize;
    @NotNull
    @Min(0)
    private Integer maxPoolSize;
    @NotNull
    @Min(0)
    private Integer queueCapacity;
    @NotBlank
    private String threadNamePrefix;

}
