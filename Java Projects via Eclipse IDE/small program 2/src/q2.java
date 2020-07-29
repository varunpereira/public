import javax.swing.*;

public class q2 {
	public static void main(String[] args) {
		int width = Integer.parseInt(JOptionPane.showInputDialog("Enter width"));
		int height = Integer.parseInt(JOptionPane.showInputDialog("Enter height"));
		String message = "";
		if (width == height)
			message = "square";

		else if (width == height == false)
		message = "rectangle";
		JOptionPane.showMessageDialog(null, message);
	}
}