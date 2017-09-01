package Q8;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PalindromeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		List<String> words = new ArrayList<>();
		List<String> palindromes = new ArrayList<>();
		
		words.add("karan");
		words.add("madam");
		palindromes.add("madam");
		words.add("tom");
		words.add("civic");
		palindromes.add("civic");
		words.add("radar");
		palindromes.add("radar");
		words.add("sexes");
		palindromes.add("sexes");
		words.add("jimmy");
		words.add("kayak");
		palindromes.add("kayak");
		words.add("john");
		words.add("refer");
		palindromes.add("refer");
		words.add("billy");
		words.add("did");
		palindromes.add("did");
		
		List<String> myPalindromes = Palindrome.getPalindromes(words);
		
		if (palindromes.size() != myPalindromes.size()) fail();
		
		for (String s : palindromes) {
			assertTrue(myPalindromes.contains(s));
		}
		
	}

}