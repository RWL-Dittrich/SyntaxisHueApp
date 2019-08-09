package nl.mesoplz.hueapp.main.timer;

import nl.mesoplz.hueapp.main.controllers.HueController;

import java.util.*;

public class Scheduler {
    private static Timer timer = new Timer();

    private static int turnOnHour = 8;
    private static int turnOnMinute = 0;

    private static int turnOffHour = 22;
    private static int turnOffMinute = 0;

    public static void ScheduleTasks() {
        timer.schedule(new TurnOn(), getDate(turnOnHour, turnOnMinute, false));
        timer.schedule(new TurnOff(), getDate(turnOffHour, turnOffMinute, false));
    }

    static class TurnOn extends TimerTask {

        @Override
        public void run() {
            try {
                HueController.lightsThread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            timer.schedule(new TurnOn(), getDate(turnOnHour, turnOnMinute, true));
        }
    }

    static class TurnOff extends TimerTask {

        @Override
        public void run() {
            HueController.lightsThread.stop();
            timer.schedule(new TurnOff(), getDate(turnOffHour, turnOffMinute, true));
        }
    }


    private static Date getDate(int hourOfDay, int minutesOfHour, boolean tomorrow){
        Calendar date = new GregorianCalendar();
        if (tomorrow) date.add(Calendar.DATE, 1);
        while (date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            date.add(Calendar.DATE, 1);
        }
        Calendar result = new GregorianCalendar(
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DATE),
                hourOfDay,
                minutesOfHour
        );
        System.out.println("Scheduled date: " + result.toString());
        return result.getTime();
    }

}
