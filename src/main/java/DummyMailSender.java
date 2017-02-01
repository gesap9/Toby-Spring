import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by gesap on 2017-02-01.
 */
public class DummyMailSender implements MailSender {
    public void send(SimpleMailMessage simpleMessage) throws MailException {

    }

    public void send(SimpleMailMessage[] simpleMessages) throws MailException {

    }
}
