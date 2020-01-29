package view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import model.Pseu2Code;

/**
 * A JFrame to be able to add a window listener to program.
 * @author Salman Soomro
 * @since 11.05.2019
 */
public class MainFrame extends JFrame implements WindowListener {
   private Pseu2Code pseu2code;
   
   public MainFrame(Pseu2Code pseu2code) {
      super();
      this.pseu2code = pseu2code;
      add(new MainPanel(pseu2code));
      setJMenuBar(new MenuBar(pseu2code));
      addWindowListener(this);
      setBounds(400, 200, 800, 600);
      setVisible(true);
   }
   
   @Override
   public void windowOpened(WindowEvent e) {
   }
   
   @Override
   public void windowClosing(WindowEvent e) {
      pseu2code.saveCurrentProject();
      pseu2code.saveOptions();
      System.exit(1);
   }
   
   @Override
   public void windowClosed(WindowEvent e) {
   }
   
   @Override
   public void windowIconified(WindowEvent e) {
   }
   
   @Override
   public void windowDeiconified(WindowEvent e) {
   }
   
   @Override
   public void windowActivated(WindowEvent e) {
   }
   
   @Override
   public void windowDeactivated(WindowEvent e) {
   }
}
