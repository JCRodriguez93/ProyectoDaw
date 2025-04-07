/**document.addEventListener("DOMContentLoaded", function() {
  const urlParams = new URLSearchParams(window.location.search);
  const subcategoryId = urlParams.get('subcategory');
  const subcategoryName = urlParams.get('subcategoryName');
  const rangeInput = document.getElementById("customRange3");
  const rangeValue = document.getElementById("rangeValue");

  let allProducts = [];

  // Cargar productos de la subcategoría indicada en la URL
  if (subcategoryId) {
    fetch("http://localhost:8080/Products")
      .then(response => response.json())
      .then(data => {
        allProducts = data.products.filter(producto =>
          Number(producto.idSubcategory) === Number(subcategoryId)
        );
        mostrarProductos(allProducts, subcategoryName);
      })
      .catch(error => console.error("Error al obtener productos:", error));
  }

  // Actualiza el texto del slider y aplica el filtro al moverlo
  rangeInput.addEventListener("input", function() {
    const valorSeleccionado = rangeInput.value;
    rangeValue.textContent = `${valorSeleccionado}€`;
    filtrarProductosPorPrecio(valorSeleccionado);
  });

  function filtrarProductosPorPrecio(precioMaximo) {

    if (Number(precioMaximo) === 0) {
      // Si el slider es 0, mostramos todos los productos
      mostrarProductos(allProducts, subcategoryName);
    } else {
      // Filtrar solo los productos cuyo precio sea EXACTAMENTE igual al valor seleccionado
      const productosFiltrados = allProducts.filter(producto =>
        Number(producto.price) <= Number(precioMaximo)
      );
      console.log("Productos filtrados:", productosFiltrados);
      mostrarProductos(productosFiltrados, subcategoryName);
    }
  }

  function mostrarProductos(productos, subcategoryName) {
    const productsContainer = document.getElementById("productsContainer");
    productsContainer.innerHTML = ""; // Limpiar el contenedor

    if (productos.length === 0) {
      productsContainer.innerHTML = `<h4>No hay productos a ese precio</h4>`;
    } else {
      productos.forEach(producto => {
        const productId = producto.idProduct;
        if (!productId) {
          console.error("Producto sin ID:", producto);
          return;
        }
        const productElement = document.createElement("div");
        productElement.classList.add("col");
        productElement.innerHTML = `
          <div class="card product-card h-100">
            <a href="product.html?id=${productId}" class="stretched-link"></a>
            <img src="${producto.imageUrl}" class="card-img-top" alt="${producto.name}">
            <div class="card-body">
              <h5 class="card-title">${producto.name}</h5>
              <p class="card-text">${producto.description}</p>
              <p class="card-text"><strong>${producto.price}€</strong></p>
            </div>
          </div>
        `;
        productsContainer.appendChild(productElement);
      });
    }
  }
});

**/