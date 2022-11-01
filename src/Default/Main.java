package Default;

import java.util.ArrayList;
import java.util.List;

import VoltageDiv.Divider;
import VoltageDiv.DividerResult;
import VoltageDiv.DividerResults;
import VoltageDiv.GetResistors;
import VoltageDiv.ResistorResult;

/**
 * Demo for the {@link VoltageDiv}- class.
 * 
 * @author Berthold
 *         <p>
 * 
 *         Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA
 *         4.0)
 *
 */
public class Main {
	public static void main(String args[]) {

		//
		// Show all standard values for a given series...
		//
		List<ResistorResult> rSeries = new ArrayList<>();
		int eSeries = 6;
		rSeries = GetResistors.ofSeries(eSeries);

		System.out.println("Series " + eSeries);
		for (ResistorResult r : rSeries)
			System.out.println(r.getFoundResistorValue_Ohms() + " Ohms");

		System.out.println();
		//
		// Check if a given value with an given error margin in percent
		// can be found in any of the series E3..E96
		//
		List<Integer> listOfSeriesExcluded = new ArrayList();
		// excludeSeries.add(3);
		// excludeSeries.add(6);

		ResistorResult r = GetResistors.getRValueClosestTo(489, 1.5, listOfSeriesExcluded);

		if (r.found())
			System.out.println("Trying to find value closest to:" + r.getGivenResistorValue_Ohms() + " Ohms.   Found:"
					+ r.getFoundResistorValue_Ohms() + " Ohms . Actual error:" + r.getActualError_P()
					+ "% Series specific error margin +/-:" + r.getSeriesSpecificErrorMargin()
					+ "%    Found in Series E" + r.getESeries());
		else
			System.out.println("No matching standard value found");

		System.out.println("");
		
		//
		// Voltage divider
		//
		// Try to find r1 and r2 for the given in- and output voltages.
		
		double vIn_V = 3.5;
		double vOut_V = .85;

		DividerResults result = new DividerResults(vIn_V, vOut_V);
		result = Divider.findResistors(vIn_V, vOut_V, listOfSeriesExcluded);
		System.out.println(
				"Input voltage=" + result.getInputVoltage_V() + "V.    Output voltage anticipated=" + result.getOutputVoltage_V());

		// Show results, if any.....
		List<DividerResult> listOfResults = new ArrayList<>();
		if (result.hasResult()) {
			listOfResults = result.getListOfResults();
			for (DividerResult dr : listOfResults) {
				System.out.println("R1=" + dr.getR1_V() + " Ohm (E" + dr.getR1FoundInSeries() + ")  R2=" + dr.getR2_V()
						+ " Ohm (E" + dr.getR2FoundInSeries() + ")" + " Max output voltage:" + dr.getvOutMax_V()
						+ "V Min output voltag:" + dr.getvOutMin_V() + "V");
			}
		}
	}
}
