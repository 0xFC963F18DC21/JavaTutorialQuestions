package net.nergi.solutions;

import java.util.HashMap;
import net.nergi.Solution;
import net.nergi.Utils;

@SuppressWarnings("unused")
public class Pdd4c implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "dd4c: Clocks";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    // Test for basic functionality.
    Thread[] testClocks = new Thread[]{
        new Thread(
            new Ticker(
                "Clock 1",
                new Clock(0),
                15
            )
        ),
        new Thread(
            new Ticker(
                "Clock 2",
                new Clock(0, 0, 0),
                15
            )
        ),
        new Thread(
            new Ticker(
                "Clock 3",
                new AlarmClock(0, 0, 0, 0, 0, 10),
                80
            )
        ),
        new Thread(
            new Ticker(
                "Clock 4",
                new RadioAlarmClock(0, 10, RadioAlarmClock.RadioStation.STATION_L),
                80
            )
        ),
        new Thread(
            new Ticker(
                "Clock 5",
                new RadioAlarmClock(0, 0, 0, 0, 0, 10, RadioAlarmClock.RadioStation.RADIO_4),
                80
            )
        )
    };

    for (Thread t : testClocks) {
      t.start();
    }

    try {
      for (Thread t : testClocks) {
        t.join();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("Test concluded.");
  }

  public static class Ticker implements Runnable {

    private final String name;
    private int secondsToTick;
    private final Clock internalClock;

    private Ticker(String name, Clock clock, int duration) {
      this.name = name;
      internalClock = clock;
      secondsToTick = duration;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
      if (secondsToTick == 0) {
        throw new IllegalStateException("Ticker has no more ticks and cannot be re-used!");
      }

      for (; secondsToTick > 0; --secondsToTick) {
        String tickString = String.format("%s shows: %s", name, internalClock);

        synchronized (System.out) {
          System.out.println(tickString);
        }

        try {
          // We *want* this to busy-wait.
          // Inspection suppression added to stop IntelliJ from complaining.
          Thread.sleep(1000L);
          internalClock.tick();
        } catch (InterruptedException e) {
          e.printStackTrace();
          return;
        }
      }
    }

  }

  public static class Clock {

    protected static final int SECONDS_IN_MINUTE = 60;
    protected static final int SECONDS_IN_HOUR = SECONDS_IN_MINUTE * 60;
    protected static final int HOURS_IN_DAY = 24;

    protected final Mode dispMode;

    // Internally, the clock will always use seconds since midnight.
    protected int secondsSinceMidnight;

    protected static int convertTime(int hr, int min, int sec) {
      return (SECONDS_IN_HOUR * hr + SECONDS_IN_MINUTE * min + sec)
          % (SECONDS_IN_HOUR * HOURS_IN_DAY);
    }

    public Clock(int secondsSinceMidnight) {
      this.dispMode = Mode.SECONDS_SINCE_MIDNIGHT;
      this.secondsSinceMidnight = secondsSinceMidnight % (SECONDS_IN_HOUR * HOURS_IN_DAY);
    }

    public Clock(int hr, int min, int sec) {
      this.dispMode = Mode.TWENTY_FOUR;
      this.secondsSinceMidnight = convertTime(hr, min, sec);
    }

    public void tick() {
      secondsSinceMidnight = (secondsSinceMidnight + 1) % (HOURS_IN_DAY * SECONDS_IN_HOUR);
    }

    @Override
    public String toString() {
      if (dispMode == Mode.SECONDS_SINCE_MIDNIGHT) {
        // SSM
        return secondsSinceMidnight + " seconds since midnight.";
      } else {
        // 24
        int hr = secondsSinceMidnight / SECONDS_IN_HOUR;
        int min = (secondsSinceMidnight % SECONDS_IN_HOUR) / SECONDS_IN_MINUTE;
        int sec = secondsSinceMidnight % SECONDS_IN_MINUTE;

        return String.format("%02d:%02d:%02d", hr, min, sec);
      }
    }

    protected enum Mode {
      TWENTY_FOUR, SECONDS_SINCE_MIDNIGHT
    }

  }

  public static class AlarmClock extends Clock {

    protected static final int ALARM_DURATION = 60;

    protected int alarmTimer = 0;
    protected final int alarmStartTime;

    public AlarmClock(int secondsSinceMidnight, int alarmStartTime) {
      super(secondsSinceMidnight);
      this.alarmStartTime = alarmStartTime % (SECONDS_IN_HOUR * HOURS_IN_DAY);

      if (secondsSinceMidnight == this.alarmStartTime) {
        alarmTimer = ALARM_DURATION;
      }
    }

    public AlarmClock(int hr, int min, int sec, int alarmHr, int alarmMin, int alarmSec) {
      super(hr, min, sec);
      alarmStartTime = convertTime(alarmHr, alarmMin, alarmSec);

      if (secondsSinceMidnight == this.alarmStartTime) {
        alarmTimer = ALARM_DURATION;
      }
    }

    public void tick() {
      secondsSinceMidnight = (secondsSinceMidnight + 1) % (HOURS_IN_DAY * SECONDS_IN_HOUR);

      if (alarmTimer > 0) {
        --alarmTimer;
      }

      if (secondsSinceMidnight == this.alarmStartTime) {
        alarmTimer = ALARM_DURATION;
      }
    }

    public boolean alarmIsOn() {
      return alarmTimer > 0;
    }

    @Override
    public String toString() {
      return super.toString() + (alarmIsOn() ? " BEEP!" : "");
    }

  }

  public static class RadioAlarmClock extends AlarmClock {

    private static final HashMap<RadioStation, String> AIRWAVES = Utils.hashMapOf(
        new RadioStation[]{
            RadioStation.NONE,
            RadioStation.RADIO_4,
            RadioStation.LIVE_FIVE,
            RadioStation.STATION_L
        },
        new String[]{
            "BEEP!",
            "Blah, blah, blah...",
            "Tonight on breaking news...",
            "... fallen into the river..."
        }
    );

    private final RadioStation tunedInto;

    public RadioAlarmClock(int secondsAfterMidnight, int alarmStartTime, RadioStation tuning) {
      super(secondsAfterMidnight, alarmStartTime);
      tunedInto = tuning;
    }

    public RadioAlarmClock(int hr, int min, int sec, int alarmHr, int alarmMin, int alarmSec,
        RadioStation tuning) {
      super(hr, min, sec, alarmHr, alarmMin, alarmSec);
      tunedInto = tuning;
    }

    @Override
    public String toString() {
      if (dispMode == Mode.SECONDS_SINCE_MIDNIGHT) {
        // SSM
        return (secondsSinceMidnight + " seconds since midnight.") + (alarmIsOn() ? " " + AIRWAVES
            .get(tunedInto) : "");
      } else {
        // 24
        int hr = secondsSinceMidnight / SECONDS_IN_HOUR;
        int min = (secondsSinceMidnight % SECONDS_IN_HOUR) / SECONDS_IN_MINUTE;
        int sec = secondsSinceMidnight % SECONDS_IN_MINUTE;

        return String.format("%02d:%02d:%02d%s", hr, min, sec,
            (alarmIsOn() ? " " + AIRWAVES.get(tunedInto) : ""));
      }
    }

    public enum RadioStation {
      NONE, RADIO_4, LIVE_FIVE, STATION_L
    }

  }

}
