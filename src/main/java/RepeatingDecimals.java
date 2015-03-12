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


  public RepeatingDecimals yield() {
    compute2();
    return this;
  }


  public static void main(String args[]) {
    Integer n = Integer.parseInt(args[0]), m = Integer.parseInt(args[1]);
    RepeatingDecimals rd;
    rd = new RepeatingDecimals().numbers(n,m).yield();

  }

}
