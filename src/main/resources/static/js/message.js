let host_mess = "https://devex.io.vn/rest/message";
//Socket
const socket = new SockJS('https://devex.io.vn/ws');
const stompClient = Stomp.over(socket);


app.controller("message-ctrl", function ($scope, $http, $window) {
  $scope.formatDateToDDMMYYYY = function (dateString) {
    const date = new Date(dateString); // Chuyển chuỗi thời gian thành đối tượng Date
    const options = { day: "numeric", month: "numeric", year: "numeric" };
    return date.toLocaleDateString("vi-VN", options); // Chuyển đổi sang "dd/MM/yyyy"
  };

  $scope.formatDateToCustomString = function (dateString) {
    const date = new Date(dateString);
    const months = [
      "Jan",
      "Feb",
      "Mar",
      "Apr",
      "May",
      "Jun",
      "Jul",
      "Aug",
      "Sep",
      "Oct",
      "Nov",
      "Dec",
    ];

    const hours = date.getHours();
    const minutes = date.getMinutes();
    const month = months[date.getMonth()];
    const day = date.getDate();

    // Định dạng giờ theo AM/PM
    const period = hours >= 12 ? "PM" : "AM";
    const formattedHours = hours % 12 || 12;

    // Chuỗi kết quả
    const formattedString = `${formattedHours}:${
      minutes < 10 ? "0" : ""
    }${minutes} ${period} | ${month} ${day}`;

    return formattedString;
  };

  $scope.formatDateTimeToDDMMYYYYHHMM = function (dateString) {
    const date = new Date(dateString);
    const options = {
      day: "numeric",
      month: "numeric",
      year: "numeric",
      hour: "numeric",
      minute: "numeric",
    };
    return date.toLocaleDateString("vi-VN", options);
  };

  //Quản lí tin nhắn
  var $message = ($scope.message = {
    list: [],
    listShop: [],
    showMessageChecked: [],
    groupMessageChat: {},
    selectedIdReceiver: "",
    newMessage : "",

    // load tin nhắn
    loadMessage() {
      var url = `${host_mess}/list`;
      $http.get(url).then((response) => {
        this.list = response.data;
        this.showGroupChat();
        console.log(this.list);
        console.log(this.groupMessageChat);
      });
    },

    // load shop
    loadShopName() {
      var url = `${host_mess}/list-shop`;
      $http.get(url).then((response) => {
        this.listShop = response.data;
        console.log(this.listShop);
      });
    },

    //hiện thị tên shop nếu đó là shop
    checkShopName(id, fullName) {
      var user = this.listShop.find((item) => item.userID === id);
      if(user == null) {
        return fullName;
      }else {
        return user.shopName;
      }
      
    },


    //hiện thị đoạn chat mình chọn
    showMessageChatOne(idUser) {
      let arr = this.groupMessageChat[idUser]
      this.showMessageChecked = [...arr];
	   // Sắp xếp các sản phẩm trong từng cụm theo CreatedAt tăng dần
	   this.showMessageChecked.sort((a, b) => {
        const dateA = new Date(a.createdAt);
        const dateB = new Date(b.createdAt);
        return dateA - dateB;
      });
      
      this.selectedIdReceiver = idUser;
    },

    //hiện thị đối tượng chat sidepart
    showGroupChat() {
      this.groupMessageChat = {};
      this.list.forEach((item) => {
		if(item.senderID === item.userID) {
			if (!this.groupMessageChat[item.receiverID]) {
				this.groupMessageChat[item.receiverID] = [];
			  }
			  this.groupMessageChat[item.receiverID].push(item);
		}

    if(item.receiverID === item.userID) {
			if (!this.groupMessageChat[item.senderID]) {
				this.groupMessageChat[item.senderID] = [];
			  }
			  this.groupMessageChat[item.senderID].push(item);
		}
          
      });

      // Chuyển đối tượng thành mảng các cặp key-value
      const objArray = Object.entries(this.groupMessageChat);
      // Sắp xếp mảng objArray dựa trên trường createdAt giảm dần
      objArray.sort((a, b) => {
        const dateA = new Date(a[1][0].createdAt).getTime();
        const dateB = new Date(b[1][0].createdAt).getTime();
        return dateB - dateA;
      });
      // Chuyển lại thành đối tượng từ mảng objArray đã sắp xếp
      this.groupMessageChat = Object.fromEntries(objArray);
    },

    //Socket
    // Connect to WebSocket
    connectSocket() {
      stompClient.connect({}, function () {
          // Subscribe to incoming messages
          console.log("connect thanh cong");
          stompClient.subscribe('/topic/message', function (response) {
              const message  = JSON.parse(response.body);
                  // Add the received message to the chat history
                  $message.showMessageChecked.push(message);
              console.log($message.showMessageChecked);
              $message.loadMessage();
          });

          
      }, function (error) {
          console.error('Error connecting to WebSocket:', error);
      });
    },
    

    // Function to send a message
    sendMessage(receiver) {
      if(this.newMessage.trim() !== '') {
        const message = {
            id: 1,
            content: $message.newMessage,
            createdAt: new Date(),
            senderID: null,
            senderAvatar: null,
            senderName: null,
            receiverID: receiver,
            receiverAvatar: null,
            receiverName: null,
            userID: null,
        };

        // Send the message to the server via WebSocket
        stompClient.send('/app/message', {}, JSON.stringify(message));

        // Clear the input field
        this.newMessage = '';
      }
        
    },

    // Function to send a message
    sendMessageAuto() {
      let userID = document.getElementById('username').value;
        const message = {
            id: 2,
            content: '',
            createdAt: new Date(),
            senderID: userID,
            senderAvatar: null,
            senderName: null,
            receiverID: null,
            receiverAvatar: null,
            receiverName: null,
            userID: null,
        };

        // Send the message to the server via WebSocket
        stompClient.send('/app/message', {}, JSON.stringify(message));

        // Clear the input field
        this.newMessage = '';
        // Sử dụng $location để chuyển hướng đến URL '/detail-order'
         $window.location.href = "/message";
        
    },

    // scrollBoxChat() {
    //   boxChat.scrollTop = boxChat.scrollHeight;
    //   alert(boxChat.scrollHeight);
    // },


  });

  $message.loadMessage();
  $message.loadShopName();
  $message.connectSocket();
});


let boxChat = document.getElementById("box-chat-detail");
// Hàm để cuộn xuống
function scrollBoxChat() {
  boxChat.scrollTop = boxChat.scrollHeight;
}

// Hàm callback của MutationObserver
function handleMutation(mutationsList, observer) {
  for (let mutation of mutationsList) {
    if (mutation.type === 'childList' || mutation.type === 'attributes') {
      // Nếu có sự thay đổi trong childList hoặc attributes, thực hiện cuộn xuống
      scrollBoxChat();
    }
  }
}

// Tạo một MutationObserver với hàm callback là handleMutation
const observer = new MutationObserver(handleMutation);

// Theo dõi sự thay đổi trong childList và attributes của boxChat
if(boxChat != null) {
  observer.observe(boxChat, { childList: true, attributes: true });
}

