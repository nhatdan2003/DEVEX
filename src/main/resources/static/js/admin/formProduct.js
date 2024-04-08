function openFilePicker() {
    // Khi người dùng nhấp vào button, ta kích hoạt sự kiện nhấn vào thẻ input type="file".
    document.getElementById('fileInput').click();
}

app = angular.module("app", []);
app.controller("product", function ($scope, $http, $window) {

    $scope.listimg = function () {
        $http.get('/api/img/product').then(resp => {
            $scope.filenames = resp.data;
        }).catch(error => {
            console.log("errors", error);
        })
    };

    $scope.deleteimg = function (filename) {
        $http.delete(filename).then(resp => {
            // $scope.listimg();
            let i = $scope.filenames.findIndex(name => name == filename);
            $scope.filenames.splice(i, 1);
            console.log(filename);
        }).catch(error => {
            console.log("errors", error);
        });
    };

    $scope.uploadimg = function (files) {
        var form = new FormData();
        for (var i = 0; i < files.length; i++) {
            form.append('files', files[i]);
        }
        $http.post('/img/product', form, {
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }).then(resp => {
            $scope.filenames.push(...resp.data);
            $scope.listimg();
        }).catch(error => {
            console.log("errors", error);
        })
    };

    $scope.id = '';
	$scope.selectedCategoryId = '';
    $scope.checkbutton = false;
    $scope.idrq = '';

    $scope.infoproduct = function () {
        $http.get('/api/ad/product').then(resp => {
            $scope.form = resp.data.product;
            if(resp.data.check == true){
                $scope.checkbutton = resp.data.check;
                $scope.idrq = resp.data.idproductrequest;
                $scope.content = resp.data.content;
            }
            console.log($scope.idrq);   
        }).catch(error => {
            console.log("errors", error);   
        })
    };

    $scope.getCategoryDetails = function () {
        $http.get('/api/ad/categoryDetails/' + $scope.selectedCategory)
        .then(function (resp) {
            $scope.categorydetail = resp.data;
            $scope.selectedCategoryDetail = $scope.categorydetail[0].id;
        });
    };

    $scope.getidcategoryDetails = function () {
        $http.get('/api/ad/idCategoryDetails').then(resp => {
            $scope.id = resp.data;
            $scope.selectedCategoryDetail = $scope.id;
        })
    };

    $scope.getidcategory = function () {
        $http.get('/api/ad/idcategory').then(resp => {
            $scope.id = resp.data;
            $scope.selectedCategory = $scope.id;
            $scope.getCategoryDetails();
            $scope.getidcategoryDetails();
        })
    };

    $scope.getcategory = function () {
        $http.get('/api/ad/category').then(resp => {
            $scope.categorys = resp.data;
        })
    };

    $scope.getbrand = function () {
        $http.get('/api/ad/brand').then(resp => {
            $scope.brands = resp.data;
        })
    };

    $scope.getidbrand = function () {
        $http.get('/api/ad/idbrand').then(resp => {
            $scope.id = resp.data;
            $scope.selectedbrand = $scope.id;
        })
    };

    $scope.lista = function () {
        $http.get('/api/ad/productvariant').then(resp => {
            $scope.variant = resp.data;
        }).catch(error => {
            console.log("errors", error);
        })
    };

    $scope.deleteproductvariant = function (id) {
        $http.delete('/api/ad/productvariant/delete/' + id).then(resp => {
            let i = $scope.filenames.findIndex(item => item == id);
            $scope.variant.splice(i, 1);
        }).catch(error => {
            console.log("errors", error);
        });
    };

    $scope.save = function () {
        $scope.form.idCategoryDetails = $scope.selectedCategoryDetail;
        $scope.form.listvariant = $scope.variant;
        $scope.form.brand = $scope.selectedbrand;
        console.log($scope.form);
        $http.put("/api/ad/info/product", $scope.form).then(resp => {
            document.getElementById('ss').click();
        }).catch(error => {
            console.log("errors", error);
            document.getElementById('fs').click();
        })
    };

    $scope.deleteProduc = function () {
        $http.delete('/api/ad/delete/product/' + $scope.form.id)
        .then(function (response) {
            document.getElementById('sd').click();
        }).catch(error => {
            console.log("errors", error);
            document.getElementById('fd').click();
        });
    };

    $scope.chuyentrang = function() {
        $window.location.href = '/ad/productmanage';
    };

    var id = 0;
	$scope.addRow = function () {
		id++;
		$scope.variant.push({
			id,
			size: '',
			color: '',
			price: '',
			quantity: ''
	    });
	};

    // delete ProductRequest
    $scope.cancelRequest = function(id) {
        $http.delete('/api/delete/productrequest?id=' + id)
        .then(function(response) {
            document.getElementById('sr').click();
        }, function(error) {
            console.error('Error:', error);
            document.getElementById('fr').click();
        });
    };

    // confirm ProductRequest
    $scope.confirmRequest = function(id) {
        $http.put('/api/update/productrequest?id=' + id)
        .then(function(response) {
            document.getElementById('sa').click();
        }, function(error) {
            console.error('Error:', error);
            document.getElementById('fa').click();
        });
    };

    // confirm ProductRequest
    $scope.chuyentrangrequestthanhcong = function(id) {
        $window.location.href = '/ad/requestproduct';
    };

    // cancel ProductRequest
    $scope.chuyentrangrequesthuythanhcong = function(id) {
        $window.location.href = '/ad/requestproduct';
    };

    // set false request product
    $scope.falseRequest = function() {
        $http.put('/api/falserequest')
        .then(function(response) {
        });
    };

    $scope.listimg();
    $scope.getcategory();
	$scope.getidcategory();
	$scope.infoproduct();
	$scope.getbrand();
	$scope.getidbrand();
    $scope.lista();

    
    window.onbeforeunload = function (event) {
		$scope.falseRequest();
    }

});

// document.addEventListener('DOMContentLoaded', function () {
//     var successbtn = document.getElementById('successbtn');
//     var failsebtn = document.getElementById('failsebtn');
//     var successModal = document.getElementById('successModal');
//     var failseModal = document.getElementById('failseModal');
//     var closeButtons = document.getElementsByClassName('close');

//     successbtn.addEventListener('click', function () {
//         successModal.style.display = 'block';
//     });

//     failsebtn.addEventListener('click', function () {
//         failseModal.style.display = 'block';
//     });

//     for (var i = 0; i < closeButtons.length; i++) {
//         closeButtons[i].addEventListener('click', function () {
//             successModal.style.display = 'none';
//             failseModal.style.display = 'none';
//         });
//     }

//     window.addEventListener('click', function (event) {
//         if (event.target === greenModal || event.target === failseModal) {
//             successModal.style.display = 'none';
//             failseModal.style.display = 'none';
//         }
//     });

// });