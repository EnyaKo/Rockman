import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Warning extends JFrame implements Runnable {
	private boolean running;
	private boolean onplay;
	private int warning_change;
	private BufferedImage warningImage[];
	private BufferedImage readImage;
	private long start_time;
	private long end_time;
	private long elapsed_time;
	private Sound_effect e1;
	private File f;
	public Warning(){
		running=true;
		warning_change=0;
		onplay=false;
		warningImage=new BufferedImage[90];
		for(int i=0;i<90;i++){
		try{
			f=new File("warning/"+i+".png");
			warningImage[i]=ImageIO.read(f);
		}catch(Exception e){
			e.printStackTrace();
			warningImage=null;
		}
		}
		e1=new Sound_effect("sound_effect/warning.wav");
		
	}
	
	
	public void run(){
		while(running){
			end_time=System.currentTimeMillis();
			elapsed_time=end_time-start_time;
			/*
			try{
				f=new File("warning/"+warning_change+".png");
				warningImage=ImageIO.read(f);

			}catch(Exception e){
				e.printStackTrace();
				warningImage=null;
			}
			*/
			readImage=warningImage[warning_change];
			
			if(elapsed_time>=60){
				warning_change++;
				set_startTime();
				}
			if(warning_change==3 && !onplay){
				e1.loop(5);
				onplay=true;
			}
			if(warning_change>89){
				running=false;
				readImage=null;
			}
		}
	}
	public BufferedImage get_image(){
		return readImage;
	}
	public int get_warning_change(){
		return warning_change;
	}
	public void set_startTime(){
	    	start_time=System.currentTimeMillis();
	    }
	public void set_running(){
		running=true;
	}
	public void stop(){
		running=false;
	}
}
