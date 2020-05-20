package Server.message;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.BasicConfigurator;

import javax.jms.*;

public class MessagerInstance {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String subjectRegister = "SOCKET2WORK";
    private static String subject = "WORK2SOCKET";

    Session session;
    Destination destinationRegister,destination;
    MessageProducer producerRegister,producer;
    MessageConsumer consumer;
    Message message;

    public MessagerInstance() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //Creating a non transactional session to send/receive JMS message.
        this.session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        //Destination represents here our queue 'JCG_QUEUE' on the JMS server.
        //The queue will be created automatically on the server.
        this.destinationRegister = session.createQueue(subjectRegister);
        this.destination = session.createQueue(subject);

        // MessageProducer is used for sending messages to the queue.
        this.producerRegister = session.createProducer(destinationRegister);
        this.producer = session.createProducer(destination);

        // MessageConsumer is used for receiving (consuming) messages
        this.consumer = session.createConsumer(destination);

        //activeMQ
        BasicConfigurator.configure();

    }

    public void send(String message) throws JMSException {
        this.producerRegister.send(this.session.createTextMessage(message));
    }

    public String recieve() throws JMSException {
        this.message = this.consumer.receive();

        // We will be using TestMessage in our example. MessageProducer sent us a TextMessage
        // so we must cast to it to get access to its .getText() method.
        if (this.message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            return textMessage.getText();
        }
        return null;
    }
}
