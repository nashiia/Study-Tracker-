package FINAL_TERM_PROJECT;

public class ProgressTracker {
    private int xp;
    private int level;

    public ProgressTracker() {
        this.xp = 0;
        this.level = 1;
    }

    public void addXP(int amount) {
        xp += amount;
        while (xp >= level * 100) {
            xp -= level * 100;
            level++;
        }
    }

    public int getXP() { return xp; }
    public int getLevel() { return level; }

    public int getXPPercentage() {
        int needed = level * 100;
        return (int) Math.round((xp / (double) needed) * 100);
    }
}
