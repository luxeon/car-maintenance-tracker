package fyi.dslab.car.maintenance.tracker.users.service.mapper;

import fyi.dslab.car.maintenance.tracker.user.api.model.UserRegistrationRequestDTO;
import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class UserMapper {

    @Setter(onMethod_ = {@Autowired})
    private PasswordEncoder passwordEncoder;

    @Mapping(source = "password", target = "password", qualifiedByName = "hashPassword")
    public abstract UserEntity toUserEntity(UserRegistrationRequestDTO requestDTO);

    @Named("hashPassword")
    public String hashPassword(String sourcePassword) {
        return passwordEncoder.encode(sourcePassword);
    }
}
