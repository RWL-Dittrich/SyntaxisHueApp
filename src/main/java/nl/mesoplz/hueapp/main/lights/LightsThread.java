package nl.mesoplz.hueapp.main.lights;

import nl.mesoplz.hue.exceptions.HueException;
import nl.mesoplz.hue.models.HueBridge;
import nl.mesoplz.hue.models.HueLight;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class LightsThread {

    private Color c1 = Color.GREEN, c2 = Color.CYAN, c3 = Color.YELLOW;
    private int minDelay = 50, maxDelay = 100;


    private boolean running = false;

    public void start(Color c1, Color c2, Color c3, int minDelay, int maxDelay) throws Exception {
        //Update the stored colors
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        if (!running) {
            running = true;
            Thread thread = new Thread(() -> {
                System.out.println("Running new thread: " + Thread.currentThread().getName());
                try {
                    HueBridge bridge = new HueBridge("localhost", "66986704230b2e75868416979af78fe"); //pc emulator hue
//                    HueBridge bridge = new HueBridge("192.168.1.102", "8f36bb73f410a65f044469ea5b645dca", 5); //home diyhue
                    ArrayList<Light> lights = new ArrayList<>();
                    //Store all the lights in the hue bridge in a ArrayList of custom Light objects. This object has a tick method
                    for (HueLight light : bridge.getLights()) {
                        light.setPower(true);
                        lights.add(new Light(light, c1, c2, c3, minDelay, maxDelay));
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
                    System.out.println("Stopping thread: " + Thread.currentThread().getName());
                    for (HueLight light : bridge.getLights()) {
                        light.setPower(false);
                    }

                } catch (IOException | HueException e) {
                    e.printStackTrace();
                }

            });
            thread.start();
        } else {
            running = false;
            //Wait for other thread to finish its tick method. Then start a new one
            Thread.sleep(150);
            start(c1, c2, c3, minDelay, maxDelay);
        }
    }

    public void stop() {
        running = false;
    }

    public Color getC1() {
        return c1;
    }

    public Color getC2() {
        return c2;
    }

    public Color getC3() {
        return c3;
    }

    public int getMinDelay() {
        return minDelay;
    }

    public int getMaxDelay() {
        return maxDelay;
    }
}
