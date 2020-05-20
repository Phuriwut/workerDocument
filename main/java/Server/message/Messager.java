package Server.message;

import javax.jms.JMSException;

public class Messager {
    static MessagerInstance mi;

    public static MessagerInstance getInstance() throws JMSException {
        if(mi == null){
            mi = new MessagerInstance();
        }
        return mi;
    }
}
