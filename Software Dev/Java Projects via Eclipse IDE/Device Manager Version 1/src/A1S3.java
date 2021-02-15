import java.awt.Color;

public class A1S3 {

	public static void main(String[] args) {
		GTerm gt = new GTerm(1200, 600);
		gt.setBackgroundColor(Color.black);
		gt.setFontSize(20);

		Color modeHeading1Color = Color.magenta;
		Color modeHeading2Color = Color.orange;
		Color apNameColor = Color.cyan;
		Color apStateOffColor = Color.red;
		Color apStateOnColor = Color.green;
		// All 5 colors can be changed from one place.

		gt.setFontColor(modeHeading1Color);
		gt.println("Hello! I'm Fraizr, your home automation bot.");
		gt.println("\n");
		gt.println("Starting appliance mode...");
		// *Algorithm Step A*

		int numAps = Integer.parseInt(gt.getInputString("Enter number of appliances"));
		while (numAps < 0)
			numAps = Integer.parseInt(gt.getInputString("Error! Enter a positive number of appliances..."));
        //user can only have 0 or positive number of appliances.
		
		String[] apNames = new String[numAps];
		String[] apStateNames = new String[numAps];

		int i = 0;
		while (i < numAps) {
			apNames[i] = gt.getInputString("enter Appliance " + (i + 1) + " Name");
			apStateNames[i] = "off";
			i += 1;
		}
		// *Algorithm Step B*
		// Each index number (variable i) is assigned an Appliance Name and Appliance State Name
		// i + 1, as index starts at 0, which might be confusing for user.

		gt.setFontColor(modeHeading2Color);
		gt.println("You have inputed " + numAps + " appliances.");
		gt.println("\n");

		gt.setFontColor(modeHeading1Color);
		gt.println("Starting listening mode...");
		gt.setFontColor(modeHeading2Color);
		gt.println("Available commands: \"turn on <appliance>\" or \"turn off <appliance>\"...");

		String message1Name = "Available appliances: ";

		gt.setFontColor(modeHeading2Color);
		gt.print(message1Name);
		// *Algorithm Step C*

		i = 0;
		while (i < numAps) {
			gt.setFontColor(apNameColor);
			gt.print(apNames[i]);
			gt.setFontColor(modeHeading2Color);
			gt.print("(");
			gt.setFontColor(apStateOffColor);
			gt.print(apStateNames[i]);
			gt.setFontColor(modeHeading2Color);
			gt.print("), ");
			i += 1;
		}

		String choice = gt.getInputString("What would you like to do?");
		// *Algorithm Step C.1*

		i = 0;
		// i is set to 0, to allow all inputed variables to be included.
		// everything contained in 1 while loop(unless user chooses cancel) to allow
		// continuous repetition of inputs/outputs.
		// for loops were discouraged, so i didn't use it, although it could make things
		// easier.
		// while loops supersede if statements, as we do things without constraining
		// limits.

		while (choice != null && i < numAps) {
			i = 0;
			// start checking, from 1st item appliance name.
			while (i < numAps && !choice.contains(apNames[i]))
				i += 1;
			// Program will go through in order from 0 to 1 to etc to number of appliances
			// inputed.
			// e.g. if user enters 5 appliances, order is: 0,1,2,3,4.

			// As we are not allowed an array for color, we need to split it into if and
			// else if statements.
			// This done twice. Using an array for colors is much easier.

			if (choice.contains("turn on ") && (i < numAps) && (choice != null)) {
				gt.println("\n");
				gt.setFontColor(apNameColor);
				gt.print(apNames[i]);
				gt.setFontColor(modeHeading2Color);
				gt.print(" turned ");
				gt.setFontColor(apStateOnColor);
				apStateNames[i] = "on";
				gt.print(apStateNames[i]);
				i += 1;
			} else if (choice.contains("turn off ") && (i < numAps) && (choice != null)) {
				gt.println("\n");
				gt.setFontColor(apNameColor);
				gt.print(apNames[i]);
				gt.setFontColor(modeHeading2Color);
				gt.print(" turned ");
				gt.setFontColor(apStateOffColor);
				apStateNames[i] = "off";
				gt.print(apStateNames[i]);
				i += 1;
				// *Algorithm Step C.2.1*
				// If input is within the list of appliance names,
				// AND input includes turn on/off,
				// then that particular appliance name and state will be shown.
				// I chose .contains and not .equalsIgnoreCase because if the user types an
				// extra space/character or similar, they
				// shouldn't be penalised. However as instructions specify exactly what to do
				// (no caps for turn on/off),
				// not getting caps right is okay to retype input.

			} else if (choice.contains("turn off ") || choice.contains("turn on ")) {
				gt.println("\n\n");
				gt.setFontColor(modeHeading2Color);
				gt.print("I can only control:... ");
				// *Algorithm Step C.2.2*
				// If user inputs 'turn on/off' AND appliance name is not on list.
				// then most recent appliance name and state list will be shown.
				// As i < numAps,, and increment of +1 eventually if program will reach numAps,
				// if appliance is not on the list.

			} else {
				gt.println("\n\n");
				gt.setFontColor(modeHeading2Color);
				gt.print("please say turn on or off only");
			}
			// *Algorithm Step C.2.3*
			// If user input, does not contain some sort of turn on/off AND does not include
			// appliances from the list, their asked to retype input.
			// as user can type anything for this else statement, its important for this
			// whole section to do a: while, if,else if, else if, else statements structure.

			gt.println("\n\n");
			gt.setFontColor(modeHeading2Color);
			gt.print(message1Name);

			i = 0;
			// Reset i to 0, and i<numAPs, allow all appliances to be included.
			// only appliances in list are ever displayed to user, otherwise error message
			// shown.
			while (i < numAps) {
				if (apStateNames[i].contains("off")) {
					gt.setFontColor(apNameColor);
					gt.print(apNames[i]);
					gt.setFontColor(modeHeading2Color);
					gt.print("(");
					gt.setFontColor(apStateOffColor);
					gt.print(apStateNames[i]);
					gt.setFontColor(modeHeading2Color);
					gt.print("), ");
					i += 1;
				} else if (apStateNames[i].contains("on")) {
					gt.setFontColor(apNameColor);
					gt.print(apNames[i]);
					gt.setFontColor(modeHeading2Color);
					gt.print("(");
					gt.setFontColor(apStateOnColor);
					gt.print(apStateNames[i]);
					gt.setFontColor(modeHeading2Color);
					gt.print("), ");
					i += 1;
				}
			}
			// *Displays all Appliance Names and Updated States*
			// *Done at end as before, apStateNames[i] and apNames[i] for 1 appliance is
			// changed first, only then can the entire appliance names and states list, be
			// shown to user.
			// this allows for appliances list memory to be accumulated.

			i = 0;
			if (i < numAps)
				choice = gt.getInputString("What would you like to do?");
			// This allows the cycle to repeat but only when all appliances on list are
			// included.
		}
		gt.println("\n\n");
		gt.setFontColor(modeHeading2Color);
		gt.print("You may now close the window. See you next time!");
		// *Algorithm Step D*
		// If user chooses to click cancel, then Goodbye Message displayed.
	}
}
