const app = angular.module("app", []);
app.controller("requestrefund", function ($scope, $http, $location, $window) {
  $scope.listOrderRequest = [];
  $scope.content = '';

  // get all ProductRequest
  $scope.getAllOrderRequest = function () {
    $http.get("/api/getallrequestrefund").then(
      function (response) {
        $scope.listOrderRequest = response.data;
      },
      function (error) {
        console.error("Error:", error);
      }
    );
  };

  // delete ProductRequest
  $scope.cancelRequest = function (id) {
    $http.delete("/api/delete/refund?id=" + id)
      .then(function (response) {
        $scope.getAllOrderRequest();
        document.getElementById('sr').click();
      }, function(error) {
        console.error('Error:', error);
        document.getElementById('fr').click();
    });
  };

  // confirm ProductRequest
  $scope.confirmRequest = function (id) {
    $http.put("/api/update/refund?id=" + id)
    .then(function (response) {
      $scope.getAllOrderRequest();
      document.getElementById('sa').click();
    }, function(error) {
      console.error('Error:', error);
      document.getElementById('fa').click();
    });
  };

  $scope.showContent = function(content) {
    $scope.content = content;
    console.log(content);
    console.log($scope.content);
    document.getElementById('ct').click();
  };

  $scope.getAllOrderRequest();
});