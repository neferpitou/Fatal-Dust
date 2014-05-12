package Main;
import Main.WindowFrame;

public class Main
{
	public static void main(String args[])
	{
		//This start the whole game
		
		//Loads up a window frame for user interaction
		WindowFrame w = new WindowFrame(100, 100, 500, 500, "Zeldish Game");
		w.run();
	}
}
