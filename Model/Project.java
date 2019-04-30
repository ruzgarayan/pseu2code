package Model;

import java.util.ArrayList;

//Ruzgar
public class Project implements Serializable
{
   private Steps initialSteps;
   private String projectName;
   private ArrayList<Variable> variables;
   private int stepLimit;
   private Steps selectedCurrentSteps;
   private Steps selectedParentSteps;  
   
   public Project(String projectName, int stepLimit) {}
   
   public Steps getInitialSteps() {}
   
   public ArrayList<Variable> getVariables() {}
   public void addVariable(Variable variable) {}
   
   public void setSelectedCurrentSteps(Steps steps) {}
   public void setSelectedParentSteps(Steps steps) {}
   
   public Steps getSelectedCurrentSteps() {}
   public Steps getSelectedParentSteps() {}
   
   public boolean canSetLimit(int stepLimit) {}
   public void setStepLimit(int stepLimit) {}
   
   public ArrayList<Step> getAllStepsInOrder() {}
   
   public ArrayList<Variable> getVariablesUnderSteps(Steps steps) {}
      
   public void generateComments(int programmingLanguage, String saveLocation) {}
   
}
