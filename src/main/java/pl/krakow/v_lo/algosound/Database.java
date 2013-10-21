/**
 * 
 */
package pl.krakow.v_lo.algosound;

import java.io.File;

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
