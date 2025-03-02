fetch('header.html')
  .then(response => response.text())
  .then(data => {
    document.getElementById('header-container').innerHTML = data;

    // 🔥 Cargar los scripts manualmente
    const script1 = document.createElement('script');
    script1.src = 'js/login-request.js';
    document.body.appendChild(script1);

    const script2 = document.createElement('script');
    script2.src = 'js/logout-request.js';
    document.body.appendChild(script2);
  });

fetch('footer.html')
  .then(response => response.text())
  .then(data => {
    document.getElementById('footer-div').innerHTML = data;
  });
