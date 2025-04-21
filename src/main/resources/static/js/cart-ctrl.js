let host = "https://devex.io.vn/rest";
const app = angular.module("app", ["ngRoute"]);

app.config([
  "$locationProvider",
  function ($locationProvider) {
    $locationProvider.hashPrefix("");
  },
]);

app.controller("cart-ctrl", function ($scope, $http, $location, $window) {

  //format tiền cho đẹp
  $scope.formatMoney = function (x) {
    var money = "";
    money = x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    return money;
  };

  $scope.formatDateToDDMMYYYY = function (dateString) {
    const date = new Date(dateString); // Chuyển chuỗi thời gian thành đối tượng Date
    const options = { day: "numeric", month: "numeric", year: "numeric" };
    return date.toLocaleDateString("vi-VN", options); // Chuyển đổi sang "dd/MM/yyyy"
  };

  $scope.formatDateTimeToDDMMYYYYHHMM = function (dateString) {
    const date = new Date(dateString);
    const options = {
      day: "numeric",
      month: "numeric",
      year: "numeric",
      hour: "numeric",
      minute: "numeric",
    };
    return date.toLocaleDateString("vi-VN", options);
  };

  // tăng giảm số lượng
  $scope.changeQuantity = function (item, action) {
    if (action === "increase") {
      if (item.quantity == item.quantityInventory) {
        $scope.message = "Số lượng tồn kho không khả dụng!";
        $('#ModalOrderMessage').modal('show');
        return;
      }
      if (item.idFlashSale !== null) {
        if (item.quantity == item.quantitySale) {
          $scope.message = "Số lượng khả dụng Flash Sale không đủ!";
          $('#ModalOrderMessage').modal('show');
          item.price = item.cost;
        } else if (item.quantity == item.quantitySaleLimit) {
          $scope.message = "Sản phẩm Flash Sale này chỉ được đặt giới hạn " + item.quantitySaleLimit + "!";
          $('#ModalOrderMessage').modal('show');
          item.price = item.cost;
        }
      }
      if (item.quantity < 15) {
        item.quantity++;
      }
    } else if (action === "decrease") {
      if (item.quantity > 1) {
        item.quantity--;
      }
      if (item.idFlashSale !== null) {
        if (item.quantity == item.quantitySale) {
          item.price = item.priceSale;
        } else if (item.quantity == item.quantitySaleLimit) {
          item.price = item.priceSale;
        }
      }

    }

    // Gọi hàm changeQty để cập nhật giá trị trong giỏ hàng và cơ sở dữ liệu
    $scope.cart.changeQty(item.id, item.quantity);
  };


  var $voucher = ($scope.voucher = {
    items: [],
    itemsApplied: [],
    itemsAvailability: [],
    itemDetail: {},
    prodVoucher: [],

    async loadMyVoucher() {
      var url = `${host}/voucher/my-saved`;
      $http.get(url).then(async (response) => {
        this.items = response.data;
        console.log(this.items);
        await this.groupVoucherApplied();
        this.groupVoucherAvailability();
        console.log(this.itemsApplied);
        console.log(this.itemsAvailability);
        // console.log(this.myVoucher[0].voucher);
      });
    },

    openModalDetail: function (item) {
      this.itemDetail = item;
      this.prodVoucher = [];
      var url = `${host}/voucher/prod-voucher/${item.id}`;
      $http.get(url).then((response) => {
        if (response.data === null) {
          this.prodVoucher = null;
        } else {
          this.prodVoucher = response.data;
        }
        console.log(this.prodVoucher);
      });
      console.log(this.itemDetail);
      $("#showDetail").modal("show");
    },


    groupVoucherApplied() {
      this.itemsApplied = this.items.filter((voucher) =>
        $cart.isItemInMyVoucherApplied(voucher)
      );
    },

    groupVoucherAvailability() {
      this.itemsAvailability = this.items.filter((voucher) => {
        // Kiểm tra các điều kiện
        return (
          !$cart.isItemInMyVoucherApplied(voucher) &&
          new Date(voucher.endDate) > new Date() && // Kiểm tra endDate > hiện tại
          voucher.active === true &&
          voucher.quantity > 0
        );
      });
    },
  });

  $voucher.loadMyVoucher();

  // quản lý giỏ hàng
  var $cart = ($scope.cart = {
    items: [],
    itemsOrder: [],
    itemsOrderSession: [],
    shopGroups: {},
    shopGroupsOrder: {},
    selectAll: false,
    selectedShopIds: [],
    sizes: [],
    colors: [],
    selectedProduct: [],
    moneyShip: 0,
    //	Voucher
    voucherAll: [],
    voucherShop: [],
    voucherDevex: [],
    voucherShipping: [],
    voucherApply: [],
    myVoucher: [],
    myVoucherManage: [],
    prodVoucher: {},
    shopSelectedOpenVoucher: "",


    // voucher trong kho quản lý khách hàng

    // lưu voucher
    saveVoucher(item) {
      var url = `${host}/voucher/save`;
      $http.post(url, item).then((response) => {
        console.log(this.voucherAll);
        this.loadVoucherOfUser();
      });
    },

    // áp dụng voucher
    applyVoucher(item) {
      this.voucherApply.push(item);
      console.log(this.voucherApply);
    },

    // cancel voucher shop
    cancelVoucherShop() {
      // Sử dụng hàm filter để tạo một mảng mới chứa các voucher không có item.creator giống với item
      this.voucherApply = this.voucherApply.filter(
        (voucher) => voucher.creator.username !== this.shopSelectedOpenVoucher
      );
      console.log(this.voucherApply);
      $("#voucherOfShop").modal("hide");
    },

    // cancel voucher devex
    cancelVoucherDevex() {
      // Sử dụng hàm filter để tạo một mảng mới chứa các voucher devex không
      this.voucherApply = this.voucherApply.filter(
        (voucher) =>
          voucher.categoryVoucher.id !== 100001 &&
          voucher.categoryVoucher.id !== 100002
      );
      console.log(this.voucherApply);
      $("#voucherOfDevex").modal("hide");
    },

    // mở modal voucher của shop
    openModalVoucherShop: function (idShop) {
      this.groupVoucherShop(idShop);

      this.shopSelectedOpenVoucher = idShop;

      console.log(this.shopSelectedOpenVoucher);
      console.log(idShop);
      $("#voucherOfShop").modal("show");
    },

    // mở modal voucher của devex
    openModalVoucherDevex: function () {
      this.groupVoucherDevex();
      $("#voucherOfDevex").modal("show");
    },

    // load voucher
    loadVoucherOfUser() {
      var url = `${host}/voucher/saved/list`;
      $http.get(url).then((response) => {
        this.myVoucher = response.data;
        console.log(this.myVoucher);
        // console.log(this.myVoucher[0].voucher);
      });
    },


    loadProdVoucher() {
      this.prodVoucher = {};
      var dataProdVoucher = [];
      var url = `${host}/voucher/prod-voucher/all`;
      $http.get(url).then((response) => {
        if (response.data === null) {
          dataProdVoucher = null;
        } else {
          dataProdVoucher = response.data;
          dataProdVoucher.forEach((item) => {
            if (!this.prodVoucher[item.voucher.creator.username]) {
              this.prodVoucher[item.voucher.creator.username] = [];
            }
            this.prodVoucher[item.voucher.creator.username].push(item);
          });
        }
        console.log(this.prodVoucher);
      });
    },

    // check voucher có sở hữu hay chưa
    isItemInMyVoucher(item) {
      return this.myVoucher.some((voucher) => voucher.voucher.id === item.id);
    },


    // check voucher có đang được apply không
    isItemInVoucherClicked(item) {
      if (this.voucherApply.indexOf(item) !== -1) {
        return true;
      } else {
        return false;
      }
    },

    //	check hiển thị nút sử dụng voucher Ship
    isShowApplyButtonShip(item) {
      if (!this.isItemInMyVoucher(item)) {
        return true;
      }
      if ($cart.amountDetail < item.minPrice) {
        return true
      }
      if (this.isShipVoucherApplied()) {
        return true;
      }
      if (this.isItemInVoucherClicked(item)) {
        return true;
      } else {
        return false;
      }
    },

    //	check hiển thị nút sử dụng voucher Devex
    isShowApplyButtonDevex(item) {
      if (!this.isItemInMyVoucher(item)) {
        return true;
      }
      if (this.amountDetail < item.minPrice) {
        return true
      }
      if (this.isDevexVoucherApplied()) {
        return true;
      }
      if (this.isItemInVoucherClicked(item)) {
        return true;
      } else {
        return false;
      }
    },

    //	check hiển thị nút sử dụng voucher
    isShowApplyButtonShop(item) {
      //kiểm tra người đang sở hữu voucher chưa
      if (!this.isItemInMyVoucher(item)) {
        return true;
      }
      //kiểm tra sản phẩm đó có được áp dụng mã ko
      if (!this.isItemInProdVoucher(item)) {
        return true;
      }
      //kiểm tra shop đó có voucher đc app mã chưa
      if (this.isShopVoucherApplied(item.creator.username)) {
        return true;
      }
      //kiểm tra voucher có click hay chưa
      if (this.isItemInVoucherClicked(item)) {
        return true;
      } else {
        return false;
      }
    },

    //check sản phẩm đó có được áp dụng voucher hay không
    isItemInProdVoucher(item) {
      let flag = false; // Tạo một biến để lưu trạng thái kiểm tra
      this.prodVoucher[item.creator.username].forEach(function (i) {
        $cart.shopGroupsOrder[item.creator.username].forEach(function (prod) {
          if (i.voucher.id === item.id && prod.idProduct === i.product.id && prod.price >= item.minPrice) {
            flag = true;
          }
        });
      });
      return flag;
    },

    //check voucher đã được sử dụng hay chưa
    isItemInMyVoucherApplied(item) {
      return this.myVoucher.some(
        (voucher) => voucher.voucher.id === item.id && !voucher.applied
      );
    },

    //check mỗi shop chỉ đc app 1 voucher
    isShopVoucherApplied(idShop) {
      return this.voucherApply.some(
        (voucher) => voucher.creator.username === idShop
      );
    },

    //check mỗi shop chỉ đc app 1 voucher
    isDevexVoucherApplied() {
      return this.voucherApply.some(
        (voucher) => voucher.categoryVoucher.id === 100001
      );
    },

    //check mỗi shop chỉ đc app 1 voucher
    isShipVoucherApplied() {
      return this.voucherApply.some(
        (voucher) => voucher.categoryVoucher.id === 100002
      );
    },

    // load voucher
    loadVoucher() {
      var url = `${host}/voucher/list`;
      $http.get(url).then((response) => {
        this.voucherAll = response.data;
        console.log(this.voucherAll);
      });
    },

    sortCreatedDateAllItem(array) {
      // Sắp xếp danh sách theo ngày tạo mới nhất (giảm dần)
      return array.sort((a, b) => {
        const dateA = new Date(a.createdDay);
        const dateB = new Date(b.createdDay);
        return dateB - dateA;
      });
    },

    // group voucher của shop đang diễn ra
    groupVoucherShop(idShop) {
      const currentDate = new Date(); // Lấy ngày hiện tại

      // Lọc danh sách các voucher đang diễn ra
      this.voucherShop = this.voucherAll.filter((item) => {
        const startDate = new Date(item.startDate);
        const endDate = new Date(item.endDate);
        return (
          startDate <= currentDate &&
          currentDate <= endDate &&
          item.active === true &&
          item.quantity > 0 &&
          item.creator.username === idShop
        );
      });

      this.sortCreatedDateAllItem(this.voucherShop);

      console.log(this.voucherShop);
    },

    // group voucher của devex
    groupVoucherDevex() {
      const currentDate = new Date(); // Lấy ngày hiện tại

      // Lọc danh sách các voucher devex đang diễn ra
      this.voucherDevex = this.voucherAll.filter((item) => {
        const startDate = new Date(item.startDate);
        const endDate = new Date(item.endDate);
        return (
          startDate <= currentDate &&
          currentDate <= endDate &&
          item.active === true &&
          item.quantity > 0 &&
          item.categoryVoucher.id === 100001
        );
      });

      this.sortCreatedDateAllItem(this.voucherDevex);

      // Lọc danh sách các voucher ship đang diễn ra
      this.voucherShipping = this.voucherAll.filter((item) => {
        const startDate = new Date(item.startDate);
        const endDate = new Date(item.endDate);
        return (
          startDate <= currentDate &&
          currentDate <= endDate &&
          item.active === true &&
          item.quantity > 0 &&
          item.categoryVoucher.id === 100002
        );
      });

      this.sortCreatedDateAllItem(this.voucherShipping);

      console.log(this.voucherDevex);
    },

    // Mở Modal chọn size và màu
    openModal: function (product) {
      //reset lại trạng thái của radio
      $scope.cart.selectedSize = null;
      $scope.cart.selectedColor = null;
      this.selectedProduct = product; // Sao chép sản phẩm để không ảnh hưởng đến sản phẩm gốc
      var url = `${host}/cart/color/${product.idProduct}`;
      $http.get(url).then((response) => {
        if (response.data === null) {
          this.colors = null;
        } else {
          this.colors = response.data;
        }
        this.selectedProduct.colors = this.colors;
        console.log("độ dài mảng màu là ", this.selectedProduct.colors.length);
      });
      // Khai báo danh sách size và color tương ứng với sản phẩm, ví dụ:
      var url = `${host}/cart/size/${product.idProduct}`;
      $http.get(url).then((response) => {
        if (response.data === null) {
          this.sizes = null;
        } else {
          this.sizes = response.data;
        }
        this.selectedProduct.sizes = this.sizes;
        console.log("độ dài mảng size là ", this.selectedProduct.sizes.length);
      });
      console.log(product.idProduct);

      $("#myModal").modal("show");
    },

    saveSelection: function () {
      let cartDetailPd = this.selectedProduct;

      if (
        $scope.cart.selectedSize === undefined ||
        $scope.cart.selectedSize === null
      ) {
        $scope.cart.selectedSize = cartDetailPd.size;
      }
      if (
        $scope.cart.selectedColor === undefined ||
        $scope.cart.selectedColor === null ||
        this.selectedProduct.colors.length === 1
      ) {
        $scope.cart.selectedColor = cartDetailPd.color;
      }
      console.log("Tên sp sau khi lưu: ", cartDetailPd.idProduct);
      console.log("Size được chọn:", $scope.cart.selectedSize);
      console.log("Màu được chọn:", $scope.cart.selectedColor);
      console.log(cartDetailPd);
      this.selectedSize = $scope.cart.selectedSize;
      this.selectedColor = $scope.cart.selectedColor;
      var url = `${host}/cart/changeSizenColor/${cartDetailPd.idProduct}?cartDetailId=${cartDetailPd.id}`;
      var data = {
        size: $scope.cart.selectedSize,
        color: $scope.cart.selectedColor,
      };
      $http.put(url, data).then((response) => {
        this.loadProductCart();
        console.log("Success", response);
      });
      $("#myModal").modal("hide");
    },
    hideModal: function () {
      $("#myModal").modal("hide");
    },
    // load sản phẩm trong giỏ hàng
    loadProductCart() {
      var url = `${host}/cart`;
      $http.get(url).then((response) => {
        this.items = response.data;
        this.groupByShopId();
        this.groupByOrders();
        console.log(this.items);
        console.log(this.shopGroups);
        console.log(this.shopGroupsOrder);
        console.log(this.count);
      });
    },

    // Thay đổi số lượng sản phẩm trong giỏ hàng khi người dùng thay đổi giá trị số lượng
    changeQty(id, qty) {
      var item = this.items.find((item) => item.id == id);
      if (item) {
        item.quantity = qty;
      }
      console.log(id);
      console.log(item.id);
      // Gọi API để cập nhật  sản phẩm trong cơ sở dữ liệu
      var url = `${host}/cart/${id}`;
      $http
        .put(url, item)
        .then((resp) => {
          // Xử lý phản hồi từ server nếu cần thiết
          // var index = this.items.findIndex((item) => item.id == id);
          this.items[index] = resp.data;
          console.log("Success", resp);
          //					 this.loadProductCart();
          //					this.countItemOrder();
          //					this.amount();
        })
        .catch(function (error) {
          // Xử lý lỗi nếu có
          console.error("Lỗi khi cập nhật số lượng sản phẩm:", error);
        });
    },

    remove(id) {
      // xóa sản phẩm khỏi giỏ hàng
      // Gọi API để xóa sản phẩm khỏi cơ sở dữ liệu
      var url = `${host}/cart/${id}`;
      $http
        .delete(url)
        .then((resp) => {
          // Xử lý phản hồi từ server nếu cần thiết
          // Sau khi xoá thành công, cập nhật lại danh sách sản phẩm trong giỏ hàng trên frontend
          // this.items = this.items.filter((item) => item.id !== id);
          console.log("Success", resp);
          this.itemsOrder = this.itemsOrder.filter((item) => item.id !== id);
          this.loadProductCart();
          // this.amount();
        })
        .catch((error) => {
          console.log("Lỗi khi xoá sản phẩm khỏi giỏ hàng:", error);
        });
    },

    removeAllOfShop(idShop) {
      // Gọi API để cập nhật giỏ hàng trong cơ sở dữ liệu
      var url = `${host}/cart/shop/${idShop}`;
      $http
        .delete(url)
        .then((resp) => {
          // Xử lý phản hồi từ server nếu cần thiết
          // this.items = response.data;
          this.itemsOrder = this.itemsOrder.filter(
            (item) => item.idShop !== idShop
          );
          console.log("Success", resp);
          this.loadProductCart();
        })
        .catch(function (error) {
          // Xử lý lỗi nếu có
          console.error(
            "Lỗi khi xoá tất cả sản phẩm của shop khỏi giỏ hàng:",
            error
          );
        });
    },
    clear() {
      // Xóa sạch các mặt hàng trong giỏ
      this.clearAllItems(); // Gán mảng rỗng để xóa sạch các mặt hàng trong giỏ hàng
      // Gọi API để cập nhật giỏ hàng trong cơ sở dữ liệu
      var url = `${host}/cart`;
      $http
        .delete(url)
        .then((resp) => {
          // Xử lý phản hồi từ server nếu cần thiết
          this.items = response.data;
          console.log("Success", resp);
          this.loadProductCart();
        })
        .catch(function (error) {
          // Xử lý lỗi nếu có
          console.error("Lỗi khi xóa sạch các mặt hàng trong giỏ hàng:", error);
        });
    },

    clearAllItems() {
      this.items = [];
      this.shopGroups = {};
      this.itemsOrder = [];
    },

    amt_of(item) {
      // tính thành tiền của 1 sản phẩm
      return item.price * item.quantity;
    },

    checkPrice(item) {
      if (item.idFlashSale !== null && item.quantity <= item.quantitySale && item.quantity <= item.quantitySaleLimit) {
        return item.price;
      } else {
        item.price = item.cost;
        return item.cost;
      }
    },

    //check số lượng đặt có lố sl tồn kho không
    checkQtyToBuy() {
      return this.itemsOrder.some((item) => item.quantity > item.quantityInventory);
    },

    get count() {
      // tính tổng số lượng các mặt hàng trong giỏ
      return this.items
        .map((item) => item.quantity)
        .reduce((total, qty) => (total += qty), 0);
    },

    get countItem() {
      return this.items.length;
    },

    get countItemOrder() {
      // tính tổng số lượng các mặt hàng trong giỏ
      return this.itemsOrder
        .map((item) => item.quantity)
        .reduce((total, qty) => (total += qty), 0);
    },

    get countItemOrderDetail() {
      // lấy sản phẩm từ session
      //			this.itemsOrderSession = JSON.parse(sessionStorage.getItem('itemsOrder')) || [];
      // tính tổng số lượng các mặt hàng trong giỏ
      return this.itemsOrderSession
        .map((item) => item.quantity)
        .reduce((total, qty) => (total += qty), 0);
    },


    get amount() {
      // tổng thành tiền các mặt hàng trong giỏ
      return this.itemsOrder
        .map((item) => this.amt_of(item))
        .reduce((total, amt) => (total += amt), 0);
    },

    get amountDetail() {
      // lấy sản phẩm từ session
      //			this.itemsOrderSession = JSON.parse(sessionStorage.getItem('itemsOrder')) || [];
      // tổng thành tiền các mặt hàng trong giỏ
      const idShops = Object.keys($cart.shopGroupsOrder);
      let total = 0;
      idShops.forEach((id) => {
        total += $cart.amountItemShop(id);
      });
      return total;
      // return this.itemsOrderSession
      //   .map((item) => this.amt_of(item))
      //   .reduce((total, amt) => (total += amt), 0);
    },

    countItemShop(idShop) {
      // tính tổng số lượng các mặt hàng trong shop
      return this.shopGroupsOrder[idShop].length;
    },



    amountItemShop(idShop) {
      // tính tiền shop
      let totalShop = this.shopGroupsOrder[idShop].map((item) => this.amt_of(item)).reduce((total, amt) => (total += amt), 0);
      if (this.isShopVoucherApplied(idShop)) {
        var voucherProd = this.voucherApply.find((item) => item.categoryVoucher.id == 100004);
        if (voucherProd != null) {
          let price = 0;
          this.prodVoucher[idShop].forEach(function (i) {
            $cart.shopGroupsOrder[idShop].forEach(function (prod) {
              if (i.voucher.id === voucherProd.id && prod.idProduct === i.product.id) {
                price += prod.price;
              }
            });
          });
          //xử lí giá giảm
          if (voucherProd.discount < 1) {
            totalShop -= (price * voucherProd.discount);

          } else {
            price -= voucherProd.discount;
            if (price <= 0) {
              price = 0;
            }
            totalShop -= price;
          }
        }

        var voucherShop = this.voucherApply.find((item) => item.categoryVoucher.id == 100003);
        if (voucherShop != null) {
          if (voucherShop.discount < 1) {
            totalShop -= (totalShop * voucherShop.discount);
          } else {
            totalShop -= voucherShop.discount;
            if (totalShop <= 0) {
              totalShop = 0;
            }
          }
        }

      }

      return totalShop;
    },


    get amountShip() {
      // tính tiền ship
      const numberOfShops = Object.keys($cart.shopGroupsOrder).length;
      this.moneyShip = 15000 * numberOfShops;
      if (this.isShipVoucherApplied()) {
        var voucher = this.voucherApply.find((item) => item.categoryVoucher.id == 100002);
        if (voucher.discount < 1) {
          this.moneyShip -= (this.moneyShip * voucher.discount);
        } else {
          this.moneyShip -= voucher.discount;
        }
      }

      return this.moneyShip;
    },

    get amountPay() {
      // tính tiền phải thanh toán
      let total = this.amountDetail;
      if (this.isDevexVoucherApplied()) {
        var voucher = this.voucherApply.find((item) => item.categoryVoucher.id == 100001);
        if (voucher.discount < 1) {
          total -= (total * voucher.discount);
        } else {
          total -= voucher.discount;
        }
      }

      return this.amountShip + total;
    },

    isItemChecked(id) {
      return this.itemsOrder.some((item) => item.id === id);
    },

    getCountItemsByShopId(shopId) {
      // Lọc ra các item có cùng shopId
      const itemsWithSameShopId = this.itemsOrder.filter(
        (item) => item.idShop === shopId
      );

      // Tính tổng số lượng của các item đã lọc
      const totalQuantity = itemsWithSameShopId.length;
      console.log(totalQuantity);
      return totalQuantity;
    },

    //check sp để thanh toán
    toggleItemOrder(id) {
      var index = this.itemsOrder.findIndex((item) => item.id == id);
      if (index !== -1) {
        var indexExtra = this.selectedShopIds.indexOf(
          this.itemsOrder[index].idShop
        );
        this.selectedShopIds.splice(indexExtra, 1);
        console.log(indexExtra);
        console.log(this.selectedShopIds);
        this.itemsOrder.splice(index, 1);
        this.selectAll = false; // Bỏ chọn "Chọn tất cả" nếu có ít nhất một sản phẩm không được chọn
      } else {
        var item = this.items.find((item) => item.id == id);
        if (item) {
          this.itemsOrder.push(item);
          console.log(this.shopGroups[item.idShop].length);
          if (
            this.shopGroups[item.idShop].length ===
            this.getCountItemsByShopId(item.idShop)
          ) {
            this.selectedShopIds.push(item.idShop);
          }
        }
      }

      console.log(this.items.length);
      console.log(this.itemsOrder.length);
      console.log(this.items);
      console.log(this.itemsOrder);
      if (this.items.length === this.itemsOrder.length) {
        this.selectAll = true;
      }
    },

    toggleSelectAll() {
      this.selectAll = !this.selectAll;
      if (this.selectAll) {
        this.itemsOrder = this.items;
        console.log(this.itemsOrder);
        this.selectedShopIds = Object.keys(this.shopGroups);
        console.log(this.selectedShopIds);
      } else {
        this.itemsOrder = [];
        this.selectedShopIds = [];
      }
    },

    toggleShopSelect(shopId) {
      var index = this.selectedShopIds.indexOf(shopId);
      if (index !== -1) {
        this.selectedShopIds.splice(index, 1);
        this.itemsOrder = this.itemsOrder.filter(
          (item) => item.idShop !== shopId
        );
        this.selectAll = false;
      } else {
        this.selectedShopIds.push(shopId);
        this.itemsOrder = this.itemsOrder.concat(
          this.items.filter((item) => item.idShop === shopId)
        );
        console.log(this.itemsOrder);
        if (
          this.selectedShopIds.length === Object.keys(this.shopGroups).length
        ) {
          this.selectAll = true;
        }
      }
    },

    groupByOrders() {
      //			sessionStorage.setItem('itemsOrder', JSON.stringify($cart.itemsOrder));
      this.shopGroupsOrder = {};
      this.itemsOrderSession =
        JSON.parse(sessionStorage.getItem("itemsOrder")) || [];
      this.itemsOrderSession.forEach((item) => {
        if (!this.shopGroupsOrder[item.idShop]) {
          this.shopGroupsOrder[item.idShop] = [];
        }
        this.shopGroupsOrder[item.idShop].push(item);
      });
    },

    groupByShopId() {
      this.shopGroups = {};
      this.items.forEach((item) => {
        if (!this.shopGroups[item.idShop]) {
          this.shopGroups[item.idShop] = [];
        }
        this.shopGroups[item.idShop].push(item);
      });

      // Sắp xếp các sản phẩm trong từng cụm theo CreatedDay giảm dần
      for (const shopId in this.shopGroups) {
        if (this.shopGroups.hasOwnProperty(shopId)) {
          this.shopGroups[shopId].sort((a, b) => {
            // Sử dụng Unix timestamp để so sánh ngày
            const dateA = new Date(a.createdDay).getTime();
            const dateB = new Date(b.createdDay).getTime();
            return dateB - dateA; // Sắp xếp giảm dần
          });
        }
      }
      // Chuyển đối tượng thành mảng các cặp key-value
      const objArray = Object.entries(this.shopGroups);
      // Sắp xếp mảng objArray dựa trên trường createdDay giảm dần
      objArray.sort((a, b) => {
        const dateA = new Date(a[1][0].createdDay).getTime();
        const dateB = new Date(b[1][0].createdDay).getTime();
        return dateB - dateA;
      });
      // Chuyển lại thành đối tượng từ mảng objArray đã sắp xếp
      this.shopGroups = Object.fromEntries(objArray);
    },
  });

  $cart.loadProductCart();
  $cart.loadVoucher();
  $cart.loadVoucherOfUser();
  $cart.loadProdVoucher();
  // Đặt hàng
  $scope.message = "";

  $scope.checkAndPerformAction = function () {
    var userAddress = document.getElementById("userAddress").value;
    var userPhoneAddress = document.getElementById("userPhoneAddress").value;
    if ($cart.itemsOrder.length === 0) {
      this.message = "Vui lòng chọn sản phẩm muốn mua!";
      $("#ModalOrderMessage").modal("show");
    } else if ($cart.checkQtyToBuy()) {
      this.message = "Số lượng tồn kho không đủ để đặt hàng!";
      $("#ModalOrderMessage").modal("show");
    } else if (userAddress === "" || userPhoneAddress === "") {
      this.message = "Vui lòng cung cấp thông tin địa chỉ của bạn!";
      $("#ModalOrderMessage").modal("show");
    } else {
      sessionStorage.setItem("itemsOrder", JSON.stringify($cart.itemsOrder));
      // Sử dụng $location để chuyển hướng đến URL '/detail-order'
      $window.location.href = "/cart/detail-order";
    }
  };
  //*BEGIN FLASHSALE
  $scope.dataTime = [];
  $scope.getTimeFlashSale = function () {
    var countdownButton = document.getElementById("countdown-btn");
    var minuteButton = document.getElementById("minute-btn");
    var secondButton = document.getElementById("second-btn");

    $http
      .get("/rest/getTimeFlashSale")
      .then(function (response) {
        var data = response.data;

        var firstTime = new Date(data.firstTime);
        var lastTime = new Date(data.lastTime);
        var currentTime = new Date();

        var remainingMilliseconds = lastTime - currentTime;
        var remainingHours = Math.floor(remainingMilliseconds / 3600000);
        if (remainingHours < 10) {
          remainingHours = "0" + remainingHours;
        }

        var remainingMinutes = Math.floor(
          (remainingMilliseconds % 3600000) / 60000
        );
        if (remainingMinutes < 10) {
          remainingMinutes = "0" + remainingMinutes;
        }
        var remainingSeconds = Math.floor(
          (remainingMilliseconds % 60000) / 1000
        );
        if (remainingSeconds < 10) {
          remainingSeconds = "0" + remainingSeconds;
        }

        $scope.countdownString =
          padNumber(remainingHours) +
          ":" +
          padNumber(remainingMinutes) +
          ":" +
          padNumber(remainingSeconds);

        countdownButton.innerHTML = remainingHours;
        minuteButton.innerHTML = remainingMinutes;
        secondButton.innerHTML = remainingSeconds;

        // Gọi lại hàm sau mỗi giây
        setTimeout($scope.getTimeFlashSale, 1000);
      })
      .catch(function (err) {
        console.error(err); // Xử lý lỗi khi gọi API
        // alert('Có lỗi xảy ra khi gọi API');
      });

    function padNumber(number) {
      return number.toString().padStart(2, "0");
    }
  };

  $scope.getTimeFlashSale();
  //!END FLASHSALE



  $scope.payment = "cash";
  $scope.publicKey_sender = ""; // public key người dùng
  $scope.publicKey_recipient = "A5jsvxmGpe2sj3G1aBv6iiF5jNQ9nfujTJhzSuJ74ozd"; // public key người nhận
  $scope.exchangeRateSOLUSD = 0; // giá trị hiện tại của Sol (USD)
  $scope.exchangeRateUSDVND = 0; // giá trị hiện tại của Sol (USD)
  $scope.amountPayBySol = 0; // số sol cần thanh toán 
  $scope.wallet = [];
  $scope.statusPayment = false; //
  // AUTO Connect to Wallet Phantom //
  (async () => {
    await window.phantom.solana.connect();
    $scope.publicKey_sender = await window.phantom.solana.publicKey.toBase58();
    console.log($scope.publicKey_sender);
  })();
  //=== Get Price Solana =================
  $scope.getSolanaPrice = () => {
    $http.get('https://api.coingecko.com/api/v3/simple/price?ids=solana&vs_currencies=usd')
      .then(function (response) {
        // Xử lý phản hồi thành công
        $scope.exchangeRateSOLUSD = response.data.solana.usd; // Lấy giá trị của SOL trong USD
        // console.log(this.exchangeRateSOLUSD)
        // console.log("exchangeRateSOLUSD: " + exchangeRateSOLUSD);
      })
      .catch(function (error) {
        // Xử lý phản hồi lỗi
        console.error('Đã xảy ra lỗi khi lấy giá của Solana:', error.message);
      });
  };
  //=== Get Price USD to VND=================
  $scope.getUSD_toVND = () => {
    $http.get('https://api.coingecko.com/api/v3/simple/price?ids=usd&vs_currencies=vnd')
      .then(function (response) {
        // Xử lý phản hồi thành công
        $scope.exchangeRateUSDVND = response.data.usd.vnd;
        // console.log("USD_toVND: " + exchangeRateUSDVND);
      })
      .catch(function (error) {
        // Xử lý phản hồi lỗi
        console.error('Đã xảy ra lỗi khi lấy giá của USD:', error.message);
      });
  };
  $scope.getSolanaPrice();
  $scope.getUSD_toVND();

  // $scope.toTransactions = (encodedTransaction) =>
  //   solanaWeb3.Transaction.from(Uint8Array.from(atob(encodedTransaction), c => c.charCodeAt(0)));
  // console.log($scope.toTransactions());
  // console.log(solanaWeb3)
  // == Test end codeTransactions == 
  // Hàm kiểm tra tính hợp lệ của chuỗi Base64
  function isBase64(str) {
    try {
      return btoa(atob(str)) === str;
    } catch (e) {
      return false;
    }
  }

  // // Hàm giải mã chuỗi Base64 thành đối tượng giao dịch Solana
  // $scope.toTransactions = (encodedTransaction) => {
  //   // Kiểm tra tính hợp lệ của chuỗi đầu vào
  //   // if (!isBase64(encodedTransaction)) {
  //   //   throw new Error('Invalid Base64 string');
  //   // }

  //   // Giải mã chuỗi Base64 thành mảng bytes
  //   const decodedBytes = Uint8Array.from(atob(encodedTransaction), c => c.charCodeAt(0));
  //   console.log("decodeTransactions: " + decodedBytes);
  //   // Tạo đối tượng giao dịch Solana từ mảng bytes
  //   return solanaWeb3.Transaction.from(decodedBytes);
  // };
  // end test 


  // $scope.sendSol = async (amount, publicKey) => {

  //   var myHeaders = new Headers();
  //   myHeaders.append("x-api-key", "-h5nBPVh3pIsuGRE"); // lấy API bên Shyft để thực hiện viẹc mint NFT
  //   myHeaders.append("Content-Type", "application/json");

  //   var raw = JSON.stringify(
  //     {
  //       "network": "devnet",
  //       "from_address": publicKey, // lấy từ windown.phantom để trả về id wallet
  //       "to_address": $scope.publicKey_recipient, // chuyển đến mặc định là tài khoản admin
  //       "amount": amount,
  //     }
  //   );

  //   var requestOptions = {
  //     method: 'POST',
  //     headers: myHeaders,
  //     body: raw,
  //     redirect: 'follow',
  //   };

  //   fetch("https://api.shyft.to/sol/v1/wallet/send_sol", requestOptions)
  //     .then(async response => {
  //       // alert("Thanh toán")
  //       let res = await response.json();
  //       $scope.statusPayment = res.success;
  //       console.log(res);
  //       console.log($scope.statusPayment);
  //       console.log("endcodeTransaction: " + res.result.encoded_transaction);
  //       // Giải mã encoded_transaction thành mảng byte
  //       const encodedTransaction = res.result.encoded_transaction;
  //       if (!isBase64(encodedTransaction)) {
  //         throw new Error('Invalid Base64 string');
  //       }
  //       const byteArray = Uint8Array.from(atob(encodedTransaction), c => c.charCodeAt(0));
  //       // Tạo đối tượng giao dịch Solana từ mảng byte
  //       const transaction = solanaWeb3.Transaction.from(byteArray);
  //       const connection = new solanaWeb3.Connection("https://api.devnet.solana.com");
  //       // Lấy recent blockhash từ mạng Solana
  //       const recentBlockhash = await connection.getRecentBlockhash();
  //       const signedTransaction = await window.phantom.solana.signTransaction(transaction);
  //       const signature = await connection.sendRawTransaction(signedTransaction.serialize());
  //       // const signature = await connection.getSignatureStatuses(signedTransaction);
  //       // if ($scope.statusPayment) {
  //       //   sessionStorage.setItem('payment', 'sol');
  //       //   this.message = "Đặt hàng thành công!";
  //       //   window.location.href = 'https://devex.io.vn/order/success';

  //       // } else {
  //       //   alert(" Orders failed !!!")
  //       // }

  //     })
  //     .catch(error => console.log('error', error));
  // };


  //*BEGIN THANH TOAN
  $scope.payment = "cash";
  $scope.purchase = function () {
    // Thực hiện đặt hàng
    const requestDataDTO = {
      itemsOrderSession: $cart.itemsOrderSession,
      voucherApply: $cart.voucherApply,
      total: $cart.amountPay,
    };
    var url = `${host}/cart/order`;
    $http
      .post(url, requestDataDTO)
      .then((resp) => {
        //				alert("Đặt hàng thành công!");
        this.message = "Đặt hàng thành công!";
        $('#ModalOrderMessage').modal('show');
        console.log(resp);
        $cart.selectAll = true;
        $cart.toggleSelectAll();
        $cart.loadProductCart();
        //				sessionStorage.removeItem('itemsOrder');
        var form = document.createElement("form");
        form.method = "POST";
        console.log($scope.payment);
        // if ($scope.payment === "sol") {
        //   alert("Payment by Solana")
        //   console.log("Số tiền cần thanh toán: " + ($cart.amountPay / $scope.exchangeRateUSDVND).toFixed(2));
        //   /** Gọi phương thức thang toán bằng SOL ở đây */
        //   $scope.amountPayBySol = Number(($cart.amountPay / $scope.exchangeRateUSDVND / $scope.exchangeRateSOLUSD).toFixed(3));
        //   $scope.sendSol($scope.amountPayBySol, $scope.publicKey_sender);
        //   from.action = "#";

        // } else if ($scope.payment === "paypal") {
        //   form.action = "/paypal-payment"; // Thay thế bằng URL tương ứng
        // } else if ($scope.payment === "vnpay") {
        //   form.action = "/submitOrder"; // Thay thế bằng URL tương ứng
        // } else {
        //   form.action = "/cash-payment"; // Thay thế bằng URL tương ứng
        // }
        if ($scope.payment === "ACB") {
          // alert("acb")
          form.action = "/acb-payment";
        } else if ($scope.payment === "paypal") {
          form.action = "/paypal-payment"; // Thay thế bằng URL tương ứng
        } else if ($scope.payment === "vnpay") {
          form.action = "/submitOrder"; // Thay thế bằng URL tương ứng
        } else {
          form.action = "/cash-payment"; // Thay thế bằng URL tương ứng
        }
        // Thêm form vào trang web và gửi POST request
        document.body.appendChild(form);
        form.submit();
      })
      .catch((error) => {
        this.message = "Lỗi khi đặt hàng!";
        $('#ModalOrderMessage').modal('show');
        console.log(error);
      });
  };
  //!END THANH TOAN

  $scope.info = [];
  $scope.fillAmountOrderAndFollow = function () {
    $http.get('/api/user/info').then(resp => {
      $scope.info = resp.data;
    }).catch(function (err) {
      console.error(err);
    });
  };



  $scope.fillAmountOrderAndFollow();

});
