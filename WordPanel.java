package skeletonCodeAssgnmt2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JPanel;

public class WordPanel extends JPanel implements Runnable {
		public static volatile boolean done;
		private WordRecord[] words;
		private int noWords;
		private int maxY;

		
		public void paintComponent(Graphics g) {
		    int width = getWidth();
		    int height = getHeight();
		    g.clearRect(0,0,width,height);
		    g.setColor(Color.red);
		    g.fillRect(0,maxY-10,width,height);

		    g.setColor(Color.black);
		    g.setFont(new Font("Helvetica", Font.PLAIN, 26));
		   //draw the words
		   //animation must be added 
		    for (int i=0;i<noWords;i++){	    	
		    	//g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());	
		    	g.drawString(words[i].getWord(),words[i].getX(),words[i].getY()+20);  //y-offset for skeleton so that you can see the words	
		    }
		   
		  }
		
		WordPanel(WordRecord[] words, int maxY) {
			this.words=words; //will this work?
			noWords = words.length;
			done=false;
			this.maxY=maxY;		
		}
		

		public void run() {
      
			for (int i = 0; i <WordApp.noWords; i++){
					WordApp.words_threads[i] = new Thread(words[i].start_thread());
					WordApp.words_threads[i].start();
			}
			
			while(true){
				repaint();
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
       //   int num = 0;
//          
//          while (true) {
//              // Thread word = new Thread(words[i]);
// 
//             try{
//                  Thread.sleep(words[i].getSpeed());
//             for (int i = 0; i < words.length; i++) {
//                 //words[i].start();
//                 words[i].drop(1);
//                 if(words[i].dropped()){
//                         words[i].resetWord();
//                         WordApp.score.missedWord();
//                         WordApp.missed.setText("Missed:" + WordApp.score.getMissed()+ "    ");
//                         }
//                 repaint();
//                 }
//                 }
//                 catch (Exception e){}
                                
            }
                     
}
		




