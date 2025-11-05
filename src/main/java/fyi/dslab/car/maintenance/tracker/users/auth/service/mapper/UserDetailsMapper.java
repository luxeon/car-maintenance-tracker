package fyi.dslab.car.maintenance.tracker.users.auth.service.mapper;

import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDetailsMapper {

    @Mapping(target = "username", source = "email")
    UserDetails toUserDetails(UserEntity user);

}
