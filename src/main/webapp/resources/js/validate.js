function validateRegistrationUser() {
    let firstName = document.getElementById("FirstName").value;
    let lastName = document.getElementById("LastName").value;
    let email = document.getElementById("Email").value;
    let password = document.getElementById("Password").value;
    let confirmPassword = document.getElementById("ConfirmPassword").value;

    let regexp = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

    if (firstName === "" || firstName.length < 1) {
        swal("Wrong!", "Please enter your 'firstname', length must be over 1 symbol");
        return false;
    }
    if (lastName === "" || lastName.length < 1) {
        swal("Wrong!", "Please enter your 'lastname', length must be over 1 symbol");
        return false;
    }
    if (!regexp.test(String(email))) {
        swal("Wrong!", "Invalid form email");
        return false;
    }
    if (password === "" || password.length < 8) {
        swal("Wrong!", "Please enter your 'password', length must be over 8 symbol");
        return false;
    }
    if (confirmPassword === "" || confirmPassword.length < 8) {
        swal("Wrong!", "Please enter your 'confirm password', length must be over 8 symbol");
        return false;
    }
    if (password !== confirmPassword) {
        swal("Wrong", "Password doesn't match");
        return false;
    }
}