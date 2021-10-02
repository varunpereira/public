import javax.swing.JOptionPane;

public class q4 {

	public static void main(String[] args) {
		GTerm gt;
		gt = new GTerm(670, 600);
		int x = Integer.parseInt(JOptionPane.showInputDialog("Enter how many value"));
		int[] dataA = new int[x];
		int[] dataB = dataA;
		int[] dataC = dataB;
		int total = 0;
		int i = 0;
		while (i < x) {
			dataB = new int[x]; //take ths out n it owrks - resets all values to default - now every value IS is zero.
			dataB[i] = Integer.parseInt(JOptionPane.showInputDialog("enter value for " + i));
			total += dataC[i];
			i += 1;
		}
		JOptionPane.showMessageDialog(null, "Total is " + total);
	}
}
