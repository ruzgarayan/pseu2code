package model;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class combines a project with the options.
 * Responsible for saving and loading of projects and options.
 * @author Salman Soomro
 * @since 11.05.2019
 */
public class Pseu2Code extends Observable implements Serializable{
   private Project currentProject;
   private Options options;
   
   // Options is loaded and a new project is created to be the currentProject.
   public Pseu2Code() {
      loadOptions();
      currentProject = new Project(this, "New Project", 7);
   }
   
   /**
    * Save the project to the given location.
    * @param saveLocation Save location.
    * @return true if save is successful, false if not.
    */
   public boolean saveCurrentProject() {
      try {
         FileOutputStream fos = new FileOutputStream(new File(options.getSaveLocation()
                                                                 + "\\" + currentProject.getProjectName() + ".p2c"));
         ObjectOutputStream oos = new ObjectOutputStream(fos);
         
         // Write object to file
         oos.writeObject(currentProject);
         
         oos.close();
         fos.close();
         
         updateView();
         
         return true;
      } catch (IOException e) {
         return false;
      }
   }
   
   
   // Saves the current project and opens a new one.
   public void newProject(String projectName) {
      saveCurrentProject();
      currentProject = new Project(this, projectName, 7);
      updateView();
   }
   
   // Opens the project at the given projectLocation.
   public void openProject(File projectLocation) {
      currentProject = loadProject(projectLocation);
      currentProject.setModel(this);
      updateView();
   }
   
   public void saveOptions() {
      options.save();
   }
   
   public void loadOptions() {
      options = Options.loadOptions();
      updateView();
   }
   
   public void generateCode() {
      currentProject.generateComments(options.getProgrammingLanguage(), options.getSaveLocation());
   }
   
   public Project getProject() {
      return currentProject;
   }
   
   public Options getOptions() {
      return options;
   }
   
   public void addView(Observer observer)
   {
      addObserver(observer);
   }
   
   public void updateView()
   {
      setChanged();
      notifyObservers();
   }
   
   /**
    * Load the project in the give file and
    * @param saveLocation Save location.
    * @return true if save is successful, false if not.
    */
   private Project loadProject(File projectLocation) {
      try {
         FileInputStream fis = new FileInputStream(projectLocation);
         ObjectInputStream ois = new ObjectInputStream(fis);
         
         // Read object
         Project read = (Project)(ois.readObject());
         
         ois.close();
         fis.close();
         
         updateView();
         
         return read;
      } catch (IOException | ClassNotFoundException e) {
         return null;
      } 
   } 
}
