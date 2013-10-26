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
  private static final int    THRESHOLD          = 0;
  private static final double SAMPLE_THRESHOLD   = 0.1F;

  public Matcher(Command pattern, Database database)
  {
    this.pattern = pattern;
    this.database = database;
    patternSamples = new ArrayList<List<Complex>>();
  }
  
  public List<List<Complex>> getPatternSamples()
  {
    return patternSamples;
  }

  public List<MatchedResult> match()
  {
    System.out.println("Starting matching.");
    
    List<MatchedResult> result = new ArrayList<MatchedResult>();
    patternSamples = computeSamplesFromCommand(pattern);
    
    for (Command command : database.getAllCommands())
    {
      if (command.getName().equals("command.wav"))
        continue;
      System.out.print("Matching " + command.getName() + "...");
      MatchedResult matchedResult = match(command);
      if (matchedResult.getMatchedSamples() > THRESHOLD)
        result.add(matchedResult);
      System.out.println(" (matched samples: " + matchedResult.getMatchedSamples() + ")");
    }
    Collections.sort(result, Collections.reverseOrder());
    return result;
  }

  private MatchedResult match(Command command)
  {
    MatchedResult result = new MatchedResult(command);
    List<List<Complex>> textSamples = computeSamplesFromCommand(command);
    int matchedSamples;
    // ucinaj wzorzec od przodu i przesuwaj
    for (int patternBegin = 0; patternBegin < patternSamples.size(); ++patternBegin)
      // przesuwaj wzorzec względem porównywanego tekstu (ucinaj tył tekstu)
      for (int textBegin = 0; textBegin < textSamples.size(); ++textBegin)
      {
//        System.out.print("matched samples (" + patternBegin + ", " + textBegin + "): ");
        matchedSamples = matchSamples(patternSamples, patternBegin, textSamples, textBegin);
//        System.out.println(matchedSamples);
        if(matchedSamples > result.getMatchedSamples())
          result.setMatchedSamples(matchedSamples);
      }
    return result;
  }

  private int matchSamples(List<List<Complex>> patternSamples, int patternBegin, 
                           List<List<Complex>> textSamples, int textBegin)
  {
    int matchedSamples = 0;
    double sumOfDiffSquares = 0;
    int pattern_i = patternBegin;
    int text_i = textBegin;
    while(text_i < textSamples.size() && pattern_i < patternSamples.size())
    {
      sumOfDiffSquares = 0;
      for(int j = 0; j < matchingSampleSize; ++j)
      {
        double patternVal = patternSamples.get(pattern_i).get(j).getReal();
        double textVal = textSamples.get(text_i).get(j).getReal();
        sumOfDiffSquares += Math.pow(patternVal - textVal, 2);
      }
//      System.out.println(sumOfDiffSquares / matchingSampleSize);
      if(sumOfDiffSquares / matchingSampleSize < SAMPLE_THRESHOLD)
        ++matchedSamples;
      ++text_i;
      ++pattern_i;
    }
    return matchedSamples;
  }

  private List<List<Complex>> computeSamplesFromCommand(Command command)
  {
    return computeSamplesFromCommand(command, matchingSampleSize);
  }
  
  public static List<List<Complex>> computeSamplesFromCommand(Command command, int matchingSampleSize)
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
      idx += matchingSampleSize;
    }
    return result;
  }
}
