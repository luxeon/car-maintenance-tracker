package fyi.dslab.car.maintenance.tracker.users.repository.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "users")
public class UserEntity {

    @Id
    private Long id;

    @NotNull
    @Email
    private String email;

}
