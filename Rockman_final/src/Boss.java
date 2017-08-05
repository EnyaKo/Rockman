
public class Boss {
    private int x, y;
    private int blood;
    private int a, b, eWidth, eHeight;
    private int c, d, aWidth, aHeight;
    private boolean can_be_attack;
    private boolean die;
    public void Boss(){
    	can_be_attack=false;
    	die=false;
    }
    public void setX(int x){
    	this.x = x;
    }
    public void setY(int y){
    	this.y = y;
    }
    public void setBlood(int blood){
    	this.blood = blood;
    }
	public void minusBlood(){
		if(can_be_attack)
			blood-=1;
		set_can_be_attack(false);
		if(blood<0){
			blood=0;
			die=true;
		}
	}

    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public int getBlood(){
    	return blood;
    }
    public void setEdRange(int a, int b, int eWidth, int eHeight){
    	this.a = getX() + a;
    	this.b = getY() + b;
    	this.eWidth = eWidth;
    	this.eHeight = eHeight*2;
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
    	return eHeight*2;
    }
    public void setAtRange(int c, int d, int aWidth, int aHeight){
    	this.c = getX() + c;
    	this.d = getY() + d;
    	this.aWidth = aWidth;
    	this.aHeight = aHeight*2;	
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
    	return aHeight*2;
    }
    public void set_can_be_attack(boolean b){
    	can_be_attack=b;
    }
    public boolean get_can_be_attack(){
    	return can_be_attack;
    }
    public boolean get_die(){
    	return die;
    }
}
