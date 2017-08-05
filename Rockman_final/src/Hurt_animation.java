import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class Hurt_animation implements Runnable{
	private BufferedImage zero_hurtImage_right[];
	private BufferedImage zero_hurtImage_left[];
	private BufferedImage zero_minusLifeBar[];
	private BufferedImage hurtImage;
	private BufferedImage bloodImage;
	private int blood;
	private int direction;
	private boolean playing;
	private boolean running;
	private boolean lock;
	private boolean play_end;
	public Hurt_animation(){
		running=true;
		zero_hurtImage_right=new BufferedImage[4];
		zero_hurtImage_left=new BufferedImage[4];
		zero_minusLifeBar=new BufferedImage[6];
		set_blood(100);
		set_direction(1);
		for(int i=0;i<4;i++){
			try{
				File f=new File("DamagedLeft/"+i+".png");
				File f1=new File("DamagedRight/"+i+".png");
				zero_hurtImage_left[i]=ImageIO.read(f);
				zero_hurtImage_right[i]=ImageIO.read(f1);

			}catch(Exception e){
				e.printStackTrace();
				zero_hurtImage_left[i]=null;
				zero_hurtImage_right[i]=null;
			}
				}
		
		for(int i=4;i>=0;i--){
			try{
				File f=new File("MinusLifeBar/"+i+".png");
				zero_minusLifeBar[i]=ImageIO.read(f);

			}catch(Exception e){
				e.printStackTrace();
				zero_minusLifeBar[i]=null;
			}
				}
		playing=false;
	}
	
	public void run(){
		playing=true;
		int i=0;
		Sound_effect e1=new Sound_effect("sound_effect/hurt.wav");
		e1.play();
		lock=true;
		play_end=false;
		running=true;
		while(running){
			if(i==2){
				//play_end=true;
			}
			if(i==3){
				play_end=true;
				running=false;
				continue;
			}
		if(direction==-1){//left
			hurtImage=zero_hurtImage_left[i];
		}
		else{//right
			hurtImage=zero_hurtImage_right[i];
		}
		bloodImage=zero_minusLifeBar[blood/20];
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i++;
			}
		playing=false;
		
		
	}
	public BufferedImage get_blood(){
		return bloodImage;
	}
	public BufferedImage get_hurt(){
		return hurtImage;
	}
	public void set_playing(boolean playing){
		this.playing=playing;
	}
	public void set_blood(int blood){
		this.blood=blood;
	}
	public void set_direction(int direction){
		this.direction=direction;
	}
	public boolean get_playing(){
		return playing;
	}
	public boolean get_play_end(){
		return play_end;
	}
	public void reset_play_end(){
		play_end=false;
	}
	public boolean getlock(){
		return lock;
	}
	public void stop(){
		running=false;
	}
}
