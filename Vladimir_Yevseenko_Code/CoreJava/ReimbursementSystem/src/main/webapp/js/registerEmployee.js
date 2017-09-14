$(function() {
	$('#registerEmployee').click(function() {
		
		$('.errorMsg').each(function() {
			$(this).text('');
		});
		
		$('.inField').each(function() {
			$(this).css('border-color', 'rgba(0,0,0,0.3)');
		});
		
		$('#registerMsg').text('');
		
		if (!$('#firstNameIn').val()) {
			$('#firstNameIn').css('border-color', 'red');
			$('#firstNameMsg').text('Pleae enter a first name');
			setTimeout(function() {
				$('#firstNameIn').css('border-color', 'rgba(0,0,0,0.3)');
				$('#firstNameMsg').text('');
			}, 3000);
			return;
		} else if (!$('#lastNameIn').val()) {
			$('#lastNameIn').css('border-color', 'red');
			$('#lastNameMsg').text('Please enter a last name');
			setTimeout(function() {
				$('#lastNameIn').css('border-color', 'rgba(0,0,0,0.3)');
				$('#lastNameMsg').text('');
			}, 3000);
			return;
		} else if (!$('#emailIn').val()) {
			$('#emailIn').css('border-color', 'red');
			$('#emailMsg').text('Please enter an email');
			setTimeout(function() {
				$('#emailIn').css('border-color', 'rgba(0,0,0,0.3)');
				$('#emailMsg').text('');
			}, 3000);
			return;
		} else if (!validateEmail($('#emailIn').val())) {
			$('#emailIn').css('border-color', 'red');
			$('#emailMsg').text('Please enter a valid email');
			setTimeout(function() {
				$('#emailIn').css('border-color', 'rgba(0,0,0,0.3)');
				$('#emailMsg').text('');
			}, 3000);
			return;
		}
		
		$.post('registerServlet',
				{ firstName: $('#firstNameIn').val(), lastName: $('#lastNameIn').val(),
				  email: $('#emailIn').val(), password: "password", isManager: "false" },
				  function(response) {
					  if (response.success) {
						  $('#registerMsg').text('Registration successful');
						  $('.inField').each(function() {
							 $(this).val(''); 
						  });
						  setTimeout(function() {
							  $('#registerMsg').text('');
						  }, 3000);
					  } else {
						  $('#emailIn').css('border-color', 'red');
						  $('#emailMsg').text('Email already in use');
						  setTimeout(function() {
							  $('#emailMsg').css('border-color', 'rgba(0,0,0,0.3)');
							  $('#emailMsg').text('');
						  }, 3000);
					  }
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
	
	$('#allReimbursementsBtn').click(function() {
		window.location.replace('allReimbursements.html')
	});
	
	$('#accInfoBtn').click(function() {
		window.location.replace('accInfo.html');
	});
	
	$('#registerEmployeeBtn').click(function() {
		window.location.replace('registerEmployee.html');
	});
	
	$('#viewEmployeesBtn').click(function() {
		window.location.replace('employees.html');
	});
});


function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}