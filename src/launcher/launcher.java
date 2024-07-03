package launcher;

import javax.swing.SwingUtilities;

import changer.Changer;

public class launcher 
{
	private static Changer changer;
	
	public static void main(String[] args)
	{
		/*
		 * Used to not create an instance of the program on the current thread, but on the Event Dispatch Thread(EDT). Trying to stop future error.
		 * https://alvinalexander.com/java/jbutton-listener-pressed-actionlistener
		 */
		SwingUtilities.invokeLater(new Runnable()
	    {
	      public void run()
	      {
	    	  changer = new Changer(800, 600);
	      }
	    });
		
	}
}
