let hostk = "/api/shoppage/voucher";

app.controller("sellerpage", function($scope, $http, $location, $window) {
    $scope.checkFollow;
    $scope.infoShopPage = [];
    $scope.listProducts = [];
    $scope.selectedCategories = [];

	//format tiền cho đẹp
	$scope.formatMoney = function(x) {
		var money = "";
		money = x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
		return money;
	};

	$scope.formatDateToDDMMYYYY = function(dateString) {
		const date = new Date(dateString); // Chuyển chuỗi thời gian thành đối tượng Date
		const options = { day: 'numeric', month: 'numeric', year: 'numeric' };
		return date.toLocaleDateString('vi-VN', options); // Chuyển đổi sang "dd/MM/yyyy"
	};

	$scope.formatDateTimeToDDMMYYYYHHMM = function(dateString) {
		const date = new Date(dateString);
		const options = {
			day: 'numeric',
			month: 'numeric',
			year: 'numeric',
			hour: 'numeric',
			minute: 'numeric',
		};
		return date.toLocaleDateString('vi-VN', options);
	};

	//Quản lí voucher
	var $voucher = ($scope.voucher = {
		items: [],
		itemDetail: {},
		itemsEarly: [],
		itemsDoing: [],
		itemsFinish: [],
		itemsDisabled: [],
		prodVoucher: [],
		itemDelete: {},

		// load voucher
		loadVoucher() {
			var url = '/api/shoppage/voucher/list?username=' + username.value;
			$http.get(url).then((response) => {
				this.items = response.data;
				console.log(this.items);
				this.sortCreatedDateAllItem(this.items);
				this.groupItemEarly();
				this.groupItemDoing();
				this.groupItemFinish();
				this.groupItemDisabled();
			});
		},

		sortCreatedDateAllItem(array) {
			// Sắp xếp danh sách theo ngày tạo mới nhất (giảm dần)
			return array.sort((a, b) => {
				const dateA = new Date(a.createdDay);
				const dateB = new Date(b.createdDay);
				return dateB - dateA;
			});
		},

		openModalDetail: function(item) {
			this.itemDetail = item;
			this.prodVoucher = [];
			var url = `${hostk}/prod-voucher/${item.id}`;
			$http.get(url).then((response) => {
				if (response.data === null) {
					this.prodVoucher = null;
				} else {
					this.prodVoucher = response.data;
				}
			});
			console.log(this.itemDetail);
			$('#showDetail').modal('show');
		},

		openModalRemove: function(item) {
			//reset lại trạng thái của radio
			this.itemDelete = item;
			$('#deleteVoucher').modal('show');
		},

		remove() {
			var url = `${hostk}/disabled/${this.itemDelete.id}`;
			$http
				.put(url)
				.then((resp) => {
					console.log("Success", resp);
					//					this.items.forEach((item) => {
					//						if (item.id === this.itemDelete.id) {
					//							item.active = false;
					//						}
					//					});
					$('#deleteVoucher').modal('hide');
					this.loadVoucher();
				})
				.catch((error) => {
					console.log("Lỗi khi xoá voucher:", error);
				});
		},

		// group item sắp diễn ra 
		groupItemEarly() {
			const currentDate = new Date(); // Lấy ngày hiện tại

			// Lọc danh sách các voucher chưa diễn ra
			this.itemsEarly = this.items.filter(item => {
				const startDate = new Date(item.startDate);
				return startDate > currentDate && item.active === true;
			});

			this.sortCreatedDateAllItem(this.itemsEarly);
		},
		// group item đang diễn ra
		groupItemDoing() {
			const currentDate = new Date(); // Lấy ngày hiện tại

			// Lọc danh sách các voucher đang diễn ra
			this.itemsDoing = this.items.filter(item => {
				const startDate = new Date(item.startDate);
				const endDate = new Date(item.endDate);
				return startDate <= currentDate && currentDate <= endDate && item.active === true;
			});

			this.sortCreatedDateAllItem(this.itemsDoing);
		},
		// group item đã kết thúc
		groupItemFinish() {
			const currentDate = new Date(); // Lấy ngày hiện tại

			// Lọc danh sách các voucher đã kết thúc
			this.itemsFinish = this.items.filter(item => {
				const endDate = new Date(item.endDate);
				return endDate < currentDate && item.active === true;
			});

			this.sortCreatedDateAllItem(this.itemsFinish);
		},
		// group item đã bị vô hiệu
		groupItemDisabled() {
			const currentDate = new Date(); // Lấy ngày hiện tại

			// Lọc danh sách các voucher đã kết thúc
			this.itemsDisabled = this.items.filter(item => !item.active);

			this.sortCreatedDateAllItem(this.itemsDisabled);
		},

        saveVoucher(item) {
            var url = `/rest/voucher/save`;
            $http.post(url, item).then((response) => {
              this.loadVoucherOfUser();
            });
        }

	});

    var username = document.getElementById('username');
    // Thông tin shop
    $scope.fillInfoShopPage = function () {
        $http.get('/api/user/shoppage?username=' + username.value).then(resp => {
          $scope.infoShopPage = resp.data;
          $scope.checkFollow = resp.data.checkFollow;
          $scope.listProducts = resp.data.listInfoProduct;
          // Lấy ngày tạo
            var createDay = new Date($scope.infoShopPage.seller.createDay);

            // Tính thời gian đã trôi qua
            var now = new Date();
            var diffMs = now - createDay; // milliseconds
            var diffSecs = Math.floor(diffMs / 1000); // seconds
            var diffMins = Math.floor(diffSecs / 60); // minutes
            var diffHrs = Math.floor(diffMins / 60); // hours
            var diffDays = Math.floor(diffHrs / 24); // days
            var diffYears = Math.floor(diffDays / 365); // years
            if(diffSecs < 60){
                document.getElementById('createdday').textContent = 'Tham gia ' + diffSecs + ' giây trước';
            } else if(diffMins < 60){
                document.getElementById('createdday').textContent = 'Tham gia ' + diffMins + ' phút trước';
            } else if(diffHrs < 25){
                document.getElementById('createdday').textContent = 'Tham gia ' + diffHrs + ' giờ trước';
            } else if(diffDays < 365){
                document.getElementById('createdday').textContent = 'Tham gia ' + diffDays + ' ngày trước';
            } else if(diffYears > 365){
                document.getElementById('createdday').textContent = 'Tham gia ' + diffYears + ' năm trước';
            }
        }).catch(function (err) {
          console.error(err); 
        });
    };
    
    $scope.follow = function() {
        $http.post('/api/user/follow?username=' + username.value)
            .then(function(response) {
                $scope.checkFollow = true;
            }, function(error) {
                // Xử lý lỗi ở đây
                console.error('Error:', error);
            });
    }; 

    $scope.unFollow = function() {
        $http.delete('/api/user/unfollow?username=' + username.value)
            .then(function(response) {
                $scope.checkFollow = false;
            }, function(error) {
                // Xử lý lỗi ở đây
                console.error('Error:', error);
            });
    }; 

    $scope.updateSelectedCategories = function(cateId) {
        var index = $scope.selectedCategories.indexOf(cateId);
        if (index > -1) {
            // Nếu cateId đã tồn tại trong mảng, xóa nó
            $scope.selectedCategories.splice(index, 1);
        } else {
            // Nếu không, thêm cateId vào mảng
            $scope.selectedCategories.push(cateId);
        }
        $scope.filterByCategory();
    };    

    $scope.filterByCategory = function() {
        $scope.listProducts = $scope.infoShopPage.listInfoProduct.filter(product => $scope.selectedCategories.includes(product.cateId));
        console.log($scope.listProducts);
		if ($scope.listProducts.length === 0) {
			$scope.fillInfoShopPage();
		}
    };  

	$voucher.loadVoucher();
    $scope.fillInfoShopPage();
});
