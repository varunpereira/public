import javax.swing.*;

public class Test {

	public static void main(String[] args) {
		int x = Integer.parseInt(JOptionPane.showInputDialog("Enter how many value"));
		int[] dataA = new int[x];
		int i = 0;
		while (i < x) {
			dataA[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter the values"));
			i += 1;
		}
		int[] dataB = new int[x * 2];
		dataB = dataA;

		i = 0;
		while (i < x) {
			if (dataB[i] < 0)
				dataB[i] = -1 * dataB[i];
			i += 1;
		} //nehative values, become postive

		int count = 0;
		i = 0;
		while (i < x) {
			if (dataA[i] == dataB[i])
				count += 1;
			i += 1;
		}
		JOptionPane.showMessageDialog(null, "Count is " + count);
	}

}
