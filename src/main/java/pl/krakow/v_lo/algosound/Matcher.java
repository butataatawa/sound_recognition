/**
 * 
 */
package pl.krakow.v_lo.algosound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.complex.Complex;

import pl.krakow.v_lo.algosound.maths.FastFourierTransform;

/**
 * @author arccha
 */
public class Matcher
{
  private Command             pattern;
  private Database            database;
  private List<List<Complex>> patternSamples;
  private static final int    matchingSampleSize = 1024;
  private static final int    THRESHOLD          = 512;
  private static final int    SAMPLE_THRESHOLD   = 10;

  public Matcher(Command pattern, Database database)
  {
    this.pattern = pattern;
    this.database = database;
    patternSamples = new ArrayList<List<Complex>>();
  }

  public List<MatchedResult> match()
  {
    List<MatchedResult> result = new ArrayList<MatchedResult>();
    patternSamples = computeSamplesFromCommand(pattern);
    for (Command command : database.getAllCommands())
    {
      MatchedResult matchedResult = match(command);
      if (matchedResult.getMatchedSamples() > THRESHOLD)
        result.add(matchedResult);
    }
    Collections.sort(result);
    return result;
  }

  private MatchedResult match(Command command)
  {
    MatchedResult result = new MatchedResult(command);
    List<List<Complex>> textSamples = computeSamplesFromCommand(command);
    for (List<Complex> patternSample : patternSamples)
    {
      for (List<Complex> textSample : textSamples)
      {
        if (matchSamples(patternSample, textSample))
          result.incrementMatchedSamples();
      }
    }
    return result;
  }

  private boolean matchSamples(List<Complex> pattern, List<Complex> text)
  {
    double meanSquaredError = 0;
    Complex sumOfSquares = new Complex(0);
    for(int i = 0; i < pattern.size(); ++i)
    {
      Complex textVal = text.get(i);
      Complex patternVal = pattern.get(i);
      sumOfSquares.add(textVal.subtract(patternVal).pow(2)); //WARNING!!!
    }
    meanSquaredError = sumOfSquares.getReal() / pattern.size();
    if(meanSquaredError < SAMPLE_THRESHOLD)
      return true;
    return false;
  }

  private List<List<Complex>> computeSamplesFromCommand(Command command)
  {
    List<List<Complex>> result = new ArrayList<List<Complex>>();
    List<Complex> rawData = command.getRawData();
    int idx = 0;
    while (idx + matchingSampleSize - 1 < rawData.size())
    {
      List<Complex> sample = rawData.subList(idx, idx + matchingSampleSize);
      FastFourierTransform fft = new FastFourierTransform(sample);
      fft.transformForward();
      result.add(fft.getResult());
    }
    return result;
  }
}
