package bob.lobcorptm;

import bob.lobcorptm.pomodoro.PomodoroClock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class PomodoroController implements Initializable {
    @FXML
    private Label statusText;
    @FXML
    private Label timerDisplayer;
    @FXML
    private Button startButton;
    @FXML
    private ToggleButton pomodoroButton;
    @FXML
    private ToggleButton pausingButton;
    @FXML
    private ToggleButton longPausingButton;
    @FXML
    private ProgressBar progressBar;
    private PomodoroClock timer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timer = new PomodoroClock() {
            @Override
            public void updateView() {
                updateTimer();
            }
            @Override
            public void onFinish() {
                Notifications noti = Notifications.create();
                noti.text("Time's up!");
                noti.title("Angela here!");
                noti.show();
                stopTimer();
                switchStateTo(timer.getStatus());
            }
        };
        switchStateTo(PomodoroClock.Status.WORKING);
    }

    private void updateTimer() {
        timerDisplayer.setText(PomodoroClock.convertSecondsToTime(timer.getTime()));
        double p = 1.0d - (double) timer.getTime() / timer.getMaxTime();
        progressBar.setProgress(p);
    }

    @FXML
    protected void onStartButtonClick() {
        if (timer.getTimeRunning()) {
            pauseTimer();
        } else {
            resumeTimer();
        }
        System.out.println(convertIntToTime((int) timer.getTime()));
    }
    @FXML
    protected void onPomodoroButtonClick() {
        switchStateTo(PomodoroClock.Status.WORKING);
    }
    @FXML
    protected void onPausingButtonClick() {
        switchStateTo(PomodoroClock.Status.PAUSING);
    }
    @FXML
    protected void onLongPausingButtonClick() {
        switchStateTo(PomodoroClock.Status.LONG_PAUSING);
    }
    private void switchStateTo(PomodoroClock.Status status) {
        stopTimer();
        pomodoroButton.setSelected(false);
        pausingButton.setSelected(false);
        longPausingButton.setSelected(false);
        switch (status) {
            case WORKING -> {
                timer.switchStatus(PomodoroClock.Status.WORKING);
                pomodoroButton.setSelected(true);
            }
            case PAUSING -> {
                timer.switchStatus(PomodoroClock.Status.PAUSING);
                pausingButton.setSelected(true);
            }
            case LONG_PAUSING -> {
                timer.switchStatus(PomodoroClock.Status.LONG_PAUSING);
                longPausingButton.setSelected(true);
            }
        }
        updateTimer();
    }

    private void resumeTimer() {
        timer.start();
        startButton.setText("Pause");
        statusText.setText("Running");
    }

    private void pauseTimer() {
        timer.pause();
        startButton.setText("Resume");
        statusText.setText("Paused");
    }
    private void stopTimer() {
        timer.pause();
        startButton.setText("Start");
        statusText.setText("Start");
    }

    private String convertIntToTime(int s) {
        int min = s/60;
        int sec = s%60;
        DecimalFormat df = new DecimalFormat("00");
        return df.format(min) + ":" + df.format(sec);
    }
}