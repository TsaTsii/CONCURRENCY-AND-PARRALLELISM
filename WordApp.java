package skeletonCodeAssgnmt2;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;


import java.util.Scanner;
import java.util.concurrent.*;
//model is separate from the view.

public class WordApp {
//shared variables
	static int noWords=4;
	static int totalWords;

   	static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;

	static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually

	static WordRecord[] words;
	static Thread[] words_threads;
	static volatile boolean done;  //must be volatile
	static Score score = new Score();

	static WordPanel w;
   	static WordRecord wR;
	static JLabel missed; 
	
	/**
	 * Updates the Missed Label on the GUI
	 */
	public static void updateGUI(){
		missed.setText("Missed:" + score.getMissed()+ "    ");
	}

	public static void setupGUI(int frameX,int frameY,int yLimit) {
		// Frame init and dimensions
		JFrame frame = new JFrame("WordGame"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameX, frameY);
		JPanel g = new JPanel();
		g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
		g.setSize(frameX,frameY);
    	
		w = new WordPanel(words,yLimit);
		w.setSize(frameX,yLimit+100);
	   	g.add(w); 
	    
		JPanel txt = new JPanel();
		txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS)); 
		JLabel caught =new JLabel("Caught: " + score.getCaught() + "    ");
		missed = new JLabel("Missed:" + score.getMissed()+ "    ");
		JLabel scr =new JLabel("Score:" + score.getScore()+ "    ");    
      	txt.add(caught);
	   	txt.add(missed);
	   	txt.add(scr);
    
  
	   	final JTextField textEntry = new JTextField("",20);
      
      	String text = textEntry.getText();
      
	   	textEntry.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) {
				String text = textEntry.getText();
				textEntry.setText("");
				textEntry.requestFocus();
            
				//Loop over all the words on the screen
				for (int i = 0; i < words.length; i++){
				
					//Check if any of the words on the screen matches the user input.
					if (words[i].matchWord(text)){
						score.caughtWord(text.length());
						scr.setText("Score:" + score.getScore()+ "    ");
						caught.setText("Caught: " + score.getCaught() + "    ");
						//System.out.println("The length of the word is " + score.getScore());
					}
				}
	        }
	   	});
	   
		txt.add(textEntry);
		txt.setMaximumSize( txt.getPreferredSize());
		g.add(txt);
	    
		JPanel b = new JPanel();
		b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS)); 
		JButton startB = new JButton("Start");;
			
			// add the listener to the jbutton to handle the "pressed" event
			startB.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					textEntry.requestFocus();  //return focus to the text entry field
					Thread wordP = new Thread(w);
					wordP.start();
						
					
				// 
	//             for (int i = 0; i < words.length; i++){
	//                  if (words[i].matchWord(text)){
	//                       score.caughtWord(text.length());
	//                       scr.setText("Score:" + score.getScore()+ "    ");
	//                       caught.setText("Caught: " + score.getCaught() + "    ");
	//                       //System.out.println("The length of the word is " + score.getScore());
	//                  }
	//               }
	//             //else System.out.println("Game has not started.");

				}
			}); 
      
		JButton endB = new JButton("End");;      
		// add the listener to the jbutton to handle the "pressed" event
		endB.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent e)
		{
			
			//default title and icon
			JOptionPane.showMessageDialog(frame, "GAME OVER!\nYour score is " + score.getScore());
			score.resetScore();
            caught.setText("Caught: " + score.getCaught() + "    ");
            scr.setText("Score:" + score.getScore()+ "    ");
            missed.setText("Missed:" + score.getMissed()+ "    ");
			for (int i = 0; i < words_threads.length; i++){
				words[i].resetWord();
				w.repaint();
				words[i].stop_thread();
				try {
					words_threads[i].join();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
		}
		});
      
      //This will allow user to restart the game getting new words on the screen.
      JButton restartB = new JButton("Restart");;
 			
 	   // add the listener to the jbutton to handle the "pressed" event
 		restartB.addActionListener(new ActionListener()
 		{
 		   public void actionPerformed(ActionEvent e)
 		   {
 		    score.resetScore();
            caught.setText("Caught: " + score.getCaught() + "    ");
            scr.setText("Score:" + score.getScore()+ "    ");
            missed.setText("Missed:" + score.getMissed()+ "    ");
			for (int i = 0; i < noWords; i++){
				words[i].resetWord();
				w.repaint();
			}  
 		   }
 		});
      
      JButton quitB = new JButton("Quit");;
			
				// add the listener to the jbutton to handle the "pressed" event
		quitB.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {  
         //Exit game
		      System.exit(0);
		   }
		});

		
		b.add(startB);
		b.add(restartB);   //Add the restart button to the GUI
		b.add(endB);
		b.add(quitB);      //Add the quit button to the GUI
			
		g.add(b);
			
		frame.setLocationRelativeTo(null);  // Center window on screen.
		frame.add(g); //add contents to window
		frame.setContentPane(g);     
			//frame.pack();  // don't do this - packs it into small space
		frame.setVisible(true);
	}

	public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();
			//System.out.println("read '" + dictLength+"'");

			dictStr=new String[dictLength];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]=new String(dictReader.next());
				//System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
			}
			dictReader.close();
		} catch (IOException e) {
			System.err.println("Problem reading file " + filename + " default dictionary will be used");
		}
		return dictStr;
	} 
	public static void main(String[] args) {

		//Initialize the arguements
		args = new String[3];
		args[0] = "10";
		args[1] = "5";
		args[2] = "example_dict.txt";
	
	
		//deal with command line arguments
		totalWords=Integer.parseInt(args[0]);  //total words to fall
		noWords = Integer.parseInt(args[1]); // total words falling at any point
		assert(totalWords>=noWords); // this could be done more neatly
		String[] tmpDict=getDictFromFile(args[2]); //file of words
		if (tmpDict!=null)
			dict= new WordDictionary(tmpDict);
		
		WordRecord.dict=dict; //set the class dictionary for the words.
		
	
		words = new WordRecord[noWords];  //shared array of current words
		words_threads = new Thread[noWords];
		
		
		setupGUI(frameX, frameY, yLimit);  
		//Start WordPanel thread - for redrawing animation

		int x_inc=(int)frameX/noWords;
		//initialize shared array of current words

		for (int i=0;i<noWords;i++) {
			words[i]=new WordRecord(dict.getNewWord(),i*x_inc,yLimit); 
		}
	}
}