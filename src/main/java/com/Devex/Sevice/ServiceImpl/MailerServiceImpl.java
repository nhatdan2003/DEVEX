package com.Devex.Sevice.ServiceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.DTO.MailInfo;
import com.Devex.Sevice.MailerService;
import com.Devex.Sevice.SessionService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@SessionScope
@Service("emailService")
public class MailerServiceImpl implements MailerService {
	private static final String EMAIL_VERIFI = "[THÔNG BÁO] MÃ XÁC THỰC OTP CỦA BẠN CHO TRANG WEB DEVEX";
	private static final String EMAIL_FORGET = "ĐÂY LÀ MẬT KHẨU CỦA BẠN CHO TRANG WEB H&B COFFEE";
	private static final String EMAIL_ORDER_CUSTOMER = "BẠN ĐÃ ĐẶT HÀNG THÀNH CÔNG VUI LÒNG KIỂM TRA ĐƠN HÀNG";
	private static final String EMAIL_ORDER_ADMIN = "THÔNG BÁO! BẠN ĐÃ CÓ MỘT ĐƠN HÀNG MỚI";
	private static final String EMAIL_ADMIN = "hnbcoffeentea@gmail.com";
	List<MailInfo> list = new ArrayList<>();

	@Autowired
	JavaMailSender sender;


	@Autowired
	SessionService session;

	@Override
	public void send(MailInfo mail) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();

		MimeMessageHelper healper = new MimeMessageHelper(message, true, "utf-8");
		healper.setFrom(mail.getFrom());
		healper.setSubject(mail.getSubject());
		healper.setText(mail.getBody(), true);
		healper.setReplyTo(mail.getFrom());

		String to = mail.getTo();
		if (to != null && to.length() > 0) {
			healper.setTo(to);
		}

		String[] cc = mail.getCc();
		if (cc != null && cc.length > 0) {
			healper.setCc(cc);
		}

		String[] bcc = mail.getBcc();
		if (bcc != null && bcc.length > 0) {
			healper.setBcc(bcc);
		}

		List<File> files = mail.getFiles();
		if (files.size() > 0) {
			for (File file : files) {
				healper.addAttachment(file.getName(), file);
			}
		}

		sender.send(message);
	}

	@Override
	public void send(String to, String subject, String body) throws MessagingException {
		// TODO Auto-generated method stub
		this.send(new MailInfo(to, subject, body));
	}

	@Override
	public void queue(MailInfo mail) {
		// TODO Auto-generated method stub
		list.add(mail);
	}

	@Override
	public void queue(String to, String subjecct, String body) {
		// TODO Auto-generated method stub
		queue(new MailInfo(to, subjecct, body));
	}

	@Scheduled(fixedDelay = 5000)
	public void run() {
		while (!list.isEmpty()) {
			MailInfo mail = list.remove(0);
			try {
				this.send(mail);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void sendMailOtpSignUp(String mail, String otp) throws MessagingException {
		String subject = EMAIL_VERIFI;
		String content = "Devex: Mã OTP của bạn là " + otp + ", vui lòng không chia sẻ mã này cho người khác.";
		// send message
		this.send(mail, subject, content);
	}

	@Override
	public void sendMailToFormat(String type, Object user) throws MessagingException {
//		try {
//			String content = "";
//			String contentAdmin = null;
//			String subject = null;
//			String subjectAdmin = null;
//
//			switch (type) {
//			case "verifi": {
//				subject = EMAIL_VERIFI;
//				content = "Gửi " + user.getFullname() + ", đây là mã xác thực của bạn tại H&B COFFEE "
//						+ user.getVericode();
//				// send message
//				this.send(user.getEmail(), subject, content);
//				break;
//			}
//
//			case "forget": {
//				subject = EMAIL_FORGET;
//				content = "Gửi " + user.getFullname() + ", đây là mật khẩu của bạn tại H&B COFFEE "
//						+ user.getPassword()
//						+ "\nXin hãy giữ mật khẩu cẩn thận và đổi mật khẩu ngay nếu bạn thấy có dấu hiệu bị xâm nhập";
//				this.send(user.getEmail(), subject, content);
//				break;
//			}
//			case "order": {
//				String bill = "";
//				subjectAdmin = EMAIL_ORDER_ADMIN;
//				contentAdmin = "Customer: " + user.getEmail();
//				subject = EMAIL_ORDER_CUSTOMER;
//				
//				content = "Bạn đã đặt hàng thành công từ H&B COFFEE.<br> Vui lòng kiểm tra kĩ đơn hàng nếu có sai sót hãy phản hồi sớm nhất tới "+  EMAIL_ADMIN;
//				for (CartItem item : cart.getItems()) {
//					Double itemPrice = item.getPrice() + priceSize(item.getSize());
//					Integer itemQuantity = item.getQty();
//					Double itemTotalPrice = itemPrice * itemQuantity;
//
//					bill += "<br>=> Name: <strong>" + item.getName() + "</strong>, Size: " + item.getSize() + ", Quantity: " + itemQuantity
//					              + ", Price: " + itemPrice + ", Total Price: " + itemTotalPrice;
//
//				}
//				bill += "<br> ===>>> <strong style='color: red;'>Total: " + cart.getAmount() + "</strong> <<<===";
//				this.send(EMAIL_ADMIN, subjectAdmin, contentAdmin + bill);
//				this.send(user.getEmail(), subject, content + bill);
//				break;
//			}
//			default:
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
	}
	
	public long priceSize(String size) {
		if(size.equalsIgnoreCase("L")) {
			return 10000;
		}else if(size.equalsIgnoreCase("M")) {
			return 5000;
		}else {
			return 0;
		}
	}

}
