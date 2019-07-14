package nl.mesoplz.hueapp.main.lights;

import nl.mesoplz.hue.exceptions.HueException;
import nl.mesoplz.hue.models.HueBridge;
import nl.mesoplz.hue.models.HueLight;

import java.io.IOException;
import java.util.ArrayList;

public class LightsThread {

    private boolean running = false;

    public void start() throws Exception {
        if (!running) {
            running = true;
            Thread thread = new Thread(() -> {
                System.out.println("Running new thread: " + Thread.currentThread().getName());
                try {
//                    HueBridge bridge = new HueBridge("localhost", "66986704230b2e75868416979af78fe"); //pc emulator hue
                    HueBridge bridge = new HueBridge("192.168.1.102", "8f36bb73f410a65f044469ea5b645dca", 5); //home diyhue
                    ArrayList<Light> lights = new ArrayList<>();

                    //Store all the lights in the hue bridge in a ArrayList of custom Light objects. This object has a tick method
                    for (HueLight light : bridge.getLights()) {
                        light.setPower(true);
                        lights.add(new Light(light));
                    }
                    while (running) {
                        try {
                            for (Light light : lights) {
                                light.tick();
                            }
                            Thread.sleep(100);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //Thread has been stopped! we need to shut off the lights!
                    for (HueLight light : bridge.getLights()) {
                        light.setPower(false);
                    }

                } catch (IOException | HueException e) {
                    e.printStackTrace();
                }

            });
            thread.start();
        } else {
            throw new Exception("Thread already running!");
        }
    }

    public void stop() {
        running = false;
    }
}
