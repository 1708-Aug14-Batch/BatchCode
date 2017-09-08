$(function() {
	$('#toManagerRegistration').click(function() {
		console.log('Manager Registration clicked');
		window.location.replace('register.html');
	});
});



$(function() {
	$('#loginSubmit').click(function() {
		console.log('Login clicked');
		$.post('http://localhost:8181/ReimbursementSystem/loginServlet',
				{ email: $('#emailIn').val(), password: $('#passwordIn').val() },
				function(data) {
					if (data.success)
						window.location.href = 'loggedIn.html';
					else
						$('#message').text('Incorrect email/password');
				},
				'JSON');
	});
});