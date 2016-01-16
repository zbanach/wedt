package pl.edu.pw.elka.wedt.jade.agents.parser.common.behaviours;

import jade.core.behaviours.Behaviour;
import pl.edu.pw.elka.wedt.jade.messages.MessageBuilder;
import pl.edu.pw.elka.wedt.parser.model.Site;
import pl.edu.pw.elka.wedt.parser.SiteParser;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafał Wolny
 */
public class SubPageAgentBehaviour extends Behaviour {

    private static final long serialVersionUID = 1336396464632297767L;

    private final Site site;
    private final String parserAgentName;
    private final String productId;
    private final Integer subPageNumber;

    public SubPageAgentBehaviour(Site site, String parserAgentName, String productId, Integer subPageNumber) {
        this.site = site;
        this.parserAgentName = parserAgentName;
        this.productId = productId;
        this.subPageNumber = subPageNumber;
    }

    @Override
    public boolean done() {
        return false;
    }

    @Override
    public void action() {
        List<String> reviews;
        try {
            reviews = SiteParser.getReviewsFromSubPage(site, subPageNumber, productId);
        } catch (IOException e) {
            e.printStackTrace();
            reviews = new ArrayList<>();
        }
        getAgent().send(MessageBuilder.inform().toLocal(parserAgentName).withContent((Serializable) reviews).build());
        getAgent().doDelete();
    }
}
