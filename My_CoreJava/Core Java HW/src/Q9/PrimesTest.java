package Q9;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PrimesTest {
	
	List<Integer> nums, primes;

	@Before
	public void setUp() throws Exception {
		nums = new ArrayList<>();
		for (int i = 0; i < 30; i++) nums.add(i+1);
		
		primes = new ArrayList<>();
		primes.add(2);
		primes.add(3);
		primes.add(5);
		primes.add(7);
		primes.add(11);
		primes.add(13);
		primes.add(17);
		primes.add(19);
		primes.add(23);
		primes.add(29);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
				
		List<Integer> myPrimes = Primes.getPrimes(nums);
		
		if (primes.size() != myPrimes.size()) fail();		
		for (Integer i : primes) {
			assertTrue(myPrimes.contains(i));
		}
	}
}