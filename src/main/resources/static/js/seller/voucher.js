let host = "http://localhost:8888/rest/seller/voucher";
const app = angular.module("app", []);


app.controller("voucher-ctrl", function($scope, $http, $location, $window) {

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
			var url = `${host}/list`;
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
			var url = `${host}/prod-voucher/${item.id}`;
			$http.get(url).then((response) => {
				if (response.data === null) {
					this.prodVoucher = null;
				} else {
					this.prodVoucher = response.data;
				}
				console.log(this.prodVoucher);
			});
			console.log(this.itemDetail);
			$('#showDetail').modal('show');
		},

		openModalRemove: function(item) {
			//reset lại trạng thái của radio
			this.itemDelete = item;
			console.log(this.itemDelete);
			$('#deleteVoucher').modal('show');
		},

		remove() {
			var url = `${host}/disabled/${this.itemDelete.id}`;
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


			console.log(this.itemsEarly);
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

			console.log(this.itemsDoing);
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

			console.log(this.itemsFinish);
		},
		// group item đã bị vô hiệu
		groupItemDisabled() {
			const currentDate = new Date(); // Lấy ngày hiện tại

			// Lọc danh sách các voucher đã kết thúc
			this.itemsDisabled = this.items.filter(item => !item.active);

			this.sortCreatedDateAllItem(this.itemsDisabled);

			console.log(this.itemsDisabled);
		},

	});

	$voucher.loadVoucher();
});
