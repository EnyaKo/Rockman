import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Door extends JFrame implements Runnable {
	private BufferedImage doorImage[];
	private BufferedImage readImage;
	private int width;
	private int height;
	private int door_change;
	private File f;
	private long start_time;
	private long end_time;
	private long elapsed_time;
	private boolean state;
	private boolean running;
	private boolean onplay;
	private Sound_effect e1;
	private Sound_effect e2;
	public Door(int width,int height){
		state=true;
		running=false;
		onplay=false;
		this.width=width;
		this.height=height;
		door_change=0;
		doorImage=new BufferedImage[26];
		this.set_startTime();
		e1=new Sound_effect("sound_effect/door_open1.wav");
		e2=new Sound_effect("sound_effect/door_open2.wav");
		for(int i=0;i<26;i++){
		try{
			f=new File("door/"+i+".png");
			doorImage[i]=ImageIO.read(f);
			//readImage=getImage(getClass().getResource(rockman_file));

		}catch(Exception e){
			e.printStackTrace();
			doorImage[i]=null;
		}
		}
		readImage=doorImage[0];
	}
	
    public void run() {
    	this.set_startTime();
    	this.reset_door_change();
    	running=true;

    	while(running){
    		
    		if(state){
    			onplay=false;
    			if(door_change==3){
    				if(!e1.isplaying()){
    				e1=new Sound_effect("sound_effect/door_open1.wav");
    				e1.play();}
    			}
    			end_time=System.currentTimeMillis();
    			elapsed_time=end_time-start_time;
    			if(elapsed_time>=60){
    				door_change++;
    				start_time=System.currentTimeMillis();
    			}
    			if(door_change>25){
    				door_change=25;
    				}
    			readImage=doorImage[door_change];

    			if(door_change==23){
    				e2=new Sound_effect("sound_effect/door_open2.wav");
    				e2.play();
    			}
    		
    		}
    		else{
    			end_time=System.currentTimeMillis();
    			elapsed_time=end_time-start_time;
    			if(elapsed_time>=60){
    				door_change--;
    				start_time=System.currentTimeMillis();
    			}
    			if(door_change<0){
    				door_change=0;
    				}

    			if(door_change==25 && !onplay){
    				e2=new Sound_effect("sound_effect/door_open2.wav");
    				e2.play();
    				onplay=true;
    			}
    			try{
    			if(door_change>=0 && door_change<=25){
    			readImage=doorImage[door_change];}
    			}
    			catch(Exception e){
    				e.printStackTrace();
    				System.out.println(door_change);
    				readImage=doorImage[0];
    			}
    			
    		}
    	}
    	door_change=0;
    	running=true;
    	
    } 
    public void stop(){
    	running=false;
    }
    public void set_startTime(){
    	start_time=System.currentTimeMillis();
    }
    public int get_door_change(){
    	return door_change;
    }
    public void reset_door_change(){

    	readImage=doorImage[0];
    }
    public BufferedImage get_image(){
    	return readImage;
    }

    public void open(){
    	state=true;
    }
    public void close(){
    	state=false;
    }
    public boolean get_state(){
    	return state;
    }
}
