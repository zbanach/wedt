package pl.edu.pw.elka.wedt.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafał Wolny
 */
@Getter
@Setter
public class Product implements Serializable {
    private static final long serialVersionUID = 5989641800799336257L;
    private String name;
    private final List<String> reviews;
    private float rating;
    private String siteUrl;
    private String siteName;

    public Product() {
        this.reviews = new ArrayList<>();
    }

    public void addReview(String review) {
        this.reviews.add(review);
    }

    public void addReviews(List<String> reviews) {
        this.reviews.addAll(reviews);
    }
}
