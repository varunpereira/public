package generation;

public class RandomDataGeneration {

    //Name of class, used in error messages.
    protected static final String programName = "RandomDataGeneration";

    // List of arguments required for the random data generation program to work
    public static void usage(String programName) {
        System.err.println(programName + ": <Scenario> [number of elements to generate] [number of commands to generate] [Data output fileName] [Command file output fileName]");
        System.err.println("<Scenario> = < 1 | 2 >");
        System.err.println("<number of elements to generate> = Number between 1 - 9,223,372,036,854,775,807");
        System.err.println("<number of elements to generate> = Integer value");
        System.exit(1);
    } // end of usage

    //driver code for random data generation
    public static void main(String[] args) {

        int Scenario = Integer.parseInt(args[0]);
        long numElements = Long.parseLong(args[1]);
        int numCommands = Integer.parseInt(args[2]);
        String dataOutputFileName = args[3];
        String commandOutputFileName = args[4];
        boolean CorrectCommands = numCommands > 0;
        boolean CorrectScenario = Scenario == 1 || Scenario == 2;

        // read command line arguments & checks for errors
        if (args.length != 5 || !CorrectScenario || !CorrectCommands || dataOutputFileName == null || commandOutputFileName == null) {
            System.err.println("Incorrect number of arguments.");
            usage(programName);
        }

        DataGeneration dataGeneration = new DataGeneration();
        CommandGeneration commandGeneration = new CommandGeneration();
        switch (Scenario) {
            case 1 -> {
                dataGeneration.GenerateData(numElements, dataOutputFileName);
                commandGeneration.GenerateKnnSearches(numCommands, commandOutputFileName);
            }
            case 2 -> {
                dataGeneration.GenerateData(numElements, dataOutputFileName);
                commandGeneration.GenerateDynamicPointsSet(numCommands, commandOutputFileName);
            }
        } // end of Switch Statements

    }

}
