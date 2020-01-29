package view;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.*;

/**
 * Represents the entire project in a tree.
 * Holds pseu2code and options
 * @author Cankat
 * @since 12.05.2019
 */
public class StepNavigator extends JPanel {
   private Pseu2Code model;
   private Project project;
   private JTree treeView;
   private Tree treeModel;
   private JButton back, backToStart;
   private JPanel buttonsPanel;
   private JScrollPane treePanel;
   
   public StepNavigator(Pseu2Code model) {
      this.model = model;
      project = model.getProject();
      buttonsPanel = new JPanel(); // a panel for both back and backToStart buttons
      back = new JButton("Back"); // inits back
      backToStart = new JButton("Back to Start"); // and backToStart buttons
      
      buttonsPanel.setLayout(new GridLayout(2, 1)); // gridlayout for that panel
      buttonsPanel.add(back); // adds back
      buttonsPanel.add(backToStart); // and backToStart to that panel
      
      treePanel = new JScrollPane();
      treeModel = new Tree(); // inits the treeModel of the project
      treeView = new JTree(treeModel); // and adds that treeModel to the JTree
      treePanel.setViewportView(treeView);
      
      setLayout(new BorderLayout());
      add(treePanel, BorderLayout.CENTER);
      add(buttonsPanel, BorderLayout.SOUTH);
      
      DefaultTreeCellRenderer dcr = (DefaultTreeCellRenderer) treeView.getCellRenderer();
      dcr.setClosedIcon(dcr.getLeafIcon()); // this part is to make every node in the tree look the same
      dcr.setOpenIcon(dcr.getLeafIcon()); // every node looks like a document
      treeView.setRootVisible(false); // first node is just there for structure no need to show it
      treeView.addMouseListener(new TreeClicker());
      back.addActionListener(new TreeClicker());
      backToStart.addActionListener(new TreeClicker());
   }
   
   /**
    * Updates the tree in accordance with the Project
    */
   public void update() {
      project = model.getProject();
      treeModel = new Tree();
      treeView.setModel(treeModel);
      for (int i = 0; i < treeView.getRowCount(); i++) {
         treeView.expandRow(i);
      }
   }
   
   /**
    * @param Project, the project to change the treeView
    */
   public void setProject(Project project) {
      treeModel.setProject(project);
   }
   
   public class Tree implements TreeModel { // an inner class that creates the tree
      private Step rootStep = new Step(model, new Path(), 0);// creates an made up rootStep just for the purpose of
      // having one root
      
      Tree() {
         rootStep.setSubsteps(project.getInitialSteps()); // gives the root substeps of the project
      }
      
      /**
       * @return Object, the root of the treeModel
       */
      @Override
      public Object getRoot() {
         return rootStep;
      }
      
      /**
       * @param parent, the node in the tree a step
       * @param index,  the index of the child node
       * @return Object, the child node of the parent at the index
       */
      @Override
      public Object getChild(Object parent, int index) {
         if (parent != null && ((Step) parent).getSubsteps().getSize() > index)
            return ((Step) parent).getSubsteps().getStep(index);
         return null;
      }
      
      /**
       * @param parent, the step that has children
       * @return int, the number of substeps under the parent
       */
      @Override
      public int getChildCount(Object parent) {
         if (parent != null)
            return ((Step) parent).getSubsteps().getSize();
         return -1;
      }
      
      /**
       * @param node, the step node that doesn't have any children
       * @return boolean, true if the node doesn't have any children else false
       */
      @Override
      public boolean isLeaf(Object node) {
         if (node != null)
            return !(((Step) node).isExpanded());
         return false;
      }
      
      /**
       * @param parent, the step that has the child
       * @param child,  the child that is going to be checked
       * @return int, the index of the child
       */
      @Override
      public int getIndexOfChild(Object parent, Object child) {
         if (parent != null && child != null) {
            for (int i = 0; i < ((Step) parent).getSubsteps().getSize(); i++) {
               if (((Step) parent).getSubsteps().getStep(i).equals(child)) {
                  return i;
               }
            }
         }
         return -1;
      }
      
      /**
       * @param project, the project to be shown in the tree
       */
      public void setProject(Project project) {
         rootStep = new Step(model, new Path(), 0);
         rootStep.setSubsteps(project.getInitialSteps());
         update();
      }
      
      @Override
      public void valueForPathChanged(TreePath path, Object newValue) {
      }
      
      @Override
      public void addTreeModelListener(TreeModelListener l) {
      }
      
      @Override
      public void removeTreeModelListener(TreeModelListener l) {
      }
      
   }
   
   public class TreeClicker implements MouseListener, ActionListener {
      
      @Override
      public void mouseClicked(MouseEvent e) {
         
      }
      
      @Override
      public void mouseEntered(MouseEvent e) {
         
      }
      
      @Override
      public void mouseExited(MouseEvent e) {
         
      }
      
      @Override
      public void mousePressed(MouseEvent e) {
         if (e.getClickCount() == 2) { // if double clicked
            if (treeView.getPathForLocation(e.getX(), e.getY()) != null){
               Path selected = ((Step) treeView.getPathForLocation(e.getX(), e.getY()).getLastPathComponent()).getPath();
               
               project.setSelectedCurrentSteps(project.getSteps(selected.getParentPath())); // returns the selected step as a parent step
            }
         }
      }
      
      @Override
      public void mouseReleased(MouseEvent e) {
         
      }
      
      @Override
      public void actionPerformed(ActionEvent e) {
         if (e.getSource().equals(back) )  { // if the back button is pressed
            
            if (project.getSelectedCurrentSteps().getParentPath().getParentPath() != null)
               project.setSelectedCurrentSteps
               (project.getSteps(project.getSelectedCurrentSteps().getParentPath().getParentPath()));
            
         } else { // if the back to start button is pressed
            Steps s = null;
            project.setSelectedCurrentSteps(project.getInitialSteps()); // sets the current as the initial Steps
            project.setSelectedParentSteps(s);
         }
      }
      
   }
}