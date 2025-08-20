package FINAL_TERM_PROJECT;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CalendarManager {
    private List<Reminder> reminders;
    private ToDoList todoList;

    public CalendarManager(ToDoList todoList) {
        this.reminders = new ArrayList<>();
        this.todoList = todoList;
    }

    public void addReminder(Reminder r) {
        reminders.add(r);
        todoList.addTask(new Task(r.getText(), r.getDate())); // sync with ToDo
    }

    public List<Reminder> getRemindersForMonth(int month, int year) {
        List<Reminder> list = new ArrayList<>();
        for (Reminder r : reminders) {
            if (r.getDate().getMonthValue() == month && r.getDate().getYear() == year) {
                list.add(r);
            }
        }
        return list;
    }

    public String getMonthCalendarAsString(int month, int year) {
        StringBuilder sb = new StringBuilder();
        YearMonth yearMonth = YearMonth.of(year, month);
        sb.append("Calendar for ")
          .append(yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH))
          .append(" ").append(year).append("\n");
        sb.append("Sun Mon Tue Wed Thu Fri Sat\n");

        LocalDate firstDay = yearMonth.atDay(1);
        int dayOfWeekIndex = firstDay.getDayOfWeek().getValue() % 7; // Sunday=0

        for (int i = 0; i < dayOfWeekIndex; i++) {
            sb.append("    ");
        }

        int daysInMonth = yearMonth.lengthOfMonth();
        List<Reminder> monthReminders = getRemindersForMonth(month, year);

        for (int day = 1; day <= daysInMonth; day++) {
            boolean hasReminder = false;
            for (Reminder r : monthReminders) {
                if (r.getDate().getDayOfMonth() == day) {
                    hasReminder = true;
                    break;
                }
            }

            if (hasReminder) {
                sb.append(String.format("%2d* ", day));
            } else {
                sb.append(String.format("%3d ", day));
            }

            dayOfWeekIndex++;
            if (dayOfWeekIndex % 7 == 0) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
