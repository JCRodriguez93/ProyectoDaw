// HACER QUE EL MENÚ SE QUEDE EN LA PARTE SUPERIOR DE LA CABECERA

let lastScrollTop = 0;
    window.addEventListener("scroll", function () {
      let header = document.getElementById("header");
      let currentScroll = window.pageYOffset || document.documentElement.scrollTop;

      if (currentScroll > lastScrollTop) {
        // Scrolling down
        header.style.top = "-100px"; // Adjust this value according to your header height
      } else {
        // Scrolling up
        header.style.top = "0";
      }
      lastScrollTop = currentScroll <= 0 ? 0 : currentScroll; // For Mobile or negative scrolling
    }, false);


    // -----------------------------------------------------

    // OBTENER LAS CATEGORÍAS Y SUBCATEGORÍAS PARA EL MENÚ

document.addEventListener("DOMContentLoaded", function () {
    fetchCategorias();
});

function fetchCategorias() {
    fetch("https://proyectodaw-32ua.onrender.com/Category")
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
            //TODO: ERA ESTA LÍNEA LA QUE FALLABA PARA CARGAR LOS DATOS DE LA SUBCATEGORÍAS.
subLink.href = `catalog.html?subcategory=${subcategoria.id_subcategory}&subcategoryName=${subcategoria.name}`;
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




