/**
 * 
 */
$(document).ready(function(){
	console.log("HERE");
		
	
	
	
	$("#AddNewUser").on("click", function(){
		$("#AddingModal").modal('show');
	})
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			
			var dto = JSON.parse(xhr.responseText);
			var user = dto.user
			var EmployeeList = dto.EmployeeList
			
			
			for(var i = 0; i < EmployeeList.length; i++){
			var table = document.getElementById("list");
			var row = table.insertRow();
			var fname = row.insertCell(0);
			var lname = row.insertCell(1);
			var email = row.insertCell(2);
			
			if(user.isManager == 0){
				fname.innerHTML = user.firstName;
				lname.innerHTML = user.lastName;
				email.innerHTML = user.email;
				
				break;
			}
			else{
				fname.innerHTML = EmployeeList[i].firstName;
				lname.innerHTML = EmployeeList[i].lastName;
				email.innerHTML = EmployeeList[i].email;
			}
				
			}
			if(user.isManager == 1){
				$('#EditUserInfo').remove();
				
		}
			if(user.isManager == 0){
				$("#AddNewUser").remove();
			}

		}
		$("#cNewUser").on("click",function(){
			
			
			var xhr2 = new XMLHttpRequest();
			var fname = $('#firstname').val();
			var lname = $("#lastname").val();
			var email = $('#email').val();
			var password = $('#password').val();
			
			var tx = [fname,lname,email,password];
			
			tx = JSON.stringify(tx);
			
			xhr2.open("POST","UserInform",true);
			xhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhr2.send(tx);
			
			
			
			
		})
	}
	xhr.open("GET", "UserInform", true);
	xhr.send();
	
	
	
});