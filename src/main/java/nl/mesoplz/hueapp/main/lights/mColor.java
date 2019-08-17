package nl.mesoplz.hueapp.main.lights;

public class mColor {

    private java.awt.Color color;

    public mColor(java.awt.Color color) {
        this.color = color;
    }


    public String getHexColor() {
        return String.format("%06X", (0xFFFFFF & color.getRGB()));
    }

    public java.awt.Color getColor() {
        return color;
    }
}
