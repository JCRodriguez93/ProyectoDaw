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