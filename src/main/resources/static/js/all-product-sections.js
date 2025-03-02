/* ===== CLASE BASE PARA SECCIONES DE PRODUCTOS ===== */
class ProductSection {
    constructor(apiUrl, containerSelector) {
        this.apiUrl = apiUrl;
        this.container = document.querySelector(containerSelector);
        this.products = [];
        if (!this.container) {
            console.error(`El contenedor ${containerSelector} no se encuentra en el DOM.`);
        }
    }

    async loadProducts() {
        try {
            const response = await fetch(this.apiUrl);
            const data = await response.json();
            this.products = data.products;
            this.displayProducts();
        } catch (error) {
            console.error("Error al cargar productos:", error);
        }
    }

    /* Método abstracto para mostrar productos: se sobrescribe en cada subclase */
    displayProducts() {
        console.error("displayProducts() debe implementarse en la subclase.");
    }

    /* Función auxiliar para barajar un array */
    shuffleArray(array) {
        for (let i = array.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [array[i], array[j]] = [array[j], array[i]];
        }
        return array;
    }

    /* Función auxiliar común para crear el elemento HTML de un producto.
       Todas las imágenes llaman a product.html con el id del producto. */
    createProductElement(product) {
        const productElement = document.createElement("div");
        productElement.classList.add("product-item");
        productElement.innerHTML = `
            <img src="${product.imageUrl}" alt="${product.name}" class="product-image" onclick="window.location.href='product.html?id=${product.idProduct}'">
            <h3 class="product-name">${product.name}</h3>
            <p class="product-price">${product.price}€</p>
        `;
        return productElement;
    }
}

/* ===== PRODUCTOS DESTACADOS ===== */
class ProductosDestacados extends ProductSection {
    constructor(apiUrl, containerSelector, maxItems = 4) {
        super(apiUrl, containerSelector);
        this.maxItems = maxItems;
    }

    displayProducts() {
        if (!this.container) return;
        this.container.innerHTML = "";
        // Barajamos y seleccionamos los primeros maxItems
        const selectedProducts = this.shuffleArray(this.products).slice(0, this.maxItems);
        selectedProducts.forEach(product => {
            const productElement = this.createProductElement(product);
            this.container.appendChild(productElement);
        });
    }
}

/* ===== PRODUCTOS POPULARES ===== */
class ProductosPopulares extends ProductSection {
    constructor(apiUrl) {
        // El contenedor para el producto popular se define de forma independiente
        super(apiUrl, ".popular-product-item-container");
    }

    displayProducts() {
        if (this.products.length < 2) {
            console.error("No hay suficientes productos para mostrar en populares.");
            return;
        }
        // Seleccionamos dos productos aleatorios y distintos
        let firstIndex = Math.floor(Math.random() * this.products.length);
        let secondIndex;
        do {
            secondIndex = Math.floor(Math.random() * this.products.length);
        } while (secondIndex === firstIndex);
        const firstProduct = this.products[firstIndex];
        const secondProduct = this.products[secondIndex];

        // Actualizamos la sección de la izquierda con el producto más vendido
        const leftImage = document.querySelector(".popular-left-image");
        if (leftImage) {
            leftImage.src = firstProduct.imageUrl;
            leftImage.alt = firstProduct.name;
            leftImage.onclick = () => { window.location.href = `product.html?id=${firstProduct.idProduct}`; };
        }
        const leftText = document.querySelector(".popular-left-text");
        if (leftText) {
            leftText.textContent = `Descubre nuestro producto más vendido: ${firstProduct.name}`;
        }
        // Mostramos el segundo producto en la sección derecha
        this.displayPopularProduct(secondProduct);
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
        const minusButton = element.querySelector(".minus-button");
        const plusButton = element.querySelector(".plus-button");
        const quantityInput = element.querySelector(".quantity-input");
        const addToCartButton = element.querySelector(".popular-add-to-cart");

        minusButton.addEventListener("click", () => {
            let currentValue = parseInt(quantityInput.value);
            if (currentValue > 1) {
                quantityInput.value = currentValue - 1;
            }
        });
        plusButton.addEventListener("click", () => {
            let currentValue = parseInt(quantityInput.value);
            quantityInput.value = currentValue + 1;
        });
        addToCartButton.addEventListener("click", () => {
            this.addToCart(product.id, parseInt(quantityInput.value));
        });
    }

    async addToCart(productId, quantity) {
        const authToken = localStorage.getItem('authToken');
        if (!authToken) {
            alert("Necesitas estar logueado para añadir productos al carrito.");
            return;
        }
        const cartItem = {
            product_id: productId,
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
                alert('Producto añadido al carrito correctamente.');
            } else {
                const errorData = await response.json();
                alert('Error al añadir producto al carrito: ' + errorData.message);
            }
        } catch (error) {
            console.error('Error al añadir producto al carrito:', error);
            alert('Error al añadir producto al carrito. Inténtalo de nuevo más tarde.');
        }
    }
}

/* ===== TOP SELLERS ===== */
class TopSellers extends ProductSection {
    constructor(apiUrl, containerSelector) {
        super(apiUrl, containerSelector);
    }

    generateStars(rating) {
        let stars = "";
        for (let i = 1; i <= 5; i++) {
            stars += `<span class="star">${i <= rating ? "&#9733;" : "&#9734;"}</span>`;
        }
        return stars;
    }

    displayProducts() {
        if (!this.container) return;
        this.container.innerHTML = "";
        const shuffledProducts = this.shuffleArray(this.products);
        const selectedProducts = shuffledProducts.slice(0, 4);
        selectedProducts.forEach(product => {
            const productElement = document.createElement("div");
            productElement.classList.add("product-item", "top-seller");
            const randomRating = Math.floor(Math.random() * 5) + 1;
            productElement.innerHTML = `
                <img src="${product.imageUrl}" alt="${product.name}" class="product-image" onclick="window.location.href='product.html?id=${product.idProduct}'">
                <h3 class="product-name">${product.name}</h3>
                <div class="product-rating">${this.generateStars(randomRating)}</div>
            `;
            this.container.appendChild(productElement);
        });
    }
}

/* ===== CARGA DE DETALLES DEL PRODUCTO (PRODUCT.HTML) ===== */
async function loadProductDetails() {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');
    const productDetails = document.getElementById('product-details');
    if (!productDetails) {
        console.error("El contenedor de detalles del producto no se encuentra en el DOM.");
        return;
    }
    try {
        const response = await fetch(`http://localhost:8080/Products/${productId}`);
        if (!response.ok) throw new Error("Error en la respuesta de la API");
        const product = await response.json();
        productDetails.innerHTML = `
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
                    <div class="d-flex justify-content-between">
                        <button class="btn btn-warning">Buy now</button>
                        <button class="btn btn-primary"><i class="me-1 fa fa-shopping-basket"></i> Add to cart</button>
                    </div>
                </div>
            </div>
        `;
                loadSimilarItems(product.subcategory);
    } catch (error) {
        console.error("Error al cargar los detalles del producto:", error);
    }

}
async function loadSimilarItems(subcategory) {
    const similarItemsContainer = document.getElementById('similar-items');
    if (!similarItemsContainer) return;

    try {
        const response = await fetch(`http://localhost:8080/Products?subcategory=${subcategory}`);
        if (!response.ok) throw new Error("Error en la respuesta de la API");
        const similarProducts = await response.json();

        similarProducts.forEach(product => {
            const productElement = document.createElement("div");
            productElement.classList.add("d-flex", "mb-3");
            productElement.innerHTML = `
                <a href="product.html?id=${product.idProduct}" class="me-3">
                    <img src="${product.imageUrl}" style="min-width: 96px; height: 96px;" class="img-md img-thumbnail" alt="imagen de producto">
                </a>
                <div class="info">
                    <a href="product.html?id=${product.idProduct}" class="nav-link mb-1">
                        ${product.name}
                    </a>
                    <strong class="text-dark">${product.price}€</strong>
                </div>
            `;
            similarItemsContainer.appendChild(productElement);
        });
    } catch (error) {
        console.error("Error al cargar los productos similares:", error);
    }
}
/* ===== INSTANCIACIÓN Y EJECUCIÓN ===== */
document.addEventListener("DOMContentLoaded", () => {
    // Si existe el contenedor de productos destacados (index.html)
    if (document.querySelector(".products-container")) {
        const featuredProducts = new ProductosDestacados("http://localhost:8080/Products", ".products-container");
        featuredProducts.loadProducts();
    }
    // Si existen contenedores para productos populares (index.html)
    if (document.querySelector(".popular-product-item-container") || document.querySelector(".popular-left-image")) {
        const popularProducts = new ProductosPopulares("http://localhost:8080/Products");
        popularProducts.loadProducts();
    }
    // Si existe el contenedor para top sellers (index.html)
    if (document.querySelector(".top-sellers-products")) {
        const topSellers = new TopSellers("http://localhost:8080/Products", ".top-sellers-products");
        topSellers.loadProducts();
    }
    // Si estamos en product.html y existe el contenedor de detalles
    if (document.getElementById('product-details')) {
        loadProductDetails();
    }
});
