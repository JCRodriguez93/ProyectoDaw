document.addEventListener("DOMContentLoaded", () => {
    // Asegúrate de que el JS se ejecute solo después de que el DOM esté completamente cargado
    let currentIndex = 0;
    const slides = document.querySelectorAll(".carousel-slide");
    const totalSlides = slides.length;
    let autoSlide;

    // Mostrar el slide actual
    function showSlide(index) {
        const carouselImages = document.querySelector(".carousel-images");
        if (!carouselImages) {
            console.error("No se encontró el contenedor de imágenes del carrusel.");
            return;
        }

        // Asegura que el índice esté dentro del rango
        if (index >= totalSlides) {
            currentIndex = 0; // Si el índice es mayor que el total de slides, va al primero
        } else if (index < 0) {
            currentIndex = totalSlides - 1; // Si el índice es menor que 0, va al último
        } else {
            currentIndex = index;
        }

        // Ajusta la posición del carrusel
        const offset = -currentIndex * 100;
        carouselImages.style.transform = `translateX(${offset}%)`;
    }

    // Función para avanzar al siguiente slide
    function nextSlide() {
        showSlide(currentIndex + 1);
    }

    // Función para retroceder al slide anterior
    function prevSlide() {
        showSlide(currentIndex - 1);
    }

    // Redirección al hacer clic en una imagen
    function redirectTo(url) {
        window.open(url, "_blank");
    }

    // Inicializar el carrusel con temporizador automático
    if (totalSlides > 0) {
        showSlide(currentIndex); // Muestra el primer slide
        autoSlide = setInterval(() => {
            nextSlide(); // Avanza automáticamente al siguiente slide cada 5 segundos
        }, 5000);
    } else {
        console.warn("No se encontraron slides para el carrusel.");
    }

    // Detener el autoplay cuando el usuario interactúa
    const carousel = document.getElementById("carousel");
    carousel.addEventListener("mouseover", () => {
        clearInterval(autoSlide); // Detener el autoplay al pasar el ratón sobre el carrusel
    });
    carousel.addEventListener("mouseout", () => {
        autoSlide = setInterval(() => {
            nextSlide(); // Reanudar el autoplay cuando el ratón sale del carrusel
        }, 5000);
    });
});
