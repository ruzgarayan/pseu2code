package model;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * This holds a set of integers and makes operations on them to help other classes.
 * @author together
 * @since 11.05.2019
 */
public class Path implements Serializable
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
      if (path.size() == 0)
         return null;
      
      Path copy = new Path(this);
      if (copy.getNumbers().size() > 0)
         copy.getNumbers().remove(copy.getNumbers().size() - 1);
      
      return copy;
   }
   
   public void changePath(Path newPath)
   {
      ArrayList<Integer> newList = newPath.getNumbers();
      path = new ArrayList<>();
      for (int i = 0; i < newList.size(); i++)
         path.add(newList.get(i));
   }
   
   public boolean equals(Path otherPath)
   {
      if (otherPath != null)
         return path.equals(otherPath.getNumbers());
      return false;
   }
   
   public int getSize()
   {
      return path.size();
   }
   
   /**
    * If this path is under the path given as parameter return true, if not return false.
    * Example -> 1.1 is under 1 , 1.3.2 is under 1.3 , 1.3.2 is under 1.3.2
    * -> 1.2 is not under 1.3 , 1.2 is not under 1.2.2 , 1.2.3.4 is not under 1.2.4
    * @param other the other path to compare.
    * @return true if given path is under this, false if not.
    */
   public boolean underThis(Path other)
   {
      ArrayList<Integer> otherNumbers = other.getNumbers();
      ArrayList<Integer> numbers = this.getNumbers();
      
      if (numbers.equals(otherNumbers))
         return true;
      if (numbers.size() <= otherNumbers.size())
         return false;
      
      for (int i = 0; i < otherNumbers.size(); i++)
      {
         if (numbers.get(i) != otherNumbers.get(i))
            return false;
      }
      
      return true;
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
      System.out.println();
      System.out.println(p3.underThis(p4));
      System.out.println(p4.underThis(p3));
      System.out.println(p3.underThis(p2));
      System.out.println(p4.underThis(p2));
   }
}
