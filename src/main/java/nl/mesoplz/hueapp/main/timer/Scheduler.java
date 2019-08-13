package nl.mesoplz.hueapp.main.timer;

import nl.mesoplz.hueapp.main.controllers.HueController;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;


public class Scheduler {
    private static Timer timer = new Timer();
    private static boolean running = false;

    private static int turnOnHour = 8;
    private static int turnOnMinute = 0;

    private static int turnOffHour = 22;
    private static int turnOffMinute = 0;

    public static void updateTime(String onTime, String offTime) {
        String[] onTimeSplit = onTime.split(":");
        String[] offTimeSplit = offTime.split(":");

        turnOnHour = Integer.parseInt(onTimeSplit[0]);
        turnOnMinute = Integer.parseInt(onTimeSplit[1]);

        turnOffHour = Integer.parseInt(offTimeSplit[0]);
        turnOffMinute = Integer.parseInt(offTimeSplit[1]);

        stopScheduler();
        scheduleTasks();
    }

    public static void scheduleTasks() {
        System.out.println("Scheduler starting!");
        running = true;
        Calendar turnOn = getDate(turnOnHour, turnOnMinute, false);
        logCalendar(turnOn, true);
        timer.schedule(new TurnOn(), turnOn.getTime());

        Calendar turnOff = getDate(turnOffHour, turnOffMinute, false);
        logCalendar(turnOff, false);
        timer.schedule(new TurnOff(), turnOff.getTime());
    }

    public static void stopScheduler() {
        running = false;
        timer.cancel();
        timer.purge();
        timer = new Timer();
        System.out.println("Scheduler stopped!");
    }

    static class TurnOn extends TimerTask {

        @Override
        public void run() {
            try {
                HueController.lightsThread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Calendar newDate = getDate(turnOnHour, turnOnMinute, true);
            logCalendar(newDate, true);
            timer.schedule(new TurnOn(), newDate.getTime());
        }
    }

    static class TurnOff extends TimerTask {

        @Override
        public void run() {
            HueController.lightsThread.stop();
            Calendar newDate = getDate(turnOffHour, turnOffMinute, true);
            logCalendar(newDate, false);
            timer.schedule(new TurnOff(), newDate.getTime());
        }
    }


    private static Calendar getDate(int hourOfDay, int minutesOfHour, boolean tomorrow){
        Calendar date = new GregorianCalendar();
        if (tomorrow) date.add(Calendar.DATE, 1);
        while (date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            date.add(Calendar.DATE, 1);
        }
        return new GregorianCalendar(
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DATE),
                hourOfDay,
                minutesOfHour
        );
    }


    private static void logCalendar(Calendar toLog, boolean on) {
        String type;
        if (on) {
            type = "turnOn";
        } else {
            type = "turnOff";
        }
        System.out.println("*-*- Scheduled date -*-*\nMonth: " + (toLog.get(Calendar.MONTH)+1) + "\nDay: " + toLog.get(Calendar.DAY_OF_MONTH) + "\nHour: " + toLog.get(Calendar.HOUR_OF_DAY) + "\nMinute: " + toLog.get(Calendar.MINUTE) + "\nType: " + type + "\n------------------------");

    }

    public static int getTurnOnHour() {
        return turnOnHour;
    }

    public static int getTurnOnMinute() {
        return turnOnMinute;
    }

    public static int getTurnOffHour() {
        return turnOffHour;
    }

    public static int getTurnOffMinute() {
        return turnOffMinute;
    }

    public static boolean isRunning() {
        return running;
    }
}
