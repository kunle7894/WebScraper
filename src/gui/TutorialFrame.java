package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import changer.Changer;

public class TutorialFrame extends Frame
{
private Changer changer;
	
	private JFrame frame;
	private JPanel panel;
	private JButton backButton;
	private JTextArea textArea;
	private JLabel label;
	
	public TutorialFrame(Changer change)
	{
		changer = change;
		frame = changer.getJFrame();
		//addButtons();
	}
	
	public void addButtons()
	{
		backButton = new JButton("Forex Factory");
		
		panel = new JPanel();
		panel.setLayout(null);
		
		backButton = new JButton("Back");
		backButton.setSize(70, 20);
		backButton.setLocation(20, 20);
		
		textArea = new JTextArea("https://www.youtube.com/watch?v=mvNeRwd1-bw");
		textArea.setSize(430,20);
		textArea.setEditable(false);
		textArea.setLocation(frame.getWidth()/2-textArea.getWidth()/2, frame.getHeight()/2-textArea.getHeight()/2);
		
		label = new JLabel("Put the following link in Google to see the tutorial");
		label.setSize(430, 20);
		label.setLocation(frame.getWidth()/2-textArea.getWidth()/2, frame.getHeight()/2-textArea.getHeight()/2-20);
		label.setOpaque(true);
		label.setBackground(Color.cyan);
				
		addActionListeners();
		panel.add(backButton);
		panel.add(textArea);
		panel.add(label);
		frame.add(panel);
		frame.show();
	}
	
	public void addActionListeners()
	{
		backButton.addActionListener(new ActionListener()
		 {
			public void actionPerformed(ActionEvent e)
			{
				frame.remove(panel);
				changer.setNeededFrame("mainFrame");
			}
		 });
	}
	
	public void removePanel()
	{
		frame.remove(panel);
	}
}
