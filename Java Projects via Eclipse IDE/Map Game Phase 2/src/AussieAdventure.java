import java.awt.Color;

public class AussieAdventure {

	public static void main(String[] args) {
		GTerm gt = new GTerm(670, 600);

		int numLocations = Integer.parseInt(gt.getInputString("How many locations?"));
		while (numLocations < 0)
			numLocations = Integer.parseInt(gt.getInputString("Error! Enter a positive number of locations.."));

		String[] locationNames = new String[numLocations];
		int[] locationXCoords = new int[numLocations];
		int[] locationYCoords = new int[numLocations];
		Color[] locationStates = new Color[numLocations];

		int i = 0;
		while (i < numLocations) {
			String locationString = gt.getInputString("Enter location in the format:\n name,x,y");
			String[] locationData = locationString.split(",");
			String locationName = locationData[0];
			int locationXCoord = Integer.parseInt(locationData[1]);
			int locationYCoord = Integer.parseInt(locationData[2]);

			locationNames[i] = locationName; // only one city, that user entered
			locationXCoords[i] = locationXCoord; // only one x, for that specific i,put
			locationYCoords[i] = locationYCoord;
			locationStates[i] = Color.black;
			i += 1;
		}

		i = 0; // reset back to 0 to allow all ranges of answers
		while (i < numLocations) {
			gt.setXY(locationXCoords[i], locationYCoords[i]);
			gt.setFontColor(locationStates[i]);
			gt.print(locationNames[i]);
			i += 1;
		}

		gt.setXY(0, 0);
		gt.addImageIcon("Australia_relief_map.jpg");

		int numberOfCitiesVisited = 0;
		String whatUserEntered = gt.getInputString("Which location do you want to visit?");

		while ((whatUserEntered != null) && (numberOfCitiesVisited < numLocations)) {

			i = 0;
			while (i < numLocations && !whatUserEntered.equalsIgnoreCase(locationNames[i]))
				i += 1;

			if (i < numLocations && locationStates[i] != Color.red) {
				locationStates[i] = Color.red;
				numberOfCitiesVisited += 1;
			}

			gt.clear();

			i = 0;
			while (i < numLocations) {
				gt.setXY(locationXCoords[i], locationYCoords[i]);
				gt.setFontColor(locationStates[i]);
				gt.print(locationNames[i]);
				i += 1;
			}

			gt.setXY(0, 0);
			gt.addImageIcon("Australia_relief_map.jpg");

			if (numberOfCitiesVisited < numLocations)
				whatUserEntered = gt.getInputString("Which location do you want to visit?");

		}
		gt.showMessageDialog("Well done on visiting all of the locations.");
		gt.close();
	}
}
