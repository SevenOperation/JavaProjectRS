package EingabeModule;

/**
 * Date: 31.5.17
 * @author Maurice Fernitz
 * @version 0.9
 * Name: JavaProjectRS
 * Klasse FS62
 */

import java.io.FileInputStream;
import java.io.ObjectInputStream;
//Handles the whole read and write thing for the arrays
public class ArrayEinlesen {
	//reads all users from a file and put them into an array (if a file exists)
	public static String[][] readBenutzer(){
		 ObjectInputStream is;
		 String [][] benutzerarray = null;
	     try {
	         is = new ObjectInputStream(new FileInputStream("benutzer.txt"));
	         benutzerarray = (String[][]) is.readObject();
	         is.close();
	         return benutzerarray;

	     }
	     catch (Exception e) {
	    
	        return null;
	     }
	  }
	//reads all houses from a file and put them into an array (if a file exists)
	 public static String[][][] readWohnungen(){
		 ObjectInputStream is;
		 String [][][] wohungsarray;
	     try {
	         is = new ObjectInputStream(new FileInputStream("wohnungen.txt"));
	         wohungsarray = (String[][][]) is.readObject();
	         is.close();
	         return wohungsarray;

	     }
	     catch (Exception e) {
	         return null;
	     }
	  } 
	//reads the catalog from a file and put it into an array (if a file exists)
	 public static String[][] readKatalog(){
		 ObjectInputStream is;
		 String [][] katalogArray;
	     try {
	         is = new ObjectInputStream(new FileInputStream("katalog.txt"));
	         katalogArray = (String[][]) is.readObject();
	         is.close();
	         return katalogArray;

	     }
	     catch (Exception e) {
	         return null;
	     }
	  } 
	
	
}
