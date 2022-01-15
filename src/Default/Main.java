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
 * @author Berthold<p>
 * 
 * Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA 4.0) 
 *
 */
public class Main {
	public static void main(String args[]) {
		//
		// Show all standard values for a given series...
		//
		List <Double>rSeries=new ArrayList<>();
		int eSeries=6;
		rSeries=GetResistors.ofSeries(eSeries);
		
		System.out.println("Series "+eSeries);
		for (double r:rSeries)
			System.out.println(r+" Ohms");
		
		System.out.println();
		//
		// Check if a given value with an given error margin in percent
		// can be found in any of the series E3..E96
		//
		ResistorResult r=GetResistors.getRValueClosestTo(93.67,5);
		
		if (r.found)
			System.out.println("Trying to find value closest to:"+r.getGivenResistorValue_Ohms() +" Ohms.   Found:"+r.getFoundResistorValue_Ohms()+" Ohms . Actual error:"+r.getActualError_P() +"%    Found in Series E"+r.getBelongsToESeries());
		else
			System.out.println("No matching standard value found");
		
		System.out.println("");
		//
		// Voltage divider
		//
		// Try to find r1 and r2 for the given in- and output voltages.
		double vIn_V=15.56;
		double vOut_V=1.79;
		double tolarableErrorForR2_P=0.009;
		
		DividerResults result=new DividerResults(vIn_V,vOut_V,tolarableErrorForR2_P);
		result=Divider.findResistors(vIn_V,vOut_V,tolarableErrorForR2_P);
		System.out.println("Input voltage="+result.getInputVoltage_V()+"V.    Output voltage="+result.getOutputVoltage_V()+" tolarable error for r2:"+result.getTolarableErrorForR2_P()+"%");
		
		// Show results, if any.....
		List <DividerResult> listOfResults=new ArrayList<>();
		if (result.hasResult()) {
			listOfResults=result.getListOfResults();
			for (DividerResult dr: listOfResults) {
				System.out.println("R1="+dr.getR1_V()+" Ohm (E"+dr.getR1FoundInSeries()+
						")  R2="+dr.getR2_V()+" Ohm (E"+dr.getR2FoundInSeries()+"  deviation from calculated:"+dr.getErrorForR2_P()+"%)"+
						" Resulting Output voltage:"+dr.getvOutCalc_V());
			}
		} else {
			System.out.println("For the allowed deviation of r2 between the calculated and the standard value (you chose:"+tolarableErrorForR2_P+"%) was no solution found.");
		}
	}
}
