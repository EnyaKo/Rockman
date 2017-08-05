

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.util.Random;

import javax.jws.Oneway;


public class Boss_animation extends JFrame implements Runnable{
    //String filename = "Begin/0.png";
    BufferedImage readImage;
    private int width = 1500;
	private int height = 1000;
	private BufferedImage doubleBuffer;
	public Thread t = null;
	private int i;
	//int a = 100,b = 100,c = 300,d = 80;
	public Background_music background_music=null;
	private Boss boss=null;
	private BufferedImage bloodImage=null;
	private BufferedImage begin[] = null;
	private BufferedImage attackRight[] = null;
	private BufferedImage attackLeft[] = null;
	private BufferedImage attackDownRight[] = null;
	private BufferedImage attackDownLeft[] = null;
	private BufferedImage attackRunRight[] = null;
	private BufferedImage attackRunLeft[] = null;
	private BufferedImage attackedRight[] = null;
	private BufferedImage attackedLeft[] = null;
	private BufferedImage blood_increase[]=null;
	private BufferedImage blood[]=null;
	private int blood_count;
	private boolean increase_blood;
	private boolean running;
	
	public Boss_animation(Boss boss) {
		this.boss=boss;
		boss.setX(900);//4
		boss.setY(400);//8
		running=false;
		begin = new BufferedImage[15];
		for(int i = 0 ; i<15 ; i++){
			try {
				File f = new File("Begin/"+i+".png");
				begin[i] = ImageIO.read(f);
			} catch (Exception e) {
				e.printStackTrace();
				readImage = null;
			}
		}
		
		attackRight = new BufferedImage[6];
		for(int i = 0 ; i<6 ; i++){
			try {
				File f = new File("AttackRight/"+i+".png");
				attackRight[i] = ImageIO.read(f);
			} catch (Exception e) {
				e.printStackTrace();
				readImage = null;
			}
		}
		
		attackLeft = new BufferedImage[6];
		for(int i = 0 ; i<6 ; i++){
			try {
				File f = new File("AttackLeft/"+i+".png");
				attackLeft[i] = ImageIO.read(f);
			} catch (Exception e) {
				e.printStackTrace();
				readImage = null;
			}
		}
		
		attackDownRight = new BufferedImage[6];
		for(int i = 0 ; i<6 ; i++){
			try {
				File f = new File("AttackDownRight/"+i+".png");
				attackDownRight[i] = ImageIO.read(f);
			} catch (Exception e) {
				e.printStackTrace();
				readImage = null;
			}
		}
		
		attackDownLeft = new BufferedImage[6];
		for(int i = 0 ; i<6 ; i++){
			try {
				File f = new File("AttackDownLeft/"+i+".png");
				attackDownLeft[i] = ImageIO.read(f);
			} catch (Exception e) {
				e.printStackTrace();
				readImage = null;
			}
		}
		
		attackRunRight = new BufferedImage[6];
		for(int i = 0 ; i<5 ; i++){
			try {
				File f = new File("AttackRunRight/"+i+".png");
				attackRunRight[i] = ImageIO.read(f);
			} catch (Exception e) {
				e.printStackTrace();
				readImage = null;
			}
		}
		
		attackRunLeft = new BufferedImage[6];
		for(int i = 0 ; i<5 ; i++){
			try {
				File f = new File("AttackRunLeft/"+i+".png");
				attackRunLeft[i] = ImageIO.read(f);
			} catch (Exception e) {
				e.printStackTrace();
				readImage = null;
			}
		}
		
		attackedRight = new BufferedImage[5];
		for(int i = 0 ; i<5 ; i++){
			try {
				File f = new File("AttackedRight/"+i+".png");
				attackedRight[i] = ImageIO.read(f);
			} catch (Exception e) {
				e.printStackTrace();
				readImage = null;
			}
		}
		
		attackedLeft = new BufferedImage[5];
		for(int i = 0 ; i<5 ; i++){
			try {
				File f = new File("AttackedLeft/"+i+".png");
				attackedLeft[i] = ImageIO.read(f);
			} catch (Exception e) {
				e.printStackTrace();
				readImage = null;
			}
		}
		blood_increase=new BufferedImage[29];
		for(int i = 0 ; i<29 ; i++){
			try {
				File f = new File("boss_increase_blood/"+i+".png");
				blood_increase[i] = ImageIO.read(f);
			} catch (Exception e) {
				e.printStackTrace();
				blood_increase[i] = null;
			}
		}		
		blood=new BufferedImage[6];
		for(int i = 0 ; i<6 ; i++){
			try {
				File f = new File("boss_blood/"+i+".png");
				blood[i] = ImageIO.read(f);
			} catch (Exception e) {
				e.printStackTrace();
				blood[i] = null;
			}
				}
				
		reset_blood_count();
		
	}
	
//	public static void main(String[] args) {
//		BGame m = new BGame();
//	}
	
	public void run(){
		running=true;
		boss.setBlood(100);
		try{
			Thread.sleep(800);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		Sound_effect e1=new Sound_effect("sound_effect/boss_entry.wav");
		e1.play();
		for(int i = 0; i <= 14; i++){
			readImage = begin[i];
			try{
				Thread.sleep(180);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		//�[�J�n��
		increase_blood=true;
		bloodImage=blood_increase[0];
		Sound_effect e2=new Sound_effect("sound_effect/boss_sound.wav");
		e2.play();
		try{
			Thread.sleep(4000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		Sound_effect e3=new Sound_effect("sound_effect/blood_increase.wav");
		e3.play();		
		for(i=0;i<29;i++){
			bloodImage=blood_increase[i];
			try{
				Thread.sleep(30);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			if(i==21){
				background_music=new Background_music("background_music/boss.wav","loop");
				background_music.play();
			}
					}
		increase_blood=false;
		boss.set_can_be_attack(true);
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		

		while (boss.getBlood() != 0) {
			if(!running){
				boss.setX(900);
				boss.setY(400);
				break;
			}
			int k = 0;
			if(boss.getBlood()==100)
				k=5;
			else if(boss.getBlood()<=100 && boss.getBlood() >=80)
				k=4;
			else if(boss.getBlood()<80 && boss.getBlood() >=60)
				k=3;
			else if(boss.getBlood()<60 && boss.getBlood() >=40)
				k=2;
			else if(boss.getBlood()<40 && boss.getBlood() >=20)
				k=1;
			else if(boss.getBlood()<20 && boss.getBlood() >=0)
				k=0;
			bloodImage=blood[k];
			Random rand = new Random();
			if(boss.getX()<=((width/2)-227)){
				int rightAction = rand.nextInt(4);
				switch (rightAction) {
				case 0:
					attackMoveRight();
					break;
				case 1:
					attackRunRight();
					break;
				case 2:
					attackRight();
					break;
				case 3:
					attackDownRight();
					break;
				default:
					break;
				}
				
			}
			else if(((width/2)-227)<boss.getX()){
				int leftAction = rand.nextInt(4);
				switch (leftAction) {
				case 0:
					attackMoveLeft();
					break;
				case 1:
					attackRunLeft();
					break;
				case 2:
					attackLeft();
					break;
				case 3:
					attackDownLeft();
					break;
				default:
					break;
				}
			}
			
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			//When attacked doing attackedLeft() & attackedRight()	
			//attackedLeft();
			//attackedRight();
			
			
			//attackRunRight();
			//attackRunLeft();
			//attackRight();
			//attackLeft();
			//attackMoveRight();
			//attackMoveLeft();
		    //attackDownRight();
		    //attackDownLeft();
		    //repaint();
		}
		background_music.stop();
		
	}
	public void attackMoveRight(){
		readImage = begin[14];
		for(int i = 0; i < 10; i++){
			if(i<5){
				boss.setX(boss.getX() + 100);
			}
			else {
				boss.setX(boss.getX() - 40);
			}
			boss.setEdRange(130,90,200,80);
			boss.setAtRange(130,90,200,80);
			try{
				Thread.sleep(100);//50
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}	
	}
	public void attackMoveLeft(){
		readImage = begin[14];
		for(int i = 0; i < 10; i++){
			if(i<5){
				boss.setX(boss.getX() - 100);
			}
			else {
				boss.setX(boss.getX() + 40);
			}
			boss.setEdRange(130,90,200,80);
			boss.setAtRange(130,90,200,80);
			try{
				Thread.sleep(100);//50
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}	
	}
	public void attackRunRight(){
		Sound_effect e1=new Sound_effect("sound_effect/attack_run.wav");
		e1.play();
		for(int i = 0; i <= 4; i++){
			readImage = attackRunRight[i];
			if(i >=2){
				boss.setX(boss.getX() + 150);
			}
			boss.setEdRange(90,100,280,80);
			boss.setAtRange(175,100,300,80);
			try{
				Thread.sleep(100);//50
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}	
	}
	
	public void attackRunLeft(){
		Sound_effect e1=new Sound_effect("sound_effect/attack_run.wav");
		e1.play();
		//Move Left 400
		for(int i = 0; i <= 4; i++){
			readImage = attackRunLeft[i];
			if(i >=2){
				boss.setX(boss.getX() - 150);
			}
			boss.setEdRange(90,100,280,80);
			boss.setAtRange(15,100,300,80);
			try{
				Thread.sleep(100);//50
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}	
	}
	
	public void attackRight(){
		Sound_effect e1=new Sound_effect("sound_effect/boss_attack.wav");
		e1.play();
		for(int i = 0; i <= 5; i++){
			readImage = attackRight[i];
			boss.setEdRange(160,100,180,120);
			boss.setAtRange(150,100,100,100);
			try{
				Thread.sleep(100);//50
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}	
	}
	
	public void attackLeft(){
		Sound_effect e1=new Sound_effect("sound_effect/boss_attack.wav");
		e1.play();
		for(int i = 0; i <= 5; i++){
			readImage = attackLeft[i];
			boss.setEdRange(160,100,180,120);
			boss.setAtRange(250,100,100,100);
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}	
	}
	
	public void attackedLeft(){
		readImage = attackedLeft[0];
		try{
			Thread.sleep(100);//50
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		for(int i = 1; i <= 2; i++){
			readImage = attackedLeft[i];
			boss.setX(boss.getX() + 100);
			boss.setY(boss.getY() - 50);
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		for(int i = 3; i <= 4; i++){
			readImage = attackedLeft[i];
			boss.setX(boss.getX() + 100);
			boss.setY(boss.getY() + 50);
			try{
				Thread.sleep(50);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void attackedRight(){
		readImage = attackedRight[0];
		try{
			Thread.sleep(100);//50
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		for(int i = 1; i <= 2; i++){
			readImage = attackedRight[i];
			boss.setX(boss.getX() - 100);
			boss.setY(boss.getY() - 50);
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		for(int i = 3; i <= 4; i++){
			readImage = attackedRight[i];
			boss.setX(boss.getX() - 100);
			boss.setY(boss.getY() + 50);
			try{
				Thread.sleep(100);//50
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void attackDownRight(){
		Sound_effect e1=new Sound_effect("sound_effect/attack_down.wav");
		e1.play();
		readImage = attackDownRight[0];
		boss.setX(boss.getX()+100);
		boss.setY(boss.getY()-50);
		boss.setEdRange(160,100,160,120);
		boss.setAtRange(160,100,160,120);
		
		try{
			Thread.sleep(200);//100
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		readImage = attackDownRight[1];	
		boss.setX(boss.getX()+150);
		boss.setY(boss.getY()-250);
		boss.setEdRange(160,110,160,120);
		boss.setAtRange(160,150,160,80);
		
		try{
			Thread.sleep(200);//100
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		readImage = attackDownRight[2];
		boss.setX(boss.getX()+300);
		boss.setY(boss.getY()-300);
		boss.setEdRange(160,110,160,120);
		boss.setAtRange(160,150,160,80);
		
		try{
			Thread.sleep(200);//100
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		readImage = attackDownRight[3];
		for(int i = 0 ; i<5 ; i++){
			boss.setY(boss.getY()+20);
			boss.setEdRange(160,70,140,160);
			boss.setAtRange(160,130,140,120);
			
			try{
				Thread.sleep(200);//50
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
		readImage = attackDownRight[4];
		for(int i = 0 ; i<5 ; i++){
			boss.setY(boss.getY()+40);
			boss.setEdRange(160,70,140,160);
			boss.setAtRange(160,130,140,135);
			
			try{
				Thread.sleep(100);//50
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
		readImage = attackDownRight[5];	
		for(int i = 0 ; i<5 ; i++){
			boss.setY(boss.getY()+60);
			boss.setEdRange(160,70,140,160);
			boss.setAtRange(160,130,140,145);
			
			try{
				Thread.sleep(100);//50
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}		
	}
	
	public void attackDownLeft(){
		Sound_effect e1=new Sound_effect("sound_effect/attack_down.wav");
		e1.play();
		readImage = attackDownLeft[0];	
		boss.setX(boss.getX()-100);
		boss.setY(boss.getY()-50);
		boss.setEdRange(160,100,160,120);
		boss.setAtRange(160,100,160,120);
		
		try{
			Thread.sleep(200);//100
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		readImage = attackDownLeft[1];	
		boss.setEdRange(160,110,160,120);
		boss.setAtRange(160,150,160,80);
		boss.setX(boss.getX()-150);
		boss.setY(boss.getY()-250);
		
		try{
			Thread.sleep(200);//100
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		readImage = attackDownLeft[2];	
		boss.setEdRange(160,110,160,120);
		boss.setAtRange(160,150,160,80);
		boss.setX(boss.getX()-300);
		boss.setY(boss.getY()-300);
		
		try{
			Thread.sleep(200);//100
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		readImage = attackDownLeft[3];	
		for(int i = 0 ; i<5 ; i++){
			boss.setY(boss.getY()+20);
			boss.setEdRange(160,70,140,160);
			boss.setAtRange(160,130,140,120);
			
			try{
				Thread.sleep(100);//50
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
		readImage = attackDownLeft[4];	
		for(int i = 0 ; i<5 ; i++){
			boss.setY(boss.getY()+40);
			boss.setEdRange(160,70,140,160);
			boss.setAtRange(160,130,140,135);
			
			try{
				Thread.sleep(100);//50
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
		readImage = attackDownLeft[5];	
		for(int i = 0 ; i<5 ; i++){
			boss.setY(boss.getY()+60);
			boss.setEdRange(160,70,140,160);
			boss.setAtRange(160,130,140,145);
			
			try{
				Thread.sleep(100);//50
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}
	public void reset_blood_count(){
		blood_count=0;
	}
	public void reset_blood(){
		increase_blood=false;
	}
	public boolean get_increase_blood(){
		return increase_blood;
	}
	public BufferedImage getImage(){
		return readImage;
	}
	public BufferedImage getbloodImage(){
		return bloodImage;
	}
	public boolean get_running(){
		return running;
	}
	public void set_running(){
		running=true;
	}
	public void reset_running(){
		running=false;
	}
	public void reset_position(){
		boss.setX(900);//4
		boss.setY(400);//8
	}

}
