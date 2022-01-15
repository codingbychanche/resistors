package VoltageDiv;

/**
 * Contains a single results of the {@link Divider.findResistors} method. Since
 * for every voltage divider there is always more than one solutions, an
 * instance of this class should be added to an instance of the
 * {@link DividerResults} class which has various methods to evaluate all the
 * results....
 * 
 * @author Berthold<p>
 * 
 * Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA 4.0) 
 *
 */
public class DividerResult {
	private double vIn_V, vOut_V;
	private double vOutCalc_V; // Resulting output voltage for the standard value f r2
	private double r1_V, r2_V;
	private int r1FoundInSeries, r2FoundInSeries;
	private double errorForR2_P; // Deviation in percent from the calculated value of r2 and the standard value
	private double errorForVout_P; // Deviation from the selected output voltage and the resulting outputvoltage
									// for the standard value of r2

	/**
	 * Creates a new result containing :
	 *
	 * @param vOutCalc_V      The resulting output voltage for the found standard
	 *                        value of r2.
	 * @param r1_V            Standart value of r1.
	 * @param r2_V            Standart value of r2.
	 * @param r1FoundInSeries The E- series in which r1 was found.
	 * @param r2FoundInSeries The E- series in which r2 was found.
	 * @param errorForR2_P    The deviation between the calculated value for r2 and
	 *                        the standard value of r2 in percent.
	 * @param errorForVout_P  The deviation between the given output voltage and the
	 *                        resulting output voltage for the standard values found
	 *                        for r1 and r2.
	 */
	public DividerResult(double vOutCalc_V, double r1_V, double r2_V, int r1FoundInSeries,
			int r2FoundInSeries, double errorForVout_P) {
		super();
		this.vOutCalc_V = vOutCalc_V;
		this.r1_V = r1_V;
		this.r2_V = r2_V;
		this.r1FoundInSeries = r1FoundInSeries;
		this.r2FoundInSeries = r2FoundInSeries;
		this.errorForVout_P = errorForVout_P;
	}

	public double getvIn_V() {
		return vIn_V;
	}

	public void setvIn_V(double vIn_V) {
		this.vIn_V = vIn_V;
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

	public double getErrorForR2_P() {
		return errorForR2_P;
	}

	public void setErrorForR2_P(double errorForR2_P) {
		this.errorForR2_P = errorForR2_P;
	}

	public double getErrorForVout_P() {
		return errorForVout_P;
	}

	public void setErrorForVout_P(double errorForVout_P) {
		this.errorForVout_P = errorForVout_P;
	}
}
