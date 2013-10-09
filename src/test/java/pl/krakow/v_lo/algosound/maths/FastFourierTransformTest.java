/**
 * 
 */
package pl.krakow.v_lo.algosound.maths;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
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
public class FastFourierTransformTest
{

  Vector<Complex> inputData;

  public FastFourierTransformTest(Vector<Complex> inputData)
  {
    this.inputData = inputData;
  }

  @Parameters
  public static Collection<Object[]> generateData()
  {
    Complex[] first = { new Complex(5), new Complex(4), new Complex(3) };
    Complex[] second = { new Complex(9), new Complex(5), new Complex(8), new Complex(6), new Complex(7),
        new Complex(3), new Complex(4), new Complex(1), new Complex(0) };

    Object[][] data = new Object[][] { { new Vector<Complex>(Arrays.asList(first)) },
        { new Vector<Complex>(Arrays.asList(second)) } };

    return Arrays.asList(data);
  }

  @Test
  public void testForwardBackwardEquality()
  {
    FastFourierTransform transform = new FastFourierTransform(inputData);
    transform.transformForward();
    transform.transformBackward();
    assertEquals("Transforming forward and then backward should result in same vector", inputData,
        transform.getResult());
  }
}
