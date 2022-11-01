package VoltageDiv;

/**
 * Contains a single result for a given voltage divider,
 * <p>
 * 
 * Since there is almost always more than one solution an instance of this class
 * will be added to an instance of the {@link DividerResults} class which stores
 * them in a {@link List} and facilitates various methods to evaluate them.
 * <p>
 * 
 * <b>TODO: Sort method must be changed.... my and min output voltage should be 
 * considered. We could determine the span between max and min error and sort the
 * list be smallest span first to biggest span last...</b>
 * 
 * @author Berthold
 *         <p>
 *         Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA
 *         4.0)
 *
 */
public class DividerResult implements Comparable<DividerResult> {
	private double vOutDesiered_V;			// Initial output voltage. 
	private double vOutMax_V, vOutMin_V; 	// Min and max output voltage
	private double r1_V, r2_V;
	private int r1FoundInSeries, r2FoundInSeries;

	/**
	 * Creates a new result containing :
	 * 
	 * @param vOutDesiered
	 * 
	 * @param vOutMax_V       Maximal output voltage of this divider <u>considering
	 *                        the smallest error margin</u> of each of the resistors
	 *                        of this divider.
	 * 
	 * @param vOutMin_V       Minimal output voltage of this divider <u>considering
	 *                        the largest error margin</u> of each of the resistors
	 *                        of this divider.
	 * 
	 * @param r1_V            Standart value of r1.
	 * @param r2_V            Standart value of r2.
	 * @param r1FoundInSeries The E- series in which r1 was found.
	 * @param r2FoundInSeries The E- series in which r2 was found.
	 * @param errorForVout_P  The deviation between the given output voltage and the
	 *                        resulting output voltage for the standard values found
	 *                        for r1 and r2.
	 */
	public DividerResult(double vOutDesiered_V,double vOutMax_V, double vOutMin_V, double r1_V, double r2_V, int r1FoundInSeries,
			int r2FoundInSeries) {

		super();
		this.vOutDesiered_V=vOutDesiered_V;
		this.vOutMax_V = vOutMax_V;
		this.vOutMin_V = vOutMin_V;
		this.r1_V = r1_V;
		this.r2_V = r2_V;
		this.r1FoundInSeries = r1FoundInSeries;
		this.r2FoundInSeries = r2FoundInSeries;
	}

	/**
	 * The initial value for the iutput voltage one anticipated.
	 * 
	 * @return Initial output voltage.
	 */
	public double getVOutDesiered() {
		return this.vOutDesiered_V;
	}
	/**
	 * <u>Maximum</u> output voltage of this divider considering the series specific
	 * error margins for R2 and R1.
	 * 
	 * @return Maximum output voltage for this divider.
	 */
	public double getvOutMax_V() {
		return vOutMax_V;
	}

	/**
	 * <u>Minimum</u> output voltage of this divider considering the series specific
	 * error margins for R2 and R1.
	 * 
	 * @return Minimum output voltage for this divider.
	 */
	public double getvOutMin_V() {
		return vOutMin_V;
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
	 *         three decimal places.<p>
	 *         
	 * <b>TODO: Change.... We should consider both errors: Between max and min output voltage... </b>
	 */
	public double getActualErrorInOutputVoltage_P() {
		int decimalPlaces = 3;
		return MathHelper.getError_P(vOutMax_V, vOutDesiered_V, decimalPlaces);
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
