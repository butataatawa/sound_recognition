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

  private Vector<Complex> input;
  private Vector<Complex> output;

  public FastFourierTransform(Vector<Complex> input)
  {
    this.input = input;
  }

  public void transformForward()
  {

  }

  public void transformBackward()
  {

  }

  private Vector<Complex> transformRadix2()
  {

    return null;
  }

  public Vector<Complex> getResult()
  {
    return output;
  }

}
