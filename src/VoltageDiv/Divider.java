package VoltageDiv;

import java.util.List;

public class Divider {

	/**
	 * Finds a sries of resistors which meet the condition 
	 * for the given in- and output voltage of a divider.
	 * 
	 * The initial solution is found by assuming that R1=1 Ohm<p>
	 * By substituting R2 with the known ration between Vin and Vout (r)
	 * we find the following formula for R2:<br>
	 * [(1-r)/r] x R1 = R2 
	 */
	public static void findResistors(double vIn_V,double vOut_V) {
		double ratio=vOut_V/vIn_V;
		double r1Calc_Ohms=1;
		double r2Calc_Ohms=(1-ratio)/ratio;
		
		int lookUpR1InSeries=3;
		List <Double> series=GetResistors.ofSeries(lookUpR1InSeries);
		double maxTolErr_P=1; // max. tolerable error between R2 calculated and R2 found in any of the standard series.
		
		double rCalc;
		ResistorResult res;
		double r2_Ohms;
		double outputVoltage_V;
		for (double r1:series) {
			rCalc=r2Calc_Ohms*r1;
			
			// Check if r is availabe in any of the standard series
			res=GetResistors.getRValueClosesdTo(rCalc, maxTolErr_P);
			if (res.found()) {
				r2_Ohms=res.getFoundResistorValue_Ohms();
				
				outputVoltage_V=(r1/(r1+r2_Ohms))*vIn_V;
				
				System.out.println ("R1="+r1+" Ohms      R2="+r2_Ohms+" Ohms (Series E"+res.getBelongsToESeries()+
						")    Error:"+res.getActualError_P()+" (calc r2 was:"+rCalc+
						")    Resulting output voltage="+outputVoltage_V+" V");
			}
		}
		
	}
}
