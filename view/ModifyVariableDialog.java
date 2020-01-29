package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The dialog that handles modifying a variable.
 * @author Ilhan Koc
 * @since 11.05.2019
 */
public class ModifyVariableDialog extends JDialog
{
   private Project project;
   private Variable variable;
   private JComboBox typeList;
   private JTextField textField;
   
   String[] typeListString = {"Integer", "String", "Boolean", "Character", "Double"};
   public ModifyVariableDialog(Project project, Variable variable) {
      System.out.println(variable.getPath());
      this.project = project;
      this.variable = variable;
      this.setLayout(new GridLayout(3, 1));
      JPanel panelField = new JPanel();
      panelField.setLayout(new GridLayout(1, 2));
      JLabel labelVariable = new JLabel("Name of the Variable: ");
      textField = new JTextField();
      textField.setSize(100,10);
      textField.setText(variable.getName());
      panelField.add(labelVariable);
      panelField.add(textField);
      this.add(panelField);
      
      JPanel panelType = new JPanel();
      panelType.setLayout(new FlowLayout());
      JLabel labelType = new JLabel("Type of the Variable: ");
      panelType.add(labelType);
      typeList = new JComboBox(typeListString);
      typeList.setSelectedItem(variable.getType());
      panelType.add(typeList);
      add(panelType);
      
      JPanel panelButton = new JPanel();
      panelButton.setLayout(new FlowLayout());
      JButton close = new JButton("Close");
      close.addActionListener(new ModifyVariableDialog.CloseVariableButtonListener());
      JButton delete = new JButton("Delete");
      delete.addActionListener(new ModifyVariableDialog.DeleteVariableButtonListener());
      JButton change = new JButton("Change");
      change.addActionListener(new ModifyVariableDialog.ChangeVariableButtonListener());
      panelButton.add(close);
      panelButton.add(delete);
      panelButton.add(change);
      
      add(panelButton);
      
      pack();
      
      setVisible(true);
   }
   private class CloseVariableButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         dispose();
      }
   }
   private class DeleteVariableButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         Variable deleteVariable;
         deleteVariable = null;
         for (Variable v: project.getVariables())
         {
            if(v.equals(variable))
            {
               deleteVariable = v;
            }
         }
         if(deleteVariable != null)
         {
            project.getVariables().remove(deleteVariable);
         }
         project.fireUpdateView();
         dispose();
      }
   }
   
   private class ChangeVariableButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         ArrayList<Variable> variables = project.getVariables();
         
         for (Variable v: variables)
         {
            if(v.equals(variable))
            {
               int index = variables.indexOf(v);
               variables.get(index).setName(textField.getText());
               variables.get(index).setType(typeList.getSelectedItem().toString());
            }
         }
         project.fireUpdateView();
         dispose();
      }
   }
}