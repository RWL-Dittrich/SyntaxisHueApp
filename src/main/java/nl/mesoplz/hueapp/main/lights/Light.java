package nl.mesoplz.hueapp.main.lights;

import nl.mesoplz.hue.models.HueLight;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Light {

    public HueLight light;

    private Color c1, c2, c3;
    private int counter = 0;

    private int toCountTo;
    private int maxDuration = 100;
    private int minDuration = 50;

    private int currentColor = 1;

    public Light(HueLight light, Color c1, Color c2, Color c3) {
        this.light = light;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        toCountTo = randomBetweenBounds(minDuration, maxDuration);
        counter = toCountTo;
    }

    public void tick() throws IOException {
        counter++;
        if (counter >= toCountTo) {
            counter = 0;
            toCountTo = randomBetweenBounds(minDuration, maxDuration);

            currentColor = selectNewRandomColor();

            switch(currentColor) {
                case(1):
                    light.setRGB(c1.getRed(), c1.getGreen(), c1.getBlue(), toCountTo);
                    light.setBri(Math.min(254, Math.max(c1.getRed(), Math.max(c1.getGreen(), c1.getBlue()))), toCountTo);
                    break;
                case(2):
                    light.setRGB(c2.getRed(), c2.getGreen(), c2.getBlue(), toCountTo);
                    light.setBri(Math.min(254, Math.max(c2.getRed(), Math.max(c2.getGreen(), c2.getBlue()))), toCountTo);
                    break;
                case(3):
                    light.setRGB(c3.getRed(), c3.getGreen(), c3.getBlue(), toCountTo);
                    light.setBri(Math.min(254, Math.max(c3.getRed(), Math.max(c3.getGreen(), c3.getBlue()))), toCountTo);
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
