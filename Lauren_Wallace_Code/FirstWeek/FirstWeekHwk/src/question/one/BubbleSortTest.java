package question.one;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BubbleSortTest {
	
	BubbleSort tester;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		tester = new BubbleSort();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void sortTest() {
		int[] expected = {0,1,2,3,3,4,5,6,7,8,9};
		int[] actual = tester.bubbleSortArray(expected);
		
		assertEquals(expected, actual);
	}

}