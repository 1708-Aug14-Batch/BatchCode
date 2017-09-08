

window.onload = function() {
	displayDashBoardInfo();
};

function displayDashBoardInfo() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			console.log(xhr.responseText);
			var curUser = JSON.parse(xhr.responseText);
			console.log(curUser);
			document.getElementById("firstName").innerHTML = curUser.first;
			document.getElementById("lastName").innerHTML = curUser.last;
			document.getElementById("email").innerHTML = curUser.email;
		}
	};
	xhr.open("GET", "getUserInfoServlet", true);
	xhr.send();
}