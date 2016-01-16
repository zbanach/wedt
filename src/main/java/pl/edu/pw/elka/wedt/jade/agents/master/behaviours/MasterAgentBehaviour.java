package pl.edu.pw.elka.wedt.jade.agents.master.behaviours;

import jade.core.behaviours.Behaviour;
import pl.edu.pw.elka.wedt.jade.messages.MessageBuilder;
import pl.edu.pw.elka.wedt.jade.messages.MessageReceiver;
import pl.edu.pw.elka.wedt.jade.model.AnalyseResult;
import pl.edu.pw.elka.wedt.parser.model.Site;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Rafał Wolny
 */
public class MasterAgentBehaviour extends Behaviour {

    private static final long serialVersionUID = -3838120677057633729L;
    private final List<AnalyseResult> analyseResults;
    private final String[] parserAgentNames;
    private State state;
    private int exceptionCount;

    public MasterAgentBehaviour(String... parserAgentNames) {
        this.parserAgentNames = parserAgentNames;
        this.state = State.GETTING_QUERY_FROM_USER;
        this.analyseResults = new ArrayList<>();
    }

    private enum State {
        GETTING_QUERY_FROM_USER, WAITING_FOR_ANALYSE_RESULT;
    }

    @Override
    public void action() {
        switch (state) {
            case GETTING_QUERY_FROM_USER:
                System.out.println();
                System.out.println("Wprowadź nazwę produktu: ");
                Scanner reader = new Scanner(System.in);
                String query = reader.nextLine();
                getAgent().send(MessageBuilder.inform().toLocal(parserAgentNames).withContent(query).build());
                state = State.WAITING_FOR_ANALYSE_RESULT;
                break;

            case WAITING_FOR_ANALYSE_RESULT:
                MessageReceiver.listen(getAgent(), this).forMessageObject((object) -> {

                    if (object instanceof AnalyseResult) {
                        AnalyseResult analyseResult = (AnalyseResult) object;

                        System.out.println();
                        System.out.println("Nazwa strony: " + analyseResult.getSiteName());
                        System.out.println("Nazwa produktu: " + analyseResult.getProductName());
                        System.out.println("Ocena: " + analyseResult.getRating());
                        System.out.println("Liczba opinii: " + analyseResult.getReviewsCount());
                        System.out.println();
                        System.out.println("-------------------------------------------------");

                        analyseResults.add(analyseResult);
                    } else {
                        System.out.println();
                        System.out.println("Nie udało się pobrać opinii ze strony: " + ((Site) object).getName());
                        System.out.println();
                        System.out.println("-------------------------------------------------");

                        exceptionCount++;
                    }
                });
                if (analyseResults.size() >= parserAgentNames.length - exceptionCount) {

                    float result = 0;
                    int positive = 0, negative = 0;
                    int numberOfReviews = 0;
                    for (AnalyseResult analyseResult : analyseResults) {
                        result += analyseResult.getSentiment();
                        numberOfReviews += analyseResult.getReviewsCount();
                        positive += analyseResult.getPositiveReviewsCount();
                        negative += analyseResult.getNegativeReviewsCount();
                    }
                    result /= analyseResults.size();
                    int positiveRatio = numberOfReviews > 0 ? Math.round(100*positive / numberOfReviews) : 0;
                    int negativeRatio = numberOfReviews > 0 ? Math.round(100*negative / numberOfReviews) : 0;

                    System.out.println();
                    System.out.println("Ogólny wydźwięk: " + (result > 0 ? result : "brak"));
                    System.out.printf("%d%% pozytywnych, %d%% negatywnych.", positiveRatio, negativeRatio);
                    System.out.println();
                    System.out.println("#################################################");

                    state = State.GETTING_QUERY_FROM_USER;
                    analyseResults.clear();
                    exceptionCount = 0;
                }
                break;
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
