
public class TypeDemo {

	public static void main(String[] args) {
		GTerm gt = new GTerm(600, 400);
		gt.setFontColor(255, 0, 0);
		gt.setFontSize(20);
		gt.print("My name is Varun Pereira. I like to play tennis. ");
		gt.println("I'm hoping we make a game.");
		gt.showHelp();

		int metres_ran=2147483647;
		byte pushups_in_one_go=Byte.MAX_VALUE;
		short days_old=Short.MAX_VALUE;
		boolean favourite_colour_is_blue=true;
		char favourite_letter='f';
		long number_people_in_existence=Long.MAX_VALUE;
        double driving_hours=53.5;
	    float hours_ran=2.4f;
	    
	    String message = "Metres_ran " + metres_ran + "\n";
	    message +="Pushups_in_one_go " + pushups_in_one_go + "\n";
	    message +="Days_old " + days_old + "\n";
	    message +="Favourite_colour_is_blue " + favourite_colour_is_blue + "\n";
	    message +="Favourite_letter " + favourite_letter + "\n";
	    message +="Number_people_in_existence " + number_people_in_existence + "\n";
	    message +="Driving_hours " + driving_hours + "\n";
	    message +="Hours_ran " + hours_ran + "\n";
	    
	    gt.println(message); 
	    
	    String chosenStringValue_1=gt.getInputString("Pick Increment, for 'Metres_ran'.");
	    int chosenIncrementValue_1 = Integer.parseInt(chosenStringValue_1);
	    metres_ran += chosenIncrementValue_1;
	    String chosenStringValue_2=gt.getInputString("Pick Increment, for 'Driving_hours'.");
	    double chosenIncrementValue_2 = Double.parseDouble(chosenStringValue_2);
	    driving_hours += chosenIncrementValue_2;
		metres_ran +=1;
		message = "Metres_ran " + metres_ran + "\n";
	    pushups_in_one_go +=1;
	    message +="Pushups_in_one_go " + pushups_in_one_go + "\n";
	    days_old +=1;
	    message +="Days_old " + days_old + "\n";
	    favourite_colour_is_blue=false;
	    message +="Favourite_colour_is_blue " + favourite_colour_is_blue + "\n";
	    favourite_letter='t';
	    message +="Favourite_letter " + favourite_letter + "\n";
	    number_people_in_existence +=1;
	    message +="Number_people_in_existence " + number_people_in_existence + "\n";
	    driving_hours +=1;
	    message +="Driving_hours " + driving_hours + "\n";
	    hours_ran +=1;
	    message +="Hours_ran " + hours_ran + "\n";
	   
	    gt.showMessageDialog(message);

	}

}