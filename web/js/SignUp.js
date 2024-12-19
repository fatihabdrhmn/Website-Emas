

document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    
    form.addEventListener("submit", function (e) {
        // Get all the input fields
        const name = document.getElementById("name").value.trim();
        const nik = document.getElementById("nik").value.trim();
        const address = document.getElementById("address").value.trim();
        const phone = document.getElementById("phone").value.trim();
        const dob = document.getElementById("dob").value.trim();
        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value.trim();
        
        let errorMessage = "";

        // Validate each input
        if (name === "") {
            errorMessage += "Name is required.\n";
        }

        if (nik === "" || isNaN(nik)) {
            errorMessage += "NIK is required and should be a number.\n";
        }

        if (address === "") {
            errorMessage += "Address is required.\n";
        }

        const phonePattern = /^[0-9]+$/;
        if (phone === "" || !phonePattern.test(phone)) {
            errorMessage += "Phone number is required and should only contain numbers.\n";
        }


        if (dob === "") {
            errorMessage += "Date of birth is required.\n";
        }

        if (username === "") {
            errorMessage += "Username is required.\n";
        }

        if (password === "" || password.length < 6) {
            errorMessage += "Password is required and should be at least 6 characters long.\n";
        }

        
        if (errorMessage !== "") {
            e.preventDefault(); 
            alert(errorMessage);
        }
    });
});
