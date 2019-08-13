package nl.mesoplz.hueapp.main;

import nl.mesoplz.hueapp.main.lights.LightsThread;
import nl.mesoplz.hueapp.main.timer.Scheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
		LightsThread.loadConfig();
	}

}
