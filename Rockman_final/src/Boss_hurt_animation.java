import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class Boss_hurt_animation implements Runnable{
	private BufferedImage boss_minusLifeBar[]=null;
	private BufferedImage bloodImage=null;
	private int blood;
	private boolean playing;
	private boolean running;
	private int i;
	public Boss_hurt_animation(){
		boss_minusLifeBar=new BufferedImage[5];
		for(int i=4;i>=0;i--){
			try{
				File f=new File("BossMinusLifeBar/"+i+".png");
				boss_minusLifeBar[i]=ImageIO.read(f);

			}catch(Exception e){
				e.printStackTrace();
				boss_minusLifeBar[i]=null;
			}
				}
		set_blood(100);
	}
	public void run(){
		running=true;
		playing=true;
		i=0;
		while(running){
			if(i==2){
				//play_end=true;
			}
			if(i==3){
				running=false;
				continue;
			}
			int k = 0;
			if(blood<=100 && blood >=80)
				k=4;
			else if(blood<80 && blood >=60)
				k=3;
			else if(blood<60 && blood >=40)
				k=2;
			else if(blood<40 && blood >=20)
				k=1;
			else if(blood<20 && blood >=0)
				k=0;
			
		bloodImage=boss_minusLifeBar[k];
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
	public void set_blood(int blood){
		this.blood=blood;
	}
	public BufferedImage get_blood(){
		return bloodImage;
	}
	public boolean get_playing(){
		return playing;
	}
	public void reset_playing(){
		playing=false;
	}
	public int get_i(){
		return i;
	}
}
