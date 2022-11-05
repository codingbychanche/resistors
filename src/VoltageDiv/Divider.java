package VoltageDiv;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * A Voltage Divider.
 * 
 * @author Berthold
 *         <p>
 * 
 *         Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA
 *         4.0)
 */
public class Divider {

	/**
	 * Finds a series of resistors which meet the condition for the given in- and
	 * output voltage of a voltage divider.
	 * <p>
	 * The initial solution is found by assuming that R1=1 Ohm.
	 * <p>
	 * By substituting R2 with the known ratio between Vin and Vout (=r) we find the
	 * following formula for R2:<br>
	 * [(1-r)/r] x R1 = R2
	 * <p>
	 * The result is a instance of {@link DividerResults} containing any possible
	 * combinations of resistors across all E- series meeting the condition for the
	 * given in- and output voltage.
	 * <p>
	 * R1 is always a standard value for which the algorithm tries to find a
	 * matching standard value for R2 in order to get the desired output voltage.
	 * <p>
	 * The maximum error margin for R2 is set to the maximum error across all
	 * standard series within the algorithm tries to find the best solution for the
	 * output voltage.
	 * 
	 * @param vIn_V                Inputvoltage in volts.
	 * @param vOutDesiered_V       Output voltage in volts.
	 * 
	 * @param listOfExcludedSeries For the result, none of these series are
	 *                             considered.
	 * 
	 * @return An instance of {@link DividerResults} containing either one or more
	 *         instances of {@link DividerResult} or none if no result was found or
	 *         the input voltage was equal or lower than the output voltage.
	 */
	public static DividerResults findResistors(double vIn_V, double vOutDesiered_V,
			List<Integer> listOfExcludedSeries) {

		double ratio = vOutDesiered_V / vIn_V;
		double r2Calc_Ohms = (1 - ratio) / ratio;
		double rCalc;

		ResistorResult foundStandardValueForR2_Ohm;

		double vOutMin, vOutMax; // Min./ maximum output voltage considering the series specific error
									// margin....

		// Create a new instance holding the result. If output voltage is bigger than
		// the input voltage, return the empty result.
		DividerResults dividerResults = new DividerResults(vIn_V, vOutDesiered_V);

		if (vOutDesiered_V >= vIn_V)
			return dividerResults;

		// A list of the standard series...
		int eSeries[] = { 3, 6, 12, 24, 48, 96 };

		// The maximum error for r2 = biggest error in any of the standard series...
		double maxTolErrForR2_P = 25;

		// For all results...
		int decimalPlaces = 3;

		//
		// Search a solutions for possible dividers, build a list of all dividers found.
		//
		// Loop through all series
		//
		Long startTimeIn_Ms = System.currentTimeMillis();

		for (int lookUpR1InSeries : eSeries) {

			// Check if this series is excluded, if so, do not consider this series.
			if (!listOfExcludedSeries.contains(lookUpR1InSeries)) {
				List<ResistorResult> listOfR1Found = GetResistors.ofSeries(lookUpR1InSeries);

				// Get R1 from series and find matching R2
				for (ResistorResult r1 : listOfR1Found) {
					rCalc = r2Calc_Ohms * r1.getFoundResistorValue_Ohms();

					// Check if r is available in any of the standard series
					foundStandardValueForR2_Ohm = GetResistors.getRValueClosestTo(rCalc, maxTolErrForR2_P,
							listOfExcludedSeries);

					if (foundStandardValueForR2_Ohm.found()) {

						vOutMax = getMaxOutputVoltag_V(vIn_V, r1, foundStandardValueForR2_Ohm);
						vOutMin = getMinOutputVoltag_V(vIn_V, r1, foundStandardValueForR2_Ohm);

						DividerResult result = new DividerResult(vOutDesiered_V, vOutMax, vOutMin,
								r1.getFoundResistorValue_Ohms(),
								foundStandardValueForR2_Ohm.getFoundResistorValue_Ohms(), lookUpR1InSeries,
								foundStandardValueForR2_Ohm.getESeries());

						dividerResults.addResult(result);
					}
				}
			}
		}
		Long endTimeIn_Ms = System.currentTimeMillis();
		dividerResults.setTimeItTookIn_ms((endTimeIn_Ms - startTimeIn_Ms));

		return dividerResults;
	}

	/**
	 * The maximum output voltage of this divider, <u>considering the series
	 * specific error for each of the resistors.</u>
	 * 
	 * <p>
	 * If r1 was chosen from series E3 and r2 was chosen from series E6:<br>
	 * <ul>
	 * r1 in E3=> error 25 % => r1_min=r1*0.75 and r1_min=r1*1.25
	 * </ul>
	 * <ul>
	 * r2 in E6=> error 20 % => r2_min=r2*0.80 and r2_min=r2*1.20
	 * </ul>
	 * </p>
	 * 
	 * <p>
	 * Now the resulting maximum output voltage for this divider is:<br>
	 * 
	 * <ul>
	 * vOut_max=(r1_min/(r1_min+r2_min))*vIn_V
	 * </ul>
	 * </P>
	 * 
	 * @param vIn_V Input voltage
	 * @param r1    Resistor 1, an instance of {@link ResistorResult}
	 * @param r2    Resistor 2, , an instance of {@link ResistorResult}
	 * @return The resulting maximum output voltage for this divider, resulting from
	 *         the minimal error margin for each of the resistors.
	 */
	public static double getMaxOutputVoltag_V(double vIn_V, ResistorResult r1, ResistorResult r2) {

		double r1Min = r1.getFoundResistorValue_Ohms() * 1 - (r1.getSeriesSpecificErrorMargin() / 100);
		double r2Min = r2.getFoundResistorValue_Ohms() * 1 - (r2.getSeriesSpecificErrorMargin() / 100);

		return getOutputVOltage(vIn_V, r1Min, r2Min);
	}

	/**
	 * The minimum output voltage of this divider, <u>considering the series
	 * specific error for each of the resistors.</u>
	 * 
	 * <p>
	 * If r1 was chosen from series E3 and r2 was chosen from series E6:<br>
	 * <ul>
	 * r1 in E3=> error 25 % => r1_max=r1*0.75 and r1_max=r1*1.25
	 * </ul>
	 * <ul>
	 * r2 in E6=> error 20 % => r2_max=r2*0.80 and r2_max=r2*1.20
	 * </ul>
	 * </p>
	 * 
	 * <p>
	 * Now the resulting maximum output voltage for this divider is:<br>
	 * 
	 * <ul>
	 * vOut_max=(r1_min/(r1_min+r2_min))*vIn_V
	 * </ul>
	 * </P>
	 * 
	 * @param vIn_V Input voltage
	 * @param r1    Resistor 1, an instance of {@link ResistorResult}
	 * @param r2    Resistor 2, , an instance of {@link ResistorResult}
	 * @return The resulting maximum output voltage for this divider, resulting from
	 *         the minimal error margin for each of the resistors.
	 */
	public static double getMinOutputVoltag_V(double vIn_V, ResistorResult r1, ResistorResult r2) {

		double r1Max = r1.getFoundResistorValue_Ohms() * (1 + (r1.getSeriesSpecificErrorMargin() / 100));
		double r2Max = r2.getFoundResistorValue_Ohms() * (1 + (r2.getSeriesSpecificErrorMargin() / 100));

		return getOutputVOltage(vIn_V, r1Max, r2Max);
	}

	/**
	 * Calculates the output voltage of this divider
	 * 
	 * @param vIn_V   Input voltage in Volts.
	 * @param r1_Ohms Value for first resistor in Ohms.
	 * @param r2_Ohms Value for second resistor in Ohms.
	 * @return Output voltage in Volts.
	 */
	public static double getOutputVOltage(double vIn_V, double r1_Ohms, double r2_Ohms) {

		return (r1_Ohms / (r1_Ohms + r2_Ohms)) * vIn_V;
	}

}
