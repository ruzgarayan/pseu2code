package model;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Each individual step in a project.
 * This also has set of steps in it. But it is only initialized when expand() method is called, not in the constuctor.
 * @author Ruzgar Ayan
 * @since 11.05.2019
 */
public class Step implements Serializable
{
   private Pseu2Code model;
   
   private Path path;  
   
   //Text that is written for this step.
   private String text;
   
   private Steps substeps;
   
   //Stars with false, becomes true when the expand() method is called.
   private boolean isExpanded;
   
   //substeps is not initialized in constructors.
   public Step(Pseu2Code model, Path parentPath, int stepNumber) {
      this.model = model;
      path = new Path(parentPath, stepNumber);
      text = "";
      isExpanded = false;
   }
   public Step(Pseu2Code model, Step parentStep, int stepNumber) {
      this(model, parentStep.getPath(), stepNumber);
   }
   
   public void setText(String text) {
      this.text = text;
   }
   
   public String getText() {
      return text;
   }
   
   public Path getPath() {
      return path;
   }
   
   public Steps getSubsteps() {
      return substeps;
   }
   
   public boolean isExpanded() {
      return isExpanded;
   }
   
   //Initializes the substeps and adds one substep to it.
   public void expand() {
      if (!isExpanded)
      {
         substeps = new Steps(model, path);
         addSubstep();
         isExpanded = true;
         
         model.updateView();
      }
   }
   
   //Can use the addStep() method of substeps.
   public void addSubstep() {
      substeps.addStep();
   }
   
   //For example: Step1.2 (Can use the toString() method of path).
   public String toString() {
      return "Step" + path.toString();
   }
   
   public void setPath(Path path)
   {
      this.path = path;
      
      model.updateView();
   }
   
   public void setSubsteps(Steps steps) {
      substeps = steps;
      isExpanded = true;
   }
   
   public void setModel(Pseu2Code model)
   {
      this.model = model;
      //If expanded, also change the model of its substeps.
      if (isExpanded)
         substeps.setModel(model);
   }
}
