package online.paescape.util;

public class Countdown {
    private long endMillis;
    private long countdownMillis;
    private long setMillis;

    public Countdown() {
        endMillis = System.currentTimeMillis();
    }

    public Countdown(long endMillis) {
        this.endMillis = endMillis;
    }

    public void addSeconds(int seconds) {
        endMillis += seconds * 1000;
    }

    public int getSeconds() {
        calculate();
        int seconds = (int) (countdownMillis / 1000);
        if (seconds > 60) {
            seconds = seconds % 60;
        }
        return seconds;
    }

    public int getMinutes() {
        calculate();
        int minutes = (int) (countdownMillis / (60 * 1000));
        if (minutes >= 60) {
            minutes = minutes % 60;
        }
        return minutes;
    }

    public boolean finished() {
        return getMinutes() <= 0 && getSeconds() <= 0;
    }

    private void calculate() {
        countdownMillis = endMillis - (System.currentTimeMillis() - setMillis);
    }
}