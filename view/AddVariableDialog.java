package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The dialog that handles adding a new variable.
 * @author Ilhan Koc
 * @since 11.05.2019
 */
public class AddVariableDialog extends JDialog
{
   private JComboBox typeList;
   JTextField textField;
   Pseu2Code model;
   
   String[] typeListString = {"Integer", "String", "Boolean", "Character", "Double"};
   
   public AddVariableDialog(Pseu2Code model) {
      this.model = model;
      setLayout(new GridLayout(3, 1));
      JPanel panelField = new JPanel();
      panelField.setLayout(new GridLayout(1, 2));
      JLabel labelVariable = new JLabel("Name of the Variable: ");
      textField = new JTextField();
      textField.setSize(100,10);
      panelField.add(labelVariable);
      panelField.add(textField);
      add(panelField);
      
      JPanel panelType = new JPanel();
      panelType.setLayout(new FlowLayout());
      JLabel labelType = new JLabel("Type of the Variable: ");
      panelType.add(labelType);
      typeList = new JComboBox(typeListString);
      panelType.add(typeList);
      add(panelType);
      
      JPanel panelButton = new JPanel();
      panelButton.setLayout(new FlowLayout());
      JButton close = new JButton("Close");
      close.addActionListener(new CloseVariableButtonListener());
      JButton add = new JButton("Add");
      add.addActionListener(new AddVariableButtonListener());
      panelButton.add(close);
      panelButton.add(add);
      add(panelButton);
      
      pack();
      
      setVisible(true);
   }
   
   private class CloseVariableButtonListener implements ActionListener {
      //Add a new variable to the project.
      public void actionPerformed(ActionEvent e) {
         dispose();
      }
   }
   
   private class AddVariableButtonListener implements ActionListener {
      //Add a new variable to the project.
      public void actionPerformed(ActionEvent e) {
         Variable n;
         if (model.getProject().getSelectedParentSteps() == null)
            n = new Variable(model, textField.getText(), typeList.getSelectedItem().toString(),
                             null);
         else
            n = new Variable(model, textField.getText(), typeList.getSelectedItem().toString(),
                             model.getProject().getSelectedParentSteps().getParentPath());
         model.getProject().getVariables().add(n);
         model.updateView();
         dispose();
      }
   }
}