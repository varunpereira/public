import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FindMeAHouse {
	private GTerm gt;
	private GTerm gt2;
	private RentalProperty[] rentals;
	private int currentNumRentals;

	public FindMeAHouse() {
		//Initialize
		this.gt = new GTerm(600, 400);
		this.rentals = new RentalProperty[1];
		this.currentNumRentals = 0;
		
		//get property data
		loadFromFile(this.gt.getFilePath());
		
		//build search form
		//address
		this.gt.print("Suburb:");
		this.gt.addTextField("", 200);
		this.gt.println("");
		this.gt.print("State:");
		this.gt.addTextField("", 200);
		this.gt.println("");
		this.gt.print("Postcode:");
		this.gt.addTextField("", 200);
		this.gt.println("");
		//property details
		this.gt.print("Number of Bedrooms:");
		this.gt.addTextField("", 200);
		this.gt.println("");
		this.gt.print("Number of Bathrooms:");
		this.gt.addTextField("", 200);
		this.gt.println("");
		
		//search button
		this.gt.addButton("Search", this, "searchProperty");
		this.gt.addButton("Clear Results", this, "clearResults");
		this.gt.println("");
		this.gt.addList(600, 200, this, "rentalList");
		
//		loadFromFile(this.gt.getFilePath());
//		showRentals();
	}
	

	
	public void clearResults() {
		int i = 0;
		while (i < currentNumRentals) {
			this.gt.removeElementFromList(0, i);
		}
	}

	// You can add methods to suit the functionality
	
	public void searchProperty() {
		//search rentals
		int i = 0;
		while (i < currentNumRentals) {
			
			//split address into search properties
			String[] fields = this.rentals[i].getAddress().split(", ");
			int field = 0;
			String streetNumber = fields[field++];
			String street = fields[field++];
			String suburb = fields[field++];
			String state = fields[field++];
			String postcode = fields[field++];
			
			//searchfields
			//suburb
			if (this.gt.getTextFromEntry(0).contentEquals(suburb)) {
				this.gt.addElementToList(0, this.rentals[i]); 
			}
			
			//state
			if (this.gt.getTextFromEntry(1).contentEquals(state)) {
				this.gt.addElementToList(0, this.rentals[i]); 
			}
			
			//postcode
			if (this.gt.getTextFromEntry(2).contentEquals(postcode)) {
				this.gt.addElementToList(0, this.rentals[i]); 
			}
			
			//
			//bedrooms
			if (this.gt.getTextFromEntry(3).contentEquals(Integer.toString(this.rentals[i].getNumBedrooms()))) {
				this.gt.addElementToList(0, this.rentals[i]); 
			}
			
			//bathrooms
			if (this.gt.getTextFromEntry(4).contentEquals(Integer.toString(this.rentals[i].getNumBathrooms()))) {
				this.gt.addElementToList(0, this.rentals[i]); 
			}
			
			
			
			
			
			
//			if (this.gt.getTextFromEntry(0).contentEquals(this.rentals[i].getAddress())) {
//				this.gt.addElementToList(0, this.rentals[i]); 	
				
				
				
				
//			showRentals();
		
		i += 1;
	}
		
		
/*		if (this.gt.getTextFromEntry(0).contentEquals(this.rentals[i].getAddress())) {
			showRentals(); 
		if (this.gt.getTextFromEntry(0).contentEquals(this.rentals[0].getAddress())) {
			showRentals(); */
/*		}
		else if (this.gt.getTextFromEntry(0).contentEquals(searchedProperty.getAddress())){
			
		}
		else {
			this.gt.showMessageDialog("HELLOOO!");
		} */
	} 
	
	public void showRentals() {
		int i = 0;
		while (i < this.currentNumRentals) {
			this.gt.addElementToList(0, this.rentals[i]);
			i += 1;
		}
	}
	
/*	public void builddevice() {
		this.gt2 = new GTerm(300, 300);
		this.gt2.println("Name:" + rentals);
		this.gt2.println("deviceStatus: " + deviceStatus);// change these to variables ltr
		this.gt2.println("Rated power consumption: " + power + " Watts");
		this.gt2.addImageIcon(deviceName);
	} */
	

	public void addRentalProperty(String address, int numBedrooms, int numBathrooms, double pricePerWeek,
			String agentPhone) {
		if (this.currentNumRentals >= this.rentals.length)
			expandArray();

		this.rentals[this.currentNumRentals] = new RentalProperty(address, numBedrooms, numBathrooms, pricePerWeek,
				agentPhone);
		this.currentNumRentals += 1;
	}

	public void loadFromFile(String filename) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filename));
			String rawLineFromFile = br.readLine();

			while (rawLineFromFile != null) {
				String[] fields = rawLineFromFile.split(":");
				int field = 0;
				String address = fields[field++];
				int numBedrooms = Integer.parseInt(fields[field++]);
				int numBathrooms = Integer.parseInt(fields[field++]);
				double pricePerWeek = Double.parseDouble(fields[field++]);
				String agentPhone = fields[field++];
				addRentalProperty(address, numBedrooms, numBathrooms, pricePerWeek, agentPhone);
				rawLineFromFile = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			this.gt.showMessageDialog("Problem: " + filename + " not found!");
		} catch (IOException e) {
			this.gt.showMessageDialog("Problem: I had trouble accessing " + filename);
		} catch (NullPointerException e) {
			this.gt.showMessageDialog("Problem: Something that was meant to be there was not there!");
		} catch (Exception e) {
			this.gt.showMessageDialog("Problem: Something else went wrong!");
		}
	}

	public void expandArray() {
		RentalProperty[] biggerRentals = new RentalProperty[this.currentNumRentals * 2];
		int i = 0;
		while (i < this.currentNumRentals) {
			biggerRentals[i] = this.rentals[i];
			i += 1;
		}
		this.rentals = biggerRentals;
	}

	public static void main(String[] args) {
		FindMeAHouse prm = new FindMeAHouse();
	}

}
