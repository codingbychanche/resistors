package Default;

import java.util.ArrayList;
import java.util.List;

import VoltageDiv.GetResistors;
import VoltageDiv.ResistorResult;

public class Main {
	public static void main(String args[]) {
			
		// Show al values for a given series...
		List <Double>rSeries=new ArrayList<>();
		rSeries=GetResistors.ofSeries(3);
		for (double r:rSeries)
			System.out.println(r);
		
		// Check if a given value with an given error margin in percent
		// can be found in any of the series E3..E96
		ResistorResult r=GetResistors.getRValueClosesdTo(335,2);
		
		if (r.found)
			System.out.println("Found:"+r.getResistorValue_Ohms()+" Ohms found in:"+r.getBelongsToESeries());
		else
			System.out.println("No matching standart value found");
		
	}
}
