$(document).ready(function () {
    $(".image-slider").slick({
      slidesToShow: 1,
      slidesToScroll: 1,
      autoplay: true,
      autoplaySpeed: 1000,
      infinite: true,
      arrows: true,
      draggable: true,
      prevArrow: `<button type='button' class='slick-prev slick-arrow'><i class="fa-solid fa-chevron-left"></i></button>`,
      nextArrow: `<button type='button' class='slick-next slick-arrow'><i class="fa-solid fa-chevron-right"></i></button>`,
      dots: true,
    //   responsive: [
    //     {
    //       breakpoint: 1025,
    //       settings: {
    //         slidesToShow: 3,
    //       },
    //     },
    //     {
    //       breakpoint: 480,
    //       settings: {
    //         slidesToShow: 1,
    //         arrows: false,
    //         infinite: false,
    //       },
    //     },
    //   ],
     
    });
  });


  $(document).ready(function () {
    $(".card-slider").slick({
      slidesToShow: 5,
      slidesToScroll: 1,
      autoplay: true,
      autoplaySpeed: 3000,
      infinite: true,
      arrows: true,
      draggable: true,
      prevArrow: `<button style="z-index: 999; position: absolute;" type='button' class='slick-prev slick-arrow'><i class="fa-solid fa-chevron-left"></i></button>`,
      nextArrow: `<button type='button' class='slick-next slick-arrow'><i class="fa-solid fa-chevron-right"></i></button>`,
    //   dots: true,
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


  $(document).ready(function () {
    $(".card_type_product").slick({
      slidesToShow: 5,
      slidesToScroll: 2,
    //   autoplay: true,
    //   autoplaySpeed: 3000,
      infinite: true,
      arrows: true,
      draggable: true,
      prevArrow: `<button type='button' class='slick-prev slick-arrow'><i class="fa-solid fa-chevron-left"></i></button>`,
      nextArrow: `<button type='button' class='slick-next slick-arrow'><i class="fa-solid fa-chevron-right"></i></button>`,
    //   dots: true,
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
            slidesToShow: 1,
            arrows: false,
            infinite: false,
          },
        },
      ],
     
    });
  });