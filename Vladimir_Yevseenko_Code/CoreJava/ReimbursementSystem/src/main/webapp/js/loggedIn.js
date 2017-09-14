$(function() {
	$.post('userInfoServlet',
			{},
			function(curUser) {
				console.log(curUser);
				if (curUser.isManager) {
					window.location.replace('allReimbursements.html')
				} else {
					window.location.replace('yourReimbursements.html');
				}
			});
	
	$('#logoutBtn').click(function() {
		$.post('logoutServlet',
				{},
				function(data) {
					window.location.replace('login.html');
				});
	});
	
	$('#viewOwnReimbursementsBtn').click(function() {
		window.location.replace('yourReimbursements.html');
	});
	
	$('#viewAllReimbursementsBtn').click(function() {
		window.location.replace('allReimbursements.html')
	});
	
	$('#updateAccInfoBtn').click(function() {
		window.location.replace('accInfo.html');
	});
	
	$('#registerEmployeeBtn').click(function() {
		window.location.replace('registerEmployee.html');
	});
	
	$('#viewEmployees').click(function() {
		window.location.replace('employees.html');
	});
	
});