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
	public static List<ResistorResult> ofSeries(int e) {
		double resistorValue_Ohms = 1;
		double rLast = 1;

		int ddigits = MathHelper.getDdigitsForSeries(e);

		ResistorResult result;
		List<ResistorResult> rSeries = new ArrayList<>();
		rLast = 1; // Every series begins with 1 Ohms!

		for (int i = 1; i <= e; i++) {
			resistorValue_Ohms = rLast * getKValueForSeries(e);
			
			result = new ResistorResult(MathHelper.round(resistorValue_Ohms, ddigits, RoundingMode.UP),
					MathHelper.round(resistorValue_Ohms, ddigits, RoundingMode.UP), e, getSeriesSpecificErrorMargin(e), true);
			
			rSeries.add(result);
			
			rLast = resistorValue_Ohms;
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
		return MathHelper.round(kVal, 2, RoundingMode.UP);
	}

	/**
	 * Gets the standard value of a resistor from any of the series E3..E96 which is
	 * within the given error margin.
	 * 
	 * @param rToTest               Value of resistor in Ohm to check.
	 * @param allowedError_P        Allowed error margin in percent.
	 * 
	 * @param listOfSeriesToExclude The list consists of either no series or all
	 *                              series not to be considered
	 * 
	 * @return The standard value for the resistor in Ohm found in series E3..E96
	 *         for which the given resistor value matches and the series to which
	 *         this resistor belongs to. The result is given inside an instance of
	 *         {@link ResistorResult}. The first series in which the resistor value
	 *         could be found will be returned.
	 */
	public static ResistorResult getRValueClosestTo(double rToTest, double allowedError_P,
			List<Integer> listOfSeriesToExclude) {

		List<ResistorResult> rStandard = new ArrayList<>();
		int eSeries[] = { 3, 6, 12, 24, 48, 96 };

		int pow = 1;
		if (rToTest >= 10)
			pow = 10;
		if (rToTest >= 100)
			pow = 100;
		if (rToTest >= 1000)
			pow = 1000;
		if (rToTest >= 10000000)
			pow = 1000000;

		// Loop through series
		for (int e : eSeries) {
			if (!listOfSeriesToExclude.contains(e)) {
				rStandard = ofSeries(e);

				// Loop through values in each series
				for (ResistorResult standardValueForR : rStandard) {

					if (MathHelper.isInRangeWithinPercentage(allowedError_P, rToTest, standardValueForR.getFoundResistorValue_Ohms() * pow)) {

						//
						// We've found a matching resistor....
						//
						ResistorResult r = new ResistorResult(standardValueForR.getFoundResistorValue_Ohms() * pow, rToTest, e,
								getSeriesSpecificErrorMargin(e), true);
						
						return r;
					}
				}
			}
		}
		//
		// We've found nothing.....
		//
		ResistorResult r = new ResistorResult(0, rToTest, 0, 0, false);
		return r;
	}

	/**
	 * Returns the specific error margin for the given E- series.
	 * 
	 * @param e The standard series (E3..E96).
	 * @return Either 0 if no matching series was passed or the specific error
	 *         margin in percent for the series passed.
	 */

	static public int getSeriesSpecificErrorMargin(int e) {

		switch (e) {
		case 3:
			return 25; // Error is > +/- 20% so I choose 25%...

		case 6:
			return 20;

		case 12:
			return 10;

		case 24:
			return 5;

		case 48:
			return 2;

		case 96:
			return 1;

		}
		return 0;
	}
}
