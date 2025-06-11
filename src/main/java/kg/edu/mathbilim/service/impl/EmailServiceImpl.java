package kg.edu.mathbilim.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public void sendEmail(String to, String lnk) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(emailFrom, "Mathbilim Support");
        mimeMessageHelper.setTo(to);

        String subject = "Here s the link to reset password";
        String content = "<p>Hello,</p>"

                + "<p>You have requested to reset your password.</p>"

                + "<p>Click the link below to change your password:</p>"

                + "<p><a href=\"" + lnk + "\">Change my password</a></p>"

                + "<br>"

                + "<p>Ignore this email if you do remember your password, "

                + "or you have not made the request.</p>";
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);
        mailSender.send(mimeMessage);
    }
}
