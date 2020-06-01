package Worker.message;

import javax.jms.*;

public class Queue {
    private javax.jms.Queue destination;
    private MessageProducer messageProducer;
    private MessageConsumer messageConsumer;
    private Session session;
    private String name;

    public Queue(Session session, String name) throws JMSException {
        this.session = session;
        this.name = name;
        this.destination = this.session.createQueue(name);
    }

    public void createCustomer() throws JMSException {
        if(this.messageConsumer != null) return;
        this.messageConsumer = this.session.createConsumer(this.destination);
    }

    public void createProducer() throws JMSException {
        if(this.messageProducer != null) return;
        this.messageProducer = this.session.createProducer(this.destination);
    }

    public void send(String message, String sessionID) throws JMSException {
        TextMessage messageMQ = this.session.createTextMessage(message);
        messageMQ.setStringProperty("session_id", sessionID);
        this.messageProducer.send(messageMQ);
    }

    public String getMessage() throws JMSException {
        if(this.messageConsumer == null) return null;
        Message message = this.messageConsumer.receive();
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            return textMessage.getText();
        }
        return null;
    }
}
