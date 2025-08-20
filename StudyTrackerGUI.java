package FINAL_TERM_PROJECT;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class StudyTrackerGUI extends JFrame {

    private StudyTracker tracker;
    private ProgressTracker progress;
    private ToDoList todoList;
    private CalendarManager calendarManager;
    private TimerTool timerTool;

    private DefaultTableModel sessionModel;
    private DefaultTableModel todoModel;
    private DefaultTableModel reminderModel;

    private JProgressBar xpBar;
    private JLabel levelLabel;

    public StudyTrackerGUI() {
        tracker = new StudyTracker();
        progress = new ProgressTracker();
        todoList = new ToDoList();
        calendarManager = new CalendarManager(todoList);
        timerTool = new TimerTool(levelLabel);

        setTitle("Study Tracker");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Tables
        sessionModel = new DefaultTableModel(new String[] { "Subject", "Date", "Minutes" }, 0);
        JTable sessionTable = new JTable(sessionModel);

        reminderModel = new DefaultTableModel(new String[] { "Reminder", "Date" }, 0);
        JTable reminderTable = new JTable(reminderModel);

        todoModel = new DefaultTableModel(new String[] { "Task", "Due Date", "Done" }, 0);
        JTable todoTable = new JTable(todoModel);

        JPanel tablesPanel = new JPanel(new GridLayout(1, 3));
        tablesPanel.add(new JScrollPane(sessionTable));
        tablesPanel.add(new JScrollPane(reminderTable));
        tablesPanel.add(new JScrollPane(todoTable));

        add(tablesPanel, BorderLayout.CENTER);

        // XP + Timer panel
        JPanel progressPanel = new JPanel(new GridLayout(2, 1));

        // XP row
        JPanel xpPanel = new JPanel(new FlowLayout());
        xpBar = new JProgressBar(0, 100);
        levelLabel = new JLabel("Level: 1");
        xpPanel.add(levelLabel);
        xpPanel.add(xpBar);

        // Timer row
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel timerLabel = new JLabel("Timer: Stopped");
        timerTool = new TimerTool(timerLabel);

        JButton startButton = new JButton("Start Timer");
        startButton.addActionListener(e -> {
            String minutesStr = JOptionPane.showInputDialog("Enter minutes:");
            try {
                int minutes = Integer.parseInt(minutesStr);
                timerTool.startTimer(minutes);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });

        JButton stopButton = new JButton("Stop Timer");
        stopButton.addActionListener(e -> timerTool.stopTimer());

        timerPanel.add(timerLabel);
        timerPanel.add(startButton);
        timerPanel.add(stopButton);

        // Add rows to progressPanel
        progressPanel.add(xpPanel);
        progressPanel.add(timerPanel);

        add(progressPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(createAddSessionButton());
        buttonPanel.add(createAddReminderButton());
        buttonPanel.add(createMarkTaskDoneButton(todoTable));
        buttonPanel.add(createViewCalendarButton());
        buttonPanel.add(createViewTotalMinutesButton());

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Buttons
    private JButton createAddSessionButton() {
        JButton button = new JButton("Add Study Session");
        button.addActionListener(e -> {
            String subject = JOptionPane.showInputDialog("Enter subject:");
            String dateStr = JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):");
            String minutesStr = JOptionPane.showInputDialog("Enter minutes:");
            try {
                LocalDate date = LocalDate.parse(dateStr);
                int minutes = Integer.parseInt(minutesStr);
                StudySession session = new StudySession(subject, date, minutes);
                tracker.addSession(session);
                progress.addXP(minutes);

                sessionModel.addRow(new Object[] { subject, date, minutes });
                xpBar.setValue(progress.getXPPercentage());
                levelLabel.setText("Level: " + progress.getLevel());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });
        return button;
    }

    private JButton createAddReminderButton() {
        JButton button = new JButton("Add Reminder");
        button.addActionListener(e -> {
            String text = JOptionPane.showInputDialog("Enter reminder:");
            String dateStr = JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):");

            try {
                LocalDate date = LocalDate.parse(dateStr);
                Reminder r = new Reminder(text, date);
                calendarManager.addReminder(r);

                reminderModel.addRow(new Object[] { r.getText(), r.getDate() });
                todoModel.addRow(new Object[] { r.getText(), r.getDate(), "No" });
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });
        return button;
    }

    private JButton createMarkTaskDoneButton(JTable todoTable) {
        JButton button = new JButton("Mark Task Done");
        button.addActionListener(e -> {
            int row = todoTable.getSelectedRow();
            if (row >= 0) {
                todoList.markDone(row);
                todoModel.setValueAt("Yes", row, 2);
            } else {
                JOptionPane.showMessageDialog(this, "Select a task first!");
            }
        });
        return button;
    }


    private JButton createViewCalendarButton() {
        JButton button = new JButton("View Calendar");
        button.addActionListener(e -> {
            String monthStr = JOptionPane.showInputDialog("Enter month (1-12):");
            String yearStr = JOptionPane.showInputDialog("Enter year:");
            try {
                int month = Integer.parseInt(monthStr);
                int year = Integer.parseInt(yearStr);
                String calendar = calendarManager.getMonthCalendarAsString(month, year);
                JOptionPane.showMessageDialog(this, calendar);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });
        return button;
    }

    private JButton createViewTotalMinutesButton() {
        JButton button = new JButton("View Total Minutes");
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Total study minutes: " + tracker.getTotalMinutes());
        });
        return button;
    }

    // Run
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudyTrackerGUI().setVisible(true));
    }
}
