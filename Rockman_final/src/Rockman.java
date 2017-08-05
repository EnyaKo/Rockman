public class Rockman {
	private int width, height;
	public int direction = 1;
	private String state;
	private int ground;
	private int x, y, a, b, c, d;
	private int eWidth, eHeight, aWidth, aHeight;
	private int sx, sy;
	private int foot;
	public boolean fall = false;
	private boolean climb = false;
	public boolean climbjump = false;
	private boolean attack1 = false;
	private boolean attack2 = false;
	private boolean attack3 = false;
	private boolean attack5 = false;
	public boolean ishurting=false;//
	private int blood;//
	public boolean die=false;
	public boolean can_be_attack;

	public Rockman() {
		
		reset();
	}

	public void reset(){
		direction=1;
		fall=false;
		climb=false;
		ishurting=false;
		blood=100;//
		reset_die();//
		can_be_attack=true;
	}
	public void setWidthHeight(int newWidth, int newHeight) {
		width = newWidth;
		height = newHeight;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}

	public void setState(String newState) {
		state = newState;
	}

	public String getState() {
		return state;
	}

	public void setGround(int newGround) {
		ground = newGround;
	}

	public int getGround() {
		return ground;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setSx(int sx){
		this.sx = sx;
	}
	
	public void setSy(int sy){
		this.sy = sy;
	}
	
	public int getSx() {
		return sx;
	}

	public int getSy() {
		return sy;
	}

	public void setClimb(boolean climb){
		this.climb = climb;
	}
	
	public boolean getClimb() {
		return climb;
	}
	
	public int getBlood(){//
		return blood;
	}
	public void minusBlood(){//
		if(can_be_attack)
			blood-=20;
		set_can_be_attack(false);
		if(blood<=0){
			set_die();
			blood=0;
		}
	}
	public void setBlood(){
		blood=100;
	}
	
	public void setAttack1(boolean attack){
		this.attack1 = attack;
	}
	
	public boolean getAttack1() {
		return attack1;
	}
	
	public void setAttack2(boolean attack){
		this.attack2 = attack;
	}
	
	public boolean getAttack2() {
		return attack2;
	}
	
	public void setAttack3(boolean attack){
		this.attack3 = attack;
	}
	
	public boolean getAttack3() {
		return attack3;
	}
	
	public boolean getAttack5() {
		return attack5;
	}
	
	public void setAttack5(boolean attack) {
		this.attack5 = attack;
	}
	
	public void setFall(boolean fall) {
		this.fall = fall;
	}
	
	public void setFoot() {
		foot = y + height;
	}

	public int getFoot() {
		return foot;
	}
	public void set_die(){//
		die=true;
	}
	public void reset_die(){//
		die=false;
	}
	public boolean get_die(){//
		return die;
	}
	
	public void setEdRange(int a, int b, int eWidth, int eHeight){
        this.a = getX() + a;
        this.b = getY() + b;
        this.eWidth = eWidth;
        this.eHeight = eHeight;
   }
   public int getA(){
        return a;
   }
   public int getB(){
        return b;
   }
   public int geteWidth(){
        return eWidth;
   }
   public int geteHeight(){
        return eHeight;
   }
   public void setAtRange(int c, int d, int aWidth, int aHeight){
        this.c = getX() + c;
        this.d = getY() + d;
        this.aWidth = aWidth;
        this.aHeight = aHeight;    
   }
   public int getC(){
        return c;
   }
   public int getD(){
        return d;
   }
   public int getaWidth(){
        return aWidth;
   }
   public int getaHeight(){
        return aHeight;
   }
   public boolean get_can_be_attack(){
	   return can_be_attack;
   }
   public void set_can_be_attack(boolean b){
	   can_be_attack=b;
   }
}
