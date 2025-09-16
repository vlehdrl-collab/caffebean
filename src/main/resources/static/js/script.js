
document.addEventListener("DOMContentLoaded", () => {
  const horizontalUnderline = document.getElementById("horizontal-underline");
  const menuLinks = document.querySelectorAll("nav > ul > li > a, .menu-toggle");

  function moveUnderlineTo(target) {	
    const nav = document.querySelector("nav");
    const navRect = nav.getBoundingClientRect();
    const targetRect = target.getBoundingClientRect();
    horizontalUnderline.style.left = (targetRect.left - navRect.left) + "px";
    horizontalUnderline.style.width = targetRect.width + "px";	
  }

  // 일반 메뉴 클릭 시 저장만 (커피메뉴는 제외)
  menuLinks.forEach(link => {
    link.addEventListener("click", (e) => {
      const isSubmenuToggle = e.currentTarget.classList.contains("menu-toggle");
      if (!isSubmenuToggle) {
        localStorage.setItem("activeMenuHref", e.currentTarget.getAttribute("href"));
        menuLinks.forEach(l => l.classList.remove("active"));
        e.currentTarget.classList.add("active");
        moveUnderlineTo(e.currentTarget);
      }
    });
  });

  // 서브메뉴 클릭 시 커피메뉴에 underline
  const submenuLinks = document.querySelectorAll(".submenu a");
  submenuLinks.forEach(sub => {
    sub.addEventListener("click", (e) => {
      const parentToggle = sub.closest(".has-submenu").querySelector(".menu-toggle");
      menuLinks.forEach(m => m.classList.remove("active"));
      parentToggle.classList.add("active");
      moveUnderlineTo(parentToggle);

      localStorage.setItem("activeMenuHref", parentToggle.getAttribute("href"));
    });
  });

  // 복원
  const savedHref = localStorage.getItem("activeMenuHref");
  let activeLink = Array.from(menuLinks).find(a => a.getAttribute("href") === savedHref);
  if (!activeLink) activeLink = menuLinks[0];
  activeLink.classList.add("active");
  moveUnderlineTo(activeLink);
});

// 드롭다운 열기
document.addEventListener("DOMContentLoaded", () => {
  const submenuToggles = document.querySelectorAll(".menu-toggle");
  submenuToggles.forEach(toggle => {
    toggle.addEventListener("click", (e) => {
      e.preventDefault();
      const parent = toggle.closest(".has-submenu");
      const isOpen = parent.classList.contains("open");
      document.querySelectorAll(".has-submenu").forEach(item => item.classList.remove("open"));
      if (!isOpen) parent.classList.add("open");
    });
  });
  document.addEventListener("click", (e) => {
    if (!e.target.closest(".has-submenu")) {
      document.querySelectorAll(".has-submenu").forEach(item => item.classList.remove("open"));
    }
  });
});

// 스크롤
document.addEventListener("DOMContentLoaded", () => {
  const fadeEls = document.querySelectorAll(".index-hero h1, .index-hero p, .hero-text h1, .hero-text p");
  const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.style.animation = 'slide 1s ease-out forwards';
      } else {
        entry.target.style.animation = 'disappear 1s ease-out forwards';
      }
    });
  }, { threshold: 0.1 });
  fadeEls.forEach(el => observer.observe(el));
});
