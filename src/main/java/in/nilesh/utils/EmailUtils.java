package in.nilesh.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;

public class EmailUtils {

	@Autowired
	private JavaMailSender jaMailSender;

	public boolean mailSend(String subject, String body, String to) {
		boolean isSent = false;
		try {

			MimeMessage mimeMessage = jaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setSubject(subject);
			helper.setText(body, true);
			helper.setTo(to);

			isSent = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return isSent;

	}

}
