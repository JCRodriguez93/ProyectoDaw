
// PRODUCTOS DESTACADOS

document.addEventListener("DOMContentLoaded", () => {
    fetch("http://localhost:8080/Products")
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
                    <img src="${product.imageUrl}" alt="${product.name}" class="product-image" onclick="redirectTo(${product.idProduct})">
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

// Redirige a la página del producto
function redirectTo(productId) {
    window.location.href = `product.html?id=${productId}`;
}

// ---------------------------------------------------------------------------------------------------

// PRODUCTOS POPULARES

document.addEventListener("DOMContentLoaded", () => {
    fetch("http://localhost:8080/Products")  // Ajusta la URL de la API según sea necesario
        .then(response => response.json())
        .then(data => {
            // Seleccionamos dos productos aleatorios y nos aseguramos de que sean diferentes
            let firstProductIndex = Math.floor(Math.random() * data.products.length);
            let secondProductIndex;
            do {
                secondProductIndex = Math.floor(Math.random() * data.products.length);
            } while (secondProductIndex === firstProductIndex);

            const firstProduct = data.products[firstProductIndex];
            const secondProduct = data.products[secondProductIndex];

            // Actualizar la imagen y texto en la sección 'popular-left-section' con el primer producto
            const leftImage = document.querySelector(".popular-left-image");
            leftImage.src = firstProduct.imageUrl;
            leftImage.alt = firstProduct.name;

            const leftText = document.querySelector(".popular-left-text");
            leftText.textContent = `Descubre nuestro producto más vendido: ${firstProduct.name}`;

            // Crear y mostrar el segundo producto aleatorio en la sección de productos populares
            const productsContainer = document.querySelector(".popular-product-item-container");
            productsContainer.innerHTML = "";  // Limpiar contenido estático

            const productElement = document.createElement("div");
            productElement.classList.add("popular-product-item");

            productElement.innerHTML = `
                       <img src="${secondProduct.imageUrl}" alt="${secondProduct.name}" class="popular-product-image">
                       <h3 class="popular-product-name">${secondProduct.name}</h3>
                       <p class="popular-product-description">${secondProduct.description}</p>
                       <div class="popular-quantity-controls">
                           <button class="btn btn-white border border-secondary px-3 minus-button" type="button" id="button-minus"
                               data-mdb-ripple-color="dark">
                               &minus;
                           </button>
                           <input type="text" class="form-control text-center border border-secondary" id="quantity" value="1" aria-label="Example text with button addon" aria-describedby="button-minus">
                           <button class="btn btn-white border border-secondary px-3 plus-button" type="button" id="button-plus"
                               data-mdb-ripple-color="dark">
                               &plus;
                           </button>
                       </div>
                       <button class="popular-add-to-cart">Añadir al carrito</button>
                   `;

            productsContainer.appendChild(productElement);

            // Asignar los eventos de clic a los botones
            const minusButton = productElement.querySelector("#button-minus");
            const plusButton = productElement.querySelector("#button-plus");
            const quantityInput = productElement.querySelector("#quantity");

            // Función para disminuir la cantidad
            minusButton.addEventListener("click", () => {
                let currentValue = parseInt(quantityInput.value);
                if (currentValue > 1) {
                    quantityInput.value = currentValue - 1;
                }
            });

            // Función para aumentar la cantidad
            plusButton.addEventListener("click", () => {
                let currentValue = parseInt(quantityInput.value);
                quantityInput.value = currentValue + 1;
            });
        })
        .catch(error => console.error("Error al cargar productos:", error));
});



// ------------------------------------------------------------------------------------------------
// PRODUCTOS MÁS VENDIDOS

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

