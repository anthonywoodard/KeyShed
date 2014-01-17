package com.anthonywoodard.keyshed.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anthony Woodard
 */
public class ReadCSV {
  BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
 
  public List<String[]> run(String csvFile) {
    List<String[]> rows = new ArrayList();
    try {
      br = new BufferedReader(new FileReader(csvFile));
      while ((line = br.readLine()) != null) {        
        // use comma as separator
        String[] row = line.split(cvsSplitBy); 
        rows.add(row);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
        }
      }
    }
    return rows;
  }
}
