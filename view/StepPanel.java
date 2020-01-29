package view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import model.Project;
import model.Step;
import model.Variable;

/**
 * Abstract class that will be extended by ParentStepPanel and CurrentStepPanel.Holds a step as model.
 * The inner class will be responsible of coloring the variables and updating the model.
 * @author Gulnihal Koruk
 * @since 11.05.2019
 */
public abstract class StepPanel extends JPanel
{
   protected Project project;
   protected Step step;
   protected StepDocument document;
   protected JTextPane stepTextPane;
   
   //Initialize the text pane and set a border for the panel.
   public StepPanel(Project project, Step step) {
      this.project = project;
      this.step = step;
      document = new StepDocument();
      stepTextPane = new JTextPane(document);
      
      Font textFont = new Font(project.getOptions().getFont(), Font.PLAIN, project.getOptions().getFontSize());
      stepTextPane.setFont(textFont);
      stepTextPane.setText(step.getText());
      
      setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), step.toString(), TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
   }
   
   public String getText() {
      return stepTextPane.getText();
   }
   
   public void setText(String text) {
      stepTextPane.setText(text);
   }
   
   public Project getProject() 
   {
      return project;
   }
   
   //The document which our JTextPane will be using.
   //It handles updating the step property as the user writes something and coloring the variables.
   private class StepDocument extends DefaultStyledDocument {
      private final StyleContext styleContext = StyleContext.getDefaultStyleContext();
      private final AttributeSet blackAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
      
      private Pattern pattern;
      
      //Taken from https://stackoverflow.com/questions/14400946/how-to-change-the-color-of-specific-words-in-a-jtextpane
      private Pattern buildPattern()
      {
         StringBuilder sb = new StringBuilder();
         ArrayList<Variable> variables = getProject().getVariables();
         for (Variable variable : variables) {
            sb.append("\\b"); // Start of word boundary
            sb.append(variable.getName());
            sb.append("\\b|"); // End of word boundary and an or for the next word
         }
         if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1); // Remove the trailing "|"
         }
         
         Pattern p = Pattern.compile(sb.toString());
         
         return p;
      }
      
      public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
      {
         super.insertString(offs, str, a);
         
         //First update the step text, then do variable coloring. 
         step.setText(stepTextPane.getText());
         colorVariables();
      }
      
      public void remove(int offs, int len) throws BadLocationException
      {
         super.remove(offs, len);
         
         //First update the step text, then do variable coloring. 
         step.setText(stepTextPane.getText());
         colorVariables();
      }
      
      public void colorVariables() throws BadLocationException {
         pattern = buildPattern();
         
         //Reset all the colors to black first.
         setCharacterAttributes(0, stepTextPane.getText().length(), blackAttributeSet, true);
         
         // Look for tokens 
         Matcher matcher = pattern.matcher(stepTextPane.getText());
         while (matcher.find()) {
            Variable v = project.getVariable(stepTextPane.getText().substring(matcher.start(), matcher.end()));
            if (v != null)
            {
               //Change the color of that substring to the color of the variable.
               AttributeSet newSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, v.getColor());
               setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), newSet, false);
            }
         }
      }
   }
}