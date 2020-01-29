package view;

import model.Variable;
import model.Pseu2Code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A set of -VariablePanel-s.
 * @author Ilhan Koc
 * @since 11.05.2019
 */
public class VariablesPanel  extends JPanel{
   private ArrayList<Variable> variables;
   private Pseu2Code model;
   private JButton addVariable;
   
   //Use getVariables() of the Project class to get the variables that will be shown,
   //add VariablePanels to the this panel for each of these varibles and a "Add model.Variable" button.
   //public VariablesPanel(Pseu2Code model) {}
   public VariablesPanel(Pseu2Code model) {
      this.model = model;
      variables = model.getProject().getCurrentVariables();
      
      setLayout(new GridLayout(10, 1));
      
      addVariable = new JButton("Add Variable");
      addVariable.setPreferredSize(new Dimension(300, 40));
      addVariable.addActionListener(new AddVariableButtonListener());
      
      add(addVariable);
   }
   
   //Removes everything from the panel and adds them again.
   public void update() {
      removeAll();
      add(addVariable);
      variables = model.getProject().getCurrentVariables();
      for (Variable v : variables) {
         add(new VariablePanel(v, model.getProject()));
      }
      
      revalidate();
      repaint();
   }
   
   //Listener class for addVariable button.
   private class AddVariableButtonListener implements ActionListener {
      //Add a new variable to the project.
      public void actionPerformed(ActionEvent e) {
         AddVariableDialog addVariableDialog = new AddVariableDialog(model);
      }
   }
}