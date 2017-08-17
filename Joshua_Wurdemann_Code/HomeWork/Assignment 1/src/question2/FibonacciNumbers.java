package question2;
/** Display the first 25 Fibonacci numbers beginning with zero.
 * To obtain the sequence, we start with 0 and 1 after 
   which every number is the sum of the previous two numbers.
 * @author jwurd
 *@version Aug 14 2017
 */
public class FibonacciNumbers {
	

public static void main(String[]args){
	int  n = 4;

	//for(int i = 0; i <= n; i++){
	System.out.println(fibonacci(n) + "");
	
}
/** Using recursion. Determines the current fibonacci number.
 * @param n
 * @return fibonacci numbers
 */
public static int fibonacci(int n){
//	if( n == 0){
//		return 0; // Base Case
//	} else if (n == 1) {
	if(n<=1){
		return n;
	} else {
		return (fibonacci(n -1) + fibonacci(n -2));
	
	}
	
}
}