let heroBg = document.querySelector("body");

setInterval(() => {
    heroBg.style.backgroundImage = "url(/images/car-light.jpg)";

    setTimeout(() => {
        heroBg.style.backgroundImage = "url(/images/car.jpg)";
    }, 1000);
}, 2200);
