const carousel = document.querySelector('.carousel');
const items = document.querySelectorAll('.carousel-item');
const prevBtn = document.querySelector('.carousel-btn.prev');
const nextBtn = document.querySelector('.carousel-btn.next');

let currentIndex = 0;
let interval;

function updateCarousel() {
  const itemWidth = items[0].offsetWidth + 32;
  carousel.style.transform = `translateX(-${currentIndex * itemWidth}px)`;
}

function goToNextSlide() {
  currentIndex = (currentIndex + 1) % items.length;
  updateCarousel();
}

function goToPrevSlide() {
  currentIndex = (currentIndex - 1 + items.length) % items.length;
  updateCarousel();
}

nextBtn.addEventListener('click', () => {
  goToNextSlide();
  resetInterval();
});

prevBtn.addEventListener('click', () => {
  goToPrevSlide();
  resetInterval();
});

function resetInterval() {
  clearInterval(interval);
  interval = setInterval(goToNextSlide, 3000);
}

window.addEventListener('resize', updateCarousel);

interval = setInterval(goToNextSlide, 3000);
updateCarousel();
