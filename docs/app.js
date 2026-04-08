
const mMenu = document.querySelector('#mobile-menu')
const mMenuLinks = document.querySelector('.navbar__menu')

mMenu.addEventListener('click', function() {
    mMenu.classList.toggle('is-active');
    mMenuLinks.classList.toggle('active');
} );

/*animations*/
gsap.registerPlugin(ScrollTrigger) 

gsap.from('.main__content', {
    duration: 1,
    opacity:0,
    y: -150,
    stagger: 0.3
});

gsap.from('.services__animation', {
     scrollTrigger: {
        trigger: '.services__animation',
        start: 'top 80%', // when element hits 80% of viewport
    },
    //ScrollTrigger: '.services__animation',
    duration: 0.5,
    opacity:0,
    x: -150,
    stagger: 0.1
});