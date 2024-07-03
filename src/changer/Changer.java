package changer;

import javax.swing.JFrame;

import gui.ForexFactoryFrame;
import gui.Frame;
import gui.MainFrame;
import gui.OtherFrame;
import gui.TutorialFrame;

public class Changer 
{
	private Frame frame;
	private MainFrame mainFram;
	private ForexFactoryFrame forexFram;
	private OtherFrame otherFrame;
	private TutorialFrame tutorialFrame;
	private String neededFrame;
	
	public Changer(int wih, int hgh)
	{
		neededFrame = "mainFrame";
		frame = new Frame(wih, hgh, this);
		mainFram = new MainFrame(this);
		forexFram = new ForexFactoryFrame(this);
		otherFrame = new OtherFrame(this);
		tutorialFrame = new TutorialFrame(this);
		
		changeDraw();
	}
	
	public void changeDraw()
	{
		if (neededFrame.equals("mainFrame"))
		{
			frame.setFrame(mainFram);
			frame.getFrame().addButtons();
		}
		if (neededFrame.equals("forexFrame"))
		{
			frame.setFrame(forexFram);
			frame.getFrame().addButtons();
		}
		if (neededFrame.equals("otherFrame"))
		{
			frame.setFrame(otherFrame);
			frame.getFrame().addButtons();
		}
		if (neededFrame.equals("tutorialFrame"))
		{
			frame.setFrame(tutorialFrame);
			frame.getFrame().addButtons();
		}
	}
	
	public void setNeededFrame(String val)
	{
		neededFrame = val;
		changeDraw();
	}
	
	public String getFrame()
	{
		return neededFrame;
	}
	
	public JFrame getJFrame()
	{
		return frame.getJframe();
	}
}
