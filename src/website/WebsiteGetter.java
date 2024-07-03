package website;

import java.util.ArrayList;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

import gui.DialogBox;
import storage.WebStorage;

public class WebsiteGetter
{	
	private ArrayList<WebStorage> webScrapStorage;
	private String website, error;
	//Tells us how many WebStorages we need
	private ArrayList<String> connectionsNeeded;
	private UserAgent userAgent;
	
	private DialogBox textArea;
	
	public WebsiteGetter(ArrayList<String> web, String webste, DialogBox dialog)
	{
		connectionsNeeded = web;
		website = webste;
		textArea = dialog;
		
		error = "";
		
		webScrapStorage = new ArrayList<WebStorage>();
		makeWebStorages();
		establishConnection();
		if (error.equals(""))
		{
			findInfoandAdd();
			addBlankToMatch();
		}
	}
	
	public void establishConnection()
	{
		userAgent =  new UserAgent();
		try 
		{
			userAgent.visit(website);
			userAgent.settings.autoSaveAsHTML = true; //Uses last saved data from site visit (Important for time)
		} 
		catch (ResponseException e) 
		{
			e.printStackTrace();
			error = "URL can't be found or can't form internet connection";
		}		
	}
	
	//We assume webscrapStorage is already organzied
	public void makeWebStorages()
	{
		for (int i=0; i<connectionsNeeded.size(); i++)
		{
			webScrapStorage.add(new WebStorage());
		}
	}
	
	public void findInfoandAdd()
	{
		for (int i=0; i<connectionsNeeded.size(); i++)
		{
			System.out.println("Connection html: "+connectionsNeeded.get(i));
			if(connectionsNeeded.get(i).startsWith("before"))
			{	
				Elements found = userAgent.doc.findEvery(connectionsNeeded.get(i).substring(6));
				for (Element e : found)
				{
					/* Changed to get all text, regardless of color
					webScrapStorage.get(i).addVal(e.getChildText());
					 */
					int lengthStorage = webScrapStorage.get(i).getVals().size();
					if (e.getTextContent().equals("") && lengthStorage>0)
					{
						System.out.println("HIT!");
						webScrapStorage.get(i).addVal(webScrapStorage.get(i).getCertain(lengthStorage-1));
					}
					else
					{
						webScrapStorage.get(i).addVal(e.getTextContent());
					}
				}
			}
			else
			{
				Elements found = userAgent.doc.findEvery(connectionsNeeded.get(i));
				for (Element e : found)
				{
					/* Changed to get all text, regardless of color
					webScrapStorage.get(i).addVal(e.getChildText());
					 */
					webScrapStorage.get(i).addVal(e.getTextContent());
				}
			}
			webScrapStorage.get(i).printResults();
		}
	}
	
	//Blanks need to be added so length are the same for the keyWord 'same'
	public void addBlankToMatch()
	{
		int biggestSize = webScrapStorage.size()>0 ? webScrapStorage.get(0).getVals().size() : 0;
		int curSize = 0;
		for (int i=1; i<webScrapStorage.size(); i++)
		{
			curSize = webScrapStorage.get(i).getVals().size();
			if (curSize>biggestSize)
			{
				biggestSize = curSize;
			}
		}
		for (int i=0; i<webScrapStorage.size(); i++)
		{
			while (webScrapStorage.get(i).getVals().size()<biggestSize)
			{
				webScrapStorage.get(i).addVal("");
			}
		}
	}
	
	public ArrayList<WebStorage> getWebStorage()
	{
		return webScrapStorage;
	}
	
	public String getError()
	{
		return error;
	}
	
	public void setError(String val)
	{
		error = val;
	}
}
