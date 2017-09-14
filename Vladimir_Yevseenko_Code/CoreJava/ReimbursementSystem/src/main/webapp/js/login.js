$(function() {
	$('#toManagerRegistration').click(function() {
		window.location.replace('register.html');
	});

	$('#loginSubmit').click(function() {
		
		$('.errorMsg').each(function() {
			$(this).text('');
		});
		
		$('.inField').each(function() {
			$(this).css('border-color', 'rgba(0,0,0,0.3)');
		});
		
		if (!$('#emailIn').val()) {
			$('#emailIn').css('border-color', 'red');
			$('#emailMsg').text('Please enter an email');
			setTimeout(function() {
				$('#emailIn').css('border-color', 'rgba(0,0,0,0.3)');
				$('#emailMsg').text('');
			}, 3000);
			return;
		} else if (!$('#passwordIn').val()) {
			$('#passwordIn').css('border-color', 'red');
			$('#passwordMsg').text('Please enter a password');
			setTimeout(function() {
				$('#passwordIn').css('border-color', 'rgba(0,0,0,0.3)');
				$('#passwordMsg').text('');
			}, 3000);
			return;
		}
		
			
		$.post('loginServlet',
				{ email: $('#emailIn').val(), password: $('#passwordIn').val() },
				function(response) {
					console.log(response.success);
					switch (response.success) {
						case "bad email":
							{
								$('#emailIn').css('border-color', 'red');
								$('#emailMsg').text('Email does not exist');
								setTimeout(function() {
									$('#emailIn').css('border-color', 'rgba(0,0,0,0.3)');
									$('#emailMsg').text('');
								}, 3000);
							} 
							break;
						case "bad password":
							{	
								$('#passwordIn').css('border-color', 'red');
								$('#passwordMsg').text('Incorrect password');
								setTimeout(function() {
									$('#passwordIn').css('border-color', 'rgba(0,0,0,0.3)');
									$('#passwordMsg').text('');
								}, 3000);
							}
							break;
						case "success":
							{
							$.post('userInfoServlet',
									{}, 
									function(curUser) {
										if (curUser.isManager)
											window.location.replace('allReimbursements.html');
										else
											window.location.replace('yourReimbursements.html');
									},
								'JSON');
							}
							break;
					}
				},
				'JSON');
	});
});