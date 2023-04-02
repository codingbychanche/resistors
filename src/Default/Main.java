package Default;

import java.util.ArrayList;
import java.util.List;

import VoltageDiv.Divider;
import VoltageDiv.DividerResult;
import VoltageDiv.DividerResults;
import VoltageDiv.DrawResult;
import VoltageDiv.GetResistors;
import VoltageDiv.ResistorResult;
import VoltageDiv.ResistorResults;
import htmlBuilder.Table;

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
		System.out.println("Specific error margin: +/-" + rSeries.get(0).getSeriesSpecificErrorMargin() + "%");
		for (ResistorResult r : rSeries)
			System.out.println(r.getFoundResistorValue_Ohms() + " Ohms");

		System.out.println();

		//
		// Specify here, which standard series you wish to be excluded....
		//
		List<Integer> excludeSeries = new ArrayList();
		// excludeSeries.add(96);
		// excludeSeries.add(48);
		// excludeSeries.add(24);
		// excludeSeries.add(12);
		// excludeSeries.add(6);
		// excludeSeries.add(3);

		//
		// Show which standard series will not be used for the following solutions
		// displayed
		//
		System.out.println("The following results will not contain resistors of any of these standard series:");
		for (int s : excludeSeries)
			System.out.print("E" + s + ",");
		System.out.println();
		System.out.println();

		//
		// Check if a given value with an given error margin in percent
		// can be found in any of the series E3..E96
		//
		double resistorNeeded = 1563;
		double errorMarginAllowedIn_P = 25;

		System.out.println("Checking which standard value can be found closest to " + resistorNeeded + " Ohm");

		ResistorResults resultList = new ResistorResults();
		resultList = GetResistors.getRValueClosestTo(resistorNeeded, errorMarginAllowedIn_P, excludeSeries);

		if (resultList.hasNoSolution())
			System.out.println("No matching standard value found for:" + resistorNeeded + " Ohm");

		else {
			List<ResistorResult> results = resultList.getListOfResults();
			for (ResistorResult r : results) {
				System.out.println("Found:" + r.getFoundResistorValue_Ohms() + " Ohms . Actual error:"
						+ r.getActualError_P() + "% Series specific error margin +/-:"
						+ r.getSeriesSpecificErrorMargin() + "%    Found in Series E" + r.getESeries());
			}
		}
		
		System.out.println("Best solution:"+resultList.getBestMatchingResistor().getFoundResistorValue_Ohms()+" Ohm");

		System.out.println("");

		//
		// Voltage divider
		//
		// Try to find r1 and r2 for the given in- and output voltages.
		//
		double vIn_V = 5.5;
		double vOut_V = 3.4;
		int decimalPlaces = 3;

		DividerResults result = new DividerResults(vIn_V, vOut_V);
		result = Divider.findResistors(vIn_V, vOut_V, decimalPlaces, excludeSeries);
		System.out.println(
				"Voltage divider (First row shown is the best solution found, last row constitutes the poorest solution):");
		System.out.println("Input voltage=" + result.getInputVoltage_V() + "V.    Output voltage anticipated="
				+ result.getOutputVoltage_V());

		DrawResult.draw(result.getListOfResults().get(0));

		//
		// Show results, if any.....
		//
		List<DividerResult> listOfResults = new ArrayList<>();

		List<String> titleRow = new ArrayList();

		titleRow.add("<b>R1 found [&Omega;]</b>");
		titleRow.add("<b>R2 found [&Omega;]</b>");

		titleRow.add("<b>Vout anticipated</b>");
		titleRow.add("<b>Vout nominal</b>");

		titleRow.add("<b>Vout max [V]</b>");
		titleRow.add("<b>Error margin [V]<br><sub>Max <--> Min</sub></b>");
		titleRow.add("<b>Vout min [V]</b>");

		Table t = new Table("Divider Results", titleRow);

		if (result.hasResult()) {
			listOfResults = result.getListOfResults();

			//
			// Show result in plain text
			//
			for (DividerResult dr : listOfResults) {

				//
				// Plain text....
				//
				System.out.println("R1=" + dr.getR1_V() + " Ohm (E" + dr.getR1FoundInSeries() + ")  R2=" + dr.getR2_V()
						+ " Ohm (E" + dr.getR2FoundInSeries() + ")    Vout nominal=" + dr.getVoutNominal()
						+ "    Max output voltage:" + dr.getvOutMax_V() + "V  <------" + dr.getErrorMargin()
						+ "V ------> Min output voltag:" + dr.getvOutMin_V()
						+ "Min. deviation from output voltage anticipated:" + dr.getMinDeltaFromNominal());

				//
				// Create a nicer looking table in HTML....
				//
				List<String> oneResult = new ArrayList<>();

				oneResult.add((String.valueOf(dr.getR1_V()) + " E" + dr.getR1FoundInSeries()));
				oneResult.add((String.valueOf(dr.getR2_V()) + " E" + dr.getR2FoundInSeries()));
				oneResult.add(String.valueOf(dr.getVOutAnticipated()));
				oneResult.add(String.valueOf(dr.getVoutNominal()));
				oneResult.add((String.valueOf(dr.getvOutMax_V()) + "(" + dr.getDevFromMaxVoltage() + ")"));
				oneResult.add((String.valueOf(dr.getErrorMargin())));
				oneResult.add((String.valueOf(dr.getvOutMin_V()) + "(" + dr.getDevFromMinVoltage() + ")"));

				t.addDataRow(oneResult);
			}
		}
		System.out.println("");
		String html = t.createHtmlTable("-");
		System.out.println(html);

		//
		// Shows a list of all standard series which were not included when searching
		// for solutions for this divider...
		//
		List<Integer> excludedSeries = result.getListOfResults().get(0).getSeriesExcluded(); // Any results contains a
																								// list of excluded
																								// series....
		System.out.println("Series excluded:");
		for (int e : excludedSeries) {
			if (e != 0)
				System.out.print("E" + e + " ");
		}
	}
}
