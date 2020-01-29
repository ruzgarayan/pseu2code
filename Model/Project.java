package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Represents a single project of the program.
 * Holds initialSteps , a set of steps, to access everything in the project.
 * @author Ruzgar Ayan
 * @since 12.05.2019
 */
public class Project implements Serializable
{
   public static final int INDENTATION_LEVEL = 3;
   
   private Pseu2Code model;
   
   //Main steps in the project which eventually lets the project access every single step.
   private Steps initialSteps;
   
   //Name of the project.
   private String projectName;
   
   //Every variable that is defined for this project.
   private ArrayList<Variable> variables;
   
   //Limit of substeps each step can have.
   private int stepLimit;
   
   //These represent the steps objects that are currently selected,
   //will be used for the UI to know what will be displayed on the screen at any time.
   private Steps selectedCurrentSteps;
   private Steps selectedParentSteps;  
   
   public Project(Pseu2Code model, String projectName, int stepLimit) {
      this.model = model;
      this.projectName = projectName;
      this.stepLimit = stepLimit;
      
      //Initialize initialSteps with an empty Path.
      initialSteps = new Steps(model ,new Path());
      
      //Set the step limit for Steps class.
      initialSteps.setStepLimit(stepLimit);
      //Add the first step by hand.
      initialSteps.addStep();
      
      variables = new ArrayList<>();
      
      //Current steps is the initial steps at first, and there are no parent steps.
      selectedCurrentSteps  = initialSteps;
      selectedParentSteps  = null;
   }
   
   /**
    * @return project's name
    */
   public String getProjectName()
   {
      return projectName;
   }
   
   /**
    * Changes the project's name.
    * @param newName project's new name
    */
   public void setProjectName(String newName)
   {
      projectName = newName;
   }
   
   /**
    * @return the initial steps
    */
   public Steps getInitialSteps() {
      return initialSteps;
   }
   
   /**
    * @return all the variables
    */
   public ArrayList<Variable> getVariables() {
      return variables;
   }
   
   /**
    * Adds a variable to the project.
    * @param variable Variable to be added.
    */
   public void addVariable(Variable variable) {
      variables.add(variable);
   }
   
   /**Searches the variable for the given name in the project and return it.
     * @param variableName the name of a variable.
     * @return the variable found, or null if it not found.
     */
   public Variable getVariable(String variableName)
   {
      for (Variable v : variables)
      {
         if (v.getName().equals(variableName))
            return v;
      }
      return null;
   }
   
   /**Changes the selectedCurrentSteps property
     * @param new Steps property.
     */
   public void setSelectedCurrentSteps(Steps steps) {
      //Current steps cannot be null.
      if (steps != null)
      {
         selectedCurrentSteps = steps;
         if (steps.getParentPath() != null && steps.getParentPath().getParentPath() != null)
         {
            selectedParentSteps = getSteps(steps.getParentPath().getParentPath());
         }
         else
         {
            selectedParentSteps = null;
         }
      }
      model.updateView();
   }
   /**Changes the selectedParentSteps property
     * @param new Steps property.
     */
   public void setSelectedParentSteps(Steps steps) {
      selectedParentSteps = steps;
      model.updateView();
   }
   
   /**
    * @return currently selected current steps.
    */
   public Steps getSelectedCurrentSteps() {
      return selectedCurrentSteps;
   }
   
   /**
    * @return currently selected parent steps.
    */
   public Steps getSelectedParentSteps() {
      return selectedParentSteps;
   }
   
   /**If it is possible to set the stepLimit to the new given one, sets it.
     * @param stepLimit new step limit.
     */
   public void setStepLimit(int stepLimit) {
      if (canSetLimit(stepLimit))
      {
         this.stepLimit = stepLimit;
         Steps.setStepLimit(stepLimit);
         model.updateView();
      }
   }
   
   /**
    * Finds if the step limit of the project can be set to the given stepLimit.
    * @param stepLimit given step limit to be checked if it can be set.
    * @return true if it can be set, false if not.
    */
   private boolean canSetLimit(int stepLimit) {
      if (!initialSteps.canSetLimit(stepLimit))
         return false;
      
      ArrayList<Step> allSteps = getAllStepsInOrder();
      //If there are any Steps that's step limit cannot be set to given value, return false. 
      for (Step s : allSteps)
      {
         if (s.isExpanded() && (!s.getSubsteps().canSetLimit(stepLimit)))
            return false;
      }
      
      return true;
   }
   
   /**
    * @return the step limit of the project.
    */
   public int getStepLimit() {
      return stepLimit;
   }
   
   /**
    * Returns all the steps in the project in order.
    * @return All steps.
    */
   public ArrayList<Step> getAllStepsInOrder() {
      ArrayList<Step> steps= new ArrayList<>();
      for (int i = 0; i < initialSteps.getSize(); i++)
      {
         steps.addAll(getAllStepsInOrder(initialSteps.getStep(i)));
      }
      return steps;
   }
   
   /**
    * Returns all the steps in the project in order that are under the step s
    * @param s The parent step of all the other steps that will be returned..
    * @return All steps.
    */
   private ArrayList<Step> getAllStepsInOrder(Step s)
   {
      ArrayList<Step> steps= new ArrayList<>();
      steps.add(s);
      
      if (!s.isExpanded())
      {
         return steps;
      }
      
      for (int i = 0; i < s.getSubsteps().getSize(); i++)
      {
         steps.addAll(getAllStepsInOrder(s.getSubsteps().getStep(i)));
      }
      return steps;
   }
   
   /**
    * Returns the set of steps that should be shown on the screen,
    * according to the current selected steps.
    * @return steps that should be shown on the screen.
    */
   public ArrayList<Variable> getCurrentVariables() {
      ArrayList<Variable> variablesUnderSteps = new ArrayList<>();
      
      if (selectedParentSteps == null)
      {
         for (Variable v : variables)
         {
            if (v.getPath() == null)
            {
               variablesUnderSteps.add(v);
            }
         }
      }
      else
      {
         for (Variable v : variables)
         {
            if (v.getPath() == null)
            {
               variablesUnderSteps.add(v);
            }
            else if (selectedParentSteps.getParentPath().underThis(v.getPath()))
            {
               variablesUnderSteps.add(v);
            }
         }
      }
      return variablesUnderSteps;
   }
   
   /**
    * For the given programming language ,create and save a text file with the program's steps are added as comments.
    * Name of the text file is the projectName.
    * @param programmingLanguage The programming language the comments and the variables will be added at.
    * @param saveLocation Save location of the file.
    */
   public void generateComments(int programmingLanguage, String saveLocation) {
      ArrayList<Step> allSteps = getAllStepsInOrder();
      
      String write = "";
      
      
      for (Variable v : variables)
      {
         write = write + variableDeclarationToString(programmingLanguage, v) + "\n";
      }
      
      write = write + "\n";
      
      for (Step s : allSteps)
      {
         write = write + indentationForStep(s) + commentForLanguage(programmingLanguage) + s + " " +
            addCommentsToStepText(s.getText(), s, programmingLanguage) + "\n\n";
      }
      
      try
      {
         PrintWriter out = new PrintWriter(new File(saveLocation + "\\" + projectName + ".txt"));
         
         out.write(write);
         out.close();
      }
      catch ( IOException e)
      {
         System.out.println("Error generating comments.");
      }
   }
   
   /**
    * Helper method for generating comments.
    * Returns the correct syntax for commenting in the given programming language.
    * @param programmingLanguage The programming language for the wanted comment.
    * @return the syntax for commenting or null if the given programming language is not found.
    */
   private String commentForLanguage(int programmingLanguage)
   {
      if (programmingLanguage == Options.JAVA)
         return "//";
      if (programmingLanguage == Options.C)
         return "//";
      if (programmingLanguage == Options.PYTHON)
         return "#";
      return "";
   }
   
   /**
    * Helper method for generating comments.
    * Returns the declaring statement of the given variable in the given programming language.
    * @param programmingLanguage The programming language for the wanted statement.
    * @param v Variable in the statement
    * @return the declaring statement.
    */
   private String variableDeclarationToString(int programmingLanguage, Variable v)
   {
      String result = "";
      if (programmingLanguage == Options.JAVA)
      {
         if (v.getType().equals("Integer"))
            result = result + "int ";
         else if (v.getType().equals("String"))
            result = result + "String ";
         else if (v.getType().equals("Boolean"))
            result = result + "boolean ";
         else if (v.getType().equals("Character"))
            result = result + "char ";
         else if (v.getType().equals("Double"))
            result = result + "char ";
         
         result = result + v.getName() + ";";
      }
      
      else if (programmingLanguage == Options.C)
      {
         if (v.getType().equals("Integer"))
            result = result + "int ";
         else if (v.getType().equals("String"))
            result = result + "String ";
         else if (v.getType().equals("Boolean"))
            result = result + "boolean ";
         else if (v.getType().equals("Character"))
            result = result + "char ";
         else if (v.getType().equals("Double"))
            result = result + "char ";
         
         result = result + v.getName() + ";";
      }
      
      return result;
   }
   
   /**
    * Helper method for generating comments.
    * Adds spaces and comment characters before each line of the given string.
    * @param s String to be changed and returned.
    * @param step Step that has the text.
    * @param programmingLanguage The programming language for the wanted statement.
    * @return Modified version of the taken String with spaces and comment characters added before each new line.
    */
   private String addCommentsToStepText(String s, Step step, int programmingLanguage) {
      return s.replaceAll("\n", "\n" + indentationForStep(step) + commentForLanguage(programmingLanguage));
   }
   
   /**
    * Helper method for generating comments.
    * For the given step, returns a string of empty characters that can be used for indenting.
    * @param s given Step to compute how many indentation will be made.
    * @return a string of "INDENTATION_LEVEL * (step path's size)" empty chars.
    */
   private String indentationForStep(Step s)
   {
      String result = "";
      for (int i = 0; i < s.getPath().getSize() - 1; i++ )
      {
         for (int j = 0; j < INDENTATION_LEVEL; j++)
            result = result + " ";
      }
      return result;
   }
   
   /**
    * Find and return the Step at the given path.
    * @param path Path of the Step to find. 
    * @return The step at the given path.
    */
   public Step getStep(Path path) {
      if (path.getSize() == 0)
         return null;
      
      Step current = initialSteps.getStep(path.getNumbers().get(0) - 1);
      
      for (int i = 1; i < path.getSize(); i++)
      {
         current = current.getSubsteps().getStep(path.getNumbers().get(i) - 1);
      }
      
      return current;
   }
   
   /**
    * Find and return the Substeps of the Step at the given path.
    * @param path Parent path of the Steps to find. 
    * @return Steps under the given path.
    */
   public Steps getSteps(Path parentPath) {
      if (parentPath.getSize() == 0)
         return initialSteps;
      return getStep(parentPath).getSubsteps();
   }
   
   /**
    * Change the model of this project.
    * @param model new model.
    */
   public void setModel(Pseu2Code model)
   {
      this.model = model;
      
      initialSteps.setModel(model);
   }
   
   /**
    * @return options
    */
   public Options getOptions()
   {
      return model.getOptions();
   }
   
   /**
    * Calls the model's updateView methods.
    */
   public void fireUpdateView()
   {
      model.updateView();
   }
}
