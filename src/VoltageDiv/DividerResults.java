package VoltageDiv;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all results of the {@link Divider.findResistors} method.
 * 
 * @author Berthold<p>
 * 
 * Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA 4.0) 
 *
 */
public class DividerResults {
	private boolean hasResult;
	private double inputVoltage_V,outputVoltage_V;
	private double tolarableErrorForR2_P; 
	private List<DividerResult> listOfResults;

	/**
	 * Contains any results found by the {@link Divider.findResistors} method.
	 * 
	 * @param inutVoltage_V The input voltage for the divider in volts.
	 * @param outputVoltage_V The output voltage in volts.
	 * @param tolarableErrorForR2_P Tolarable deviation between the calculated value for r2 and the found standard value.
	 * @param inputVoltage_V 
	 */
	
	public DividerResults(double inputVoltage_V,double outputVoltage_V,double tolarableErrorForR2_P) {
		listOfResults = new ArrayList<>();
		hasResult = false;
		this.inputVoltage_V=inputVoltage_V;
		this.outputVoltage_V=outputVoltage_V;
		this.tolarableErrorForR2_P=tolarableErrorForR2_P;
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
	 *         one or more instances of {@link DividerResult}.
	 */
	public List<DividerResult> getListOfResults() {
		return listOfResults;
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

	public double getTolarableErrorForR2_P() {
		return tolarableErrorForR2_P;
	}

	public void setTolarableErrorForR2_P(double tolarableErrorForR2_P) {
		this.tolarableErrorForR2_P = tolarableErrorForR2_P;
	}
	
	
}
