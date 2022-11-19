package VoltageDiv;

import java.math.RoundingMode;

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
 * considered. We could determine the span between max and min error and sort
 * the list be smallest span first to biggest span last...</b>
 * 
 * @author Berthold
 *         <p>
 *         Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA
 *         4.0)
 *
 */
public class DividerResult implements Comparable<DividerResult> {
	private double vOutDesiered_V; // Initial output voltage.
	private double vOutNominal_V; // Theoretical output voltage, no errors in resistor value considered.
	private double vOutMax_V, vOutMin_V; // Min and max output voltage
	private double r1_V, r2_V;
	private int r1FoundInSeries, r2FoundInSeries;
	private int decimalPlaces;

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
	 * @param decimalPlaces   Decimal places used for all values in this instance.
	 */
	public DividerResult(double vOutDesiered_V, double vOutNominal, double vOutMax_V, double vOutMin_V, double r1_V,
			double r2_V, int r1FoundInSeries, int r2FoundInSeries, int decimalPlaces) {

		super();
		this.vOutDesiered_V = vOutDesiered_V;
		this.vOutNominal_V = vOutNominal_V;
		this.vOutMax_V = vOutMax_V;
		this.vOutMin_V = vOutMin_V;
		this.r1_V = r1_V;
		this.r2_V = r2_V;
		this.r1FoundInSeries = r1FoundInSeries;
		this.r2FoundInSeries = r2FoundInSeries;
		this.decimalPlaces = decimalPlaces;
	}

	/**
	 * The initial value for the input voltage one anticipated.
	 * 
	 * @return Output voltage anticipated.
	 */
	public double getVOutDesiered() {
		return this.vOutDesiered_V;
	}
	
	/**
	 * The theoretical output voltage with no errors considered for resistor 1 and resistor 2
	 * of this divider.
	 * 
	 * @return A {@link Double value for the nominal output voltage of this divider.
	 */
	public double getVoutNominal() {
		return vOutNominal_V;
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

	/**
	 * Resistor 1 of this divider.
	 * 
	 * @return A {@link Double} value of the resistance in &Omega;
	 */
	public double getR1_V() {
		return r1_V;
	}

	/**
	 * Resistor 1 of this divider.
	 * 
	 * @param r1_V A {@link Double} value of the resistance in &Omega;
	 */
	public void setR1_V(double r1_V) {
		this.r1_V = r1_V;
	}

	/**
	 * Resistor 2 of this divider.
	 * 
	 * @return A {@link Double} value of the resistance in &Omega;
	 */
	public double getR2_V() {
		return r2_V;
	}

	/**
	 * Resistor 2 of this divider.
	 * 
	 * @param r2_V A {@link Double} value of the resistance in &Omega;
	 */
	public void setR2_V(double r2_V) {
		this.r2_V = r2_V;
	}

	/**
	 * The standard series resistor 1 of this divider belongs to.
	 * 
	 * @return A non dimensional {@link Integer} value containing the standard
	 *         series (E3..E96).
	 */
	public int getR1FoundInSeries() {
		return r1FoundInSeries;
	}

	/**
	 * The standard series resistor 1 of this divider belongs to.
	 * 
	 * @param r1FoundInSeries non dimensional {@link Integer} value containing the
	 *                        standard series (E3..E96).
	 */
	public void setR1FoundInSeries(int r1FoundInSeries) {
		this.r1FoundInSeries = r1FoundInSeries;
	}

	/**
	 * The standard series resistor 2 of this divider belongs to.
	 * 
	 * @return A non dimensional {@link Integer} value containing the standard
	 *         series (E3..E96).
	 */
	public int getR2FoundInSeries() {
		return r2FoundInSeries;
	}

	/**
	 * The standard series resistor 2 of this divider belongs to.
	 * 
	 * @param r2FoundInSeries non dimensional {@link Integer} value containing the
	 *                        standard series (E3..E96).
	 */
	public void setR2FoundInSeries(int r2FoundInSeries) {
		this.r2FoundInSeries = r2FoundInSeries;
	}

	/**
	 * Determines the deviation from the maximum output voltage from the output
	 * voltage anticipated of this divider. The deviation depends on the series
	 * specific error margin of the resistors chosen for this divider.
	 * 
	 * @return A {@link Double} value showing the deviation in volts, from the
	 *         output voltage anticipated.
	 */
	public double getDevFromMaxVoltage() {
		return MathHelper.round(this.vOutMax_V - this.vOutDesiered_V, decimalPlaces, RoundingMode.CEILING);
	}

	/**
	 * Determines the deviation from the minimum output voltage from the output
	 * voltage anticipated of this divider. The deviation depends on the series
	 * specific error margin of the resistors chosen for this divider.
	 * 
	 * @return A {@link Double} value showing the deviation in volts, from the
	 *         output voltage anticipated.
	 */
	public double getDevFromMinVoltage() {
		return MathHelper.round(this.vOutMin_V - this.vOutDesiered_V, decimalPlaces, RoundingMode.CEILING);
	}

	/**
	 * The difference between the maximum and the minimum output voltage of this
	 * divider.
	 * 
	 * @return A {@link Double} value containing the difference between the maximum
	 *         and the minimum output voltage of this divider, rounded to the
	 *         decimal places specified, using Java's {@link BigDecimal}
	 *         {@link RoundingMode.CEILING}.
	 */
	public double getErrorMargin() {
		return MathHelper.round(this.vOutMax_V - this.vOutMin_V, decimalPlaces, RoundingMode.CEILING);
	}

	/**
	 * <p>
	 * Evaluates the result for this given divider.
	 * </p>
	 * 
	 * <p>
	 * The smaller this value is, the closer either the maximum or the minimum
	 * output voltage is to the output voltage anticipated and the the smaller the
	 * spread between maximum and minimum output voltage. is
	 * </p>
	 * 
	 * <p>
	 * <u>Evaluation is done as follows:</u>
	 * </p>
	 * <ul>
	 * |anticipatedOutputV-maxOutputV|+|anticipatedOutputV-minOutputV|
	 * </ul>
	 * 
	 * <u>Example:</u>
	 * <p>
	 * Anticipated: 1.8 V<br>
	 * Input Voltage: 5.5 V
	 * </p>
	 * 
	 * <p>
	 * Best result:<br>
	 * R1=4.7&Omega;<br>
	 * R2=10.1&Omega;<br>
	 * VOut<sub>max</sub>=1,712 V (-0.088V from output voltage anticipated)<br>
	 * VOut<sub>min</sub>=1,747 V (-0.052V from output voltage anticipated)<br>
	 * </p>
	 * 
	 * <p>
	 * Poorest result:<br>
	 * R1=10.1&Omega;<br>
	 * R2=22.0&Omega;<br>
	 * VOut<sub>max</sub>=1,721 V (-0.078V from output voltage anticipated)<br>
	 * VOut<sub>min</sub>=1,683 V (-0.116V from output voltage anticipated)<br>
	 * </p>
	 * 
	 * <p>
	 * As one can see, the poorest result has a larger error margin and the
	 * deviation from the output voltage anticipated is greater compared to the best
	 * result.
	 * </p>
	 * 
	 * 
	 * 
	 * 
	 * @return A non- dimensional {@link Double} value evaluating the accuracy of
	 *         this divider.
	 */
	public double getEval() {
		return Math.abs(this.vOutDesiered_V - this.vOutMax_V) + (this.vOutDesiered_V - this.vOutMin_V);
	}

	/**
	 * Sort.
	 * <p>
	 * Sorts instances of this based on the evaluation value of this divider.
	 */
	@Override
	public int compareTo(DividerResult r) {
		if (Math.abs(this.getEval()) > Math.abs(r.getEval()))
			return 1;
		if (Math.abs(this.getEval()) < Math.abs(r.getEval()))
			return -1;
		return 0;
	}
}
