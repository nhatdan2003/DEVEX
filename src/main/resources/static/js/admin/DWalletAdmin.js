// app = angular.module("app", []);
app.controller("DWalletAdmin_Controller", function ($scope, $http) {
  var $wallet = ($scope.wallet = {
    loadDWalletAdmin: [],
    historyTransaction: [],
    tempHistoryTransaction: [],
    getDWallet: function () {
      $http
        .get("/api/walletAdmin")
        .then((resp) => {
          this.loadDWalletAdmin = resp.data;
          console.log(this.loadDWalletAdmin);
        })
        .catch(function (err) {
          console.error(err); // xử lý lỗi khi gọi API
        });
    },
    loadHistoryTransaction: function () {
      $http
        .get("/api/historyAdmin")
        .then((resp) => {
          this.historyTransaction = resp.data;
          this.tempHistoryTransaction = resp.data;
          console.log(this.historyTransaction);
          this.fillToTable(this.historyTransaction);
        })
        .catch(function (err) {
          console.error(err); // xử lý lỗi khi gọi API
        });
    },
    fillToTable: function (data) {
      if ($.fn.dataTable.isDataTable("#wallet")) {
        // Nếu DataTable đã tồn tại, hủy bỏ nó trước
        $("#wallet").DataTable().destroy();
      }

      table = $("#wallet").DataTable({
        processing: true,
        data: data,
        select: true,
        language: {
          url: "https://cdn.datatables.net/plug-ins/1.10.22/i18n/Vietnamese.json"
        },
        columns: [
          { data: "id" },
          { data: "from" },
          { data: "to" },
          {
            data: "createDay",
            render: function (data, type, row) {
              return moment(data).format(" HH:mm DD/MM/yyyy");
            },
          },
          { data: "value" },
        ], "order": [[3, "desc"]],
        columnDefs: [
          {
            targets: [1, 2], // Các cột cần áp dụng kiểu CSS
            render: function (data, type, row, meta) {
              return '<div class="ellipsis">' + data + "</div>";
            },
          },
          {
            targets: 4,
            render: function (data, type, row, meta) {
              const idWallet = document.getElementById("idWallet").innerHTML;
              const formattedValue = parseFloat(data);
              const formattedText =
                (row.to === idWallet ? "+ " : "- ") +
                formattedValue.toLocaleString();

              return (
                '<div id="fluctuations"><span id="historyTransaction" class="' +
                (row.to === idWallet ? "green-text" : "red-text") +
                '">' +
                formattedText +
                "</span></div>"
              );
            },
          },
        ],
      });
      // Lắng nghe sự kiện thay đổi trên thẻ select của cột thứ tư
      $("#filterColumn2").on("change", function () {
        const idWallet = document.getElementById("idWallet").innerHTML;
        // Lấy giá trị được chọn từ thẻ select
        var selectedValue = $(this).val();

        // Kiểm tra giá trị và xác định cách hiển thị dữ liệu trong DataTables
        switch (selectedValue) {
          case "1": // Hiển thị tất cả
            table.column(1).search("").column(2).search("").draw();
            break;
          case "2": // Biến động tăng
            table.column(1).search("").column(2).search(idWallet).draw();
            break;
          case "3": // Biến động giảm
            table.column(1).search(idWallet).column(2).search("").draw();
            break;
          default:
            break;
        }
      });
    }, // end fillToTable
    sortTransactionByFluctuations: function () { },
  });
  $wallet.getDWallet();
  $wallet.loadHistoryTransaction();
});
