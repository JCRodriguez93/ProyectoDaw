$(document).ready(function() {
  $("#additionalFields").hide();
  $("#additionalFields").children().hide();

  $("#continueButton").click(function() {
    $(this).fadeOut(100, function() {
      $("#additionalFields").fadeIn(100, function() {
        $("#additionalFields").children().each(function(index) {
          $(this).delay(100 * index).fadeIn(200);
        });
      });
    });
  });

  // Expresión regular para validar la contraseña
  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@!%*?&+])[A-Za-z\d$@!%*?&+]{8,25}$/;

  $("#password").on("input", function() {
    const password = $(this).val();

    if (passwordRegex.test(password)) {
      $(this).removeClass("invalid-password").addClass("valid-password");
    } else {
      $(this).removeClass("valid-password").addClass("invalid-password");
    }
  });

  $("#registerForm").on("submit", async function(event) {
    event.preventDefault();

    const userName = $("#first_name").val();
    const userSurname = $("#last_name").val();
    const email = $("#register_email").val();
    const password = $("#password").val();
    const userBirth = $("#birthdate").val() + "T00:00:00";
    const roleId = 1;

    if (!passwordRegex.test(password)) {
      alert("La contraseña no cumple con los requisitos.");
      return;
    }

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
