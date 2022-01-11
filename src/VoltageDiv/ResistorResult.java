package VoltageDiv;

/**
 * Holds a result for a resistor value found inside
 * in any of the series E3..E96
 * 
 * @author Berthold
 *
 */
public class ResistorResult {
	public double resistorValue_Ohms;
	public int belongsToESeries;
	public boolean found;
	
	public ResistorResult(double resistorValue_Ohms, int belongsToESeries,boolean found) {
		super();
		this.resistorValue_Ohms = resistorValue_Ohms;
		this.belongsToESeries = belongsToESeries;
		this.found=found;
	}

	public double getResistorValue_Ohms() {
		return resistorValue_Ohms;
	}

	public void setResistorValue_Ohms(float resistorValue_Ohms) {
		this.resistorValue_Ohms = resistorValue_Ohms;
	}

	public int getBelongsToESeries() {
		return belongsToESeries;
	}

	public void setBelongsToESeries(int belongsToESeries) {
		this.belongsToESeries = belongsToESeries;
	}
	
	public boolean found() {return found;}
}


