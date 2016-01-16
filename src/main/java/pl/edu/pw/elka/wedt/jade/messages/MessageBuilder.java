package pl.edu.pw.elka.wedt.jade.messages;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Rafał Wolny
 */
public class MessageBuilder {

    private final ACLMessage message;

    private MessageBuilder(int type) {
        this.message = new ACLMessage(type);
    }

    public static MessageBuilder inform() {
        return new MessageBuilder(ACLMessage.INFORM);
    }

    public MessageBuilder toLocal(String... otherAgentNames) {
        for (String agentName : otherAgentNames) {
            AID address = new AID(agentName, AID.ISLOCALNAME);
            message.addReceiver(address);
        }
        return this;
    }

    public MessageBuilder withContent(String content) {
        message.setContent(content);
        return this;
    }

    public MessageBuilder withContent(Serializable object) {
        try {
            message.setContentObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ACLMessage build() {
        return message;
    }
}
