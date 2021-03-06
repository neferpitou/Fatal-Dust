package factory;

import java.awt.Image;

import characters.AyakoTurner;
import characters.MalMartinez;
import characters.VanillaCharacter;
import kernel.FatalKernel;

/**
 * A factory class that makes creating instances of characters and stages
 * easier.
 * 
 * @author Marcos Davila
 *
 */
public class FatalFactory {

	public static final int TOTAL_NUM_STAGES = 7;
	private static FatalKernel kernel = FatalKernel.getInstance();
	
	// Creates a new character of the desired character type
	public static VanillaCharacter spawnCharacter(CharacterType c, boolean isPlayerOne){
		VanillaCharacter character = null;

		switch (c) {
		case AyakoTurner:
			character = new AyakoTurner(isPlayerOne);
			break;
		case MalMartinez:
			character = new MalMartinez(isPlayerOne);
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
			imgName = "DragonBackground.gif";
			break;
		case 4:
			imgName = "FireBackground.gif";
			break;
		case 5:
			imgName = "mal_stage.gif";
			break;
		case 6:
			imgName = "outsideStoneHouseBackground.gif";
			break;
		case 7:
			imgName = "PalaceBackground.gif";
			break;
		default:
			imgName = "mal_stage.gif";
			break;
		}
		
		return kernel.loadImage(imgName);
	}
}