package nl.mesoplz.hueapp.main.config;

import nl.mesoplz.hueapp.main.lights.Delays;
import nl.mesoplz.hueapp.main.lights.MColor;
import nl.mesoplz.hueapp.main.lights.Theme;

import java.util.ArrayList;

public class ConfigObject {
    private String ip, user;
    private ArrayList<MColor> MColors;
    private ArrayList<Theme> themes;
    private Delays delays;
    private int turnOnHour, turnOnMinute, turnOffHour, turnOffMinute;
    private boolean excludeWeekends;

    public ConfigObject(String ip, String user, ArrayList<MColor> MColors, ArrayList<Theme> themes, Delays delays, int turnOnHour, int turnOnMinute, int turnOffHour, int turnOffMinute, boolean excludeWeekends) {
        this.ip = ip;
        this.user = user;
        this.MColors = MColors;
        this.themes = themes;
        this.delays = delays;
        this.turnOnHour = turnOnHour;
        this.turnOnMinute = turnOnMinute;
        this.turnOffHour = turnOffHour;
        this.turnOffMinute = turnOffMinute;
        this.excludeWeekends = excludeWeekends;
    }

    public String getIp() {
        return ip;
    }

    public String getUser() {
        return user;
    }

    public ArrayList<MColor> getMColors() {
        return MColors;
    }

    public ArrayList<Theme> getThemes() {
        return themes;
    }

    public Delays getDelays() {
        return delays;
    }

    public int getTurnOnHour() {
        return turnOnHour;
    }

    public int getTurnOnMinute() {
        return turnOnMinute;
    }

    public int getTurnOffHour() {
        return turnOffHour;
    }

    public int getTurnOffMinute() {
        return turnOffMinute;
    }

    public boolean getExcludeWeekends() {
        return excludeWeekends;
    }
}
