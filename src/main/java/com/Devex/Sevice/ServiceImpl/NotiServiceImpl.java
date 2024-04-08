package com.Devex.Sevice.ServiceImpl;

import com.Devex.DTO.MailInfo;
import com.Devex.Entity.Comment;
import com.Devex.Entity.Notifications;
import com.Devex.Entity.User;
import com.Devex.Sevice.MailerService;
import com.Devex.Sevice.NotiService;
import com.Devex.Sevice.NotificationsService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SessionScope
@Service("notiService")
public class NotiServiceImpl implements NotiService {
    @Autowired
    NotificationsService notificationsService;

    @Override
    public void sendNotification(String userFrom, String userTo, String link, String type, String object) {
        String userIDAdmin = "haopg";
        try {
            Notifications noti = new Notifications();
            String content = "";

            switch (type) {
                case "repplycommentcustomer": {
                    content = "Shop " + userFrom + " đã trả lời bình luận của bạn của sản phẩm " + object + ".";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "comment": {
                    content = "Người dùng " + userFrom + " đã bình luận cho sản phẩm " + object + ".";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "newproduct": {
                    content = "Shop " + userFrom + " đã thêm sản phẩm " + object + ".";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "dathang": {
                    content = "Bạn đã đặt một đơn hàng mới có mã đơn hàng là " + object + ".";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "choxacnhan": {
                    content = "Bạn có 1 đơn hàng mới cần xác nhận, mã đơn hàng là " + object + ".";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "dahuycuanguoidungchung": {
                    content = "Đơn hàng có mã " + object + " đã bị hủy do shop từ chối!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "dahuycuanguoidung": {
                    content = "Đơn hàng có mã " + object + " đã bị hủy do shop " + userFrom + " từ chối!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "dahuycuashop": {
                    content = "Người dùng " + userFrom + " đã hủy hóa đơn có mã " + object + "!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "donhangdaxacnhan": {
                    content = "Đơn hàng có mã " + object
                            + " đã được shop xác nhận, trạng thái đơn hàng hiện tại là 'Đang vận chuyển'. ";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "danggiaocuanguoidung": {
                    content = "Đơn hàng có mã " + object
                            + " đang được giao đến bạn, trạng thái đơn hàng hiện tại là 'Đang giao'.";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "danggiaocuashop": {
                    content = "Đơn hàng có mã " + object + " đang được giao đến người dùng " + userTo
                            + " trạng thái đơn hàng hiện tại là 'Đang giao'.";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "nhanhangcuanguoidung": {
                    content = "Đơn hàng có mã " + object
                            + " đã được giao đến bạn, vui lòng thanh toán đơn hàng của bạn!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "nhanhangcuashop": {
                    content = "Đơn hàng có mã " + object + " đã được giao đến người dùng " + userTo + "!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "hoanthanhcuanguoidung": {
                    content = "Đơn hàng có mã " + object + " đã được hoàn thành!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "hoanthanhcuanshop": {
                    content = "Đơn hàng có mã " + object
                            + " đã được hoàn thành, tiền sẽ được cộng vào tài khoản của bạn trong giây lát!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "trahang": {
                    content = "Người dùng " + userFrom
                            + " đã yêu cầu trả hàng/hoàn tiền, để biết thêm thông tin vui lòng ấn vào xem chi tiết!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "welcome": {
                    content = "Chào mừng người dùng " + object
                            + " đã đến với sàn thương mại điện tử DEVEX do team Thiếu Nữ thành lập!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "canhbao": {
                    content = "Bạn đã có hành động vi phạm quy định của DEVEX! Vui lòng không " + object;
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "changeprofileshop": {
                    content = "Shop " + object + " đã thay đổi thông tin của shop!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "follow": {
                    content = "Người dùng " + userFrom + " đã follow bạn!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "unfollow": {
                    content = "Người dùng " + userFrom + " đã hủy follow bạn!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "tangvoucher": {
                    content = "Chúng tôi xin chân thành cảm ơn bạn đã đến với sản thương mại điện tử DEVEX! đây là món quà của chúng tôi gửi đến bạn "
                            + object;
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "updateseller": {
                    content = "Người dùng " + userFrom + " đã yêu cầu nâng cấp tài khoản thành người bán!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "updateUser": {
                    content = "Tài khoản của bạn đã được cập nhật!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "repplyupdatesellerpass": {
                    content = "Chào mừng người dùng " + object
                            + " đã trở thành người bán hàng của DEVEX! Để biết thêm thông tin về người bán bạn vui lòng ấn xem chi tiết!";
                    noti.setContent(content);
                    noti.setUserTo(userIDAdmin);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "repplyupdatesellerfalse": {
                    content = "Người dùng " + userFrom
                            + " đã bị từ chối yêu cầu nâng cấp tài khoản thành người bán! Lý do " + object;
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "feedback": {
                    content = object;
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "duyetbansanpham": {
                    content = "Người dùng " + userFrom + " đã yêu cầu duyệt bán sản phẩm!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "duybansanphampass": {
                    content = "Sản phẩm có id là " + object + " đã được cho phép bán hàng!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "duybansanphamfalse": {
                    content = "Sản phẩm có id là " + object + " bị từ chới cho phép bán hàng! Vì lí do " + object;
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "khachhangmoi": {
                    content = "Người dùng mới " + object + " vừa đăng ký thành công!";
                    noti.setContent(content);
                    noti.setUserTo(userIDAdmin);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "updateProfile": {
                    content = "Người dùng " + object + " vừa chỉnh sử hồ sơ!";
                    noti.setContent(content);
                    noti.setUserTo(userIDAdmin);
                    noti.setUserFrom(null);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "refundSuccess": {
                    content = "Đơn hàng của bạn đã được chấp thuận. Tiền được hoàn vào ví!! Dwallet +" + object
                            + " VND";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(userIDAdmin);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "refundFail": {
                    content = "Đơn hàng của bạn bị từ chối trả hàng/hoàn tiền!";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(userIDAdmin);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                default:
            }

        } catch (Exception e) {
        }
    }

    @Override
    public void sendHistory(String userFrom, String userTo, String link, String type, String object) {
        try {
            Notifications noti = new Notifications();
            String content = "";

            switch (type) {
                case "updateprofile": {
                    content = "Bạn đã thay đổi " + object;
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "follow": {
                    content = "Bạn đã follow shop " + userTo + "!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "unfollow": {
                    content = "Bạn đã hủy follow shop " + userTo + "!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "newproduct": {
                    content = "Bạn đã thêm sản phẩm mới có id là " + object + "!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "updateproduct": {
                    content = "Bạn đã cập nhật thông tin sản phẩm có id là " + object + "!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "deleteproduct": {
                    content = "Bạn đã xóa sản phẩm có id là " + object + "!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "retoreproduct": {
                    content = "Bạn đã khôi phục sản phẩm đã xóa có id là " + object + "!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "xacnhandonhang": {
                    content = "Bạn đã xác nhận đơn hàng có mã hóa đơn là " + object + "!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "huydonhangcuashop": {
                    content = "Bạn đã hủy đơn hàng có id là " + object + "!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "huydonhangcuacustomer": {
                    content = "Bạn đã hủy đơn hàng có id là " + object + " của shop " + userTo + "!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "createvoucher": {
                    content = "Bạn đã tạo voucher mới có id là " + object + "!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "deletevoucher": {
                    content = "Bạn đã xóa voucher có id là " + object + "!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "updatevoucher": {
                    content = "Bạn đã cập nhật thông tin voucher có id là " + object + "!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "newFlashSale": {
                    content = "Bạn đã thêm khung giờ flashsale!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "applyflashsale": {
                    content = "Bạn đã thêm sản phẩm có id là " + object + " vào khung giờ flashsale!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "repplycomment": {
                    content = "Bạn đã trả lời comment của " + userTo + " với sản phẩm có id là " + object;
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "updateseller": {
                    content = "Bạn đã nâng cấp tài khoản trở thành người bán thành công!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "updateUser": {
                    content = "Tài khoản " + object + " đã được thay đổi!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "dathang": {
                    content = "Bạn đã đặt một đơn hàng mới có mã hóa đơn là " + object
                            + ", Xin vui lòng chờ shop xác nhận!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "hoantien": {
                    content = "Bạn đã yêu cầu hoàn tiền cho hóa đơn có id là " + object + "!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "changepassword": {
                    content = "Bạn đã đã thay đổi mật khẩu";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "comment": {
                    content = "Bạn đã bình luận cho sản phẩm " + object + ".";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "khoiphucsanpham": {
                    content = "Bạn đã khôi phục sản phẩm có id là " + object + ".";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "orderReport": {
                    content = "Bạn đã gửi feedback cho quản trị viên với nội dung là " + object + ".";
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }
                case "welcome": {
                    content = "Chào mừng bạn đến với sàn thương mại điện tử DEVEX do team Thiếu Nữ thành lập!";
                    noti.setContent(content);
                    noti.setUserTo(null);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }

                default:
            }

        } catch (Exception e) {
        }
    }
}
