/**
 * 
 */
package pl.krakow.v_lo.algosound;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.apache.commons.math3.complex.Complex;

/**
 * @author arccha Simple, stupid class used to manage command .wav files, which are used as texts.
 */
public class Database
{
  File       databaseDir;
  File       rawCommandDir;
  File       propertiesFile;
  Properties properties;

  public Database()
  {
    databaseDir = new File("./.algosound");
    rawCommandDir = new File(databaseDir, "rawCommand");
    propertiesFile = new File(databaseDir, "database.properties");
    properties = new Properties();
    if (!databaseDir.exists())
    {
      databaseDir.mkdir();
      rawCommandDir.mkdir();
      initProperties();
    }
    try
    {
      properties.load(new FileInputStream(propertiesFile));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  private void initProperties()
  {
    properties.setProperty("commandList", "");
    saveProperties();
  }
  
  private void saveProperties()
  {
    try
    {
      properties.store(new FileOutputStream(propertiesFile), null);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public void saveRawCommand(String name, ByteArrayOutputStream stream)
  {
    if(!name.endsWith(".wav"))
      name += ".wav";
    File newCommandFile = new File(rawCommandDir, name);
    try
    {
      FileOutputStream newCommandStream = new FileOutputStream(newCommandFile);
      newCommandStream.write(stream.toByteArray());
      newCommandStream.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void saveCommand(Command command, String sufix) throws IOException
  {
    File saveFile = new File(command.getLocation().getParentFile(), command.getLocation().getName() + sufix);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    List<Complex> commandData = command.getData();
    for(Complex complex : commandData)
    {
      //TODO UPEWNIĆ SIĘ ŻE TO DZIAŁA
      double sample = complex.abs();
      short test = (short) sample;
      ByteBuffer bb = ByteBuffer.allocate(2);
      bb.order(ByteOrder.LITTLE_ENDIAN);
      bb.putShort(test);
      outputStream.write(bb.array());
    }
    InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    AudioFileFormat.Type audioType = AudioFileFormat.Type.WAVE;
    AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 1, 2, 44100.0F, false);
    AudioInputStream audioInputStream = new AudioInputStream(inputStream, audioFormat, outputStream.size());
    AudioSystem.write(audioInputStream, audioType, saveFile);
  }
  
  public void saveCurrentCommand(ByteArrayOutputStream stream)
  {
    saveRawCommand("command", stream);
  }

  public ArrayList<Command> getAllCommands()
  {
    File[] commands = databaseDir.listFiles(new FileFilter()
    {
      @Override
      public boolean accept(File arg0)
      {
        if (!arg0.isDirectory())
          return true;
        return false;
      }
    });
    ArrayList<Command> result = new ArrayList<Command>();
    for (File file : commands)
    {
      result.add(new Command(file));
    }
    return result;
  }

  public boolean isNameAvailable(String name)
  {
    File file = new File(getDatabasePath(name));
    return !file.exists();
  }

  public static String getDatabasePath(String name)
  {
    return "./databaseAS/" + name;
  }
}
