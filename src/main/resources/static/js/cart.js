// Recuperar el token desde localStorage
const authToken = localStorage.getItem('authToken');

// Al cargar la página, aseguramos que el contenido se oculte temporalmente
document.body.classList.remove('visible');

// Verificar si el token está disponible
if (!authToken) {
    window.location.href = 'login.html';
} else {
    document.body.classList.add('visible');

    // Función para obtener y mostrar el carrito
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
            cartContainer.innerHTML = '';
            cartFooter.innerHTML = '';

            if (response.ok) {
                const cartItems = await response.json();

                if (cartItems.length === 0) {
                    cartContainer.innerHTML = '<tr><td colspan="5" class="text-center"><h3>No hay productos en tu carrito.</h3></td></tr>';
                } else {
                    let total = 0;

                    for (const item of cartItems) {
                        const product_id = item.product_id; // Aquí ya tienes el ID correcto
                        console.log(product_id); // Mostrará el ID de cada producto en el carrito

                        // Función para obtener los detalles del producto (como la imageUrl y description)
                        async function getProductDetails(product_id) {
                            try {
                                const response = await fetch(`http://localhost:8080/Products/${product_id}`, {
                                    method: 'GET',
                                    headers: {
                                        'Authorization': `Bearer ${authToken}`,
                                        'Content-Type': 'application/json'
                                    }
                                });

                                if (response.ok) {
                                    const product = await response.json();
                                    return product; // Devuelve el producto encontrado
                                } else {
                                    console.error('No se pudo obtener el producto:', response.statusText);
                                }
                            } catch (error) {
                                console.error('Error al obtener el producto:', error);
                            }
                        }

                        // Obtener los detalles del producto
                        const product = await getProductDetails(product_id);

                        // Si el producto es encontrado, crear la fila con la imagen y la descripción
                        if (product) {
                            const row = document.createElement('tr');
                            const price = parseFloat(item.price);
                            const subtotal = price * item.quantity;
                            total += subtotal;

                            row.innerHTML = `
                                <td data-th="Producto">
                                    <div class="row">
                                        <div class="col-sm-2"><img src="${product.imageUrl}" alt="${product.product_name}" class="img-fluid"></div>
                                        <div class="col-sm-10">
                                            <h4 class="nomargin">${item.product_name}</h4>
                                            <p>${product.description}</p> <!-- Aquí se muestra la descripción del producto -->
                                        </div>
                                    </div>
                                </td>
                                <td data-th="Precio">€${price.toFixed(2)}</td>
                                <td data-th="Cantidad">
                                    <input type="number" class="form-control text-center quantity" value="${item.quantity}" min="1" data-id="${item.id}">
                                </td>
                                <td data-th="Subtotal" class="text-center subtotal">€${subtotal.toFixed(2)}</td>
                                <td class="actions">
                                    <button class="btn btn-info btn-sm update" data-id="${item.id}"><i class="fas fa-sync-alt"></i></button>
                                    <button class="btn btn-danger btn-sm delete" data-id="${item.id}"><i class="fas fa-trash-alt"></i></button>
                                </td>
                            `;

                            cartContainer.appendChild(row);
                        }
                    }

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

                    addEventListeners();
                }
            } else {
                console.error('Error al obtener el carrito:', response.statusText);
                alert('Hubo un problema al cargar el carrito.');
            }
        } catch (error) {
            console.error('Error de red al obtener el carrito:', error);
            alert('No se pudo conectar con el servidor.');
        }
    }

  async function updateCartItem(productId, quantity) {
      // Verificar que los parámetros sean válidos
      if (!productId || isNaN(quantity) || quantity <= 0) {
          alert('Los valores del producto o cantidad no son válidos.');
          return;
      }

      try {
          const response = await fetch('http://localhost:8080/cart', {
              method: 'POST',
              headers: {
                  'Authorization': `Bearer ${authToken}`,
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify({
                  product_id: productId, // ID del producto
                  quantity: quantity,    // Nueva cantidad
                  action: 'modify'       // Acción de modificar
              })
          });

          if (response.ok) {
              viewCart(); // Recargar el carrito
          } else {
              const errorMessage = await response.json();
              alert(errorMessage.message || 'No se pudo actualizar la cantidad.');
          }
      } catch (error) {
          console.error('Error al actualizar el producto:', error);
          alert('Hubo un error al actualizar el carrito.');
      }
  }


  async function deleteCartItem(productId) {
      // Verificar que el productId es válido
      if (!productId) {
          alert('El ID del producto no es válido.');
          return;
      }

      try {
          const response = await fetch('http://localhost:8080/cart', {
              method: 'POST',
              headers: {
                  'Authorization': `Bearer ${authToken}`,
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify({
                  product_id: productId, // ID del producto
                  action: 'remove'       // Acción de eliminar
              })
          });

          if (response.ok) {
              viewCart(); // Recargar el carrito
          } else {
              const errorMessage = await response.json();
              alert(errorMessage.message || 'No se pudo eliminar el producto.');
          }
      } catch (error) {
          console.error('Error al eliminar el producto:', error);
          alert('Hubo un error al eliminar el producto.');
      }
  }


  // Añadir eventos a los botones de actualizar y eliminar
  function addEventListeners() {
      document.querySelectorAll('.update').forEach(button => {
          button.addEventListener('click', () => {
              const productId = button.getAttribute('data-id');
              const quantityInput = document.querySelector(`input[data-id="${productId}"]`);
              const newQuantity = parseInt(quantityInput.value, 10);

              // Verificar que la cantidad es válida
              if (newQuantity > 0) {
                  updateCartItem(productId, newQuantity);
              } else {
                  alert('Por favor, ingrese una cantidad válida.');
              }
          });
      });

      document.querySelectorAll('.delete').forEach(button => {
          button.addEventListener('click', () => {
              const productId = button.getAttribute('data-id');
              deleteCartItem(productId);
          });
      });
  }




    // Añadir eventos a los botones de actualizar y eliminar
    function addEventListeners() {
        document.querySelectorAll('.update').forEach(button => {
            button.addEventListener('click', () => {
                const productId = button.getAttribute('data-id');
                const quantityInput = document.querySelector(`input[data-id="${productId}"]`);
                const newQuantity = parseInt(quantityInput.value, 10);
                if (newQuantity > 0) {
                    updateCartItem(productId, newQuantity);
                }
            });
        });

        document.querySelectorAll('.delete').forEach(button => {
            button.addEventListener('click', () => {
                const productId = button.getAttribute('data-id');
                deleteCartItem(productId);
            });
        });
    }

    // Llamar a la función para mostrar el carrito
    viewCart();
}
