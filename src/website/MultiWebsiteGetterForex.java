package website;

import java.util.ArrayList;

import javax.swing.JTextArea;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.Elements;

import gui.DialogBox;
import storage.WebStorage;

public class MultiWebsiteGetterForex 
{
	private ArrayList<String> items;
	private String website, chromeDriver;
	
	private ChromeOptions options;
	
	private DialogBox jTextBox;
	
	private ArrayList<WebStorage> storageSyst;
	
	public MultiWebsiteGetterForex(ArrayList<String> vals, String wsite, String chromeLoc, DialogBox dialog)
	{
		items = vals;
		website = wsite;
		chromeDriver = chromeLoc;
		jTextBox = dialog;
		
		init();
	}
	
	public void init()
	{
		options = new ChromeOptions();                //create chrome options object
		options.addArguments("--headless");  
		
		Browser userAgent;
		System.setProperty("webdriver.chrome.driver", chromeDriver);
		userAgent = new Browser(new ChromeDriver(options));
		userAgent.visit(website);
 		//userAgent.openContent("<tr class=\"calendar__row calendar_row calendar__row--grey calendar__row--new-day newday calendar__row--expanded\" data-eventid=\"112899\" data-touchable>");
		
		storageSyst = new ArrayList<>();
		storageSyst.add(new WebStorage());
		storageSyst.add(new WebStorage());
		storageSyst.add(new WebStorage());
		storageSyst.add(new WebStorage());
		
		System.out.println(userAgent.getLocation());
		
		//Elements found = userAgent.doc.findEvery("<td class=\"full calendarspecs__specdescription\"");
		String fileContent = userAgent.doc.outerHTML();
	    
		userAgent.quit();
	    ArrayList<String> vals = addTester(fileContent);
	    vals = purgeSim(vals);
	    for (int i=0; i<vals.size(); i++)
	    {
	    	readTestData(vals.get(i), website+"/#detail=");
	    }
	}
	
	public int findSpotOccur(String stuff, String start)
	{
		int foundPos = -99;
		for (int i=0; i<stuff.length()-start.length() && foundPos==-99; i++)
		{
			if (stuff.substring(i, i+start.length()).equals(start))
			{
				foundPos = i+start.length();
			}
		}
		return foundPos;
	}
	
	public ArrayList<String> purgeSim(ArrayList<String> vals)
	{
		ArrayList<String> nedVals = new ArrayList<>();
		if (vals.size()>0)
		{
			nedVals.add(vals.get(0));
		}
		for (int i=1; i<vals.size(); i++)
		{
			if (!vals.get(i).equals(vals.get(i-1)))
			{
				nedVals.add(vals.get(i));
			}
		}
		return nedVals;
	}
	
	public ArrayList<String> addTester(String fileContent)
	{
		ArrayList<String> subPages = new ArrayList<String>();
		String first = fileContent;
		int occur = findSpotOccur(first, "data-eventid=\"");
		
		while (occur!=-99 && occur+6<first.length())
		{
			first = first.substring(occur+6);
			occur = findSpotOccur(first, "data-eventid=\"");
			if (occur!=-99 && !first.substring(occur, occur+6).contains("data"))
			{
				subPages.add(first.substring(occur, occur+6));
			}
		}
		return subPages;
	}
	
	public void readTestData(String add, String source)
	{
		String newSource = source+add;
		System.out.println(newSource);
		
		Browser userAgent = new Browser(new ChromeDriver(options));
		userAgent.visit(newSource);
		//Cur is used to monitior which value in the arraylist is used
		int cur = 0;
		
		for (int i=0; i<items.size(); i++)
		{
			Elements found = userAgent.doc.findEvery(items.get(i));
			String[] info = {"'Actual'", "More", "No consi"};
			boolean foundOne = false;
			
			for (Element e : found)
			{
				if (i==0 && checkInfo(e.getChildText(), info))
				{
					foundOne = true;
					storageSyst.get(cur).addVal(e.getChildText());
					cur++;
				}
				else if (i!=0 && cur<storageSyst.size())
				{
					storageSyst.get(cur).addVal(e.getTextContent());
					cur++;
				}
			}
			
			/* 
			 * Used for Actuals. If they don't exist
			 */
			if (!foundOne && i==0)
			{
				storageSyst.get(0).addVal("");
			}
		}
		userAgent.quit();
	}
	
	public boolean checkInfo(String find, String[] info)
	{
		boolean found = false;
		for (int i=0; i<info.length && !found; i++)
		{
			if (info[i].length()<=find.length() && find.substring(0, info[i].length()).equals(info[i]))
			{
				found = true;				
			}
		}
		return found;
	}
	
	public ArrayList<WebStorage> getWebStorage()
	{
		return storageSyst;
	}
}

