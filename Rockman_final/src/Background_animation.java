import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Background_animation extends JFrame implements Runnable{
	private BufferedImage[] backgroundImage;
	private BufferedImage readImage;
	private int background_change;
	private long start_time;
	private long end_time;
	private long elapsed_time;
	private boolean running;
	private File f;
	public Background_animation(){
		
		this.reset_background_change();
		running=false;
		this.set_startTime();
		backgroundImage=new BufferedImage[20];
		for(int i=0;i<20;i++){
		try{
			f=new File("background/"+i+".png");
			backgroundImage[i]=ImageIO.read(f);
			//readImage=getImage(getClass().getResource(rockman_file));

		}catch(Exception e){
			e.printStackTrace();
			backgroundImage=null;
		}
		}
		
	}
	public void run(){
		this.set_startTime();
		this.reset_background_change();
		running=true;
		while(running){
			end_time=System.currentTimeMillis();
    		elapsed_time=end_time-start_time;
    		if(elapsed_time>=1000){
    			background_change++;
    			start_time=System.currentTimeMillis();
    		}
    		if(background_change>19){
    			background_change=0;
    			}
    		/*
    		try{
    			f=new File("background/"+background_change+".png");
    			backgroundImage=ImageIO.read(f);
    			//readImage=getImage(getClass().getResource(rockman_file));

    		}catch(Exception e){
    			e.printStackTrace();
    			backgroundImage=null;
    		}
    		*/
    		readImage=backgroundImage[background_change];
    		try {
    			Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public int get_background_change(){
    	return background_change;
    }
	public void reset_background_change(){
		background_change=0;
	}
	public void set_startTime(){
    	start_time=System.currentTimeMillis();
    }
	public BufferedImage get_image(){
		return readImage;
	}
	
    public void stop()
    {
        this.running = false;
    }
}

