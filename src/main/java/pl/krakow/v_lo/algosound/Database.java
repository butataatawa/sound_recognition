/**
 * 
 */
package pl.krakow.v_lo.algosound;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.math3.complex.Complex;

/**
 * @author arccha Simple, stupid class used to manage command .wav files, which are used as texts.
 */
public class Database
{
  File       databaseDir;
  File       propertiesFile;
  Properties properties;

  public Database()
  {
    databaseDir = new File("./.algosound");
    propertiesFile = new File(databaseDir, "database.properties");
    properties = new Properties();
    if (!databaseDir.exists())
    {
      databaseDir.mkdir();
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

  public void saveRawCommandBytes(String name, ByteArrayOutputStream stream)
  {
    if(!name.endsWith(".wav"))
      name += ".wav";
    File newCommandFile = new File(databaseDir, name);
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
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    List<Complex> commandData = command.getData();
    for(Complex complex : commandData)
    {
      double sample = complex.abs();
      short test = (short) sample;
      ByteBuffer bb = ByteBuffer.allocate(2);
      bb.order(ByteOrder.LITTLE_ENDIAN);
      bb.putShort(test);
      outputStream.write(bb.array());
    }
    saveRawCommandBytes(command.getName() + sufix, outputStream);
  }
  
  public void saveCurrentCommand(ByteArrayOutputStream stream)
  {
    saveRawCommandBytes("command", stream);
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
  
  public Command getCommand(String commandName)
  {
    File commandFile = new File(databaseDir, commandName + ".wav");
    return new Command(commandFile);
  }
}
