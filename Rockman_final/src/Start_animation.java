import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Start_animation extends JFrame {
	int start;
	private int width;
	private int height;
	private int rightBound;
	private int leftBound;
	private Rockman rockman;
	private int ground;
	private int startX;
	private int startY;
	private int endX;
	private int start_step;
	private int ready_change;
	private double centerX1;
	private double centerX2;
	private long start_start_time;
	private long start_end_time;
	private long start_elapsed_time;
	private BufferedImage startImage;
	private BufferedImage startImage1;
	private BufferedImage zeroImage;
	private BufferedImage doubleBuffer=null;
	private BufferedImage ready_change_color[]=null;
	private BufferedImage ready_change_font[]=null;
	private BufferedImage ready_change_zero[]=null;
	private Sound_effect e2;
	private int start_zero_x;
	private int start_zero_y;
	private Sound_effect e;
	private boolean start_end;
	Start_animation(Rockman rockman,int width,int height,int ground){
		start=1;
		start_end=false;
		this.width=width;
		this.height=height;
		this.rockman=rockman;
		this.rightBound=this.width-rockman.getWidth();
		this.ground=ground;
		startX=-150;
		startY=this.height/2;
		endX=-150;
		start_step=1;
		ready_change=1;
		centerX1 = 0;
		centerX2 = 0;
		start_zero_x=0;
		start_zero_y=0;
		reset_zeroIn_sound();
		
		ready_change_color=new BufferedImage[6]; //1-5
		ready_change_font=new BufferedImage[8]; //1-7 
		ready_change_zero=new BufferedImage[14]; //0-13
		
		for(int i=1;i<=5;i++){
			String ready_file="ready_change_color/"+i+".png";
				try{
					File f=new File(ready_file);
					ready_change_color[i]=ImageIO.read(f);
					
				}catch(Exception e){
					e.printStackTrace();
					ready_change_color[i]=null;
				}
		}	
		
		for(int i=1;i<=7;i++){
			String ready_file="ready_change_font/"+i+".png";
				try{
					File f=new File(ready_file);
					ready_change_font[i]=ImageIO.read(f);
					
				}catch(Exception e){
					e.printStackTrace();
					ready_change_font[i]=null;
				}
		}	
		
		for(int i=0;i<14;i++){
			String ready_file="ready_change_zero/"+i+".png";
				try{
					File f=new File(ready_file);
					ready_change_zero[i]=ImageIO.read(f);
					
				}catch(Exception e){
					e.printStackTrace();
					ready_change_zero[i]=null;
				}
		}	
	}
	public void reset_zeroIn_sound(){
		e2=new Sound_effect("sound_effect/zeroIn.wav");
	}
	public void play(Graphics g){
			   
			g.setColor(Color.BLUE);
			((Graphics2D) g).setStroke(new BasicStroke(3.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
			//doubleBuffer = (BufferedImage) this.createImage(800, 600); 
			//Graphics2D g2=(Graphics2D) doubleBuffer.getGraphics();
			if(start_step==1){
				if(startX>rightBound+rockman.getWidth()){
					if(ready_change>=6){
						ready_change=5;
					}		
					startImage=ready_change_color[ready_change++];
	
					/*
					String ready_file="ready_change_color/"+(ready_change++)+".png";
					try{
						File f=new File(ready_file);
						startImage=ImageIO.read(f);
						
					}catch(Exception e){
						e.printStackTrace();
						startImage=null;
					}
					*/

					if(endX>=width){
						start_step++;
						startX=0;
						endX=0;
						start_start_time=System.currentTimeMillis();
					}
					else{
						g.drawLine(endX+=150,startY,startX,startY);
					}
					double centerX=((0.5)*width)-((0.5)*startImage.getWidth())*2;
					double centerY=(0.5)*height-(0.5)*startImage.getHeight()*2;
					g.drawImage(startImage, (int)centerX, (int)centerY, startImage.getWidth()*2, startImage.getHeight()*2, this);
			    
			}
				else{
					g.drawLine(0,startY,startX+=150,startY);
				}
			}
			else if(start_step==2){
					start_end_time=System.currentTimeMillis();
					start_elapsed_time=start_end_time-start_start_time;
					if(start_elapsed_time>1000){
						start_step++;
						ready_change=1;
						start_start_time=System.currentTimeMillis();
					}
					
					/*
					String ready_file="ready_change_color/"+"5.png";
					try{
						File f=new File(ready_file);
						startImage=ImageIO.read(f);
						
					}catch(Exception e){
						e.printStackTrace();
						startImage=null;
					}
					*/
					startImage=ready_change_color[5];
					double centerX=((0.5)*width)-((0.5)*startImage.getWidth())*2;
					double centerY=(0.5)*height-(0.5)*startImage.getHeight()*2;
					//g2.clearRect(0, 0, width, height);
					//g2.drawImage(startImage, (int)centerX, (int)centerY, startImage.getWidth(), startImage.getHeight(), this);	
					//g.clearRect(0, 0, width, height);
					g.drawImage(startImage, (int)centerX, (int)centerY, startImage.getWidth()*2, startImage.getHeight()*2, this);	
			}
			else if(start_step==3){
				
				if(ready_change==1){
				Sound_effect e1=new Sound_effect("sound_effect/ready.wav");
				e1.play();
					}
				
			
				if(ready_change==4){
					start_end_time=System.currentTimeMillis();
					ready_change=3;
					start_elapsed_time=start_end_time-start_start_time;
					if(start_elapsed_time>=1000){
						ready_change=5;
					}
				}
				
				if(ready_change>=8){
					ready_change=7;
					start_step=4;
				}
				/*
				String ready_file="ready_change_font/"+(ready_change++)+".png";
				
				try{
					File f=new File(ready_file);
					startImage=ImageIO.read(f);
					
				}catch(Exception e){
					e.printStackTrace();
					startImage=null;
				}
				*/
				startImage=ready_change_font[ready_change++];
				double centerX=((0.5)*width)-((0.5)*startImage.getWidth())*2;
				double centerY=(0.5)*height-(0.5)*startImage.getHeight()*2;
				g.drawImage(startImage, (int)centerX, (int)centerY, startImage.getWidth()*2, startImage.getHeight()*2, this);
			}
			else if(start_step==4){
				/*
				String ready_file="ready_change_font/"+"7.png";

				try{
					File f=new File(ready_file);
					startImage=ImageIO.read(f);
					
				}catch(Exception e){
					e.printStackTrace();
					startImage=null;
				}
				*/
				startImage=ready_change_font[7];
				
				centerX1=((0.5)*width)-((0.5)*startImage.getWidth())*2;
				centerX2=((0.5)*width)-((0.5)*startImage.getWidth())*2;
				start_step=5;
				ready_change=0;
			}
			else if(start_step==5){
				if(ready_change==1){
					if(!e2.is_play()){
						e2.play();
						}
					}
				/*
				String ready_file="ready_change_font/"+"7.png";
				String zero_file="ready_change_zero/"+ready_change+".png";
				try{
					File f=new File(ready_file);
					File fzero=new File(zero_file);
					startImage=ImageIO.read(f);
					startImage1=ImageIO.read(f);
					zeroImage=ImageIO.read(fzero);
					
				}catch(Exception e){
					e.printStackTrace();
					startImage=null;
					zeroImage=null;
				}
				*/
				try{
				startImage=ready_change_font[7];
				startImage1=ready_change_font[7];
				zeroImage=ready_change_zero[ready_change];
				
				}catch(Exception e){
					e.printStackTrace();
					startImage=null;
					zeroImage=null;
				}
				//int zero_start_height=zeroImage.getHeight();
				double centerY=(0.5)*height-(0.5)*startImage.getHeight()*2;
				centerX1+=80;
				centerX2-=80;

				g.drawImage(startImage, (int)centerX1, (int)centerY, startImage.getWidth()*2, startImage.getHeight()*2, this);
				g.drawImage(startImage1, (int)centerX2, (int)centerY, startImage.getWidth()*2, startImage.getHeight()*2, this);
				if(centerX2<-startImage.getWidth()){
					//centerX1=-startImage.getWidth();
					//centerX2=width;
				}
				int zero_new_width=(int)(zeroImage.getWidth()*3);//1.4
				int zero_new_height=(int)(zeroImage.getHeight()*3);
				

				if(start_zero_y<500){ //ground
					if(ready_change==0){
						ready_change=1;
						
					}
					else{
						ready_change=0;
					}
					
					g.drawImage(zeroImage, -50, start_zero_y,zero_new_width , zero_new_height, this);
					start_zero_y+=50;
					if(start_zero_y>ground){
						ready_change=2;
					}
				}
				else{
					
					g.drawImage(zeroImage, -50, 500, zero_new_width, zero_new_height, this);
					ready_change++;
					if(ready_change==13){
						try {
							Thread.sleep(200); //200
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(ready_change>13){
						ready_change=1;
						centerX1 = 0;
						centerX2 = 0;
						start_zero_x=0;
						start_zero_y=0;
						start=0;
						start_step=1;
						e2.reset_play();
						reset_zeroIn_sound();
						set_start_end(true);
					}
				}
			}
	}
	public int getStart(){
		return start;
	}
	public void setStart(int start){
		this.start=start;
	}
	public void resetStartStep(){
		start_step=1;
	}
	public void set_start_end(boolean start_end){
		this.start_end=start_end;
	}
	public boolean get_start_end(){
		return start_end;
	}
	public int getStartStep(){
		return start_step;
	}
}
	

