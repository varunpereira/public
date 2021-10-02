package generation;
import nearestNeigh.Point;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommandGeneration {
    private List<Point> randomPointsList = DataGeneration.getRandomPointsList(); //get list of points previously generated
    private List<Point> additionalPointsList = DataGeneration.getadditionalPointsList();
    private static List<Command> randomCommandList = new ArrayList<>();
    Random random = new Random();
    int MaxK = 20; //maximum number of neighbours to search for
    DataGeneration DG = new DataGeneration();

    // Used to create random commands for scenario 1
    public void GenerateKnnSearches(int numCommands, String commandOutputFileName){
        GenerateSearchCommand();
        OutputFile(commandOutputFileName);
    }

    // Used to create random commands for scenario 2
    public void GenerateDynamicPointsSet(int numCommands, String commandOutputFileName) {
        try{
            for(int i  = 0; i < numCommands; i++){ // handles generating delete command
                int location = getRandomLocation();
                String ID = randomPointsList.get(location).getId();
                String category = randomPointsList.get(location).getCategory().toString();
                double lat = randomPointsList.get(location).getLat();
                double lon = randomPointsList.get(location).getLon();
                Command deleteCommand = new Command("D", "id" + ID, category, lat, lon);
                randomCommandList.add(deleteCommand);

            }

            for(int i  = 0; i < numCommands; i++){ // handles generating add command
                int newID = additionalPointsList.size() + i;
                DG.generateAdditionalPoints(i);
                String category = additionalPointsList.get(i).getCategory().toString();
                double lat = additionalPointsList.get(i).getLat();
                double lon = additionalPointsList.get(i).getLon();
                Command addCommand = new Command("A", "id" + newID, category, lat, lon);
                randomCommandList.add(addCommand);
            }
            // Generate Search Command
            GenerateSearchCommand();
        }catch(Exception e){
            System.err.println(e);
        }
        OutputFile(commandOutputFileName);
    }

    private void GenerateSearchCommand() {
        for(int i = 2; i <= 20; i+=2){
            int location = getRandomLocation();
            String category = randomPointsList.get(location).getCategory().toString();
            double lat = randomPointsList.get(location).getLat();
            double lon = randomPointsList.get(location).getLon();
            Command searchCommand = new Command("S", category, lat, lon, i);
            randomCommandList.add(searchCommand);
        }
    }

    // Picks a random point from the generated data
    private int getRandomLocation() {
        return random.nextInt(randomPointsList.size());
    }

    // Returns a random K value to search for
    private int getRandomKValue() {
        return random.nextInt(MaxK);
    }

    //Handles outputting the commands into a text file
    private void OutputFile(String dataOutputFileName){
        try{
            //Create necessary file writers & buffered writer to output generated data as a file
            File file = new File(dataOutputFileName);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writeFileToDisk = new BufferedWriter(fileWriter);

            for (Command command : randomCommandList) {
                if(command.getOperation().equals("S")){
                    writeFileToDisk.write(command.searchCommandToString()); //converts point object to string
                }else{
                    writeFileToDisk.write(command.actionCommandToString()); //converts point object to string
                }
                writeFileToDisk.newLine(); //create new line for next output
            }
            writeFileToDisk.flush(); //take data in memory & write to disk
            writeFileToDisk.close();
        }catch(IOException e){
            System.err.println(e);
        }
    }

}
