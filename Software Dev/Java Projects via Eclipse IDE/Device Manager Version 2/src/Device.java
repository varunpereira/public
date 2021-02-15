public class Device {
//Second class was named 'Device' because every devices data is stored here.
//So any changes made in main class, update this object or device class.
//Similar names could have included 'Appliance' or 'Object',
//but these are either inefficiently longer or too broad.

	private GTerm deviceWindow;
	private String deviceName;
	private String deviceState;
	private int devicePower;
	private String deviceImagePath;
	// Member Variables:
	// All declared outside a method as this way it makes each piece of data (eg
	// deviceWindow) an object in this class.
	// For consistency and simplicity, all member variables were named: with device
	// (the name of the
	// class it belonged to) and then what kind of information that data stored.
	// eg a device's window was named deviceWindow it belonged to the device object,
	// and the information it carried about that device object was its window.
	// Data type String was chosen for a device's name, state name and image file
	// name, because using char or numbers like int would have been restricting and
	// inappropriate.
	// Data type GTerm was chosen for a device's window,thats only known way to
	// create a window.
	// Data type int was chosen for devicePower because its more simple than
	// decimals like double.

	public Device(GTerm deviceWindow, String deviceName, String deviceState, int devicePower, String deviceImagePath) {
		this.deviceName = deviceName;
		this.deviceState = deviceState;
		this.devicePower = devicePower;
		this.deviceImagePath = deviceImagePath;
		this.deviceWindow = deviceWindow;
	}
	// Parameters were chosen, because all 5 variables, are need for the device's
	// data. This is especially true when a device is created.
	// No additional parameters were needs or else its inefficient code.
	// All the contents of the body are necessary for program to run properly.
	// This mean that all object variables were initialised, so when referring to
	// them in another class, only the variable names and data type needed to be the
	// same yet they would be an object.

	public void printDevice() {
		this.deviceWindow.print("Name: ");
		this.deviceWindow.print(this.deviceName);

		this.deviceWindow.print("\n");
		this.deviceWindow.print("Status: ");

		if (this.deviceState == "stateOff") {
			// condition and body chosen as it shows the action that will occur if a device
			// is off: it will print off.
			// then its switched to on, to keep the toggle button in a continuous cycle.
			this.deviceWindow.print("off");
			this.deviceState = "stateOn";
		} else if (this.deviceState == "stateOn") {
			// condition and body chosen as it shows the action that will occur if a device
			// is on: it will print on.
			// then its switched to off, to keep the toggle button in a continuous cycle.
			this.deviceWindow.print("on");
			this.deviceState = "stateOff";
		}

		this.deviceWindow.print("\n");
		this.deviceWindow.print("Power ");

		if (this.devicePower >= 0) {
			// Condition and body allows positive values of device power to be printed as
			// consumption.
			this.deviceWindow.print("CONSUMPTION (Watts): ");
			this.deviceWindow.print(String.valueOf(this.devicePower));
		} else if (this.devicePower < 0) {
			// Condition and body allows negative values of device power to be printed as
			// production.
			this.deviceWindow.print("PRODUCTION (Watts): ");
			this.deviceWindow.print(String.valueOf(this.devicePower * -1));
		}

		this.deviceWindow.print("\n");
		this.deviceWindow.addImageIcon(deviceImagePath);
	}
//Method is accurately named 'printDevice' as its function is to print the device once 
//it has been created and updated by the main class.
//Similar names such as 'applianceDisplayed' or 'applianceUpdated' are appropriate.
//However, these are not as accurate or descriptive.
//And device changes are applied in the main class, not in the device class.
//No parameters needed, as they would be called depending on the conditions set in the main class, not in this class.
//Return type void was selected because everything inside the method's body, was necessary to display any changes to the user.
// Other return types wouldn't have achieved this or been as efficient.
//Method was not split as its easier to have all information in one method.
//Method wasn't combined as it would caused dysfunction in program.

	public GTerm getDeviceWindow() {
		return this.deviceWindow;
	}
	// Method is accurately named 'getDeviceWindow' as its function is to retrieve
	// the device's window.
	// Similar names such as 'retrieveApplianceWindow' or 'applianceWindow' are
	// appropriate.
	// However, these are either too long and inefficient or too short and
	// undescriptive.
	// No parameters needed, as they would be called depending on the conditions set
	// in the main class, not in this class.
	// Return type GTerm was selected because the deviceWindow requires returning a
	// window.
	// Method was not split as there's only 1 action.
	// Method wasn't combined as variable returned needed to be an object.

	public String getDeviceName() {
		return this.deviceName;
	}
	// Method is accurately named 'getDeviceName' as its function is to retrieve the
	// device's name.
	// Similar names such as 'retrieveApplianceName' or 'applianceName' are
	// appropriate.
	// However, these are either too long and inefficient or too short and
	// undescriptive.
	// No parameters needed, as they would be called depending on the conditions set
	// in the main class, not in this class.
	// Return type String was selected because the deviceName requires returning a
	// string of characters.
	// Other return types like char wouldn't have achieved this or been as
	// efficient.
	// Method was not split as there's only 1 action.
	// Method wasn't combined as variable returned needed to be an object.

	public String getDeviceState() {
		return this.deviceState;
	}
	// Method is accurately named 'getDeviceState' as its function is to retrieve
	// the device's state.
	// Similar names such as 'retrieveApplianceState' or 'applianceState' are
	// appropriate.
	// However, these are either too long and inefficient or too short and
	// undescriptive.
	// No parameters needed, as they would be called depending on the conditions set
	// in the main class, not in this class.
	// Return type String was selected because the deviceState requires returning a
	// string of characters.
	// Other return types like char wouldn't have achieved this or been as
	// efficient.
	// Method was not split as there's only 1 action.
	// Method wasn't combined as variable returned needed to be an object.

	public int getDevicePower() {
		return this.devicePower;
	}
	// Method is accurately named 'getDevicePower' as its function is to retrieve
	// the device's power.
	// Similar names such as 'retrieveAppliancePower' or 'appliancePower' are
	// appropriate.
	// However, these are either too long and inefficient or too short and
	// undescriptive.
	// No parameters needed, as they would be called depending on the conditions set
	// in the main class, not in this class.
	// Return type int was selected because the devicePower requires returning a
	// number.
	// Other return types like double would make it unnecessarily more complex with
	// decimals, and long wasn't used as so many devices existing were highly
	// unlikely.
	// Method was not split as there's only 1 action.
	// Method wasn't combined as variable returned needed to be an object.

	public String getDeviceImagePath() {
		return this.deviceImagePath;
	}
	// Method is accurately named 'getDeviceImagePath' as its function is to
	// retrieve the device's image.
	// Similar names such as 'retrieveApplianceImage'' or 'applianceImage' are
	// appropriate.
	// However, these are either too long and inefficient or too short and
	// undescriptive.
	// No parameters needed, as they would be called depending on the conditions set
	// in the main class, not in this class.
	// Return type String was selected because the deviceImagePath requires
	// returning a string of characters.
	// Other return types like char wouldn't have achieved this or been as
	// efficient.
	// Method was not split as there's only 1 action.
	// Method wasn't combined as variable returned needed to be an object.

	public String toString() {
		return this.deviceName;
	}
	// Method is accurately named 'toString' as its function is to display the
	// device name
	// correctly, not the location where it is stored.
	// No parameters needed, device name always had to be correctly displayed.
}