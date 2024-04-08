app = angular.module("app", ['ui.bootstrap']);
app.controller('order', function($scope, $http, $window) {
    $scope.currentPage = 0;
    $scope.itemsPerPage = 10;
    $scope.listOrders = { listOrder: [] };

    $scope.listOrder = function() {
        $http.get('/api/list/order?status=' + $scope.selectedStatus + '&search=' + $scope.search).then(resp => {
            $scope.listOrders.listOrder = resp.data.listOrder.map(function(order, index) {
                order.customerName = resp.data.listCustomer[index];
                order.paymentName = resp.data.listPayment[index];
                return order;
            });
            $scope.totalItems = $scope.listOrders.listOrder.length;
        }).catch(error => {
            console.log("errors", error);
        });
    };

    $scope.listStatus = function() {
        $http.get('/api/list/status').then(resp => {
            $scope.statuss = resp.data;
            $scope.selectedStatus = $scope.statuss[0].id;
            $scope.listOrder();
        }).catch(error => {
            console.log("errors", error);
        });
    };

    $scope.getOrderStatus = function() {
        $scope.listOrder();
    };

    $scope.searchOrder = function() {
        $scope.listOrder();
    };

    $scope.prevPage = function() {
        if ($scope.currentPage > 0) {
            $scope.currentPage--;
        }
    };

    $scope.nextPage = function() {
        if ($scope.currentPage < $scope.pageCount()-1) {
            $scope.currentPage++;
        }
    };

    $scope.pageCount = function() {
        return Math.ceil($scope.totalItems / $scope.itemsPerPage);
    };

    $scope.range = function() {
        var rangeSize = 5;
        var ret = [];
        var start;
    
        if ($scope.pageCount() < rangeSize) {
            rangeSize = $scope.pageCount();
        }
    
        start = $scope.currentPage - Math.floor(rangeSize / 2);
        if (start < 0) {
            start = 0;
        }
        if (start + rangeSize > $scope.pageCount()) {
            start = $scope.pageCount() - rangeSize;
        }
    
        for (var i = start; i < start + rangeSize; i++) {
            ret.push(i);
        }
        return ret;
    };    

    $scope.setPage = function(n) {
        $scope.currentPage = n;
    };

    $scope.listStatus();
});

app.filter('pagination', function() {
    return function(input, start) {
        start = +start;
        return input.slice(start);
    };
});

// định nghĩa sự kiện nhấn enter input
app.directive("ngEnter", function () {
    return function (scope, element, attrs) {
      element.bind("keydown keypress", function (event) {
        if (event.which === 13) {
          scope.$apply(function () {
            scope.$eval(attrs.ngEnter, { event: event });
          });

          event.preventDefault();
        }
      });
    };
  });
