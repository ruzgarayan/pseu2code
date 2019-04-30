package Model;

import java.util.ArrayList;

public class Path
{
   private ArrayList<Integer> path;
   
   //Empty path constructor
   public Path() {
      path = new ArrayList<>();
   }
   
   //Copy constructor
   public Path(Path copyPath)
   {
      this();
      changePath(copyPath);
   }
   
   //Given the path of the parent step and the step number, creates the path of child step.
   //For the path [1, 2, 1] and step number 3, it creates [1, 2, 1, 3]
   public Path(Path parentPath, int stepNumber) {
      this(parentPath);
      path.add(stepNumber);
   }
   
   //Returns the array list of integers.
   public ArrayList<Integer> getNumbers() {
      return path;
   }  
   
   //Returns the last element of the array list.
   public int getStepNumber() {
      return path.get(path.size() - 1);
   }
   
   //For [1, 2, 3], returns 1.2.3
   public String toString() {
      String s = "";
      
      for (int i = 0; i < path.size() - 1; i++)
      {
         s = s + path.get(i) + ".";
      }
      if (path.size() != 0)
         s = s + path.get(path.size() - 1);
      
      return s;
   }
   
   public Path getParentPath() {
      if (path.size() > 0)
         path.remove(path.size() - 1);
   }
   
   public void changePath(Path newPath)
   {
      ArrayList<Integer> newList = newPath.getNumbers();
      path = new ArrayList<>();
      for (int i = 0; i < newList.size(); i++)
         path.add(newList.get(i));
   }
   
   public static void main(String args[])
   {
      Path p1 = new Path();
      System.out.println(p1);
      Path p2 = new Path(p1, 1);
      System.out.println(p2);
      Path p3 = new Path(p2, 2);
      System.out.println(p3);
      Path p4 = new Path(p3, 1);
      System.out.println(p4);
   }
}
