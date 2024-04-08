const app = angular.module("app", []);
app.controller("myController", function ($scope, $http, $window) {

});

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
  
  