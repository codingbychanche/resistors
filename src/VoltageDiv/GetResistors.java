package VoltageDiv;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of basic formulas for resistors.
 * 
 * @author Berthold
 *         <p>
 * 
 *         Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA
 *         4.0)
 *
 */
public class GetResistors {

	/**
	 * Returns a list of all resistors matching the given series.
	 * 
	 * @param e Series designator (E3,E6....E96).
	 * @return A {@link List} of all resistors matching the given series. Values are
	 *         rounded to 1 or decimal places depending on the series chosen.
	 * 
	 *         TODO: Rounding good, but not perfect. E.g. 2.06 Ohms in E48=> wrong
	 *         true value= 2.05. This seems to be the case because actually standard
	 *         resistors available in series E24 do not always match the calculated
	 *         value:
	 *         <p>
	 *         https://en.wikipedia.org/wiki/E_series_of_preferred_numbers
	 */
	public static List<Double> ofSeries(double e) {
		double r = 1;
		double rLast = 1;

		int ddigits = MathHelper.getDdigitsForSeries(e);

		List<Double> rSeries = new ArrayList<>();
		rLast = 1; // Every series begins with 1 Ohms!

		for (int i = 1; i <= e; i++) {
			r = rLast * getKValueForSeries(e);
			rSeries.add(MathHelper.round(rLast, ddigits, RoundingMode.UP));
			rLast = r;
		}
		return rSeries;
	}

	/**
	 * Calculates the k- value for the given series.
	 * 
	 * @param e Series designator (E3,E6....E96).
	 * @return The k- value for the given series rounded to two decimal places.
	 * 
	 */
	public static double getKValueForSeries(double e) {
		double kVal = Math.pow(10, 1 / e);
		return MathHelper.round(kVal, 2,RoundingMode.UP);
	}

	/**
	 * Gets the standard value of a resistor from any of the series E3..E96 which is
	 * within the given error margin.
	 * 
	 * @param rToTest        Value of resistor in Ohm to check.
	 * @param allowedError_P Allowed error margin in percent.
	 * @return The standard value for the resistor in Ohm found in series E3..E96
	 *         for which the given resistor value matches and the series to which
	 *         this resistor belongs to. The result is given inside an instance of
	 *         {@link ResistorResult}. The first series in which the resistor value
	 *         could be found will be returned.
	 */
	public static ResistorResult getRValueClosestTo(double rToTest, double allowedError_P) {
		List<Double> rStandard = new ArrayList<>();
		int eSeries[] = { 3, 6, 12, 24, 48, 96 };

		int pow=1;
		if (rToTest>=10) pow=10;
		if (rToTest>=100) pow=100;
		if (rToTest>=1000) pow=1000;
		if (rToTest>=10000000) pow=1000000;
		
		// Loop through series
		for (int e : eSeries) {

			rStandard = ofSeries(e);

			// Loop through values in each series
			for (double standardValueForR : rStandard) {

				if (MathHelper.isInRangeWithinPercentage(allowedError_P, rToTest, standardValueForR * pow)) {
					
					ResistorResult r = new ResistorResult(standardValueForR * pow, rToTest, e, true);
					return r;
				}
			}
		}
		ResistorResult r = new ResistorResult(0, rToTest, 0, false);
		return r;
	}
}
