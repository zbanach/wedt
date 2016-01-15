package pl.edu.pw.elka.wedt.jade.agents.parser.euro;

import jade.core.Agent;
import pl.edu.pw.elka.wedt.jade.agents.parser.common.behaviours.PageAgentBehaviour;
import pl.edu.pw.elka.wedt.model.Site;

/**
 * @author Rafał Wolny
 */
public class EuroPageAgent extends Agent {

    private static final long serialVersionUID = -1409837728877516600L;

    @Override
    public void setup() {
        final String analyserAgentName = (String) getArguments()[0];
        final String masterAgentName = (String) getArguments()[1];
        addBehaviour(new PageAgentBehaviour(Site.EURO, analyserAgentName, masterAgentName));
    }
}
