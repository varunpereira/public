import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Color;

public class CompanyACarManager {
	private GTerm mainWindow;
	// window that has all main functions
	private CarInfo[] carsInfos;
	// everytime a car is created the CarInfo class is called upon.
	//an array is used because its more efficient.
	private int currentNumCar;
//keeps track of how many cars there are currently.

	public static void main(String[] args) {
		CompanyACarManager start = new CompanyACarManager();
	}

	public CompanyACarManager() {
		this.mainWindow = new GTerm(500, 400);
		this.mainWindow.setBackgroundColor(Color.black);
		this.carsInfos = new CarInfo[100];
		this.mainWindow.setFontColor(Color.cyan);
		this.mainWindow.print("Company A Rental Manager:");
		this.mainWindow.print("\n");
		this.mainWindow.print("Chosen Car Info: ");
		this.mainWindow.setFontColor(Color.red);
		this.mainWindow.addTextField("", 159);
		this.mainWindow.print("\n");
		this.mainWindow.print("\n");
		this.mainWindow.addButton("Add Car", this, "addCar");
		this.mainWindow.addButton("Remove Car", this, "removeCar");
		this.mainWindow.addButton("Modify Car Info", this, "modifyCarInfo");
		this.mainWindow.addButton("?", this, "helpDialog");
		this.mainWindow.print("\n");
		this.mainWindow.addList(400, 300, this, "carInfoList");
		// in total 4 buttons to add, remove and modify
		// acar's info, as well as a help button when needed.
		// everytime a button is clicked it will go straight to the
		// associated method.
		// textfield displays chosen car on the list.
		// list contains all the cars currently existing, past, present and future.

	}

}
