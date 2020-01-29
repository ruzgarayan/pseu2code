package view;

import model.Variable;
import model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Each part of the VariablesPanel.
 * @author Ilhan Koc
 * @since 11.05.2019
 */
public class VariablePanel extends JPanel
{
   private static final int FONT_SIZE = 15;
   private static final String FONT = "Serif";
   
   private Project project;
   private Variable variable;
   private JLabel label;
   
   //Sets the things up.
   public VariablePanel(Variable variable, Project project) {
      this.project = project;
      label = new JLabel(variable.getName()+" - "+variable.getType());
      label.setForeground(variable.getColor());
      label.setOpaque(true);
      label.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
      this.add(label);
      
      addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent me) {
            ModifyVariableDialog modifyVariableDialog = new ModifyVariableDialog(project, variable);
         }
      });
   }
}
