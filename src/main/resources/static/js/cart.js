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
        try {
            const response = await fetch('http://localhost:8080/cart', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                }
            });

            const cartContainer = document.querySelector('#cart tbody');
            const cartFooter = document.querySelector('#cart tfoot');
            cartContainer.innerHTML = '';  // Limpiar contenido estático
            cartFooter.innerHTML = '';  // Limpiar el pie de tabla

            if (response.status === 200) {
                const cartItems = await response.json();

                if (cartItems.length === 0) {
                    // No hay productos en el carrito
                    const emptyMessage = document.createElement('tr');
                    emptyMessage.innerHTML = '<td colspan="5" class="text-center"><h3>No hay productos en tu carrito.</h3></td>';
                    cartContainer.appendChild(emptyMessage);
                } else {
                    // Mostrar los productos en el carrito
                    cartItems.forEach(item => {
                        const row = document.createElement('tr');

                        // Asegurarse de que item.price sea un número antes de usar toFixed
                        const price = parseFloat(item.price); // Convertir a número
                        const subtotal = price * item.quantity;

                        if (!isNaN(price)) {
                            row.innerHTML = `
                                <td data-th="Producto">
                                    <div class="row">
                                        <div class="col-sm-2"><img src="${item.imageUrl}" alt="${item.name}" class="img-fluid"></div>
                                        <div class="col-sm-10">
                                            <h4 class="nomargin">${item.name}</h4>
                                            <p>${item.description}</p>
                                        </div>
                                    </div>
                                </td>
                                <td data-th="Precio">€${price.toFixed(2)}</td>
                                <td data-th="Cantidad">
                                    <input type="number" class="form-control text-center" value="${item.quantity}" min="1">
                                </td>
                                <td data-th="Subtotal" class="text-center">€${subtotal.toFixed(2)}</td>
                                <td class="actions" data-th="">
                                    <button class="btn btn-info btn-sm btn-info"><i class="fas fa-sync-alt"></i></button>
                                    <button class="btn btn-danger btn-sm bg-danger"><i class="fas fa-trash-alt"></i></button>
                                </td>
                            `;
                        } else {
                            console.error('Invalid price value:', item.price);
                        }

                        cartContainer.appendChild(row);
                    });

                    // Calcular el total del carrito
                    const total = cartItems.reduce((sum, item) => {
                        const price = parseFloat(item.price); // Asegurarse de que item.price sea un número
                        return !isNaN(price) ? sum + price * item.quantity : sum;
                    }, 0);

                    cartFooter.innerHTML = `
                        <tr>
                            <td colspan="5" class="text-center"><strong>Total €${total.toFixed(2)}</strong></td>
                        </tr>
                        <tr>
                            <td><a href="#" class="btn btn-warning"><i class="fa fa-angle-left"></i> Continuar comprando</a></td>
                            <td colspan="2"></td>
                            <td class="text-center"><strong>Total €${total.toFixed(2)}</strong></td>
                            <td><a href="#" class="btn btn-success btn-block">Pagar <i class="fa fa-angle-right"></i></a></td>
                        </tr>
                    `;
                }
            } else if (response.status === 404) {
                const emptyMessage = document.createElement('tr');
                emptyMessage.innerHTML = '<td colspan="5" class="text-center"><h3>No hay productos en tu carrito.</h3></td>';
                cartContainer.appendChild(emptyMessage);
            } else {
                alert('Error: ' + response.statusText);
            }
        } catch (error) {
            console.error('Error al cargar el carrito:', error);
        }
    }

    // Llamar a la función para mostrar el carrito
    viewCart();
}
