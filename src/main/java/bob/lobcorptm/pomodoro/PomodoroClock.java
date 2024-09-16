package bob.lobcorptm.pomodoro;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.text.DecimalFormat;

public abstract class PomodoroClock {
  public enum Status {
    WORKING,
    PAUSING,
    LONG_PAUSING
  }
  public static final int WORKING_TIME = 10;
  public static final int PAUSING_TIME = 8;
  public static final int LONG_PAUSING_TIME = 9;
  private Status pomodoroStatus;
  private long time;
  private boolean timeRunning;
  private final Timeline timerTicker;
  private int cycle;

  public PomodoroClock() {
    this.time = WORKING_TIME;
    pomodoroStatus = Status.WORKING;
    timeRunning = false;
    cycle = 0;
    timerTicker = new Timeline(new KeyFrame(Duration.seconds(1.0d), e -> {
      this.time--;
      if (this.time == 0) {
        endPeriod();
      }
      updateView();
    }));
    timerTicker.setCycleCount(Animation.INDEFINITE);
  }

  private void endPeriod() {
    timerTicker.stop();
    timeRunning = false;
    cycle++;
    switch (pomodoroStatus) {
      case WORKING -> {
        if (cycle%4 == 0) {
          switchStatus(Status.LONG_PAUSING);
        } else {
          switchStatus(Status.PAUSING);
        }
      }
      case PAUSING, LONG_PAUSING -> switchStatus(Status.WORKING);
    }
    onFinish();
  }

  public static String convertSecondsToTime(long s) {
    long min = s / 60;
    int sec = (int) (s % 60);
    DecimalFormat df = new DecimalFormat("00");
    return df.format(min) + ':' + df.format(sec);
  }
  public boolean getTimeRunning() {
    return timeRunning;
  }
  public Status getStatus() {
    return pomodoroStatus;
  }
  public long getTime() {
    return time;
  }
  public long getMaxTime() {
    switch (pomodoroStatus) {
      case WORKING -> {
        return WORKING_TIME;
      }
      case PAUSING -> {
        return PAUSING_TIME;
      }
      case LONG_PAUSING -> {
        return LONG_PAUSING_TIME;
      }
      default -> {
        return 1;
      }
    }
  }
  public void start() {
    timeRunning = true;
    timerTicker.play();
  }
  public void pause() {
    timeRunning = false;
    timerTicker.pause();
  }
  public void switchStatus(Status status) {
    pause();
    switch (status) {
      case WORKING -> {
        time = WORKING_TIME;
        this.pomodoroStatus = Status.WORKING;
      }
      case PAUSING -> {
        time = PAUSING_TIME;
        this.pomodoroStatus = Status.PAUSING;
      }
      case LONG_PAUSING -> {
        time = LONG_PAUSING_TIME;
        this.pomodoroStatus = Status.LONG_PAUSING;
      }
    }
  }
  public abstract void updateView();
  public abstract void onFinish();
}
