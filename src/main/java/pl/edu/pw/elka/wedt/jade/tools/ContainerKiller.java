package pl.edu.pw.elka.wedt.jade.tools;

import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

/**
 * @author Rafał Wolny
 */
public class ContainerKiller {

    public static void killContainerOf(Agent agent) {
        final AgentContainer containerController = agent.getContainerController();
        agent.doDelete();
        new Thread(() -> {
            try {
                containerController.kill();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
