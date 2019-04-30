package Model;

import java.util.Properties;

//Cankat
public class Options
{
  public static final int JAVA = 1;
  public static final int C = 2;
  public static final int PYTHON = 3;
  
  private Properties properties;
  
  //Save the properties to the root directory of program, System.getProperty("user.dir").
  public void save() {}
  //Loads the options and returns it
  public static Options loadOptions() {}
  
  /*setters and getters for 
  String saveLocation;
  int programmingLanguage;
  String font;
  int fontSize;
  */
}
