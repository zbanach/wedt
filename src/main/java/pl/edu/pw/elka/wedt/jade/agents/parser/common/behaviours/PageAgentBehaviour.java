package pl.edu.pw.elka.wedt.jade.agents.parser.common.behaviours;

import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import org.jsoup.nodes.Element;
import pl.edu.pw.elka.wedt.jade.messages.MessageBuilder;
import pl.edu.pw.elka.wedt.jade.messages.MessageReceiver;
import pl.edu.pw.elka.wedt.parser.model.Product;
import pl.edu.pw.elka.wedt.parser.model.Site;
import pl.edu.pw.elka.wedt.parser.SiteParser;

import java.io.IOException;
import java.util.List;

/**
 * @author Rafał Wolny
 */
public class PageAgentBehaviour extends Behaviour {

    private static final long serialVersionUID = -3699492960613022758L;

    private final String analyserAgentName;
    private final String masterAgentName;
    private final Site site;
    private Product product;
    private State state;
    private int subPageCount;
    private int collectedReviewCount;

    public PageAgentBehaviour(Site site, String analyserAgentName, String masterAgentName) {
        this.site = site;
        this.analyserAgentName = analyserAgentName;
        this.masterAgentName = masterAgentName;
        this.state = State.WAITING_FOR_PRODUCT_NAME;
        this.product = new Product();
    }

    @Override
    public void action() {

        switch (state) {
            case WAITING_FOR_PRODUCT_NAME:
                MessageReceiver.listen(getAgent(), this).forMessageString((query) -> {

                    try {
                        Element productPage = SiteParser.getProductPage(site, query, product)
                                .orElseThrow(SiteParser.ParserException::new);
                        subPageCount = SiteParser.getSubPageCount(site, productPage);

                        AgentContainer agentContainer = getAgent().getContainerController();
                        String name = site.getName() + "-sub-page-agent-";

                        for (int i = 1; i <= subPageCount; i++) {
                            AgentController agentController = agentContainer.createNewAgent(name + i,
                                    "pl.edu.pw.elka.wedt.jade.agents.parser.common.SubPageAgent",
                                    new Object[]{site.getName() + "-parser-agent", site, product.getSiteUrl(), i});
                            agentController.start();
                        }
                        state = State.WAITING_FOR_REVIEWS;

                    } catch (IOException | StaleProxyException | SiteParser.ParserException e) {
                        getAgent().send(MessageBuilder.inform().toLocal(masterAgentName).withContent(site).build());
                        resetState();
                    }
                });
                break;

            case WAITING_FOR_REVIEWS:
                MessageReceiver.listen(getAgent(), this).forMessageObject((reviews) -> {
                    product.addReviews((List<String>) reviews);
                    collectedReviewCount++;
                });

                if (collectedReviewCount >= subPageCount) {
                    state = State.REVIEWS_COLLECTED;
                }
                break;

            case REVIEWS_COLLECTED:
                getAgent().send(MessageBuilder.inform().toLocal(analyserAgentName).withContent(product).build());
                resetState();
                break;
        }
    }

    @Override
    public boolean done() {
        return false;
    }

    private void resetState() {
        state = State.WAITING_FOR_PRODUCT_NAME;
        collectedReviewCount = 0;
        subPageCount = 0;
        product = new Product();
    }

    private enum State {
        WAITING_FOR_PRODUCT_NAME, WAITING_FOR_REVIEWS, REVIEWS_COLLECTED;
    }
}
