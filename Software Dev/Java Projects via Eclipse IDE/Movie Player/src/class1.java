//my program is like netflix
//single characters don't work some reason
//class1 and class2 makes it easy for developer to distinguish between the two
// i only had 2 classes because it represents cinema like apps more, 
//a window for movies you are watching, and another directing what will happen next (goodbye message)



import java.awt.Color;

public class class1 {
	private GTerm gt;
	private String[] movie;
	private String[] movieState;
	private String choice;
	private int i;
	private int numMovies;
	private String name;
	
	private Color playColor;
	private Color pauseColor;
	private Color movieColor;
	private Color heading1Color;
	private Color heading2Color;

	private class2 class2;
	
	//variable names accurately represent what they do
	//these data types are the only ones that can get job done efficiently
	//having it declared over here a start allows me to create an object for it
	//arrays data types and named so, because they 
	//accurately represent what they are, and what values they can hold.

	public class1() {
//no parameters as all these steps are necessary for program to run
//only the introduction is kept here, the rest is linked to other methods, for efficiency

		

		this.gt = new GTerm(800, 400);
		this.gt.setBackgroundColor(Color.black);
		this.gt.setFontSize(40);
		
		this.playColor = Color.green;
		this.pauseColor = Color.red;
		this.movieColor = Color.magenta;
		this.heading1Color = Color.cyan;
		this.heading2Color = Color.orange;
		
				
		this.gt.setFontColor(this.heading1Color);
		this.gt.println("WELCOME (^_^)");
		this.gt.println("\n\n");
		this.gt.setFontColor(this.heading2Color);
		this.gt.addButton("CLICK to Log In", this, "logIn");
		this.gt.setFontSize(16);
	}

	public void logIn() {
		this.gt.print("\n");
		this.name = this.gt.getInputString("What's your name please?");
		this.gt.clear();
		this.gt.setFontColor(this.heading1Color);
		this.gt.println("Hello " + this.name + "! Welcome to YOUR Home Cinema! (^_^)");
		this.gt.println("\n");

		this.numMovies = Integer.parseInt(this.gt.getInputString("How many movies you watching, " + this.name + "?"));
		while (this.numMovies < 0)
			this.numMovies = Integer
					.parseInt(this.gt.getInputString("Please ENTER a POSITIVE number of movies to watch"));

		this.gt.println(this.name + ", Enjoy Watching " + this.numMovies + " Movies! (^_^)");
		this.gt.println("\n");

		movie = new String[this.numMovies];
		movieState = new String[this.numMovies];
		//arrays are positioned here as their values depend on how many movies user enters.

		initial();

		current();

	}

	public void initial() {
		this.i = 0;
		while (this.i < this.numMovies) {
			movie[this.i] = this.gt.getInputString("Please ENTER Movie " + (this.i + 1) + " Name");
			movieState[this.i] = "paused";

			this.i += 1;
		}
		this.gt.println(this.name + ", you can watch:");
		this.i = 0;
		while (this.i < this.numMovies) {
			this.gt.setFontColor(this.movieColor);
			this.gt.print(movie[this.i]);
			this.gt.setFontColor(this.heading2Color);
			this.gt.print("(");
			this.gt.setFontColor(this.pauseColor);
			this.gt.print("Not Started");
			this.gt.setFontColor(this.heading2Color);
			this.gt.print("), ");
			this.i += 1;
		}

	}

	public void current() {
		this.i = 0;
		this.choice = this.gt.getInputString("ENTER: 'pause' or 'play' + 'Movie Name' ");

		while (this.choice != null && this.i < this.numMovies) {
			this.i = 0;
			while (this.i < this.numMovies && !this.choice.contains(movie[this.i]))
				this.i += 1;
			if (this.choice.contains("play") && (this.i < this.numMovies) && (this.choice != null)) {
				this.gt.println("\n");
				this.gt.setFontColor(this.movieColor);
				this.gt.print(movie[this.i]);
				this.gt.setFontColor(this.heading2Color);
				this.gt.print(" is ");
				this.gt.setFontColor(this.playColor);
				movieState[this.i] = "playing";
				this.gt.print(movieState[this.i]);
				this.i += 1;
			} else if (this.choice.contains("pause") && (this.i < this.numMovies) && (this.choice != null)) {
				this.gt.println("\n");
				this.gt.setFontColor(this.movieColor);
				this.gt.print(movie[this.i]);
				this.gt.setFontColor(this.heading2Color);
				this.gt.print(" is ");
				this.gt.setFontColor(this.pauseColor);
				movieState[this.i] = "paused";
				this.gt.print(movieState[this.i]);
				this.i += 1;
			} else {
				this.gt.setFontColor(this.heading2Color);
				this.gt.println("\n\n");
				this.gt.println("ONLY ENTER: 'play' or 'pause' + 'Movie Name'");
			}

			allMovies();

			this.i = 0;
			if (i < numMovies)
				this.choice = this.gt.getInputString("Enter: 'play' or 'pause' + 'Movie Name' ");

		}
		this.gt.clear();
		this.gt.close();
		this.class2 = new class2();

	}

	public void allMovies() {
		this.gt.println("\n\n");
		this.gt.setFontColor(this.heading2Color);
		this.gt.print("Movie Status:");
		this.i = 0;
		while (this.i < this.numMovies) {
			if (movieState[this.i].contains("play")) {
				this.gt.setFontColor(this.movieColor);
				this.gt.print(movie[this.i]);
				this.gt.setFontColor(this.heading2Color);
				this.gt.print("(");
				this.gt.setFontColor(this.playColor);
				this.gt.print(movieState[this.i]);
				this.gt.setFontColor(this.heading2Color);
				this.gt.print("), ");
				this.i += 1;
			} else if (movieState[this.i].contains("pause")) {
				this.gt.setFontColor(this.movieColor);
				this.gt.print(movie[this.i]);
				this.gt.setFontColor(this.heading2Color);
				this.gt.print("(");
				this.gt.setFontColor(this.pauseColor);
				this.gt.print(movieState[this.i]);
				this.gt.setFontColor(this.heading2Color);
				this.gt.print("), ");
				this.i += 1;
			}
		}
	}
//no parameters need for loops, this step is essential
//loops body can not be anywhere else, as program will not work properly, and wont be repeated infinitely.
//all if statements incorporate all options user can enter, if they don't answer
//what's required, they repeat question like normal.
//if statements body can not be anywhere else, because then program will not
//work as desired, and be chaotic.
//member variable names accurately represent what they do
//these data types are the only ones that can get job done efficiently
	
	
	
	public static void main(String[] args) {
		class1 movie1 = new class1();
	}
}

//all methods are named so, as they accurately represent what action/role they take.
//no parameters are needed as they all repeated and necessary.
//void is necessary for smooth cooperation with constructor method.
//there are only 3 sections to my program. besides constructor and main,
//adding more will be inefficient, losing some will make things more difficult.



