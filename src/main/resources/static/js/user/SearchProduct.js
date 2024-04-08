app.controller("search-ctrl", function ($scope, $http, $window, $timeout) {
  let main = document.getElementById('container_main');
  let spinner = document.getElementById('spinner');
  var selectedMall = document.getElementById("Mall");
  var selectedSoldCount = document.getElementById("ASC_soldCount");
  // let first = document.getElementById('firstPage');
  var $search = ($scope.search = {
    loadData: [],
    temLoadData: [],
    itemsPerPage: 28,
    currentPage: 1,
    totalPages: 1, // Khởi tạo totalPages ban đầu
    initialData: [], // Sao lưu dữ liệu ban đầu
    temLoadData: [],
    isLoading: false, // Ban đầu, không hiển thị hình ảnh load\
    loadBrand: [],
    loadCategory: [],
    loadProvinces: [],
    loadProductSearch: function () {
      isLoading = true;
      $http
        .get("/api/search")
        .then((resp) => {
          this.initialData = resp.data;
          this.loadData = this.initialData.slice();
          this.temLoadData = this.initialData.slice();
          console.log(this.temLoadData.length);
          this.checkSearchNull();
          this.getBrandAndCategory();
          this.updatePagedItems();
          this.loadProvinces();
          this.loadSpinner();
          console.log(this.loadData);
        })
        .catch(function (err) {
          console.error(err); // xử lý lỗi khi gọi API
        });
    }, // loading data

    loadProvinces: function () {
      $http
        .get("https://provinces.open-api.vn/api/?depth=1")
        .then((resp) => {
          this.loadProvinces = resp.data.map((item) => {
            const cityName = item.name.replace(/\b(Thành phố|Tỉnh)\s*/, "");
            return cityName;
          });
          // console.log(this.loadProvinces);
        })
        .catch(function (err) {
          console.error(err); // xử lý lỗi khi gọi API
        });
    },

    firstPage: function () {
      this.currentPage = 1;
      this.updatePagedItems();
      $window.scrollTo(0, 0); // Cuộn lên đầu trang
    },

    prevPage: function () {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.updatePagedItems();
        $window.scrollTo(0, 0); // Cuộn lên đầu trang
      }
    },

    nextPage: function () {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.updatePagedItems();
        $window.scrollTo(0, 0); // Cuộn lên đầu trang
      }
    },
    lastPage: function () {
      this.currentPage = this.totalPages;
      this.updatePagedItems();
      $window.scrollTo(0, 0); // Cuộn lên đầu trang
    },

    updatePagedItems: function () {

      this.totalPages = Math.ceil(this.loadData.length / this.itemsPerPage);
      var begin = (this.currentPage - 1) * this.itemsPerPage;
      var end = Math.min(begin + this.itemsPerPage, this.loadData.length);
      this.pagedItems = this.loadData.slice(begin, end);
    },
    calculateDiscount: function (priceSale, price) {
      if (price && priceSale) {
        var discount = 100 - (priceSale / price) * 100;
        if (discount > 0) {
          return discount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ","); // Định dạng giá trị số với dấu phẩy ngăn cách hàng nghìn
        }
      }
      return "";
    },

    SortProductBySoldCount: function () {
      if (selectedSoldCount.checked = true) {
        this.loadData.sort(function (a, b) {
          return b.soldCount - a.soldCount;
        });
        this.currentPage = 1;
        this.updatePagedItems();
      }
      // Cập nhật phân trang sau khi sắp xếp
      // console.log(selectedSoldCount);
    },
    SortProductByPrice: function () {
      var selectedPrice = document.getElementById("sortPrice").value;
      // this.loadData = this.temLoadData.slice(); // Tạo bản sao của mảng gốc
      switch (selectedPrice) {
        case "ASC": {
          this.loadData.sort(function (a, b) {
            return (
              a.productVariants[0].priceSale - b.productVariants[0].priceSale
            );
          });
          break;
        }
        case "DESC": {
          this.loadData.sort(function (a, b) {
            return (
              b.productVariants[0].priceSale - a.productVariants[0].priceSale
            );
          });
          break;
        }
        default: {
        }
      }
      this.updatePagedItems();
      // console.log(selectedPrice);
    },
    filterProductByMall: function () {

      // this.temLoadData = this.initialData.slice(); // initialData is the original  data
      if (selectedMall.checked == true) {
        this.loadData = this.temLoadData.filter(
          (item) => item.sellerProduct.mall === true
        );
        this.currentPage = 1;
        this.updatePagedItems(); // Cập nhật phân trang sau khi sắp xếp

        // first.addEventListener('click', function () {
        //   this.firstPage();
        // });
      }
    },

    getBrandAndCategory: function () {
      this.loadBrand = Array.from(
        new Set(
          this.initialData.map(function (item) {
            return item.productbrand.name;
          })
        )
      );
      this.loadCategory = Array.from(
        new Set(
          this.initialData.map(function (item) {
            return item.categoryDetails.name;
          })
        )
      );
    },
    searchFilter: function () {
      // Lấy giá trị của danh mục được chọn
      const selectedCategories = Array.from(
        document.querySelectorAll('input[name="category"]:checked')
      ).map((input) => input.value);

      // Lấy giá trị của tỉnh thành được chọn
      const selectedProvince = document.getElementById("provinces").value;

      // Lấy giá trị của thương hiệu được chọn
      const selectedBrands = Array.from(
        document.querySelectorAll('input[name="brand"]:checked')
      ).map((input) => input.value);

      // Lấy giá trị của giá từ và giá đến
      const priceFrom = document.querySelector('input[name="priceFrom"]').value;
      const priceTo = document.querySelector('input[name="priceTo"]').value;
      this.filterData(
        selectedCategories,
        selectedProvince,
        selectedBrands,
        priceFrom,
        priceTo
      );
    },
    filterData: function (categories, province, brands, priceFrom, priceTo) {
      this.loadData = this.initialData.filter((item) => {
        // Kiểm tra tiêu chí danh mục
        if (
          categories.length > 0 &&
          !categories.includes(item.categoryDetails.name)
        ) {
          return false;
        }

        // Kiểm tra tiêu chí nơi bán (tỉnh thành)
        if (
          province !== "Tỉnh Thành" &&
          !item.sellerProduct.address.includes(province)
        ) {
          return false;
        }

        // Kiểm tra tiêu chí thương hiệu
        if (brands.length > 0 && !brands.includes(item.productbrand.name)) {
          return false;
        }

        // Kiểm tra tiêu chí khoảng giá
        if (
          item.productVariants[0].priceSale < parseInt(priceFrom) ||
          item.productVariants[0].priceSale > parseInt(priceTo)
        ) {
          return false;
        }

        // Nếu không có điều gì cản trở, giữ lại mục này trong kết quả lọc
        return true;
      });
      $window.scrollTo(0, 0);
      this.updatePagedItems();
    },
    removeFilter: function () {
      //Mall and SoldCount
      document.getElementById("Mall").checked = false;
      document.getElementById("ASC_soldCount").checked = false;
      // Đặt lại giá trị của các checkbox
      const categoryCheckboxes = document.querySelectorAll(
        'input[name="category"]'
      );
      categoryCheckboxes.forEach((checkbox) => (checkbox.checked = false));

      document.getElementById("provinces").value = "Tỉnh Thành"; // Đặt tỉnh thành về giá trị mặc định

      const brandCheckboxes = document.querySelectorAll('input[name="brand"]');
      brandCheckboxes.forEach((checkbox) => (checkbox.checked = false));

      // Đặt lại giá trị của giá từ và giá đến
      document.querySelector('input[name="priceFrom"]').value = "";
      document.querySelector('input[name="priceTo"]').value = "";
      this.loadData = this.initialData.slice();
      this.updatePagedItems();
    },

    checkSearchNull: function () {
      if (this.temLoadData.length <= 0) {
        main.style = "flex-direction: column;justify-content: center; align-items: center;";
        main.innerHTML = "<i class= 'fs-1 mt-3 fa-solid fa-xmark '></i>";
        main.innerHTML += "<h4 class='mt-2'>Không tìm thấy kết quả nào ...</h4>";
        main.innerHTML += "<h4>Hãy thử sử dụng các từ khóa chung chung hơn</h4>";
        spinner.style.display = "none";
      }
    },
    loadSpinner: function () {
      if (this.temLoadData.length > 0) {
        spinner.style.display = "none";
      }
    }

  }); // end function search

  $search.loadProductSearch();

});
