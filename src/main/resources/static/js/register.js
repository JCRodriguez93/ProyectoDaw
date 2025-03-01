document.getElementById("registerForm").addEventListener("submit", async function(event) {
          event.preventDefault();

          const userName = document.getElementById("first_name").value;
          const userSurname = document.getElementById("last_name").value;
          const email = document.getElementById("register_email").value;
          const password = document.getElementById("password").value;
          const userBirth = document.getElementById("birthdate").value + "T00:00:00";
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
                  alert("Registro exitoso: " + data.message);

                  // Redirección después de 1.5 segundos para que el usuario vea el mensaje
                  setTimeout(() => {
                      window.location.href = "index.html";
                  }, 1500);

              } else {
                  alert("Error en el registro: " + (data.error || "No se pudo registrar"));
              }
          } catch (error) {
              console.error("Error en la solicitud:", error);
              alert("Hubo un problema con el registro");
          }
      });