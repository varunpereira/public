import javax.swing.*;

public class q3 {

	
	public static void main(String[] args) {
		GTerm gt;
		gt = new GTerm(670, 600);
		int a = Integer.parseInt(JOptionPane.showInputDialog("Enter a number"));
		String message = "";
		int i = 0;
		while (i < a) {
			int j = 0;
			while (j < a) {
				message += j;
				j += 1;
			}
			i += 1;
		}
		gt.print(message);
	}
}