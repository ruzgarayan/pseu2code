package Model;

//Salman
public class Pseu2Code
{
   private Project currentProject;
   private Options options;
   
   //Options is loaded and a new project is created to be the currentProject.
   public Pseu2Code() {}
   
   //There are methods in Options and Project classes for everything below.
   
   //Saves the current project to save location.
   public void saveCurrentProject() {}
   //Saves the current project and opens a new one.
   public void newProject() {}
   //Opens the project at the given projectLocation.
   public void openProject(File projectLocation) {}
   
   public void saveOptions() {}
   public void loadOptions() {}
   
   public void generateCode(int programmingLanguage, String saveLocation) {}
   
   //setters and getters
}
