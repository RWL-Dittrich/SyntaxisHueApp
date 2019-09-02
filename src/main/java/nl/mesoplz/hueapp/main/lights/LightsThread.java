package nl.mesoplz.hueapp.main.lights;

import nl.mesoplz.hue.exceptions.HueException;
import nl.mesoplz.hue.models.HueBridge;
import nl.mesoplz.hue.models.HueLight;
import nl.mesoplz.hueapp.main.config.ConfigLoader;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LightsThread {

    private static ArrayList<MColor> MColors = new ArrayList<>(Arrays.asList(new MColor(Color.GREEN), new MColor(Color.CYAN), new MColor(Color.YELLOW)));
    private static Delays delays = new Delays();


    /**
     * These Strings are only used if there is no config file in the application's folder (so a fallback)
     */
//    private static String ip = "localhost", user = "66986704230b2e75868416979af78fe"; //PC emulator hue
//        private static String ip = "192.168.137.1"; private static String user = "66986704230b2e75868416979af78fe"; //PC emulator hue from PI
        private static String ip = "192.168.1.102"; private static String user = "8f36bb73f410a65f044469ea5b645dca"; //home diyhue


    private boolean running = false;

    public void start() {
        if (!running) {
            running = true;
            Thread thread = new Thread(() -> {
                System.out.println("Running new thread: " + Thread.currentThread().getName());
                try {
                    HueBridge bridge = new HueBridge(ip, user, 5);
                    ArrayList<Light> lights = new ArrayList<>();
                    //Store all the lights in the hue bridge in a ArrayList of custom Light config. This object has a tick method
                    for (HueLight light : bridge.getLights()) {
                        light.setPower(true);
                        lights.add(new Light(light, MColors, delays));
                    }
                    while (running) {
                        try {
                            for (Light light : lights) {
                                light.tick();
                            }
                            Thread.sleep(100);
                        } catch (IOException e) {
                            System.out.println("Something went wrong when connecting to the light: " + e.getMessage());
                        } catch (InterruptedException e) {
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
                    running = false;
                }

            });
            thread.start();
        } else {
            running = false;
            //Wait for other thread to finish its tick method. Then start a new one
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            start();
        }
    }

    public void stop() {
        running = false;
    }

    public static ArrayList<MColor> getMColors() {
        return MColors;
    }

    public static void setMColors(ArrayList<MColor> MColors) {
        LightsThread.MColors = MColors;
    }

    public static void setDelays(Delays delays) {
        LightsThread.delays = delays;
    }

    public static void setMinDelay(int minDelay) {
        delays.minDelay = minDelay;
    }

    public static void setMaxDelay(int maxDelay) {
        delays.maxDelay = maxDelay;
    }

    public static int getMinDelay() {
        return delays.minDelay;
    }

    public static int getMaxDelay() {
        return delays.maxDelay;
    }

    public static Delays getDelays() {
        return delays;
    }

    public boolean isRunning() {
        return running;
    }

    public static void setIp(String ip) {
        LightsThread.ip = ip;
    }

    public static void setUser(String user) {
        LightsThread.user = user;
    }

    public static String getIp() {
        return ip;
    }

    public static String getUser() {
        return user;
    }

}
