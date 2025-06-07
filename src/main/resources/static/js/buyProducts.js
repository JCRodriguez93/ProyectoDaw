async function buyProduct() {
  // Primero, comprobamos que el usuario está logueado
  const authToken = localStorage.getItem('authToken');
  const userId = localStorage.getItem('userId'); // Se guarda en el login

  // si no existe el token, te devuelve al login
  if (!authToken || !userId) {
            window.location.href = "login.html";
    return;
  }

  // Obtenemos la cantidad del input
  const quantity = parseInt(document.getElementById('quantity').value, 10);
  if (isNaN(quantity) || quantity <= 0) {
    Swal.fire({
      title: "Error",
      text: "La cantidad debe ser mayor que cero.",
      icon: "error"
    });
    return;
  }

  // Obtenemos el precio del producto
  const priceText = document.querySelector(".product-price").textContent;
  // Quitamos el símbolo "€" y convertimos a número
  const price = parseFloat(priceText.replace("€", ""));
  const totalPrice = price * quantity;

  // Creamos el objeto de la orden con los valores que necesitemos
  const order = {
    idUser: userId,
    totalQuantity: quantity,
    totalPrice: totalPrice,
    date: new Date().toISOString(),
    orderStatus: "PAGADO"
  };

  try {
    // Enviamos la orden al endpoint Orders
    const response = await fetch('http://192.168.1.34:8080/Orders', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${authToken}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(order)
    });

    if (response.ok) {
      Swal.fire({
        title: "Compra realizada!",
        text: "Tu compra se ha realizado con éxito.",
        icon: "success"
      });
    } else {
      const errorData = await response.json();
      Swal.fire({
        title: "Error",
        text: errorData.message || "No se pudo procesar la compra.",
        icon: "error"
      });
    }
  } catch (error) {
    console.error("Error al realizar la compra:", error);
    Swal.fire({
      title: "Error",
      text: "Ocurrió un error al procesar tu compra. Inténtalo más tarde.",
      icon: "error"
    });
  }
}
