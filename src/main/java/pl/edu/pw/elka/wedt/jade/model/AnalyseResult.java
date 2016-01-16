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
    private int reviewsCount;
    private float rating;
    private double sentiment;

}
