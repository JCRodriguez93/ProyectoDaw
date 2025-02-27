document.addEventListener("DOMContentLoaded", () => {
    fetch("http://localhost:8080/Products")  // Asegúrate de que la URL sea correcta
        .then(response => response.json())
        .then(data => {
            const productsContainer = document.querySelector(".products-container");
            productsContainer.innerHTML = "";  // Limpiar contenido estático

            // Barajar los productos aleatoriamente
            const shuffledProducts = shuffleArray(data.products);

            // Seleccionar los primeros 4 productos del array barajado
            shuffledProducts.slice(0, 4).forEach(product => {
                const productElement = document.createElement("div");
                productElement.classList.add("product-item");

                productElement.innerHTML = `
                    <img src="${product.imageUrl}" alt="${product.name}" class="product-image" onclick="redirectTo('${product.url}')">
                    <h3 class="product-name">${product.name}</h3>
                    <p class="product-price">${product.price}€</p>
                `;

                productsContainer.appendChild(productElement);
            });
        })
        .catch(error => console.error("Error al cargar productos:", error));
});

// Función para barajar el array de productos de manera aleatoria
function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]]; // Intercambiar elementos
    }
    return array;
}

// Redirige al enlace del producto
function redirectTo(url) {
    window.location.href = url;
}
