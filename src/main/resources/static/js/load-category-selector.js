
    async function loadCategories() {
    try {
        const response = await fetch('http://localhost:8080/Category');
        if (!response.ok) throw new Error('No se pudieron cargar las categorías');

        const data = await response.json();
        const categories = data.categories; // Suponiendo que la respuesta contiene un objeto con un array llamado "categories"

        const categorySelect = document.getElementById('category');

        // Agregar las categorías al select
        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.id; // Usamos el ID como valor
            option.textContent = category.name; // El nombre de la categoría como texto
            categorySelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error al cargar las categorías:', error);
    }
}

// Cargar las categorías cuando se carga la página
document.addEventListener('DOMContentLoaded', loadCategories);
