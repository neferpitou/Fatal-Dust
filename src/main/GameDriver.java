package main;

import java.awt.*;
import screen.SimpleScreenManager;

public class GameDriver {

	final static int RESOLUTION_WIDTH = 1366;
	final static int RESOLUTION_HEIGHT = 768;
	final static int BIT_DEPTH = 24;
	
		public static void main(String[] args) {

			DisplayMode displayMode;

			if (args.length == 3) {
				displayMode = new DisplayMode(Integer.parseInt(args[0]),
						Integer.parseInt(args[1]), Integer.parseInt(args[2]),
						DisplayMode.REFRESH_RATE_UNKNOWN);
			} else {
				displayMode = new DisplayMode(RESOLUTION_WIDTH, RESOLUTION_HEIGHT, BIT_DEPTH,
						DisplayMode.REFRESH_RATE_UNKNOWN);
			}

			SimpleScreenManager test = new SimpleScreenManager();
			test.run(displayMode);
		}

	}
