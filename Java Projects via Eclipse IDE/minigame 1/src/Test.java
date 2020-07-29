import javax.swing.*;

public class Test {

	public static void main(String[] args) {
		int a = Integer.parseInt(JOptionPane.showInputDialog("Enter a number"));
		String message = "";
		int b = 0;
		while (b < a) {
			int c = 0;
			message += b;
			while (c < b) {
				message += c;
				c += 1;
			}
			message += "\n";
			b += 1;

		}
		JOptionPane.showMessageDialog(null, message);

	}
}


