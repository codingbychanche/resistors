package VoltageDiv;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all results of the {@link Divider.findResistors} method.
 * 
 * @author Berthold
 *
 */
public class DividerResults {
	private boolean hasResult;
	private List<DividerResult> listOfResults;

	public DividerResults() {
		listOfResults = new ArrayList<>();
		hasResult = false;
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
}
