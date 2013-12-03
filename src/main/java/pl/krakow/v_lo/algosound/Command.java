/**
 * 
 */
package pl.krakow.v_lo.algosound;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.complex.Complex;

/**
 * @author arccha
 */
public class Command
{
  private File          location;
  private String        name;
  private List<Complex> data;

  public Command(File location)
  {
    this.location = location;
    name = location.getName();
    data = new ArrayList<Complex>();
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
    Path path = Paths.get(location.getAbsolutePath());
    byte [] bytes = Files.readAllBytes(path);
    ByteBuffer bb = ByteBuffer.wrap(bytes);
    bb.order(ByteOrder.LITTLE_ENDIAN);
    while(bb.hasRemaining())
    {
      data.add(new Complex((double) bb.getShort(), 0));
    }
  }

  public List<Complex> getData()
  {
    return data;
  }

  public String getName()
  {
    return name;
  }

  @Override
  public String toString()
  {
    return "Command [name=" + name + "]";
  }

  public File getLocation()
  {
    return location;
  }
  
  
}
