import java.io.*;
import java.util.ArrayList;

public class LineUp {
    private String FILENAME = "submissionList.ser";
    public ArrayList<Contestant> stagePool;
    public ArrayList<Pause> pauses;
    public ArrayList<Stageprep> stageprep;

    public LineUp() {
        // Load existing data; loadList now initializes both lists
        loadList();
    }

    public void addSubmission(Contestant contestant) {
        removeSubmission(contestant.name);
        stagePool.add(contestant);
        saveList(); 
    }

    public void removeSubmission(String name) {
        for (int i = 0; i < stagePool.size(); i++) {
            if (stagePool.get(i).name.equals(name)) {
                stagePool.remove(i);
                saveList();
                break;
            }
        }
    }

    public void addPause(Pause pause) {
        pauses.add(pause);
        saveList();
    }

    public void popPause(int index) {
        pauses.remove(index);
        saveList();
    }

    public void removePause(String description) {
        for (int i = 0; i < pauses.size(); i++) {
            if (pauses.get(i).description.equals(description)) {
                pauses.remove(i);
                saveList();
                break;
            }
        }
    }

    public void addPreparation(Stageprep prep) {
        stageprep.add(prep);
        saveList();
    }

    public void popPreparation(int index) {
        Stageprep s;
        for (int i = 0; i < stageprep.size(); i++) {
            for (int j = 0; j < stageprep.size() - 1; j++) {
                if (stageprep.get(j).time > stageprep.get(j+1).time) {
                    s = stageprep.get(j);
                    stageprep.set(j, stageprep.get(j+1));
                    stageprep.set(j+1, s);
                }
            }
        }
        stageprep.remove(index);
        saveList();
    }

    public void removePreparation(String description) {
        for (int i = 0; i < stageprep.size(); i++) {
            if (stageprep.get(i).description.equals(description)) {
                stageprep.remove(i);
                saveList();
                break;
            }
        }
    }

    public void saveList() {
        duplicantRemover();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            // Save both objects in order
            oos.writeObject(stagePool);
            oos.writeObject(pauses);
            oos.writeObject(stageprep);
            System.out.println("Data saved successfully to " + FILENAME);
        } catch (IOException e) {
            System.err.println("Error saving list: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadList() {
        File file = new File(FILENAME);
        if (!file.exists()) {
            this.stagePool = new ArrayList<>();
            this.pauses = new ArrayList<>();
            this.stageprep = new ArrayList<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            this.stagePool = (ArrayList<Contestant>) ois.readObject();
            this.pauses = (ArrayList<Pause>) ois.readObject();
            this.stageprep = (ArrayList<Stageprep>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading list: " + e.getMessage());
            this.stagePool = new ArrayList<>();
            this.pauses = new ArrayList<>();
            this.stageprep = new ArrayList<>();
        }
    }

    private void duplicantRemover() {
        java.util.LinkedHashSet<Contestant> set = new java.util.LinkedHashSet<>(stagePool);
        stagePool = new ArrayList<>(set);
    }

    public void Writer() {
        if (stagePool.isEmpty()) {
            System.out.println("The stage pool is currently empty.");
        } else {
            for (Contestant c : stagePool) {
                System.out.println(c.toString());
            }
        }
    }

    public String toString() {
        return String.format("Submissions: %d\nPauses: %d\nPreperation tasks:%d", stagePool.size(), pauses.size(), stageprep.size());
    }
}
