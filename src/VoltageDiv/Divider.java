package VoltageDiv;

import java.util.List;

/**
 * A Voltage Divider.
 * 
 * @author Berthold
 *
 */
public class Divider {

	/**
	 * Finds a series of resistors which meet the condition for the given in- and
	 * output voltage of a divider.
	 * <p>
	 * The initial solution is found by assuming that R1=1 Ohm
	 * <p>
	 * By substituting R2 with the known ratio between Vin and Vout (r) we find the
	 * following formula for R2:<br>
	 * [(1-r)/r] x R1 = R2
	 * <p>
	 * The result is a ist with any possible combination of resistors across all e-
	 * series meeting the condition for the given in- and output voltage.
	 * 
	 * 
	 * @param vIn_V            Inputvoltage in volts.
	 * @param vOut_V           Output voltage in volts.
	 * @param maxTolErrForR2_P Biggest tolarable error between calclated value of R2
	 *                         and standard value found in any of the standard
	 *                         series.
	 */
	public static void findResistors(double vIn_V, double vOut_V, double maxTolErrForR2_P) {
		double ratio = vOut_V / vIn_V;
		double r1Calc_Ohms = 1;
		double r2Calc_Ohms = (1 - ratio) / ratio;

		double rCalc;
		ResistorResult res;
		double r2_Ohms;
		double outputVoltage_V;
		int eSeries[] = { 3, 6, 12, 24, 48, 96 };

		// Loop through all series
		for (int lookUpR1InSeries : eSeries) {
			List<Double> series = GetResistors.ofSeries(lookUpR1InSeries);

			// Get R1 from series and find matching R2
			for (double r1 : series) {
				rCalc = r2Calc_Ohms * r1;

				// Check if r is availabe in any of the standard series
				res = GetResistors.getRValueClosesdTo(rCalc, maxTolErrForR2_P);
				if (res.found()) {
					r2_Ohms = res.getFoundResistorValue_Ohms();

					outputVoltage_V=getOutputVoltage_V(vIn_V,r1,r2_Ohms);

					System.out.println("R1=" + r1 + " Ohm (Series E" + lookUpR1InSeries + ")      R2=" + r2_Ohms
							+ " Ohms (Series E" + res.getBelongsToESeries() + ")    Error:" + res.getActualError_P()
							+ " (calc r2 was:" + rCalc + ")    Resulting output voltage=" + outputVoltage_V + " V");
				}
			}
		}

	}
	
	/**
	 * Calculates the output voltage of a divider.
	 * 
	 * @param vIn_V Input voltage in Volts.
	 * @param r1_Ohms Value for first resistor in Ohms.
	 * @param r2_Ohms Value for second resistor in Ohms.
	 * @return Output voltage in Volts.
	 */
	public static double getOutputVoltage_V(double vIn_V,double r1_Ohms,double r2_Ohms) {
		return (r1_Ohms / (r1_Ohms + r2_Ohms)) * vIn_V;
	}
}
