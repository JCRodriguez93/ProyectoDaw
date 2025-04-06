document.addEventListener("DOMContentLoaded", function () {
  // Función que muestra los productos en la página (esta funciona bien)
  function mostrarProductos(productos, mensaje) {
    const productsContainer = document.getElementById("productsContainer");
    productsContainer.innerHTML = ""; // Limpiar contenido previo

    if (productos.length === 0) {
      productsContainer.innerHTML = `<p>No se encontraron productos para ${mensaje}</p>`;
    } else {
      productos.forEach(producto => {
        // Usamos producto.idProduct (como viene en el JSON)
        const productId = producto.idProduct;
        if (!productId) {
          console.error("Producto sin ID:", producto);
          return;
        }

        const productElement = document.createElement("div");
        productElement.classList.add("col");
        productElement.innerHTML = `
          <div class="card product-card h-100">
            <a href="product.html?id=${productId}" class="stretched-link"></a>
            <img src="${producto.imageUrl}" class="card-img-top" alt="${producto.name}">
            <div class="card-body">
              <h5 class="card-title">${producto.name}</h5>
              <p class="card-text">${producto.description}</p>
              <p class="card-text"><strong>${producto.price}€</strong></p>
            </div>
          </div>
        `;
        productsContainer.appendChild(productElement);
      });
    }
  }

  // Función para cargar los productos de la categoría seleccionada
  function loadProductsForCategory(selectedCategory) {
    // Si se selecciona "todos", se muestran todos los productos
    if (selectedCategory === "todos") {
      fetch("http://localhost:8080/Products")
        .then(response => response.json())
        .then(data => {
          mostrarProductos(data.products, "Todos los productos");
        })
        .catch(error => console.error("Error al obtener productos:", error));
      return;
    }

    // Si se elige un ID específico de categoría, se obtiene el detalle de la categoría
    // donde se incluyen las subcategorías
    fetch(`http://localhost:8080/Category/${selectedCategory}`)
      .then(response => response.json())
      .then(categoryData => {
        // Extraer los IDs de las subcategorías asociadas a la categoría
        const subcategoryIds = categoryData.subcategories.map(subcat => subcat.id_subcategory);

        // Obtener todos los productos y filtrar aquellos que pertenezcan a alguna de las subcategorías
        fetch("http://localhost:8080/Products")
          .then(response => response.json())
          .then(data => {
            // Aquí se corrige el nombre de la propiedad a "idSubcategory"
            const filteredProducts = data.products.filter(producto =>
              subcategoryIds.includes(producto.idSubcategory)
            );
            mostrarProductos(filteredProducts, `Categoría: ${categoryData.name}`);
          })
          .catch(error => console.error("Error al obtener productos:", error));
      })
      .catch(error => console.error("Error al obtener la categoría:", error));
  }

  // Función para cargar las categorías en el <select>
  async function loadCategories() {
    try {
      const response = await fetch('http://localhost:8080/Category');
      if (!response.ok) throw new Error('No se pudieron cargar las categorías');

      const data = await response.json();
      const categories = data.categories;
      const categorySelect = document.getElementById('category');

      // Agregar las categorías al select
      categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.id_category;
        option.textContent = category.name;
        categorySelect.appendChild(option);
      });
    } catch (error) {
      console.error('Error al cargar las categorías:', error);
    }
  }

  // Cargar las categorías al iniciar la página
  loadCategories();

  // Agregar el evento al botón de "Aplicar filtros"
  const applyButton = document.querySelector("button[type='submit']");
  if (applyButton) {
    applyButton.addEventListener("click", function (e) {
      e.preventDefault(); // Evitar comportamiento por defecto
      const selectedCategory = document.getElementById("category").value;

      if (selectedCategory === "todos") {
        // Actualizar URL sin parámetros para "todos"
        history.pushState(null, "", "catalog.html");
        loadProductsForCategory(selectedCategory);
      } else {
        // Para una categoría específica, obtenemos el detalle de la categoría para actualizar la URL
        fetch(`http://localhost:8080/Category/${selectedCategory}`)
          .then(response => response.json())
          .then(categoryData => {
            if (categoryData.subcategories && categoryData.subcategories.length > 0) {
              const qp = new URLSearchParams({
                category: selectedCategory,
                categoryName: categoryData.name
              });
              history.pushState(null, "", "catalog.html?" + qp.toString());

            } else {
              console.warn("No se encontraron subcategorías para esta categoría.");
              history.pushState(null, "", "catalog.html");
            }
            loadProductsForCategory(selectedCategory);
          })
          .catch(error => console.error("Error al obtener la categoría para actualizar la URL:", error));
      }
    });
  } else {
    console.error("Botón de aplicar filtros no encontrado.");
  }
});