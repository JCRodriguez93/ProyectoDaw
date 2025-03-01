document.addEventListener('DOMContentLoaded', function () {
  const loginForm = document.getElementById('loginForm');
  if (loginForm) {
    loginForm.addEventListener('submit', handleLogin);
  }
  checkUserSession(); // Actualiza el mensaje de bienvenida si hay sesión activa

});

/* LOGIN y MENSAJE*/
async function handleLogin(event) {
  event.preventDefault();
  const email = document.getElementById('login_email').value;
  const password = document.getElementById('login_password').value;
  const errorMessage = document.getElementById('error-message');

  const result = await loginUser(email, password);
  if (result && result.token) {
    await saveUserSession(result.token);
    window.location.href = 'index.html';
  } else {
    showError(errorMessage, result?.message || 'Credenciales incorrectas.');
  }
}
async function loginUser(email, password) {
  try {
    const response = await fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });
    return await response.json();
  } catch (error) {
    console.error("Error en la solicitud de login:", error);
    return { message: 'Error en el servidor. Intente más tarde.' };
  }
}
async function saveUserSession(token) {
  localStorage.setItem('authToken', token);
  try {
    // Extraer el email del token (payload)
    const tokenParts = token.split('.')[1];
    const decodedPayload = JSON.parse(atob(tokenParts));
    const userEmail = decodedPayload.sub;

    // Obtener la lista de usuarios y extraer el array desde data.Users
    const response = await fetch("http://localhost:8080/Users", {
      method: "GET",
      headers: { "Authorization": `Bearer ${token}` }
    });
    if (!response.ok) throw new Error("No se pudo obtener los usuarios");

    const data = await response.json();
    const users = data.Users; // Extraemos el array de usuarios
    const user = users.find(u => u.email === userEmail);
    if (user) {
      localStorage.setItem('userId', user.idUser);
    } else {
      console.error("Usuario no encontrado en la base de datos");
    }
  } catch (error) {
    console.error("Error obteniendo el ID del usuario:", error);
  }
}
async function checkUserSession() {
  const authToken = localStorage.getItem('authToken');
  if (authToken) {
    await fetchUserData(authToken);
  }
}
async function fetchUserData(authToken) {
  try {
    const response = await fetch("http://localhost:8080/Users", {
      method: "GET",
      headers: { "Authorization": `Bearer ${authToken}` }
    });
    if (!response.ok) throw new Error("No se pudo obtener los usuarios");

    const data = await response.json();
    const users = data.Users; // Extraemos el array de usuarios

    // Decodificamos el token para obtener el email
    const tokenParts = authToken.split('.')[1];
    const decodedPayload = JSON.parse(atob(tokenParts));
    const userEmail = decodedPayload.sub;

    const user = users.find(u => u.email === userEmail);
    if (user) {
      document.getElementById("welcome-message").innerText = `Bienvenido, ${user.userName}`;
    } else {
      console.error("Usuario no encontrado");
    }
  } catch (error) {
    console.error("Error obteniendo datos del usuario:", error);
  }
}
function showError(element, message) {
  element.style.display = 'block';
  element.textContent = message;
}










