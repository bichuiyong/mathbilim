package kg.edu.mathbilim.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kg.edu.mathbilim.repository.UserRepository;
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
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setFrom(emailFrom, "MathBilim");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("Сброс пароля - MathBilim");

        String content = buildPasswordResetEmailContent(lnk);
        mimeMessageHelper.setText(content, true);

        mailSender.send(mimeMessage);
    }

    private String buildPasswordResetEmailContent(String resetLink) {
        return "<html><body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
                "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
                "<h2 style='color: #2c3e50; text-align: center;'>Сброс пароля</h2>" +
                "<p>Здравствуйте!</p>" +
                "<p>Вы запросили сброс пароля для вашей учетной записи в MathBilim.</p>" +
                "<p>Нажмите на кнопку ниже, чтобы создать новый пароль:</p>" +
                "<div style='text-align: center; margin: 30px 0;'>" +
                "<a href='" + resetLink + "' " +
                "style='background-color: #e74c3c; color: white; padding: 12px 30px; text-decoration: none; " +
                "border-radius: 5px; font-weight: bold; display: inline-block;'>Сбросить пароль</a>" +
                "</div>" +
                "<p>Если кнопка не работает, скопируйте и вставьте следующую ссылку в адресную строку браузера:</p>" +
                "<p style='word-break: break-all; background-color: #f8f9fa; padding: 10px; border-radius: 3px;'>" +
                resetLink + "</p>" +
                "<p style='color: #e74c3c; font-weight: bold;'>⚠️ Важно:</p>" +
                "<ul style='color: #666;'>" +
                "<li>Ссылка действительна в течение ограниченного времени</li>" +
                "<li>Если вы не запрашивали сброс пароля, просто проигнорируйте это письмо</li>" +
                "<li>Ваш текущий пароль останется неизменным до тех пор, пока вы не создадите новый</li>" +
                "</ul>" +
                "<p style='color: #666; font-size: 14px;'>Если у вас возникли вопросы, свяжитесь с нашей службой поддержки.</p>" +
                "<hr style='border: none; border-top: 1px solid #eee; margin: 30px 0;'>" +
                "<p style='color: #999; font-size: 12px; text-align: center;'>© 2025 MathBilim. Все права защищены.</p>" +
                "</div></body></html>";
    }

    public void sendVerificationEmail(String email, String verificationLink)
            throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Подтверждение электронной почты - MathBilim");
        helper.setFrom("noreply@mathbilim.edu.kg", "MathBilim");

        String content = buildVerificationEmailContent(verificationLink);
        helper.setText(content, true);

        mailSender.send(message);
    }

    private String buildVerificationEmailContent(String verificationLink) {
        return "<html><body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
                "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
                "<h2 style='color: #2c3e50; text-align: center;'>Подтверждение электронной почты</h2>" +
                "<p>Добро пожаловать в MathBilim!</p>" +
                "<p>Спасибо за регистрацию на нашей платформе. Для завершения регистрации необходимо подтвердить ваш email адрес.</p>" +
                "<div style='text-align: center; margin: 30px 0;'>" +
                "<a href='" + verificationLink + "' " +
                "style='background-color: #3498db; color: white; padding: 12px 30px; text-decoration: none; " +
                "border-radius: 5px; font-weight: bold; display: inline-block;'>Подтвердить email</a>" +
                "</div>" +
                "<p>Если кнопка не работает, скопируйте и вставьте следующую ссылку в адресную строку браузера:</p>" +
                "<p style='word-break: break-all; background-color: #f8f9fa; padding: 10px; border-radius: 3px;'>" +
                verificationLink + "</p>" +
                "<p style='color: #666; font-size: 14px;'>Если вы не регистрировались на сайте MathBilim, просто проигнорируйте это письмо.</p>" +
                "<hr style='border: none; border-top: 1pxsolpid #eee; margin: 30px 0;'>" +
                "<p style='color: #999; font-size: 12px; text-align: center;'>© 2025 MathBilim. Все права защищены.</p>" +
                "</div></body></html>";
    }
}