import java.io.Serializable;

public class Pause implements Serializable {
    public int duration;
    public String description;

    public Pause (int duration) {
        this.duration = duration;
    }

    public Pause (int duration, String description) {
        this.duration = duration;
        this.description = description;
    }

    @Override
    public String toString() {
        if (description == null) {
            return String.format("A %d minute break", duration);
        } 
        return String.format("A %d minute break. %s", duration, description);
    }
}

