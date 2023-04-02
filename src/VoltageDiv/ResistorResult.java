package VoltageDiv;

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
public class ResistorResult implements Comparable<ResistorResult> {
	private double givenResistorValue_Ohms; 	// Initial value searched for
	private double foundResistorValue_Ohms; // Found resistor
	private int belongsToESeries;
	private double actualError_P; 				// Error between value searched and standard value found.
	private double seriesSpecificErrorMargin; 	// Error +/- in percent for the series the resistor belongs to.

	/**
	 * Creates a new result for a resistor.
	 * 
	 * @param foundResistorValue_Ohms   The value found.
	 * @param givenResistorValue_Ohms   The value searched for.
	 * @param belongsToESeries          The E- series this resistor belongs to.
	 * @param seriesSpecificErrorMargin The error margin for this resistor
	 *                                  considering the series it belongs to.
	 * @param found                     True, if a result for the resistor was
	 *                                  found.
	 */
	public ResistorResult(double foundResistorValue_Ohms, double givenResistorValue_Ohms, int belongsToESeries,
			int seriesSpecificErrorMargin, boolean found) {
		super();
		this.foundResistorValue_Ohms = foundResistorValue_Ohms;
		this.givenResistorValue_Ohms = givenResistorValue_Ohms;
		this.belongsToESeries = belongsToESeries;
		this.seriesSpecificErrorMargin = seriesSpecificErrorMargin;
	}

	/**
	 * Calculates the error between the value searched and the standard value
	 * available rounded to two decimal places.
	 * 
	 * @return Error in percent.
	 */
	public double getActualError_P() {
		int dDigits = 2;
		return MathHelper.getError_P(foundResistorValue_Ohms, givenResistorValue_Ohms, dDigits);
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

	public int getESeries() {
		return belongsToESeries;
	}

	public void setESeries(int belongsToESeries) {
		this.belongsToESeries = belongsToESeries;
	}

	/**
	 * The error margin for the resistor, depending from the standard series it
	 * belongs to.
	 * 
	 * @return Error margin in %
	 */
	public double getSeriesSpecificErrorMargin() {
		return this.seriesSpecificErrorMargin;
	}
	
	/**
	 * Sorts all results by actual error between value searched for and value found 
	 * in any of the E- series.
	 * 
	 * @param r An instance of type {@link ResistorResult}
	 * @return An integer either 1 or 0.
	 */
	@Override
	public int compareTo(ResistorResult r) {
		if (Math.abs(this.getActualError_P()) > Math.abs(r.getActualError_P()))
			return 1;
		if (Math.abs(this.getActualError_P()) < Math.abs(r.getActualError_P()))
			return -1;
		return 0;
	}
}
