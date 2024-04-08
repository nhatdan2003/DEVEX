const app = angular.module("app", []);
app.controller("requestproduct", function ($scope, $http, $location, $window) {
  $scope.listProductRequest = [];
  $scope.listProductRequestTrue = [];

  // get all ProductRequest
  $scope.getAllProductRequest = function () {
    $http.get("/api/getallproductrequest").then(
      function (response) {
        $scope.listProductRequest = response.data;
      },
      function (error) {
        console.error("Error:", error);
      }
    );
  };

  // delete ProductRequest
  $scope.cancelRequest = function (id) {
    $http.delete("/api/delete/productrequest?id=" + id)
      .then(function (response) {
        $scope.listProductRequest = response.data;
        document.getElementById('sr').click();
      }, function(error) {
        console.error('Error:', error);
        document.getElementById('fr').click();
    });
  };

  // confirm ProductRequest
  $scope.confirmRequest = function (id) {
    $http.put("/api/update/productrequest?id=" + id)
    .then(function (response) {
      $scope.listProductRequest = response.data;
      document.getElementById('sa').click();
    }, function(error) {
      console.error('Error:', error);
      document.getElementById('fa').click();
    });
  };

  // focus URL
  $scope.focusURL = function (id) {
    $http.get('/api/idproductrequest')
      .then(function (response) {
        if (response.data == 0) {
          $window.location.href = '/ad/showproduct/' + id;
        }
      }).catch(function (error) {
        console.error('Error making API request:', error);
        // Xử lý lỗi nếu cần thiết
      });
  };

  // get all ProductRequestTrue
  $scope.getAllProductRequestTrue = function () {
    $http.get("/api/getallproductrequesttrue").then(
      function (response) {
        $scope.listProductRequestTrue = response.data;
      },
      function (error) {
        console.error("Error:", error);
      }
    );
  };

  // delete ProductRequest
  $scope.cancelRequestTrue = function (id) {
    $http.delete("/api/delete/productrequesttrue?id=" + id)
      .then(function (response) {
        $scope.listProductRequestTrue = response.data;
        document.getElementById('sr').click();
      }, function(error) {
        console.error('Error:', error);
        document.getElementById('fr').click();
    });
  };

  // confirm ProductRequest
  $scope.confirmRequestTrue = function (id) {
    $http.put("/api/update/productrequesttrue?id=" + id)
    .then(function (response) {
      $scope.listProductRequestTrue = response.data;
      document.getElementById('sa').click();
    }, function(error) {
      console.error('Error:', error);
      document.getElementById('fa').click();
    });
  };

  $scope.getAllProductRequest();
  $scope.getAllProductRequestTrue();
});