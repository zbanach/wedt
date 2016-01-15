package pl.edu.pw.elka.wedt.jade.agents.master;

import jade.core.Agent;
import pl.edu.pw.elka.wedt.jade.agents.master.behaviours.MasterAgentBehaviour;

/**
 * @author Rafał Wolny
 */
public class MasterAgent extends Agent {

    private static final long serialVersionUID = 1820724876232852206L;

    @Override
    public void setup() {
        String[] parserNames = (String[]) getArguments();
        addBehaviour(new MasterAgentBehaviour(parserNames));
    }
}
