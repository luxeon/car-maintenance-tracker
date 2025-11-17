package fyi.dslab.car.maintenance.tracker.car.repository.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(name = "cars")
public class CarEntity {

    @Id
    private Long id;

    @NotNull
    private Long userId;

    @NotBlank
    private String make;

    @NotBlank
    private String model;

    private Integer year;

    private String engine;

    private String fuelType;

    private LocalDateTime createdAt;

}
