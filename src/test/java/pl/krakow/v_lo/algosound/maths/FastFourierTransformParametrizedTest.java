/**
 * 
 */
package pl.krakow.v_lo.algosound.maths;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import java.util.Vector;

import org.apache.commons.math3.complex.Complex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author arccha
 */
@RunWith(Parameterized.class)
public class FastFourierTransformParametrizedTest
{

  Vector<Complex> inputData;
  Vector<Complex> expectedOutput;

  public FastFourierTransformParametrizedTest(Vector<Complex> inputData, Vector<Complex> expectedOutput)
  {
    this.inputData = inputData;
    this.expectedOutput = expectedOutput;
  }

  @Parameters
  public static Collection<Object[]> generateData()
  {
    InputStream in = FastFourierTransform.class.getClassLoader().getResourceAsStream("fftTests.txt");
    Scanner scanner = new Scanner(in);
    final int numberOfTestCases = scanner.nextInt();
    scanner.nextLine();
    Object[][] inputData = new Object[numberOfTestCases][2];
    for(int i = 0; i < numberOfTestCases; ++i)
    {
      String line = scanner.nextLine();
      String[] splitted = line.split("\\|"); 
      
      Vector<Complex> input = new Vector<Complex>();
      Scanner inputScanner = new Scanner(splitted[0]);
      while(inputScanner.hasNextDouble())
      {
        double real = inputScanner.nextDouble();
        double imag = inputScanner.nextDouble();
        input.add(new Complex(real, imag));
      }
      inputScanner.close();
      inputData[i][0] = input;
      
      Vector<Complex> result = new Vector<Complex>();
      Scanner resultScanner = new Scanner(splitted[1]);
      while(resultScanner.hasNextDouble())
      {
        double real = resultScanner.nextDouble();
        double imag = resultScanner.nextDouble();
        result.add(new Complex(real, imag));
      }
      resultScanner.close();
      inputData[i][1] = result;
    }
    scanner.close();
    return Arrays.asList(inputData);
  }

  @Test
  public void testForwardBackwardEquality()
  {
    FastFourierTransform transform = new FastFourierTransform(inputData);
    transform.transformForward();
    FastFourierTransform second = new FastFourierTransform(transform.getResult());
    second.transformBackward();
    assertEquals("Transforming forward and then backward should result in the same vector", inputData,
        second.getResult());
  }
  
  @Test
  public void testResultOfTransform()
  {
    FastFourierTransform transform = new FastFourierTransform(inputData);
    transform.transformForward();
    Vector<Complex> result = transform.getResult();
    for(int i = 0; i < result.size(); ++i)
    {
      assertEquals(expectedOutput.get(i).getReal(), result.get(i).getReal(), 10e-6);
      assertEquals(expectedOutput.get(i).getImaginary(), result.get(i).getImaginary(), 10e-6);
    }
  }
}
