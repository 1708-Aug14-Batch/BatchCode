window.onload = function(){
		
	loadDashboardView();
	
};

//Views
function loadDashboardView(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById('view').
			innerHTML = xhr.responseText;
			getUserInformation();
		}
	}
	console.log("getting dash")
	xhr.open("GET", "getDashboard", true);
	xhr.send();
}

function getUserInformation(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			console.log(xhr.responseText);
			var dto = JSON.parse(xhr.responseText);
			var user = dto.user;
			var accounts = dto.accounts;
			
			document.getElementById('name')
				.innerHTML = user.firstname + " " + user.lastname;
			if (accounts.length == 0){
				document.getElementById("accounts").style.visibility = "hidden"; 
				
			}
			else{
				console.log("not null");
				console.log(accounts.length());
				// populate accounts table
			}
		}
	}
	xhr.open("GET", "getUserInfo", true);
	xhr.send();
	
}
