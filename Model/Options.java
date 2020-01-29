package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.JFileChooser;
import java.io.Serializable;

/**
 * Saves and loads also retrieves the user settings of the program.
 * @author Cankat
 * @since 12.05.2019
 */
public class Options implements Serializable {
   
   public static final int JAVA = 1;
   public static final int C = 2;
   public static final int PYTHON = 3;
   
   private final Properties properties;
   private static final String fileLocation = System.getProperty("user.dir") + "\\properties.data";
   
   Options() {
      properties = new Properties();
   }
   
   public static void main(String[] args) { // Just for test purposes
      Options o = Options.loadOptions();
      o.save();
   }
   
   // Save the properties to the root directory of program,
   // System.getProperty("user.dir").
   /**
    * @return whether the file was succesfully saved
    */
   public boolean save() {
      try {
         OutputStream save = new FileOutputStream(fileLocation); // the save location of this file is final and is
         // right next to the jar file
         properties.store(save, "Do Not Delete This File\nThis file contains the properties of Psue2Code"); // this
         // saves
         // the
         // properties
         // unto
         // a
         // file
         return true; // and returns true if it is able to save
      } catch (IOException e) {
         return false; // for some reason if it can't save it will return false
      }
   }
   
   // Loads the options and returns it
   /**
    * @return the saved Options if they are present
    */
   public static Options loadOptions() {
      Options o;
      boolean creation;
      boolean exists;
      File saveLocation;
      saveLocation = new File((System.getProperty("user.dir") + "\\Pseu2Code\\"));
      o = new Options();
      try {
         InputStream load = new FileInputStream(fileLocation); // if the properties are found in a save file
         o.properties.load(load); // loads the properties
      } catch (IOException e) {
         o.setFont("Calibri MS"); // otherwise loads the default properties
         o.setFontSize(13);
         o.setProgrammingLanguage("JAVA");
         
         exists = saveLocation.exists(); // checks if it exists
         creation = saveLocation.mkdir(); // for save location the default creates a folder
         if (exists)
            o.setSaveLocation(saveLocation.getPath()); // if it successfully creates a folder it will save that
         else if (creation)
            o.setSaveLocation(saveLocation.getPath()); // else it will save the default saving location.
         else
            o.setSaveLocation(System.getProperty("user.dir"));
      }
      return o; // and returns the options
   }
   
   /**
    * @param saveLocation, the location of the file to save the project
    */
   public void setSaveLocation(String saveLocation) {
      properties.setProperty("saveLocation", saveLocation);
   }
   
   /**
    * @param font, the font of the project
    */
   public void setFont(String font) {
      properties.setProperty("font", font);
   }
   
   public void setFontSize(int size) {
      properties.setProperty("fontSize", (size + ""));
   }
   
   public void setProgrammingLanguage(String language) {
      properties.setProperty("programmingLanguage", language);
   }
   
   public int getProgrammingLanguage() {
      String language;
      language = properties.getProperty("programmingLanguage");
      if (language.equals("JAVA")) {
         return Options.JAVA;
      } else if (language.equals("PYTHON")) {
         return Options.PYTHON;
      } else {
         return Options.C;
      }
   }
   
   public String getLanguageName() {
      return properties.getProperty("programmingLanguage");
   }
   
   public int getFontSize() {
      return Integer.parseInt(properties.getProperty("fontSize"));
   }
   
   public String getFont() {
      return properties.getProperty("font");
   }
   
   public String getSaveLocation() {
      return properties.getProperty("saveLocation");
   }
}
