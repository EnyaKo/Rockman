import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;


public class Sound_effect extends JFrame {
	private Clip clip = null;
	private boolean playing;
	private String file_name;
	private AudioInputStream audioInputStream;
	Sound_effect(String file_name){
		playing=false;
		this.file_name=file_name;
		File sound=new File(this.file_name);
		audioInputStream = null;


		
		try {
			audioInputStream = AudioSystem.getAudioInputStream(sound);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		AudioFormat audioFormat = audioInputStream.getFormat();

		int bufferSize = (int) Math.min(audioInputStream.getFrameLength() * audioFormat.getFrameSize(), Integer.MAX_VALUE); //蝺抵?憭批?嚗??閮?獢?憭改??臭誑?券摮蝺抵?蝛粹???潭?閰脰???券?瘙箏?
		DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, audioFormat, bufferSize);

		try {
			clip = (Clip) AudioSystem.getLine(dataLineInfo);
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void play(){
		clip.start();
		playing=true;
	}
	public boolean is_play(){
		return playing;
	}
	public void reset_play(){
		File sound=new File(file_name);
		try {
			audioInputStream = AudioSystem.getAudioInputStream(sound);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		AudioFormat audioFormat = audioInputStream.getFormat();

		int bufferSize = (int) Math.min(audioInputStream.getFrameLength() * audioFormat.getFrameSize(), Integer.MAX_VALUE); //蝺抵?憭批?嚗??閮?獢?憭改??臭誑?券摮蝺抵?蝛粹???潭?閰脰???券?瘙箏?
		DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, audioFormat, bufferSize);

		try {
			clip = (Clip) AudioSystem.getLine(dataLineInfo);
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void loop(int count){
		clip.loop(count);
	}
	public void loopforever(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void volume(int v){
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(v);
	}
	public void stop(){
		clip.stop();
	}
	public boolean isplaying(){
		return clip.isRunning();
	}
}
