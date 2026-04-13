import java.io.*;
import java.util.ArrayList;

public class LineUp {
    private String FILENAME = "submissionList.ser";
    public ArrayList<Contestant> stagePool;

    public LineUp() {
        // When LineUp is created, try to load existing data from the file
        this.stagePool = loadList();
    }

    public void addSubmission(Contestant contestant) {
        stagePool.add(contestant);
        saveList(); // Auto-save
    }

    public void removeSubmission(Contestant contestant) {
        stagePool.remove(contestant);
        saveList(); // Auto-save
    }

    // Thanks to Gemini for making saving functionality
    public void saveList() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(stagePool);
            System.out.println("Data saved successfully to " + FILENAME);
        } catch (IOException e) {
            System.err.println("Error saving list: " + e.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    public ArrayList<Contestant> loadList() {
        File file = new File(FILENAME);
        if (!file.exists()) {
            System.out.println("No existing save file found. Starting fresh.");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            return (ArrayList<Contestant>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading list: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // debug
    public void Writer() {
        if (stagePool.isEmpty()) {
            System.out.println("The stage pool is currently empty.");
        } else {
            for (Contestant c : stagePool) {
                System.out.println(c.toString());
            }
        }
    }
}
