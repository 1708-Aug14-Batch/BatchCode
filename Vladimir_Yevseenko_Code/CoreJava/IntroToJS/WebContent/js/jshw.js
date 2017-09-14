
function fib(n) {
	if (n <= 1)
		return n;
	return fib(n-1) + fib(n-2);
}


function runFib() {
	var display = document.getElementById("fibOut");
	var n = document.getElementById("fibIn").value;
	display.innerHTML = fib(n);
}

document.getElementById("doFib").addEventListener("click", runFib);