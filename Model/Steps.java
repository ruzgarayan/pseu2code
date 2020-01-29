package model;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Steps class : A set of steps to be used as substeps of a step.
 * @author Gülnihal Koruk
 * @since 11.05.2019
 */
public class Steps implements Serializable
{
   private static int stepLimit = 7;
   private Pseu2Code model;
   private ArrayList<Step> setOfSteps;
   private Path parentStepPath;
   
   
   //creates a new Steps with the given path
   public Steps(Pseu2Code model, Path parentStepPath)
   {
      this.model = model;
      this.parentStepPath = parentStepPath; 
      setOfSteps = new ArrayList<>();
   }
   
   /**
    * @return False if the number of steps in the arraylist is more than this stepLimit. 
    * @param stepLimit Max limit for Steps
    * */ 
   public boolean canSetLimit(int stepLimit) 
   {
      if ( stepLimit < setOfSteps.size()  )
         return false; 
      return true;
   }
   
   /**
    * changes the max limit for steps
    * @param stepLimit New limit for Steps
    */
   public static void setStepLimit(int newStepLimit) 
   {
      stepLimit = newStepLimit;
   }
   
   /**
    * Gets the valid stepLimit 
    * @return Valid stepLimit
    */
   public int getStepLimit() 
   {
      return stepLimit;
   }
   
   /**
    * @return The arraylist that store the Steps
    */
   public ArrayList<Step> getSteps() 
   {
      return setOfSteps;
   }
   
   /**
    * @param index Desired step's index
    * @return This Step
    */
   public Step getStep(int index) 
   {
      return setOfSteps.get(index);
   }
   
   /**
    * Adds a new Step to the arraylist using the constructor: Step(Path parentPath, int stepNumber) 
    * where the stepNumber is 1 for the first element.
    * @return True if the new step can be added
    */
   public boolean addStep() 
   {
      if (setOfSteps.size() < stepLimit)
      {
         setOfSteps.add(new Step(model ,parentStepPath, setOfSteps.size() + 1));
         
         model.updateView();
         return true;
      }
      return false;
   }
   
   /**
    * Removes the step at the given index
    * @param step The step that will be removed
    * @return True if the step is removed
    */
   public boolean removeStep(Step step) 
   {
      for (int i = 0; i < setOfSteps.size(); i++)
      {
         if (setOfSteps.get(i) == step)
         {
            setOfSteps.remove(i);
            updateStepPaths();
            
            model.updateView();
            return true;
         }
      }
      
      model.updateView();
      return false;
   }
   
   /**
    * In the setOfSteps(ArrayList), swap the element in the given index with the one before it
    * Example: If the arraylist is [a, b, c, d] and stepMoveUp(1) is called, 
    * Swap the element in the index 1 with the index 0, so the arraylist becomes [b, a, c, d].
    * @param stepIndex The index of the step that will move up
    */
   public void stepMoveUp(int stepIndex) 
   {
      if ( stepIndex != 0)
      {
         //Step willUp = getStep(stepIndex);
         //getStep(stepIndex) = getStep(stepIndex-1);
         //getStep(stepIndex-1) = willUp;
         
         Step willUp = getStep(stepIndex);
         Step willDown = getStep(stepIndex-1);
         setOfSteps.set(stepIndex-1, willUp);
         setOfSteps.set(stepIndex, willDown);
         
         updateStepPaths();
      }            
   }
   
   /**
    * In the setOfSteps(ArrayList), swap the element in the given index with the one after it
    * Example: If the arraylist is [a, b, c, d] and stepMoveDown(3) is called,
    * Swap the element in the index 3 with the index 2, so the arraylist becomes [a, b, d, c].
    * Be careful if this is called for the first element in the arraylist.
    * @param stepIndex The index of the step that will move down
    */
   public void stepMoveDown(int stepIndex) 
   {
      if ( stepIndex < setOfSteps.size()-1 )
      {
         //Step willDown = getStep(stepIndex);
         //getStep(stepIndex) = getStep(stepIndex+1);
         //getStep(stepIndex+1) = willDown;
         
         Step willDown = getStep(stepIndex);
         Step willUp = getStep(stepIndex+1);  
         setOfSteps.set(stepIndex+1, willDown);
         setOfSteps.set(stepIndex, willUp);
         
         updateStepPaths();
      }
   }
   
   /**
    * After a change is made in the order of steps,
    * this method updates their paths.
    */
   private void updateStepPaths()
   {
      for (int i = 0; i < setOfSteps.size(); i++)
      {
         setOfSteps.get(i).setPath(new Path(parentStepPath, i + 1));
         if (setOfSteps.get(i).isExpanded())
         {
            setOfSteps.get(i).getSubsteps().setParentPath(setOfSteps.get(i).getPath());
            setOfSteps.get(i).getSubsteps().updateStepPaths();
         }
      }
      model.updateView();
   }
   
   /**
    * @return Size of the arraylist
    */
   public int getSize() 
   {
      return setOfSteps.size();
   }
   
   /**
    * Changes the parent step path.
    * @param parentStepPath new path.
    */
   public void setParentPath(Path parentStepPath) 
   {
      this.parentStepPath = parentStepPath;
   }
   
   /**
    * @return Parent step's path
    */
   public Path getParentPath() 
   {
      return parentStepPath;
   }
   
   public void setModel(Pseu2Code model)
   {
      this.model = model;
      //Change the models of each step inside the arraylist.
      for (Step s : setOfSteps)
      {
         s.setModel(model);
      }
   }
}

