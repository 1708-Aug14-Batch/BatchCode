/**
 * 
 */

// TODO form validation before clicking a button
// TODO Look into callback functions to get a result from an XMLHttpRequest function
//					https://stackoverflow.com/questions/12421860/returning-xmlhttp-responsetext-from-function-as-return

var loggedIn = false;
window.onload = function() {

	$("#navbarDiv").attr("hidden", true);
	
	// If a session is currently active, log in to that one
	// Otherwise show the login screen
	tryLogin();

	setEventListeners();
	
};


function setEventListeners() {
	// Navbar
	$("#home").click(loadDashboard);
	$("#submitReim").click(submitReim);
	$("#viewPendingReim").click(viewPendingReim);
	$("#viewResolvedReim").click(viewResolvedReim);
	$("#resolveReim").click(resolveReim);
	
	$("#profile").click(loadProfileView);
	
	// Set event listeners for logging in and creating an account
	$("#username_input").keypress(handleKeyPress);
	$("#password_input").keypress(handleKeyPress);
	$("#login_button").click(login);
	$("#create_button").click(loadCreateAccount);
	
	// Miscellaneous
	$("#view_password_check").click(togglePasswordView);
	$("#update_profile").click(updateAccount);
	$("#submit_button").click(submitReim);
}

function hideAllViews() {
	$("#loginDiv").attr("hidden", true);
	$("#profileDiv").attr("hidden", true);
	$("#viewAccDiv").attr("hidden", true);
	$("#submitReimDiv").attr("hidden", true);
	$("#transferDiv").attr("hidden", true);
	$("#withdrawDiv").attr("hidden", true);
	$("#editDiv").attr("hidden", true);
	$("#dashboardDiv").attr("hidden", true);
	$("#createAccountDiv").attr("hidden", true);
	$("#reimTableDiv").attr("hidden", true);
}

function loadLoginView() {
	hideAllViews();
	$("#navbarDiv").attr("hidden", true);
	$("#loginDiv").attr("hidden", false);
}

function loadDashboard() {
	hideAllViews();
	$("#navbarDiv").attr("hidden", false);
	$("#dashboardDiv").attr("hidden", false);
	$("#dashboardDiv").find("h3")[0].innerHTML = "Welcome to";
	$("#dashboardDiv").find("h3")[1].innerHTML = "The Online Reimbursement Resource";
	
	// TODO
}

function submitReim() {
	hideAllViews();
	//$("#viewAccDiv").attr("hidden", false);
	 
	// TODO
}

function loadCreateAccount() {
	hideAllViews();
	$("#loginDiv").attr("hidden", false);
	$("#createAccountDiv").attr("hidden", false);
	// TODO
}

function loadProfileView() {
	hideAllViews();
	$("#message_edit").text("");	// Clear message text
	$("#view_password_check").attr("checked", false);	// FIXME doesn't de-select the checkbox
	displayProfileInformation();
	$("#profileDiv").attr("hidden", false);
}

function viewPendingReim() {
	hideAllViews();
	//viewAccounts();
	//$("#submitReimDiv").attr("hidden", false);;
	// TODO	
}
function viewAllPendingReim() {
	
}
function viewResolvedReim() {
	hideAllViews();
	//viewAccounts();
	//$("#transferDiv").attr("hidden", false);
	// TODO	
}
function viewAllResolvedReim() {
	
}
function resolveReim() {
	hideAllViews();
	//viewAccounts();
	//$("#withdrawDiv").attr("hidden", false);
	// TODO	
}
function submitReim() {
	hideAllViews();
	$("#submitReimDiv").attr("hidden", false);
	
	submitReimbursement()
}

function togglePasswordView() {
	if ($("#view_password_check").is(":checked"))
		$("#password_edit").attr("type", "text");
	else
		$("#password_edit").attr("type", "password");
}

//Called when the user presses a key in the password field
function handleKeyPress(event) {
	var PRESS_ENTER = 13;
	if (event.keyCode == PRESS_ENTER)
		login();
}

function viewAccounts() {
	$("#reimTableDiv").attr("hidden", false);
	
	console.log("Getting account information");
	getAccountInformation(); // loads user info by calling function
}

function submitReimbursement() {
	var employeeId = $("#id_text").text();
	var description = $("#submit_description")[0].value;
	var ammount = $("#submit_ammount")[0].value;
	
	var dto = [employeeId, description, ammount];
	
	dto = JSON.stringify(dto);
	console.log("updateAccount dto: " + dto);
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// Message arrived
			var response =  xhr.responseText;

			console.log("xhr response arrived in updateAccount()");
			if (response == "false"){
				$("#message_edit").text("Information could not be updated");
				$("#message_edit").attr("style", "color:red");
			}
			else {
				setTimeout(loadProfileView(), 0);
				
				$("#message_edit").text("Information updated.");
				$("#message_edit").attr("style", "color:green");
			}
			
		}
	}
	
	xhr.open("POST", "updateProfile", true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(dto);
}

function displayProfileInformation() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {

		if (xhr.readyState == 4 && xhr.status == 200) {
			var user = JSON.parse(xhr.responseText).user;
			console.log("Displaying profile...");
			// Set display information:
			// id, name, username, email
			$("#id_text").text(user.workerId);
			$("#name_text").text(user.firstName + " " + user.lastName);
			$("#username_text").text(user.username);
			$("#email_text").text(user.email);
			
			// Set values for edit information:
			// firstname, lastname, username, password, email
			$("#firstname_edit").attr("value", user.firstName);
			$("#lastname_edit").attr("value", user.lastName);
			$("#username_edit").attr("value", user.username);
			$("#password_edit").attr("value", user.password);
			$("#email_edit").attr("value", user.email);
		}
	}
	xhr.open("GET", "getUserInfo", true);
	xhr.send();

}

function getAccountInformation() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {

		if (xhr.readyState == 4 && xhr.status == 200) {
			var dto = JSON.parse(xhr.responseText);
			var accounts = dto.accounts;
			console.log("Accounts: " + accounts);
			if (accounts.length == 0) {
				$("#reimTableDiv").text("You have no accounts");
				console.log("null");
				console.log("accounts");
			} else {
				console.log("not null");
				console.log(accounts.length);
				populateAccountsTable(accounts);
			}
		}
	}
	xhr.open("GET", "getUserInfo", true);
	xhr.send();
}

function populateAccountsTable(accounts) {
	var html = "<h3>Accounts</h3>";
	console.log("Populating accounts table...");
	// Table head
	html += "<table class='table table-striped'><thead>	" + "<th>ID</th>"
			+ "<th>Date opened</th>" + "<th>Level</th>" + "<th>Type</th>"
			+ "<th>Balance</th><tbody>";

	// Table rows
	for (var i = 0; i < accounts.length; i++) {

		var day = accounts[i].accountOpenedDate;
		html += "<tr>"
		html += "<td>" + accounts[i].accountId + "</td>";
		html += "<td>" + day.month + " " + day.dayOfMonth + ", " + day.year
				+ "</td>";
		html += "<td>" + accounts[i].level + "</td>";
		html += "<td>" + accounts[i].type + "</td>";
		html += "<td>" + accounts[i].balance + "</td>";
		html += "</tr>";
	}

	// Table end
	html += "</tbody></thead></table>";

	$("#reimTableDiv").html(html);
}

// Either log in to a current session or show the login screen
function tryLogin() {

	// TODO redirect to manager page if a manager
	
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {

		if (xhr.readyState == 4) {
			if (xhr.status == 200) {

				var dto = JSON.parse(xhr.responseText);
				var user = dto.user;
				console.log("User: " + user);

				// Open the navbar and show the home page
				loggedIn = true;
				loadDashboard();
			} else if (xhr.status == 418) {
				// Show login page
				loggedIn = false;
				loadLoginView();
			}
		}
	}
	xhr.open("GET", "getUserInfo", true);
	xhr.send();
}


//Validates login and returns a useful error message if there is a mistake
function login() {
	var username = $("#username_input")[0].value;
	var password = $("#password_input")[0].value;

	var dto = [username, password];
	
	dto = JSON.stringify(dto);
	
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// Message arrived
			var response =  xhr.responseText;

			console.log("xhr response arrived in login function in loginMessage.js: " + xhr.responseText);
			if (response == "fail"){
				$("#message").text("Invalid username. Please try again");
			}
			else if(response == "pass"){
				$("#message").text("Invalid password. Please try again");
			}
			else{
				$("#message").text("");
				loggedIn = true;
				console.log(response);
				console.log("calling success function");
				loadDashboard();
				$("#navbarDiv").attr("hidden", false);
			}
			
		}
	}
	
	xhr.open("POST", "login", true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(dto);
}

function updateAccount() {
	var userId = $("#id_text").text();
	var firstName = $("#firstname_edit")[0].value;
	var lastName = $("#lastname_edit")[0].value;
	var email = $("#email_edit")[0].value;
	var username = $("#username_edit")[0].value;
	var password = $("#password_edit")[0].value;
	
	var dto = [userId, firstName, lastName, email, username, password];
	
	dto = JSON.stringify(dto);
	console.log("updateAccount dto: " + dto);
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// Message arrived
			var response =  xhr.responseText;

			console.log("xhr response arrived in updateAccount()");
			if (response == "false"){
				$("#message_edit").text("Information could not be updated");
				$("#message_edit").attr("style", "color:red");
			}
			else {
				setTimeout(loadProfileView(), 0);
				
				$("#message_edit").text("Information updated.");
				$("#message_edit").attr("style", "color:green");
			}
			
		}
	}
	
	xhr.open("POST", "updateProfile", true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(dto);
}
