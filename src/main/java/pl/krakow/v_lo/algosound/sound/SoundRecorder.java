/**
 * 
 */
package pl.krakow.v_lo.algosound.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * @author arccha
 */
public class SoundRecorder extends Thread
{
  private TargetDataLine                    line;
  private AudioInputStream                  audioInputStream;
  private File                              outputFile;
  private static final AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;

  public SoundRecorder(File outputFile)
  {
    this.outputFile = outputFile;
  }

  public void startRecording()
  {
    setUpRecorder();
    line.start();
    super.start();
  }

  public void stopRecording()
  {
    line.stop();
    line.close();
  }

  @Override
  public void run()
  {
    try
    {
      AudioSystem.write(audioInputStream, targetType, outputFile);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  

  private void setUpRecorder()
  {
    AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2, 4, 44100.0F, false);
    DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
    try
    {
      line = (TargetDataLine) AudioSystem.getLine(info);
      line.open(audioFormat);
    }
    catch (LineUnavailableException e)
    {
      e.printStackTrace();
    }
    this.audioInputStream = new AudioInputStream(line);
  }

}
