/*
 * 
 */

$(document).ready(function(){
	
	
	document.getElementById("loginSubmit").
	addEventListener("click",login, true);
	
});

function login(){
	var email = document.getElementById("email").value;
	var pwd = document.getElementById("pwd").value;
	//e.preventDefault();
	
	var tx = [email, pwd];
	console.log(email, pwd);
	tx = JSON.stringify(tx);
	
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById("message")
			.innerHTML = xhr.responseText;
		}
	}
	
	console.log(tx);
	
	xhr.open("POST", "loginmessagetest", true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(tx);
	
	
};