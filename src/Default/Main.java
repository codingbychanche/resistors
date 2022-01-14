package Default;

import java.util.ArrayList;
import java.util.List;

import VoltageDiv.Divider;
import VoltageDiv.GetResistors;
import VoltageDiv.ResistorResult;

public class Main {
	public static void main(String args[]) {
			
		// Show al values for a given series...
		List <Double>rSeries=new ArrayList<>();
		int eSeries=96;
		rSeries=GetResistors.ofSeries(eSeries);
		
		System.out.println("Series "+eSeries);
		for (double r:rSeries)
			System.out.println(r+" Ohms");
		
		System.out.println();
		
		// Check if a given value with an given error margin in percent
		// can be found in any of the series E3..E96
		ResistorResult r=GetResistors.getRValueClosesdTo(93.67,1);
		
		if (r.found)
			System.out.println("Trying to find value closest to:"+r.getGivenResistorValue_Ohms() +" Ohms.   Found:"+r.getFoundResistorValue_Ohms()+" Ohms . Actual error:"+r.getActualError_P() +"%    Found in Series E"+r.getBelongsToESeries());
		else
			System.out.println("No matching standard value found");
		
		System.out.println("");
		
		// Voltage divider
		double vIn_V=8.5;
		double vOut_V=5.3;
		System.out.println("Input voltage="+vIn_V+"V.    Output voltage="+vOut_V);
		Divider.findResistors(vIn_V,vOut_V,5);
		
	}
}
