
$(document).ready(function () {
  $('.slider-for').slick({
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: false,
    fade: true,
    asNavFor: '.image-slider'
  });
});

$(document).ready(function () {
  $('.image-slider').slick({
    slidesToShow: 3,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 3000,
    asNavFor: '.slider-for',
    prevArrow: `<button type='button' class='slick-prev slick-arrow'><i class="fa-solid fa-chevron-left"></i></button>`,
    nextArrow: `<button type='button' class='slick-next slick-arrow'><i class="fa-solid fa-chevron-right"></i></button>`,
    // dots: true,
    centerMode: true,
    focusOnSelect: true,
    // infinite: false,
  });
});

$(document).ready(function () {
  $(".card-slider").slick({
    slidesToShow: 6,
    slidesToScroll: 2,
    // autoplay: true,
    // autoplaySpeed: 3000,
    infinite: false,
    arrows: true,
    draggable: true,
    prevArrow: `<button type='button' class='slick-prev slick-arrow'><i class="fa-solid fa-chevron-left"></i></button>`,
    nextArrow: `<button type='button' class='slick-next slick-arrow'><i class="fa-solid fa-chevron-right"></i></button>`,

    responsive: [
      {
        breakpoint: 1025,
        settings: {
          slidesToShow: 3,
        },
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 2,
          arrows: false,
          infinite: false,
        },
      },
    ],

  });
});
