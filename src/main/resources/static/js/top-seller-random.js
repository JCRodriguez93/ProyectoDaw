document.addEventListener("DOMContentLoaded", () => {
    fetch("http://localhost:8080/Products")
        .then(response => response.json())
        .then(data => {
            const productsContainer = document.querySelector(".top-sellers-products");
            productsContainer.innerHTML = ""; // Limpiar contenido estático

            // Verificar si los productos están en el formato correcto
            console.log(data);

            // Barajar los productos aleatoriamente
            const shuffledProducts = shuffleArray(data.products);

            // Seleccionar los primeros 4 productos del array barajado
            shuffledProducts.slice(0, 4).forEach(product => {
                const productElement = document.createElement("div");
                productElement.classList.add("product-item", "top-seller");

                // Asignar una calificación aleatoria entre 1 y 5
                const randomRating = Math.floor(Math.random() * 5) + 1;

                productElement.innerHTML = `
                    <img src="${product.imageUrl}" alt="${product.name}" class="product-image">
                    <h3 class="product-name">${product.name}</h3>
                    <div class="product-rating">${generateStars(randomRating)}</div>
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

// Genera estrellas para la calificación (usa una calificación aleatoria entre 1 y 5)
function generateStars(rating) {
    let stars = "";
    for (let i = 1; i <= 5; i++) {
        stars += `<span class="star">${i <= rating ? "&#9733;" : "&#9734;"}</span>`;
    }
    return stars;
}
