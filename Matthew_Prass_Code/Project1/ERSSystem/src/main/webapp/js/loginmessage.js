var formdata;

$(document).ready(function(){
	$('#submitform').on('submit', function(e){
		e.preventDefault();
		formdata = $(this).serialize();
		login();
	})
	//document.getElementById("submitform").addEventListener("submit",login);
});


function login(){
//	var email = document.getElementById("name").value;
//	var pass = document.getElementById("paw").value;
//	var username = document.getElementById("username").value;
//	
//	var tx = [email, pass, username];
	
	//tx = JSON.stringify(tx);
	console.log(formdata);
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			var response =  xhr.responseText;
			
			if (response == "fail"){
				document.getElementById("message")
				.innerHTML = "Invalid credentials. Please try again";
			}
			else if(response == "pass"){
				document.getElementById("message")
				.innerHTML = "Invalid user. Please try again";
			}
			else{
				var emp = JSON.parse(response);
				if(emp.isManager == 0){
					console.log(response.text);
					window.location ="employee.html";
				}
				else{
					console.log(response.text);
					window.location="manager.html";
				}
			}
		}
	}
	xhr.open("GET","login?"+formdata,true);
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.send();
};

//function loadEmployeePage(){
//	var xhr = new XMLHttpRequest();
//	xhr.onreadystatechange = function(){
//		if(xhr.readyState == 4 && xhr.status == 200){
//			var response =  xhr.responseText;
//			//console.log(responseText);
//		}
//	}
//	xhr.open("POST","getEmployeePage",true);
//	xhr.send();
//}

