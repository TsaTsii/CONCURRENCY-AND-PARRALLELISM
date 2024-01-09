package skeletonCodeAssgnmt2;

public class WordRecord implements Runnable{
	static int SPEED_FACTOR = 50; // helps speed up the words

	private String text;
	private  int x;
	private int y;
	private int maxY;
	private boolean dropped;

	private boolean STOP;
	
	private int fallingSpeed;
	private static int maxWait=1500;
	private static int minWait=100;

	public static WordDictionary dict;
	
	
	WordRecord() {
		text="";
		x=0;
		y=0;	
		maxY=300;
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait);
		this.STOP = false; 
	}
	
	WordRecord(String text) {
		this();
		this.text=text;
	}
	
	WordRecord(String text,int x, int maxY) {
		this(text);
		this.x=x;
		this.maxY=maxY;
	}
	
// all getters and setters must be synchronized
	public synchronized  void setY(int y) {
		if (y>maxY) {
			y=maxY;
			dropped=true;
		}
		this.y=y;
	}
	
	public synchronized  void setX(int x) {
		this.x=x;
	}
	
	public synchronized  void setWord(String text) {
		this.text=text;
	}

	public synchronized  String getWord() {
		return text;
	}
	
	public synchronized  int getX() {
		return x;
	}	
	
	public synchronized  int getY() {
		return y;
	}
	
	public synchronized  int getSpeed() {
		return fallingSpeed;
	}

	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}
	public synchronized void resetPos() {
		setY(0);
	}

	public synchronized void resetWord() {
		resetPos();
		text=dict.getNewWord();
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		//System.out.println(getWord() + " falling speed = " + getSpeed());

	}
	
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);
		if (typedText.equals(this.text)) {
			resetWord();
			return true;
		}
		else
			return false;
	}
   
   /**This changes the y value of the word.*/
	public synchronized void drop(int inc) {
		setY(y+inc);
	}
   
	Score score = new Score();
	
	public synchronized void stop_thread(){
		this.STOP = true;
	}
	
	public synchronized WordRecord start_thread(){
		this.STOP = false;
		return this;
	}

	public void run(){
			while (!STOP){
						
				this.drop(1);
				try {
					Thread.sleep(this.getSpeed()/SPEED_FACTOR); // Falls too slow without this factor 
					//System.out.println("slept for" + getSpeed()/SPEED_FACTOR);
				}  
				catch (Exception e) {
					e.printStackTrace();
				}
				if (dropped){
					WordApp.score.missedWord();
					this.resetWord();
					WordApp.updateGUI();
				}
				
			}
		}
	
		public synchronized  boolean dropped() {
			return dropped;
		}

}
