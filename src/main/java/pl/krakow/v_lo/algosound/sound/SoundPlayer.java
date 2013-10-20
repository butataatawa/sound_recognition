/**
 * 
 */
package pl.krakow.v_lo.algosound.sound;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author arccha
 */
public class SoundPlayer
{
  private InputStream      sound;
  private static final int BUFFER_LENGHT = 128000;

  public SoundPlayer(InputStream sound)
  {
    this.sound = sound;
  }

  public void playSound()
  {
    // Prepare input stream
    AudioInputStream audioInputStream = null;
    try
    {
      audioInputStream = AudioSystem.getAudioInputStream(sound);
    }
    catch (UnsupportedAudioFileException | IOException e)
    {
      e.printStackTrace();
    }

    // Prepare output line
    AudioFormat audioFormat = audioInputStream.getFormat();
    SourceDataLine line = null;
    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
    try
    {
      line = (SourceDataLine) AudioSystem.getLine(info);
      line.open(audioFormat);
    }
    catch (LineUnavailableException e)
    {
      e.printStackTrace();
    }
    line.start();

    // Play sound
    int nBytesRead = 0;
    byte[] data = new byte[BUFFER_LENGHT];
    while (nBytesRead != -1)
    {
      try
      {
        nBytesRead = audioInputStream.read(data, 0, data.length);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      if (nBytesRead >= 0)
      {
        line.write(data, 0, nBytesRead);
      }
    }
    line.drain();
    line.close();
  }

}
