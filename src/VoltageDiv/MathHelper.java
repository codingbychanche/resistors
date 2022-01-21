package VoltageDiv;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Formatter.BigDecimalLayoutForm;

public class MathHelper {

	/**
	 * Rounds a {@link Double} value to the specified number of decimal places using
	 * Java's {@link BigDecimal} class.
	 * 
	 * @param r                       Number to be rounded.
	 * @param dDigits                 Number of decimal digits.
	 * @param bigDecimalsRoundingMode {@link BigDecimals} rounding method.
	 * @return A {@link Double} value rounded to the specified number of decimal
	 *         places.
	 */
	public static double round(double r, int dDigits, RoundingMode bigDecimalsRoundingMode) {
		BigDecimal rD = new BigDecimal(r);
		BigDecimal rRounded = rD.setScale(dDigits, bigDecimalsRoundingMode);
		return rRounded.doubleValue();
	}

	/**
	 * Checks if a given value is inside an allowed error margin.
	 * 
	 * @param allowedError_P Error margin in percent.
	 * @param rToTest        The value to be tested.
	 * @param rStandard      The value tested if it matches.
	 * @return True if the value to be tested matches. False if not.<br>
	 *         E.g. Given: 4.35, error margin is 2%. Value to be tested: 4.3<br>
	 *         Lower boundary: 4.3-4.3*(2/100)=4.21<br>
	 *         Upper boundary: 4.3*(1+(2/100))=4.38 => true!<br>
	 * 
	 */
	public static boolean isInRangeWithinPercentage(double allowedError_P, double rToTest, double rStandard) {
		double p = allowedError_P / 100;
		if (rToTest >= rStandard - (rStandard * p) && rToTest <= rStandard * (1 + p))
			return true;
		return false;
	}

	/**
	 * Error in percent between two values rounded to two decimal places using
	 * Java's {@link BigDecimal} class, RoundingMode.UP.
	 * 
	 * @param value1  The Greater value.
	 * @param value2  The smaller value.
	 * @param dDigits Decimal places.
	 * @return The error in percent, rounded to the specified decimal places.
	 */
	public static double getError_P(double value1, double value2, int dDigits) {

		double errorIn_P;

		if (value1 > value2) {
			errorIn_P = (100 - (value2 / value1) * 100);
			return MathHelper.round(errorIn_P, dDigits, RoundingMode.UP);
		}

		if (value1 < value2) {
			errorIn_P = (100 - (value1 / value2) * 100) * -1;
			return MathHelper.round(errorIn_P, dDigits, RoundingMode.UP);
		}
		return 0;
	}

	/**
	 * Determines the number of decimal places for the value of an resistor
	 * depending on the standard series it belongs to.
	 * 
	 * @param e Standard series the resistor belongs to (E3--E96).
	 * @return Number of decimal places used for the value of the resistor.
	 */
	public static int getDdigitsForSeries(double e) {
		if (e >= 48)
			return (2);
		else
			return (1);
	}
}
