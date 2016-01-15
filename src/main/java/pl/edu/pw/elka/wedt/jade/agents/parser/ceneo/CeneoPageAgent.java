package pl.edu.pw.elka.wedt.jade.agents.parser.ceneo;

import jade.core.Agent;
import pl.edu.pw.elka.wedt.jade.agents.parser.common.behaviours.PageAgentBehaviour;
import pl.edu.pw.elka.wedt.model.Site;

/**
 * @author Rafał Wolny
 */
public class CeneoPageAgent extends Agent {

    private static final long serialVersionUID = -3474128229201798039L;

    @Override
    public void setup() {
        final String analyserAgentName = (String) getArguments()[0];
        final String masterAgentName = (String) getArguments()[1];
        addBehaviour(new PageAgentBehaviour(Site.CENEO, analyserAgentName, masterAgentName));
    }
}
