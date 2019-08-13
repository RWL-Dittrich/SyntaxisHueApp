package nl.mesoplz.hueapp.main.lights;

import nl.mesoplz.hue.models.HueLight;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

class Light {

    private HueLight light;

    private Color c1, c2, c3;
    private int counter;

    private int toCountTo;
    private int maxDuration;
    private int minDuration;

    private int currentColor = 0;

    Light(HueLight light, Color c1, Color c2, Color c3, int minDuration, int maxDuration) {
        this.light = light;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        toCountTo = randomBetweenBounds(minDuration, maxDuration);
        counter = toCountTo;
    }

    void tick() throws IOException {
        counter++;
        if (counter >= toCountTo) {
            counter = 0;
            toCountTo = randomBetweenBounds(minDuration, maxDuration);

            currentColor = selectNewRandomColor();

            switch(currentColor) {
                case(1):
                    light.setRGB(c1.getRed(), c1.getGreen(), c1.getBlue(), toCountTo);
                    break;
                case(2):
                    light.setRGB(c2.getRed(), c2.getGreen(), c2.getBlue(), toCountTo);
                    break;
                case(3):
                    light.setRGB(c3.getRed(), c3.getGreen(), c3.getBlue(), toCountTo);
                    break;
            }
        }
    }

    private int randomBetweenBounds(int minimum, int maximum) {
        Random rn = new Random();
        int range = maximum - minimum + 1;
        return rn.nextInt(range) + minimum;
    }

    private int selectNewRandomColor() {
        Random rn = new Random();
        int range = 3 - 1 + 1;
        int random = rn.nextInt(range) + 1;

        while(random == currentColor) {
            random = rn.nextInt(range) + 1;
        }
        return random;
    }


}
