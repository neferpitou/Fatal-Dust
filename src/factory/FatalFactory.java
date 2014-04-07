package factory;

import java.awt.Image;

import kernel.FatalKernel;
import objects.VanillaCharacter;

/**
 * A factory class that makes creating instances of characters and stages
 * easier.
 * 
 * @author Marcos Davila
 *
 */
public class FatalFactory {

	public static final int TOTAL_NUM_STAGES = 8;
	private static FatalKernel kernel = FatalKernel.getInstance();
	
	// Creates a new character of the desired character type
	public static VanillaCharacter spawnCharacter(CharacterType c, boolean isPlayerOne, int x, int y){
		VanillaCharacter character = null;

		switch (c) {
		case AyakoTurner:
			character = new VanillaCharacter(isPlayerOne, CharacterType.AyakoTurner, x, y);
			break;
		case MalMartinez:
			character = new VanillaCharacter(isPlayerOne, CharacterType.MalMartinez, x, y);
			break;
		default:
			// TODO: throw some exception
			break;
		}
		
		return character;
	}
	
	// Chooses a stage based on the number provided
	public static Image setStage(int numStage){
		String imgName = "";
		
		switch(numStage){
		case 0:
			imgName = "AsianFireBackground.gif";
			break;
		case 1:
			imgName = "ayako_stage.gif";
			break;
		case 2:
			imgName = "BlueWaterfallBackground.gif";
			break;
		case 3:
			imgName = "DogsBackground.gif";
			break;
		case 4:
			imgName = "DragonBackground.gif";
			break;
		case 5:
			imgName = "FireBackground.gif";
			break;
		case 6:
			imgName = "mal_stage.gif";
			break;
		case 7:
			imgName = "outsideStoneHouseBackground.gif";
			break;
		case 8:
			imgName = "PalaceBackground.gif";
			break;
		default:
			imgName = "mal_stage.gif";
			break;
		}
		
		return kernel.loadImage(imgName);
	}
}