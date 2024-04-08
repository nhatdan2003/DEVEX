const app = angular.module("app", []);
app.controller("myController", function ($scope, $http) {
  let table;
  $scope.data = [];
  $scope.flashSalesTime = function () {
    $http
      .get("/api/flashSales")
      .then((resp) => {
        $scope.data = resp.data;
        console.log($scope.data)
        initTableData($scope.data);
      })
      .catch(function (err) {
        console.error(err); // xử lý lỗi khi gọi API
        // alert('Có lỗi xảy ra khi gọi API'); // hiển thị thông báo lỗi cho người dùng
      });
  };

  $scope.flashSalesTime(); // lấy dữ

  function initTableData(data) {
    // Kiểm tra xem DataTable đã được khởi tạo chưa
    if ($.fn.dataTable.isDataTable("#FlashTime")) {
      // Nếu DataTable đã tồn tại, hủy bỏ nó trước
      $("#FlashTime").DataTable().destroy();
    }
    // Khởi tạo DataTable mới với dữ liệu mới
    table = $("#FlashTime").DataTable({
      processing: true,
      data: $scope.data,
      language: {
        url: "https://cdn.datatables.net/plug-ins/1.10.22/i18n/Vietnamese.json"
      },
      columns: [{ data: "id" }, { data: "firstTime" }, { data: "lastTime" }], "order": [[1, "desc"]],
    });
  }

  $scope.saveData = function () {
    let message = document.getElementById("message-validation");
    var today = new Date();
    var dateToday = (today.getMonth() + 1) + '/' + today.getDate() + '/' + today.getFullYear();
    let date = new Date($scope.formData.date);
    let firstTime = new Date($scope.formData.firstTime);
    let lastTime = new Date($scope.formData.lastTime);
    let checkExist = true;
    let checkDate = true;
    let dateFormatted =
      date.getMonth() + 1 + "/" + date.getDate() + "/" + date.getFullYear();
    let firstTimeFormatted =
      (firstTime.getHours() > 12
        ? firstTime.getHours() - 12
        : firstTime.getHours()) +
      ":" +
      (firstTime.getMinutes() < 10 ? "0" : "") +
      firstTime.getMinutes() +
      (firstTime.getHours() >= 12 ? " PM" : " AM");
    let lastTimeFormatted =
      (lastTime.getHours() > 12
        ? lastTime.getHours() - 12
        : lastTime.getHours()) +
      ":" +
      (lastTime.getMinutes() < 10 ? "0" : "") +
      lastTime.getMinutes() +
      (lastTime.getHours() >= 12 ? " PM" : " AM");

    // Dữ liệu để gửi lên server
    let dataToSend = {
      firstTime: firstTimeFormatted + "" + dateFormatted,
      lastTime: lastTimeFormatted + "" + dateFormatted,
    };

    // console.log('test' + dateToday);
    // console.log('test' + dateFormatted);
    // console.log('test' + Date(date).getDate());
    // kiểm tra thời gian bắt đầu có tồn tại trong khoảng thời gian 
    let firstTimeDate = new Date(dataToSend.firstTime);
    let lastTimeDate = new Date(dataToSend.lastTime);
    if (dateFormatted < dateToday) {
      checkDate = false;
      // return false;
    }
    if (firstTimeDate.valueOf() > lastTimeDate.getTime()) { //thời gian kết thúc <= thời gian bắt đầu
      checkExist = false;
      // console.log("Checking: " + checkExist);
      // return false;
    }
    $scope.data.forEach(item => {
      let itemStartTime = new Date(item.firstTime);
      let itemEndTime = new Date(item.lastTime);

      if (itemStartTime >= lastTimeDate && lastTimeDate <= itemEndTime) { // thời gian hết thúc nằm trong 1 khoảng thời gian
        checkExist = false;
        return false;
      }

      if (firstTimeDate >= itemStartTime && firstTimeDate <= itemEndTime) { // thời gian bắt đầu nằm trong 1 khoảng thời gian
        checkExist = false;
        return false;
      }
      if (firstTimeDate <= itemStartTime && itemEndTime <= lastTimeDate) {
        checkExist = false;
        return false;
      }
    });
    //validation
    if (checkDate == false) {
      message.innerText = "Không thể tạo thời gian ở quá khứ!";
      $("#modalValidate").modal("show");
      return false;
    }
    if (checkExist == false) {
      message.innerText = "Hãy kiểm tra lại khung thời gian!";
      $("#modalValidate").modal("show");
      return false;
    }
    //end validation
    console.log(checkExist);
    // Sử dụng Fetch API để gửi dữ liệu lên server
    if (checkExist == true && checkDate == true) {
      // alert("oke")
      $http
        .post("/api/saveFlashSales", dataToSend)
        .then((response) => {
          //  alert("ngon");/**/
          message.innerText = "Thêm khung giờ thành công!";
          $("#modalValidate").modal("show");
          $scope.flashSalesTime(); // lấy dữ
        })
        .catch((error) => {
          console.error(error);
        });
    }
  };

}); // end controller
