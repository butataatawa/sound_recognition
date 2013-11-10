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
  private double  matchingRate;

  public MatchedResult(Command command)
  {
    this.command = command;
  }
  
  public MatchedResult(Command command, double matchingRate)
  {
    this.command = command;
    this.matchingRate = matchingRate;
  }

  public Command getCommand()
  {
    return command;
  }

  public double getMatchingRate()
  {
    return matchingRate;
  }
  
  public void setMatchingRate(double matchingRate)
  {
    this.matchingRate = matchingRate;
  }

  @Override
  public int compareTo(MatchedResult o)
  {
    return (int) matchingRate - (int) o.getMatchingRate();
  }

  @Override
  public String toString()
  {
    return "MatchedResult [command=" + command + ", matchingRate=" + matchingRate + "]";
  }
}
