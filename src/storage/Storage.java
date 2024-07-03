package storage;

import java.util.ArrayList;

public abstract class Storage 
{
	private ArrayList<String> values;
	
	/*
	 * Values added to storage shouldn't be removed rather in a class that extends this one
	 */
	
	public Storage (ArrayList<String> vals)
	{
		values = vals;
	}
	
	public Storage ()
	{
		values = new ArrayList<String>();
	}
	
	public ArrayList<String> getVals()
	{
		return values;
	}
	
	public void addVal(String val)
	{
		values.add(val);
	}
	
	public void addVal(int loc, String val)
	{
		values.add(loc, val);
	}
	
	//XJ71R is the flag term for problem receiving storage.
	public String getCertain(int loc)
	{
		return loc<values.size() ? values.get(loc) : "XJ71R";
	}
	
	public void printResults()
	{
		for (int i=0; i<values.size(); i++)
		{
			System.out.println((i+1)+ ": "+values.get(i));
		}
	}
}
