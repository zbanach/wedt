package pl.edu.pw.elka.wedt.jade.messages;

/**
 * @author Rafał Wolny
 */
public interface MessageContentReceiver<T> {

    void onMessage(T message);
}
