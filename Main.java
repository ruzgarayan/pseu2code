import javax.swing.JFrame;

import model.Pseu2Code;
import view.MainPanel;
import view.MenuBar;
import view.MainFrame;

public class Main {
   public static void main(String[] args)
   {
      Pseu2Code p = new Pseu2Code();
      MainFrame frame = new MainFrame(p);
   }
}
