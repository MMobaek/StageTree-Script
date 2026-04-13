public class Main {
    public static void main(String[] args) {
        LineUp tester = new LineUp();
        StandStructure organization = new StandStructure(15, 4, 18, 30, 22, 15, 11, 30);

        tester.Writer();
        System.out.println(organization.toString());
    }
}
