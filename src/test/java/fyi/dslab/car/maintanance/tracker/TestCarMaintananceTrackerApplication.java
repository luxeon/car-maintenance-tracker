package fyi.dslab.car.maintanance.tracker;

import org.springframework.boot.SpringApplication;

public class TestCarMaintananceTrackerApplication {

	static void main(String[] args) {
		SpringApplication.from(CarMaintananceTrackerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
