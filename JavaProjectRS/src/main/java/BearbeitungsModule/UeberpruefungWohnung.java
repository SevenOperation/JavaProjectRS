 package BearbeitungsModule;



//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Scanner;



public class UeberpruefungWohnung {



static void ueberpruefungWonhungen(){

String wohnung [][][]={{{"12.03.17-13 .04.17","10.02.17-13.02.17"},{"14.04.17-23.05.17","12.06.17-13.06.17"}}};	
boolean a=false;
String aber;
aber=eingabeDatum();

a=kontrolle(wohnung,aber);
ausgabe(a);

//datumsValidierung();
}
//Kleine Method zur Datumsvalidierung als hilfestellung #MirwarLangweillig
/*public static void datumsValidierung(){
	Scanner sc = new Scanner(System.in);
	String d = sc.next();
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
	sdf.setLenient(false);
	try {
		Date date = sdf.parse(d);
		System.out.println(sdf.parseObject(d));
		if(date.after(new Date())){
		
			System.out.println("Ihre Datum eingabe war richtig");
			date.after(new Date());
		}
		
	} catch (ParseException e) {
		System.out.println("Datum ist nicht richtig");
	}

}*/	


public static boolean  datumsUeberpruefen (String date){   // Methode zur Datumsüberprüfung
	
	String split[]=date.split("-");
	boolean a=true;
	SimpleDateFormat df =new SimpleDateFormat("dd.MM.yyyy");
    df.setLenient(false);	
	try{
	Date d3=df.parse(split[0]);
	Date d4=df.parse(split[1]);
	System.out.println("Datumseingabe Korrekt");
	//a=false; Why if it throws an exception than it is wrong but when not it is correct
	}catch(ParseException ex){
		System.out.println("Falsche  Datums Eingabe, bitte Wiederholen sie ihre Eingabe");
		a = false;
		return a;
	}	

	 return a;
	}
static String eingabeDatum(){  // Methode zu Datums Eingabe und zur Überprüfung der richtigen Notation
	Scanner sc = new Scanner (System.in);
	boolean a=true;
	String dt2;
	do{
	System.out.println("Datum eingeben in der Form 12.03.2017-13.03.2017");
	
	 dt2=sc.next();	
	if(dt2.matches("(\\d{2}[.]{1}\\d{2}[.]{1}\\d{4}[-]{1}\\d{2}[.]{1}\\d{2}[.]{1}\\d{4})")){
		
	 a=datumsUeberpruefen(dt2);
	} else{
		System.out.println("Eingabe entsprich nicht der Vorgabe");
		a=false;
	}
	 
	
	}while(a==false);
	return dt2;
}


public static boolean kontrolle(String wohn[][][],String dt2){ // Methode zur Kontrolle ob Wohnungen zu dem angegeben Zeitraum frei sind.
	

boolean a=false;
String eingabeDatum[]=dt2.split("-");
SimpleDateFormat df =new SimpleDateFormat("dd.MM.yy");
df.setLenient(false);
try{
	
Date d1=df.parse(datum[0]);
Date d2=df.parse(datum[1]);
Date d3=df.parse(eingabeDatum[0]);
Date d4=df.parse(eingabeDatum[1]);
Date d5=df.parse(datum2[0]);
Date d6=df.parse(datum2[1]);

if ((d3.compareTo(d1)>=0 )&& (d3.compareTo(d2)<=0)||(d4.compareTo(d1)>=0 )&& (d4.compareTo(d2)<=0)||(d3.compareTo(d5)>=0 )&& (d3.compareTo(d6)<=0)||(d4.compareTo(d5)>=0 )&& (d4.compareTo(d6)<=0)){

	a=true;
}

}catch(ParseException ex){
	System.out.println("Falsches Datum");
	
}
return a;
}


public static void ausgabe(boolean b){
 if(b==false){
	System.out.println("Wohnungen sind Frei");
	}else
		System.out.println("Wonhungen sind zu dem angegeben Zeitpunkt belegt");
	
}	
	
}
