package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Path;
import model.Step;
import model.Project;
import model.Steps;

/**
 * This will hold an arraylist of StepPanels, either current or parent. Holds a Steps object as model.
 * At the bottom of all the StepPanels, there will be a JButton "Add New Step".
 * @author Gulnihal Koruk
 * @since 11.05.2019
 */
public class StepsPanel extends JPanel
{
   private Project project;
   private Steps steps;
   //True if this is a currentStepsPanel, false if this is a parentStepsPanel.
   private boolean currentSteps;
   private JButton addNewStep;
   
   //Creates an arraylist of StepPanels and adds the components to panel.
   public StepsPanel(Project project, Steps steps, boolean currentSteps) {
      this.project = project;
      
      if (steps != null)
      {
         this.steps = steps;
         this.currentSteps = currentSteps;
         
         //Current step panels has 1 more row for the button.
         if (currentSteps)
            setLayout(new GridLayout(steps.getStepLimit() + 1, 1));
         else
            setLayout(new GridLayout(steps.getStepLimit(), 1));
         
         addNewStep = new JButton("Add New Step");
         addNewStep.addActionListener(new AddNewStepListener());
         
         addComponents();
      }
   }
   
   //Change the steps property of this panel.
   public void setSteps(Steps steps) {
      this.steps = steps;
   }
   
   public void setProject(Project project)
   {
      this.project = project;
   }
   
   //First add the stepPanels and then the "Add New Step" button if currentSteps is true.
   public void addComponents() {
      if (steps != null)
      {
         if (currentSteps)
            setLayout(new GridLayout(steps.getStepLimit() + 1, 1));
         else
            setLayout(new GridLayout(steps.getStepLimit(), 1));
         
         for (int i = 0; i < steps.getSize(); i++)
         {
            //If this is a current steps panel, add -CurrentStepPanel-s.
            //If not, add -ParentStepPanel-s.
            if (currentSteps)
            {
               add(new CurrentStepPanel(project, steps.getStep(i)));
            }
            else
            {
               //Make the selected parent step's button green.
               ParentStepPanel stepPanel = new ParentStepPanel(project, steps.getStep(i));
               Path currentPath = project.getSelectedCurrentSteps().getParentPath();
               if (currentPath.getSize() > 0 && i == currentPath.getStepNumber() - 1)
                  stepPanel.setSelected();
               add(stepPanel);
               
            }
         }
         
         //If this is current steps panel, add the button too.
         if(currentSteps) 
            add(addNewStep);
      }
   }
   
   //First clear the panel(removeAll() method of JPanel),
   //then reinitialize the stepPanel arraylist and add the components again.
   public void update() {
      if (steps != null)
      {
         if (currentSteps)
            setLayout(new GridLayout(steps.getStepLimit() + 1, 1));
         else
            setLayout(new GridLayout(steps.getStepLimit(), 1));
      }
      //Clear the panel.
      removeAll();
      //Add everything again.
      addComponents();
      //Revalidate and repaint the panel so the user can see the new version of this panel.
      revalidate();
      repaint();
   }
   
   //Listener for addNewStep button. 
   private class AddNewStepListener implements ActionListener
   {
      //Add a new step to the steps object.
      public void actionPerformed(ActionEvent e) {
         steps.addStep();
      }
   }
}