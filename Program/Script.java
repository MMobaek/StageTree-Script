public class Script {
    private StandStructure timing;
    public String[] script;
    private int[] timeStamps;

    
    public Script(int contestantCount, int pauseCount, int starthour, int startMinute, int endHour, int endMinute, int beredingHour, int beredingMinute) {
        this.timing = new StandStructure(contestantCount, pauseCount, starthour, startMinute, endHour, endMinute, beredingHour, beredingMinute);
        this.timeStamps = timing.timeStamps();
        this.script = new String[timeStamps.length];
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
            agenda = agenda + script[i] + "\n";
        }
        return agenda;
    }
}
