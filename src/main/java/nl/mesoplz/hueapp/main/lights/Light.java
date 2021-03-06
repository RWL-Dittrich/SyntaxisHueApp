package nl.mesoplz.hueapp.main.lights;

import nl.mesoplz.hue.models.HueLight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class Light {

    private HueLight light;

    private ArrayList<MColor> MColors;
    private int counter;

    private int toCountTo;
    private Delays delays;

    private int currentColor = 0;

    Light(HueLight light, ArrayList<MColor> MColors, Delays delays) {
        this.light = light;
        this.MColors = MColors;
        this.delays = delays;
        toCountTo = randomBetweenBounds(delays.minDelay, delays.maxDelay);
        counter = toCountTo;
    }

    void tick() throws IOException {
        counter++;
        if (counter >= toCountTo) {
            counter = 0;
            toCountTo = randomBetweenBounds(delays.minDelay, delays.maxDelay);

            java.awt.Color nextColor = selectRandomColor();

            light.setRGB(nextColor.getRed(), nextColor.getGreen(), nextColor.getBlue(), toCountTo);

        }
    }


    private int randomBetweenBounds(int minimum, int maximum) {
        Random rn = new Random();
        int range = maximum - minimum + 1;
        return rn.nextInt(range) + minimum;
    }

    private java.awt.Color selectRandomColor() {
        Random rn = new Random();
        return MColors.get(rn.nextInt(MColors.size())).getColor();
    }



}
