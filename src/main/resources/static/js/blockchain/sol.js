// // const { LAMPORTS_PER_SOL } = require("@solana/web3.js");
// // import "@solana/web3.js";

// const walletAddress = "A5jsvxmGpe2sj3G1aBv6iiF5jNQ9nfujTJhzSuJ74ozd";
// const publicKeyN = "Wsz8UnrdvfktCuNqeWZ2jtMWmjcXq4ba61uk5xPrTPa";
// const privateKey = new Uint8Array[235, 184, 104, 220, 166, 53, 215, 53, 12, 165, 69, 91, 235, 110, 63, 53, 220, 111, 77, 120, 3, 65, 83, 94, 29, 187, 12, 205, 80, 121, 229, 125, 134, 239, 252, 238, 3, 170, 82, 64, 55, 23, 139, 119, 71, 215, 216, 61, 131, 26, 21, 192, 250, 12, 104, 2, 78, 113, 118, 145, 66, 152, 94, 126];


// const connection = new solanaWeb3.Connection('https://api.devnet.solana.com', 'confirmed');

// // Chuyển địa chỉ ví từ dạng chuỗi thành đối tượng PublicKey
// const publicKey = new solanaWeb3.PublicKey(walletAddress);
// const publicKeyNN = new solanaWeb3.PublicKey(publicKeyN);

// // Lấy số dư của ví
// connection.getBalance(publicKey).then((balance) => {
//     console.log(`Balance: ${balance / solanaWeb3.LAMPORTS_PER_SOL} SOL`);
// });



// // Tạo đối tượng Transaction
// const transaction = new solanaWeb3.Transaction().add(
//     solanaWeb3.SystemProgram.transfer({
//         fromPubkey: new solanaWeb3.PublicKey(walletAddress),
//         toPubkey: new solanaWeb3.PublicKey(publicKeyNN),
//         lamports: solanaWeb3.LAMPORTS_PER_SOL * 0.001, // Số lượng SOL muốn chuyển
//     })
// );

// // Ký và gửi giao dịch
// solanaWeb3.sendAndConfirmTransaction(connection, transaction, privateKey)
//     .then((signature) => {
//         console.log('Transaction Signature:', signature);
//     })
//     .catch((error) => {
//         console.error('Transaction Error:', error);
//     });
import { Connection } from "@solana/web3.js";
const app = angular.module("app", []);
app.controller("solanaWeb3", function ($scope, $http, $location, $window) {

});