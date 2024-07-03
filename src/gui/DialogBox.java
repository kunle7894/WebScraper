package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class DialogBox extends Thread
{
	private JFrame frame;
	private JDialog jDialog;
	private JTextArea textInformation;
	
	public DialogBox(JFrame fram)
	{
		frame = fram;
	}
	
	public void run()
	{
		createDialog();
	}
	
	public void createDialog()
	{
		jDialog = new JDialog(frame, "Events");
		
		jDialog.setSize(300, 200);
		
		textInformation = new JTextArea("Creating Excel File Based on data\nThis might take a while\n");
		textInformation.setEditable(false);
		textInformation.setSize(300, 200);
		textInformation.setLocation(0, 0);		
		jDialog.add(textInformation);
		
		jDialog.setResizable(false);				
		jDialog.setLocationRelativeTo(frame);
		jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
		jDialog.setFocusable(false);
		jDialog.setVisible(true);

	}
	
	public void addInfo(String info)
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				textInformation.append(info);
			}
		});
	}
}
