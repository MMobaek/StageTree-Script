public class Main {
    public static void main(String[] args) {
        LineUp submissions = new LineUp();

        // Pause pause1 = new Pause(15, "The first break");
        // submissions.addPause(pause1);

        // Pause pause2 = new Pause(10, "The second break");
        // submissions.addPause(pause2);

        System.out.print(submissions.toString());
        //StandStructure organization = new StandStructure(15, 4, 18, 30, 22, 15, 11, 30);


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

        Script script = new Script(15, 18, 30, 22, 15, 11, 30, submissions);
        submissions.Writer();
        System.out.println(script.toString());
    }
}
