import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Die_animation extends JFrame {
	private int die_change;
	private int rockman_die;
	private int width;
	private int height;
	private int dark;
	private int ground;
	private BufferedImage zeroImage=null;
	private Rockman rockman=null;
	private boolean die_end=false;
	Die_animation(){
		die_change=1;
		rockman_die=0;
		dark=40;
	}
	public void play(int x,int y,Graphics g){
		//((Graphics2D) g).setStroke(new BasicStroke(3.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		//Graphics2D g2=(Graphics2D)g;
		if(die_change==6){
			Sound_effect e1=new Sound_effect("sound_effect/zeroDie.wav");
			e1.play();
		}
		if(die_change>13){

			//g2.clearRect(0, 0, width, height);
	        g.setColor(new Color(0,0,0));
	        g.fillRect(0, 0, 1500, 1000);
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rockman_die=0;
			die_change=1;
			die_end=true;
			dark=40;
			return ;
		}
		String die_file="zero_die/"+(die_change)+".png";
		die_change++;

		try{
			File f=new File(die_file);
			zeroImage=ImageIO.read(f);
			
		}catch(Exception e){
			e.printStackTrace();
			zeroImage=null;
		}
		//g2.clearRect(0, 0, width, height);
		if(die_change>=11){
	        g.setColor(new Color(0,0,0,dark+=20));
	        g.fillRect(0, 0, 1500, 1000);
		}
//		int x=rockman.getX()-(int)(zeroImage.getWidth()*0.75);//rockman_x
//		int y=rockman.getY()-(int)(zeroImage.getWidth()*0.3);//rockman_y
//		int zero_new_width=(int)(zeroImage.getWidth()*2);
//		int zero_new_height=(int)(zeroImage.getHeight()*2);
		g.drawImage(zeroImage, x, y, zeroImage.getWidth()*3, zeroImage.getHeight()*3, this);
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getRockmanDie(){
		return rockman_die;
	}
	public void setRockmanDie(int rockman_die){
		this.rockman_die=rockman_die;
	}
	public void set_die_end(boolean die_end){
		this.die_end=die_end;
	}
	public boolean get_die_end(){
		return die_end;
	}

}

