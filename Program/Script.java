import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Script {
    private StandStructure timing;
    private SongOrder songOrder;
    public String[] script;
    private int[] timeStamps;
    private int pauseCount;
    private int slotIndex;
    private int pausesUsed;
    private int pauseTime;

    

    
    public Script(int contestantCount, int starthour, int startMinute, int endHour, int endMinute, int beredingHour, int beredingMinute, LineUp submissions) {
        this.pauseCount = submissions.pauses.size();
        this.timing = new StandStructure(contestantCount, pauseCount, starthour, startMinute, endHour, endMinute, beredingHour, beredingMinute);
        this.timeStamps = timing.timeStamps();
        this.script = new String[timeStamps.length];
        this.songOrder = new SongOrder(submissions);
        this.pausesUsed = 0;
        this.slotIndex = 0;
        addPreparation();
        addPerformances();
        exportToCSV();
    } 

    private void addPreparation() {
        while (slotIndex < timeStamps.length && timeStamps[slotIndex] < timing.startTime0) {
            Stageprep foundPrep = null;
            int currentClockTime = timeStamps[slotIndex] + timing.beredingTime; 

            for (Stageprep s : songOrder.submissions.stageprep) {
                if (s.time == currentClockTime) {
                    foundPrep = s;
                    break;
                }
            }

            if (foundPrep != null) {
                int slotsNeeded = Math.ceilDiv(foundPrep.duration, 5);
                for (int j = 0; j < slotsNeeded && slotIndex < script.length; j++) {
                    script[slotIndex] = foundPrep.toString();
                    slotIndex++;
                }
            } else {
                script[slotIndex] = "Other preparation for concert";
                slotIndex++;
            }
        }
    }



    private void addPerformances() {
        int contestantIdx = 0;
        ArrayList<Contestant> contestants = songOrder.submissions.stagePool;

        while (slotIndex < script.length) {
            if (pausePlanner()) {
                addPause();
                continue;
            }

            if (contestantIdx < contestants.size()) {
                Contestant c = contestants.get(contestantIdx);
                int slotsNeeded = c.duration / 5 + 1;

                for (int i = 0; i < slotsNeeded && slotIndex < script.length; i++) {
                    script[slotIndex] = c.toString();
                    slotIndex++;
                }
                contestantIdx++;
            } else {
                script[slotIndex] = "Empty Slot";
                slotIndex++;
            }
        }
    }


    private void addPause() {
        if (slotIndex < script.length) {
            for (int i = 0; i < songOrder.submissions.pauses.get(pausesUsed).duration; i += 5) {
                script[slotIndex] = songOrder.submissions.pauses.get(pausesUsed).toString();
                slotIndex++;
            }
            pausesUsed++;
        }
    }


    private boolean pausePlanner() {
        if (pausesUsed >= pauseCount) return false;

        // Calculate when the NEXT pause should happen
        int nextPauseMilestone = timing.startTime0 + (timing.duration * (pausesUsed + 1) / (pauseCount + 1));

        // If the current slot has reached or passed the milestone, trigger pause
        return timeStamps[slotIndex] >= nextPauseMilestone;
    }

    public ArrayList<String> getSegments () {
        ArrayList<String> agenda = new ArrayList<>();
        for (String s : script) {
            agenda.add(s);
        }
        return agenda;
    }

    public void exportToCSV() {
        String filename = "StageTreeScript.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Optional: Add a header row
            writer.println("Time,Activity");

            for (int i = 0; i < script.length; i++) {
                // Calculate the time exactly as you do in your toString()
                int currentTotalMinutes = timeStamps[i] + (timing.startTime - timing.startTime0);
                String timeStr = String.format("%02d:%02d", 
                    timing.minutesToDatehour(currentTotalMinutes), 
                    timing.minutesToDateminute(currentTotalMinutes));

                // Clean the script content: handle nulls and escape double quotes
                String content = (script[i] == null) ? "" : script[i].replace("\"", "\"\"");

                // Write as "Time","Activity Content"
                writer.println(String.format("%s,\"%s\"", timeStr, content));
            }
            System.out.println("CSV exported successfully to: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }



    @Override
    public String toString() {
        String agenda = "";
        for (int i = 0; i < script.length; i++) {
            int currentTotalMinutes = timeStamps[i] + (timing.startTime - timing.startTime0); 
            agenda = agenda + String.format("%02d:%02d\t%s\n", timing.minutesToDatehour(currentTotalMinutes), timing.minutesToDateminute(currentTotalMinutes), script[i]);
        }
        return agenda;
    }
}
