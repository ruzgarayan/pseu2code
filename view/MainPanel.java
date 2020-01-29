package view;

import java.util.Observer;
import java.util.Observable;

import javax.swing.*;

import extras.RelativeLayout;

import java.awt.*;
import java.awt.event.*;

import model.Project;
import model.Pseu2Code;

/**
 * Holds every panel together. 
 * Holds current and parent steps, variable and the naviagor panels.
 * @author Cankat Kadim
 * @since 12.05.2019
 */
public class MainPanel extends JPanel implements Observer {
   private Pseu2Code model;
   
   private StepsPanel parentStepsPanel;
   private StepsPanel currentStepsPanel;
   
   private JLabel projectNameLabel;
   
   private JTabbedPane navigators;
   private ProjectNavigator projectNavigator;
   private StepNavigator stepNavigator;
   private VariablesPanel variablesPanel;
   private JPanel rightPanel;
   private JPanel leftPanel;
   
   private MenuBar menuBar;
   
   public MainPanel(Pseu2Code model) {
      this.model = model;
      model.addView(this);
      
      currentStepsPanel = new StepsPanel(model.getProject(), model.getProject().getSelectedCurrentSteps(), true);
      parentStepsPanel = new StepsPanel(model.getProject(), model.getProject().getSelectedParentSteps(), false);
      stepNavigator = new StepNavigator(model);
      projectNavigator = new ProjectNavigator(model);
      variablesPanel = new VariablesPanel(model);
      
      projectNameLabel = new JLabel(model.getProject().getProjectName(), SwingConstants.CENTER);
      projectNameLabel.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent e) {
            String projectName = JOptionPane.showInputDialog("Enter Project's New Name");
            model.getProject().setProjectName(projectName);
            model.updateView();
         }
      });
      
      leftPanel = new JPanel();
      leftPanel.setLayout(new BorderLayout());
      
      rightPanel = new JPanel();
      rightPanel.setLayout(new GridLayout(2, 1));
      
      navigators = new JTabbedPane();
      navigators.add(stepNavigator);
      
      navigators.addTab("Step Navigator", stepNavigator);
      navigators.addTab("Project Navigator", projectNavigator);
      
      leftPanel.add(projectNameLabel, BorderLayout.NORTH);
      leftPanel.add(parentStepsPanel, BorderLayout.CENTER);
      
      rightPanel.add(navigators);
      rightPanel.add(variablesPanel);
      
      RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS, 3);
      setLayout(rl);
      
      rl.setFill(true);
      add(leftPanel, new Float(2.5));
      
      add(currentStepsPanel, new Float(6));
      
      add(rightPanel, new Float(1.5));
   }
   
   // Updates all the components.
   public void update(Observable o, Object ob) {
      parentStepsPanel.setProject(model.getProject());
      currentStepsPanel.setProject(model.getProject());
      
      projectNameLabel.setText((model.getProject().getProjectName()));
      
      parentStepsPanel.setSteps(model.getProject().getSelectedParentSteps());
      currentStepsPanel.setSteps(model.getProject().getSelectedCurrentSteps());
      
      parentStepsPanel.update();
      currentStepsPanel.update();
      stepNavigator.update();
      projectNavigator.update();
      variablesPanel.update();
   }
   
   public Project getProject() {
      return model.getProject();
   }
   
}