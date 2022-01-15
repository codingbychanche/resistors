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
	public static DividerResults findResistors(double vIn_V, double vOut_V, double maxTolErrForR2_P) {
		double ratio = vOut_V / vIn_V;
		double r1Calc_Ohms = 1;
		double r2Calc_Ohms = (1 - ratio) / ratio;
		double rCalc;
		ResistorResult foundStandardValueForR2_Ohm;
		double r2_Ohms;
		double outputVoltage_V;
		DividerResults dividerResults=new DividerResults(vIn_V,vOut_V,maxTolErrForR2_P);
		
		int eSeries[] = { 3, 6, 12, 24, 48, 96 };

		// Loop through all series
		for (int lookUpR1InSeries : eSeries) {
			List<Double> series = GetResistors.ofSeries(lookUpR1InSeries);

			// Get R1 from series and find matching R2
			for (double r1 : series) {
				rCalc = r2Calc_Ohms * r1;

				// Check if r is available in any of the standard series
				foundStandardValueForR2_Ohm = GetResistors.getRValueClosestTo(rCalc, maxTolErrForR2_P);
				if (foundStandardValueForR2_Ohm.found()) {
					r2_Ohms = foundStandardValueForR2_Ohm.getFoundResistorValue_Ohms();

					outputVoltage_V=getOutputVoltage_V(vIn_V,r1,r2_Ohms);

					//System.out.println("R1=" + r1 + " Ohm (Series E" + lookUpR1InSeries + ")      R2=" + r2_Ohms
							//+ " Ohms (Series E" + res.getBelongsToESeries() + ")    Error:" + res.getActualError_P()
							//+ " (calc r2 was:" + rCalc + ")    Resulting output voltage=" + outputVoltage_V + " V");
					
					DividerResult result=new DividerResult(
							outputVoltage_V,
							r1,r2_Ohms,
							lookUpR1InSeries,
							foundStandardValueForR2_Ohm.belongsToESeries,
							0);
					
					dividerResults.addResult(result);
				}
			}
		}
		return dividerResults;
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
