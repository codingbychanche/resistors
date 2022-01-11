package VoltageDiv;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of basic formulas.
 * 
 * @author Berthold
 *
 */
public class GetResistors {

	/**
	 * Returns a list of all resistors matching the given series.
	 * 
	 * @param e Series designator (E3,E6....E96).
	 * @return A {@link List} of all resistors matching the given series. Values are
	 *         rounded to 1 decimal place.
	 */
	public static List<Double> ofSeries(double e) {
		double r = 1;
		double rLast = 1;
		
		List<Double> rSeries = new ArrayList<>();

		rSeries.add(1.0); // Every series begin with 1 Ohm!

		for (int i = 1; i < e; i++) {
			r = rLast * getKValueForSeries(e);
			
			BigDecimal rD=new BigDecimal(r);
			BigDecimal rRounded=rD.setScale(1,RoundingMode.HALF_DOWN);
			
			rSeries.add(rRounded.doubleValue());
			rLast = r;
		}
		return rSeries;
	}

	/**
	 * Calculates the k- Val for the given series.
	 * 
	 * @param e Series disignator (E3,E6....E96).
	 * @return The k- value for the given series.
	 * 
	 */
	public static double getKValueForSeries(double e) {
		double kVal = Math.pow(10, 1 / e);
		return kVal;
	}

	/**
	 * Gets the standart value of a resistor from any of the series E3..E96 which is
	 * within the given error margin.
	 * 
	 * @param rToTest        Value of resistor in ohms to check.
	 * @param allowedError_P Allowed error margin in percent.
	 * @return The standart value for the resistor in ohms found in series E3..E96
	 *         for which the given resistor value matches and the series to which
	 *         this resistor belongs to. The result is given inside an instance of
	 *         {@link ResistorResult}. The first series found will be returned.
	 */
	public static ResistorResult getRValueClosesdTo(double rToTest, double allowedError_P) {
		List<Double> rStandard = new ArrayList<>();
		int eSeries[] = { 3, 6, 12, 24, 48, 96 };

		// Loop through series
		for (int e : eSeries) {

			rStandard = ofSeries(e);

			// Loop through values in each series
			for (double s : rStandard) {

				// Loop through powers of 10 for each series
				int pow = 1;
				for (int i = 1; i <= 8; i++) {
					if (isInRangeWithinPercentage(allowedError_P, rToTest, s * pow)) {
						ResistorResult r = new ResistorResult(s * pow, e, true);
						return r;
					}
					pow = pow * 10;
				}
			}
		}
		ResistorResult r = new ResistorResult(0, 0, false);
		return r;
	}

	/**
	 * Checks if a given value is inside an allowed error margin.
	 * 
	 * @param allowedError_P Error marign in percent.
	 * @param rToTest        The value to be tested.
	 * @param rStandart      The value to be testet against
	 * @return True if the value to be tested matches. False if not.<br>
	 *         E.g. Given: 4.35, error margin is 2%. Value to be tested against:
	 *         4.3<br>
	 *         Lower boundary: 4.3-4.3*(2/100)=4.21<br>
	 *         Upper boundary: 4.3*(1+(2/100))=4.38 => true!<br>
	 * 
	 */
	public static boolean isInRangeWithinPercentage(double allowedError_P, double rToTest, double rStandart) {
		double p = allowedError_P / 100;
		if (rToTest >= rStandart - (rStandart * p) && rToTest <= rStandart * (1 + p))
			return true;
		return false;
	}
}
