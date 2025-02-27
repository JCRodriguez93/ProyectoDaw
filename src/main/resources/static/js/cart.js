// Recuperar el token desde localStorage
const authToken = localStorage.getItem('authToken');

// Al cargar la página, aseguramos que el contenido se oculte temporalmente
document.body.classList.remove('visible'); 

// Verificar si el token está disponible
if (!authToken) {
    // Redirigir a login si no hay token
    window.location.href = 'login.html';
} else {
    // Si hay un token, hacemos visible el contenido de la página
    document.body.classList.add('visible');

    // El token está presente, ahora puedes hacer peticiones con él
    async function viewCart() {
        const response = await fetch('http://localhost:8080/cart', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${authToken}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.status === 200) {
            const cartItems = await response.json();
            // Aquí puedes procesar y mostrar los items en el carrito
            console.log(cartItems);
        } else if (response.status === 404) {
            alert('Cart is empty or not found');
        } else {
            alert('Error: ' + response.statusText);
        }
    }

    // Llamar a la función para mostrar el carrito
    viewCart();
}
