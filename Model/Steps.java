package Model;

import java.util.ArrayList;

//A set of steps to be used as substeps of a step.
//Gülnihal
public class Steps
{
   private static int stepLimit;
   
   private ArrayList<Step> setOfSteps;
   private Path parentStepPath;
   
   public Steps(Path parentPath, int stepLimit) {}
   
   //Returns false if the number of steps in the arraylist is more than this stepLimit.  
   public boolean canSetLimit(int stepLimit) {}
   public void setStepLimit(int stepLimit) {}
   public int getStepLimit() {}
   
   public Step getStep(int index) {}
   //Adds a new Step to the arraylist using the constructor: Step(Path parentPath, int stepNumber)
   //where the stepNumber is 1 for the first element.
   public boolean addStep() {}
   public boolean removeStep(int index) {}
   
   //Swaps the element of the arraylist in the given index, with one below or above it.
   //Also change their paths.
   public void stepMoveUp(int stepIndex) {}
   public void stepMoveDown(int stepIndex) {}
   
   public int getSize() {}
   
   public Path getParentPath() {}
}
