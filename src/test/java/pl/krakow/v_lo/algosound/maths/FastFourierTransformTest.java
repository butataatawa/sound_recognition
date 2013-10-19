/**
 * 
 */
package pl.krakow.v_lo.algosound.maths;

import java.util.Vector;

import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

/**
 * @author arccha
 */
public class FastFourierTransformTest
{
  
  @Test(expected = IllegalArgumentException.class)
  public void testIfInputVectorHasNotSizeThatIsPowerOfTwoItThrowsException()
  {
    Vector<Complex> data = new Vector<Complex>();
    data.add(new Complex(1, 2));
    data.add(new Complex(2, 3));
    data.add(new Complex(1, 3));
    @SuppressWarnings("unused")
    FastFourierTransform fft = new FastFourierTransform(data);
  }

}
