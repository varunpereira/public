import java.awt.Color;

public class class2 {
	private String help;
	private GTerm gt;

	public class2() {
		this.gt = new GTerm(800, 400);
		this.gt.setBackgroundColor(Color.black);
		this.gt.setFontSize(30);
		this.gt.setFontColor(Color.cyan);
		this.help = "Thank YOU For Watching! Cya Soon! (^_^)";
		this.gt.println(this.help);

	}

	public String getHelp() {
		return this.help;
	}

}
