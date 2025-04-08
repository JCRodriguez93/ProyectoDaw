/* ===== CARGA DE DETALLES DEL PRODUCTO (PRODUCT.HTML) ===== */
async function loadProductDetails() {
  const urlParams = new URLSearchParams(window.location.search);
  const idProduct = urlParams.get('id');

  const productDetails = document.getElementById('product-details');
  if (!productDetails) {
    console.error("El contenedor de detalles del producto no se encuentra en el DOM.");
    return;
  }
  try {
    const productResponse = await fetch(`http://localhost:8080/Products/${idProduct}`);
    if (!productResponse.ok) throw new Error("Error en la respuesta de la API del producto");
    const product = await productResponse.json();

    // Obtener los datos de la subcategoría del producto
    const idSubcategory = product.idSubcategory;
    const subcatResponse = await fetch(`http://localhost:8080/Subcategory/${idSubcategory}`);
    if (!subcatResponse.ok) throw new Error("Error en la respuesta de la API de subcategoría");
    const subcategory = await subcatResponse.json();
    const subcategoryName = subcategory.name;
    const categoryId = subcategory.id_category;

    // Obtener los datos de la categoría
    const catResponse = await fetch(`http://localhost:8080/Category/${categoryId}`);
    if (!catResponse.ok) throw new Error("Error en la respuesta de la API de categoría");
    const category = await catResponse.json();
    const categoryName = category.name;

    // Generar el breadcrumb dinámico
    const breadcrumbHTML = `
      <nav class="d-flex">
        <h6 class="mb-0">
          <a href="index.html" class="text-white-50">Inicio</a>
          <span class="text-white-50 mx-2"> > </span>
          <a href="catalog.html?category=${categoryId}&categoryName=${encodeURIComponent(categoryName)}" class="text-white-50">${categoryName}</a>
          <span class="text-white-50 mx-2"> > </span>
          <a href="catalog.html?subcategory=${idSubcategory}&subcategoryName=${encodeURIComponent(subcategoryName)}" class="text-white-50">${subcategoryName}</a>
          <span class="text-white-50 mx-2"> > </span>
          <span class="text-white"><u>${product.name}</u></span>
        </h6>
      </nav>
    `;

    productDetails.innerHTML = `
      <div class="bg-danger" style="margin-bottom: 20px;">
        <div class="container py-4">
          ${breadcrumbHTML}
        </div>
      </div>

      <div class="row">
        <div class="col-lg-6">
          <div class="border rounded-4 mb-3 d-flex justify-content-center">
            <a data-fslightbox="mygallery" class="rounded-4" target="_blank" data-type="image" href="${product.imageUrl}">
              <img style="max-width: 100%; max-height: 100vh; margin: auto;" class="rounded-4 fit zoom" src="${product.imageUrl}" alt="${product.name}">
            </a>
          </div>
        </div>
        <div class="col-lg-6">
          <h4 class="title">${product.name}</h4>
          <p class="product-price">${product.price}€</p>
          <p class="product-description">${product.description}</p>
          <div class="row">
            <dl class="row">
              <dt class="col-3">Type:</dt><dd class="col-9">${product.type}</dd>
              <dt class="col-3">Color:</dt><dd class="col-9">${product.color}</dd>
              <dt class="col-3">Material:</dt><dd class="col-9">${product.material}</dd>
              <dt class="col-3">Brand:</dt><dd class="col-9">${product.brand}</dd>
            </dl>
          </div>
          <div class="col-md-4 col-6 mb-3">
            <label class="mb-2 d-block">Quantity</label>
            <div class="input-group mb-3" style="width: 170px;">
              <button class="btn btn-white border border-secondary px-3 minus-button" type="button" id="button-minus" data-mdb-ripple-color="dark">
                &minus;
              </button>
              <input type="text" class="form-control text-center border border-secondary" id="quantity" value="1" aria-label="Example text with button addon" aria-describedby="button-minus">
              <button class="btn btn-white border border-secondary px-3 plus-button" type="button" id="button-plus" data-mdb-ripple-color="dark">
                &plus;
              </button>
            </div>
          </div>
          <button class="btn btn-warning shadow-0" onclick="buyProduct()">Comprar</button>
          <a href="#" class="btn btn-primary shadow-0" id="add-to-cart-btn">
            <i class="me-1 fa fa-shopping-basket"></i>Añadir al carrito
          </a>
        </div>
      </div>
      <hr style="border: 1px solid #000; width: 50%; margin: 20px auto;">
    `;

    const addButton = document.getElementById('add-to-cart-btn');
    if (addButton) {
      addButton.addEventListener("click", function (e) {
        e.preventDefault();
        const quantity = parseInt(document.getElementById('quantity').value, 10);
        addToCart(idProduct, quantity);
      });
    }

    loadSimilarItems(idSubcategory);
  } catch (error) {
    console.error("Error al cargar los detalles del producto:", error);
  }
}

async function addToCart(idProduct, quantity) {
    if (quantity <= 0 || isNaN(quantity)) {
        alert("La cantidad debe ser un número válido y mayor que cero.");
        return;
    }

    const authToken = localStorage.getItem('authToken');
    if (!authToken) {
        window.location.href = "login.html";
        return;
    }

    const cartItem = {
        product_id: idProduct,
        quantity: quantity,
        action: "add"
    };

    try {
        const response = await fetch('http://localhost:8080/cart', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${authToken}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(cartItem)
        });
        if (response.status === 200) {
            // Muestra una alerta de éxito
            Swal.fire({
                icon: 'success',
                title: '¡Producto añadido!',
                text: 'El producto se ha añadido al carrito correctamente.',
                confirmButtonText: 'Aceptar'
            });
        } else {
            const errorData = await response.json();
            // Muestra una alerta de error
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Error al añadir producto al carrito: ' + errorData.message,
                confirmButtonText: 'Aceptar'
            });
        }

    } catch (error) {
        console.error('Error al añadir producto al carrito:', error);
        alert('Error al añadir producto al carrito. Inténtalo de nuevo más tarde.');
    }
}

async function loadSimilarItems(idSubcategory) {
    const similarItemsContainer = document.getElementById('similar-items');
    if (!similarItemsContainer) {
        console.error("El contenedor 'similar-items' no se encontró en el DOM.");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/Products?subcategory=${idSubcategory}`);
        if (!response.ok) {
            throw new Error("Error en la respuesta de la API");
        }

        let responseData = await response.json();
        // Si la respuesta tiene la propiedad 'products', extraemos ese array.
        let similarProducts = responseData.products ? responseData.products : responseData;

        if (!Array.isArray(similarProducts)) {
            similarItemsContainer.innerHTML = "<p>No se encontraron productos similares.</p>";
            return;
        }

        // Opcional: barajar el array (similar a lo que hice en ProductosDestacados)
        similarProducts = similarProducts.sort(() => 0.5 - Math.random());
        // Limitar a 4 productos
        const productsToShow = similarProducts.slice(0, 4);

        similarItemsContainer.innerHTML = "";
        productsToShow.forEach(product => {
            similarItemsContainer.innerHTML += `
                <div class="d-flex mb-3">
                    <a href="product.html?id=${product.idProduct}" class="me-3">
                        <img src="${product.imageUrl}" style="min-width: 96px; height: 96px;" class="img-md img-thumbnail" alt="imagen producto">
                    </a>
                    <div class="info">
                        <a href="product.html?id=${product.idProduct}" class="nav-link mb-1">${product.name}</a>
                        <strong class="text-dark">${product.price}€</strong>
                    </div>
                </div>
            `;
        });
    } catch (error) {
        console.error('Error al cargar los productos similares:', error);
        similarItemsContainer.innerHTML = "<p>Error al cargar productos relacionados.</p>";
    }
}

/* ===== INSTANCIACIÓN Y EJECUCIÓN ===== */

// Delegación de eventos: escuchamos los clics en el documento
document.addEventListener("click", function(event) {
    // Verificamos si el clic es en uno de los botones de cantidad
    if (event.target && event.target.matches("#button-minus")) {
        let quantityInput = document.querySelector("#quantity");
        let currentQuantity = parseInt(quantityInput.value, 10);

        if (currentQuantity > 1) {
            quantityInput.value = currentQuantity - 1;
        }
    }

    if (event.target && event.target.matches("#button-plus")) {
        let quantityInput = document.querySelector("#quantity");
        let currentQuantity = parseInt(quantityInput.value, 10);

        quantityInput.value = currentQuantity + 1;
    }
});

// escucha para esperar que el DOM esté cargado
document.addEventListener("DOMContentLoaded", () => {
    // Verificar si estamos en products.html antes de ejecutar
    if (document.getElementById("product-details")) {
        loadProductDetails(); // Llamar a la función cuando el DOM esté listo
    } else {
        console.warn("No se encontró el contenedor 'product-details', asegurarse de que este script se ejecuta en 'products.html'.");
    }
});
