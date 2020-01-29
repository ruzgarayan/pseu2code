package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Step;
import model.Project;

import extras.RelativeLayout;

/**
 * Each part of a StepsPanel that contains a Step and represents a -current step-.
 * It has 4 buttons on it to make different operations.
 * @author Ruzgar Ayan
 * @since 11.05.2019
 */
public class CurrentStepPanel extends StepPanel
{
   private JButton expand;
   private JButton delete;
   private JButton moveUp;
   private JButton moveDown;
   
   //Set the layout and add all the components as needed.
   public CurrentStepPanel(Project project, Step step) {
      super(project, step);

      //Note: Find a better layout to use in these panels. BorderLayout doesn't keep a ratio when it is too big. (RelativeLayout maybe?)
      JPanel buttonsPanel = new JPanel();
      expand = new JButton("Expand");
      delete = new JButton("Delete");
      moveUp = new BasicArrowButton(SwingConstants.NORTH);
      moveDown = new BasicArrowButton(SwingConstants.SOUTH);

      CurrentStepButtonListener listener = new CurrentStepButtonListener();
      
      //Add listener to buttons.
      expand.addActionListener(listener);
      delete.addActionListener(listener);
      moveUp.addActionListener(listener);
      moveDown.addActionListener(listener);

      //Expand and delete buttons.
      JPanel p1 = new JPanel();
      p1.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 0.5;
      c.weighty = 0.6;
      c.gridx = 0;
      c.gridy = 0;
      p1.add(expand, c);
      c.weighty = 0.4;
      c.gridy = 1;
      p1.add(delete, c);

      //moveUp and moveDown buttons.
      JPanel p2 = new JPanel();
      p2.setLayout(new GridBagLayout());
      GridBagConstraints g = new GridBagConstraints();
      g.fill = GridBagConstraints.BOTH;
      g.weightx = 0.5;
      g.weighty = 0.5;
      g.gridx = 0;
      g.gridy = 0;
      p2.add(moveUp, g);
      g.gridy = 1;
      p2.add(moveDown, g);

      //Add all buttons a panel.
      buttonsPanel.setLayout(new BorderLayout());
      buttonsPanel.add(p1, BorderLayout.CENTER);
      buttonsPanel.add(p2, BorderLayout.WEST);

      //
      RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS, 3);
      setLayout(rl);
      rl.setFill(true);
      add(buttonsPanel, new Float(2));
      add(stepTextPane, new Float(8));
   }
   
   //Listener class for all the 4 buttons in this panel.
   private class CurrentStepButtonListener implements ActionListener{
      public void actionPerformed(ActionEvent e)
      {
         if(e.getSource() == expand)
         {
            //Expand this step, and then change the selectedParentSteps and selectedCurrentSteps properties of the project.
            step.expand();
            //New parent steps are the current steps.
            getProject().setSelectedParentSteps(getProject().getSelectedCurrentSteps());
            //And the new current steps are the substeps of it.
            getProject().setSelectedCurrentSteps(step.getSubsteps());
         }
         else if(e.getSource() == delete)
         {
            //Get the parent Steps of this Step, and remove this from it.
            getProject().getSteps(step.getPath().getParentPath()).removeStep(step);
         }
         else if(e.getSource() == moveUp)
         {
            //Get the parent Steps of this Step, and call its moveUp method for this step.
            getProject().getSteps(step.getPath().getParentPath()).stepMoveUp(step.getPath().getStepNumber() - 1);
         }
         else if(e.getSource() == moveDown)
         {
            //Get the parent Steps of this Step, and call its moveDown method for this step.
            getProject().getSteps(step.getPath().getParentPath()).stepMoveDown(step.getPath().getStepNumber() - 1);
         }
      }
   }
}