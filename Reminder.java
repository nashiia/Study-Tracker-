package FINAL_TERM_PROJECT;

import java.time.LocalDate;

public class Reminder {
    private String text;
    private LocalDate date;

    public Reminder(String text, LocalDate date) {
        this.text = text;
        this.date = date;
    }

    public String getText() { return text; }
    public LocalDate getDate() { return date; }

    @Override
    public String toString() {
        return text + " on " + date;
    }
}
