public class Main {
    public static void main(String[] args) {
        LineUp submissions = new LineUp();
        //StandStructure organization = new StandStructure(15, 4, 18, 30, 22, 15, 11, 30);

        // Contestant Jimmy = new Contestant("Jimmy", "Ordinary", "song", 7, -10);
        // submissions.addSubmission(Jimmy);
        //submissions.removeSubmission("Kim");

        Script script = new Script(15, 4, 18, 30, 22, 15, 11, 30, submissions);
        submissions.Writer();
        //System.out.println(script.toString());
    }
}
