import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game extends JFrame implements Runnable,KeyListener{
	private Keyboard k = null;
	private int start_step;//?豢??脤??? 0:press start 1:銝??2:?豢?鈭箇
	private int select_mode;//?豢??桀??? ?詨0????嚗?????

	private Background_animation background=null;
	private Thread background_t;
	private boolean loading;
	private int change;//蝯奸oading??????start?恍 ??start?恍 ???訾犖?拍??????
	private int gifchange;//蝯血?????
	private int width = 1500; //600
	private int height = 1000; //500
	private int backgroundx;
	private int groundx=0;
	private int wallx=0;
	private int doorx=0;
	private boolean lock;
	private int ground;
	private boolean show_life_bar;
	private Hurt_animation hurt_animation=null;
	private Boss_hurt_animation boss_hurt_animation=null;
	private Thread th_hurt=null;
	private Thread th_boss_hurt=null;
	private Thread th=null;

	private Background_music background_music=null;
	private Sound_effect efall;
	
	private boolean first_enter_boss;
	private int enter_boss;//0:?芷脣??/1:?脣??銝?2:撌脤脣??(?撌脤?銝?
	//private boolean enter_boss;
	Rockman zero;
	private Boss boss=null;
	private Boss_animation boss_animation=null;
	Door door=null;
	Warning warning=null;
	Thread door_t;
	Thread boss_t=null;
	private Thread warning_t=null;
	private Thread music_t=null;
	String file_name = "StandRight/StandRight0.png";
	int a = 0; //run
	int b = 0; //stand
	int c = 0; //jump
	int d = 3; // climb
	int e = 0; // attack
	int f = 0; //dash
	BufferedImage titleImage[]=null;
	BufferedImage titleSelectImage[]=null;
	BufferedImage select_background_x[]=null;
	BufferedImage select_background_zero[]=null;
	BufferedImage select_character_x=null;
	BufferedImage select_character_zero=null;
	BufferedImage select_character_x_gif[]=null;
	BufferedImage select_character_zero_gif[]=null;
	BufferedImage loadingImage[]=null;
	BufferedImage zero_life_bar[]=null;
	BufferedImage readImage;
	BufferedImage groundImage;
	BufferedImage doorImage;
	BufferedImage wallImage;
	BufferedImage warningImage;
	BufferedImage bossImage=null;
	private Start_animation start_animation=null;
	private Die_animation die_animation=null;
	public Thread t = null;
	private boolean running;
	//Image OffScreen = createImage(width,height);
	//Graphics drawOffScreen= OffScreen.getGraphics();
	public Game() {
		
		setSize(width, height);
		lock=false;
		first_enter_boss=true;
		enter_boss=0;
		running=true;
		loading=true;
		change=0;
		gifchange=0;
		show_life_bar=false;//
		hurt_animation=new Hurt_animation();
		boss_hurt_animation=new Boss_hurt_animation();
		
		/*OffScreen = createImage(width,height);
		drawOffScreen = OffScreen.getGraphics();*/
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		
		efall=new Sound_effect("sound_effect/fall_ground.wav");


		/*
		door=new Door(width,height);
		door_t= new Thread(door);
		warning=new Warning();
		warning_t=new Thread(warning);
		music_t=new Thread(background_music);
		music_t.start();
		*/
		zero = new Rockman();

		try{
			File f=new File("ground.png");
			groundImage=ImageIO.read(f);
			//readImage=getImage(getClass().getResource(rockman_file));

		}catch(Exception e){
			e.printStackTrace();
			groundImage=null;
		}
		
		try{
			File f1=new File("wall.png");
			wallImage=ImageIO.read(f1);
			//readImage=getImage(getClass().getResource(rockman_file));

		}catch(Exception e){
			e.printStackTrace();
			wallImage=null;
		}
		ground=850;

		zero.setX(-50);
		//zero.setX(400);
		zero.setY(ground-360);//height/2
		start_animation=new Start_animation(zero,width,height,ground);
		die_animation=new Die_animation();
		
		k = new Keyboard();
		addKeyListener(k);
		addKeyListener(this);
		th = new Thread(k);
		th.start();
		loadingImage=new BufferedImage[2];
		titleImage=new BufferedImage[2];
		titleSelectImage=new BufferedImage[3];
		select_background_x=new BufferedImage[3];
		select_background_zero=new BufferedImage[3];
		select_character_x_gif=new BufferedImage[12];
		select_character_zero_gif=new BufferedImage[18];
		zero_life_bar=new BufferedImage[6];//
		
		for(int i=0;i<2;i++){
			try{
				File f=new File("loading/"+i+".png");
				File f1=new File("title/title_start_"+i+".png");
				
				loadingImage[i]=ImageIO.read(f);
				titleImage[i]=ImageIO.read(f1);
				//select_character_x[i]=ImageIO.read(f2);
				//select_character_zero[i]=ImageIO.read(f3);
			}catch(Exception e){
				e.printStackTrace();
				loadingImage[i]=null;
				titleImage[i]=null;
				//select_character_x[i]=null;
				//select_character_zero[i]=null;
			}
		}
		
		for(int i=0;i<3;i++){
		try{
			File f=new File("title/title_select_"+i+".png");
			File f1=new File("character_select/x_"+i+".png");
			File f2=new File("character_select/zero_"+i+".png");
			titleSelectImage[i]=ImageIO.read(f);
			select_background_x[i]=ImageIO.read(f1);
			select_background_zero[i]=ImageIO.read(f2);
		}catch(Exception e){
			e.printStackTrace();
			titleSelectImage[i]=null;
			select_background_x[i]=null;
			select_background_zero[i]=null;
			}
		}
		
		try{
			File f=new File("character_select/x_character.gif");
			File f1=new File("character_select/zero_character.gif");
			select_character_x=ImageIO.read(f);
			select_character_zero=ImageIO.read(f1);
		}catch(Exception e){
			e.printStackTrace();
			select_character_x=null;
			select_character_zero=null;
			}
		
		for(int i=0;i<12;i++){
			try{
				File f=new File("character_select/x_gif/"+i+".gif");
				select_character_x_gif[i]=ImageIO.read(f);
			}catch(Exception e){
				e.printStackTrace();
				select_character_x_gif[i]=null;

				}
		}
		
		for(int i=0;i<18;i++){
			try{
				File f=new File("character_select/zero_gif/"+i+".gif");
				select_character_zero_gif[i]=ImageIO.read(f);
			}catch(Exception e){
				e.printStackTrace();
				select_character_zero_gif[i]=null;

				}
		}
		

		
		start_step=0;//閮剖??脣?暺?0:press start 1:銝??2:?豢?鈭箇)
		select_mode=0;

		
	}

	public static void main(String[] args) {

		Game m=new Game();
		m.background_music=new Background_music("background_music/title.wav");
		m.background_music.play();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while(m.start_step==0){
			m.repaint();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(m.start_step==1){
			m.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.background_music.stop();
		m.background_music=new Background_music("background_music/character_select.wav","loop");
		m.background_music.play();
		while(m.start_step==2){
			m.repaint();
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		m.background_music.stop();
		m.background_music=new Background_music("background_music/1.wav");
		m.change=0;
		Thread t = new Thread(m);
		t.start();
		while(m.loading){
			m.repaint();
			if(m.change==0){
				m.change=1;
			}
			else{
				m.change=0;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void run(){
		door=new Door(width,height);
		door_t= new Thread(door);
		warning=new Warning();
		warning_t=new Thread(warning);
		//music_t=new Thread(background_music);

		background=new Background_animation();
		boss=new Boss();
		boss_animation=new Boss_animation(boss);
		boss_t=new Thread(boss_animation);
		background_t= new Thread(background);
		for(int i=5;i>0;i--){//
			try{
				File f=new File("LifeBar/"+i+".png");
				zero_life_bar[i]=ImageIO.read(f);

			}catch(Exception e){
				e.printStackTrace();
				zero_life_bar[i]=null;
			}
				}
		loading=false;
		//music_t.start();
		background_t.start();
		background_music.play("loop");
		while (true) {
			//System.out.println(boss.getBlood());
			if(zero.getBlood()<=0){
				lock=true;//?犖??
				k.setlock();//???
				die_animation.setRockmanDie(1);
				 zero.setBlood();
			}
			if(!start_animation.get_start_end()){
				k.setlock();//???
				 boss_hurt_animation.reset_playing();
				
				/*
				if(!background_music.get_running()){
				music_t=new Thread(background_music);
				music_t.start();
				//System.out.println(music_t.isAlive());
				//music_t.stop();	
								}
				*/
			}
		
			else if(die_animation.get_die_end()){ //?剖?甇颱滿?
				start_animation.setStart(1);
				die_animation.set_die_end(false);
				//lock=false;//閫??鈭箇
				//k.resetlock();//閫???萇
				zero.setX(-50);
				zero.setY(ground-360);
				start_animation.set_start_end(false);
				//zero.setFall(false);
				zero.reset(); //reset??
				k.resetAll();//reset?萇
				background_music.stop();
				background_music=new Background_music("background_music/1.wav");
				background_music.play("loop");
				//music_t.stop();
				lock=false;
				 backgroundx=0;
				 groundx=0;
				 wallx=0;
				 doorx=0;
				// door_t= new Thread(door);
				 enter_boss=0;
				 background.reset_background_change();
				 running=false;
				 show_life_bar=false;//
				 hurt_animation.reset_play_end();
				 boss_animation.reset_running();
				 boss_animation.background_music.stop();
				 boss_t.stop();
				 boss_t=new Thread(boss_animation);
				 boss_hurt_animation.reset_playing();
				 boss_animation.reset_position();
				// System.out.println("hi");
				//zero.setDirection(1);
			}
			else if(!die_animation.get_die_end() && die_animation.getRockmanDie()==1){
				lock=true;
				k.setlock();
				k.resetAll();
				file_name = "StandRight/StandRight0.png";
				//file_name ="StandRight/StandRight" + b + ".png";
				//zero.setFall(false);
			}
			else{
				if(!lock)
				lock=false;
				//System.out.println("hi");
				//lock=false;
				k.resetlock();
			}
			
			if(!lock){
				setPosition();	
			}
			
			collision();
			
			if(enter_boss==2){
			zero.set_can_be_attack(!hurt_animation.get_playing());
			boss.set_can_be_attack(!boss_hurt_animation.get_playing());
			}
			//if()
			
			if(k.getSpace()){
				k.resetSpace();
				lock=true;//?犖??
				k.setlock();//???
				die_animation.setRockmanDie(1);
			}
			if(start_animation.getStartStep()==5){//
				show_life_bar=true;
			}
			repaint();

				if(door.get_door_change()==0 && enter_boss==0){	 //蝣啣?
				if(zero.getX()>=1030){
					enter_boss=1;
					lock=true;
					//if(!door_t.isAlive())
					door.open();
					door_t= new Thread(door);
					door_t.start();
					//door_open=true;
					}
						}

			
			if(door.get_door_change()==25){
				
				backgroundx+=5;
				groundx+=10;
				wallx+=20;
				doorx+=20;
				zero.setX(zero.getX()-17);
				
			}
			if(groundx>=720){
				groundx=720;
			}
			if(backgroundx>=360){//250
				backgroundx=360;
			}
			if(wallx>=1440){
				wallx=1440;
			}
			if(doorx>=1440){
				doorx=1440;
				}
			if(enter_boss==1 && zero.getX()<= -208){ //撌脤脣??
				zero.setX(-208);
				lock=false;//閫??鈭箇
				k.setlock();//???
				//if(door.get_state())
					door.set_startTime();
				door.close();
				enter_boss=2;
				background_music.stop();
				if(first_enter_boss){
				warning.set_startTime();
				if(warning.get_warning_change()==0)
					warning_t.start();
					}

				if(door.get_door_change()==0){
					//door_t.stop();
					door.stop();
				}
				if(warning.get_warning_change()==89){
					first_enter_boss=false;
					warning.stop();
				}
				
			}
			if(first_enter_boss && enter_boss==2 && warning.get_warning_change()==89){
					if(!boss_animation.get_running()){
						boss_t.start();
						boss_animation.set_running();
				}
			}
			else if(enter_boss==2 && warning.get_warning_change()==90)
			{
				if(!boss_animation.get_running()){
					boss_t.start();
					boss_animation.set_running();
				}
			}
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setPosition() {
		zero.setEdRange(272, 150, 136, 150);
		
		if (k.getRight())
			zero.direction = 1;
		if (k.getLeft())
			zero.direction = -1;

		if (k.getContinueAttack() == 1) {
			zero.setAttack3(true);
		}
		if (k.getContinueAttack() == 2) {
			zero.setAttack2(true);
		}
		if (k.getContinueAttack() == 3) {
			zero.setAttack1(true);
		}
		if(k.getAttack() && zero.getY() < ground - 360){
			zero.setAttack5(true);
		}
		

		if (k.getJump() && !k.getFall() && !zero.getAttack5()) {// jump

			if (k.getJumpElapsedTime() <= 15) {
				c++;
				if (c > 3)
					c = 3;
				zero.setY(zero.getY() - 42);
			}
			if (k.getJumpElapsedTime() > 15 && k.getJumpElapsedTime() <= 40) {
				zero.setY(zero.getY() - 8);
			}
			if (k.getJumpElapsedTime() > 40) {
				k.setFall(true);
				c = 0;
			}
			
			//zero.setEdRange(272, 150, 136, 150);
			
			if (zero.direction == 1) {
				file_name = "JumpRight/JumpRight" + c + ".png";
				if (k.getRight())
					zero.setX(zero.getX() + 15);
			} else if (zero.direction == -1) {
				file_name = "JumpLeft/JumpLeft" + c + ".png";
				if (k.getLeft())
					zero.setX(zero.getX() - 15);
			}
		}

		else if (k.getFall() && !zero.getClimb() && !zero.getAttack5()) {// fall
			zero.setY(zero.getY() + 26);
			if (zero.getY() > ground - 360) {
				if(!efall.isplaying()){
					efall=new Sound_effect("sound_effect/fall_ground.wav");
					efall.play();
				}
				k.setFall(false);
				zero.setY(ground - 360);
			} else if (zero.getY() > ground - 360 - 5) {
				c = 9;
			} else if (zero.getY() > ground - 360 - 10) {
				c = 8;
			} else {
				c++;
				if (c > 7)
					c = 5;
			}
			
			//zero.setEdRange(272, 150, 136, 150);
			
			if (zero.direction == 1) {
				file_name = "FallRight/FallRight" + c + ".png";
				if (k.getRight())
					zero.setX(zero.getX() + 10);
			}
			if (zero.direction == -1) {
				file_name = "FallLeft/FallLeft" + c + ".png";
				if (k.getLeft())
					zero.setX(zero.getX() - 10);
			}
		}

		else if (zero.getAttack5()) {
			zero.setAtRange(272-50, 150-50, 136+120 , 150+100);
			e++;
			if (e > 8) {
				e = 0;
				zero.setAttack5(false);
			}else

			if (zero.direction == 1) {
				file_name = "Attack5Right/" + e + ".png";
			} else if (zero.direction == -1) {
				file_name = "Attack5Left/" + e + ".png";
			}
		}

		else if (zero.getAttack1()) {

			e++;
			if (e > 11) {
				e = 0;
				zero.setAttack1(false);
			}else

			if (zero.direction == 1) {
				zero.setAtRange(272+136, 150, 136 , 150);
				file_name = "Attack1Right/" + e + ".png";
			} else if (zero.direction == -1) {
				zero.setAtRange(272-136, 150, 136 , 150);
				file_name = "Attack1Left/" + e + ".png";
			}
		} else if (zero.getAttack2()) {

			e++;
			if (e > 10) {
				e = 0;
				zero.setAttack2(false);
			}else

			if (zero.direction == 1) {
				zero.setAtRange(272+136, 150+56, 136 , 37);
				file_name = "Attack2Right/" + e + ".png";
			} else if (zero.direction == -1) {
				zero.setAtRange(272-136, 150+56, 136 , 37);
				file_name = "Attack2Left/" + e + ".png";
			}
		} else if (zero.getAttack3()) {

			e++;
			if (e > 13) {
				e = 0;
				zero.setAttack3(false);
			}else

			if (zero.direction == 1) {
				zero.setAtRange(272+150, 150-10, 150 , 150);
				file_name = "Attack3Right/" + e + ".png";
			} else if (zero.direction == -1) {
				zero.setAtRange(272-150-14, 150-10, 150 , 150);
				file_name = "Attack3Left/" + e + ".png";
			}
		}else if(k.pressJumpOne){
			zero.setX(zero.getX() - 30);
		}
		else if (zero.getClimb()) {

			zero.setEdRange(272, 150, 136, 150);
			
			if (d > 4)
				d = 3;

			if (zero.direction == 1) {
				if(k.getLeft())
					zero.setClimb(false);
				file_name = "ClimbRight/" + d + ".png";
			} else if (zero.direction == -1) {
				if(k.getRight())
					zero.setClimb(false);
				file_name = "ClimbLeft/" + d + ".png";
			}

			d++;

			zero.setY(zero.getY() + 10);
			
			
		}

		else if (k.getRight() && !k.getJump()) {//right run
				
			if (k.getElapsedTime() <= 7) {
				a = 1;
				zero.setX(zero.getX() + 3);
			}
			if (k.getElapsedTime() > 7) {
				a++;
				zero.setX(zero.getX() + 15);
				if (a > 15)
					a = 2;
			}
			
			//zero.setEdRange(272, 150, 136, 150);
			file_name = "RunRight/RunRight" + a + ".png";
			

		} else if (k.getLeft() && !k.getJump()) {//left run
			if (k.getElapsedTime() <= 7) {
				a = 1;
				zero.setX(zero.getX() - 3);
			}
			if (k.getElapsedTime() > 7) {
				a++;
				zero.setX(zero.getX() - 15);
				if (a > 15)
					a = 2;
			}
			//zero.setEdRange(272, 150, 136, 150);
			file_name = "RunLeft/RunLeft" + a + ".png";

			/*
			 * if (zero.getX() <= -150) { zero.setX(-150); }
			 */
			// zero.direction = -1;
		}else if(k.getDash() && !k.dashend){
			zero.setEdRange(272, 200, 150, 100);
			if (k.getDashElapsedTime() <= 45) {
				f++;
				if (f > 6)
					f = 4;
				if(zero.direction == 1)
					zero.setX(zero.getX() + 40);
				else if(zero.direction == -1)
					zero.setX(zero.getX() - 40);
			}

			if (k.getDashElapsedTime() > 45) {
				k.dashend = true;
				f = 7;
			}
			
			
			if(zero.direction == 1)
				file_name = "DashRight/" + f + ".png";
			else if(zero.direction == -1)
				file_name = "DashLeft/" + f + ".png";
		}
		else if(k.dashend){
			//zero.setEdRange(272, 225, 150, 100);
			f++;
			if(f > 10)
				k.dashend = false;
				
			if (zero.direction == 1) {
				file_name = "DashRight/" + f + ".png";
				zero.setX(zero.getX() + 10);
			}
			if (zero.direction == -1) {
				file_name = "DashLeft/" + f + ".png";
				zero.setX(zero.getX() - 10);
			}
			
		}

		if (!k.getRight() && !k.getLeft() && !k.getJump() && !k.getFall() && !k.dashend && !k.getDash()) {
			//zero.setEdRange(272, 150, 136, 150);
			if (zero.direction == 1) {
				if (k.getElapsedTime() % 150 >= 0
						&& k.getElapsedTime() % 150 < 50)
					b = 0;
				if (k.getElapsedTime() % 150 >= 50
						&& k.getElapsedTime() % 150 < 75)
					b = 1;
				if (k.getElapsedTime() % 150 >= 75
						&& k.getElapsedTime() % 150 < 125)
					b = 2;
				if (k.getElapsedTime() % 150 >= 125
						&& k.getElapsedTime() % 150 < 150)
					b = 3;
				file_name = "StandRight/StandRight" + b + ".png";

			} else {
				if (k.getElapsedTime() % 150 >= 0
						&& k.getElapsedTime() % 150 < 50)
					b = 0;
				if (k.getElapsedTime() % 150 >= 50
						&& k.getElapsedTime() % 150 < 75)
					b = 1;
				if (k.getElapsedTime() % 150 >= 75
						&& k.getElapsedTime() % 150 < 125)
					b = 2;
				if (k.getElapsedTime() % 150 >= 125
						&& k.getElapsedTime() % 150 < 150)
					b = 3;
				file_name = "StandLeft/StandLeft" + b + ".png";
			}
		}
		
		/*if (zero.getAttack5()) {
			e++;
			if (e > 7) {
				e = 0;
				zero.setAttack5(false);
			}else

			if (zero.direction == 1) {
				file_name = "Attack5Right/" + e + ".png";
			} else if (zero.direction == -1) {
				file_name = "Attack5Left/" + e + ".png";
			}
		}*/
		
		if (k.getRight() && zero.getY() < ground - 360) {// ?斗?亥???
			//zero.setEdRange(272, 150, 136, 150);
			if (zero.getX() > width - 600)
				zero.setClimb(true);
				
			else if (zero.getX() > width - 610 && zero.getX() < width - 600)

				file_name = "ClimbRight/2.png";

			else if (zero.getX() > width - 620 && zero.getX() < width - 610)

				file_name = "ClimbRight/1.png";
				
				
		}else if (k.getLeft() && zero.getY() < ground - 360) {// ?斗?亥???
			//zero.setEdRange(272, 150, 136, 150);
			if (zero.getX() < -150)
				zero.setClimb(true);
				
			else if (zero.getX() > -150 && zero.getX() < -140)

				file_name = "ClimbLeft/2.png";

			else if (zero.getX() > -140 && zero.getX() < -130)

				file_name = "ClimbLeft/1.png";
		
		}
		else

			zero.setClimb(false);

		 

		if (zero.getX() <= -150) {
			zero.setX(-150);
		}


	}
	
	public void collision(){
		if(zero.get_can_be_attack() && enter_boss==2){
		if(boss.getX() + c > zero.getX() + a && boss.getX() + c < zero.getX() + a + zero.geteWidth() &&
				boss.getY() + d > zero.getY() + b && boss.getY() + d < zero.getY() + b + zero.geteHeight()){
			zero.minusBlood();
			hurt_animation.set_blood(zero.getBlood());
			hurt_animation.set_direction(zero.getDirection());
			th_hurt=new Thread(hurt_animation);
			th_hurt.start();
		}
		else if(boss.getX() + c + boss.getaWidth() > zero.getX() + a && boss.getX() + c + boss.getaWidth() < zero.getX() + a + zero.geteWidth() &&
				boss.getY() + d > zero.getY() + b && boss.getY() + d < zero.getY() + b + zero.geteHeight()){
			zero.minusBlood();
			hurt_animation.set_blood(zero.getBlood());
			hurt_animation.set_direction(zero.getDirection());
			th_hurt=new Thread(hurt_animation);
			th_hurt.start();
		}
		else if(boss.getX() + c > zero.getX() + a && boss.getX() + c < zero.getX() + a + zero.geteWidth() &&
				boss.getY() + d + boss.getaHeight() > zero.getY() + b && boss.getY() + d + boss.getaHeight() < zero.getY() + b + zero.geteHeight()){
			zero.minusBlood();
			hurt_animation.set_blood(zero.getBlood());
			hurt_animation.set_direction(zero.getDirection());
			th_hurt=new Thread(hurt_animation);
			th_hurt.start();
		}
		else if(boss.getX() + c + boss.getaWidth() > zero.getX() + a && boss.getX() + c + boss.getaWidth() < zero.getX() + a + zero.geteWidth() &&
				boss.getY() + d + boss.getaHeight() > zero.getY() + b && boss.getY() + d + boss.getaHeight() < zero.getY() + b + zero.geteHeight()){
			zero.minusBlood();
			hurt_animation.set_blood(zero.getBlood());
			hurt_animation.set_direction(zero.getDirection());
			th_hurt=new Thread(hurt_animation);
			th_hurt.start();
		}
		else if(boss.getX() + c > zero.getX() + a && boss.getX() + c < zero.getX() + a + zero.geteWidth() &&
				boss.getY() + d + boss.getaHeight()/2 > zero.getY() + b && boss.getY() + d + boss.getaHeight()/2 < zero.getY() + b + zero.geteHeight()){
			zero.minusBlood();
			hurt_animation.set_blood(zero.getBlood());
			hurt_animation.set_direction(zero.getDirection());
			th_hurt=new Thread(hurt_animation);
			th_hurt.start();
		}
		else if(boss.getX() + c + boss.getaWidth() > zero.getX() + a && boss.getX() + c + boss.getaWidth() < zero.getX() + a + zero.geteWidth() &&
				boss.getY() + d + boss.getaHeight()/2 > zero.getY() + b && boss.getY() + d + boss.getaHeight()/2 < zero.getY() + b + zero.geteHeight()){
			zero.minusBlood();
			hurt_animation.set_blood(zero.getBlood());
			hurt_animation.set_direction(zero.getDirection());
			th_hurt=new Thread(hurt_animation);
			th_hurt.start();
		}

		}
		
		
		//zero attack
		if(boss.get_can_be_attack() && enter_boss==2){
		if(zero.getX() + c > boss.getX() + a && zero.getX() + c < boss.getX() + a + boss.geteWidth() &&
				zero.getY() + d > boss.getY() + a && zero.getY() + d < boss.getY() + d + boss.geteHeight()){
			boss.minusBlood();
			boss_hurt_animation.set_blood(boss.getBlood());
			th_boss_hurt=new Thread(boss_hurt_animation);
			th_boss_hurt.start();
			//System.out.println("123");
			
		}
		else if(zero.getX() + c + zero.getaWidth() > boss.getX() + a && zero.getX() + c + zero.getaWidth() < boss.getX() + a + boss.geteWidth() &&
				zero.getY() + d > boss.getY() + b && zero.getY() + d < boss.getY() + b + boss.geteHeight()){
			boss.minusBlood();
			boss_hurt_animation.set_blood(boss.getBlood());
			th_boss_hurt=new Thread(boss_hurt_animation);
			th_boss_hurt.start();
			//System.out.println("123");
		}
		else if(zero.getX() + c > boss.getX() + a && zero.getX() + c < boss.getX() + a + boss.geteWidth() &&
				zero.getY() + d + zero.getaHeight() > boss.getY() + b && zero.getY() + d + zero.getaHeight() < boss.getY() + b + boss.geteHeight()){
			boss.minusBlood();
			boss_hurt_animation.set_blood(boss.getBlood());
			th_boss_hurt=new Thread(boss_hurt_animation);
			th_boss_hurt.start();
			//System.out.println("123");
		}
		else if(zero.getX() + c + zero.getaWidth() > boss.getX() + a && zero.getX() + c + zero.getaWidth() < boss.getX() + a + boss.geteWidth() &&
				zero.getY() + d + zero.getaHeight() > boss.getY() + b && zero.getY() + d + zero.getaHeight() < boss.getY() + b + boss.geteHeight()){
			boss.minusBlood();
			boss_hurt_animation.set_blood(boss.getBlood());
			th_boss_hurt=new Thread(boss_hurt_animation);
			th_boss_hurt.start();
			//System.out.println("123");
		}
	}
		
	}

	private BufferedImage doubleBuffer;

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		
		try {
			File f = new File(file_name);
			readImage = ImageIO.read(f);
		} catch (Exception e) {
			e.printStackTrace();
			readImage = null;
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, width, height);
		
		/*?????????????????????????????????????????脤??脣??鼓鋆賡?蝔?????????????????????????????????????????*/
		if(start_step==0){
			if(change==0){
				change=1;
			}
			else{
				change=0;
			}
			if(titleImage!=null)
				g2.drawImage(titleImage[change], 0, 0, 1500,1000,this);
			return;
		}
		if(start_step==1){
			if(titleSelectImage!=null)
				g2.drawImage(titleSelectImage[select_mode], 0, 0, 1500,1000,this);
			return;
		}
		if(start_step==2){
			change++;
			if(change>2){
				change=0;
			}
			
			if(select_mode==0){
				if(select_background_x!=null)
					g2.drawImage(select_background_x[change], 0, 0, 1500,1000,this);
				if(select_character_zero!=null)
					g2.drawImage(select_character_zero, 1100, 510, select_character_zero.getWidth(null)*3,select_character_zero.getHeight(null)*3,this);
				if(select_character_x_gif!=null){
					g2.drawImage(select_character_x_gif[gifchange++], 20, 480, select_character_x.getWidth(null)*3,select_character_x.getHeight(null)*3,this);
					if(gifchange>11)
						gifchange=0;
					}
				
					
			}
			else{
				if(select_background_zero!=null)
					g2.drawImage(select_background_zero[change], 0, 0, 1500,1000,this);
				if(select_character_x!=null)
					g2.drawImage(select_character_x, 20, 480, select_character_x.getWidth(null)*3,select_character_x.getHeight(null)*3,this);
				if(select_character_zero_gif!=null){
					g2.drawImage(select_character_zero_gif[gifchange++], 1100, 510, select_character_zero.getWidth(null)*3,select_character_zero.getHeight(null)*3,this);
					if(gifchange>14)
						gifchange=0;
					}
				
					
			}
			return;
		}
		if(loading){
			if(loadingImage!=null){
				if(loadingImage!=null)
					g2.drawImage(loadingImage[change], 0, 0, 1500,1000,this);
			return ;}
		}
		/*?????????????????????????????????????????脤??脣??鼓鋆賡?蝔?????????????????????????????????????????*/
		
		if(background!=null){
			if(background.get_image()!=null){
				g2.drawImage(background.get_image(),0, 0, width, height, backgroundx, 0, backgroundx+1500, 1700, this);
				//g2.drawImage(background.get_image(),0, 0, width, height, this);
			}
		}
		if(groundImage!=null){
		g2.drawImage(groundImage, 0, 750, 1500, 1035, groundx, 0, groundx+(int)(1500*0.5), 185, this);
		}
		
		if(wallImage!=null && doorImage!=null){
			g2.drawImage(wallImage, width-wallImage.getWidth()*3-25-wallx, height-ground-doorImage.getWidth()*3-450, (int)(wallImage.getWidth()*3.5),(int)(wallImage.getHeight()*3.5),this);
			g2.drawImage(wallImage, width-wallImage.getWidth()*3-25-wallx+width-wallImage.getWidth()*2, height-ground-doorImage.getWidth()*3-450+doorImage.getHeight()+40, (int)(wallImage.getWidth()*3.5),(int)(wallImage.getHeight()*3.5),this);
			}
		if(door!=null){
			doorImage=door.get_image();
			//System.out.println("door:"+door.get_door_change());
			//g2.drawImage(doorImage, width-(int)(doorImage.getWidth()*2*0.58), ground-(int)(doorImage.getHeight()*2*0.75), doorImage.getWidth()*2, doorImage.getHeight()*2, this);
			g2.drawImage(doorImage, width-(int)(doorImage.getWidth()*3*0.58)-doorx, ground-(int)(doorImage.getHeight()*3*0.75)-50, doorImage.getWidth()*3, doorImage.getHeight()*3, this);
		}
		if(boss_animation.get_running() && boss_animation.getImage()!=null){
			bossImage=boss_animation.getImage();
			if(boss_hurt_animation.get_playing()&&(boss_hurt_animation.get_i()==0 || boss_hurt_animation.get_i()==2))
				bossImage=null;
			g2.drawImage(bossImage, boss.getX(),boss.getY(), 474, 300*2, null);
			/*g2.setColor(Color.RED);
			g2.drawRect(boss.getA(), boss.getB(), boss.geteWidth(), boss.geteHeight());
			g2.setColor(Color.BLUE);
			g2.drawRect(boss.getC(), boss.getD(), boss.getaWidth(), boss.getaHeight());*/
		}
		if(boss_animation.get_running() && boss_animation.getbloodImage()!=null){
			if(!boss_hurt_animation.get_playing()){
				if(boss_animation.getbloodImage()!=null)
				g2.drawImage(boss_animation.getbloodImage(), 1250,220, (int)(boss_animation.getbloodImage().getWidth()*1.3), (int)(boss_animation.getbloodImage().getHeight()*1.3), null);
				}
			else{
				if(boss_hurt_animation.get_blood()!=null)
				g2.drawImage(boss_hurt_animation.get_blood(), 1250,220, (int)(boss_hurt_animation.get_blood().getWidth()*1.3), (int)(boss_hurt_animation.get_blood().getHeight()*1.3), null);
			}
		}
		if(first_enter_boss && enter_boss==2){
			warningImage=warning.get_image();
			if(warningImage!=null)
			g2.drawImage(warningImage, 50, 150, warningImage.getWidth()*4,warningImage.getHeight()*4,this);
		}
		if(!hurt_animation.get_playing()){
		if(show_life_bar){
			if(zero_life_bar[zero.getBlood()/20]!=null)
			g2.drawImage(zero_life_bar[zero.getBlood()/20],-20, 250, (int)(zero_life_bar[zero.getBlood()/20].getWidth()*1.5),(int)(zero_life_bar[zero.getBlood()/20].getHeight()*1.5), this);
		}
		}
		else{
			if(hurt_animation.get_blood()!=null)
			g2.drawImage(hurt_animation.get_blood(),-20, 250, (int)(hurt_animation.get_blood().getWidth()*1.5),(int)(hurt_animation.get_blood().getHeight()*1.5), this);
		}
		if(start_animation!=null){
		if(start_animation.getStart()==1){
			//if(!background_music.get_running())
			//music_t.start();
			if(start_animation!=null && start_animation.getStart()==1){
				start_animation.play(g2);
				//start=start_animation.getStart();
			}
		}
		else if(die_animation.getRockmanDie()==1){
			die_animation.play(zero.getX(),zero.getY(),g2);
			//lock=true;
		}
		else if(hurt_animation.get_playing()){//
			lock=true;
			g2.drawImage(hurt_animation.get_hurt(), zero.getX(), zero.getY() , 681, 450, null);
			
		}
		
		else{
			
		if (readImage != null) {
			g2.drawImage(readImage, zero.getX(), zero.getY() , 681, 450, null);//474 300

		}
		
			}
		if(hurt_animation.get_play_end()){
			lock=false;
		}
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		if(key==e.VK_A){//
			if(!zero.get_can_be_attack()){
				return;
			}
		//	zero.minusBlood();
		//	boss.minusBlood();
			hurt_animation.set_blood(zero.getBlood());
			hurt_animation.set_direction(zero.getDirection());
		//	th_hurt=new Thread(hurt_animation);
		//	th_hurt.start();
			
			boss_hurt_animation.set_blood(boss.getBlood());
		//	th_boss_hurt=new Thread(boss_hurt_animation);
		//	th_boss_hurt.start();
		}
		if (key == e.VK_RIGHT) {
			if(start_step==2){
				gifchange=0;
				if(select_mode==0){
					select_mode=1;
					Sound_effect e1=new Sound_effect("sound_effect/character_switch.wav");
					e1.play();
				}
			}

		}

		if (key == e.VK_LEFT) {
			if(start_step==2){
				gifchange=0;
				if(select_mode==1){
					select_mode=0;
					Sound_effect e1=new Sound_effect("sound_effect/character_switch.wav");
					e1.play();
				}
				
			}

		}
		
		if (key == e.VK_UP) {
			if(start_step==1){
				select_mode--;
				if(select_mode<0){
					select_mode=2;
				}
				Sound_effect e1=new Sound_effect("sound_effect/title_switch.wav");
				e1.play();
			}
			
		}
		if (key == e.VK_DOWN) {
			if(start_step==1){
				select_mode++;
				if(select_mode>2){
					select_mode=0;
				}
				Sound_effect e1=new Sound_effect("sound_effect/title_switch.wav");
				e1.play();
			}
			
		}


		if(key == e.VK_SPACE){
			if(start_step==0){
				start_step=1;
				Sound_effect e1=new Sound_effect("sound_effect/title_enter.wav");
				e1.play();
			}
			else if(start_step==1){
				if(select_mode==0){
					start_step=2;
					Sound_effect e1=new Sound_effect("sound_effect/title_enter.wav");
					e1.play();
				}
				else{
					Sound_effect e1=new Sound_effect("sound_effect/title_switch.wav");
					e1.play();
				}
			}
			else if(start_step==2){
				if(select_mode==0){
					Sound_effect e1=new Sound_effect("sound_effect/character_switch.wav");
					e1.play();
				}
				else{
					start_step=3;
					Sound_effect e1=new Sound_effect("sound_effect/character_enter.wav");
					e1.play();
				}
			}
			else{
				removeKeyListener(this);
			}
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}
	
	

}
