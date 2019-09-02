package nl.mesoplz.hueapp.main.config;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import nl.mesoplz.hueapp.main.lights.LightsThread;
import nl.mesoplz.hueapp.main.timer.Scheduler;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigLoader {
    public static void loadConfig() throws IOException, JsonSyntaxException {
        FileReader fr = null;
        JsonReader reader = null;
        try {
            Gson gson = new Gson();
            fr = new FileReader("config.json");
            reader = new JsonReader(new FileReader("config.json"));
            ConfigObject configObject = gson.fromJson(reader, ConfigObject.class);
            if (configObject == null) {
                throw new IOException("Empty config file!");
            }
            LightsThread.setIp(configObject.getIp());
            LightsThread.setUser(configObject.getUser());
            LightsThread.setMColors(configObject.getMColors());
            LightsThread.setDelays(configObject.getDelays());
            Scheduler.setTurnOnHour(configObject.getTurnOnHour());
            Scheduler.setTurnOnMinute(configObject.getTurnOnMinute());
            Scheduler.setTurnOffHour(configObject.getTurnOffHour());
            Scheduler.setTurnOffMinute(configObject.getTurnOffMinute());
            Scheduler.setExcludeWeekends(configObject.getExcludeWeekends());
        } finally {
            if (reader != null) {
                reader.close();
                fr.close();
            }
        }

    }

    public static void updateConfig() throws IOException {
        Gson gson = new Gson();
        ConfigObject configObject = new ConfigObject(LightsThread.getIp(), LightsThread.getUser(), LightsThread.getMColors(), LightsThread.getDelays(), Scheduler.getTurnOnHour(), Scheduler.getTurnOnMinute(), Scheduler.getTurnOffHour(), Scheduler.getTurnOffMinute(), Scheduler.getExcludeWeekends());
        String jsonString = gson.toJson(configObject);
        FileWriter fw = new FileWriter("config.json");
        fw.write(jsonString);
        fw.close();
    }
}
