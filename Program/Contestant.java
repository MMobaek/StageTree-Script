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

    public String toString() {

        if (name.equals("lacking")) {
            return "This song is empty";
        }
        if (importance == -1) {
            return String.format("%s will perform a %s called %s. It lasts %d minutes. Importance is unknown", name, actCategory, act, duration);
        }
        return String.format("%s will perform a %s called %s. It lasts %d minutes", name, actCategory, act, duration);
    }
}