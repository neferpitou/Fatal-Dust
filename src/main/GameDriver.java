package main;

import screen.LoadScreen;
import threads.LoadingThread;

public class GameDriver {

	public static void main(String[] args) {
		LoadScreen test = new LoadScreen("load-screen-1.gif", new LoadingThread());
		test.setDisplayMode(args);
		test.init();
	}

}
