package EingabeModule;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystemNotFoundException;


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
	    	 System.out.println("sadsa");
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
