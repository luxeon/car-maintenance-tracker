package fyi.dslab.car.maintenance.tracker.car.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CarNotFoundException extends RuntimeException {

    private final Long userId;
    private final Long carId;
}
