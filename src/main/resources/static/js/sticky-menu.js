let lastScrollTop = 0;
    window.addEventListener("scroll", function () {
      let header = document.getElementById("header");
      let currentScroll = window.pageYOffset || document.documentElement.scrollTop;

      if (currentScroll > lastScrollTop) {
        // Scrolling down
        header.style.top = "-100px"; // Adjust this value according to your header height
      } else {
        // Scrolling up
        header.style.top = "0";
      }
      lastScrollTop = currentScroll <= 0 ? 0 : currentScroll; // For Mobile or negative scrolling
    }, false);