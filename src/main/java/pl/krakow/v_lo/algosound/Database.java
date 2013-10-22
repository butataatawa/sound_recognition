/**
 * 
 */
package pl.krakow.v_lo.algosound;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

/**
 * @author arccha Simple, stupid class used to manage command .wav files, which are used as texts.
 */
public class Database
{
  File location;

  public Database()
  {
    location = new File("./databaseAS");
    if (!location.exists())
    {
      location.mkdir();
    }
  }
  
  public ArrayList<Command> getAllCommands()
  {
    File [] commands = location.listFiles(new FileFilter()
    {
      @Override
      public boolean accept(File arg0)
      {
        if(!arg0.isDirectory())
          return true;
        return false;
      }
    });
    ArrayList<Command> result = new ArrayList<Command>();
    for(File file : commands)
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
