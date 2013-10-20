package pl.krakow.v_lo.algosound;
import javax.swing.SwingUtilities;

import pl.krakow.v_lo.algosound.gui.AlgoSoundGUI;

/**
 * 
 */

/**
 * @author arccha
 */
public class AlgoSound
{

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {

      @Override
      public void run()
      {
        AlgoSoundGUI algosound = new AlgoSoundGUI();
        algosound.setVisible(true);
      }
    });
  }
}
