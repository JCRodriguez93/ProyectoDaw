// JavaScript para el carrusel
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
    currentIndex = 0;
  } else if (index < 0) {
    currentIndex = totalSlides - 1;
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
  restartInterval();
}

// Función para retroceder al slide anterior
function prevSlide() {
  showSlide(currentIndex - 1);
  restartInterval();
}

// Redirección al hacer clic en una imagen
function redirectTo(url) {
  window.open(url, "_blank");
}

// Reiniciar el temporizador automático
function restartInterval() {
  clearInterval(autoSlide);
  autoSlide = setInterval(nextSlide, 5000);
}

// Inicializar el carrusel
document.addEventListener("DOMContentLoaded", () => {
  if (totalSlides > 0) {
    showSlide(currentIndex);
    autoSlide = setInterval(nextSlide, 4000); // Comienza el temporizador automático
  } else {
    console.warn("No se encontraron slides para el carrusel.");
  }
});
