package fyi.dslab.car.maintenance.tracker.users.service.mapper;

import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import fyi.dslab.car.maintenance.tracker.users.service.model.AuthenticatedUserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDetailsMapper {

    AuthenticatedUserDetails toUserDetails(UserEntity user);

}
