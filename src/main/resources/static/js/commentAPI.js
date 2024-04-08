app.controller("comment-ctrl", function ($scope, $http, $location, $window) {
    var $comment = ($scope.comment = {

        productID: null,
        commentList: null,
        commentListWithoutSeller: null,
        totalStar: 0,
        sortRatingByStar: [0,0,0,0,0],
        content: 'hao đẹp trai',
        modalCounter : 0,
        activeOrderDetailID: null,
        openModal(orderDetailID) {
            console.log(orderDetailID)
            this.activeOrderDetailID = orderDetailID;
            $('#formReply').modal('show');
        },
        hideModal() {
            $('#formReply').modal('hide');
        },
        // Hàm để tải danh sách bình luận từ server
        loadAllComments() {
            console.log(this.productID)
            var url = `${host}/comment/${this.productID}`;
            console.log(url);
            $http.get(url).then((response) => {
                this.commentList = response.data;
                this.loadCommentsListWithOutSeller();
                console.log(this.commentList);
                console.log
            })
                .catch(function (error) {
                    console.error('Lỗi khi loadComments():', error);
                });
        }
        ,

        loadAllCommentsByStar(ratting) {
            console.log(this.productID)
            var url = `${host}/commentbystar/${this.productID}`;
            console.log(url);
            $http.post(url,ratting).then((response) => {
                this.commentList = response.data;
                this.loadCommentsListWithOutSellerAndByStar(ratting);
                console.log(this.commentList);
            })
                .catch(function (error) {
                    console.error('Lỗi khi loadAllCommentsByStar():', error);
                });
        },

        // Hàm để thêm bình luận mới
        addComment() {
            var url = `${host}/comment/add/${this.activeOrderDetailID}`;
            const content = this.content;
            $http.post(url, content)
                .then(function (response) {
                    $comment.loadAllComments();
                    // Xóa nội dung trong form
                    this.content = '';
                    $comment.modalCounter++;
                    $('#formReply').modal('hide');
                })
                .catch(function (error) {
                    console.error('Lỗi khi thêm bình luận:', error);
                });
        },
        loadCommentsListWithOutSeller() {
            console.log(this.productID)
            var url = `${host}/commentwtseller/${this.productID}`;
            console.log(url);
            $http.get(url).then((response) => {
                this.commentListWithoutSeller = response.data;
                this.totalStar = this.loadStarandRating();
                console.log(this.sortRatingByStar)
                console.log(this.totalStar);
            })
                .catch(function (error) {
                    console.error('Lỗi khi commentListWithoutSeller():', error);
                });
        }
        ,
        loadCommentsListWithOutSellerAndByStar(ratting) {
            console.log(this.productID)
            var url = `${host}/commentwtsellerbystar/${this.productID}`;
            console.log(url);
            $http.post(url,ratting).then((response) => {
                this.commentListWithoutSeller = response.data;
                console.log("cmt wt Seller"+this.commentListWithoutSeller)
            })
                .catch(function (error) {
                    console.error('Lỗi khi commentListWithoutSeller():', error);
                });
        }
        ,
        loadStarandRating() {
            this.sortRatingByStar = [0,0,0,0,0];
            var totalRating = 0;
            var numComments = this.commentListWithoutSeller.length;
            for (var i = 0; i < numComments; i++) {
                totalRating += this.commentListWithoutSeller[i].comment.rating;
                var countRatingBystar = this.commentListWithoutSeller[i].comment.rating;
                this.sortRatingByStar[countRatingBystar-1] += 1;
            }
            var averageRating = numComments > 0 ? (totalRating / numComments) : 0;

            // Làm tròn trung bình cộng tới 1 số sau dấu phẩy
            return averageRating.toFixed(1);
        },

    });
    $scope.init = function () {
        $scope.comment.productID = angular.element(document.getElementById('productID')).val();
    };

    $scope.init();
    $comment.loadAllComments();

});

