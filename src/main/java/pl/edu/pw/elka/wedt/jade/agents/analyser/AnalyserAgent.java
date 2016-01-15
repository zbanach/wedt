package pl.edu.pw.elka.wedt.jade.agents.analyser;

import jade.core.Agent;
import pl.edu.pw.elka.wedt.jade.agents.analyser.behaviours.AnalyserAgentBehaviour;

/**
 * @author Rafał Wolny
 */
public class AnalyserAgent extends Agent {

    private static final long serialVersionUID = -5145881907571393342L;

    @Override
    public void setup() {
        final String masterAgentName = (String) getArguments()[0];
        addBehaviour(new AnalyserAgentBehaviour(masterAgentName));
    }
}
