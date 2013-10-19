package pl.krakow.v_lo.algosound;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Hello world!
 */
public class AlgoSound extends JFrame
{

  /*
   * No idea what it is used for, but eclipse complains about it.
   */
  private static final long serialVersionUID = -8821408889675820562L;
  private static final Dimension appDimension = new Dimension(800, 600);

  public AlgoSound()
  {
    initializeUI();
  }

  private void initializeUI()
  {
    setTitle("Algosound");
    setSize(appDimension);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    initializeMenuBar();
    initializePanel();
  }
  
  private void initializePanel()
  {
    BorderLayout layout = new BorderLayout(10, 10);
    JPanel panel = new JPanel(layout);
    panel.setPreferredSize(appDimension);
    
    
    add(panel);
    pack();
  }

  private void initializeMenuBar()
  {
    JMenuBar menubar = new JMenuBar();
    JMenu database = new JMenu("Database");
    initializeDatabaseMenu(database);
    menubar.add(database);
    
    setJMenuBar(menubar);
  }

  private void initializeDatabaseMenu(JMenu database)
  {
    JMenuItem addCommand = new JMenuItem("Add command");
    addCommand.setToolTipText("Add command to database.");
    addCommand.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        
      }
    });
    database.add(addCommand);
  }

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {

      @Override
      public void run()
      {
        AlgoSound algosound = new AlgoSound();
        algosound.setVisible(true);
      }
    });
  }
}
