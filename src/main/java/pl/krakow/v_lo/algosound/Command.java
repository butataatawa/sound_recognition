/**
 * 
 */
package pl.krakow.v_lo.algosound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.math3.complex.Complex;

/**
 * @author arccha
 */
public class Command
{
  private File               location;
  private String             name;
  private ArrayList<Complex> rawData;

  public Command(File location)
  {
    this.location = location;
    name = location.getName();
    rawData = new ArrayList<Complex>();
    try
    {
      loadRawData();
    }
    catch (IOException e)
    {
      // Something is wrong with audioInputStream.available()
      e.printStackTrace();
    }
  }

  private void loadRawData() throws IOException
  {
    FileInputStream audioSrc = null;
    try
    {
      audioSrc = new FileInputStream(location);
    }
    catch (FileNotFoundException e1)
    {
      e1.printStackTrace();
    }
    InputStream bufferedAudio = new BufferedInputStream(audioSrc);
    AudioInputStream audioInputStream = null;
    try
    {
      audioInputStream = AudioSystem.getAudioInputStream(bufferedAudio);
    }
    catch (UnsupportedAudioFileException | IOException e)
    {
      e.printStackTrace();
    }
    while (audioInputStream.available() > 0)
    {
      byte[] buffer = new byte[2];
      audioInputStream.read(buffer);
      double sample = ((buffer[0] & 0xFF) | (buffer[1] << 8)) / 32768.0; // It's magick. Don't ask.
      rawData.add(new Complex(sample));
    }
    audioInputStream.close();
  }

  public ArrayList<Complex> getRawData()
  {
    return rawData;
  }

  public String getName()
  {
    return name;
  }
}
