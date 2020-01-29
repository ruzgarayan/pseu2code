package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.*;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * Each part of a StepsPanel that contains a Step and represents a -parent step-.
 * @author Salman Soomro
 * @since 11.05.2019
 */
public class ParentStepPanel extends StepPanel
{
   private JButton expand;
   
   public ParentStepPanel(Project project, Step step)
   {
      super(project, step);
      setLayout(new BorderLayout());
      add(stepTextPane, BorderLayout.CENTER);
      expand = new BasicArrowButton(SwingConstants.EAST);
      expand.addActionListener(new ParentStepButtonListener());
      add(expand, BorderLayout.EAST);
   }
   
   //Sets the background color of the expand button to green.
   public void setSelected()
   {
      expand.setBackground(Color.GREEN);
   }
   
   private class ParentStepButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         //Expand this step, and then change the selectedParentSteps and selectedCurrentSteps properties of the project.
         step.expand();
         //And the new current steps are the substeps of it.
         getProject().setSelectedCurrentSteps(step.getSubsteps());
      }
   }
}