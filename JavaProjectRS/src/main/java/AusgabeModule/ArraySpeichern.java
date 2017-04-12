package AusgabeModule;

import java.io.FileOutputStream;

import java.io.ObjectOutputStream;


public class ArraySpeichern {
  public static void save(String [][] benutzerarray){
	  try{
		  ObjectOutputStream os = new ObjectOutputStream( new FileOutputStream("benutzer.txt"));
          os.writeObject(benutzerarray);
          os.close();
	  }
	  catch(Exception e){
		  
	  }
  }
 public static void save(String [][][] wohungsarray){
	  try{
		  ObjectOutputStream os = new ObjectOutputStream( new FileOutputStream("wohnungen.txt"));
          os.writeObject(wohungsarray);
          os.close();
	  }
	  catch(Exception e){
		  
	  }
  }
 
 public static void saveKatalog(String[][] katalogArray){
	  try{
		  System.out.println("");
		 
		  ObjectOutputStream os = new ObjectOutputStream( new FileOutputStream("katalog.txt"));
         os.writeObject(katalogArray);
         os.close();
	  }
	  catch(Exception e){
		  System.out.println("wHY");
	  }
 }
}
