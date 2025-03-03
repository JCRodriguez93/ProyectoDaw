document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const subcategoryId = urlParams.get("category");
    const subcategoryName = urlParams.get("subcategoryName");
    const searchTerm = urlParams.get("search");

    fetch("http://localhost:8080/Products")
        .then(response => response.json())
        .then(data => {
            let filteredProducts = [];

            if (subcategoryId) {
                // Filtrar por subcategoría
                filteredProducts = data.products.filter(producto => producto.idSubcategory === parseInt(subcategoryId));
                mostrarProductos(filteredProducts, `Categoría: ${subcategoryName}`);
            } else if (searchTerm) {
                // Filtrar por búsqueda de nombre
                filteredProducts = data.products.filter(producto =>
                    producto.name.toLowerCase().includes(searchTerm.toLowerCase())
                );
                mostrarProductos(filteredProducts, `Resultados para: "${searchTerm}"`);
            }
        })
        .catch(error => console.error("Error al obtener productos:", error));

    function mostrarProductos(productos, mensaje) {
        const productsContainer = document.getElementById("productsContainer");
        productsContainer.innerHTML = ""; // Limpiar contenido

        if (productos.length === 0) {
            productsContainer.innerHTML = `<p>No se encontraron productos para ${mensaje}</p>`;
        } else {
            productos.forEach(producto => {
                // Verifica que el producto tenga un id válido
                if (!producto.idProduct) {
                    console.error("Producto sin ID:", producto);
                    return; // Salir si no tiene un ID válido
                }

                const productElement = document.createElement("div");
                productElement.classList.add("col");
                productElement.innerHTML = `
                    <div class="card product-card h-100">
                        <a href="product.html?id=${producto.idProduct}" class="stretched-link"></a>
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
