package VoltageDiv;

/**
 * Contains a single result for a given voltage divider,
 * <p>
 * 
 * Since there is almost always more than one solution an instance of this class
 * will be added to an instance of the {@link DividerResults} class which stores
 * them in a {@link List} and facilitates various methods to evaluate them.
 * 
 * @author Berthold
 *         <p>
 *         Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA
 *         4.0)
 *
 */
public class DividerResult implements Comparable<DividerResult> {
	private double vOut_V;
	private double vOutCalc_V; // Resulting output voltage for the standard value f R2
	private double r1_V, r2_V;
	private int r1FoundInSeries, r2FoundInSeries;

	/**
	 * Creates a new result containing :
	 *
	 * @param vOutCalc_V      The resulting output voltage for the found standard
	 *                        value of r2.
	 * @param r1_V            Standart value of r1.
	 * @param r2_V            Standart value of r2.
	 * @param r1FoundInSeries The E- series in which r1 was found.
	 * @param r2FoundInSeries The E- series in which r2 was found.
	 * @param errorForVout_P  The deviation between the given output voltage and the
	 *                        resulting output voltage for the standard values found
	 *                        for r1 and r2.
	 */
	public DividerResult(double vOut_V, double vOutCalc_V, double r1_V, double r2_V, int r1FoundInSeries,
			int r2FoundInSeries) {
		super();
		this.vOut_V = vOut_V;
		this.vOutCalc_V = vOutCalc_V;
		this.r1_V = r1_V;
		this.r2_V = r2_V;
		this.r1FoundInSeries = r1FoundInSeries;
		this.r2FoundInSeries = r2FoundInSeries;
	}

	public double getvOut_V() {
		return vOut_V;
	}

	public void setvOut_V(double vOut_V) {
		this.vOut_V = vOut_V;
	}

	public double getvOutCalc_V() {
		return vOutCalc_V;
	}

	public void setvOutCalc_V(double vOutCalc_V) {
		this.vOutCalc_V = vOutCalc_V;
	}

	public double getR1_V() {
		return r1_V;
	}

	public void setR1_V(double r1_V) {
		this.r1_V = r1_V;
	}

	public double getR2_V() {
		return r2_V;
	}

	public void setR2_V(double r2_V) {
		this.r2_V = r2_V;
	}

	public int getR1FoundInSeries() {
		return r1FoundInSeries;
	}

	public void setR1FoundInSeries(int r1FoundInSeries) {
		this.r1FoundInSeries = r1FoundInSeries;
	}

	public int getR2FoundInSeries() {
		return r2FoundInSeries;
	}

	public void setR2FoundInSeries(int r2FoundInSeries) {
		this.r2FoundInSeries = r2FoundInSeries;
	}

	/**
	 * Determines the error between initial Vout and found Vout.
	 * 
	 * @return A {@link Double} value containing the error in percent, rounded to
	 *         three decimal places.
	 */
	public double getActualErrorInOutputVoltage_P() {
		int decimalPlaces = 3;
		return MathHelper.getError_P(vOutCalc_V, vOut_V, decimalPlaces);
	}

	/**
	 * Sort.
	 * <p>
	 * Sorts instances of this by the size of the error between the initial Vout and
	 * Vout resulting from the found combinations of R1 and R2 in ascending order.
	 */
	@Override
	public int compareTo(DividerResult r) {
		if (Math.abs(this.getActualErrorInOutputVoltage_P()) > Math.abs(r.getActualErrorInOutputVoltage_P()))
			return 1;
		if (Math.abs(this.getActualErrorInOutputVoltage_P()) < Math.abs(r.getActualErrorInOutputVoltage_P()))
			return -1;
		return 0;
	}
}
