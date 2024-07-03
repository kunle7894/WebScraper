package gui;

import java.awt.GraphicsConfiguration;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import changer.Changer;

public class Frame 
{
	private Frame frame;
	
	private JFrame jframe;
	
	private int width, height;
	
	private GraphicsConfiguration g;
	
	private Changer changer;
	
	public Frame(int wid, int hgh, Changer change)
	{
		width = wid;
		height = hgh;
		
		changer = change;
		
		createFrame();
	}
	
	public Frame()
	{
		
	}
	
	public void createFrame()
	{
		jframe = new JFrame(g);
		
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jframe.setTitle("Web Scraper");
		jframe.setSize(width, height);
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.setVisible(true);
		jframe.setLocationRelativeTo(null);
	}
	
	public void addButtons()
	{
		
	}
	
	public Frame getFrame()
	{
		return frame;
	}
	
	public void setFrame(Frame fram)
	{
		frame = fram;
	}
	
	public JFrame getJframe()
	{
		return jframe;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getWidth()
	{
		return width;
	}
}
