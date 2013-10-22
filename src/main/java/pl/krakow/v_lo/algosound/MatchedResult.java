/**
 * 
 */
package pl.krakow.v_lo.algosound;

/**
 * @author arccha
 */
public class MatchedResult implements Comparable<MatchedResult>
{
  private Command command;
  private int     matchedSamples;

  public MatchedResult(Command command)
  {
    this.command = command;
  }
  
  public MatchedResult(Command command, int matchedSamples)
  {
    this.command = command;
    this.matchedSamples = matchedSamples;
  }

  public Command getCommand()
  {
    return command;
  }

  public int getMatchedSamples()
  {
    return matchedSamples;
  }
  
  public void setMatchedSamples(int matchedSamples)
  {
    this.matchedSamples = matchedSamples;
  }
  
  public void incrementMatchedSamples()
  {
    matchedSamples++;
  }

  @Override
  public int compareTo(MatchedResult o)
  {
    return matchedSamples - o.getMatchedSamples();
  }

  @Override
  public String toString()
  {
    return "MatchedResult [command=" + command + ", matchedSamples=" + matchedSamples + "]";
  }
}
