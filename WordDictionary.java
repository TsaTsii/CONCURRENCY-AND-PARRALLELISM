package skeletonCodeAssgnmt2;

public class WordDictionary {
	int size;
   
   //This array stores all the words that could possibly fall on the screen
	static String [] theDict= {"litchi","banana","apple","mango","pear","orange","strawberry",
		"cherry","lemon","apricot","peach","guava","grape","kiwi","quince","plum","prune",
		"cranberry","blueberry","rhubarb","fruit","grapefruit","kumquat","tomato","berry",
		"boysenberry","loquat","avocado"}; //default dictionary
	
	WordDictionary(String [] tmp) {
		size = tmp.length;
		theDict = new String[size];
		for (int i=0;i<size;i++) {
			theDict[i] = tmp[i];
		}
		
	}
	
   
   //This constructor initialises the varible size to the size of the dictionery 
	WordDictionary() {
		size=theDict.length;
		
	}
	
   //This method is synchronized so that only one word at a time can access the method, changing 
	public synchronized String getNewWord() {
		int wdPos= (int)(Math.random() * size);
		return theDict[wdPos];
	}
	
}
