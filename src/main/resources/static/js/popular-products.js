document.addEventListener("DOMContentLoaded", () => {
    fetch("http://localhost:8080/Products")  // Ajusta la URL de la API según sea necesario
        .then(response => response.json())
        .then(data => {
            // Seleccionamos un producto aleatorio de la lista
            const randomProduct = data.products[Math.floor(Math.random() * data.products.length)];

            // Actualizar la imagen y texto en la sección 'popular-left-section'
            const leftImage = document.querySelector(".popular-left-image");
            leftImage.src = randomProduct.imageUrl;
            leftImage.alt = randomProduct.name;

            const leftText = document.querySelector(".popular-left-text");
            leftText.textContent = `Descubre nuestro producto más vendido: ${randomProduct.name}`;

            // Crear y mostrar el producto aleatorio en la sección de productos populares
            const productsContainer = document.querySelector(".popular-product-item-container");
            productsContainer.innerHTML = "";  // Limpiar contenido estático

            const productElement = document.createElement("div");
            productElement.classList.add("popular-product-item");

            productElement.innerHTML = `
                <img src="${randomProduct.imageUrl}" alt="${randomProduct.name}" class="popular-product-image">
                <h3 class="popular-product-name">${randomProduct.name}</h3>
                <p class="popular-product-description">${randomProduct.description}</p>
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
