var username = '';
const app = angular.module("app", []);
app.controller("product", function($scope, $http, $location, $window) {
    
    $scope.listDistributor = [];
    $scope.listProduct = [];
    $scope.listOrder =[];
    $scope.seller = '';
    $scope.cuon = 0;

    // get all Distributor
    $scope.getAllDistributor = function() {
        $http.get('/api/getlistdistributor')
        .then(function(response) {
            $scope.listDistributor = response.data;
            if ($scope.listDistributor.listSeller.length > 0) {
                $scope.firstDistributor = $scope.listDistributor.listSeller[0];
                $scope.showInfo($scope.firstDistributor.username);
            }
        }, function(error) {
            console.error('Error:', error);
        });
    };

    $scope.showInfo = function(username) {
        $http.get('/api/list/productseller?username=' + username)
        .then(function(response) {
            $scope.listProduct = response.data;
            $scope.seller = username;
            username = username;
            if($scope.cuon > 0){
                var productTable = document.getElementById('thongke');
                if (productTable) {
                    productTable.scrollIntoView({ behavior: 'smooth' });
                } 
            }
            $scope.cuon++;
            updateChartrevenuemonthline(yearSelectrevenuemonthline.value, monthSelectrevenuemonthline.value, $scope.seller);
            updateChartrevenueyearline(yearSelectrevenueyearyearline.value, $scope.seller);
            $scope.getListOrder(username);
        }, function(error) {
            console.error('Error:', error);
        });
    };    

    // search
	$scope.searchDistributor = function () {
        $http.get('/api/search/distributor?keyword=' + $scope.distributor)
        .then(function(response) {
            $scope.listDistributor = response.data;
        }, function(error) {
            console.error('Error:', error);
        });
    };

    $scope.searchProduct = function() {
        $http.get('/api/search/productseller?keyword=' + $scope.product + '&username=' + $scope.seller)
        .then(function(response) {
            $scope.listProduct = null;
            $scope.listProduct = response.data;
        }, function(error) {
            console.error('Error:', error);
        });
    };   

    $scope.updateActive = function() {
        $http.put('/api/update/productactive?username=' + $scope.seller)
        .then(function(response) {

        }, function(error) {
            console.error('Error:', error);
        });
    };

    $scope.getListOrder = function(username) {
        $http.get('/api/list/orderseller?username=' + username)
        .then(function(response) {
            $scope.listOrder = response.data;
        }, function(error) {
            console.error('Error:', error);
        });
    };

    $scope.searchOrder = function() {
        console.log($scope.order);
        $http.get('/api/search/orderseller?keyword=' + $scope.order + '&username=' + $scope.seller)
        .then(function(response) {
            $scope.listOrder = response.data;
        }, function(error) {
            console.error('Error:', error);
        });
    };

    $scope.getAllDistributor();

    
    // Biểu đồ
    var ctx = document.getElementById('statisticalrevenuemonth').getContext('2d');
    var ctx1 = document.getElementById('statisticalrevenueyear').getContext('2d');
    var myChart = null;
    var myChart1 = null;

    // Hàm cập nhật biểu đồ
    function updateChartrevenuemonthline(year, month, username) {
        fetch('/api/seller/revenue/month?year=' + year + '&month=' + month + '&username=' + username)
        .then(response => response.json())
        .then(data => {
            // Cập nhật dữ liệu cho biểu đồ
            myChart.data.labels = data.map(liststatis => liststatis.day);
            myChart.data.datasets[0].data = data.map(liststatis => liststatis.price);
            myChart.data.datasets[1].data = data.map(liststatis => liststatis.priceCompare);
            myChart.update();
            let priceChartcontributemonthline = data.reduce((sum, liststatis) => sum + liststatis.priceCompare, 0);
            let headerElement = document.querySelector('.description-block .contributeMonth');
            let formattedPrice = priceChartcontributemonthline.toLocaleString('vi-VN', {
                style: 'currency',
                currency: 'VND'
            });
            headerElement.textContent = formattedPrice;
        });
    }

    // Hàm cập nhật biểu đồ 2
    function updateChartrevenueyearline(year, username) {
        fetch('/api/seller/revenue/year?year=' + year + '&username=' + username)
        .then(response => response.json())
        .then(data => {
            // Cập nhật dữ liệu cho biểu đồ
            myChart1.data.labels = data.map(liststatis => liststatis.day);
            myChart1.data.datasets[0].data = data.map(liststatis => liststatis.price);
            myChart1.data.datasets[1].data = data.map(liststatis => liststatis.priceCompare);
            myChart1.update();
            let priceChartcontributeyearline = data.reduce((sum, liststatis) => sum + liststatis.priceCompare, 0);
            let headerElement = document.querySelector('.description-block .contributeYear');
            let formattedPrice = priceChartcontributeyearline.toLocaleString('vi-VN', {
                style: 'currency',
                currency: 'VND'
            });
            headerElement.textContent = formattedPrice;
        });
    }

    // Lấy thẻ select theo id
    var monthSelectrevenuemonthline = document.getElementById('monthSelectrevenuemonthline');
    var yearSelectrevenuemonthline = document.getElementById('yearSelectrevenuemonthline');
    var yearSelectrevenueyearyearline = document.getElementById('yearSelectrevenueyearyearline');

    // Tạo các phần tử option cho tháng và năm
    for (var i = 1; i <= 12; i++) {
        var option = document.createElement('option');
        option.value = i;
        option.textContent = i;
        monthSelectrevenuemonthline.appendChild(option);
    }

    var currentYearMonthline = new Date().getFullYear();
    for (var i = 0; i < 5; i++) {
        var year = currentYearMonthline - i;
        var option = document.createElement('option');
        option.value = year;
        option.textContent = year;
        yearSelectrevenuemonthline.appendChild(option);
    }

    var currentYearYearline = new Date().getFullYear();
    for (var i = 0; i < 5; i++) {
        var year = currentYearYearline - i;
        var option = document.createElement('option');
        option.value = year;
        option.textContent = year;
        yearSelectrevenueyearyearline.appendChild(option);
    }

    // Gắn sự kiện change cho các thẻ select
    // sự kiện tháng của biểu đồ line
    monthSelectrevenuemonthline.addEventListener('change', function () {	
        updateChartrevenuemonthline(yearSelectrevenuemonthline.value, monthSelectrevenuemonthline.value, $scope.seller);
    });

    //sự kiện năm của biểu đồ line
    yearSelectrevenuemonthline.addEventListener('change', function () {
        updateChartrevenuemonthline(yearSelectrevenuemonthline.value, monthSelectrevenuemonthline.value, $scope.seller);
    });

    // sự kiện select theo năm của biểu đồ line
    yearSelectrevenueyearyearline.addEventListener('change', function () {
        updateChartrevenueyearline(yearSelectrevenueyearyearline.value, $scope.seller);
    });

    // Khởi tạo biểu đồ ban đầu
    // Biểu đồ 1
    myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: [],
            datasets: [{
                label: 'Doanh thu tháng này',
                data: [],
                borderColor: 'red',
                fill: false,
                cubicInterpolationMode: 'monotone',
                tension: 0.4
            }, {
                label: 'Chiết khấu',
                data: [],
                borderColor: 'blue',
                fill: false,
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: false,
                    text: 'Chart.js Line Chart - Cubic interpolation mode'
                },
            },
            interaction: {
                intersect: false,
            },
            scales: {
                x: {
                    display: true,
                    title: {
                        display: true,
                        text: 'Ngày'
                    }
                },
                y: {
                    display: true,
                    title: {
                        display: true,
                        text: 'Doanh thu'
                    },
                    suggestedMin: 0,
                    suggestedMax: 200
                }
            }
        }
    });

    //Biểu đồ 2
    myChart1 = new Chart(ctx1, {
        type: 'line',
        data: {
            labels: [],
            datasets: [{
                label: 'Doanh thu năm nay',
                data: [],
                borderColor: 'red',
                fill: false,
                cubicInterpolationMode: 'monotone',
                tension: 0.4
            }, {
                label: 'Chiết khấu',
                data: [],
                borderColor: 'blue',
                fill: false,
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: false,
                    text: 'Chart.js Line Chart - Cubic interpolation mode'
                },
            },
            interaction: {
                intersect: false,
            },
            scales: {
                x: {
                    display: true,
                    title: {
                        display: true,
                        text: 'Ngày'
                    }
                },
                y: {
                    display: true,
                    title: {
                        display: true,
                        text: 'Doanh thu'
                    },
                    suggestedMin: 0,
                    suggestedMax: 200
                }
            }
        }
    });

    // Tự động chọn tháng và năm hiện tại
    monthSelectrevenuemonthline.value = new Date().getMonth() + 1;
    yearSelectrevenuemonthline.value = currentYearMonthline;
    yearSelectrevenueyearyearline.value = currentYearYearline;

});

// định nghĩa sự kiện nhấn enter input
app.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
                scope.$apply(function () {
                    scope.$eval(attrs.ngEnter, { 'event': event });
                });

                event.preventDefault();
            }
        });
    };
});

