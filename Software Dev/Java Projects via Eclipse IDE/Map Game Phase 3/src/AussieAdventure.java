import java.awt.Color;

public class AussieAdventure {
	//class name AussieAdventure accurately describes the program, a travel adventure on australian map.
	//Other appropriate possibilities include OZAdventure or AussieTravels.
	//class contains bulk of code, performs all necessary actions of program,
	//except for Locations array.

	private GTerm gtMap;
	//Named gtMap as its where australian map is displayed.
	//calling it anything else would not be as accurate for eg. gtPicture.
	//Data type GTerm was chosen because a new window is required for map, 
	//no other window alternatives were available in GTerm class.
	//declared outside methods so its available to all methods.
	
	private GTerm gtMenu;
	//Named gtMenu as its where the menu list is displayed.
	//calling it anything else would not be as accurate for eg. gtList.
	//Data type GTerm was chosen because a new window is required for menu, 
	//no other window alternatives were available in GTerm class.
	//declared outside methods so its available to all methods.
	
	private Location[] locations;
	//Named locations as all locations will be stored here.
	//calling it anything else would not be as accurate for eg. Cities.
	//Data type Location was chosen because it refers to location class and its functions.
	//no other window alternatives were available in GTerm class.
	//declared outside methods so its available to all methods.
	
	private int currentNumLocations;
	//Named currentNumLocations as it keeps track of which location currently visiting.
	//calling it anything else would not be as accurate for eg. howManyCities.
	//Data type int was chosen because its a number, 
	//other number data types like double were possible, but using whole numbers is easiest.
	//declared outside methods so its available to all methods.
	
	private int numberOfLocationsVisited;
	//Named numberOfLocationsVisited as it keeps track of number locatiosn visited.
	//calling it anything else would not be as accurate or efficient for eg. howManyBeenTo
	//Data type int was chosen because a whole number was required.
	//other number data types like double were possible, but int was easier.
	//declared outside methods so its available to all methods.


	public AussieAdventure() {
	//No parameters were chosen, because in this constructor,
	//all functions had necessary actions, that made program run properly.
	//using parameters is unnecessary and inefficient use of code.
	//All member variables were intialised at start, so they could be used later on.
	//If I hadn't then default values would be used, which is risky.
	//All other functions were included here, because the user always should have access
	//to all those functions including add location button.
	//without these actions, the program would not work properly, so they had to be included.
	//Other potential actions were not included as they were not currently needed.
	
		
		
		this.locations = new Location[10];
		this.currentNumLocations = 0;
		this.numberOfLocationsVisited = 0;

		this.gtMap = new GTerm(670, 600);
		this.gtMap.setXY(0, 0);
		this.gtMap.addImageIcon("Australia_relief_map.jpg");

		this.gtMenu = new GTerm(670, 600);
		this.gtMenu.addTextField("", 300);
		this.gtMenu.println("");
		this.gtMenu.addButton("Add location", this, "addLocation");
		this.gtMenu.println("");
		this.gtMenu.addList(300, 500, this, "visitLocation");
	}


	public void addLocation() {
		//Name chosen accurately as it demonstrates the process that occurs when you click button called addlocation.
		//Other names were not as accurate or efficient for eg creatingNewLocation
		//No parameters as all actions and variables were an essential process to run program properly.
		//any other alternatives would have hindered program's normal operations or were unnecessary.
		//Return type void  was used as data was best to be NOT returned any other method.
		//Composition of this method's structure was maintained as merging or splitting
		//proved to be unnecessarily complex and inefficient
		
		if (this.currentNumLocations < this.locations.length) {
			//condition was chosen as it accurately checks if current number locations are less than total location number value in array.
			//other conditions would not have been as efficient like changing the broad equation to a more specific equation like LHS + RHS -1.
			//Body was chosen because it precisely demonstrates the options and their processes when add location button is clicked.
			//The body can not belong anywhere else as it is part of a cohesive sequence.
			
			String locationString = this.gtMenu.getTextFromEntry(0);
			String[] locationElements = locationString.split(",");

			this.locations[this.currentNumLocations] = new Location(locationElements[0],
					Integer.parseInt(locationElements[1]), Integer.parseInt(locationElements[2]));

			this.gtMenu.addElementToList(0, this.locations[this.currentNumLocations]);
			this.currentNumLocations += 1;
		}
		redrawMap();
	}
	
	
	public void redrawMap() {
		//Name chosen accurately as it demonstrates that map is redrawn after every location is added/visited.
		//Other names were not as accurate or efficient for eg resettingThePicture.
		//No parameters as all actions and variables were an essential process to run program properly.
		//any other alternatives would have hindered program's normal operations or were unnecessary.
		//Return type void  was used as data was best to be NOT returned any other method.
		//Composition of this method's structure was maintained as merging or splitting
		//proved to be unnecessarily complex and inefficient
		Color notVisitedColor = Color.black;
		Color visitedColor = Color.red;
		this.gtMap.clear();
		int i = 0;
		//i stands for index, 'index' could have been used but its longer.
		//only integers were used as its easier to use whole numbers,
		//using a number data type like double with decimals proves to be unnecessarily complicated.
		//was declared in method as variable only changes once redaw map method is called,
		// it does not need to be updated or changed by other methods because they won't directly need to update it.
		
		
		while (i < this.currentNumLocations) {
			//For this while loop, the condition was required because it allows program to select from all the options the user has
			//entered, and has done so efficiently, to go to next location i += 1 was found to be best option.
			//other similar parameters included using i != this.currentNumLocation - 1,but this proves to be unnecessarily complicated. 
			//this body was very appropriate as it efficiently clears the map, and displayed all visited and unvisited locations and their 
			//corresponding positions.
			//The if statements could not go outside this method, as this method can only be called once the other two methods have completed.
			//thats why they are called right at the end.
			this.gtMap.setXY(this.locations[i].getXCoordinate(), this.locations[i].getYCoordinate());
			if (this.locations[i].getVisitedBefore() == true)
				this.gtMap.setFontColor(visitedColor);
			else
				this.gtMap.setFontColor(notVisitedColor);
			this.gtMap.print(locations[i].getName());
			i += 1;
		}
		//condition was chosen as it accurately only modifies visited or not visited locations.
		//other conditions would not have been appropriate like changing == to a specific value like +1.
		//Body was chosen because it precisely and concisely demonstrates the options and their processes when cuurnet number of locations is modified.
		//The body can not belong anywhere else as it is part of a cohesive sequence.
	
		this.gtMap.setXY(0, 0);
		this.gtMap.addImageIcon("Australia_relief_map.jpg");
	}

	public void visitLocation() {
		//Name chosen accurately as it demonstrates the process that occurs when you add to list called visitLocation.
		//Other names were not as accurate or efficient for eg hadGoneToLocation.
		//No parameters as all actions and variables were an essential process to run program properly.
		//any other alternatives would have hindered program's normal operations or were unnecessary.
		//Return type void  was used as data was best to be NOT returned any other method.
		//Composition of this method's structure was maintained as merging or splitting
		//proved to be unnecessarily complex and inefficient
		if (this.numberOfLocationsVisited != this.currentNumLocations) {
			Location selectedLocation = (Location) this.gtMenu.getSelectedElementFromList(0);

			if (selectedLocation.getVisitedBefore() == false) {
				selectedLocation.setVisitedBefore(true);
				this.numberOfLocationsVisited += 1;
				redrawMap();
			}
			
			//condition was chosen as it accurately only changes values that are within the array.
			//other conditions would not have been as efficient like changing !+ to a specific value like +1.
			//Body was chosen because it precisely and concisely demonstrates the options and their processes when visitLocation list is clicked.
			//The body can not belong anywhere else as it is part of a cohesive sequence.
		}

		if (this.numberOfLocationsVisited == this.currentNumLocations) {
			this.gtMap.showMessageDialog("Congrats! You've visited all locations!");
		}
		//condition was chosen as it accurately shows the conditions to represent the congratulatory message.
		//if they were not equal, it would not have been possible.
		//other conditions would not have been as efficient 
		//Body was chosen because it precisely and concisely demonstrates the options and their processes when add location button is clicked.
		//The body can not belong anywhere else as it is part of a cohesive sequence.
	}

	public static void main(String[] args) {
		AussieAdventure Action1 = new AussieAdventure();
	}
	//main method is first section to run in program, therefore
	//its important to declare and intialise constructor here.
	//name Action1 because main method is first to run in program.
}
