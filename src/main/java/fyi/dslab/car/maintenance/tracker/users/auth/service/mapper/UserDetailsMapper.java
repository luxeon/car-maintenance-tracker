package fyi.dslab.car.maintenance.tracker.users.auth.service.mapper;

import fyi.dslab.car.maintenance.tracker.users.auth.service.model.AuthenticatedUserDetails;
import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDetailsMapper {

    AuthenticatedUserDetails toUserDetails(UserEntity user);

}
