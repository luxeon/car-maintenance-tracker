package fyi.dslab.car.maintenance.tracker;

import org.springframework.boot.SpringApplication;

public class TestCarMaintenanceTrackerApplication {

    static void main(String[] args) {
        SpringApplication.from(CarMaintenanceTrackerApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }

}
