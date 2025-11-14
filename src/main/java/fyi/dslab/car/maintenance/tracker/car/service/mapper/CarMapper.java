package fyi.dslab.car.maintenance.tracker.car.service.mapper;

import fyi.dslab.car.maintenance.tracker.car.api.model.CreateCarRequestDTO;
import fyi.dslab.car.maintenance.tracker.car.api.model.UpdateCarRequestDTO;
import fyi.dslab.car.maintenance.tracker.car.repository.entity.CarEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, imports = {LocalDateTime.class})
public interface CarMapper {

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    CarEntity toUser(Long userId, CreateCarRequestDTO createCarRequestDTO);

    void update(@MappingTarget CarEntity car, UpdateCarRequestDTO updateCarRequestDTO);
}
