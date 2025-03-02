(function() {
    document.addEventListener('DOMContentLoaded', function () {
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
    });
logoutButton.addEventListener('click', function() {
    const authToken = localStorage.getItem('authToken');
    console.log('Token obtenido antes de logout:', authToken);
    handleLogout(authToken);
});
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
                    alert('Revisar consola antes de redirigir.');

                    console.log('Después de borrar:', localStorage.getItem('authToken'));

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
})();
