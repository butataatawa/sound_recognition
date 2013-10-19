/**
 * 
 */
package pl.krakow.v_lo.algosound.maths;

import java.util.Vector;

import org.apache.commons.math3.complex.Complex;

/**
 * @author arccha
 */
public class FastFourierTransform
{

  private Vector<Complex> data;

  public FastFourierTransform(Vector<Complex> data) throws IllegalArgumentException
  {
    if (!isPowerOfTwo(data.size()))
      throw new IllegalArgumentException("Input vector size must be a power of two.");
    this.data = data;
  }

  public void transformForward()
  {
    data = transformRadix2(0, data.size(), 1, false);
  }

  public void transformBackward()
  {
    data = transformRadix2(0, data.size(), 1, true);
  }

  private Vector<Complex> transformRadix2(int startingIdx, int size, int step, boolean reverse)
  {
    Vector<Complex> result = new Vector<Complex>();
    if(size == 1)
    {
      result.add(data.get(startingIdx));
    }
    else
    {
      result.setSize(size);
      Vector<Complex> resultOfPart1 = transformRadix2(startingIdx, size / 2, step * 2, reverse);
      Vector<Complex> resultOfpart2 = transformRadix2(startingIdx + step, size / 2, step * 2, reverse);
      for(int i = 0; i < resultOfPart1.size(); ++i)
      {
        Complex omega;
        if(reverse)
          omega = new Complex(0, (2) * Math.PI * i / size);
        else
          omega = new Complex(0, (-2) * Math.PI * i / size);
        omega = omega.exp();
        
        Complex temp = resultOfPart1.get(i);
        result.set(i, temp.add(omega.multiply(resultOfpart2.get(i))));
        result.set(i + size / 2, temp.subtract(omega.multiply(resultOfpart2.get(i))));
        if(reverse)
        {
          result.set(i, result.get(i).divide(2));
          result.set(i + size / 2, result.get(i + size / 2).divide(2));
        }
      }
    }
    return result;
  }

  public Vector<Complex> getResult()
  {
    return data;
  }

  private boolean isPowerOfTwo(int number)
  {
    while (number % 2 == 0)
    {
      number /= 2;
    }
    if (number == 1)
      return true;
    return false;
  }

}
