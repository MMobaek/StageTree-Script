import java.util.ArrayList;



public class StandStructure {
    public int contestantCount;
    public int pauseCount;
    private int beredingHour;
    private int beredingMinute;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    public int startTime;
    private int beredingTime;
    private int endTime;
    private int currentTime;
    private int duration;
    private int beredingTime0;
    public int startTime0;
    private int endTime0;
    


    public StandStructure(int contestantCount, int pauseCount, int startHour, int startMinute, int endHour, int endMinute, int beredingHour, int beredingMinute) {
        this.contestantCount = contestantCount;
        this.pauseCount = pauseCount;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.beredingHour = beredingHour;
        this.beredingMinute = beredingMinute;
        this.startTime = 60 * startHour + startMinute;
        this.endTime = 60 * endHour + endMinute;
        this.beredingTime = 60 * beredingHour + beredingMinute;
        this.duration = endTime - startTime;
        this.beredingTime0 = beredingTime - beredingTime;
        this.startTime0 = startTime - beredingTime;
        this.endTime0 = endTime - beredingTime;
    }

    public int dateToMinutes(int hours, int minutes) {
        return 60 * hours + minutes;
    }

    public int minutesToDatehour(int minutes) {
        return minutes / 60;
    }

    public int minutesToDateminute(int minutes) {
        return minutes % 60;
    }

    public int[] timeStamps() {
        int count = endTime0 / 15;
        int[] times = new int[count];
        for(int i = 0; i < count; i++) {
            times[i] = 15 * i;
        }
        return times;
    }



    // After creating an ArrayList of the participants create a 2x2 list














    public String toString() {
        if (currentTime == 0) {
            return String.format("There are %d participants per now. We are using %d breaks. It starts at %d:%d and ends %d:%d. In minutes this becomes respectively %d and %d. The show lasts for %d minutes. We need to show up at %d:%d (%d minute)", 
                                contestantCount, pauseCount, startHour, startMinute, endHour, endMinute, startTime, endTime, duration, beredingHour, beredingMinute, beredingTime);
        }
        return String.format("There are %d participants per now. We are using %d breaks. It starts at %d:%d and ends %d:%d. In minutes this becomes respectively %d and %d minutes. The last analysed time was at %d minutes. The show lasts for %d minutes. We need to show up at %d:%d (%d)", 
                            contestantCount, pauseCount, startHour, startMinute, endHour, endMinute, startTime, endTime, currentTime, duration, beredingHour, beredingMinute, beredingTime);
    }

}
