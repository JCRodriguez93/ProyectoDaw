document.addEventListener("DOMContentLoaded", function () {
    /* ------------------------------------------------------------------
        Función que muestra los productos en la página.
        Recibe un array de productos y un mensaje descriptivo para mostrar.
    ------------------------------------------------------------------- */
    function showProducts(product, msj) {
      const productsContainer = document.getElementById("productsContainer");
      productsContainer.innerHTML = ""; // Limpiar el contenedor
      if (product.length === 0) {
        productsContainer.innerHTML = `<p>No se encontraron productos para ${msj}</p>`;
      } else {
        product.forEach(product => {
          const productId = product.idProduct;
          if (!productId) {
            console.error("Producto sin ID:", product);
            return;
          }
          const productElement = document.createElement("div");
          productElement.classList.add("col");
          productElement.innerHTML = `
            <div class="card product-card h-100">
              <a href="product.html?id=${productId}" class="stretched-link"></a>
              <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">
              <div class="card-body">
                <h5 class="card-title">${product.name}</h5>
                <p class="card-text">${product.description}</p>
                <p class="card-text"><strong>${product.price}€</strong></p>
              </div>
            </div>
          `;
          productsContainer.appendChild(productElement);
        });
      }
    }

    /* ------------------------------------------------------------------
        Función para cargar todas las categorías y agregarlas al select.
        Se le envían los datos al <select id="category">
    ------------------------------------------------------------------- */
    async function loadCategories() {
      try {
        const response = await fetch("http://192.168.1.34:8080/Category");
        if (!response.ok) throw new Error("No se pudieron cargar las categorías");
        const data = await response.json();
        const categories = data.categories;
        const categorySelect = document.getElementById("category");
        // Se añaden las opciones a continuación de "Todos"
        categories.forEach(category => {
          const option = document.createElement("option");
          option.value = category.id_category; // Ej: 1, 2, 3, etc.
          option.textContent = category.name;
          categorySelect.appendChild(option);
        });
      } catch (error) {
        console.error("Error al cargar las categorías:", error);
      }
    }

    /* ------------------------------------------------------------------
        Función para obtener todos los productos desde la API.
    ------------------------------------------------------------------- */
    async function fetchAllProducts() {
      try {
        const response = await fetch("http://192.168.1.34:8080/Products");
        const data = await response.json();
        return data.products;
      } catch (error) {
        console.error("Error al obtener productos:", error);
        return [];
      }
    }

    /* ------------------------------------------------------------------
        Función para obtener el detalle de una categoría (incluye sus subcategorías)
        a partir de su ID.
    ------------------------------------------------------------------- */
    async function fetchCategoryDetail(categoryId) {
      try {
        const response = await fetch(`http://192.168.1.34:8080/Category/${categoryId}`);
        return await response.json();
      } catch (error) {
        console.error("Error al obtener la categoría:", error);
        return null;
      }
    }

    /* ------------------------------------------------------------------
        Función que aplica los filtros combinados:
        - Filtro por categoría (o "todos")
        - Filtro por búsqueda (nombre del producto)
        - Filtro por precio (utilizando el slider)

        Esta función se llama al pulsar el botón "Aplicar filtros".
    ------------------------------------------------------------------- */
    async function applyFilters() {
      // Recoger valores de los controles
      const selectedCategory = document.getElementById("category").value;
      const searchText = document.getElementById("search").value.trim().toLowerCase();
      const priceValue = Number(document.getElementById("customRange3").value);

      let products = [];

      // FILTRO POR CATEGORÍA:
      if (selectedCategory === "todos") {
        products = await fetchAllProducts();
      } else {
        const categoryData = await fetchCategoryDetail(selectedCategory);
        if (!categoryData) {
          console.error("No se pudo obtener el detalle de la categoría");
          return;
        }
        // Extraer los IDs de las subcategorías correspondientes
        const subcategoryIds = categoryData.subcategories.map(subcat => subcat.id_subcategory);
        const allProducts = await fetchAllProducts();
        // Se incluyen aquellos productos cuyo idSubcategory esté en la lista
        products = allProducts.filter(product =>
          subcategoryIds.includes(product.idSubcategory)
        );
      }

      // FILTRO POR BUSQUEDA:
      if (searchText) {
        products = products.filter(product =>
          product.name && product.name.toLowerCase().includes(searchText)
        );
      }

      // FILTRO POR PRECIO:
      if (priceValue > 0) {
        // Cambia acá la comparación si deseas exactitud (===) o establecer un valor máximo (<=)
        products = products.filter(product =>
          Number(product.price) <= priceValue
        );
      }

      // Actualización de la URL (opcional) mostrando algunos de los filtros aplicados
      let qp = new URLSearchParams();
      if (selectedCategory !== "todos") {
        qp.set("category", selectedCategory);
        qp.set("categoryName", document.getElementById("category").selectedOptions[0].textContent);
      }
      if (searchText) {
        qp.set("search", searchText);
      }
      if (priceValue > 0) {
        qp.set("price", priceValue);
      }
      const nuevaUrl = "catalog.html" + (qp.toString() ? "?" + qp.toString() : "");
      history.pushState(null, "", nuevaUrl);

      // Construir un mensaje descriptivo de los filtros aplicados
      let mensaje = "Filtros aplicados: ";
      mensaje += selectedCategory === "todos"
        ? "Todas las categorías"
        : document.getElementById("category").selectedOptions[0].textContent;
      if (searchText) mensaje += `, búsqueda: "${searchText}"`;
      if (priceValue > 0) mensaje += `, precio <= ${priceValue}€`;

      // Mostrar los productos filtrados
      showProducts(products, mensaje);
    }

    /* ------------------------------------------------------------------
        Evento para actualizar la visualización del valor del slider.
        (Esto es independiente del filtrado, ya que se muestra en tiempo real.)
    ------------------------------------------------------------------- */
    const rangeInput = document.getElementById("customRange3");
    const rangeValueElem = document.getElementById("rangeValue");
    rangeInput.addEventListener("input", function () {
      rangeValueElem.textContent = `${rangeInput.value}€`;
    });

    /* ------------------------------------------------------------------
        Asignar el evento al botón "Aplicar filtros"
    ------------------------------------------------------------------- */
    const applyButton = document.querySelector("button[type='submit']");
    if (applyButton) {
      applyButton.addEventListener("click", function (e) {
        e.preventDefault(); // Evitar el envío de formulario o recarga
        applyFilters();
      });
    } else {
      console.error("Botón de aplicar filtros no encontrado.");
    }

    // Cargar las categorías al iniciar la página
    loadCategories();
  });