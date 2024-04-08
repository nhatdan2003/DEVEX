        var ctx = document.getElementById('statisticalrevenuemonthline').getContext('2d');
		var ctx1 = document.getElementById('statisticalrevenueyearline').getContext('2d');
		var ctx2 = document.getElementById('statisticalcategoryyearpie').getContext('2d');
		var ctx3 = document.getElementById('statisticalordermonthpie').getContext('2d');
		var ctx4 = document.getElementById('statisticalorderyearpie').getContext('2d');
		var myChart = null;
		var myChart1 = null;
		var myChart2 = null;
		var myChart3 = null;
		var myChart4 = null;
		// code lấy đường dẫn icon month line
		var iconrevenuemonthline = document.querySelector('.description-block .iconrevenuemonthline i');
		var iconrevenueyearline = document.querySelector('.description-block .iconrevenueyearline i');

		//cookie
		// function setCookie(cname, cvalue, exdays) {
		// 	const d = new Date();
		// 	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
		// 	let expires = "expires=" + d.toUTCString();
		// 	document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
		// }

		// function getCookie(cname) {
		// 	let name = cname + "=";
		// 	let ca = document.cookie.split(';');
		// 	for (let i = 0; i < ca.length; i++) {
		// 		let c = ca[i];
		// 		while (c.charAt(0) == ' ') {
		// 			c = c.substring(1);
		// 		}
		// 		if (c.indexOf(name) == 0) {
		// 			return c.substring(name.length, c.length);
		// 		}
		// 	}
		// 	return "";
		// }

		// function checkCookie() {
		// 	let user = getCookie("username");
		// 	if (user != "") {
		// 		alert("Welcome again " + user);
		// 	} else {
		// 		user = prompt("Please enter your name:", "");
		// 		if (user != "" && user != null) {
		// 			setCookie("username", user, 365);
		// 		}
		// 	}
		// }

		// Hàm cập nhật biểu đồ month line 
		function updateChartrevenuemonthline(year, month) {
			fetch('/api/ad/revenue/line/month?year=' + year + '&month=' + month)
				.then(response => response.json())
				.then(data => {
					// Lấy giá trị 
					const liststatis = data.liststatis;
					// Cập nhật dữ liệu cho biểu đồ
					myChart.data.labels = data.liststatis.map(item => item.day);
					myChart.data.datasets[0].data = data.liststatis.map(item => item.price);
					myChart.data.datasets[1].data = data.liststatis.map(item => item.priceCompare);
					myChart.update();
					// Tính tổng giá trị của trường 'price' trong danh sách
					let priceChartrevenuemonthline = data.liststatis.reduce((sum, item) => sum + item.price, 0);
					// Tính tổng giá trị của trường 'priceCompare' trong danh sách
					let pricecompareChartrevenuemonthline = data.liststatis.reduce((sum, item) => sum + item.priceCompare,
						0);
					let percentage;
					if (priceChartrevenuemonthline > pricecompareChartrevenuemonthline) {
						if (pricecompareChartrevenuemonthline !== 0) {
							percentage = (priceChartrevenuemonthline / pricecompareChartrevenuemonthline) * 100;
						} else {
							percentage = 100;
						}
					} else if (priceChartrevenuemonthline < pricecompareChartrevenuemonthline) {
						if (priceChartrevenuemonthline !== 0) {
							percentage = (pricecompareChartrevenuemonthline / priceChartrevenuemonthline) * 100;
						} else {
							percentage = 100;
						}
					} else if (priceChartrevenuemonthline == pricecompareChartrevenuemonthline) {
						percentage = 0;
					}
					let percentageElement = document.querySelector(
						'.description-block .iconrevenuemonthline .numberrevenuemonthline');
					let headerElement = document.querySelector('.description-block .totalrevenuemonthline');
					let formattedPrice = priceChartrevenuemonthline.toLocaleString('vi-VN', {
						style: 'currency',
						currency: 'VND'
					});
					if (priceChartrevenuemonthline > pricecompareChartrevenuemonthline) {
						iconrevenuemonthline.className = "fa-solid fa-caret-up";
						percentageElement.textContent = percentage.toFixed(0) + '%';
						headerElement.textContent = formattedPrice;
					} else if (priceChartrevenuemonthline < pricecompareChartrevenuemonthline) {
						iconrevenuemonthline.className = "fa-solid fa-caret-down text-danger";
						percentageElement.textContent = '-' + percentage.toFixed(0) + '%';
						headerElement.textContent = formattedPrice;
					} else {
						iconrevenuemonthline.className = "";
						percentageElement.textContent = percentage.toFixed(0) + '%';
						headerElement.textContent = formattedPrice;
					}
				});
		}

		// Hàm cập nhật biểu đồ month line 
		function updateChartrevenueyearline(year) {
			fetch('/api/ad/revenue/line/year?year=' + year)
				.then(response => response.json())
				.then(data => {
					// Lấy giá trị 
					const liststatis = data.liststatis;
					// Cập nhật dữ liệu cho biểu đồ
					myChart1.data.labels = data.liststatis.map(item => item.day);
					myChart1.data.datasets[0].data = data.liststatis.map(item => item.price);
					myChart1.data.datasets[1].data = data.liststatis.map(item => item.priceCompare);
					myChart1.update();
					// Tính tổng giá trị của trường 'price' trong danh sách
					let priceChartrevenuemonthline = data.liststatis.reduce((sum, item) => sum + item.price, 0);
					// Tính tổng giá trị của trường 'priceCompare' trong danh sách
					let pricecompareChartrevenuemonthline = data.liststatis.reduce((sum, item) => sum + item.priceCompare,
						0);
					let percentage;
					if (priceChartrevenuemonthline > pricecompareChartrevenuemonthline) {
						if (pricecompareChartrevenuemonthline !== 0) {
							percentage = (priceChartrevenuemonthline / pricecompareChartrevenuemonthline) * 100;
						} else {
							percentage = 100;
						}
					} else if (priceChartrevenuemonthline < pricecompareChartrevenuemonthline) {
						if (priceChartrevenuemonthline !== 0) {
							percentage = (pricecompareChartrevenuemonthline / priceChartrevenuemonthline) * 100;
						} else {
							percentage = 100;
						}
					} else if (priceChartrevenuemonthline == pricecompareChartrevenuemonthline) {
						percentage = 0;
					}
					let percentageElement = document.querySelector(
						'.description-block .iconrevenueyearline .numberrevenueyearline');
					let headerElement = document.querySelector('.description-block .totalrevenueyearline');
					let formattedPrice = priceChartrevenuemonthline.toLocaleString('vi-VN', {
						style: 'currency',
						currency: 'VND'
					});
					if (priceChartrevenuemonthline > pricecompareChartrevenuemonthline) {
						iconrevenueyearline.className = "fa-solid fa-caret-up";
						percentageElement.textContent = percentage.toFixed(0) + '%';
						headerElement.textContent = formattedPrice;
					} else if (priceChartrevenuemonthline < pricecompareChartrevenuemonthline) {
						iconrevenueyearline.className = "fa-solid fa-caret-down text-danger";
						percentageElement.textContent = '-' + percentage.toFixed(0) + '%';
						headerElement.textContent = formattedPrice;
					} else {
						iconrevenueyearline.className = "";
						percentageElement.textContent = percentage.toFixed(0) + '%';
						headerElement.textContent = formattedPrice;
					}
				});
		}

		// hàm update giá trị biểu đồ order cate year pie
		function updateChartordercatemonthpie(year) {
			fetch('/api/ad/statistical/pie/year?year=' + year)
				.then(response => response.json())
				.then(data => {
                    var check = 0;
                    var categorySelectHTML;
					// Sử dụng map để chuyển đổi danh sách thành mảng
					var id = data.map(item => item.id);
					var labels = data.map(item => item.name);
					var values = data.map(item => item.countProductSell);
					// Cập nhật dữ liệu cho biểu đồ
					myChart2.data.labels = labels;
					myChart2.data.datasets[0].data = values;
					myChart2.update();
                    
                    data.forEach(item => {
                        if (check == 0) {
                            categorySelectHTML += `<option value="${item.id}" selected>${item.name}</option>`
                        } else {
                            categorySelectHTML += `<option value="${item.id}">${item.name}</option>`
                        }
						check++;
                    });
                    
                    categorySelect.innerHTML = categorySelectHTML;
					updateListProductByStatus(yearSelectcategoryyearpie.value, categorySelectedValue.value);
				});
		}

		// hàm update giá trị biểu đồ order month pie
		function updateChartordermonthpie(year, month) {
			fetch('/api/ad/order/pie/month?year=' + year + '&month=' + month)
				.then(response => response.json())
				.then(data => {
					console.log(data);
					// Sử dụng map để chuyển đổi danh sách thành mảng
					var id = data.map(item => item.id);
					var labels = data.map(item => item.name);
					var values = data.map(item => item.countProductSell);
					// Cập nhật dữ liệu cho biểu đồ
					myChart3.data.labels = labels;
					myChart3.data.datasets[0].data = values;
					myChart3.update();
				});
		}

		// hàm update giá trị biểu đồ order year pie
		function updateChartorderyearpie(year) {
			fetch('/api/ad/order/pie/year?year=' + year)
				.then(response => response.json())
				.then(data => {
					console.log(data);
					// Sử dụng map để chuyển đổi danh sách thành mảng
					var id = data.map(item => item.id);
					var labels = data.map(item => item.name);
					var values = data.map(item => item.countProductSell);
					// Cập nhật dữ liệu cho biểu đồ
					myChart4.data.labels = labels;
					myChart4.data.datasets[0].data = values;
					myChart4.update();
				});
		}

		function updateListProductByStatus(year, id) {
			fetch('/api/admin/listproductcate?year=' + year + '&id=' + id)
				.then(response => response.json())
				.then(data => {
					console.log(id);
					console.log(year);
					const tableBody = document.getElementById('productTableBody');
					tableBody.innerHTML = '';

					// Populate the table with the received data
					data.forEach(product => {
						const row = document.createElement('tr');
						row.innerHTML = `
							<td class="product-name">${product.id}</td>
							<td class="product-name">${product.name}</td>
							<td class="text-center">${product.soldCount}</td>
							<td>${product.sellerProduct.username}</td>
							<td><a href="#"><i class="fas fa-eye"></i></a></td>
						`;
						tableBody.appendChild(row);
					});
					tableBody.style.height = 200;
					tableBody.style.overflowY = scroll;
				});
		}

		// Lấy thẻ select theo id
		var monthSelectrevenuemonthline = document.getElementById('monthSelectrevenuemonthline');
		var yearSelectrevenuemonthline = document.getElementById('yearSelectrevenuemonthline');
		var yearSelectrevenueyearline = document.getElementById('yearSelectrevenueyearline');
		var yearSelectcategoryyearpie = document.getElementById('yearSelectcategoryyearpie');
		var categorySelectedValue = document.getElementById('categorySelect');
		var monthSelectordermonthpie = document.getElementById('monthSelectordermonthpie');
		var yearSelectordermonthpie = document.getElementById('yearSelectordermonthpie');
		var yearSelectorderyearpie = document.getElementById('yearSelectorderyearpie');

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
			yearSelectrevenueyearline.appendChild(option);
		}

		var currentYearYearpie = new Date().getFullYear();
		for (var i = 0; i < 5; i++) {
			var year = currentYearYearline - i;
			var option = document.createElement('option');
			option.value = year;
			option.textContent = year;
			yearSelectcategoryyearpie.appendChild(option);
		}

		for (var i = 1; i <= 12; i++) {
			var option = document.createElement('option');
			option.value = i;
			option.textContent = i;
			monthSelectordermonthpie.appendChild(option);
		}

		var currentYearOrderMonthPie = new Date().getFullYear();
		for (var i = 0; i < 5; i++) {
			var year = currentYearOrderMonthPie - i;
			var option = document.createElement('option');
			option.value = year;
			option.textContent = year;
			yearSelectordermonthpie.appendChild(option);
		}

		var currentYearOrderYearPie = new Date().getFullYear();
		for (var i = 0; i < 5; i++) {
			var year = currentYearOrderYearPie - i;
			var option = document.createElement('option');
			option.value = year;
			option.textContent = year;
			yearSelectorderyearpie.appendChild(option);
		}

		// Gắn sự kiện change cho các thẻ select
		// sự kiện tháng của biểu đồ line
		monthSelectrevenuemonthline.addEventListener('change', function () {
			updateChartrevenuemonthline(yearSelectrevenuemonthline.value, monthSelectrevenuemonthline.value);
		});
		//sự kiện năm của biểu đồ line
		yearSelectrevenuemonthline.addEventListener('change', function () {
			updateChartrevenuemonthline(yearSelectrevenuemonthline.value, monthSelectrevenuemonthline.value);
		});
		//sự kiện năm của biểu đồ line
		yearSelectrevenueyearline.addEventListener('change', function () {
			updateChartrevenueyearline(yearSelectrevenueyearline.value);
		});
		//sự kiện năm của biểu đồ pie
		yearSelectcategoryyearpie.addEventListener('change', function () {
			updateChartordercatemonthpie(yearSelectcategoryyearpie.value);
		});
		// sự kiện chọn category
		categorySelectedValue.addEventListener('change', function () {
			updateListProductByStatus(yearSelectcategoryyearpie.value, categorySelectedValue.value);
		});
		// sự kiện tháng của biểu đồ order month pie
		monthSelectordermonthpie.addEventListener('change', function () {
			updateChartordermonthpie(yearSelectordermonthpie.value, monthSelectordermonthpie.value);
		});
		//sự kiện năm của biểu đồ order month pie
		yearSelectordermonthpie.addEventListener('change', function () {
			updateChartordermonthpie(yearSelectordermonthpie.value, monthSelectordermonthpie.value);
		});
		//sự kiện năm của biểu đồ order year pie
		yearSelectorderyearpie.addEventListener('change', function () {
			updateChartorderyearpie(yearSelectorderyearpie.value);
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
					label: 'Doanh thu tháng trước',
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

		// Biểu đồ 2
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
					label: 'Doanh thu năm trước',
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

		// biểu đồ 3
		myChart2 = new Chart(ctx2, {
			type: 'doughnut',
			data: {
				labels: [],
				datasets: [{
					data: [],
					backgroundColor: [
						'#34495E', // Màu xám đậm
						'#F39C12', // Màu cam nhạt
						'#F1C40F', // Màu vàng đậm
						'#3498DB', // Màu xanh dương đậm
						'#9B59B6', // Màu tím đậm
					],
				}],
			},
			options: {
				responsive: true,
				plugins: {
					title: {
						display: false,
						text: 'Biểu đồ thống kê loại sản phẩm bán chạy theo tháng',
					},
				},
			},
		});

		// biểu đồ 4
		myChart3 = new Chart(ctx3, {
			type: 'doughnut',
			data: {
				labels: [],
				datasets: [{
					data: [],
					backgroundColor: [
						'#F39C12', // Màu cam nhạt
						'#F1C40F', // Màu vàng đậm
						'#32CD32', // Màu xanh lá
						'#CD5C5C', // Màu đỏ nhạt
						'#DC143C', // Màu đỏ đậm
					],
				}],
			},
			options: {
				responsive: true,
				plugins: {
					title: {
						display: false,
						text: 'Biểu đồ thống kê hóa đơn theo tháng',
					},
				},
			},
		});

		// biểu đồ 5
		myChart4 = new Chart(ctx4, {
			type: 'doughnut',
			data: {
				labels: [],
				datasets: [{
					data: [],
					backgroundColor: [
						'#F39C12', // Màu cam nhạt
						'#F1C40F', // Màu vàng đậm
						'#32CD32', // Màu xanh lá
						'#CD5C5C', // Màu đỏ nhạt
						'#DC143C', // Màu đỏ đậm
					],
				}],
			},
			options: {
				responsive: true,
				plugins: {
					title: {
						display: false,
						text: 'Biểu đồ thống kê hóa đơn theo năm',
					},
				},
			},
		});

		// Tự động chọn tháng và năm hiện tại
		monthSelectrevenuemonthline.value = new Date().getMonth() + 1;
		yearSelectrevenuemonthline.value = currentYearMonthline;
		yearSelectrevenueyearline.value = currentYearYearline;
		yearSelectcategoryyearpie.value = currentYearYearpie;
		categorySelectedValue.value = document.getElementById('categorySelect').value;
		monthSelectordermonthpie.value = new Date().getMonth() + 1;
		yearSelectordermonthpie.value = currentYearOrderMonthPie;
		yearSelectorderyearpie.value = currentYearOrderYearPie;

		// Gọi hàm cập nhật ban đầu để hiển thị dữ liệu
		updateChartrevenuemonthline(yearSelectrevenuemonthline.value, monthSelectrevenuemonthline.value);
		updateChartrevenueyearline(yearSelectrevenueyearline.value);
		updateChartordercatemonthpie(yearSelectcategoryyearpie.value);
		updateChartordermonthpie(yearSelectordermonthpie.value, monthSelectordermonthpie.value);
		updateChartorderyearpie(yearSelectorderyearpie.value);
	