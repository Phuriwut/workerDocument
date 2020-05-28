package Worker.message;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.BasicConfigurator;

import javax.jms.*;

public class Messager {
    // URL of the JMS server
//    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String url = "tcp://172.20.10.6:61616";
    // default broker URL is : tcp://localhost:61616"

    // Name of the queue we will receive messages from
    private static String subject = "SOCKET2WORK";
    private static String subjectWorker = "WORK2SOCKET";

    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    Destination destination,destinationWorker;
    MessageConsumer consumer;
    javax.jms.Message message;
    MessageProducer producer;

    public Messager() throws JMSException {
        // Getting JMS connection from the server
        this.connectionFactory = new ActiveMQConnectionFactory(url);
        this.connection = connectionFactory.createConnection();
        this.connection.start();

        // Creating session for seding messages
        this.session = this.connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        // Getting the queue 'JCG_QUEUE'
        this.destination = this.session.createQueue(subject);
        this.destinationWorker = this.session.createQueue(subjectWorker);

        // MessageConsumer is used for receiving (consuming) messages
        this.consumer = this.session.createConsumer(destination);

        //MessageProducer is used for sending messages to the queue.
        this.producer = session.createProducer(destinationWorker);

        //activeMQ
        BasicConfigurator.configure();

    }

    public void send(String message, String sessionID) throws JMSException {
        Message messageMQ = this.session.createTextMessage(message);
        messageMQ.setStringProperty("session_id", sessionID);
        this.producer.send(messageMQ);
    }

    public Message recieve() throws JMSException{
        this.message = this.consumer.receive();

        if (this.message instanceof TextMessage) {
            return this.message;
        }
        return null;
    }

    void stop(){
        try {
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
