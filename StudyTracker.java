package FINAL_TERM_PROJECT;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudyTracker {
    private List<StudySession> sessions;

    public StudyTracker() {
        this.sessions = new ArrayList<>();
    }

    public void addSession(StudySession s) {
        sessions.add(s);
    }

    public List<StudySession> getSessions() {
        return sessions;
    }

    public int getTotalMinutes() {
        int total = 0;
        for (StudySession s : sessions) {
            total += s.getMinutes();
        }
        return total;
    }

    public List<StudySession> getSessionsByDate(LocalDate date) {
        List<StudySession> result = new ArrayList<>();
        for (StudySession s : sessions) {
            if (s.getDate().equals(date)) {
                result.add(s);
            }
        }
        return result;
    }
}
