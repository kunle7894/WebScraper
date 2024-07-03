package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import changer.Changer;
import excel.ExcelAcceser;
import storage.WebStorage;
import website.MultiWebsiteGetterForex;
import website.WebsiteGetter;

public class OtherFrame extends Frame
{
private Changer changer;
	
	private JPanel panel, dialogPanel;
	private JButton backButton, openDirectory, start;
	private JFrame frame;
	private JTable presetTable;
	private JLabel directory, excelFile, dataCollect, webName, error;
	private JTextArea websiteName;
	private boolean restart;
	
	private WebsiteGetter webGetter;
	
	private String saveDirectory, url;
	
	private ExcelAcceser excelWriter;
	
	private ArrayList<String> names;
	
	private DialogBox dialogBox;
	
	private Object[][] contents;
	
	public OtherFrame(Changer change)
	{
		changer = change;
		frame = changer.getJFrame();
		dialogBox = new DialogBox(frame);
	}
	
	public void addButtons()
	{
		panel = new JPanel();
		panel.setLayout(null);
		
        String[] headers = {"Name", "Location(Web)"};
		contents = new Object[][]
							  {
								{"Name", "HTML Location"},
								{"", ""},
								{"", ""},
								{"", ""},
								{"", ""},
								{"", ""},
								{"", ""},
								{"", ""},
								{"", ""},
								{"", ""},
								{"", ""},
								{"", ""},
								{"", ""},
								{"", ""}
							  };
		DefaultTableModel tableModel = new DefaultTableModel(contents, headers)
										{  
		      								public boolean isCellEditable(int row, int column)
		      								{  
		      									boolean edit = true;
		      									if (row==0)
		      									{
		      										edit = false;
		      									}
		      									return edit;  
		      								}  
										};  
		
		presetTable = new JTable(tableModel);
		presetTable.setBounds(50, 120, 700, 180);
		
		backButton = new JButton("Back");
		backButton.setSize(70, 20);
		backButton.setLocation(20, 20);
		
		openDirectory = new JButton("Open Directory");
		openDirectory.setSize(150, 20);
		openDirectory.setLocation(600, 80);
		
		webName = new JLabel("Website Address: ");
		webName.setSize(120, 20);
		webName.setLocation(50, 60);
		webName.setOpaque(true);
		webName.setBackground(Color.CYAN);
		
		url = "";
		websiteName = new JTextArea(url);
		
		websiteName.setSize(430,20);
		websiteName.setLocation(170, 60);
		
		excelFile = new JLabel("Excel File Location: ");
		excelFile.setSize(120, 20);
		excelFile.setLocation(50, 80);
		excelFile.setOpaque(true);
		excelFile.setBackground(Color.CYAN);
		
		saveDirectory = "";
		directory = new JLabel("");
		directory.setSize(430,20);
		directory.setLocation(170, 80);
		directory.setOpaque(true);
		directory.setBackground(Color.white);
		
		dataCollect = new JLabel("Data to be collected:");
		dataCollect.setSize(120, 20);
		dataCollect.setLocation(50, 100);
		dataCollect.setOpaque(true);
		dataCollect.setBackground(Color.CYAN);
				
		start = new JButton("Start Scrap");
		start.setSize(100, 50);
		start.setLocation(frame.getWidth()/2-50, 400);
		
		addActionListiner();
		panel.add(webName);
		panel.add(websiteName);
		panel.add(start);
		panel.add(dataCollect);
		panel.add(excelFile);
		panel.add(directory);
		panel.add(backButton);
		panel.add(openDirectory);
		panel.add(presetTable);
		frame.add(panel);
		frame.show();
		
		
		
	}
	
	public void addActionListiner()
	{
		openDirectory.addActionListener(new ActionListener()
		 {
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(fileChooser);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    String text = selectedFile.getAbsolutePath();
				    if (text.endsWith(".xls") || text.endsWith(".xlsx"))
				    {
				    	directory.setText(selectedFile.getAbsolutePath());
				    	saveDirectory = text;
				    }
				    //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
				}
			}
		 });
		start.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				url = websiteName.getText();
				if (saveDirectory.equals(""))
				{
					JOptionPane.showMessageDialog(frame, "No Save Directory Found", "Input Error", JOptionPane.ERROR_MESSAGE);
				}
				else if (url.equals(""))
				{
					JOptionPane.showMessageDialog(frame, "No URL can be Found", "Input Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					Thread thread = new Thread(dialogBox);
					thread.setPriority(Thread.MAX_PRIORITY);
					thread.start();
					thread.run();
					//dialogBox.createDialog();
					try {
						thread.join();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
					restart = false;
					
					SwingUtilities.invokeLater(new Runnable() 
												{
			            							@Override
			            							public void run() 
			            							{
			            								createWebScraper();
			            								if (!restart)
			            								{
			            									writeExcel();
			            								}
			            							}
												});
				}
			}
		 });
		backButton.addActionListener(new ActionListener()
		 {
			public void actionPerformed(ActionEvent e)
			{
				frame.remove(panel);
				changer.setNeededFrame("mainFrame");
			}
		 });
	}
	
	public void writeExcel()
	{
		createWrite();
		addInfoExcel();
		exportExcel();
		if (!excelWriter.getError().equals(""))
		{
			JOptionPane.showMessageDialog(frame, excelWriter.getError(), "Input Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(frame, "Finishied Without Problems!", "Input Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void createWrite()
	{
		excelWriter = new ExcelAcceser(saveDirectory);
	}
	
	public void createWebScraper()
	{
		ArrayList<String> vals = new ArrayList<String>();
		names = new ArrayList<String>();
		
		String tempContent;
		for (int i=0; contents.length>0 && i+1<contents.length; i++)
		{
			tempContent  = ""+presetTable.getValueAt(i+1, 1);
			if (!tempContent.equals(""))
			{
				System.out.println(""+presetTable.getValueAt(i+1, 1));
				vals.add(""+presetTable.getValueAt(i+1, 1));
				names.add(""+presetTable.getValueAt(i+1, 0));
				dialogBox.addInfo("Getting "+names.get(i)+"\n");
			}
		}
		/*
		vals.add("<td class=\"calendar__cell calendar__time time\">");
		vals.add("<td class=\"calendar__cell calendar__currency currency \">");
		vals.add("<span class=\"calendar__event-title\">");
		vals.add("<td class=\"calendar__cell calendar__forecast forecast\"");
		vals.add("<td class=\"calendar__cell calendar__actual actual\"");
		*/
		webGetter = new WebsiteGetter(vals, url, dialogBox);
		if (!webGetter.getError().equals(""))
		{
			JOptionPane.showMessageDialog(frame, webGetter.getError(), "Input Error", JOptionPane.ERROR_MESSAGE);
			restart = true;
		}
		/*
		names.add("Time");
		names.add("Currency");
		names.add("Title");
		names.add("Forecast");
		names.add("Actual");
		*/
	}
	
	//Adds information to Excel
	public void addInfoExcel()
	{
		ArrayList<WebStorage> webInfo = webGetter.getWebStorage();
		ArrayList<ArrayList<String>> infoTogether;
		infoTogether = new ArrayList<ArrayList<String>>();
		for (int i=0; i<webInfo.size(); i++)
		{
			webInfo.get(i).addVal(0, names.get(infoTogether.size()));
			infoTogether.add(webInfo.get(i).getValues());
		}
		excelWriter.addRowData(infoTogether);
	}
	
	public void exportExcel()
	{
		excelWriter.extractFile();
	}
	
	public void removePanel()
	{
		frame.remove(panel);
	}
}
