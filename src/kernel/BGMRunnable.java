package kernel;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BGMRunnable implements Runnable {
	
	private String filename;
	private FatalKernel fk;
	private volatile boolean running;
	
	public BGMRunnable(String filename){
		this.filename = filename;
		fk = FatalKernel.getInstance();
	}

	@Override
	public void run() {
		int BUFFER_SIZE = 256;
		running = true;
		AudioInputStream audio = null;
		SourceDataLine auline = null;
		
		try {
			audio = AudioSystem.getAudioInputStream(fk
					.loadResourceAsStream(filename));
			AudioFormat format = audio.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
			auline.start();
		} catch (IOException | UnsupportedAudioFileException
				| LineUnavailableException e) {
			e.printStackTrace();
		}

		int nBytesRead = 0;
		byte[] abData = new byte[BUFFER_SIZE];
		
		while (nBytesRead != -1 && running) {
			try {
				nBytesRead = audio.read(abData, 0, abData.length);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (nBytesRead >= 0) {
				auline.write(abData, 0, nBytesRead);
			}
		}

	}
	
	public void halt(){
		running = false;
	}

}
