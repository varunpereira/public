import java.io.File;

public class OffGridManager {
//Main class was named 'OffGridManager' as all program's main repeated functions (buttons etc)
//were located on one main window. Accurately describes that it manages the system 
//and that it applies any changes to the device in this class.
//Similar names could have included 'MainWindow' or 'ElectricalDeviceManager',
//however those either do not describe what the program is, or is a bit broad and not specific to this program.

	private GTerm mainWindow;
	private Device[] devices;
	private int currentTotalNumDevices;
	private int currentTotalPowerExcess;
	// Member Variables:
	// All declared outside a method as this way it makes each piece of data (eg
	// devices array) an object in this class.

	// name 'devices' was used for the array of devices because thats the
	// information it stored: all the data about each and every device created.
	// Similar names like 'appliances' were appropriate but unnecessarily longer.

	// name 'currentTotalNumDevices' and 'currentTotalPowerExcess' was used as it
	// accurately describes its function: to keep track of how many devices
	// currently existed, and how much power was currently in access, respectively.

	// name 'mainWindow' was used because it is first and main window in program.
	// 'w1' wasn't as accurate or descriptive.

	// Data type int was chosen for current num devices and power excess because
	// using string is dysfunctional and decimals like double is unnecessarily
	// complicated.

	public OffGridManager() {
		// No parameters were chosen, because in this constructor,
		// all functions had necessary actions, that made program run properly.
		// Using parameters is unnecessary and inefficient use of code.
		// All the contents of the body are necessary for program to run properly.
		// This includes all 3 buttons, 1 text field and 1 list.
		// Actions would only be performed once there were interacted with.

		this.mainWindow = new GTerm(500, 400);
		this.devices = new Device[100];
		this.currentTotalNumDevices = 0;
		this.currentTotalPowerExcess = 0;

		this.mainWindow.print("Current Power Postion (Watts):");
		this.mainWindow.addTextField("0", 159);
		this.mainWindow.print("\n");
		this.mainWindow.setTextInEntry(0, String.valueOf(this.currentTotalPowerExcess));
		this.mainWindow.addButton("Add Device", this, "addDevice");
		this.mainWindow.addButton("Remove Device", this, "removeDevice");
		this.mainWindow.addButton("Toggle (On/Off)", this, "changeDeviceStatus");
		this.mainWindow.print("\n");
		this.mainWindow.addList(300, 300, this, "deviceList");
	}

	public void deviceList() {
		Device selectedDevice = (Device) this.mainWindow.getSelectedElementFromList(0);
		if (selectedDevice != null)
			// Condition and body were chosen as it prevents an error when a device is
			// slected from list.
			;
	}
	// Method is accurately named 'deviceList' because it tells you what happens
	// when you click on adevice in list.
	// Similar names such as 'chosenDevice' are appropriate are confusing wiht other
	// variables and do not refer to list.
	// No parameters needed, as we always want the user to be able to click on an
	// item on list.
	// Only the return type void was selected because everything inside the method's
	// body, was necessary to display any changes to the user when called.
	// Other return types wouldn't have achieved this or been as efficient.
	// Method wasn't split as it was easier to have everything in 1 method.
	// Method wasn't merged as it is distinct to other methods and only started when
	// item is clicked on in the list.

	public void addDevice() {
		// Method is accurately named 'addDevice' as when user clicks 'Add Device'
		// button, its function is to create a new device.
		// Similar names such as 'createNewDevice' or 'newApplianceCreated' are
		// appropriate.
		// However, these are longer and inefficient.
		// No parameters needed, as they would be called depending on the condition
		// which was pressing its associated button on the main window 'mainWindow'.
		// Only the return type void was selected because everything inside the method's
		// body, was necessary to display any changes to the user when called.
		// Other return types wouldn't have achieved this or been as efficient.
		// Method wasn't split as it was easier to have everything in 1 method.
		// Method wasn't merged as it is distinct to other methods and only started by a
		// button.

		String deviceName;
		String deviceState;
		int devicePower;
		String deviceImagePath;
		GTerm deviceWindow;
		// Method Variables:
		// All 5 method variables declared inside method because they are only needed if
		// the addDevice method is used.
		// For consistency and simplicity, all method variables were named: with device
		// (the name of the class it belonged to) and then what kind of information that
		// data stored.
		// eg a device's window was named deviceWindow it belonged to the device object,
		// and the information it carried about that device object was its window.
		// Data type String was chosen for a device's name, state name and image file
		// name, because using char or numbers like int would have been restricting and
		// inappropriate.
		// Data type GTerm was chosen for a device's window,thats only known way to
		// create a window.
		// Data type int was chosen for devicePower because its more simple than
		// decimals like double.

		if (this.currentTotalNumDevices >= this.devices.length)
		// Condition chosen to fix an issue of inadequate number of arrays available.
		// Solution is in the body: go to expandDeviceArray to double the current
		// available slots in array.
		{
			expandDeviceArray();
		}

		deviceWindow = new GTerm(500, 400);

		this.mainWindow.showMessageDialog("Select Image");
		deviceImagePath = this.mainWindow.getFilePath();
		File file = new File(deviceImagePath);
		String[] filePiece = file.getName().toString().split("\\.");
		deviceName = filePiece[0];

		deviceState = "stateOff";

		devicePower = Integer.parseInt(this.mainWindow.getInputString("Enter power usage for " + deviceName));

		Device deviceToList = new Device(deviceWindow, deviceName, deviceState, devicePower, deviceImagePath);
		this.devices[this.currentTotalNumDevices] = deviceToList;
		this.mainWindow.addElementToList(0, deviceToList);
		this.currentTotalNumDevices += 1;

		deviceToList.printDevice();
	}

	public void removeDevice() {
		// Method is accurately named 'removeDevice' as when user clicks 'Remove Device'
		// button, its function is to remove an existing device.
		// Similar names such as 'deleteExistingDevice' or 'applianceDelete' are
		// appropriate.
		// However, there are longer and inefficient.
		// No parameters needed, as they would be called depending on the condition
		// which was pressing its associated button on the main window 'mainWindow'.
		// Only the return type void was selected because everything inside the method's
		// body,
		// was necessary to display any changes to the user when called.
		// Other return types wouldn't have achieved this or been as efficient.
		// Method wasn't split as it was easier to have everything in 1 method.
		// Method wasn't merged as it is distinct to other methods and only started by a
		// button.

		String deviceState;
		int devicePower;
		GTerm deviceWindow;
		// Method Variables:
		// All 2 method variables declared inside method because they are only needed if
		// the removeDevice method is used.
		// For consistency and simplicity, all method variables were named: with device
		// (the name of the class it belonged to) and then what kind of information that
		// data stored.
		// eg a device's state was named deviceState as it belonged to the device
		// object,
		// and the information it carried about that device object was its state.
		// Data type String was chosen for a device's state name because using char or
		// numbers like int would have been restricting and
		// inappropriate.
		// Data type int was chosen for devicePower because its more simple than
		// decimals like double.

		Device selectedDevice = (Device) this.mainWindow.getSelectedElementFromList(0);

		if (selectedDevice == null) {
			// Condition and body chosen as error message will pop up if an item is NOT
			// chosen.
			this.mainWindow.showMessageDialog("Please select a device.");
		}

		else if (selectedDevice != null) {
			// Condition and body chosen as it shows what will happen if a device IS chosen.

			int i = 0;
			while (i < this.currentTotalNumDevices && !this.devices[i].equals(selectedDevice)) {
				// Condition and body was chosen to allow the program to run through all
				// possible items in list from start to finish, to see if it matches up with
				// selected item.
				i += 1;
			}

			deviceState = selectedDevice.getDeviceState();
			devicePower = selectedDevice.getDevicePower();
			deviceWindow = selectedDevice.getDeviceWindow();

			if (deviceState == "stateOn" && i < this.currentTotalNumDevices) {
				// Condition and body was chosen as any device that is currently off can be
				// removed, with no consequences.
				this.mainWindow.removeElementFromList(0, i);
				deviceWindow.close();
				this.currentTotalNumDevices -= 1;

				while (i < this.currentTotalNumDevices) {
					this.devices[i + 1] = this.devices[i];
					i += 1;
				}
				// while loop shifts the position of all existing values down by 1 from the
				// position of that selected value.
			}

			else if (deviceState == "stateOff" && devicePower >= 0) {
				// Condition and body was chosen as any device that is positive and on will need
				// to be turned off.
				this.mainWindow.showMessageDialog("Need to turn device OFF.");
			}

			else if (deviceState == "stateOff" && devicePower <= 0
					&& this.currentTotalPowerExcess - devicePower * -1 >= 0) {
				// Condition and body was chosen as any device that is negative and on and has a
				// positve power excess will need to be turned off.
				this.mainWindow.showMessageDialog("Need to turn device OFF.");
			}

			else if (deviceState == "stateOff" && devicePower <= 0
					&& this.currentTotalPowerExcess - devicePower * -1 < 0) {
				// Condition and body was chosen as any device that is negative and on and has a
				// negative power excess will need to be turned off.
				this.mainWindow.showMessageDialog("NOT enough power to turn OFF.");
				this.mainWindow.showMessageDialog("Unable to remove device");

			}
		}
	}

	public void changeDeviceStatus() {
		// Method is accurately named 'changeDeviceStatus' as when user clicks 'Toggle
		// On/Off' button, its function is to change the selected device's status to the
		// other value.
		// Similar names such as 'changeStatus' or 'statusChange' are appropriate.
		// However, these are not as descriptive or consistent.
		// No parameters needed, as they would be called depending on the condition
		// which was pressing its associated button on the main window 'mainWindow'.
		// Only the return type void was selected because everything inside the method's
		// body,
		// was necessary to display any changes to the user when called.
		// Other return types wouldn't have achieved this or been as efficient.
		// Method wasn't split as it was easier to have everything in 1 method.
		// Method wasn't merged as it is distinct to other methods and only started by a
		// button.

		String deviceName;
		String deviceState;
		int devicePower;
		String deviceImagePath;
		GTerm deviceWindow;
		// Method Variables:
		// All 5 method variables declared inside method because they are only needed if
		// the changeDeviceStatus method is used.
		// For consistency and simplicity, all method variables were named: with device
		// (the name of the class it belonged to) and then what kind of information that
		// data stored.
		// eg a device's window was named deviceWindow it belonged to the device object,
		// and the information it carried about that device object was its window.
		// Data type String was chosen for a device's name, state name and image file
		// name, because using char or numbers like int would have been restricting and
		// inappropriate.
		// Data type GTerm was chosen for a device's window,thats only known way to
		// create a window.
		// Data type int was chosen for devicePower because its more simple than
		// decimals like double.

		Device selectedDevice = (Device) this.mainWindow.getSelectedElementFromList(0);

		if (selectedDevice == null) {
			// Condition and body chosen as error message will pop up if an item is NOT
			// chosen.
			this.mainWindow.showMessageDialog("Please select a device.");
		}

		else if (selectedDevice != null) {
			// Condition and body chosen as it shows what will happen if a device IS chosen.
			int i = 0;
			while (i < this.currentTotalNumDevices && !this.devices[i].equals(selectedDevice)) {
				// Condition and body chosen as if chosen device does not match with counter,
				// then it keeps adding to from starting position till it does.
				i += 1;
			}

			deviceName = selectedDevice.getDeviceName();
			deviceState = selectedDevice.getDeviceState();
			devicePower = selectedDevice.getDevicePower();
			deviceImagePath = selectedDevice.getDeviceImagePath();
			deviceWindow = selectedDevice.getDeviceWindow();

			if (deviceState == "stateOn" && devicePower <= 0) {
				// Condition and body chosen as device is off and negative, you print it as on,
				// then change status to on to keep in loop.
				deviceWindow.clear();
				this.mainWindow.setTextInEntry(0, String.valueOf(this.currentTotalPowerExcess += -1 * devicePower));
				deviceState = "stateOff";
				selectedDevice.printDevice();
			}

			else if (deviceState == "stateOff" && devicePower <= 0
					&& this.currentTotalPowerExcess - devicePower * -1 >= 0) {
				// Condition and body chosen as device is on and positive, you print it as off,
				// then change status to on to kepp in loop.
				deviceWindow.clear();
				this.mainWindow.setTextInEntry(0, String.valueOf(this.currentTotalPowerExcess -= -1 * devicePower));
				deviceState = "stateOn";
				selectedDevice.printDevice();
			}

			else if (deviceState == "stateOff" && devicePower <= 0
					&& this.currentTotalPowerExcess - devicePower * -1 < 0) {
				// Condition and body chosen as device is on and negative,
				this.mainWindow.showMessageDialog("NOT enough power to turn OFF.");
			}

			else if (deviceState == "stateOff" && devicePower >= 0 && devicePower <= this.currentTotalPowerExcess) {
				// Condition and body chosen as device is on and positive and differences in
				// power levels are acceptable.
				deviceWindow.clear();
				this.mainWindow.setTextInEntry(0, String.valueOf(this.currentTotalPowerExcess += devicePower));
				deviceState = "stateOn";
				selectedDevice.printDevice();
			}

			else if (deviceState == "stateOn" && devicePower >= 0 && devicePower <= this.currentTotalPowerExcess) {
				// Condition and body chosen as device is off and positive and differences in
				// power levels are acceptable.
				deviceWindow.clear();
				this.mainWindow.setTextInEntry(0, String.valueOf(this.currentTotalPowerExcess -= devicePower));
				deviceState = "stateOff";
				selectedDevice.printDevice();
			}

			else if (deviceState == "stateOff" && devicePower >= 0 && devicePower >= this.currentTotalPowerExcess) {
				// Condition and body chosen as device is on and positive and differences in
				// power levels are acceptable.
				deviceWindow.clear();
				this.mainWindow.setTextInEntry(0, String.valueOf(this.currentTotalPowerExcess += devicePower));
				deviceState = "stateOn";
				selectedDevice.printDevice();
			}

			else if (deviceState == "stateOn" && devicePower >= 0 && devicePower >= this.currentTotalPowerExcess) {
				// Condition and body chosen as device is on and positive and differences in
				// power levels are unacceptable (power position goes below 0).
				this.mainWindow.showMessageDialog("Sorry, Not Enough Power to turn on " + deviceName);

			}
		}
	}

	public void expandDeviceArray() {
		// Method is accurately named 'expandDeviceArray' as whenever, there isn't
		// enough slots available in the array,
		// the function is to double the available slots, and then a new device can be
		// created.
		// Similar names such as 'expandArray' or 'expandDevice' are appropriate.
		// However, there are too short and not descriptive enough.
		// No parameters needed, as they would be called depending on the condition
		// which was determined by 'addDevice' method.
		// Only the return type void was selected because everything inside the method's
		// body,
		// was necessary to display any changes to the user when called.
		// Other return types wouldn't have achieved this or been as efficient.
		// Method wasn't split as it was easier to have everything in 1 method.
		// Method wasn't merged as it wasn't always required by the 'addDevice' method.

		Device[] moreDevices = new Device[this.devices.length * 2];
		int i = 0;
		while (i < this.currentTotalNumDevices) {
			// Condition and body chosen as the number of devices needs to be doubled
			// whenever scarce number of slots available in device array.
			moreDevices[i] = this.devices[i];
			i += 1;
		}
		this.devices = moreDevices;
	}

	public static void main(String[] args) {
		OffGridManager start = new OffGridManager();
	}
//Main method located at bottom as it can't be used for object oriented programming.
//Its the first method to run, so the Constructor is declared here, 
//and is accordingly named 'start'.

}