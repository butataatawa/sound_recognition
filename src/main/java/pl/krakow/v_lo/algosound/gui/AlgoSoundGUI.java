package pl.krakow.v_lo.algosound.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import pl.krakow.v_lo.algosound.sound.SoundPlayer;

/**
 * Hello world!
 */
public class AlgoSoundGUI extends JFrame
{

  /*
   * No idea what it is used for, but eclipse complains about it.
   */
  private static final long      serialVersionUID = -8821408889675820562L;
  private static final Dimension appDimension     = new Dimension(800, 600);

  public AlgoSoundGUI()
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

    BorderLayout layout = new BorderLayout(10, 10);
    JPanel panel = new JPanel(layout);
    panel.setPreferredSize(appDimension);

    initializeEast(panel);

    add(panel);
    pack();
  }

  private void initializeEast(JPanel panel)
  {
    GridLayout gridLayout = new GridLayout(3, 1);
    JPanel innerPanel = new JPanel(gridLayout);
    JButton record = new JButton("Record");
    record.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        // TODO Auto-generated method stub
        
      }
    });
    innerPanel.add(record);
    
    JButton play = new JButton("Play");
    innerPanel.add(play);
    
    JButton match = new JButton("Match");
    innerPanel.add(match);
    
    panel.add(innerPanel, BorderLayout.EAST);
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
}
