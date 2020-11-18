package edu.lewisu.cs.kylevbye.util.io;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TextFileReader {
	
	///
	///	Fields
	///
	private String fileName;
	private String[] data;
	
	///
	///	Getters
	///
	public String getFileName() {
		return fileName;
	}
	
	public String[] getData() {
		return data;
	}
	
	///
	///	Setters
	///
	public void setFileName(String fileNameIn) {
		fileName = fileNameIn;
	}
	
	public void setData(String[] data) {
		this.data = data;
	}
	
	///
	///	Functions
	///
	/**
	 * Returns true if successfull
	 */
	public boolean readFile() {
		
		//	Initialization and checks
		if (fileName == null || fileName.equals(new String())) return false;;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new BufferedReader(new FileReader(new File(fileName))));
		}
		catch (FileNotFoundException fnfe) {
			return false;
		}
		
		//	Read until end of file
		ArrayList<String> workingData = new ArrayList<String>();
		String line;
		try { 
			while ((line = reader.readLine()) != null) workingData.add(line); 
			reader.close();
		}
		catch (IOException ioe) { return false; }
		
		
		//	Set data field accordingly
		setData(new String[workingData.size()]);
		for (int i = 0; i<workingData.size(); ++i) data[i] = workingData.get(i);
		
		//	Indicate that this method executed successfully
		return true;
	}
	
	/**
	 * 
	 * @param fileNameIn
	 * @return
	 */
	public boolean readFile(String fileNameIn) {
		setFileName(fileNameIn);
		return readFile();
	}
	
	/**
	 * Returns true if the data field isn't null and its
	 * length is greater than 0. Otherwise, false is returned.
	 * 
	 * @return	true/false
	 */
	public boolean dataIsEmpty() {
		return data == null || data.length == 0;
	}

	///
	///	Constructors
	///
	public TextFileReader() {
		setFileName("");
		setData(null);
		
	}
	
	public TextFileReader(String fileNameIn) {
		this();
		setFileName(fileNameIn);
	}

}
