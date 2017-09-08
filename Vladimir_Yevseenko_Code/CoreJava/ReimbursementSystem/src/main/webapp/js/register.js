$(function() {
	$('#backToLogin').click(function() {
		console.log('Back To Login clicked');
		window.location.replace('login.html');
	});
});


$(function() {
	$('#registerSubmit').click(function() {
		console.log('Register clicked');
		$.post('http://localhost:8181/ReimbursementSystem/registerServlet',
				{ firstName: $('#firstNameIn').val(), lastName: $('#lastNameIn').val(),
				  email: $('#emailIn').val(), password: $('#passwordIn').val() },
				function(data) {
					if (data.success)
						$('#message').text('Registration successful');
					else
						$('#message').text('Email already in use');
				},
				'JSON');
	});
});