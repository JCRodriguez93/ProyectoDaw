$(document).ready(function() {
  // Oculta inicialmente el contenedor de campos adicionales y sus hijos
  $("#additionalFields").hide();
  $("#additionalFields").children().hide();

  $("#continueButton").click(function() {
    // Oculta el botón "Continue" con un efecto fadeOut
    $(this).fadeOut(100, function() {
      // Muestra el contenedor de campos adicionales
      $("#additionalFields").fadeIn(100, function() {
        // Muestra cada hijo del contenedor en cascada
        $("#additionalFields").children().each(function(index) {
          $(this).delay(100 * index).fadeIn(200);
        });
      });
    });
  });

  $("#registerForm").on("submit", async function(event) {
    event.preventDefault();

    const userName = $("#first_name").val();
    const userSurname = $("#last_name").val();
    const email = $("#register_email").val();
    const password = $("#password").val();
    const userBirth = $("#birthdate").val() + "T00:00:00";
    const roleId = 1;

    const requestBody = {
      userName,
      userSurname,
      email,
      password,
      roleId,
      userBirth
    };

    try {
      const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(requestBody)
      });

      const data = await response.json();

      if (response.ok) {
        //alert("Registro exitoso: " + data.message);
        // Redirección después de 1.5 segundos para que el usuario vea el mensaje
        setTimeout(() => {
          window.location.href = "index.html";
        }, 100);
      } else {
        alert("Error en el registro: " + (data.error || "No se pudo registrar"));
      }
    } catch (error) {
      console.error("Error en la solicitud:", error);
      alert("Hubo un problema con el registro");
    }
  });
});
