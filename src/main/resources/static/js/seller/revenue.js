const app = angular.module("app", []);
app.controller("sellerrevenue", function ($scope, $http, $window) {
  $scope.total = function () {
    $http
      .get("/seller/revenue/gettotalprice")
      .then((resp) => {
        $scope.statistical = resp.data;
      })
      .catch((error) => {
        console.log("errors", error);
      });
  };
  
  $scope.listOrder = function () {
    $http
      .get("/rest/list/order?year=2023&month=9&trangthai=daxacnhan")
      .then((resp) => {
        $scope.a = resp.data;
        console.log($scope.a);
      })
      .catch((error) => {
        console.log("errors", error);
      });
  };

  $scope.listWork = function () {
    $http
      .get("/rest/list/work")
      .then((resp) => {
        $scope.work = resp.data;
        console.log(resp.data+'a');
        console.log('a');
      })
      .catch((error) => {
        console.log("errors", error);
      });
  };

  $scope.listOrder();
  $scope.total();
  $scope.listWork();
});
