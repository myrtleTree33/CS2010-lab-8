import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by joel on 3/11/15.
 */
public class RepeatingDecimals {

  public String decimal;
  public BigDecimal n, m;

  public RepeatingDecimals RepeatingDecimals() {
    return this;
  }


  /**
   * This function stores the numerator and denominator values.
   *
   * As the maximum number of recurrent cycles cannot exceed the number of denominator
   * places, and limit is set to 3000, this is set to 3000.
   * @param n
   * @param m
   * @return The instance.
   */
  public RepeatingDecimals numbers(int n, int m) {
    this.n = BigDecimal.valueOf(n);
    this.m = BigDecimal.valueOf(m);

    MathContext mc = new MathContext(3002, RoundingMode.HALF_DOWN);
    DecimalFormat df = new DecimalFormat();
    df.setMinimumFractionDigits(3002);

    BigDecimal result = this.n.divide(this.m, mc);
    this.decimal = df.format(result).substring(2);
    this.decimal = this.decimal.substring(0, 3000);

    return this;
  }


  /**
   * Implements Floyd's cycle-finding algorithm.  That is,
   * 2 pointers are given, tortoise and hare.
   *
   * Where tortoise is = i
   * and hare = 2 * i, where i is the index, 0 < i < 3000.
   *
   * Theory follows if both tortoise and hare pointers are equal,
   * tortoise != 0 and hare != 0,
   * then the cycle is found and cycle length given by hare - tortoise.
   *
   * Certain boundary cases fail.  Hence, checks are given to avoid these
   * recurrent number failures (for a reasonable tolerance).
   *
   * Searching for this sequence has O(n) time complexity and O(1) space complexity.
   *
   * This may give numbers out of phase.  Hence they need to be shifted.
   *
   */
  private void compute2() {
    for (int i = 2; i < decimal.length() / 2; i++) {
      int j = 2 * i;
      if (decimal.charAt(i) == decimal.charAt(j)) {
        String result = decimal.substring(i, j);
        if (result.length() == 2 && result.charAt(0) == result.charAt(1)) {
          result = result.substring(1);
        } else if (result.length() == 3 &&
                result.charAt(0) == result.charAt(1) &&
                result.charAt(0) == result.charAt(2)) {
          result = result.substring(1);
        }
        System.out.println("Repeating cycle: " + findCorrectPhase(result) + "\n   Cycle length: " + result.length());
        return;
      }
    }
  }


  /**
   * The input sequence is out of phase.  To correct, a series of possible phases is generated.
   * This is searched through original string, yielding the earliest index found.
   *
   * This forms a key-value of entries, with the key being the phase and the value
   * the sequence.  This was chosen over arrays as sparse arrays waste space.
   *
   * After value is found in O(n) time, An iteration proceeds to find the value in
   * the set.  If the key is found (guaranteed), then the function returns with the
   * corrected phase string.
   *
   * This implies that on second iteration, it is highly possible that < n further
   * iterations are needed.
   *
   * @param arg the out of phase sequence
   * @return the sequence with corrected phase.
   */
  private String findCorrectPhase(String arg) {
    Map<String, Integer> shortestPhase = new HashMap<String, Integer>();
    int n = arg.length(), min = 0;
    for (int i = 0; i < arg.length(); i++) {
      String key = "";
      if (i == 0) {
        key = arg;
      } else {
        key = arg.substring(n - i, n) + arg.substring(0,n - i);
      }
      shortestPhase.put(key, decimal.indexOf(key));
      min = Collections.min(shortestPhase.values());
    }

    Iterator itr = shortestPhase.entrySet().iterator();

    while(itr.hasNext()) {
      Map.Entry<String, Integer> elem = (Map.Entry) itr.next();
      int val;
      val = (int) elem.getValue();
      if (val == min) {
        return (String) elem.getKey();
      }
    }
    return arg;
  }


  /**
   * Executes the function chain.
   * @return instance of itself.
   */
  public RepeatingDecimals yield() {
    compute2();
    return this;
  }


  /**
   * the first argument input is numerator, while second argument input is denominator.
   * @param args
   */
  public static void main(String args[]) {
    Integer n = Integer.parseInt(args[0]), m = Integer.parseInt(args[1]);
    RepeatingDecimals rd;
    rd = new RepeatingDecimals().numbers(n,m).yield();
  }

}
