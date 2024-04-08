const app = angular.module("app", []);
app.controller("myController", function ($scope, $http, $window) {
  $scope.data = []; 
  $scope.products = [];
  var content = document.getElementById('content');
	var contentfalse = document.getElementById('contentfalse');

  $scope.fillSeller = function () {
    $http.get('/api/shop').then(resp => {
      $scope.data = resp.data;
      document.querySelector('.profile-user-img').src = $scope.data.imageuser;
    }).catch(function (err) {
      console.error(err);
    });
  };

  // info product
  $scope.fillProducts = function () {
    $http.get('/api/seller/product').then(resp => {
      $scope.products = resp.data;
    }).catch(function (err) {
      console.error(err); 
    });
  };

  $scope.fillSeller(); 
  $scope.fillProducts();

  $scope.updateShop = function () {
    var data = {
      shopName: $scope.data.seller.shopName,
      address: $scope.data.seller.address,
      phoneAddress: $scope.data.seller.phoneAddress,
      mall: $scope.data.seller.mall,
      description: $scope.data.seller.description
    };
    console.log(data);
    $http.put('/api/updateShop', data).then(response => {
      // Xử lý kết quả sau khi cập nhật thành công
      document.getElementById('success').click();
    }).catch(error => {
      console.error('Có lỗi xảy ra khi cập nhật', error);
      // Xử lý lỗi nếu có
      // document.getElementById('false').click();
      document.getElementById('success').click();
    });
  };

  // chuyển trang
  $scope.productDetail = function (id) {
    $window.location.href = '/details/' + id;
  };

  // chuyển trang
  $scope.editProduct = function (id) {
    $window.location.href = '/seller/product/edit/' + id;
  };

  $scope.openImageUploader = function () {
    // Tạo một input[type="file"] ẩn để người dùng chọn tệp hình ảnh.
    var fileInput = document.createElement('input');
    fileInput.type = 'file';
    fileInput.accept = 'image/*';

    fileInput.addEventListener('change', function (event) {
        var file = event.target.files[0];
        if (file) {
            var formData = new FormData();
            formData.append('file', file);

            $http.post('/api/updateimageprofile', formData, {
                transformRequest: angular.identity,
                headers: { 'Content-Type': undefined }
            }).then(function (response) {
                // Xử lý thành công nếu cần
                document.getElementById('profile-image').src = URL.createObjectURL(file);
                console.log('Hình ảnh đã được tải lên thành công');
            }, function (error) {
                // Xử lý lỗi nếu cần
                console.error('Lỗi khi tải lên hình ảnh: ' + error.status);
            });
        }
    });

    // Tự động kích hoạt sự kiện click cho input[type="file"] ẩn.
    fileInput.click();
};

});
// END ANGULARJS

// History
function fillHistory() {
  fetch('/api/seller/history')
    .then(response => response.json())
    .then(data => {
    var listHistory = data;
    var historiesHTML = '';
    listHistory.forEach(history => {
      var nowHistory = new Date();
      var createdDate = new Date(history.createdDay);
      var minutesHistory = Math.floor(((nowHistory.getTime() - new Date(history.createdDay).getTime()) / 1000) / 60);
      var hoursHistory = Math.floor(minutesHistory / 60);
      var daysHistory = Math.floor(hoursHistory / 24);
      var day = createdDate.getDate();
      var month = createdDate.getMonth() + 1;
      var year = createdDate.getFullYear();
      var hours = createdDate.getHours();
      var minutes = createdDate.getMinutes();
      var historyHTML = '';
      if (minutesHistory <= 60) {
      historyHTML = `
        <div class="time-label">
        <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
        </div>
        <div>
        <div class="timeline-item">
          <span class="time"><i class="far fa-clock"></i> ${minutesHistory} minutes ago</span>
          <h3 class="timeline-header border-0">${history.content}</h3>
        </div>
        </div>
      `;
      } else if (hoursHistory <= 24) {
      historyHTML = `
        <div class="time-label">
        <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
        </div>
        <div>
        <div class="timeline-item">
          <span class="time"><i class="far fa-clock"></i> ${hoursHistory} hours ago</span>
          <h3 class="timeline-header border-0">${history.content}</h3>
        </div>
        </div>
      `;
      } else {
      historyHTML = `
        <div class="time-label">
        <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
        </div>
        <div>
        <div class="timeline-item">
          <span class="time"><i class="far fa-clock"></i> ${daysHistory} days ago</span>
          <h3 class="timeline-header border-0">${history.content}</h3>
        </div>
        </div>
      `;
      }
      historiesHTML += historyHTML;
    });
    historiesHTML += '<div><i class="far fa-clock bg-gray"></i></div>';
    document.getElementById('history').innerHTML = historiesHTML;
    });
  }

  fillHistory();

  // fillAllNotifications
  function fillAllNotifications() {
  fetch('/api/user/allnotifications')
    .then(response => response.json())
    .then(data => {
    var listNotifications = data;
    var notificationsHTML = '';
    listNotifications.forEach(notification => {
      var nowNotification = new Date();
      var createdDate = new Date(notification.createdDay);
      var minutesNotification = Math.floor(((nowNotification.getTime() - new Date(notification.createdDay).getTime()) / 1000) / 60);
      var hoursNotification = Math.floor(minutesNotification / 60);
      var daysNotification = Math.floor(hoursNotification / 24);
      var day = createdDate.getDate();
      var month = createdDate.getMonth() + 1;
      var year = createdDate.getFullYear();
      var hours = createdDate.getHours();
      var minutes = createdDate.getMinutes();
      var notificationHTML = '';
      if(notification.link == null) {
        if (notification.status == false) {
          if (minutesNotification <= 60) {
            notificationHTML = `
              <div class="time-label">
                <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
              </div>
              <div>
                <div class="timeline-item" id="${notification.id}" style="background-color: #ECECEC;">
                  <span class="time"><i class="far fa-clock"></i> ${minutesNotification} minutes ago</span>
                  <h3 class="timeline-header border-0">${notification.content}</h3>
                </div>
              </div>
            `;
          } else if (hoursNotification <= 24) {
            notificationHTML = `
              <div class="time-label">
                <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
              </div>
              <div>
                <div class="timeline-item" id="${notification.id}" style="background-color: #ECECEC;">
                  <span class="time"><i class="far fa-clock"></i> ${hoursNotification} hours ago</span>
                  <h3 class="timeline-header border-0">${notification.content}</h3>
                </div>
              </div>
            `;
          } else {
            notificationHTML = `
              <div class="time-label">
              <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
              </div>
              <div>
                <div class="timeline-item" id="${notification.id}" style="background-color: #ECECEC;">
                  <span class="time"><i class="far fa-clock"></i> ${daysNotification} days ago</span>
                  <h3 class="timeline-header border-0">${notification.content}</h3>
                </div>	
              </div>
            `;
          }
        } else {
          if (minutesNotification <= 60) {
            notificationHTML = `
              <div class="time-label">
                <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
              </div>
              <div>
                <div class="timeline-item bg-white" id="${notification.id}">
                  <span class="time"><i class="far fa-clock"></i> ${minutesNotification} minutes ago</span>
                  <h3 class="timeline-header border-0">${notification.content}</h3>
                </div>
              </div>
            `;
          } else if (hoursNotification <= 24) {
            notificationHTML = `
              <div class="time-label">
                <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
              </div>
              <div>
                <div class="timeline-item bg-white" id="${notification.id}">
                  <span class="time"><i class="far fa-clock"></i> ${hoursNotification} hours ago</span>
                  <h3 class="timeline-header border-0">${notification.content}</h3>
                </div>
              </div>
            `;
          } else {
            notificationHTML = `
              <div class="time-label">
                <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
              </div>
              <div>
                <div class="timeline-item bg-white" id="${notification.id}">
                  <span class="time"><i class="far fa-clock"></i> ${daysNotification} days ago</span>
                  <h3 class="timeline-header border-0">${notification.content}</h3>
                </div>
              </div>
            `;
          }
        }
      }else {
        if (notification.status == false) {
          if (minutesNotification <= 60) {
            notificationHTML = `
              <div class="time-label">
                <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
              </div>
              <div>
                <div class="timeline-item" id="${notification.id}" style="background-color: #ECECEC;">
                  <span class="time"><i class="far fa-clock"></i> ${minutesNotification} minutes ago</span>
                  <h3 class="timeline-header border-0">${notification.content}
                    <button class="btn btn-primary">Xem chi tiết</button></h3>
                </div>
              </div>
            `;
          } else if (hoursNotification <= 24) {
            notificationHTML = `
              <div class="time-label">
                <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
              </div>
              <div>
                <div class="timeline-item" id="${notification.id}" style="background-color: #ECECEC;">
                  <span class="time"><i class="far fa-clock"></i> ${hoursNotification} hours ago</span>
                  <h3 class="timeline-header border-0">${notification.content}
                    <button class="btn btn-primary">Xem chi tiết</button></h3>
                </div>
              </div>
            `;
          } else {
            notificationHTML = `
              <div class="time-label">
              <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
              </div>
              <div>
                <div class="timeline-item" id="${notification.id}" style="background-color: #ECECEC;">
                  <span class="time"><i class="far fa-clock"></i> ${daysNotification} days ago</span>
                  <h3 class="timeline-header border-0">${notification.content}
                    <button class="btn btn-primary">Xem chi tiết</button></h3>
                </div>
              </div>
            `;
          }
        } else {
          if (minutesNotification <= 60) {
            notificationHTML = `
              <div class="time-label">
                <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
              </div>
              <div>
                <div class="timeline-item bg-white" id="${notification.id}">
                  <span class="time"><i class="far fa-clock"></i> ${minutesNotification} minutes ago</span>
                  <h3 class="timeline-header border-0">${notification.content}
                    <button class="btn btn-primary">Xem chi tiết</button></h3>
                </div>
              </div>
            `;
          } else if (hoursNotification <= 24) {
            notificationHTML = `
              <div class="time-label">
                <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
              </div>
              <div>
                <div class="timeline-item bg-white" id="${notification.id}">
                  <span class="time"><i class="far fa-clock"></i> ${hoursNotification} hours ago</span>
                  <h3 class="timeline-header border-0">${notification.content}
                    <button class="btn btn-primary">Xem chi tiết</button></h3>
                </div>
              </div>
            `;
          } else {
            notificationHTML = `
              <div class="time-label">
                <span class="bg-success"><i class="far fa-clock bg-success"></i> ${day}/${month}/${year} ${hours}:${(minutes < 10 ? '0' : '')}${minutes}</span>
              </div>
              <div>
                <div class="timeline-item bg-white" id="${notification.id}">
                  <span class="time"><i class="far fa-clock"></i> ${daysNotification} days ago</span>
                  <h3 class="timeline-header border-0">${notification.content}
                    <button class="btn btn-primary">Xem chi tiết</button></h3>
                </div>
              </div>
            `;
          }
        }
      }
      
      notificationsHTML += notificationHTML;
    });
    notificationsHTML += '<div><i class="far fa-clock bg-gray"></i></div>';
    document.getElementById('allnotifications').innerHTML = notificationsHTML;
    });
  }

  fillAllNotifications();

