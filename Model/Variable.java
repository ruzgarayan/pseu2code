package model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.*;
import java.io.Serializable;

/**
 * Variables class that holds several properties in it to represent a variable.
 * @author Ilhan Koc
 * @since 11.05.2019
 */
public class Variable implements Serializable {
   private Pseu2Code model;
   
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
   public Variable(Pseu2Code model, String name, String type, Path path) {
      this.model = model;
      this.name = name;
      this.type = type;
      this.path = path;
      
      int R = (int)(Math.random()*256);
      int G = (int)(Math.random()*256);
      int B = (int)(Math.random()*256);
      this.color = new Color(R, G, B);
      
      
   }
   
   public void setName(String name) {
      this.name = name;
      model.updateView();
   }
   
   public String getName()
   {
      return this.name;
   }
   
   public void setType(String type)
   {
      this.type = type;
      model.updateView();
   }
   
   public String getType() {
      return this.type;
   }
   
   public Color getColor() {
      return this.color;
   }
   
   public Path getPath()
   {
      return path;
   }
   
   public boolean equals(Variable variable)
   {
      if(this.getName() != variable.getName())
         return false;
      if(this.getType() != variable.getType())
         return false;
      if(this.getColor() != variable.getColor())
         return false;
      return true;
   }
}
