package skeletonCodeAssgnmt2;

public class Score {
	private int missedWords;
	private int caughtWords;
	private int gameScore;
	
	Score() {
		missedWords=0;
		caughtWords=0;
		gameScore=0;
	}
		
	
   //This method returns the number of missed words.	
	public int getMissed() {
		return missedWords;
	}
   
   //This method returns the number of words the user was able to type before they got to the red line.
	public int getCaught() {
		return caughtWords;
	}
	
   //This method returns the total number of words that have been displayed on the screen since the game started.
	public int getTotal() {
		return (missedWords+caughtWords);
	}
   
   //This method returns the score which is measured by the sum of the length of the words the user was able to get.
	public int getScore() {
		return gameScore;
	}
	
   //This method increments the missed words variable everytime a word gets to the red line.
	public void missedWord() {
		missedWords++;
	}
   
   //This method increments the score and the number of words everytime a user types a word correctly before it can get to the red line.
	public void caughtWord(int length) {
		caughtWords++;
		gameScore+=length;
	}
 
   //This method resets the game when the user presses the end or start button.
	public void resetScore() {
		caughtWords=0;
		missedWords=0;
		gameScore=0;
	}
}
