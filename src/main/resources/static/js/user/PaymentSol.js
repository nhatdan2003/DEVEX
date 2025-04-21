// console.log(window.phantom.solana);


// app.controller("Payment_Sol", function ($scope, $http, $location, $window) {

//     // AUTO CONNECTIONS //
//     (async () => {
//         await window.phantom.solana.connect();

//     })();

//     const $Solana = ($scope.Solana = {

//         solanaPriceUSD: null,
//         // lấy giá tiền hiện tại của Sol; 
//         getSolanaPrice: () => {

//             $http.get('https://api.coingecko.com/api/v3/simple/price?ids=solana&vs_currencies=usd')
//                 .then(function (response) {
//                     // Xử lý phản hồi thành công
//                     this.solanaPriceUSD = response.data.solana.usd; // Lấy giá trị của SOL trong USD
//                     // console.log(this.solanaPriceUSD)
//                     console.log("solanaPriceUSD: " + this.solanaPriceUSD);
//                 })
//                 .catch(function (error) {
//                     // Xử lý phản hồi lỗi
//                     console.error('Đã xảy ra lỗi khi lấy giá của Solana:', error.message);
//                 });

//         },
//         toTransactions: (encodedTransaction) => solanaWeb3.Transaction.from(Uint8Array.from(atob(encodedTransaction), c => c.charCodeAt(0))),

//         sendSol: async () => {
//             var myHeaders = new Headers();
//             myHeaders.append("x-api-key", "-h5nBPVh3pIsuGRE"); // lấy API bên Shyft để thực hiện viẹc mint NFT
//             myHeaders.append("Content-Type", "application/json");

//             // const publicKeySender = window.phantom.solana.

//             var raw = JSON.stringify(
//                 {
//                     "network": "devnet",
//                     "from_address": "A5jsvxmGpe2sj3G1aBv6iiF5jNQ9nfujTJhzSuJ74ozd", // lấy từ windown.phantom để trả về id wallet
//                     "to_address": "956WAyd7EW8a8jytQ3zzKgnZZc7aEURHfyYjK7urb91g", // chuyển đến mặc định là tài khoản admin
//                     "amount": 0.1
//                 }
//             );

//             var requestOptions = {
//                 method: 'POST',
//                 headers: myHeaders,
//                 body: raw,
//                 redirect: 'follow'
//             };

//             fetch("https://api.shyft.to/sol/v1/wallet/send_sol", requestOptions)
//                 .then(async response => {
//                     let res = await response.json();
//                     let transaction = toTransactions(res.result.encode_transactions);
//                     const signedTransaction = await window.phantom.solana.signTransaction(transaction);
//                     const connection = new solanaWeb3.Connection(solanaWeb3.clusterApiUrl("devnet"));
//                     const signature = await connection.sendRawTransaction(signedTransaction.serialize());

//                 })
//                 .catch(error => console.log('error', error));
//         },


//     })

//     console.log(totalPriceElement);
//     $Solana.getSolanaPrice();
// });
// // mint NFT ||
// // var formdata = new FormData();
// // formdata.append("network", "devnet");
// // formdata.append("wallet", "2fmz8SuNVyxEP6QwKQs6LNaT2ATszySPEJdhUDesxktc");
// // formdata.append("name", "SHYFT NFT");
// // formdata.append("symbol", "SH");
// // formdata.append("description", "Shyft makes Web3 so easy!");
// // formdata.append("attributes", '[{"trait_type":"dev power","value":"over 900"}]');
// // formdata.append("external_url", "https://shyft.to");
// // formdata.append("max_supply", "0");
// // formdata.append("royalty", "5");
// // formdata.append("file", fileInput.files[0], "index.png");
// // formdata.append("nft_receiver", "5KW2twHzRsAaiLeEx4zYNV35CV2hRrZGw7NYbwMfL4a2");
// // formdata.append('service_charge', '{ "receiver": "499qpPLdqgvVeGvvNjsWi27QHpC8GPkPfuL5Cn2DtZJe",  "token": "DjMA5cCK95X333t7SgkpsG5vC9wMk7u9JV4w8qipvFE8",  "amount": 0.01}');
