package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Pseu2Code;

/**
 * Menus for the program. File, edit and help menus.
 * @author Ruzgar Ayan
 * @since 11.05.2019
 */
public class MenuBar extends JMenuBar
{
   Pseu2Code model;
   
   private JMenu file;
   private JMenu edit;
   private JMenu help;
   
   //File menu
   private JMenuItem menuNew;
   private JMenuItem menuSave;
   private JMenuItem menuOpen;
   private JMenuItem menuGenerateCode;
   private JMenuItem menuOptions;
   private JMenuItem menuExit;
   
   //Edit menu
   private JMenuItem menuUndo;
   private JMenuItem menuRedo;
   private JMenuItem menuFind;
   private JMenuItem menuReplace;
   
   //Help menu
   private JMenuItem menuHowToUse;
   private JMenuItem menuExamples;
   private JMenuItem menuAbout;
   
   public MenuBar(Pseu2Code model)
   {
      this.model = model;
      
      //Initialize all the menus separately
      initFileMenus();
      initEditMenus();
      initHelpMenus();
   }
   
   public void initFileMenus()
   {
      file = new JMenu();
      menuNew = new JMenuItem();
      menuSave = new JMenuItem();
      menuOpen = new JMenuItem();
      menuGenerateCode = new JMenuItem();
      menuOptions = new JMenuItem();
      menuExit = new JMenuItem();
      
      file.setText("File");
      
      menuNew.setText("New");
      menuNew.addActionListener(new ActionListener()  {
         
         //Open a new project with the given name.
         public void actionPerformed(ActionEvent e)
         {
            String projectName = JOptionPane.showInputDialog("Enter Project Name");
            model.newProject(projectName);
            model.updateView();
         }
         
      });
      file.add(menuNew);
      
      menuSave.setText("Save");
      menuSave.addActionListener(new ActionListener()  {
         
         //Try to save the current project. Inform the user if it was successful or not.
         public void actionPerformed(ActionEvent e)
         {
            boolean savedSuccessfully = model.saveCurrentProject();
            if (savedSuccessfully)
               JOptionPane.showMessageDialog(null, "Project Succesfully Saved");
            else 
               JOptionPane.showMessageDialog(null, "Project Couldn't Be Saved");
         }
      });
      file.add(menuSave);
      
      menuOpen.setText("Open");
      menuOpen.addActionListener(new ActionListener()  {
         
         //Ask the user to choose a file and open the project at that file.
         public void actionPerformed(ActionEvent e)
         {
            JFileChooser f = new JFileChooser(System.getProperty("user.dir"));
            f.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter p2cfilter = new FileNameExtensionFilter(
                                                                            "Pseu2Code files (*.p2c)", "p2c");
            f.setFileFilter(p2cfilter);
            f.addChoosableFileFilter(p2cfilter);
            f.showDialog(null, "Open project");
            
            model.openProject(f.getSelectedFile());
            model.updateView();
         }
         
      });
      file.add(menuOpen);
      
      menuGenerateCode.setText("Generate Code");
      menuGenerateCode.addActionListener(new ActionListener()  {
         
         public void actionPerformed(ActionEvent e)
         {
            model.generateCode();
         }
         
      });
      file.add(menuGenerateCode);
      
      menuOptions.setText("Options");
      menuOptions.addActionListener(new ActionListener()  {
         
         public void actionPerformed(ActionEvent e)
         {
            new OptionsDialog(model);
         }
         
      });
      file.add(menuOptions);
      
      menuExit.setText("Exit");
      menuExit.addActionListener(new ActionListener()  {
         
         //Save the project and options, then exit the program.
         public void actionPerformed(ActionEvent e)
         {
            model.saveCurrentProject();
            model.saveOptions();
            System.exit(1);
         }
         
      });
      file.add(menuExit);
      
      add(file);
   }
   public void initEditMenus()
   {
      edit = new JMenu();
      menuUndo = new JMenuItem();
      menuRedo = new JMenuItem();
      menuFind = new JMenuItem();
      menuReplace = new JMenuItem();
      
      edit.setText("Edit");
      
      menuUndo.setText("Undo");
      menuUndo.addActionListener(new ActionListener()  {
         
         public void actionPerformed(ActionEvent e)
         {
            //NOT WORKING
         }
         
      });
      edit.add(menuUndo);
      
      menuRedo.setText("Redo");
      menuRedo.addActionListener(new ActionListener()  {
         
         public void actionPerformed(ActionEvent e)
         {
            //NOT WORKING
         }
         
      });
      edit.add(menuRedo);
      
      menuFind.setText("Find");
      menuFind.addActionListener(new ActionListener()  {
         
         public void actionPerformed(ActionEvent e)
         {
            //NOT WORKING
         }
         
      });
      edit.add(menuFind);
      
      menuReplace.setText("Replace");
      menuReplace.addActionListener(new ActionListener()  {
         
         public void actionPerformed(ActionEvent e)
         {
            //NOT WORKING
         }
         
      });
      edit.add(menuReplace);
      
      add(edit);
   }
   public void initHelpMenus()
   {
      help = new JMenu();
      menuHowToUse = new JMenuItem();
      menuExamples = new JMenuItem();
      menuAbout = new JMenuItem();
      
      help.setText("Help");
      
      menuHowToUse.setText("How to use");
      menuHowToUse.addActionListener(new ActionListener()  {
         
         public void actionPerformed(ActionEvent e)
         {
            new HelpDialog();
         }
         
      });
      help.add(menuHowToUse);
      
      menuExamples.setText("Examples");
      menuExamples.addActionListener(new ActionListener()  {
         
         public void actionPerformed(ActionEvent e)
         {
            new ExampleDialog();
         }
         
      });
      help.add(menuExamples);
      
      menuAbout.setText("About");
      menuAbout.addActionListener(new ActionListener()  {
         
         public void actionPerformed(ActionEvent e)
         {
            new AboutDialog();
         }
         
      });
      help.add(menuAbout);
      
      add(help);
   }
   
   //Cankat
   private class ExampleDialog extends JDialog{
      private JTextPane helpText;
      private JButton example1, example2;
      private JPanel buttonsPanel;
      
      public ExampleDialog() {
         
         buttonsPanel = new JPanel();
         setLayout(new GridLayout(1,2));
         helpText = new JTextPane();
         helpText.setEditable(false);
         helpText.setText(" Here you can see the examples that show you \n two easy samples on how to use our program");
         example1 = new JButton("Example 1");
         example1.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
               model.openProject(new File("examples\\example1.p2c"));
            }
         });
         example2 = new JButton("Example 2");
         example2.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
               model.openProject(new File("examples\\example2.p2c"));
            }
         });
         setLayout(new GridLayout(2,1));
         
         add(helpText);
         buttonsPanel.add(example1);
         buttonsPanel.add(example2);
         add(buttonsPanel);
         pack();
         setResizable(false);
         setVisible(true);
      }
   }
   
   //Cankat
   public class HelpDialog extends JDialog {
      
      private JTextPane helpPane;
      private JButton back, next;
      private int lastPage;
      
      HelpDialog() {
         lastPage = 0;
         helpPane = new JTextPane();
         helpPane.setPreferredSize(new Dimension(350,100));
         back = new JButton("Back");
         back.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
               if (lastPage == 0) {
                  dispose();
               } else if (lastPage == 1) {
                  lastPage--;
                  initFirstPage();
               } else if (lastPage == 2) {
                  lastPage--;
                  secondPage();
               } else if (lastPage == 3) {
                  lastPage--;
                  thirdPage();
               } else if (lastPage == 4) {
                  lastPage--;
                  fourthPage();
               } else {
                  lastPage--;
                  lastPage();
               }
            }
         });
         next = new JButton("Next");
         next.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
               if (lastPage == 0) {
                  lastPage++;
                  secondPage();
               } else if (lastPage == 1) {
                  lastPage++;
                  thirdPage();
               } else if (lastPage == 2) {
                  lastPage++;
                  fourthPage();
               } else if (lastPage == 3) {
                  lastPage++;
                  lastPage();
               } else {
                  dispose();
               }
            }
         });
         
         helpPane.setEditable(false);
         initFirstPage();
         
         getContentPane().setLayout(new GridBagLayout());
         GridBagConstraints c = new GridBagConstraints();
         
         c.weightx = 0.5;
         c.weighty = 0.5;
         c.gridx = 0;
         c.gridy = 0;
         c.gridwidth = 3;
         getContentPane().add(helpPane, c);
         
         c.gridy = 1;
         c.gridwidth = 1;
         c.gridx = 0;
         getContentPane().add(back, c);
         
         c.gridx = 1;
         getContentPane().add(next, c);
         
         setBounds(350, 175,0 ,0);
         pack();
         setResizable(false);
         setVisible(true);
      }
      
      public void initFirstPage() {
         helpPane.setText("Hello, Welcome to Pseu2Code. "
                             + "Pseu2Code is a pseudo code editor \n We have divided the screen into three parts for ease of acces. "
                             + "\nWe will talk about each panel in the next pages. "
                             + "\nClick next to contunie to the next page");
      }
      
      public void secondPage() {
         helpPane.setText("At the left corner you have your Parent Steps."
                             + "These Steps are the predecessors of the ones you have in your main Panel.\n"
                             + "You can expand these steps individually and mess with their substeps \n");
      }
      
      public void thirdPage() {
         helpPane.setText("In the middle there is your Current Steps. \n"
                             + "You can expand these steps delete them, move them up and down. \n"
                             + "There is also a Add New Step button which adds a new step to the parent");
      }
      
      public void fourthPage() {
         helpPane.setText("At the far right is Tools Panel. There is the navigator and the variable panels. \n"
                             + "Step and Project navigators let you browse your steps and saved projects respectively. \n"
                             + "And the variable panel lets you see, add, delete and change the variables you will use in your code. \n"
                             + "The variables entered will also highlight in their respective steps.\n");
      }
      
      public void lastPage() {
         helpPane.setText("Last of all the menus at the top. \n"
                             + "These menus allow you to use features such as save, load, open and \n "
                             + "generate code which generates the comments to the code you plan to write.\n "
                             + "Let you edit the steps with find, replace, undo and redo\n "
                             + "They also allow you to acces our About, Examples and this very How To Use pages");
      }
   }
   
   public class AboutDialog extends JDialog {
      
      private JTextPane aboutPane;
      private JLabel logo;
      BufferedImage image;
      
      AboutDialog() {
         try {
            image = ImageIO.read(new File("extras\\logo.png"));
            logo = new JLabel(new ImageIcon(image));
         } catch (Exception e) {
            logo = new JLabel();
         }
         aboutPane = new JTextPane();
         aboutPane.setEditable(false);
         aboutPane.setText("Pseu2Code: Pseudo Code Editor\n" + "\n" + "Version: 2019-05-12\n"
                              + "(c) Copyright Bilkent contributors and others, 2019.  \n"
                              + "All rights reserved. Pseu2Code and the Pseu2Code logo are trademarks of the The Last Stand Group.");
         setLayout(new GridBagLayout());
         add(logo);
         add(aboutPane);
         setBounds(500, 250, 0, 0);
         pack();
         setResizable(false);
         setVisible(true);
      }
   }
}