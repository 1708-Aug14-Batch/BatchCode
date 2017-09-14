$(function() {
	$.post('userInfoServlet',
			{},
			function(curUser) {
				if (!curUser.isManager) {
					$('#viewAllReimbursementsBtn').hide();
					$('#registerEmployeeBtn').hide();
					$('#viewEmployees').hide();
				} else {
					$('#viewOwnReimbursementsBtn').hide();
				}
			});
	
	
	$('#changeUserInfoBtn').click(function() {
			$.post('changeUserInfoServlet',
			{ first: $('#firstNameInput').val(), last: $('#lastNameInput').val(),
			  email: $('#emailInput').val(), password: $('#passwordInput').val() },
			  function(response) {
				  if (response.success)
					  $('#message').text('Account info updated');
				  else
					  $('#message').text('Email is already in use');
			  },
			  'JSON');
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
	
	$('#allReimbursementsBtn').click(function() {
		window.location.replace('allReimbursements.html')
	});
	
	$('#accInfoBtn').click(function() {
		window.location.replace('accInfo.html');
	});
	
	$('#registerEmployeeBtn').click(function() {
		window.location.replace('registerEmployee.html');
	});
	
	$('#viewEmployees').click(function() {
		window.location.replace('employees.html');
	});
});