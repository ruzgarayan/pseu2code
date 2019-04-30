package Model;

import java.util.ArrayList;

//Each individual step in a project.
//Rüzgar
public class Step
{
   private Path path;  
   
   //Text that is written for this step.
   private String text;
   
   private Steps substeps;
   
   //Stars with false, becomes true when the expand() method is called.
   private boolean isExpanded;
   
   //substeps is not initialized in constructors.
   public Step(Path parentPath, int stepNumber) {
      path = new Path(parentPath, stepNumber);
      text = "";
      isExpanded = false;
   }
   public Step(Step parentStep, int stepNumber) {
      this(parentStep.getPath(), stepNumber);
   }
   
   public void setText(String text) {
      this.text = text;
   }
   public String getText() {
      return text;
   }
   
   public void changePath(Path path) {
      this.path = path;
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
      substeps = new Steps(path);
      isExpanded = true;
      addSubstep();
   }
   
   //Can use the addStep() method of substeps.
   public void addSubstep() {
      substeps.addStep();
   }
   
   //For example: Step1.2 (Can use the toString() method of path).
   public String toString() {
      return "Step" + path.toString();
   }
}
