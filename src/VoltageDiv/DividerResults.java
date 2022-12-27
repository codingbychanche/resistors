package VoltageDiv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Contains all results of the {@link Divider.findResistors} method.
 * 
 * @author Berthold
 *         <p>
 * 
 *         Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA
 *         4.0)
 *
 */
public class DividerResults {
	private boolean hasResult;
	private double inputVoltage_V, outputVoltage_V;
	private List<DividerResult> listOfResults;
	private Long timeItTookIn_ms;

	/**
	 * Contains any results found by the {@link Divider.findResistors} method.
	 * 
	 * @param inutVoltage_V   The input voltage for the divider in volts.
	 * @param outputVoltage_V The output voltage in volts.
	 * @param inputVoltage_V
	 */
	public DividerResults(double inputVoltage_V, double outputVoltage_V) {
		listOfResults = new ArrayList<>();
		hasResult = false;
		this.inputVoltage_V = inputVoltage_V;
		this.outputVoltage_V = outputVoltage_V;
	}

	/**
	 * Adds one result to this instance.
	 * 
	 * @param dividerResult Instance of the {@link DivederResult} class.
	 */
	public void addResult(DividerResult dividerResult) {
		this.listOfResults.add(dividerResult);
		hasResult = true;
	}

	/**
	 * Gets the list of results.
	 * 
	 * @return A {@link List} of results of a calculated voltage divider containing
	 *         one or more instances of {@link DividerResult}.<br>
	 *         The list is sorted by the error between calculated output voltage
	 *         resulting from the chosen resistors and the output voltage wished.
	 *         <p>
	 *         R1 and R2 with smallest resulting error in output voltage is the
	 *         first element in this list.
	 * 
	 */
	public List<DividerResult> getListOfResults() {
		Collections.sort(listOfResults);
		return listOfResults;
	}

	/**
	 * Return the solution with the smallest error in output voltage. Since the list
	 * is in ascending order, the first element is returned.
	 * 
	 * @return A instance of {@link DividerResult} with the smallest error in output
	 *         voltage
	 */
	public DividerResult getSolutionWsmallestErrInOutputVoltage() {
		return this.listOfResults.get(0);
	}

	/**
	 * Flag declaring if this instance contains at least one result.
	 * 
	 * 
	 * @return True if at least one instance of {@link DividerResult} was added to
	 *         this instance. False if not.
	 */
	public boolean hasResult() {
		return hasResult;
	}

	public double getInputVoltage_V() {
		return inputVoltage_V;
	}

	public void setInputVoltage_V(double inputVoltage_V) {
		this.inputVoltage_V = inputVoltage_V;
	}

	public double getOutputVoltage_V() {
		return outputVoltage_V;
	}

	public void setOutputVoltage_V(double outputVoltage_V) {
		this.outputVoltage_V = outputVoltage_V;
	}

	public Long getTimeItTookIn_ms() {
		return timeItTookIn_ms;
	}

	public void setTimeItTookIn_ms(Long timeItTookIn_s) {
		this.timeItTookIn_ms = timeItTookIn_s;
	}
}
