fetch('header.html')
  .then(response => response.text())
  .then(data => {
    document.getElementById('header-container').innerHTML = data;

    // Añadir los scripts manualmente después de cargar el header
    const script1 = document.createElement('script');
    script1.src = 'js/login/login-request.js';
    document.body.appendChild(script1);



    // Inicializar eventos
    initLogoutButton();
  });

fetch('footer.html')
  .then(response => response.text())
  .then(data => {
    document.getElementById('footer-div').innerHTML = data;
  });

function initLogoutButton() {
    const logoutButton = document.getElementById('logoutButton');
    const welcomeMessage = document.getElementById('welcome-message');

    // Evento de logout
    if (logoutButton) {
        logoutButton.addEventListener('click', function() {
            console.log('Logout button clicked.');
            const authToken = localStorage.getItem('authToken');
            handleLogout(authToken);
        });
    }
}

// Función para manejar el logout
async function handleLogout(authToken) {
    try {
        console.log('Handling logout. Auth token:', authToken);
        // Si existe un token, hacemos la petición de logout
        if (authToken) {
            const response = await fetch('http://localhost:8080/auth/logout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authToken}`
                }
            });

            const data = await response.json();
            console.log('Logout response data:', data);

            console.log(data.message);
            // Verificamos que el logout fue exitoso
            if (data.message === "Successful logout") {
                // Limpiar el token del localStorage
                console.log('Successful logout. Clearing local storage.');
                localStorage.removeItem('authToken');
                localStorage.removeItem('userId');
                // Actualizar la UI
                document.getElementById("welcome-message").innerText = 'Bienvenido,';
                window.location.href = 'index.html';  // Redirige al inicio
            } else {
                console.error("Error en logout:", data.message);
            }
        } else {
            console.warn('No auth token found.');
        }
    } catch (error) {
        console.error("Error al hacer logout:", error);
    }
}
