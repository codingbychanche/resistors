package VoltageDiv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Contains all results of the {@link GetResistors.getValueClosestTo} method.
 * 
 * @author Berthold
 *         <p>
 * 
 *         Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA
 *         4.0)
 *
 */
public class ResistorResults {

	private List<ResistorResult> listOfResults;

	public ResistorResults() {
		listOfResults = new ArrayList<>();
	}

	/**
	 * Ads a result for a resistor found to this instance.
	 * 
	 * @param resistorResult Instance of {@link ResistorResult} to be added.
	 */
	public void addResult(ResistorResult resistorResult) {
		this.listOfResults.add(resistorResult);
	}

	/**
	 * A list containing all matching resistors found.
	 * 
	 * @return A list of {@link ResistorResult} instances containing all matching
	 *         resistors.
	 */
	public List<ResistorResult> getListOfResults() {
		Collections.sort(listOfResults);
		return listOfResults;
	}

	/**
	 * The best solution found.
	 * 
	 * @return An instance of {@link ResistorResult} holding the best solution
	 *         found.
	 */
	public ResistorResult getBestMatchingResistor() {

		// Resistor closest to resistor searched is always on top of the
		// sorted list of results.
		Collections.sort(listOfResults);
		return listOfResults.get(0);
	}

	/**
	 * Checks, if the list is empty or not...
	 * 
	 * @return True if the list of solutions contains no entries...False, if the
	 *         list contains a least one entry.
	 */
	public boolean hasNoSolution() {
		if (listOfResults.isEmpty())
			return true;
		else
			return false;
	}
}
