document.getElementById('loginForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // Previene la recarga de la página

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const response = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    });

    const result = await response.json();

    if (result.token) {
        // Guardar el token en localStorage
        localStorage.setItem('authToken', result.token);

        // Redirigir a la página del carrito
        window.location.href = 'cart.html';
    } else {
        // En caso de error en el login, mostrar mensaje
        alert('Login failed: ' + result.message);
    }
});




document.getElementById('continueButton').addEventListener('click', function() {
      var additionalFields = document.getElementById('additionalFields');
      this.classList.add('hidden');

      // Utiliza jQuery para la transición suave
      $(additionalFields).slideDown(500, function() {
        additionalFields.classList.add('visible'); // Asegúrate de que los campos sean visibles después de la animación
      });
    });