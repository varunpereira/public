public class MainCalculatorApp {
	private GTerm gt;
	private Calculation[] calculations;
	private int currentNumCalculations;

	public MainCalculatorApp() {
		currentNumCalculations = 0;
		this.gt = new GTerm(800, 400);
		this.gt.setFontSize(16);

		this.gt.addTextField("", 300);
		this.gt.print("\n");

		this.gt.addButton("Add calculation", this, "addCalculation");
		this.gt.print("\n");

		this.gt.addList(300, 300, this, "chooseCalculation");
	}

	
	public void addCalculation() {
		this.currentNumCalculations += 1;
		this.calculations = new Calculation[this.currentNumCalculations];
		this.gt.addElementToList(0, this.gt.getTextFromEntry(0));
	}

	public void chooseCalculation() {
	
		int i = 0;
		while (i < this.calculations.length) {
			String chosenCalculation = (String) this.gt.getSelectedElementFromList(0);
			String[] eqPieces = chosenCalculation.split(" ");
			double operand1 = Double.parseDouble(eqPieces[0]); 
			String operator = eqPieces[1];
			double operand2 = Double.parseDouble(eqPieces[2]);
			this.calculations[i] = new Calculation(operand1,operator, operand2);
			this.gt.setTextInEntry(0, String.valueOf (this.calculations[i].getResult()));
			i += 1;
		}		

	}

	public static void main(String[] args) {
		MainCalculatorApp calc1 = new MainCalculatorApp();

	}
}
