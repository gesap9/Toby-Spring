package springbook;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gesap on 2017-02-01.
 */
public class MockMailSender implements MailSender {
    private List<String> requests = new ArrayList<String>();

    public List<String> getRequests() {
        return requests;
    }

    public void send(SimpleMailMessage simpleMessage) throws MailException {
        requests.add(simpleMessage.getTo()[0]);
    }

    public void send(SimpleMailMessage[] simpleMessages) throws MailException {

    }
}
