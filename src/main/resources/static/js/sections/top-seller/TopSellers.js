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

// Si existe el contenedor para top sellers (index.html)
    if (document.querySelector(".top-sellers-products")) {
        const topSellers = new TopSellers("http://localhost:8080/Products", ".top-sellers-products");
        topSellers.loadProducts();
    }