package Main;

import java.awt.*;
import java.awt.event.*;

public class WindowFrame
{
	//class variables containing positions and names
	private int x;
	private int y;
	private int width;
	private int height;
	private String name;

	WindowFrame(int x, int y, int width, int height, String name)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
	}

	public void run()
	{
		//creates the game applet and initializes it
		Drawing applet = new Drawing();

		Frame frame = new Frame();
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		//sets the window frames applet to be viewed
		frame.add(applet);
		frame.setTitle(this.name);
		frame.setLocation(this.x, this.y);
		frame.setSize(this.width, this.height);
		frame.setVisible(true);

		applet.run();
	}
}
