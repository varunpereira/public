
public class Location {
	//Location name was chosen for this class, because all location functions in the location array, 
	//are carried out here.
	//Other appropriate alternatives would not have been as simple/accurate such as Place.
	//composition was maintained because splitting would have created unnecessary disruptions.
	//and merging with AussieAdventure class was avoided because the Location action is large
	//and easier to keep this process in another class.
	
	private String name;
	//Named name as it accurately and easily describes the location name.
	//calling it anything else would not be as accurate or efficient for eg. locationname.
	//Data type String was chosen because name of locations are multiple characters, and not numbers.
	//declared outside methods so its available to all methods.
	
	private int xCoordinate;
	//Named xCoordinate as it stores x coordinate.
	//calling it anything else would not be as accurate or efficient for eg. xValue.
	//Data type int was chosen because user enters an integer.
	//other data types like double would've been harder for user, as decimals become unnecessary.
	//declared outside methods so its available to all methods.
	
	private int yCoordinate;
	//Named yCoordinates as it stores y coordinate.
	//calling it anything else would not be as accurate or efficient for eg. yValue.
	//Data type int was chosen because user enters an integer.
	//other data types like double would've been harder for user, as decimals become unnecessary.
	//declared outside methods so its available to all methods.
	
	private boolean visitedBefore;
	//Named visitedBefore as all locations can either be visited or not.
	//calling it anything else would not be as accurate or efficient for e.g. CityHasBeenTravelledTo.
	//Data type boolean as only 2 option are available - visited or not.
	//no other data type alternatives were chosen, because this is easiest way for 2 options
	//using number data types would have been more code unnecessarily
	//declared outside methods so its available to all methods.

	public Location(String name, int xCoordinate, int yCoordinate) {
		//Parameters were chosen, because in this constructor,
		//ALL these three functions were required, to display correct name location and postion.
		//If no parameters were used, then the information user entered would not have been split up properly.
		//All member variables were intialised at start, so they could be used later on with other methods.
		//If I hadn't, then default values would be used, which is risky.
		//Only initialisation was doen because nothing else was required when adding new locations.
		//without these actions, the program would not work properly, so they had to be included.
		//Other potential actions were not included as they were not currently needed in relation to the location.
		
		this.name = name;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.visitedBefore = false;
	}

	public int getXCoordinate() {
		return this.xCoordinate;
	}
	//Name chosen accurately as it retrieve x coordinate.
	//Other names were not as accurate or efficient for eg retrieveX
	//No parameters as the variable was essential data.
	//The User had to enter 3 pieces of data only.
	//Return type int was used as data was best stored as integer
	//Composition of this method's structure was maintained as merging or splitting
	//proved to be unnecessarily complex and inefficient

	public int getYCoordinate() {
		return this.yCoordinate;
	}
	//Name chosen accurately as it retrieves y coordinate.
	//Other names were not as accurate or efficient for eg retrieve Y
	//No parameters as the variable was essential data. 
	//The User had to enter 3 pieces of data only.
	//Return type int was used as data was best stored as integer
	//Composition of this method's structure was maintained as merging or splitting
	//proved to be unnecessarily complex and inefficient

	public boolean getVisitedBefore() {
		return this.visitedBefore;
	}
	//Name chosen accurately as it retrieves status of visit.
	//Other names were not as accurate or efficient for eg retrieveStatus
	//No parameters as the variable was essential data.
	//The User had to enter 3 pieces of data only.
	//Return type boolean was used as data was best stored as boolean
	//Composition of this method's structure was maintained as merging or splitting
	//proved to be unnecessarily complex and inefficient

	public String getName() {
		return this.name;
	}
	//Name chosen accurately as it retrieves location name.
	//Other names were not as accurate or efficient for eg retrieveLocation
	//No parameters as the variable was essential data. 
	//The User had to enter 3 pieces of data only.
	//Return type string was used as data was best stored as string
	//Composition of this method's structure was maintained as merging or splitting
	//proved to be unnecessarily complex and inefficient
	
	public void setVisitedBefore(boolean visitedBefore) {
		this.visitedBefore = visitedBefore;
	}
	//Name chosen accurately as it retrieves if location visited or not.
	//Other names were not as accurate or efficient for eg changeVisitStatus
	//Parameters were carefully chosen, as ,location visited could be true or false only, and depends on previous input.
	//I could not think of any similar parameters, as this was most efficient.
	//The User had to enter 3 pieces of data only.
	//Return type void  was used as data was best to be NOT returned to other class.
	//Composition of this method's structure was maintained as merging or splitting
	//proved to be unnecessarily complex and inefficient
	
	public String toString() {
		return this.name;
	}
	//Name chosen accurately as converts location name to string.
	//No parameters as the variable was essential data.
	//The User had to enter 3 pieces of data only.
	//Return type string was used as data was best stored as string
	//Composition of this method's structure was maintained as merging or splitting
	//proved to be unnecessarily complex and inefficient

}
