package pl.krakow.v_lo.algosound.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pl.krakow.v_lo.algosound.AlgoSound;
import pl.krakow.v_lo.algosound.sound.SoundPlayer;
import pl.krakow.v_lo.algosound.sound.SoundRecorder;

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
  private final AlgoSoundGUI     THIS             = this;
  private AlgoSound              algoSound;

  public AlgoSoundGUI(AlgoSound algoSound)
  {
    this.algoSound = algoSound;
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
    JPanel innerPanel = new JPanel();
    BoxLayout boxLayout = new BoxLayout(innerPanel, BoxLayout.Y_AXIS);
    innerPanel.setLayout(boxLayout);

    JButton record = new JButton("Record");
    record.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        SoundRecorder soundRecorder = new SoundRecorder(new File("./command.wav"));
        soundRecorder.startRecording();
        try
        {
          Thread.sleep(1500);
        }
        catch (InterruptedException e)
        {
          Thread.currentThread().interrupt();
        }
        soundRecorder.stopRecording();
      }
    });

    JButton match = new JButton("Match");
    match.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        // TODO Auto-generated method stub

      }
    });

    JButton playCommand = new JButton("Play command");
    playCommand.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        File soundFile = new File("./command.wav");
        BufferedInputStream sound = null;
        try
        {
          FileInputStream tmp = new FileInputStream(soundFile);
          sound = new BufferedInputStream(tmp);
        }
        catch (FileNotFoundException e1)
        {
          JOptionPane.showMessageDialog(THIS, "You need to record command first.");
          return;
        }
        SoundPlayer soundPlayer = new SoundPlayer(sound);
        soundPlayer.playSound();
      }
    });

    JButton playMatched = new JButton("Play matched");
    playMatched.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        // TODO Auto-generated method stub

      }
    });

    justifyButtonsAndAdd(innerPanel, record, playCommand, playMatched, match);

    panel.add(innerPanel, BorderLayout.EAST);
  }

  private void justifyButtonsAndAdd(JPanel panel, JButton... buttons)
  {
    Dimension dimension = new Dimension(140, 30);
    Component spacer = Box.createRigidArea(new Dimension(5, 5)); // TODO Don't work FIX it later.
    for (JButton button : buttons)
    {
      button.setMinimumSize(dimension);
      button.setPreferredSize(dimension);
      button.setMaximumSize(dimension);
      button.setSize(dimension);
      panel.add(button);
      panel.add(spacer);
    }
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
        AddCommand addCommand = new AddCommand(THIS, algoSound);
        addCommand.setVisible(true);
      }
    });
    database.add(addCommand);
  }
}
