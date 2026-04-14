import java.util.ArrayList;

public class Script {
    private StandStructure timing;
    public String[] script;
    private int[] timeStamps;
    private SongOrder songOrder;
    private int slotIndex;

    
    public Script(int contestantCount, int pauseCount, int starthour, int startMinute, int endHour, int endMinute, int beredingHour, int beredingMinute, LineUp submissions) {
        this.timing = new StandStructure(contestantCount, pauseCount, starthour, startMinute, endHour, endMinute, beredingHour, beredingMinute);
        this.timeStamps = timing.timeStamps();
        this.script = new String[timeStamps.length];
        this.songOrder = new SongOrder(submissions);
        this.slotIndex = 0;
        addPreparation();
        addPerformances();
    } 

    private void addPreparation() {
        for (int i = 0; i < timeStamps.length; i++) {
            if (timeStamps[i] < timing.startTime0) {
                script[i] = "Preparation for consert";
                slotIndex++;
            }
        }
    }

    private void addPerformances() {
        for (Contestant c : songOrder.submissions.stagePool) {
            // Calculate slots: e.g., 5 mins / 5 = 1 slot. 
            // Use Math.ceil if durations aren't perfectly divisible.
            int slotsNeeded = c.duration / 5 + 1; 

            for (int i = 0; i < slotsNeeded; i++) {
                if (slotIndex < script.length) {
                    script[slotIndex] = c.toString();
                    slotIndex++;
                }
            }
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
