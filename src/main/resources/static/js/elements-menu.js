// Hacer la llamada a la API y renderizar las categorías en el menú
async function cargarCategories() {
    try {
        const response = await fetch('http://localhost:8080/Category', {
            headers: {
                'Authorization': 'Bearer <tu_token>', // Cambia esto por el token de seguridad si lo usas
            }
        });

        if (!response.ok) {
            throw new Error('Error al obtener las categorías: ' + response.statusText);
        }

        const data = await response.json();
        const categories = data.categories; // Suponiendo que la respuesta tiene `categories`

        const menu = document.getElementById('menu-category');
        categories.forEach(category => {
            const li = document.createElement('li');
            li.className = 'nav-item'; // Clase de Bootstrap para un elemento de navegación
            li.innerHTML = `
                <a class="nav-link" href="/category/${category.id}">${category.name}</a>
            `;
            menu.appendChild(li);
        });
    } catch (error) {
        console.error('Error:', error);
    }
}

// Llama a la función al cargar la página
document.addEventListener('DOMContentLoaded', cargarCategories);
