// Retrieve token and userId from localStorage
const authToken = localStorage.getItem('authToken');
const userId = localStorage.getItem('userId');

// Hide page content until authentication is confirmed
document.body.classList.remove('visible');

// Redirect to login if token is missing
if (!authToken) {
    window.location.href = 'login.html';
} else {
    document.body.classList.add('visible');

    // Function to fetch and display the cart
    async function viewCart() {
        try {
            const response = await fetch('https://jorgedaw.store/cart', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                }
            });

            const cartContainer = document.querySelector('#cart tbody');
            const cartFooter = document.querySelector('#cart tfoot');
            cartContainer.innerHTML = '';
            cartFooter.innerHTML = '';

            if (response.ok) {
                const cartItems = await response.json();

                if (cartItems.length === 0) {
                    cartContainer.innerHTML = '<tr><td colspan="5" class="text-center"><h3>Your cart is empty.</h3></td></tr>';
                } else {
                    let total = 0;

                    // Store cartItems globally so we can use them when clearing the cart after payment
                    window.cartItems = cartItems;

                    for (const item of cartItems) {
                        const productId = item.product_id;

                        // Function to fetch product details (like imageUrl and description)
                        async function getProductDetails(productId) {
                            try {
                                const response = await fetch(`https://jorgedaw.store/${productId}`, {
                                    method: 'GET',
                                    headers: {
                                        'Authorization': `Bearer ${authToken}`,
                                        'Content-Type': 'application/json'
                                    }
                                });

                                if (response.ok) {
                                    const product = await response.json();
                                    return product;
                                } else {
                                    console.error('Failed to fetch product:', response.statusText);
                                }
                            } catch (error) {
                                console.error('Error fetching product:', error);
                            }
                        }

                        const product = await getProductDetails(productId);

                        if (product) {
                            const row = document.createElement('tr');
                            const price = parseFloat(item.price);
                            const subtotal = price * item.quantity;
                            total += subtotal;

                            row.innerHTML = `
                                <td data-th="Product">
                                    <div class="row">
                                        <div class="col-sm-2"><img src="${product.imageUrl}" alt="${product.product_name}" class="img-fluid"></div>
                                        <div class="col-sm-10">
                                            <h4 class="nomargin">${item.product_name}</h4>
                                            <p>${product.description}</p>
                                        </div>
                                    </div>
                                </td>
                                <td data-th="Price">€${price.toFixed(2)}</td>
                                <td data-th="Quantity">
                                    <input type="number" class="form-control text-center quantity" value="${item.quantity}" min="1" data-id="${item.product_id}">
                                </td>
                                <td data-th="Subtotal" class="text-center subtotal">€${subtotal.toFixed(2)}</td>
                                <td class="actions">
                                    <button class="btn btn-info btn-sm update" data-id="${item.product_id}"><i class="fas fa-sync-alt"></i></button>
                                    <button class="btn btn-danger btn-sm delete" data-id="${item.product_id}"><i class="fas fa-trash-alt"></i></button>
                                </td>
                            `;
                            cartContainer.appendChild(row);
                        }
                    }

                    window.lastCartTotal = total;
                    window.lastCartQuantity = cartItems.reduce((acc, item) => acc + item.quantity, 0);

                    cartFooter.innerHTML = `

                       <tr>
                           <td>
                               <a href="https://jorgedaw.store/catalog.html" class="btn btn-warning">
                                   <i class="fa fa-angle-left"></i> Continuar comprando
                               </a>
                           </td>
                           <td colspan="2"></td>
                           <td class="text-center"><strong>Total €${total.toFixed(2)}</strong></td>
                           <td>
                               <a href="#" id="pay-button" class="btn btn-success btn-block">
                                   Pagar <i class="fa fa-angle-right"></i>
                               </a>
                           </td>
                       </tr>
                   `;

                    addEventListeners();
                }
            } else if (response.status === 404) {
                cartContainer.innerHTML = '<tr><td colspan="5" class="text-center"><h3>Sin productos en el carrito.</h3></td></tr>';
                cartFooter.innerHTML = '';
            } else {
                alert('There was a problem loading the cart.');
            }
        } catch (error) {
            alert('Could not connect to the server.');
        }
    }

    // Function to update an item in the cart
    async function updateCartItem(productId, quantity) {
        if (!productId || isNaN(quantity) || quantity <= 0) {
            alert('Invalid product or quantity.');
            return;
        }

        try {
            const response = await fetch('https://jorgedaw.store/cart', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    product_id: productId,
                    quantity: quantity,
                    action: 'modify'
                })
            });

            if (response.ok) {
                viewCart();
            } else {
                alert('Failed to update the quantity.');
            }
        } catch (error) {
            alert('There was an error updating the cart.');
        }
    }

    // Function to delete an item from the cart (with confirmation)
    async function deleteCartItem(productId, quantity) {
        if (!productId) {
            alert('Invalid product ID.');
            return;
        }

        Swal.fire({
            title: "¿Estás seguro?",
            text: "Esta acción no puede deshacerse",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Si, borrar producto",
            cancelButtonText: "Cancelar"
        }).then(async (result) => {
            if (result.isConfirmed) {
                try {
                    const response = await fetch('https://jorgedaw.store/cart', {
                        method: 'POST',
                        headers: {
                            'Authorization': `Bearer ${authToken}`,
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            product_id: productId,
                            quantity: quantity,
                            action: 'remove'
                        })
                    });

                    if (response.ok) {
                        Swal.fire({
                            title: "Borrado",
                            text: "El producto ha sido eliminado.",
                            icon: "success"
                        });
                        viewCart();
                    } else {
                        Swal.fire({
                            title: "Error",
                            text: "Fallo al eliminar el producto.",
                            icon: "error"
                        });
                    }
                } catch (error) {
                    Swal.fire({
                        title: "Error",
                        text: "Hubo un error al eliminar el producto.",
                        icon: "error"
                    });
                }
            }
        });
    }

    // Function to add event listeners to update and delete buttons
    function addEventListeners() {
        document.querySelectorAll('.update').forEach(button => {
            button.addEventListener('click', () => {
                const productId = button.getAttribute('data-id');
                const quantityInput = document.querySelector(`input[data-id="${productId}"]`);
                const newQuantity = parseInt(quantityInput.value, 10);

                if (newQuantity > 0) {
                    updateCartItem(productId, newQuantity);
                } else {
                    alert('Please enter a valid quantity.');
                }
            });
        });

        document.querySelectorAll('.delete').forEach(button => {
            button.addEventListener('click', () => {
                const productId = button.getAttribute('data-id');
                const quantityInput = document.querySelector(`input[data-id="${productId}"]`);
                const quantity = quantityInput ? parseInt(quantityInput.value, 10) : 0;
                deleteCartItem(productId, quantity || 0);
            });
        });

        const payButton = document.getElementById('pay-button');
        if (payButton) {
            payButton.addEventListener('click', (e) => {
                e.preventDefault();
                payOrder();
            });
        }
    }

    // Function to remove a cart item without confirmation (used when clearing the cart)
    async function removeCartItemWithoutConfirm(productId, quantity) {
        try {
            const response = await fetch('https://jorgedaw.store/cart', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    product_id: productId,
                    quantity: quantity,
                    action: 'remove'
                })
            });
            if (!response.ok) {
                console.error(`Failed to remove product ${productId}`);
            }
        } catch (error) {
            console.error(`Error removing product ${productId}:`, error);
        }
    }

    // Function to clear the entire cart by removing each item individually
    async function clearCart() {
        try {
            // Fetch current cart items
            const response = await fetch('https://jorgedaw.store/cart', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                }
            });
            if (response.ok) {
                const cartItems = await response.json();
                // Remove each item without confirmation
                for (const item of cartItems) {
                    await removeCartItemWithoutConfirm(item.product_id, item.quantity);
                }
                viewCart(); // Refresh cart view
            }
        } catch (error) {
            console.error('Error clearing cart:', error);
        }
    }

    // Function to process payment
    async function payOrder() {
        const totalQuantity = window.lastCartQuantity;
        const totalPrice = window.lastCartTotal;
        const date = new Date().toISOString();

        const orderData = {
            idUser: userId,
            totalQuantity: totalQuantity,
            totalPrice: totalPrice,
            date: date,
            orderStatus: 'PAGADO'
        };

        const token = localStorage.getItem('authToken');

        try {
            const response = await fetch('https://jorgedaw.store/Orders', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(orderData)
            });

            if (response.ok) {
                Swal.fire({
                    icon: 'success',
                    title: '¡Pedido completado!',
                    text: 'Tu pedido se ha realizado con éxito.',
                    confirmButtonText: 'Aceptar'
                }).then(() => {
                    clearCart(); // Vaciar el carrito después de completar el pedido
                });

            } else {
                const errorData = await response.text();
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: `There was a problem creating the order: ${errorData || 'Unknown error'}`
                });
            }
        } catch (error) {
            Swal.fire({
                icon: 'error',
                title: 'Connection Error',
                text: 'Could not connect to the server. Please try again later.'
            });
            console.error('Connection error:', error);
        }
    }

    // Call viewCart to display the cart on page load
    viewCart();
}
