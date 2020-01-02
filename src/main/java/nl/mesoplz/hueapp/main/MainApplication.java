package nl.mesoplz.hueapp.main;

import com.google.gson.JsonSyntaxException;
import nl.mesoplz.hueapp.main.config.ConfigLoader;
import nl.mesoplz.hueapp.main.lights.LightsThread;
import nl.mesoplz.hueapp.main.timer.Scheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
		try {
			ConfigLoader.loadConfig();
			System.out.println("Config loaded!");
		} catch (IOException | JsonSyntaxException e) {
			File config = new File("config.json");
			try {
				if(config.createNewFile()) {
					System.out.println("New config file created!");
				}
				ConfigLoader.updateConfig();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
