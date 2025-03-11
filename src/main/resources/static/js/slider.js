
/*
Con el slider no aparecen los productos
........................................

document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
const subcategoryId = urlParams.get('subcategory'); // Ahora coincide con la URL
    const subcategoryName = urlParams.get('subcategoryName');
    const rangeInput = document.getElementById("customRange3");
    const rangeValue = document.getElementById("rangeValue");

    let allProducts = [];

    if (subcategoryId) {
        fetch("http://localhost:8080/Products")
            .then(response => response.json())
            .then(data => {
                allProducts = data.products.filter(producto => producto.idSubcategory === parseInt(subcategoryId));
                mostrarProductos(allProducts, subcategoryName);
            })
            .catch(error => console.error("Error al obtener productos:", error));
    }

    rangeInput.addEventListener("input", function() {
        rangeValue.textContent = `${rangeInput.value}€`;
        filtrarProductosPorPrecio(rangeInput.value);
    });

    function filtrarProductosPorPrecio(precioMaximo) {
     console.log("Precio máximo seleccionado:", precioMaximo);
        console.log("Productos antes del filtro:", allProducts);
        if (parseInt(precioMaximo) === 0) {
                    console.log("Producto:", producto.name, "Precio:", producto.price);

            mostrarProductos(allProducts, subcategoryName); // Mostrar todos los productos nuevamente
        } else {
const productosFiltrados = allProducts.filter(producto => Number(producto.price) <= Number(precioMaximo));
            mostrarProductos(productosFiltrados, subcategoryName);
        }
    }


    function mostrarProductos(productos, subcategoryName) {
        const productsContainer = document.getElementById("productsContainer");
        productsContainer.innerHTML = ""; // Limpiar contenido estático

        if (productos.length === 0) {
            productsContainer.innerHTML = `<h4>No hay productos a ese precio</h4>`;
        } else {
            productos.forEach(producto => {
                const productElement = document.createElement("div");
                productElement.classList.add("col");

                productElement.innerHTML = `
                    <div class="card product-card h-100">
                        <a href="product.html?id=${producto.id}" class="stretched-link"></a>
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
*/