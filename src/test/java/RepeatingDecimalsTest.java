import static org.junit.Assert.*;

public class RepeatingDecimalsTest {

  @org.junit.Before
  public void setUp() throws Exception {

  }

  @org.junit.After
  public void tearDown() throws Exception {

  }

  @org.junit.Test
  public void testNumbers() throws Exception {
    RepeatingDecimals bla;
//    bla = new RepeatingDecimals().numbers(1,3).yield();
    bla = new RepeatingDecimals().numbers(1,6).yield();
    bla = new RepeatingDecimals().numbers(5,7).yield();
    bla = new RepeatingDecimals().numbers(1,250).yield();
    bla = new RepeatingDecimals().numbers(300,31).yield();
    bla = new RepeatingDecimals().numbers(655,900).yield();
    bla = new RepeatingDecimals().numbers(1,3).yield();
  }
}