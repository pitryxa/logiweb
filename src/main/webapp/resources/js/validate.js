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

function validateDriver() {
    let personalNumber = document.getElementById("personalNumber").value;

    let regexpPersNum = /^\d{1,8}$/;

    if (!regexpPersNum.test(String(personalNumber))) {
        swal("Wrong!", "Personal number must contain from 1 to 8 digits");
        return false;
    }
}

function validateTruck() {
    let regNumber = document.getElementById("regNumber").value;

    let regexpRegNumber = /^[A-Za-z]{2}\d{5}$/;

    if (!regexpRegNumber.test(String(regNumber))) {
        swal("Wrong!", "Registration number must contain 2 latin letters and 5 digits");
        return false;
    }
}

function validateCargo() {
    let cityFrom = document.getElementById("city-from").value;
    let cityTo = document.getElementById("city-to").value;

    if (cityFrom === cityTo) {
        swal("Wrong!", "The city of departure and the city of delivery should not be the same");
        return false;
    }
}

function validateOrder(shift) {
    if ($('#drivers option:selected').length != shift) {
        swal("Wrong!", "Please select " + shift + " drivers");
        return false;
    }
}

function validateDistance() {
    let cityFrom = document.getElementById("city-from").value;
    let cityTo = document.getElementById("city-to").value;

    if (cityFrom === cityTo) {
        swal("Wrong!", "The city of departure and the city of delivery should not be the same");
        return false;
    }
}