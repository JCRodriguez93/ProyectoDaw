document.addEventListener("DOMContentLoaded", function() {
        const urlParams = new URLSearchParams(window.location.search);
        const subcategoryId = urlParams.get('category');
        const subcategoryName = urlParams.get('subcategoryName'); // Suponiendo que también pasas el nombre de la subcategoría

        if (subcategoryId) {
            fetch("http://localhost:8080/Products")
                .then(response => response.json())
                .then(data => {
                    const filteredProducts = data.products.filter(producto => producto.idSubcategory === parseInt(subcategoryId));
                    mostrarProductos(filteredProducts, subcategoryName);
                })
                .catch(error => console.error("Error al obtener productos:", error));
        }

        function mostrarProductos(productos, subcategoryName) {
            const productsContainer = document.getElementById("productsContainer");
            productsContainer.innerHTML = ""; // Limpiar contenido estático

            if (productos.length === 0) {
                productsContainer.innerHTML = `<p>No existen productos de la categoría: ${subcategoryName}</p>`;
            } else {
                productos.forEach(producto => {
                    const productElement = document.createElement("div");
                    productElement.classList.add("col");

                    productElement.innerHTML = `
                        <div class="card product-card h-100">
                            <img src="${producto.imageUrl}" class="card-img-top" alt="${producto.name}">
                            <div class="card-body">
                                <h5 class="card-title">${producto.name}</h5>
                                <p class="card-text">${producto.description}</p>
                                <p class="card-text"><strong>${producto.price}</strong></p>
                            </div>
                        </div>
                    `;

                    productsContainer.appendChild(productElement);
                });
            }
        }
    });