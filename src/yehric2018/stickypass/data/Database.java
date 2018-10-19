package yehric2018.stickypass.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Database {
	private Map<String, Password> passwords;
	
	private File storage;
	private PrintStream output;
	
	private Set<String> savedSites;
	
	public Database() {
		passwords = new HashMap<String, Password>();
		savedSites = new HashSet<String>();
		
		storage = new File(".\\info\\passwords.txt");
		init();
		
		try {
			output = new PrintStream(storage);
			saveData();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		}
	}
	
	// Save the passwords to a database in passwords.txt
	public void saveData() {
		for (String site : passwords.keySet()) {
			if (!savedSites.contains(site)) {
				output.println(site + " " + passwords.get(site).getPlainText());
				savedSites.add(site);
			}
		}
	}
	
	// Add a new site to the database, matching with a corresponding password
	public void addSite(String site, String password) {
		passwords.put(site, new Password(password));
	}
	
	// Returns the password corresponding to the given site name
	// Throws IllegalArgumentException if not present
	public String getPassword(String site) {
		if (!passwords.containsKey(site)) {
			throw new IllegalArgumentException("Site not registered in database");
		}
		
		return passwords.get(site).getPlainText();
	}
	
	// Returns whether the database contains the given site
	public boolean containsSite(String site) {
		return passwords.containsKey(site);
	}
	
	private void init() {
		try {
			Scanner s = new Scanner(storage);
			
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] pair = line.split(" ");
				
				passwords.put(pair[0], new Password(pair[1]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}