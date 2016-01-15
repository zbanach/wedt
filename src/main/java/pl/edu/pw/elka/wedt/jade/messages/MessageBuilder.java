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

    private MessageBuilder(int performative) {
        this.message = new ACLMessage(performative);
    }

    public static MessageBuilder inform() {
        return new MessageBuilder(ACLMessage.INFORM);
    }

    public MessageBuilder to(AID... recievers) {
        for (AID reciever : recievers) {
            message.addReceiver(reciever);
        }
        return this;
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

    public ACLMessage build() {
        return message;
    }

    public MessageBuilder withContent(int i) {
        return withContent(Integer.toString(i));
    }

    public MessageBuilder withContent(Serializable object) {
        try {
            message.setContentObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
