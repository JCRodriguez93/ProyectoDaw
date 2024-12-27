document.addEventListener("DOMContentLoaded", function () {
    const menu = document.querySelector('.navbar');
    const header = document.querySelector('header'); // El contenedor del menú
    let initialMenuOffset = header.offsetHeight; // Almacenar la altura del header

    window.addEventListener('scroll', function () {
        if (window.scrollY >= initialMenuOffset) {
            // Si la posición de scroll es mayor o igual a la altura del header, hacemos el menú sticky
            menu.classList.add('sticky');
        } else {
            // Si volvemos arriba, quitamos la clase sticky
            menu.classList.remove('sticky');
        }
    });
  });