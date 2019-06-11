package ru.javaops.masterjava.service.mail;

import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import ru.javaops.masterjava.config.Configs;

import java.util.List;

@Slf4j
public class MailSender {
    private static final Config MAIL = Configs.getConfig("mail.conf", "mail");

    private MailSender() {
    }

    static void sendMail(List<Addressee> to, List<Addressee> cc, String subject, String body) {
        to.forEach(address -> {
            try {
                val email = new SimpleEmail();
                email.setHostName(MAIL.getString("host"));
                email.setSmtpPort(MAIL.getInt("port"));
                email.setAuthenticator(new DefaultAuthenticator(MAIL.getString("username"), MAIL.getString("password")));
                email.setSSL(MAIL.getBoolean("useSSL"));
                email.setTLS(MAIL.getBoolean("useTLS"));
                email.setDebug(MAIL.getBoolean("debug"));
                email.setFrom(MAIL.getString("fromMail"), MAIL.getString("fromName"));
                email.setSubject(subject);
                email.setMsg(body);
                email.addTo(address.getEmail(), address.getName());
                cc.forEach(addressCC -> {
                    if (cc != null) {
                        try {
                            email.addCc(addressCC.getEmail(), addressCC.getName());
                        } catch (EmailException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                });
                email.send();
                log.info("Send MAIL to: " + to + "cc: " + cc);
            } catch (EmailException e) {
                throw new IllegalStateException(e);
            }
        });
    }
}
