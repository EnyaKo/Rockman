import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;

public class Keyboard implements KeyListener, Runnable {
	private Queue q = new LinkedList();
	private boolean pressLeft = false;
	private boolean pressRight = false;
	private boolean pressJump = false;
	private boolean pressSpace=false;
	private boolean pressDash = false;
	private boolean pressAttack = false;
	private int continueAttack = 0;
	
	public boolean pressJumpOne = false;
	public boolean pressAttackOne = false;
	
	private long startTime = 0;
	private long endTime = 0;
	private long elapsedTime = 0;
	private long jumpStartTime = 0;
	private long jumpElapsedTime = 0;
	private long jumpEndTime = 0;
	private long attackStartTime = 0;
	private long attackElapsedTime = 0;
	private long attackEndTime = 0;
	private long dashStartTime = 0;
	private long dashElapsedTime = 0;
	private long dashEndTime = 0;
	
	private Sound_effect e1;
	private Sound_effect e2;	
	private Sound_effect e3;
	private Sound_effect e4;
	private boolean fall = false;
	public boolean dashend = false;
	private boolean lock;
	int a = 0;
	public Keyboard(){
		e1=new Sound_effect("sound_effect/jump.wav");
		e2=new Sound_effect("sound_effect/zero_jump.wav");
		e3=new Sound_effect("sound_effect/dash.wav");
		e4=new Sound_effect("sound_effect/attack.wav");
		lock=false;
	}
	public void run() {
		Object o = null;
		startTime = System.nanoTime();
		jumpStartTime = System.nanoTime();
		attackStartTime = System.nanoTime();
		dashStartTime = System.nanoTime();
		while (true) {
			//if(pressLeft || pressRight){
				endTime = System.nanoTime();
				elapsedTime = (endTime - startTime) / 10000000;
			//}
				//if(pressAttack){
				attackEndTime = System.nanoTime();
				attackElapsedTime = (attackEndTime - attackStartTime) / 10000000;
			//}
			//System.out.println();
			/*if(attackElapsedTime > 5){
				pressAttackOne = false;
			}else if(attackElapsedTime <= 5 && pressAttackOne ){
				continueAttack++;
				if(continueAttack > 3)
					continueAttack = 0;
			}*/
			pressJumpOne = false;
				
			if(attackElapsedTime >= 30)
				continueAttack = 0;
			
			if(pressJump){
				jumpEndTime = System.nanoTime();
				jumpElapsedTime = (jumpEndTime - jumpStartTime) / 10000000;
			}
			if(pressDash){
				dashEndTime = System.nanoTime();
				dashElapsedTime = (dashEndTime - dashStartTime) / 10000000;
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean getRight() {
		return pressRight;
	}

	public boolean getLeft() {
		return pressLeft;
	}

	public boolean getJump() {
		return pressJump;
	}
	public boolean getSpace(){
		return pressSpace;
	}
	public boolean getDash(){
		return pressDash;
	}
	
	public boolean getAttack() {
		return pressAttack;
	}
	
	public void setFall(boolean fall){
		this.fall = fall;
	}
	
	public boolean getFall(){
		return fall;
	}
	
	public int getContinueAttack() {
		return continueAttack;
	}
	
	public void setContinueAttack(int a) {
		continueAttack = a ;
	}
	/*public String getQueue() {
		Object o;
		if ((o = q.poll()) != null) {
			return (String) o;
		} else {
			return "";
		}
	}*/
	
	public long getElapsedTime() {
		return elapsedTime;
	}
	
	public long getDashElapsedTime() {
		return dashElapsedTime;
	}
	
	public long getJumpElapsedTime() {
		return jumpElapsedTime;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(lock){
			return;
		}
		int key = e.getKeyCode();

		if (key == e.VK_RIGHT) {

			if (pressRight == false)
				startTime = System.nanoTime();

			pressRight = true;

		}

		if (key == e.VK_LEFT) {

			if (pressLeft == false)
				startTime = System.nanoTime();
			
			pressLeft = true;

		}

		if (key == e.VK_X) {
			if(!e1.isplaying() && !e2.isplaying()){
				e1=new Sound_effect("sound_effect/jump.wav");
				e1.play();
				e2=new Sound_effect("sound_effect/zero_jump.wav");
				e2.play();
			}
		//	if(!e2.isplaying()){
				
		//	}
			//S!e2.isplaying()ound_effect e1=new Sound_effect("sound_effect/jump.wav");
			//e1.play();
			//Sound_effect e2=new Sound_effect("sound_effect/zero_jump.wav");
			//e2.play();
			if(!fall){
				if(pressJump == false)
					jumpStartTime = System.nanoTime();
				pressJump = true;
			}
			else
				pressJump = false;

		}
		if(key == e.VK_Z){
			//if(!e4.isplaying()){
				Sound_effect e5=new Sound_effect("sound_effect/attack.wav");
				e5.play();
		//	}
			continueAttack++;
			if(continueAttack > 3)
				continueAttack = 0;
			
			if(pressAttack == false)
				attackStartTime = System.nanoTime();
			pressAttack = true;
		}
		if(key == e.VK_C){
			if(!e3.isplaying()){
				e3=new Sound_effect("sound_effect/dash.wav");
				e3.play();
			}
			if(pressDash == false)
				dashStartTime = System.nanoTime();
			pressDash = true;
		}
		if(key == e.VK_SPACE){
			pressSpace=true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(lock){
			return;
		}
		int key = e.getKeyCode();
		if (key == e.VK_RIGHT) {

			if (pressRight == true)
				startTime = System.nanoTime();
			elapsedTime = 0;
			pressRight = false;

		}
		if (key == e.VK_LEFT) {

			if (pressLeft == true)
				startTime = System.nanoTime();
			elapsedTime = 0;
			pressLeft = false;

		}
		if (key == e.VK_X) {

			if (pressJump == true)
				jumpStartTime = System.nanoTime();
			jumpElapsedTime = 0;
			pressJump = false;
			fall = true;
		}
		if(key == e.VK_Z){
			
			if (pressAttack == true)
				attackStartTime = System.nanoTime();
			pressAttack = false;
		}
		if(key == e.VK_C){
			if (pressDash == true)
				dashStartTime = System.nanoTime();
			dashElapsedTime = 0;
			pressDash = false;
			dashend = false;
		}
		if(key == e.VK_SPACE){
			pressSpace=false;
		}
	}

	public void keyTyped(KeyEvent e) {
		int key = e.getKeyChar();
		
				if(key == 120)//X
					pressJumpOne = true;
				
				if(key == 122){//Z
					//attackStartTime = System.nanoTime();
					pressAttackOne = true;
				}
	}
	
	public void setlock(){
		lock=true;
	}
	public void resetlock(){
		lock=false;
	}
	public void resetSpace(){
		pressSpace=false;
	}
	public void resetAll(){
		pressLeft = false;
		pressRight = false;
		pressJump = false;
		pressSpace=false;
		fall=false;
	}
}
