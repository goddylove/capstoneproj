// custom.js
$(document).ready(function() {
    // Show the error alert when the form is submitted with errors
    $('.signup-container form').submit(function(event) {
        var hasErrors = false;
        $('.signup-container .form-group').each(function() {
            if ($(this).hasClass('has-error')) {
                hasErrors = true;
                return false; // Stop the loop if we find any errors
            }
        });

        if (hasErrors || !$('#terms').is(':checked')) {
            $('.error-alert').removeClass('d-none');
            $('.success-alert').addClass('d-none');
            event.preventDefault(); // Prevent form submission
        } else {
            $('.error-alert').addClass('d-none');
            $('.success-alert').removeClass('d-none');
        }
    });
});

