import java.io.Serializable;

public class Stageprep implements Serializable {
    public int duration;
    public String description;
    public int time;

    public Stageprep (int duration, int hour, int minute, String description) {
        this.duration = duration;
        this.description = description;
        this.time = 60*hour + minute;
    }

    public Stageprep (int duration, int time, String description) {
        this.duration = duration;
        this.description = description;
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%s will be done for %d minuets (%d:%d)", description, duration, time/60, time%60);
    }
}
