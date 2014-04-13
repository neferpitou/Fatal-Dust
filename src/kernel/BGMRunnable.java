package kernel;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

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

	public BGMRunnable(String filename) {
		this.filename = filename;
		fk = FatalKernel.getInstance();
	}

	@Override
	public void run() {
		int BUFFER_SIZE = 4096;
		running = true;
		AudioInputStream audio = null;
		SourceDataLine auline = null;

		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(fk
					.loadResourceAsStream(filename));
			AudioFormat baseFormat = in.getFormat();
			AudioFormat decodedFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			audio = AudioSystem.getAudioInputStream(decodedFormat, in);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class,
					decodedFormat);
			auline = (SourceDataLine) AudioSystem.getLine(info);

			if (auline != null) {
				auline.open(decodedFormat);
				auline.start();

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

			auline.drain();
			auline.stop();
			auline.close();
			audio.close();

		} catch (IOException | UnsupportedAudioFileException
				| LineUnavailableException e) {
			e.printStackTrace();
		} finally {
			if (audio != null) {
				try {
					audio.close();
				} catch (IOException e) {
				}
			}
		}

	}

	public void halt() {
		running = false;
	}

}
