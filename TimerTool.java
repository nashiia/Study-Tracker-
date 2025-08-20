package FINAL_TERM_PROJECT;

import javax.swing.*;

public class TimerTool {
    private Timer timer;
    private int secondsRemaining;
    private JLabel timerLabel;

    public TimerTool(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    public void startTimer(int minutes) {
        secondsRemaining = minutes * 60;
        updateLabel();

        timer = new Timer(1000, e -> {
            secondsRemaining--;
            updateLabel();
            if (secondsRemaining <= 0) {
                ((Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(null, "Time's up!");
            }
        });
        timer.start();
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
            timerLabel.setText("Timer: stopped");
        }
    }

    private void updateLabel() {
        int min = secondsRemaining / 60;
        int sec = secondsRemaining % 60;
        timerLabel.setText(String.format("Timer: %02d:%02d", min, sec));
    }
}
