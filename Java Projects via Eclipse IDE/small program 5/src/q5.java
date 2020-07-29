import javax.swing.JOptionPane;

public class q5 {
	private String message;
	private int num;

	public q5() {
		this.message = "";
		this.num = 0;
		methodAB();
		JOptionPane.showMessageDialog(null, this.message);
	}

	public void methodAB() {
		if (this.num / 2 * 2 == this.num) {
			message += "A";
		} else
			message += "B";
		this.num += 1;
		if (this.num < 6)
			methodAB();
	}

	public static void main(String[] args) {
		q5 q5 = new q5();
	}
}