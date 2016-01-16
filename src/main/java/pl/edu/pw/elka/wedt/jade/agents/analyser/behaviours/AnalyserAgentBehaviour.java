package pl.edu.pw.elka.wedt.jade.agents.analyser.behaviours;

import jade.core.behaviours.Behaviour;
import pl.edu.pw.elka.wedt.jade.messages.MessageBuilder;
import pl.edu.pw.elka.wedt.jade.messages.MessageReceiver;
import pl.edu.pw.elka.wedt.model.AnalyseResult;
import pl.edu.pw.elka.wedt.model.Product;
import pl.edu.pw.elka.wedt.model.SentimentAnalyzer;

/**
 * @author Rafał Wolny
 */
public class AnalyserAgentBehaviour extends Behaviour {

    private static final long serialVersionUID = -339926547511619285L;
    private final String masterAgentName;

    public AnalyserAgentBehaviour(String masterAgentName) {
        this.masterAgentName = masterAgentName;
    }

    @Override
    public void action() {
        MessageReceiver.listen(getAgent(), this).forMessageObject((object) -> {
            Product product = (Product) object;
            double sentiment = new SentimentAnalyzer().sumUpSentiment(
                    product.getReviews(), SentimentAnalyzer.BIG_SCALE_SENTIMENT);

            AnalyseResult analyseResult = new AnalyseResult();
            analyseResult.setSiteName(product.getSiteName());
            analyseResult.setProductName(product.getName());
            analyseResult.setReviewsCount(product.getReviews().size());
            analyseResult.setRating(product.getRating());
            analyseResult.setSentiment(sentiment);

            getAgent().send(MessageBuilder.inform().toLocal(masterAgentName).withContent(analyseResult).build());
        });
    }

    @Override
    public boolean done() {
        return false;
    }
}
