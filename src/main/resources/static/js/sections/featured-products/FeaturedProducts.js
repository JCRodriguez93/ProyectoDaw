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


document.addEventListener("DOMContentLoaded", () => {
    if (document.querySelector(".products-container")) {
        const featuredProducts = new ProductosDestacados("http://localhost:8080/Products", ".products-container");
        featuredProducts.loadProducts();
    }
});
