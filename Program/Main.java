public class Main {
    public static void main(String[] args) {
        LineUp submissions = new LineUp();

        // Pause pause1 = new Pause(15, "The first break");
        // submissions.addPause(pause1);

        // Pause pause2 = new Pause(10, "The second break");
        // submissions.addPause(pause2);

        // Stageprep fixSound = new Stageprep(20, 16, 45, "Fix sound for elguitar etc...");
        // submissions.addPreparation(fixSound);

        // Contestant Jimmy = new Contestant("Jimmy", "Ordinary", "song", 7, -10);
        // submissions.addSubmission(Jimmy);
        // Contestant Mary = new Contestant("Mary", "Bazz", "trombone piece", 3, 30);
        // submissions.addSubmission(Mary);
        // Contestant Orion = new Contestant("Orion", "Final destiny", "trumpet piece", 12, 20);
        // submissions.addSubmission(Orion);
        // Contestant Adam = new Contestant("Adam", "Grace Kelly", "song", 5, 70);
        // submissions.addSubmission(Adam);
        // Contestant Eliza = new Contestant("Eliza", "Mr Blue", "song", 7, -40);
        // submissions.addSubmission(Eliza);
        // Contestant Sophia = new Contestant("Sophia", "Wuthering heights", "song", 10, 80);
        // submissions.addSubmission(Sophia);
        // Contestant Leo = new Contestant("", "Stolen Dance", "song", 5, 40);
        // submissions.addSubmission(Leo);
        // submissions.removeSubmission("Kim");


        System.out.println(submissions.toString());
        Script script = new Script(15, 18, 30, 22, 15, 11, 30, submissions);
        submissions.Writer();
        System.out.println(script.toString());

        // ... setup your submissions and stand structure ...
        Script myScript = new Script(15, 18, 30, 22, 15, 11, 30, submissions);;

        // Launch the UI
        ScriptDisplay.showScript(myScript);

    }
}
