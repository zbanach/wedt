package pl.edu.pw.elka.wedt.jade.messages;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.Serializable;

/**
 * @author Rafał Wolny
 */
public class MessageReceiver {
    private final Agent agent;
    private final Behaviour behavior;

    private MessageReceiver(Agent agent, Behaviour behavior) {
        this.agent = agent;
        this.behavior = behavior;
    }

    public static MessageReceiver listen(Agent agent, Behaviour behavior) {
        return new MessageReceiver(agent, behavior);
    }

    public void forMessageString(MessageContentReceiver<String> contentReceiver) {
        ACLMessage message = agent.receive();
        if (message != null) {
            contentReceiver.onMessage(message.getContent());
        } else {
            behavior.block();
        }
    }

    public void forMessageObject(MessageContentReceiver<Serializable> contentReceiver) {
        ACLMessage message = agent.receive();
        if (message != null) {
            try {
                contentReceiver.onMessage(message.getContentObject());
            } catch (UnreadableException e) {
                behavior.block();
            }
        } else {
            behavior.block();
        }
    }
}
