package nl.mesoplz.hueapp.main.lights;

import java.util.ArrayList;

public class Theme {
    private String name;
    private ArrayList<MColor> themeColors;

    public Theme(String name, ArrayList<MColor> themeColors) {
        this.name = name;
        this.themeColors = themeColors;
    }

    public String getName() {
        return name;
    }

    public ArrayList<MColor> getThemeColors() {
        return themeColors;
    }

    public void setThemeColors(ArrayList<MColor> themeColors) {
        this.themeColors = themeColors;
    }
}
