function toggleEdit() {
    const inputs = document.querySelectorAll('#user-profile-form input');
    const isDisabled = inputs[0].disabled;

    inputs.forEach(input => input.disabled = !isDisabled);
    
    // Toggle Save button
    document.getElementById('save-button').disabled = !isDisabled;

    // Change Edit button text
    document.getElementById('edit-button').innerText = isDisabled ? 'Cancel' : 'Edit';
}

document.getElementById('toggle-password').addEventListener('click', function () {
const passwordInput = document.getElementById('password');
const icon = document.getElementById('toggle-password');

// Toggle between 'password' and 'text'
if (passwordInput.type === 'password') {
passwordInput.type = 'text';
// Ubah icon ke show
icon.classList.remove('bx-hide');
icon.classList.add('bx-show');
} else {
passwordInput.type = 'password';
// Ubah icon ke hide
icon.classList.remove('bx-show');
icon.classList.add('bx-hide');
}
});