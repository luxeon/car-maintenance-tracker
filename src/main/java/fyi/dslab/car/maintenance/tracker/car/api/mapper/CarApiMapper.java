package fyi.dslab.car.maintenance.tracker.car.api.mapper;

import fyi.dslab.car.maintenance.tracker.car.api.model.CarDetailsResponseDTO;
import fyi.dslab.car.maintenance.tracker.car.repository.entity.CarEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarApiMapper {

    CarDetailsResponseDTO toCreateCarResponseDTO(CarEntity createdCar);

}
