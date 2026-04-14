import java.util.Objects;
import java.io.Serializable;

public class Contestant implements Serializable{
    public String name;
    public String act;
    public String actCategory;
    public int duration;
    public int importance;

    public Contestant(String name, String act, String actCategory, int duration, int importance) {
        this.name = name;
        this.act = act;
        this.actCategory = actCategory;
        this.duration = duration;
        this.importance = importance;
    }

    public Contestant(String name, String act, String actCategory, int duration) {
        this.name = name;
        this.act = act;
        this.actCategory = actCategory;
        this.duration = duration;
        this.importance = -1;
    }

    public Contestant() {
        this.name = "lacking";
        this.act = "lacking";
        this.actCategory = "lacking";
        this.duration = 0;
        this.importance = -1;
    }

    @Override
    public String toString() {

        if (name.equals("lacking")) {
            return "This song is empty";
        }
        if (importance == -1) {
            return String.format("%s will perform a %s called %s. It lasts %d minutes. Importance is unknown", name, actCategory, act, duration);
        }
        return String.format("%s will perform a %s called %s. It lasts %d minutes, importance = %d", name, actCategory, act, duration, importance);
    }

    @Override
    public boolean equals(Object o) {
        // 1. Check if it's the exact same memory address
        if (this == o) return true;
        
        // 2. Check if the other object is null or a different class
        if (o == null || getClass() != o.getClass()) return false;
        
        // 3. Cast the object and compare the specific fields
        Contestant that = (Contestant) o;
        return Objects.equals(name, that.name) && 
            Objects.equals(act, that.act);
    }

    @Override
    public int hashCode() {
        // Generates a unique integer based on the name and act
        return Objects.hash(name, act);
    }
}