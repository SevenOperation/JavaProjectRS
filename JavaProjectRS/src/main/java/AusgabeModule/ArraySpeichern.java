package AusgabeModule;
/**
 * Date: 31.5.17
 * @author Maurice Fernitz
 * @version 0.9
 * Name: JavaProjectRS
 * Klasse FS62
 */

import java.io.FileOutputStream;

import java.io.ObjectOutputStream;
//Handles everything which has to do with saving
public class ArraySpeichern {
	//saves the user array to a text file
  public static void save(String [][] benutzerarray){
	  try{
		  ObjectOutputStream os = new ObjectOutputStream( new FileOutputStream("benutzer.txt"));
          os.writeObject(benutzerarray);
          os.close();
	  }
	  catch(Exception e){
		  
	  }
  }
  //saves the house array to a text file
 public static void save(String [][][] wohungsarray){
	  try{
		  ObjectOutputStream os = new ObjectOutputStream( new FileOutputStream("wohnungen.txt"));
          os.writeObject(wohungsarray);
          os.close();
	  }
	  catch(Exception e){
		  
	  }
  }
 //saves the catalog array to a text file
 public static void saveKatalog(String[][] katalogArray){
	  try{
		  ObjectOutputStream os = new ObjectOutputStream( new FileOutputStream("katalog.txt"));
         os.writeObject(katalogArray);
         os.close();
	  }
	  catch(Exception e){
		  
	  }
 }
}
