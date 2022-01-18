package VoltageDiv;

/**
 * Contains a single results of the {@link Divider.findResistors} method. Since
 * for every voltage divider there is always more than one solution, an instance
 * of this class should be added to an instance of the {@link DividerResults}
 * class which has various methods to evaluate all the results....
 * 
 * @author Berthold
 *         <p>
 * 
 *         Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA
 *         4.0)
 *
 */
public class DividerResult implements Comparable <DividerResult>{
	private double vOut_V;
	private double vOutCalc_V; // Resulting output voltage for the standard value f r2
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
	public DividerResult(double vOut_V,double vOutCalc_V, double r1_V, double r2_V, int r1FoundInSeries, int r2FoundInSeries) {
		super();
		this.vOut_V=vOut_V;
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

	public double getActualErrorInOutputVoltage_P() {
		if (vOutCalc_V>vOut_V)
			 return 100-((vOut_V/vOutCalc_V)*100);
		if (vOutCalc_V<vOut_V)
			return (100-(vOut_V/vOutCalc_V)*100)*-1;
		return 0;
	}

	@Override
	public int compareTo(DividerResult r) {
		if (this.getActualErrorInOutputVoltage_P()>r.getActualErrorInOutputVoltage_P())
			return 1;
		if (this.getActualErrorInOutputVoltage_P()<r.getActualErrorInOutputVoltage_P())
			return -1;
		return 0;
	}
}
