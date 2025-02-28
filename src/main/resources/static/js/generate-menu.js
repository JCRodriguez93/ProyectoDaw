document.addEventListener("DOMContentLoaded", function () {
    fetchCategorias();
});

function fetchCategorias() {
    fetch("http://localhost:8080/Category")
        .then(response => response.json())
        .then(data => generarMenu(data.categories))
        .catch(error => console.error("Error al obtener categorías:", error));
}

function generarMenu(categorias) {
    const menu = document.getElementById("menuCategorias");
    menu.innerHTML = ""; // Limpiar el menú antes de agregar elementos nuevos

    categorias.forEach(categoria => {
        let li = document.createElement("li");
        li.className = "nav-item dropdown";

        let a = document.createElement("a");
        a.className = "nav-link dropdown-toggle";
        a.href = "#";
        a.setAttribute("id", "dropdown" + categoria.id_category);
        a.setAttribute("role", "button");
        a.setAttribute("data-bs-toggle", "dropdown");
        a.setAttribute("aria-expanded", "false");
        a.textContent = categoria.name;

        let dropdownMenu = document.createElement("div");
        dropdownMenu.className = "dropdown-menu";
        dropdownMenu.setAttribute("aria-labelledby", "dropdown" + categoria.id_category);

        let subcategoryList = document.createElement("ul");
        subcategoryList.className = "list-unstyled"; // Evita el estilo por defecto de listas

        categoria.subcategories.forEach(subcategoria => {
            let subItem = document.createElement("li");
            let subLink = document.createElement("a");
            subLink.className = "dropdown-item";
            subLink.href = `catalog.html?category=${subcategoria.id_subcategory}`; // Enlazar a catalog.html con el ID de la subcategoría
            subLink.textContent = subcategoria.name;

            subItem.appendChild(subLink);
            subcategoryList.appendChild(subItem);
        });

        dropdownMenu.appendChild(subcategoryList);
        li.appendChild(a);
        li.appendChild(dropdownMenu);
        menu.appendChild(li);
    });
}

