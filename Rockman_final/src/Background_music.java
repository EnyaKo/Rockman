import javax.swing.JFrame;


public class Background_music extends JFrame implements Runnable{
	private Sound_effect background_music;
	private boolean running;
	private Sound_effect music;
	private String file_name;
	public Background_music(String file_name){
		//running=true;
		music=new Sound_effect(file_name);
		this.file_name=file_name;
	}
	public Background_music(String file_name,String loop){
		music=new Sound_effect(file_name);
		this.file_name=file_name;
		if(loop.equals("loop")){
			music.loopforever();
		}
	}
	public void run(){
		running=true;
		music=new Sound_effect(file_name);
		music.loopforever();
		play();
		while(running){
			
		}
	}
	public void stop(){
		//running=false;
		music.stop();
		//music.flush();
	}
	public void play(){
			music.play();
	}
	public void play(String loop){
		music.loopforever();
		music.play();
	}
	public boolean get_running(){
		return running;
	}
	public void volumn(int v){
		music.volume(v);
	}
}

