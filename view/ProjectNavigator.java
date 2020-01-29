package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import model.Options;
import model.Pseu2Code;

/**
 * Represents the saved projects in the projects folder as a tree.
 * Holds pseu2code and options
 * @author Cankat
 * @since 12.05.2019
 */
public class ProjectNavigator extends JScrollPane {
   private Pseu2Code pseu2code;
   private Options settings;
   private Tree treeModel;
   private JTree treeView;
   
   public ProjectNavigator(Pseu2Code pseu2code) {
      // inits the pseu2code
      this.pseu2code = pseu2code;
      // gets the options from the pseu2code
      settings = pseu2code.getOptions();
      
      treeModel = new Tree();
      treeView = new JTree(treeModel);
      // adds the view to the tree
      treeView.setRootVisible(false);
      // doesn't show the root folder, it looks better
      treeView.addMouseListener(new TreeClicker());
      setViewportView(treeView);
   }
   
   public void update() {
      // updates the view every time there has been a change in the project
      treeModel = new Tree();
      treeView.setModel(treeModel);
   }
   
   public class Tree implements TreeModel {
      
      // this class uses an inner class called treeFile
      // to add some methods to the file class
      treeFile[] subfolders;
      treeFile root;
      
      Tree() {
         root = new treeFile(new File(settings.getSaveLocation()));
         subfolders = root.listFiles();
      }
      
      /**
       * @return Object, the root of the treeModel
       */
      @Override
      public Object getRoot() {
         // returns the project folder
         return root;
      }
      
      /**
       * @param parent, the node in the tree a file
       * @param index,  the index of the child node
       * @return Object, the child node of the parent at the index
       */
      @Override
      public Object getChild(Object parent, int index) {
         // returns the child at the index if the parent exists
         if (parent != null && index < subfolders.length)
            return subfolders[index];
         return null;
      }
      
      /**
       * @param parent, the file that has children
       * @return int, the number of files under the parent
       */
      @Override
      public int getChildCount(Object parent) {
         // returns the length of the subfolders the folder has 
         if (parent != null && subfolders != null)
            return subfolders.length;
         return -1;
      }
      
      /**
       * @param node, the file node that doesn't have any children
       * @return boolean, true if the node doesn't have any children else false
       */
      @Override
      public boolean isLeaf(Object node) {
         // instead of returning every file the navigator only returns .p2c files
         if (((treeFile) node).isP2C())
            return true;
         return false;
      }
      
      /**
       * @param parent, the file that has the child
       * @param child,  the child that is going to be checked
       * @return int, the index of the child
       */
      @Override
      public int getIndexOfChild(Object parent, Object child) {
         if (parent != null && child != null)
            for (int i = 0; i < subfolders.length; i++)
            if (subfolders[i].equals(child))
            return i;
         return -1;
      }
      
      @Override
      public void valueForPathChanged(TreePath path, Object newValue) {
         
      }
      
      @Override
      public void addTreeModelListener(TreeModelListener l) {
         // TODO Auto-generated method stub
         
      }
      
      @Override
      public void removeTreeModelListener(TreeModelListener l) {
         // TODO Auto-generated method stub
         
      }
      
      public class treeFile {
         
         // inner class of project Nav
         // contains a file object
         private File myFile;
         
         public treeFile(File file) {
            myFile = file;
         }
         
         public boolean isP2C() {
            //this method makes sure that every leaf in the tree is a .p2c file
            String s = myFile.getName();
            if (s.length() >= 4)
               return s.substring(s.length() - 4, s.length()).equals(".p2c");
            return false;
         }
         
         
         /**
          * @return treeFile[], the array of the files under the folder that are in treeFile form 
          */
         public treeFile[] listFiles() {
            final File[] files = myFile.listFiles();
            if (files == null)
               return null;
            if (files.length < 1)
               return new treeFile[0];
            
            final treeFile[] ret = new treeFile[files.length];
            for (int i = 0; i < ret.length; i++) {
               final File f = files[i];
               ret[i] = new treeFile(f);
            }
            return ret;
         }
         
         @Override
         public String toString() {
            // returns only the name of the file not the extension
            if(isP2C())
               return myFile.getName().substring(0, myFile.getName().length() - 4);
            else return myFile.getName();
         }
      }
   }
   
   public class TreeClicker implements MouseListener {
      
      @Override
      public void mouseClicked(MouseEvent e) {
         // if double clicked
         if (e.getClickCount() == 2) {
            // gets that project and opens it
            pseu2code.saveCurrentProject();
            pseu2code.openProject(new File(settings.getSaveLocation() + "\\"
                                              + treeView.getPathForLocation(e.getX(), e.getY()).getLastPathComponent() + ".p2c"));
         }
      }
      
      @Override
      public void mousePressed(MouseEvent e) {
         
      }
      
      @Override
      public void mouseReleased(MouseEvent e) {
      }
      
      @Override
      public void mouseEntered(MouseEvent e) {
      }
      
      @Override
      public void mouseExited(MouseEvent e) {
      }
      
   }
   
}