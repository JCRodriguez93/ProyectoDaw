
 // CAROUSEL PARA EL INDEX.HTML

document.addEventListener('DOMContentLoaded', function () {

    // Inicializar el carrusel de Bootstrap
    const carouselElement = $('#carouselExampleCaptions').carousel({
        interval: 3000, // Tiempo de transición entre imágenes
        ride: 'carousel' // Asegura que inicie automáticamente
    });

    let carouselInterval = setInterval(() => {
        // Avanza al siguiente slide
        $('#carouselExampleCaptions').carousel('next');
    }, 3000);

    // Pausar el auto-desplazamiento cuando el usuario interactúe con los botones
    document.querySelector('.carousel-control-prev').addEventListener('click', () => {
        clearInterval(carouselInterval);
        $('#carouselExampleCaptions').carousel('prev');
        restartAutoPlay();
    });

    document.querySelector('.carousel-control-next').addEventListener('click', () => {
        clearInterval(carouselInterval);
        $('#carouselExampleCaptions').carousel('next');
        restartAutoPlay();
    });

    // Función para reiniciar el auto-desplazamiento después de la interacción
    function restartAutoPlay() {
        carouselInterval = setInterval(() => {
            $('#carouselExampleCaptions').carousel('next');
        }, 3000);
    }

    // Asegurar que todas las imágenes mantengan el mismo tamaño
    window.addEventListener('load', adjustCarouselSize);
    window.addEventListener('resize', adjustCarouselSize);

    function adjustCarouselSize() {
        const images = document.querySelectorAll('.carousel-item img');
        let maxHeight = 0;

        // Buscar la imagen más alta
        images.forEach(img => {
            if (img.clientHeight > maxHeight) {
                maxHeight = img.clientHeight;
            }
        });

        // Ajustar todas las imágenes a la misma altura
        images.forEach(img => {
            img.style.height = `${maxHeight}px`;
            img.style.objectFit = 'cover'; // Ajuste sin deformar la imagen
        });
    }
});
