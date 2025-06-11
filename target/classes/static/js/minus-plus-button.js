document.getElementById('button-minus').addEventListener('click', function() {
    var quantity = parseInt(document.getElementById('quantity').value, 10);
    if (quantity > 1) { // Evita que el valor sea menor que 1
      document.getElementById('quantity').value = quantity - 1;
    }
  });

  document.getElementById('button-plus').addEventListener('click', function() {
    var quantity = parseInt(document.getElementById('quantity').value, 10);
    document.getElementById('quantity').value = quantity + 1;
  });