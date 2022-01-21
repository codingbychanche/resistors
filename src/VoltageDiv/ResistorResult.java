package VoltageDiv;

import java.math.RoundingMode;

/**
 * Holds a result for a resistor value found inside in any of the series E3..E96
 * 
 * @author Berthold
 *         <p>
 * 
 *         Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA
 *         4.0)
 *
 */
public class ResistorResult {
	private double foundResistorValue_Ohms;
	private double givenResistorValue_Ohms;
	private int belongsToESeries;
	private double actualError_P;
	private boolean found;

	public ResistorResult(double foundResistorValue_Ohms, double givenResistorValue_Ohms, int belongsToESeries,
			boolean found) {
		super();
		this.foundResistorValue_Ohms = foundResistorValue_Ohms;
		this.givenResistorValue_Ohms = givenResistorValue_Ohms;
		this.belongsToESeries = belongsToESeries;
		this.found = found;
	}

	/**
	 * Calculates the error between the value searched and
	 * the standard value available rounded to two decimal places.
	 * 
	 * @return Error in percent.
	 */
	public double getActualError_P() {
		int dDigits=2;
		return MathHelper.getError_P(foundResistorValue_Ohms,givenResistorValue_Ohms,dDigits);
	}

	public double getFoundResistorValue_Ohms() {
		return foundResistorValue_Ohms;
	}

	public double getGivenResistorValue_Ohms() {
		return givenResistorValue_Ohms;
	}

	public void setResistorValue_Ohms(float resistorValue_Ohms) {
		this.foundResistorValue_Ohms = resistorValue_Ohms;
	}

	public int getBelongsToESeries() {
		return belongsToESeries;
	}

	public void setBelongsToESeries(int belongsToESeries) {
		this.belongsToESeries = belongsToESeries;
	}

	public boolean found() {
		return found;
	}
}
