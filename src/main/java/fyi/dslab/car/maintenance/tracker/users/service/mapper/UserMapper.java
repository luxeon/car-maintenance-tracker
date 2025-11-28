package fyi.dslab.car.maintenance.tracker.users.service.mapper;

import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class UserMapper {

    public abstract UserEntity toUserEntity(String email);

}
