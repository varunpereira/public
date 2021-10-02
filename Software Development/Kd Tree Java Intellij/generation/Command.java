package generation;

//Class for generating Command objects
public class Command {
    private String Operation;
    private String Category;
    private double lat;
    private double lon;
    private int k;
    private String ID;

    public Command() {
        // Default empty constructor
    }

    //For generating Locate knn commands
    public Command(String Operation, String Category, double lat, double lon, int k) {
        this.Operation = Operation;
        this.Category = Category;
        this.lat = lat;
        this.lon = lon;
        this.k = k;
    }

    //for generating Add / Delete / Contains commands
    public Command(String Operation, String ID, String Category, double lat, double lon) {
        this.Operation = Operation;
        this.ID = ID;
        this.Category = Category;
        this.lat = lat;
        this.lon = lon;
    }

    public String searchCommandToString() {
        return Operation + " " + Category + " " + lat + " " + lon + " " + k;
    }

    public String actionCommandToString() {
        return Operation + " " + ID + " " + Category + " " + lat + " " + lon;
    }

    public String getOperation(){
        return Operation;
    }



}
