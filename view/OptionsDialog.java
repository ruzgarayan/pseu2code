package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import model.Pseu2Code;

/**
 * The dialog that will be opened from menus/file/options.
 * User will be able to change some options of the program here.
 * @author Salman Soomro
 * @since 11.05.2019
 */
public class OptionsDialog extends JDialog {
   private Pseu2Code model;
   private JComboBox languages, steps, fontOptions, sizeOptions;
   private JButton okButton, cancelButton, setProject;
   private JLabel projects;
   private String newSaveLocation;
   
   public OptionsDialog(Pseu2Code model) {
      this.model = model;
      getContentPane().setLayout(new BorderLayout());
      
      newSaveLocation = model.getOptions().getSaveLocation();
      
      // buttonPane in the South of the panel, it has "Apply and Close" and "Cancel"
      // buttons.
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      
      ActionListener listener = new ButtonsListener();
      
      okButton = new JButton("Apply and Close");
      okButton.addActionListener(listener);
      buttonPane.add(okButton);
      
      cancelButton = new JButton("Cancel");
      cancelButton.addActionListener(listener);
      buttonPane.add(cancelButton);
      
      setProject = new JButton("Set Projects Folder");
      setProject.addActionListener(listener);
      
      // tabbedpane that is in the Center of the panel, it has "General" and "Font"
      // panels.
      JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
      getContentPane().add(tabbedPane, BorderLayout.CENTER);
      JPanel general = new JPanel();
      
      // Code for general JPanel.
      general.setLayout(new BoxLayout(general, BoxLayout.PAGE_AXIS));
      
      projects = new JLabel("Projects Folder:" + model.getOptions().getSaveLocation());
      general.add(projects);
      general.add(setProject);
      
      JLabel language = new JLabel("Language to Generate Code:");
      general.add(language);
      
      String[] lang = { "JAVA", "C", "PYTHON" };
      languages = new JComboBox(lang);
      languages.setSelectedItem(model.getOptions().getLanguageName());
      general.add(languages);
      
      JLabel maxSteps = new JLabel("Maximum Steps:");
      general.add(maxSteps);
      
      String[] step = { "5", "6", "7", "8", };
      steps = new JComboBox(step);
      steps.setSelectedItem(model.getProject().getStepLimit() + "");
      
      general.add(steps);
      
      tabbedPane.addTab("General", null, general, null);
      
      JPanel font = new JPanel();
      
      // Code for font JPanel.
      
      font.setLayout(new BoxLayout(font, BoxLayout.PAGE_AXIS));
      
      JLabel fOptions = new JLabel("Font Options:");
      font.add(fOptions);
      
      JLabel fonts = new JLabel("Font:");
      font.add(fonts);
      
      String[] fontOption = { "Arial", "Bodoni", "Futura", "Frutiger", };
      fontOptions = new JComboBox(fontOption);
      fontOptions.setSelectedItem(model.getOptions().getFont());
      font.add(fontOptions);
      
      JLabel size = new JLabel("Size:");
      font.add(size);
      
      String[] sizes = { "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" };
      sizeOptions = new JComboBox(sizes);
      sizeOptions.setSelectedItem(model.getOptions().getFontSize() + "");
      font.add(sizeOptions);
      
      tabbedPane.addTab("Font", null, font, null);
      
      pack();
      setResizable(false);
      setVisible(true);
   }
   
   //Listener for the 3 buttons in the dialog.
   //Changes all the options when "Apply and close" button is pressed.
   private class ButtonsListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         JButton pressed = (JButton) e.getSource();
         
         if (pressed.equals(cancelButton)) {
            dispose();
         } else if (pressed.equals(okButton)) {
            model.getOptions().setProgrammingLanguage((String) languages.getSelectedItem());
            model.getProject().setStepLimit(Integer.parseInt((String) steps.getSelectedItem()));
            model.getOptions().setFont((String) fontOptions.getSelectedItem());
            model.getOptions().setFontSize(Integer.parseInt((String) sizeOptions.getSelectedItem()));
            model.getOptions().setSaveLocation(newSaveLocation);
            model.updateView();
            dispose();
         } else if (pressed.equals(setProject)) {
            JFileChooser choose = new JFileChooser();
            choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            choose.showOpenDialog(null);
            if (choose.getSelectedFile() != null) {
               newSaveLocation = choose.getSelectedFile().toString();
               projects.setText("Projects Folder: " + newSaveLocation);
            }
         }
      }
   }
}