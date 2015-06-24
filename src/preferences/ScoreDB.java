package preferences;
import game.GamePanel.Diff;

import java.sql.*;
import java.util.ArrayList;

/**
 * Klasse fuer die Datenbank zum Speichern der Highscores
 */
public class ScoreDB {
	
	private Connection c;
	private DatabaseMetaData dbm;
	Statement stm;
	
	private boolean ready = false;

	/**
	 * Constructor
	 * Verbindung zur Datenbank 
	 */
	public ScoreDB(Diff diff){
		c = null;		
		try {
		      Class.forName("org.sqlite.JDBC"); // Laedt die Klasse JDBC
		      c = DriverManager.getConnection("jdbc:sqlite:score.db");	// Verbindet mit der score.db 
		  	  stm = c.createStatement(); // Statement
		  	  
		  	  // Datenbank durchsuchen
		      if(c != null){			    	
						dbm = c.getMetaData(); // Meta Daten
						
						/**
						 *  Wenn der uebergebene Schwierigkeitsgrad EASY ist, wird ein Datensatz fuer diesen erstellt
						 */
						if (diff.equals(Diff.EASY)) {
							ResultSet tablesE = dbm.getTables(null, null, "SCORETABLE", null); // Waehlt den Datensatz
						if (tablesE.next())	ready = true; 	// Wenn die Tabelle existiert, bereit 				
						else {						 		// Wenn die Tabelle nicht existiert, neu
						  // SQL Statement 
						  String sql = "CREATE TABLE SCORETABLE " +
				                   "(ID INTEGER PRIMARY KEY   AUTOINCREMENT," +
				                   " DIFF           TEXT    NOT NULL, " +
				                   " NAME           TEXT    NOT NULL, " + 
				                   " SCORE          INT     NOT NULL, " + 
				                   " DATE          	TEXT    NOT NULL) "; 
						  stm.executeUpdate(sql); // Ausfuehren des Statements
						  ready = true;			  // Bereit 
						}
						}
						
						/**
						 *  Wenn der uebergebene Schwierigkeitsgrad MEDIUM ist, wird ein Datensatz fuer diesen erstellt
						 */
						if (diff.equals(Diff.MEDIUM)) {
							ResultSet tablesM = dbm.getTables(null, null, "SCORETABLEMED", null); // Waehlt den Datensatz 
						if (tablesM.next())	ready = true; 	// Wenn die Tabelle existiert, bereit 				
						else {						 		// Wenn die Tabelle nicht existiert, neu
							// SQL Statement
						  String sql = "CREATE TABLE SCORETABLEMED " +
				                   "(ID INTEGER PRIMARY KEY   AUTOINCREMENT," +
				                   " DIFF           TEXT    NOT NULL, " +
				                   " NAME           TEXT    NOT NULL, " + 
				                   " SCORE          INT     NOT NULL, " + 
				                   " DATE          	TEXT    NOT NULL) "; 
						  stm.executeUpdate(sql); // Ausfuehren des Statements
						  ready = true;			  // Bereit
						}
						}
						
						/**
						 *  Wenn der uebergebene Schwierigkeitsgrad HARD ist, wird ein Datensatz fuer diesen erstellt
						 */
						if (diff.equals(Diff.HARD)) {
							ResultSet tablesH = dbm.getTables(null, null, "SCORETABLEHARD", null); // Waehlt den Datensatz  
						if (tablesH.next())	ready = true; 	// Wenn die Tabelle existiert, bereit 			
						else {						 		// Wenn die Tabelle nicht existiert, neu
						  // SQL Statement 
						  String sql = "CREATE TABLE SCORETABLEHARD " +
				                   "(ID INTEGER PRIMARY KEY   AUTOINCREMENT," +
				                   " DIFF           TEXT    NOT NULL, " +
				                   " NAME           TEXT    NOT NULL, " + 
				                   " SCORE          INT     NOT NULL, " + 
				                   " DATE          	TEXT    NOT NULL) "; 
						  stm.executeUpdate(sql); // Ausfuehren des Statements
						  ready = true;			  // Bereit
						}
						}
						
		      } 
				
		    } catch (SQLException | ClassNotFoundException e) {
		    	System.out.println("Exception occurred while connectiong to the database:");
		    	e.printStackTrace();
		    	ready = false; // Falls es einen Error gab, nicht bereit
		    }
		
	 }
	
	/**
	 * Fuegt die Daten in die Tabelle ein
	 * @param diff  Schwierigkeitsgrad
	 * @param name  Name
	 * @param time 	Zeit
	 * @param date  Datum
	 */
	public void insertScore(Diff diff, String name, long time, String date){
	  if(ready){
		try {
			// SQL Anweisung, wenn der Schwierigkeitsgrad EASY ist, in den zugehoerigen Datensatz schreiben
			if (diff.equals(Diff.EASY)) {
			String sql = "INSERT INTO SCORETABLE (DIFF,NAME,SCORE,DATE) " +
	            "VALUES (" +
				"'" + diff + "','" + name + "','" + time + "','" + date + "');"; 	
			
			stm.executeUpdate(sql); // Fuehrt Anweisung aus
			}
			// SQL Anweisung, wenn der Schwierigkeitsgrad MEDIUM ist, in den zugehoerigen Datensatz schreiben
			if (diff.equals(Diff.MEDIUM)) {
			String sql = "INSERT INTO SCORETABLEMED (DIFF,NAME,SCORE,DATE) " +
	            "VALUES (" +
				"'" + diff + "','" + name + "','" + time + "','" + date + "');"; 	
			
			stm.executeUpdate(sql); // Fuehrt Anweisung aus
			}
			// SQL Anweisung, wenn der Schwierigkeitsgrad HARD ist, in den zugehoerigen Datensatz schreiben
			if (diff.equals(Diff.HARD)) {
			String sql = "INSERT INTO SCORETABLEHARD (DIFF,NAME,SCORE,DATE) " +
	            "VALUES (" +
				"'" + diff + "','" + name + "','" + time + "','" + date + "');"; 	
			
			stm.executeUpdate(sql); // Fuehrt Anweisung aus
			}
		} catch (SQLException e) {
	    	System.out.println("Exception occurred while inserting score to the database:");
	    	e.printStackTrace();
		}
	  }			
	}
	
	/**
	 * Loescht die Daten aus der Datenbank
	 */
	public void clearTable(Diff diff){
	  if(ready){
		try{
			// Fuer den jeweilige Schwierigkeitsgrad
			if (diff.equals(Diff.EASY)) {
			String sql = "DELETE FROM SCORETABLE"; // SQL Anweisung
			stm.executeUpdate(sql); // Fuehrt die Anweisung aus
			}
			if (diff.equals(Diff.MEDIUM)) {
			String sql = "DELETE FROM SCORETABLEMED"; // SQL Anweisung
			stm.executeUpdate(sql); // Fuehrt die Anweisung aus
			}
			if (diff.equals(Diff.HARD)) {
			String sql = "DELETE FROM SCORETABLEHARD"; // SQL Anweisung
			stm.executeUpdate(sql); // Fuehrt die Anweisung aus
			}
		} catch (SQLException e){
	    	System.out.println("Exception occurred while clearing table:");
	    	e.printStackTrace();
		}
	  }
	}
	
	/**
	 * Holt die eingetragenen Daten
	 * @return Liste Schwierigkeit, Zeit, Name, Datum
	 */
	public ArrayList<ArrayList<String>> seclectScores(Diff diffi){
	  if(ready){
		ArrayList<ArrayList<String>> nodes = new ArrayList<ArrayList<String>>(); // Erzeugt eine ArrayList
		String sql = null;
		try{
		// Fuer jeden Schwierigkeitsgrad
		if (diffi.equals(Diff.EASY)) {
		sql = "SELECT * FROM SCORETABLE ORDER BY SCORE ASC;"; // SQL Anweisung 
		}
		if (diffi.equals(Diff.MEDIUM)) {
		sql = "SELECT * FROM SCORETABLEMED ORDER BY SCORE ASC;"; // SQL Anweisung  
		}
		if (diffi.equals(Diff.HARD)) {
		sql = "SELECT * FROM SCORETABLEHARD ORDER BY SCORE ASC;"; // SQL Anweisung  
		}
		ResultSet rs = stm.executeQuery(sql); // Fuehrt die Anweisung aus und speichert
		while(rs.next()){
			ArrayList<String> i = new ArrayList<String>(); // Neue ArrayList
			
			// Holt die Daten in String
			String diff = rs.getString("DIFF");
			String name = rs.getString("NAME"); 
			String score = new Integer(rs.getInt("SCORE")).toString();
			String date = rs.getString("DATE");
			
			// Fuegt die Daten hinzu
			i.add(diff);
			i.add(name);
			i.add(score);
			i.add(date);
			
			nodes.add(i); // Fuegt die Liste der Liste hinzu 
		}				
		
		return nodes; // Returns "nodes"  
		
		} catch (SQLException e){
	    	System.out.println("Exception occurred while reading results from table:");
	    	e.printStackTrace();
			return null;
		}		
	  }
	  return null; // Wenn die Datenbank nicht ready ist, null 
	}
	
	/**
	 *  Schlieﬂt die Verbindung zur Datenbank
	 */
	public void close(){
		try {
			if (ready) c.close(); // Schlieﬂt
		} catch (SQLException e) {
	    	System.out.println("Exception occurred while closing the database:");
	    	e.printStackTrace();
	    	c = null;
		}
	}
	
	
	
}

