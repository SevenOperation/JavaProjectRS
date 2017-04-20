package EingabeModule;

import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class ArrayEinlesen {
	public static String[][] readBenutzer(){
		 ObjectInputStream is;
		 String [][] benutzerarray = null;
	     try {
	         is = new ObjectInputStream(new FileInputStream("benutzer.txt"));
	         if(is.readObject() != null){
	         benutzerarray = (String[][]) is.readObject();
	         }
	         is.close();
	         return benutzerarray;

	     }
	     catch (Exception e) {
	        return null;
	     }
	  }
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
	
	 public static String[][] readKatalog(){
		 ObjectInputStream is;
		 String [][] katalogArray;
	     try {
	    	 System.out.println("Session begin");
	         is = new ObjectInputStream(new FileInputStream("katalog.txt"));
	         katalogArray = (String[][]) is.readObject();
	         is.close();
	         return katalogArray;

	     }
	     catch (Exception e) {
	    	 System.out.println("wHY");
	         return null;
	     }
	  } 
	
	
}
