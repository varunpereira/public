public class Calculation {
	private GTerm gt;

	private double operand1;
	private String operator;
	private double operand2;

	public Calculation(double operand1, String operator, double operand2) {

		this.operand1 = operand1;
		this.operator = operator;
		this.operand2 = operand2;
	}

	public double getResult() {
		double result = 0;
		if (this.operator.contentEquals("+"))
			result = this.operand1 + this.operand2;
		else if (this.operator.contentEquals("-"))
			result = this.operand1 - this.operand2;
		else if (this.operator.contentEquals("/"))
			result = this.operand1 / this.operand2;
		else if (this.operator.contentEquals("*"))
			result = this.operand1 * this.operand2;

		return result;
	}

}
