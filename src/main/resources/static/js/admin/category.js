const app = angular.module("app", []);
    app.controller("category", function($scope, $http, $location, $window) {
        
        $scope.listCategory = [];
        $scope.listCategoryDetails = [];
        $scope.listProductBrand = [];
        $scope.editca = {};
        $scope.categoryNames = {};
        $scope.editcad = {};
        $scope.categoryDetailsNames = {};
        $scope.editpb = {};
        $scope.productBrandNames = {};

        var idcate = '';
        var idcad = '';
        var idpb = '';

        // get all category
        $scope.getAllCategory = function() {
            $http.get('/api/getcategory')
            .then(function(response) {
                $scope.listCategory = response.data;
                if ($scope.listCategory.length > 0) {
                    $scope.firstCategory = $scope.listCategory[0];
                    $scope.showCategoryDetails($scope.firstCategory.id);
                    $scope.cafalse();
                }
            }, function(error) {
                console.error('Error:', error);
            });
        }; 

        // get all categoryDetails by category id
        $scope.showCategoryDetails = function(id) {
            $http.get('/api/getcategorydetails?id=' + id)
            .then(function(response) {
                if(idcate){
                    document.getElementById(idcate).style.backgroundColor = 'white';
                }
                idcate = id;
                document.getElementById(id).style.backgroundColor = '#808080';
                $scope.listCategoryDetails = response.data;
                $scope.cadfalse();
            }, function(error) {
                console.error('Error:', error);
            });
        }; 

        // insert a new category
        $scope.addCategory = function() {
            $http.post('/api/add/category?name=' + $scope.namecategory)
            .then(function(response) {
                var id = response.data.id;
                $scope.listCategory = response.data.listCategory;
                $scope.showCategoryDetails(id);
                $scope.namecategory = '';
                document.getElementById('si').click();
            }, function(error) {
                console.error('Error:', error);
                document.getElementById('fi').click();
            });
        }; 

        // insert a new categoryDetails 
        $scope.addCategoryDetails = function() {
            $http.post('/api/add/categorydetails?name=' + $scope.namecategorydetails + '&id=' + idcate)
            .then(function(response) {
                $scope.namecategorydetails = '';
                $scope.listCategoryDetails = response.data;
                $scope.cadfalse();
                document.getElementById('si').click();
            }, function(error) {
                console.error('Error:', error);
                document.getElementById('fi').click();
            });
        }; 

        // set unknown categorydetails
        $scope.updateCategoryDetails = function() {
            $http.put('/api/update/categorydetails?id=' + idcad + '&idfill=' + idcate)
            .then(function(putResponse) {
                $scope.listCategoryDetails = putResponse.data;
                $scope.cadfalse();
                document.getElementById('sd').click();
            }, function(error) {
                console.error('Error:', error);
                document.getElementById('fd').click();
            });
        };

        // delete a categoryDetails
        $scope.deleteCategoryDetails = function(id) {
            idcad = id;
            $scope.amountProduct = 0;
            $http.get('/api/getamountproduct?id=' + id)
            .then(function(response) {
                $scope.amountProduct = response.data;
                if ($scope.amountProduct != 0) {
                    document.getElementById('cad').click();
                } else {
                    $http.delete('/api/delete/categorydetails?iddelete=' + id + '&idfill=' + idcate)
                    .then(function(response) {
                        $scope.listCategoryDetails = response.data;
                        $scope.cadfalse();
                        document.getElementById('sd').click();
                    }, function(error) {
                        console.error('Error:', error);
                        document.getElementById('fd').click();
                    });
                }
            });
        };

        // set unknown categorydetails
        $scope.updateCategory = function() {
            $http.put('/api/update/category?id=' + idcate)
            .then(function(putResponse) {
                idcate = '';
                $scope.listCategory = putResponse.data;
                if ($scope.listCategory.length > 0) {
                    $scope.firstCategory = $scope.listCategory[0];
                    $scope.showCategoryDetails($scope.firstCategory.id);
                }
                document.getElementById('sd').click();
            }, function(error) {
                console.error('Error:', error);
                document.getElementById('fd').click();
            });
        };

        // delete a category
        $scope.deleteCategory = function(id) {
            $scope.amountCategoryDetails = 0;
            $http.get('/api/getamountcategorydetails?id=' + id)
            .then(function(response) {
                $scope.amountCategoryDetails = response.data;
                if ($scope.amountCategoryDetails != 0) {
                    document.getElementById('ca').click();
                } else {
                    $http.delete('/api/delete/category?id=' + id)
                    .then(function(response) {
                        idcate = '';
                        $scope.listCategory = response.data;
                        if ($scope.listCategory.length > 0) {
                            $scope.firstCategory = $scope.listCategory[0];
                            $scope.showCategoryDetails($scope.firstCategory.id);
                        }
                        document.getElementById('sd').click();
                    }, function(error) {
                        console.error('Error:', error);
                        document.getElementById('fd').click();
                    });
                }
            });
        };

        // get all ProductBrand
        $scope.getAllProductBrand = function() {
            $http.get('/api/getproductbrand')
            .then(function(response) {
                $scope.listProductBrand = response.data;
                $scope.pbfalse();
            }, function(error) {
                console.error('Error:', error);
            });
        };

        // set unknown ProductBrand
        $scope.updateProductBrand = function() {
            $http.put('/api/update/productbrand?id=' + idpb)
            .then(function(putResponse) {
                $scope.listProductBrand = putResponse.data;
                $scope.pbfalse();
                idpb = '';
                document.getElementById('sd').click();
            }, function(error) {
                console.error('Error:', error);
                document.getElementById('fd').click();
            });
        };

        // delete a productBrand
        $scope.deleteProductBrand = function(id) {
            $scope.amountProductBrand = 0;
            $http.get('/api/getamountproductbrand?id=' + id)
            .then(function(response) {
                $scope.amountProductBrand = response.data;
                idpb = id;
                if ($scope.amountProductBrand != 0) {
                    document.getElementById('pb').click();
                } else {
                    $http.delete('/api/delete/productbrand?id=' + id)
                    .then(function(response) {
                        $scope.listProductBrand = response.data;
                        $scope.pbfalse();
                        idpb = '';
                        document.getElementById('sd').click();
                    }, function(error) {
                        console.error('Error:', error);
                        document.getElementById('fd').click();
                    });
                }
            });
        };

        // insert a new productBrand 
        $scope.addProductBrand = function() {
            $http.post('/api/add/productbrand?name=' + $scope.nameProductBrand)
            .then(function(response) {
                $scope.nameProductBrand = '';
                $scope.listProductBrand = response.data;
                $scope.pbfalse();
                document.getElementById('si').click();
            }, function(error) {
                console.error('Error:', error);
                document.getElementById('fi').click();
            });
        }; 

        // set false khong hiện input trong category
        $scope.cafalse = function() {
            for (var i = 0; i < $scope.listCategory.length; i++) {
                $scope.editca[$scope.listCategory[i].id] = false;
            }
        };

        // edit category
        $scope.editCategory = function(id) {
            $scope.editca[id] = true;
            $scope.categoryNames[id] = '';
        };

        // cancel category
        $scope.cancelCategory = function(id) {
            $scope.editca[id] = false;
        };

        // save category
        $scope.saveCategory = function(id) {
            console.log(id);
            $http.put('/api/update/savecategory?id=' + id + '&name=' + $scope.categoryNames[id])
            .then(function(putResponse) {
                $scope.listCategory = putResponse.data.listca;
                $scope.id = putResponse.data.id;
                $scope.showCategoryDetails($scope.id);
                document.getElementById('su').click();
            }, function(error) {
                console.error('Error:', error);
                document.getElementById('fu').click();
            });
            $scope.editca[id] = false;
        };

        // set false khong hiện input trong categoryDetails
        $scope.cadfalse = function() {
            for (var i = 0; i < $scope.listCategoryDetails.length; i++) {
                $scope.editcad[$scope.listCategoryDetails[i].id] = false;
            }
        };

        // edit categoryDetails
        $scope.editCategoryDetails = function(id) {
            $scope.editcad[id] = true;
            $scope.categoryDetailsNames[id] = '';
        };

        // cancel categoryDetails
        $scope.cancelCategoryDetails = function(id) {
            $scope.editcad[id] = false;
        };

        // save categoryDetails
        $scope.saveCategoryDetails = function(id) {
            $http.put('/api/update/savecategorydetails?id=' + id + '&name=' + $scope.categoryDetailsNames[id])
            .then(function(putResponse) {
                $scope.id = putResponse.data;
                $scope.showCategoryDetails($scope.id);
                document.getElementById('su').click();
            }, function(error) {
                console.error('Error:', error);
                document.getElementById('fu').click();
            });
            $scope.editca[id] = false;
        };

        // set false khong hiện input trong productBrand
        $scope.pbfalse = function() {
            for (var i = 0; i < $scope.listProductBrand.length; i++) {
                $scope.editpb[$scope.listProductBrand[i].id] = false;
            }
        };

        // edit productBrand
        $scope.editProductBrand = function(id) {
            $scope.editpb[id] = true;
            $scope.productBrandNames[id] = '';
        };

        // cancel productBrand
        $scope.cancelProductBrand = function(id) {
            $scope.editpb[id] = false;
        };

        // save productBrand
        $scope.saveProductBrand = function(id) {
            $http.put('/api/update/saveproductbrand?id=' + id + '&name=' + $scope.productBrandNames[id])
            .then(function(putResponse) {
                $scope.listProductBrand = putResponse.data;
                $scope.pbfalse();
                document.getElementById('su').click();
            }, function(error) {
                console.error('Error:', error);
                document.getElementById('fu').click();
            });
            $scope.editpb[id] = false;
        };

        $scope.getAllCategory();
        $scope.getAllProductBrand();
    });