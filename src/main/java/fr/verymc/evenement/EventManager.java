package main.java.fr.verymc.evenement;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class EventManager {

    public static EventManager instance;

    public EventManager() {
        instance = this;
        new DailyBonus();
    }

    public String getCurrentEventRandom() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
        Calendar calendar = Calendar.getInstance();
        if (DailyBonus.instance.active) {
            return "§ax2 /c " + (System.currentTimeMillis() - DailyBonus.instance.lastAct) / 1000 + "s restantes";
        } else {
            int next = -1;
            int max = 0;
            for (Integer i : DailyBonus.instance.hours) {
                if (i < calendar.getTime().getHours()) {
                    continue;
                }
                if (next == -1) {
                    next = i;
                    max = i;
                    continue;
                }
                if (max > i) {
                    next = i;
                    max = i;
                }
            }
            return "§7Prochain x2 /c: §c" + returnFormattedTime((int) TimeUnit.MILLISECONDS.toSeconds(getTimeBeforeReset(next)));
        }
    }

    public long getTimeBeforeReset(int hour) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTime().getHours() < hour) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        }
        long time = calendar.getTimeInMillis();
        final long total = time - System.currentTimeMillis();
        calendar.clear();
        return total;
    }

    public String returnFormattedTime(int timeforconv) {
        int nHours = (timeforconv % 86400) / 3600;
        int nMin = ((timeforconv % 86400) % 3600) / 60;
        int nSec = (((timeforconv % 86400) % 3600) % 60);

        String nhoursnew;
        String nminnew;
        String nsecnew;
        if (nHours <= 9) {
            nhoursnew = "0" + nHours;
        } else {
            nhoursnew = "" + nHours;
        }
        if (nMin <= 9) {
            nminnew = "0" + nMin;
        } else {
            nminnew = "" + nMin;
        }
        if (nSec <= 9) {
            nsecnew = "0" + nSec;
        } else {
            nsecnew = "" + nSec;
        }
        return nhoursnew + ":" + nminnew + ":" + nsecnew;
    }


}
