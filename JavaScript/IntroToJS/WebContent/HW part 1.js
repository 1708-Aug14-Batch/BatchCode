/**
 * JS Homework Connor Monson
 */

/*
 * 1. Fibonacci Define function: fib(n) Return the nth number in the fibonacci
 * sequence.
 */
function runFibonacci() {
	var display = document.getElementById("fibonacci_display");
	var n = document.getElementById("fibonacci_text").value;
	display.innerHTML = fibonacci(n);
}
function fibonacci(n) {
	if (n <= 1)
		return n;
	else
		return (fibonacci(n - 1) + fibonacci(n - 2));
}
document.getElementById("fibonacci_button").addEventListener("click",
		runFibonacci);

/*
 * 2. Bubble Sort Define function: bubbleSort(numArray) Use the bubble sort
 * algorithm to sort the array. Return the sorted array.
 */
function runBubbleSort() {
	var display = document.getElementById("bubble_display");
	var numArray = document.getElementById("bubble_text").value;
	// display.innerHTML = bubbleSort(numArray);
	alert(numArray);
	alert(numArray.length);
}
function bubbleSort(numArray) {
	var swapped = true;

	while (swapped) {
		// Set to false by default. If nothing is swapped then loop will end
		swapped = false;

		// Current index for searching through the array
		var i;
		/*
		 * Bubble sort will be looking at numbers in pairs so the loop ends at
		 * one previous to the last element
		 */
		for (i = 0; i < parseInt(numArray.length - 1); i++) {
			console.log(i);
			if (numArray[i] > numArray[i + 1]) {
				swapped = true;

				// Swapping two elements requires a third
				// temporary variable
				temp = numArray[i];
				numArray[i] = numArray[i + 1];
				numArray[i + 1] = temp;
			}
		}
		console.log(swapped);
		console.log(loop);
		
	}

	return numArray;
}
document.getElementById("bubble_button").addEventListener("click",
		runBubbleSort);

/*
 * 3. Reverse String Define function: reverseStr(someStr) Reverse and return the
 * String.
 */
function runReverseString() {
	var display = document.getElementById("reverse_display");
	var someStr = document.getElementById("reverse_text").value;
	display.innerHTML = reverseString(someStr);
}
function reverseString(someStr) {
	var i;
	var resultStr = "";

	for (i = someStr.length - 1; i >= 0; i--)
		resultStr = resultStr.concat(someStr.charAt(i));
	// resultStr = resultStr.concat(someStr.charAt(i));

	return resultStr;
}
document.getElementById("reverse_button").addEventListener("click",
		runReverseString);

/*
 * 4. Factorial Define function: factorial(someNum) Use recursion to compute and
 * return the factorial of someNum.
 */
function runFactorial() {
	var display = document.getElementById("factorial_display");
	var someNum = document.getElementById("factorial_text").value;
	display.innerHTML = factorial(someNum);
}
function factorial(someNum) {
	if (someNum == 1)
		return someNum;

	return someNum * factorial(someNum - 1);
}
document.getElementById("factorial_button").addEventListener("click",
		runFactorial);

/*
 * 5. Substring Define function substring(someStr, length, offset) Return the
 * substring contained between offset and (offset + length) inclusively. If
 * incorrect input is entered, use the alert function and describe why the input
 * was incorrect.
 */
function runSubstring() {
	var display = document.getElementById("substring_display");
	var someStr = document.getElementById("substring_text").value;
	var length = document.getElementById("substring_length").value;
	var offset = document.getElementById("substring_offset").value;
	display.innerHTML = substring(someStr, length, offset);
}
function substring(someStr, length, offset) {
	var i;
	var resultStr = "";
	if (length != parseInt(length)) {
		alert("Error: length needs to be a number");
		return null;
	}
	if (offset != parseInt(offset)) {
		alert("Error: offset needs to be a number");
		return null;
	}

	// Length and offset are both numbers at this point
	length = parseInt(length);
	offset = parseInt(offset);

	if (length < 0 || length > someStr.length) {
		alert("Error: length must be greater than 0 and less than the string length");
		return null;
	}
	if (offset < 0 || offset > someStr.length) {
		alert("Error: offset must be greater than 0 and less than the string length");
		return null;
	}
	if (offset + length > someStr.length) {
		alert("Error: offset+length must be less than the string length");
		return null;
	}

	for (i = offset; i < (offset + length); i++)
		resultStr = resultStr.concat(someStr.charAt(i));
	// resultStr = resultStr.concat(someStr.charAt(i));

	return resultStr;
}
document.getElementById("substring_button").addEventListener("click",
		runSubstring);

/*
 * 6. Even Number Define function: isEven(someNum) Return true if even, false if
 * odd. Do not use % operator.
 */
function runEvenDefiner() {
	var display = document.getElementById("even_display");
	var someNum = document.getElementById("even_text").value;
	display.innerHTML = evenDefiner(someNum);
}
function evenDefiner(someNum) {
	someNum = parseInt(someNum);
	var newNum = (Math.floor(someNum / 2) * 2);

	return (newNum == someNum);
}
document.getElementById("even_button")
		.addEventListener("click", runEvenDefiner);

/*
 * 7. Palindrome Define function isPalindrome(someStr) Return true if someStr is
 * a palindrome, otherwise return false
 */
function runPalindrome() {
	var display = document.getElementById("palindrome_display");
	var someStr = document.getElementById("palindrome_text").value;
	display.innerHTML = palindrome(someStr);
}
function palindrome(someStr) {
	// Remove whitespace
	someStr = someStr.replace(/\s+/g, '').toLowerCase();
	// Reverse string
	var newStr = reverseString(someStr);

	return (newStr == someStr);
}
document.getElementById("palindrome_button").addEventListener("click",
		runPalindrome);

/*
 * 8. Shapes Define function: printShape(shape, height, character) shape is a
 * String and is either "Square", "Triangle", "Diamond". height is a Number and
 * is the height of the shape. Assume the number is odd. character is a String
 * that represents the contents of the shape. Assume this String contains just
 * one character. Use a switch statement to determine which shape was passed in.
 * Use the console.log function to print the desired shape.
Example for printShape("Square", 3, "%");
%%%
%%%
%%%
Example for printShape("Triangle", 3, "$");
$
$$
$$$
Example for printShape("Diamond", 5, "*");
  *
 ***
*****
 ***
  *

 */
function runShape() {
	var display = document.getElementById("shape_display");
	var someChar = document.getElementById("shape_text").value;
	var height = document.getElementById("shape_height").value;
	
	var form = document.getElementById('radios');
	var radios = form.elements['radio'];
	var val;
	for (var i=0, len=radios.length; i<len; i++) {
		if (radios[i].checked == true) {
			val = radios[i].value;
			break;
		}
	}
	
	display.innerHTML = shape(someChar, height, val);
}
function shape(someChar, height, type) {

	for (var i = 0; i < height; i++) {
		switch (type) {
		case 'square':
			var str = "";
			for (var j = 0; j < height; j++)
				str += someChar;
			console.log(str);
			break;

		case 'triangle':
			var str = ""
			for (var j = 0; j < i + 1; j++)
				str += someChar;
			console.log(str);
			break;

		case 'diamond':
			var str = "";
			
			/*
			 * This part almost makes a diamond. More like a square
			 * with the bottom-left and top-right cut off
			 */
			for (var j = 0; j < height; j++) {

				if (Math.abs(i - j) <= Math.floor(height/2)) {
					str += someChar;

				} else
					str += " ";
			}
			
			// This makes an almost diamond with both left corners cut off
			if (i < height/2)
				str = reverseString(str);
			
			// So if the left side is correct, reflect it (and overwrite) the right side
			var tempStr = substring(str, Math.floor(parseInt(str.length/2)), 0);
			str = tempStr + someChar + reverseString(tempStr);
			
			console.log(str);
			break;
		}
	}

	return "(See console for output)";
}
document.getElementById("shape_button").addEventListener("click", runShape);

/*
 * 9. Object literal Define function traverseObject(someObj) Print every
 * property and it's value.
 */
function runLiteral() {
	var display = document.getElementById("literal_display");
	var someObj = {"name":"Dude", "favorite color":"Green", "favorite drink":"Sprite"};
	display.innerHTML = traverseObject(someObj);
}
function traverseObject(someObj) {
	var someStr = "";
	
	var propValue;
	for(var propName in someObj) {
	    propValue = someObj[propName]

	    someStr += propName + ":" + propValue + ", ";
	}
	
	return someStr;
}
document.getElementById("literal_button").addEventListener("click", runLiteral);

/*
 * 10. Delete Element Define function deleteElement(someArr)
 * Print length
 * Delete the third element in the array.
 * Print length The lengths should be the same.
 */
function runDeleteEl() {
	var display = document.getElementById("delete_display");
	var someArr = ["low", "fuel", "audio", "ordeal", "pulse", "talk", "context", "arson", "foot", "beat", "bland"];
	var someStr = "";
	someStr += "Array length: " + someArr.length + " before, ";
	someStr += deleteElement(someArr);
	
	display.innerHTML = someStr;
}
function deleteElement(someArr) {
	var someStr = "";

	if (someArr.length < 3) {
		str += "Array isn't long enough";
		return someStr;
	}
	
	// Cut out the third element
	someArr[3] = "";
	
	someStr += "Array length " + someArr.length + " after deleting the third element. New array: " + someArr;
	
	return someStr;
}
document.getElementById("delete_button").addEventListener("click", runDeleteEl);

/*
 * 11. Splice Element Define function
 * spliceElement(someArr) Print length
 * Splice the third element in the array.
 * Print length The lengths should be one less than the original length.
 */
function runSpliceEl() {
	var display = document.getElementById("splice_display");
	var someArr = ["low", "fuel", "audio", "ordeal", "pulse", "talk", "context", "arson", "foot", "beat", "bland"];
	var someStr = "";
	someStr += "Array length: " + someArr.length + " before, ";
	someStr += spliceElement(someArr);
	
	display.innerHTML = someStr;
}
function spliceElement(someArr) {
	var someStr = "";

	if (someArr.length < 3) {
		str += "Array isn't long enough";
		return someStr;
	}
	
	// Cut out the third element
	var newArr = someArr.splice(0, 2);
	newArr = newArr.concat(someArr.splice(1, someArr.length-1));
	
	someStr += "Array length " + newArr.length + " after deleting the third element. New array: " + newArr;
	
	return someStr;
}
document.getElementById("splice_button").addEventListener("click", runSpliceEl);

/*
 * 12. Defining an object using a constructor Define a function Person(name,
 * age) The following line should set a Person object to the variable john: var
 * john = new Person("John", 30);
 */
function runDefineCon() {
	var display = document.getElementById("define_con_display");
	var someStr = document.getElementById("define_con_text").value;
	
	var john = new Person("John", 30);
	
	display.innerHTML = traverseObject(john);
}
function Person(name, age) {
	
	return {"name":name, "age":age};
}
document.getElementById("define_con_button").addEventListener("click",
		runDefineCon);

/*
 * 13. Defining an object using an object literal
 * Define function getPerson(name, age)
 * The following line should set a Person object to the variable john:
 *    var john = getPerson("John", 30);
 */
function runDefineLit() {
	var display = document.getElementById("define_lit_display");
	var someStr = document.getElementById("define_lit_text").value;
	
	var john = getPerson("John", 30);
	
	display.innerHTML = traverseObject(john);
}
function getPerson(name, age) {

	return Person(name, age);
	
}
document.getElementById("define_lit_button").addEventListener("click",
		runDefineLit);
