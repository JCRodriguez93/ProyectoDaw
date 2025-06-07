/* ===== PRODUCTOS DESTACADOS ===== */
class ProductosDestacados extends ProductSection {
    constructor(apiUrl, containerSelector, maxItems = 4) {
        super(apiUrl, containerSelector);
        this.maxItems = maxItems;
    }

    displayProducts() {
        if (!this.container || !this.products.length) return;
        this.container.innerHTML = "";

        // Barajamos y seleccionamos los primeros maxItems
        const selectedProducts = this.shuffleArray(this.products).slice(0, this.maxItems);
        selectedProducts.forEach(product => {
            const productElement = this.createProductElement(product);
            this.container.appendChild(productElement);
        });
    }

    // Si shuffleArray no está en ProductSection, lo agregamos aquí
    shuffleArray(array) {
        return array
            .map(item => ({ item, sort: Math.random() }))
            .sort((a, b) => a.sort - b.sort)
            .map(({ item }) => item);
    }
}

// Un solo evento DOMContentLoaded para manejar todo
document.addEventListener("DOMContentLoaded", () => {
    if (document.querySelector(".products-container")) {
        const featuredProducts = new ProductosDestacados("http://192.168.1.34:8080/Products", ".products-container");
        featuredProducts.loadProducts();
    }

   /*
   NO BORRAR POR SI ACASO PERO ESTO NO SIRVE DE MOMENTO
   -----------------------------------------------------
    // Verificar si estamos en products.html antes de ejecutar
       if (document.getElementById("product-details")) {
           console.log("Cargando detalles del producto...");
           loadProductDetails();
       } else {
           console.warn("No se encontró el contenedor 'product-details', asegurarse de que este script se ejecuta en 'products.html'.");
       }

   */
});
