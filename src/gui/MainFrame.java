package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import changer.Changer;

public class MainFrame extends Frame
{
	private Changer changer;
	
	private JFrame frame;
	private JPanel panel;
	private JButton forexButton, otherButton, tutorialButton;
	private JLabel jLabel;
	
	public MainFrame(Changer change)
	{
		changer = change;
		frame = changer.getJFrame();
		//addButtons();
	}
	
	public void addButtons()
	{
		forexButton = new JButton("Forex Factory");
		otherButton = new JButton("Other");
		tutorialButton = new JButton("Tutorial");
		
		panel = new JPanel();
		jLabel = new JLabel("WebScraper");
		panel.setLayout(null);
		
		jLabel.setSize(300, 50);
		jLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
		jLabel.setLocation(changer.getJFrame().getWidth()/2-90, 50);
		
		forexButton.setSize(120, 80);
		forexButton.setLocation(changer.getJFrame().getWidth()/2-60, 170);
		otherButton.setSize(120, 80);
		otherButton.setLocation(changer.getJFrame().getWidth()/2-60, 300);
		tutorialButton.setSize(120, 80);
		tutorialButton.setLocation(changer.getJFrame().getWidth()/2-60, 430);
	
		addActionListeners();
		panel.add(forexButton);
		panel.add(otherButton);
		panel.add(tutorialButton);
		panel.add(jLabel);
		frame.add(panel);
		frame.show();
	}
	
	public void addActionListeners()
	{
		forexButton.addActionListener(new ActionListener()
	    							  {
		      							public void actionPerformed(ActionEvent e)
		      							{
		      								frame.remove(panel);
		      								changer.setNeededFrame("forexFrame");
		      							}
	    							  });
		otherButton.addActionListener(new ActionListener()
		                              {
										public void actionPerformed(ActionEvent e)
										{
											frame.remove(panel);
											changer.setNeededFrame("otherFrame");
										}
		                              });
		tutorialButton.addActionListener(new ActionListener()
		  							     {
											public void actionPerformed(ActionEvent e)
											{
												frame.remove(panel);
												changer.setNeededFrame("tutorialFrame");
											}
		  							     });
	}
	
	public void removePanel()
	{
		frame.remove(panel);
	}
}
