package nl.mesoplz.hueapp.main.lights;

import nl.mesoplz.hue.exceptions.HueException;
import nl.mesoplz.hue.models.HueBridge;
import nl.mesoplz.hue.models.HueLight;

import java.io.IOException;

public class LightsThread {

    private boolean running = false;

    public void start() throws Exception {
        if (!running) {
            running = true;
            Thread thread = new Thread(() -> {
                System.out.println("Running new thread: " + Thread.currentThread().getName());
                try {
                    HueBridge bridge = new HueBridge("192.168.1.102", "8f36bb73f410a65f044469ea5b645dca", 5); //home diyhue

                    while (running) {
                        for (HueLight light : bridge.getLights()) {
                            light.setBri(254);
                            light.setRGB(255, 0, 0);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for (HueLight light : bridge.getLights()) {
                            light.setBri(254);
                            light.setRGB(255, 255, 255);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (HueException e) {
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
