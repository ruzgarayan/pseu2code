package Model;

import java.awt.Color;

//Ilhan
public class Variable
{
   //Color of the variable to be shown in the UI.
   private Color color;
 
   //Name of the variable that will be also used in generated code as the variable name.
   //These names will be colored in the UI.
   private String name;
   
   //Type of the variable. For example it can be Integer or Boolean.
   //This will also be used in generated code to determine the variable type in different progrraming languages.
   private String type;
   
   //The Path of the parent of Steps object which this variables was created at.
   //For example if this variable is created under the substeps of Step 1.4, this path is [1, 4].
   //This will be used in the UI to determine if this variables should be shown at that moment. 
   private Path path;
   
   //Just initialize the variables, give them different colors.  
   public Variable(String name, String type, Path path) {}
   
   public void setName(String name) {}
   public String getName() {}
   
   public void setType(String type) {}
   public String getType() {}
   
   
   public Color getColor() {}
}
