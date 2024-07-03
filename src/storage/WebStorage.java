package storage;

import java.util.ArrayList;

public class WebStorage extends Storage
{
	private int curLoc;
	
	public WebStorage()
	{
		super();
		
		curLoc = 0;
	}
	public WebStorage(ArrayList<String> vals)
	{
		super(vals);
		
		curLoc = 0;
	}
	
	public String getCertain()
	{
		String needed = super.getCertain(curLoc);
		curLoc++;
		return needed;
	}
	
	public void resetLoc()
	{
		curLoc = 0;
	}
	
	public ArrayList<String> getValues()
	{
		return super.getVals();
	}
}
