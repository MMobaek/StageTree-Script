public class ScheduleCleaner {

    public String makeNeat(String input) {
        if (input == null || input.isEmpty()) return "";

        String[] lines = input.split("\n");
        StringBuilder result = new StringBuilder();

        int i = 0;
        while (i < lines.length) {
            String[] currentParts = lines[i].split("\t");
            if (currentParts.length < 2) {
                i++;
                continue;
            }

            String startTime = currentParts[0];
            String currentDesc = currentParts[1];

            // Find how many consecutive lines have the same description
            int j = i;
            while (j + 1 < lines.length) {
                String[] nextParts = lines[j + 1].split("\t");
                if (nextParts.length >= 2 && nextParts[1].equals(currentDesc)) {
                    j++;
                } else {
                    break;
                }
            }

            // The 'endTime' is the time from the line immediately AFTER this block
            String endTime;
            if (j + 1 < lines.length) {
                endTime = lines[j + 1].split("\t")[0];
            } else {
                // For the very last block in the schedule, you can define a custom end
                endTime = "End"; 
            }

            result.append(startTime).append(" - ").append(endTime)
                  .append(" ").append(currentDesc).append("\n");

            // Move the index to the start of the next new activity
            i = j + 1;
        }

        return result.toString();
    }
}
