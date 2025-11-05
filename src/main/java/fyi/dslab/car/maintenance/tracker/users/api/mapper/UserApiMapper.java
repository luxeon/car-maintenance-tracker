package fyi.dslab.car.maintenance.tracker.users.api.mapper;

import fyi.dslab.car.maintenance.tracker.user.api.model.UserResponseDTO;
import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserApiMapper {
    UserResponseDTO toUserResponseDTO(UserEntity createdUser);
}
