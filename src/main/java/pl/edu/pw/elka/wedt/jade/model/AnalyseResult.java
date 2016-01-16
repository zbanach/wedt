package pl.edu.pw.elka.wedt.jade.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Rafał Wolny
 */
@Getter
@Setter
public class AnalyseResult implements Serializable {

    private static final long serialVersionUID = -6214046682886280942L;

    private String siteName;
    private String productName;
    private float rating;
    private int reviewsCount;
    private int positiveReviewsCount;
    private int negativeReviewsCount;
    private double sentiment;

}
