// Asegurarse de que el botón de logout solo esté visible si el usuario está autenticado
window.onload = function() {
    // Verificar si el token existe en localStorage
    const authToken = localStorage.getItem('authToken');
    const userId = localStorage.getItem('userId');

    if (authToken && userId) {
        // Si el usuario está autenticado, mostrar el botón de logout
        document.getElementById('logoutButton').style.display = 'block';
    } else {
        // Si no está autenticado, ocultar el botón de logout
        document.getElementById('logoutButton').style.display = 'none';
    }
};

// Manejar la acción de logout
document.getElementById('logoutButton').addEventListener('click', function(event) {
    event.preventDefault(); // Prevenir el comportamiento por defecto del enlace

    // Eliminar los elementos de localStorage
    localStorage.removeItem('authToken');
    localStorage.removeItem('userId');

    // Redirigir al usuario a la página de login
    window.location.href = 'login.html';
});
