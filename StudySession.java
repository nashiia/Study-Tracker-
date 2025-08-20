package FINAL_TERM_PROJECT;

import java.time.LocalDate;

public class StudySession {
    private String subject;
    private LocalDate date;
    private int minutes;

    public StudySession(String subject, LocalDate date, int minutes) {
        this.subject = subject;
        this.date = date;
        this.minutes = minutes;
    }

    public String getSubject() { return subject; }
    public LocalDate getDate() { return date; }
    public int getMinutes() { return minutes; }

    @Override
    public String toString() {
        return subject + " on " + date + " for " + minutes + " minutes";
    }
}
