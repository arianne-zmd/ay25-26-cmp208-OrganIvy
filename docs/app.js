
const mMenu = document.querySelector('#mobile-menu')
const mMenuLinks = document.querySelector('.navbar__menu')

mMenu.addEventListener('click', function() {
    mMenu.classList.toggle('is-active');
    mMenuLinks.classList.toggle('active');
} );