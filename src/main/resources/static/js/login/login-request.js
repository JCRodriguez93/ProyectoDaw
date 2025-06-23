document.addEventListener('DOMContentLoaded', function () {
  const loginForm = document.getElementById('loginForm');
  if (loginForm) {
    loginForm.addEventListener('submit', handleLogin);
  }
  checkUserSession(); // Actualiza el mensaje de bienvenida si hay sesión activa
    //La sesión depende de si hay un token JWT en el navegador
});

/* LOGIN y MENSAJE*/
async function handleLogin(event) {
  // Prevenir que el formulario se envíe de forma predeterminada
  event.preventDefault();

  // Obtener los valores del email y la contraseña del formulario
  const email = document.getElementById('login_email').value;
  const password = document.getElementById('login_password').value;

  // Obtener el elemento donde se mostrarán los mensajes de error
  const errorMessage = document.getElementById('error-message');

  // Llamar a la función loginUser para intentar autenticar al usuario
  const result = await loginUser(email, password);

  // Si la autenticación es exitosa (hay un token), guardar la sesión del usuario y redirigir
  if (result && result.token) {
    // Llamar a la función saveUserSession para guardar el token en el almacenamiento del navegador (localStorage)
    await saveUserSession(result.token);

    // Redirigir al usuario a la página principal (index.html)
    window.location.href = 'index.html';
  } else {
    // Si las credenciales son incorrectas o no hay token, mostrar el mensaje de error
    showError(errorMessage, result?.message || 'Credenciales incorrectas.');
  }
}

async function loginUser(email, password) {
  try {
    // Hacer una petición fetch al endpoint
    const response = await fetch('https://jorgedaw.store/auth/login', {
      method: 'POST',  // Solicitud (en este caso POST) para enviar los datos
      headers: { 'Content-Type': 'application/json' },  // Definir el tipo de contenido como JSON
      body: JSON.stringify({ email, password })  // Enviar los datos de login (email y contraseña) como JSON
    });

    // Esperar la respuesta del servidor y devolver el resultado en formato JSON
    return await response.json();
  } catch (error) {
    // Si hay un error durante la solicitud (como problemas de red o el servidor no responde)
    console.error("Error en la solicitud de login:", error);

    // Devolver un mensaje de error general si no se puede completar la solicitud
    return { message: 'Error en el servidor. Intente más tarde.' };
  }
}

async function saveUserSession(token) {
  // Guardar el token de autenticación en el almacenamiento local del navegador (localStorage)
  localStorage.setItem('authToken', token);

  try {
    // Extraer el email del usuario desde el payload del token JWT
    const tokenParts = token.split('.')[1];  // El token JWT tiene tres partes separadas por puntos. La segunda parte contiene el payload.
    const decodedPayload = JSON.parse(atob(tokenParts));  // Decodificar el payload (que está en base64) y convertirlo en objeto JSON.
    const userEmail = decodedPayload.sub;  // El campo 'sub' normalmente contiene el email del usuario en un JWT.

    // Hacer una solicitud GET para obtener la lista de usuarios desde el servidor
    const response = await fetch("https://jorgedaw.store/Users", {
      method: "GET",  // Solicitud GET (obtener datos)
      headers: { "Authorization": `Bearer ${token}` }  // Incluir el token en los encabezados para autorizar la solicitud
    });

    // Verificar si la respuesta del servidor fue exitosa (código de estado 200)
    if (!response.ok) throw new Error("No se pudo obtener los usuarios");

    // Convertir la respuesta del servidor a formato JSON
    const data = await response.json();
    const users = data.Users;  // Extraer el array de usuarios del objeto recibido

    // Buscar el usuario con el email que está en el payload del token
    const user = users.find(u => u.email === userEmail);
    if (user) {
      // Si se encuentra el usuario, guardar su ID en localStorage
      localStorage.setItem('userId', user.idUser);
    }
  } catch (error) {
    // Si ocurre algún error durante la ejecución (decodificación del token, solicitud HTTP, etc.)
    console.error("Error obteniendo el ID del usuario:", error);
  }
}

async function checkUserSession() {
  // Obtener el token de autenticación almacenado en el localStorage del navegador
  const authToken = localStorage.getItem('authToken');

  // Verificar si el token existe (es decir, si el usuario está autenticado)
  if (authToken) {
    // Si hay un token de autenticación, llamar a la función fetchUserData para obtener la información del usuario
    await fetchUserData(authToken);
  }
}

async function fetchUserData(authToken) {
  try {
    // Hacer una solicitud GET al servidor para obtener la lista de usuarios
    const response = await fetch("https://jorgedaw.store/Users", {
      method: "GET",  // Solicitud GET (obtener datos)
      headers: { "Authorization": `Bearer ${authToken}` }  // Incluir el token en los encabezados para autorizar la solicitud
    });

    // Verificar si la respuesta fue exitosa
    if (!response.ok) throw new Error("No se pudo obtener los usuarios");

    // Convertir la respuesta del servidor a formato JSON
    const data = await response.json();
    const users = data.Users;  // Extraer el array de usuarios del objeto recibido

    // Decodificar el token JWT para obtener el email del usuario
    const tokenParts = authToken.split('.')[1];  // El token JWT tiene tres partes separadas por puntos. La segunda parte contiene el payload.
    const decodedPayload = JSON.parse(atob(tokenParts));  // Decodificar el payload (que está en base64) y convertirlo en objeto JSON.
    const userEmail = decodedPayload.sub;  // El campo 'sub' normalmente contiene el email del usuario en un JWT.

    // Buscar el usuario con el email que está en el payload del token
    const user = users.find(u => u.email === userEmail);  // Buscar al usuario por su email

    // Si se encuentra el usuario, actualizar el mensaje de bienvenida
    if (user) {
      document.getElementById("welcome-message").innerText = `Bienvenido, ${user.userName}`;  // Actualizar el texto del mensaje de bienvenida
    }

  } catch (error) {
    // Si ocurre algún error durante la ejecución (como problemas de red, la solicitud falla, etc.)
    console.error("Error obteniendo datos del usuario:", error);
  }
}

function showError(element, message) {
  // Cambiar el estilo del elemento para hacerlo visible
  element.style.display = 'block';

  // Establecer el texto del mensaje de error en el contenido del elemento
  element.textContent = message;
}


document.addEventListener('DOMContentLoaded', () => {
  const pwdInput = document.getElementById('login_password');
  const toggleBtn = document.getElementById('toggleLoginPassword');
  const icon = document.getElementById('iconLoginPassword');

  // tus SVGs como strings
  const eyeOpen = `<svg data-slot="icon" fill="none" stroke-width="1.5" stroke="currentColor"
                     viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">
                     <path stroke-linecap="round" stroke-linejoin="round"
                           d="M12 12a3 3 0 1 0 0-6 3 3 0 0 0 0 6z
                              M2.458 12C3.732 7.943 7.523 5
                              12 5c4.477 0 8.268 2.943
                              9.542 7-1.274 4.057-5.065
                              7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path>
                   </svg>`;
  const eyeClosed = `<svg data-slot="icon" fill="none" stroke-width="1.5" stroke="currentColor"
                        viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">
                        <path stroke-linecap="round" stroke-linejoin="round"
                              d="M3.98 8.223A10.477 10.477 0 0 0
                                 1.934 12C3.226 16.338 7.244 19.5
                                 12 19.5c.993 0 1.953-.138
                                 2.863-.395M6.228 6.228A10.451
                                 10.451 0 0 1 12 4.5c4.756 0
                                 8.773 3.162 10.065 7.498a10.522
                                 10.522 0 0 1-4.293 5.774M6.228
                                 6.228 3 3m3.228 3.228 3.65
                                 3.65m7.894 7.894L21 21m-3.228-3.228
                                 -3.65-3.65m0 0a3 3 0 1 0-4.243-4.243
                                 m4.242 4.242L9.88 9.88"></path>
                     </svg>`;

  toggleBtn.addEventListener('click', () => {
    const isHidden = pwdInput.type === 'password';
    pwdInput.type = isHidden ? 'text' : 'password';
    icon.innerHTML = isHidden ? eyeOpen : eyeClosed;
    toggleBtn.setAttribute('aria-label', isHidden ? 'Ocultar contraseña' : 'Mostrar contraseña');
  });
});
