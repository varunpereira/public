
public class Tutorial01 {

	public static void main(String[] args) {
		GTerm gt = new GTerm(600, 400);
		gt.setFontColor(255, 0, 0);
		gt.setFontSize(20);
		gt.print("My name is Varun Pereira. I like to play tennis. ");
		gt.println("I'm hoping we make a game.");
		gt.showHelp();
	}

}