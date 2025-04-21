// // var stompClient = null;

// // function connectWebSocket() {
// //     var socket = new SockJS('/ws');
// //     stompClient = Stomp.over(socket);
// //     stompClient.connect({}, function (frame) {
// //         console.log('Connected: ' + frame);
// //         stompClient.subscribe('/topic/messages', function (message) {
// //             displayMessage(JSON.parse(message.body));
// //         });
// //     });
// // }

// // function displayMessage(message) {
// //     var messagesDiv = $("#messages");
// //     messagesDiv.append("<p><strong>" + message.sender + ":</strong> " + message.content + "</p>");
// // }

// // function sendMessage() {
// //     var messageInput = $("#messageInput");
// //     var content = messageInput.val().trim();
// //     if (content !== "") {
// //         stompClient.send("/app/chat", {}, JSON.stringify({ content: content }));
// //         messageInput.val("");
// //     }
// // }

// $(document).ready(function () {
//     connectWebSocket();
// });

// // app.js
// const socket = new SockJS('https://devex.io.vn/ws');
// const stompClient = Stomp.over(socket);

// stompClient.connect({}, () => {
//     stompClient.subscribe('/user/topic/message', (response) => {
//         const message = JSON.parse(response.body);
//         displayMessage(message.sender, message.content);
//     });
// }, (error) => {
//     console.error('Error connecting to WebSocket:', error);
// });

// function sendMessage(recipient) {
//     const inputId = `${recipient}MessageInput`;
//     const messageInput = document.getElementById(inputId).value;
    
//     if (messageInput.trim() !== '') {
//         const message = {
//             content: messageInput,
//             sender: 'guest',
//             recipient: recipient,
//         };
//         stompClient.send('/app/message', {}, JSON.stringify(message));
//         document.getElementById(inputId).value = '';
//     }
// }

// function displayMessage(sender, content) {
//     const chatId = `${sender.toLowerCase()}Chat`;
//     const chatDiv = document.getElementById(chatId);
//     const messageDiv = document.createElement('div');
//     messageDiv.textContent = `${sender}: ${content}`;
//     chatDiv.appendChild(messageDiv);
//     chatDiv.scrollTop = chatDiv.scrollHeight;
// }

