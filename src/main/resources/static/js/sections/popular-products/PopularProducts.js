/* ===== PRODUCTOS POPULARES ===== */
class ProductosPopulares extends ProductSection {
    constructor(apiUrl) {
        super(apiUrl, ".popular-product-item-container");
    }

    displayProducts() {
        if (this.products.length < 2) {
            console.error("No hay suficientes productos para mostrar en populares.");
            return;
        }

        // Selección de dos productos aleatorios y distintos
        const [firstProduct, secondProduct] = this.getRandomProducts();

        // Actualizar la sección de la izquierda con el producto más vendido
        this.updateLeftSection(firstProduct);

        // Mostrar el segundo producto en la sección derecha
        this.displayPopularProduct(secondProduct);
    }

    getRandomProducts() {
        let firstIndex = Math.floor(Math.random() * this.products.length);
        let secondIndex;
        do {
            secondIndex = Math.floor(Math.random() * this.products.length);
        } while (secondIndex === firstIndex);

        return [this.products[firstIndex], this.products[secondIndex]];
    }

    updateLeftSection(product) {
        const leftImage = document.querySelector(".popular-left-image");
        const leftText = document.querySelector(".popular-left-text");

        if (leftImage) {
            leftImage.src = product.imageUrl;
            leftImage.alt = product.name;
            leftImage.onclick = () => { window.location.href = `product.html?id=${product.idProduct}`; };
        }

        if (leftText) {
            leftText.textContent = `Descubre nuestro producto más vendido: ${product.name}`;
        }
    }

    displayPopularProduct(product) {
        if (!this.container) return;
        this.container.innerHTML = "";
        const productElement = document.createElement("div");
        productElement.classList.add("popular-product-item");
        productElement.innerHTML = `
            <img src="${product.imageUrl}" alt="${product.name}" class="popular-product-image" onclick="window.location.href='product.html?id=${product.idProduct}'">
            <h3 class="popular-product-name">${product.name}</h3>
            <p class="popular-product-description">${product.description}</p>
            <div class="popular-quantity-controls">
                <button class="btn btn-white border border-secondary px-3 minus-button" type="button">&minus;</button>
                <input type="text" class="form-control text-center border border-secondary quantity-input" value="1">
                <button class="btn btn-white border border-secondary px-3 plus-button" type="button">&plus;</button>
            </div>
            <button class="popular-add-to-cart">Añadir al carrito</button>
        `;
        this.container.appendChild(productElement);
        this.addQuantityEvents(productElement, product);
    }

    addQuantityEvents(element, product) {
        const quantityInput = element.querySelector(".quantity-input");

        element.querySelector(".minus-button").addEventListener("click", () => {
            let currentValue = parseInt(quantityInput.value);
            if (currentValue > 1) {
                quantityInput.value = currentValue - 1;
            }
        });

        element.querySelector(".plus-button").addEventListener("click", () => {
            let currentValue = parseInt(quantityInput.value);
            quantityInput.value = currentValue + 1;
        });

        element.querySelector(".popular-add-to-cart").addEventListener("click", () => {
            this.addToCart(product.idProduct, parseInt(quantityInput.value));
        });
    }


// AÑADIR PRODUCTOS AL CARRITO DESDE POPULAR PRODUCTS
    async addToCart(idProduct, quantity) {
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
               Swal.fire({
                   icon: 'success',
                   title: '¡Éxito!',
                   text: 'Producto añadido al carrito correctamente.',
                   confirmButtonText: 'Aceptar'
               });
           } else {
               const errorData = await response.json();
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
}

// Inicialización si existen contenedores para productos populares (index.html)
document.addEventListener("DOMContentLoaded", () => {
    if (document.querySelector(".popular-product-item-container") || document.querySelector(".popular-left-image")) {
        const popularProducts = new ProductosPopulares("http://localhost:8080/Products");
        popularProducts.loadProducts();
    }
});
