package nl.mesoplz.hueapp.main.lights;

import nl.mesoplz.hue.exceptions.HueException;
import nl.mesoplz.hue.models.HueLight;

import java.io.IOException;
import java.util.Random;

public class Light {

    public HueLight light;

    private int counter = 0;

    private int toCountTo;
    private int maxDuration = 10;
    private int minDuration = 0;

    private boolean red = true;

    public Light(HueLight light) {
        this.light = light;
        toCountTo = randomBetweenBounds(minDuration, maxDuration);
    }

    public void tick() throws IOException {
        counter++;
        if (counter >= toCountTo) {
            counter = 0;
            toCountTo = randomBetweenBounds(minDuration, maxDuration);

            if (red) {
                light.setRGB(255, 0, 0, toCountTo);
                red = false;
            } else {
                light.setRGB(0, 255, 0, toCountTo);
                red = true;
            }
        }
    }

    private int randomBetweenBounds(int minimum, int maximum) {
        Random rn = new Random();
        int range = maximum - minimum + 1;
        return rn.nextInt(range) + minimum;
    }
}
