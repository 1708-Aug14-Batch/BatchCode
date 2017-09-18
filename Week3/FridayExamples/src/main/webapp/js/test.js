

var myJSON = '{"name": "Jathmel", "age": 10, "city": "Baltimore"}';

var myObj = JSON.parse(myJSON);
console.log(myJSON);
console.log(myObj);

document.getElementById("demo").innerHTML = myObj.name;

function getText(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById("things").innerHTML = xhr.responseText;
		}
	}
	xhr.oen("GET", "getText", true);
	xhr.send();

	document.getElementById("myBtn").addEventListener("click", getTest);
}