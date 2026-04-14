public class Script {
    private StandStructure timing;
    public String[] script;
    private int[] timeStamps;
    private SongOrder songOrder;

    
    public Script(int contestantCount, int pauseCount, int starthour, int startMinute, int endHour, int endMinute, int beredingHour, int beredingMinute, LineUp submissions) {
        this.timing = new StandStructure(contestantCount, pauseCount, starthour, startMinute, endHour, endMinute, beredingHour, beredingMinute);
        this.timeStamps = timing.timeStamps();
        this.script = new String[timeStamps.length];
        this.songOrder = new SongOrder(submissions);
        addPreparation();
    } 

    private void addPreparation() {
        for (int i = 0; i < timeStamps.length; i++) {
            if (timeStamps[i] < timing.startTime0) {
                script[i] = "Preparation for consert";
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
