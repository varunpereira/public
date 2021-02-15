
public class RentalProperty {
	private String address;
	private int numBedrooms;
	private int numBathrooms;
	private double pricePerWeek;
	private String agentPhone;

	public RentalProperty(String address, int numBedrooms, int numBathrooms, double pricePerWeek, String agentPhone) {
		this.address = address;
		this.numBedrooms = numBedrooms;
		this.numBathrooms = numBathrooms;
		this.pricePerWeek = pricePerWeek;
		this.agentPhone = agentPhone;
	}

	public String getAddress() {
		return this.address;
	}

	public int getNumBedrooms() {
		return this.numBedrooms;
	}

	public int getNumBathrooms() {
		return this.numBathrooms;
	}

	public double getPricePerWeek() {
		return this.pricePerWeek;
	}

	public String getAgentPhone() {
		return this.agentPhone;
	}

	public String toString() {
		String message = "";
		message += this.address + ":";
		message += this.numBedrooms + ":";
		message += this.numBathrooms + ":";
		message += this.pricePerWeek + ":";
		message += this.agentPhone;
		return message;
	}

}
