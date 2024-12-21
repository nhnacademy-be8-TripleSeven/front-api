document.addEventListener('DOMContentLoaded', () => {
    const togglePassword = document.getElementById('toggle-password');
    const toggleConfirmPassword = document.getElementById('toggle-confirm-password');
    const passwordField = document.getElementById('password');
    const confirmPasswordField = document.getElementById('confirm-password');

    const toggleVisibility = (field, icon) => {
        const isPassword = field.type === 'password';
        field.type = isPassword ? 'text' : 'password';
        icon.src = isPassword ? '/image/icons/Eye.png' : '/image/icons/EyeSlash.png';
    };

    togglePassword.addEventListener('click', () =>
        toggleVisibility(passwordField, togglePassword.querySelector('img'))
    );

    toggleConfirmPassword.addEventListener('click', () =>
        toggleVisibility(confirmPasswordField, toggleConfirmPassword.querySelector('img'))
    );
});