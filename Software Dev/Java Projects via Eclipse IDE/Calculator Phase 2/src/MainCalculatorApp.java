public class MainCalculatorApp {
	private GTerm gt;
	private Calculation[] calculations;
	private int currentNumCalculations;

	public MainCalculatorApp() {

		this.gt = new GTerm(310, 400);
		this.calculations = new Calculation[2];
		this.currentNumCalculations = 0;

		this.gt.addTextField("", 305);
		this.gt.println("");

		this.gt.addButton("Add calculation", this, "addCalculation");
		this.gt.addButton("Remove calculation", this, "removeCalculation");
		this.gt.println("\n");
		this.gt.addList(305, 340, this, "loadCalculation");
	}

	public void loadCalculation() {
		Calculation selectedCalculation = (Calculation) this.gt.getSelectedElementFromList(0);
		if (selectedCalculation != null)
			this.gt.setTextInEntry(0, "" + selectedCalculation.getResult());
	}
	//sends selectedcalculation to calc class and then to a method called getResult

	public void addCalculation() {
		String expressionStr = this.gt.getTextFromEntry(0);
		String[] expressionElements = expressionStr.split(" ");
		double operand1 = Double.parseDouble(expressionElements[0]);
		double operand2 = Double.parseDouble(expressionElements[2]);

		if (this.currentNumCalculations >= this.calculations.length)
			expandArray();
		//if you run out of values in array - so i can do + 1

		Calculation calculationToAdd = new Calculation(operand1, expressionElements[1], operand2);
		this.calculations[this.currentNumCalculations] = calculationToAdd;
		this.gt.addElementToList(0, calculationToAdd);
		this.currentNumCalculations += 1;
	}

	public void expandArray() {
		Calculation[] biggerCalculations = new Calculation[this.calculations.length * 2];
		int i = 0;
		while (i < this.currentNumCalculations) {
			biggerCalculations[i] = this.calculations[i];
			i += 1;
		}
		this.calculations = biggerCalculations;
	}

	public void removeCalculation() {
		Calculation selectedCalculation = (Calculation) this.gt.getSelectedElementFromList(0);
		int i = 0;
		while (i < this.currentNumCalculations && !this.calculations[i].equals(selectedCalculation))
			i += 1;
		
		//does not work for duplicate names
		// 
		
		//equals classname.methodname (devicename)

		
		if (i < this.currentNumCalculations) {
			this.gt.removeElementFromList(0, i);
			this.currentNumCalculations -= 1;

		}

		while (i < this.currentNumCalculations) {
			this.calculations[i] = this.calculations[i + 1];
			i += 1;

		}
		
	}

	public static void main(String[] args) {
		MainCalculatorApp calc1 = new MainCalculatorApp();

	}
}
