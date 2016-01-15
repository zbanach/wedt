package pl.edu.pw.elka.wedt.jade.agents.parser.common;

import jade.core.Agent;
import pl.edu.pw.elka.wedt.jade.agents.parser.common.behaviours.SubPageAgentBehaviour;
import pl.edu.pw.elka.wedt.model.Site;

/**
 * @author Rafał Wolny
 */
public class SubPageAgent extends Agent {

    private static final long serialVersionUID = 4076491161465151726L;

    @Override
    public void setup() {
        final String analyserAgentName = (String) this.getArguments()[0];
        final Site site = (Site) this.getArguments()[1];
        final String productId = (String) this.getArguments()[2];
        final Integer subPageNumber = (Integer) this.getArguments()[3];
        addBehaviour(new SubPageAgentBehaviour(site, analyserAgentName, productId, subPageNumber));
    }
}
