app = angular.module("app", []);
app.controller("sellerproduct", function ($scope, $http, $window, $compile) {

    $scope.files = [];
    $scope.variant = [];
    $scope.form = {};
    $scope.selectedCategory = '';
    $scope.selectedCategoryDetail = '';
    $scope.selectedbrand = '';
    let idCounter = 0;


    $scope.uploadimg = function (files) {
        $scope.$apply(function () {
            Array.from(files).forEach(file => {
                const fileObj = {
                    id: idCounter++,
                    file: file
                };
                $scope.files.push(fileObj);
                const reader = new FileReader();
                reader.onloadend = function () {
                    const img = document.createElement('img');
                    img.src = reader.result;
                    img.alt = '';
                    img.className = 'img-fluid';
                    img.setAttribute('ng-dblclick', `deleteimg(${fileObj.id})`);
                    const label = document.createElement('label');
                    label.className = 'col-1';
                    label.title = 'Click vào hình ảnh 2 lần để xóa';
                    label.appendChild(img);
                    const compiledLabel = $compile(label)($scope);
                    document.querySelector('#imgProduct').appendChild(compiledLabel[0]);
                };
                reader.readAsDataURL(file);
                console.log($scope.files.length);
            });
        });
    };

    $scope.deleteimg = function (id) {
        $scope.files = $scope.files.filter(fileObj => fileObj.id !== id);
        const imgElement = document.querySelector(`#imgProduct img[ng-dblclick='deleteimg(${id})']`);
        if (imgElement) {
            imgElement.parentElement.remove();
        }
        console.log($scope.files);
    };    

    var id = 0;
    $scope.checkdeletepv = false;
    $scope.addRow = function () {
        id++;
        $scope.variant.push({
            id,
            size: '',
            color: '',
            price: '',
            quantity: ''
        });
        if($scope.variant.length === 1){
            $scope.checkdeletepv = true;
        }else{
            $scope.checkdeletepv = false;
        }
    };

    $scope.openFilePicker = function () {
        document.getElementById('fileInput').click();
    };

    $scope.getcategory = function () {
        $http.get('/categorynew').then(resp => {
            $scope.categorys = resp.data;
            $scope.selectedCategory = resp.data[0].id;
            $scope.getCategoryDetails();
        })
    };

    $scope.getCategoryDetails = function () {
        $http.get('/categorydetailsnew/' + $scope.selectedCategory)
            .then(function (resp) {
                $scope.categorydetail = resp.data;
                $scope.selectedCategoryDetail = $scope.categorydetail[0].id;
            });
    };

    $scope.getbrand = function () {
        $http.get('/brandnew').then(resp => {
            $scope.brands = resp.data;
            $scope.selectedbrand = resp.data[0].id;
        })
    };

    $scope.deleteproductvariant = function (id) {
        let i = $scope.variant.findIndex(item => item == id);
        $scope.variant.splice(i, 1);
        if($scope.variant.length === 1){
            $scope.checkdeletepv = true;
        }else{
            $scope.checkdeletepv = false;
        }
    }

    $scope.id = '';

    $scope.chuyentrang = function () {
        $window.location.href = '/seller/product/edit/' + $scope.id;
    };

    $scope.save = function () {
        $scope.form.idCategoryDetails = $scope.selectedCategoryDetail;
        $scope.form.listvariant = $scope.variant;
        $scope.form.brand = $scope.selectedbrand;
        
         $http.post("/insert/product", $scope.form).then(resp => {
            $scope.id = resp.data.id;
            console.log($scope.id);
            $scope.saveimg($scope.id);
        }).catch(error => {
            document.getElementById('fs').click();
        });
    };    

    $scope.saveimg = function (id) {
        var form = new FormData();
        for (var i = 0; i < $scope.files.length; i++) {
            form.append('files', $scope.files[i].file);
            console.log($scope.files[i].file);
        }
        $http.post('/insert/image/productnew?id=' + id, form, {
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }).then(resp => {
            document.getElementById('ss').click();
        }).catch(error => {
            console.log("errors", error);
        })
    };

    $scope.addRow();
    $scope.getcategory();
    $scope.getbrand();
});