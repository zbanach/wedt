package pl.edu.pw.elka.wedt.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.edu.pw.elka.wedt.model.Product;
import pl.edu.pw.elka.wedt.model.Site;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Rafał Wolny
 */
public class SiteParser {

    public static Optional<Element> getProductPage(Site site, String query, Product product) throws IOException {

        query = query.replace(" ", "+");
        String siteUrl = site.getURL();
        String url, productUrl = "";
        Element productListPage, productPage = null;
        Elements products, rating;

        switch (site) {
            case CENEO:
                url = siteUrl + "/;szukaj-" + query;
                productListPage = Jsoup.connect(url).get().body();
                products = productListPage.getElementsByClass("cat-prod-row-body");
                if (products.isEmpty()) {
                    return Optional.empty();
                }
                productUrl = products.get(1).getElementsByTag("a").first().attr("href").split("#")[0];

                productPage = Jsoup.connect(siteUrl + productUrl).get().body();
                product.setName(productPage.getElementsByClass("product-name").first().text());
                rating = productPage.getElementsByClass("product-score");
                if (rating.isEmpty()) {
                    return Optional.empty();
                }
                product.setRating(Float.parseFloat(rating.first().text().split("/")[0].replace(",", ".")));
                break;

            case EURO:
                url = siteUrl + "/search.bhtml?keyword=" + query;
                productListPage = Jsoup.connect(url).get().body();
                products = productListPage.getElementsByClass("product-container");
                if (products.isEmpty()) {
                    return Optional.empty();
                }
                productUrl = products.get(0).getElementsByTag("a").first().attr("href");

                productPage = Jsoup.connect(siteUrl + productUrl).get().body();
                product.setName(productPage.getElementsByClass("center-top-bar").first().text());
                rating = productPage.getElementsByClass("pr_com_op");
                if (rating.isEmpty()) {
                    return Optional.empty();
                }
                product.setRating(Float.parseFloat(rating.first().getElementsByClass("bold").first()
                        .text().replace(",", ".")));
                break;
        }

        product.setSiteUrl(productUrl);
        product.setSiteName(site.getName().substring(0, 1).toUpperCase() + site.getName().substring(1));
        return Optional.of(productPage);
    }

    public static int getSubPageCount(Site site, Element productPage) {
        int reviewsCount;
        switch (site) {
            case CENEO:
                reviewsCount = Integer.parseInt(productPage.getElementsByClass("product-reviews-link").first().children().last().text());
                return reviewsCount / 10 + 1;
            case EURO:
//                reviewsCount = Integer.parseInt(productPage.getElementsByClass("linkToOpinionTab").first().children().first().text());
//                return reviewsCount / 20 + 1;
                return 1;
            default:
                return 0;
        }
    }

    public static List<String> getReviewsFromSubPage(Site site, Integer subPageNumber, String productId) throws IOException {
        List<String> reviews = new ArrayList<>();
        Element reviewList;
        Elements reviewElements;
        switch (site) {
            case CENEO:
                reviewList = Jsoup.connect(site.getURL() + productId + "/opinie-" + subPageNumber).get().body();

                reviewElements = reviewList.getElementsByClass("product-review");
                for (Element element : reviewElements) {
                    String review = element.getElementsByClass("product-review-body").text();
                    reviews.add(review);
                }
                break;

            case EURO:
                reviewList = Jsoup.connect(site.getURL() + productId + "#opinie").get().body();

                reviewElements = reviewList.getElementsByClass("vote_box");
                for (Element element : reviewElements) {
                    String review = element.getElementsByClass("fullOpinion").text();
                    reviews.add(review);
                }
                break;
        }
        return reviews;
    }

    // reszta do testownia..

    private static Product getReviews(Site site, String query) {

        Product product;

        switch (site) {
            case CENEO:
                product = parseCeneo(site.getURL(), query);
                break;
            case EURO:
                product = parseEuro(site.getURL(), query);
                break;
            default:
                product = new Product();
        }

        return product;
    }

    private static Product parseEuro(String siteUrl, String query) {

        query = query.replace(" ", "+");

        String URL = siteUrl + "/search.bhtml?keyword=" + query;
        Product product = new Product();

        try {
            Element productList = Jsoup.connect(URL).get().body();

            String productId = productList.getElementsByClass("product-container").get(0).getElementsByTag("a").first().attr("href");

            Element productPage = Jsoup.connect(siteUrl + productId).get().body();

            product.setName(productPage.getElementsByClass("center-top-bar").first().text());
            product.setRating(Float.parseFloat(productPage.getElementsByClass("pr_com_op").first().getElementsByClass("bold").first().text()));

            int reviewsCount = Integer.parseInt(productPage.getElementsByClass("linkToOpinionTab").first().children().first().text());

            Element reviewList;

            reviewList = Jsoup.connect(siteUrl + productId + "#opinie").get().body();

            Elements reviewElements = reviewList.getElementsByClass("vote_box");
            for (Element element : reviewElements) {
                String review = element.getElementsByClass("fullOpinion").text();
                product.addReview(review);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return product;
    }

    private static Product parseCeneo(String siteUrl, String query) {

        query = query.replace(" ", "+");

        String URL = siteUrl + "/;szukaj-" + query;
        Product product = new Product();

        try {
            Element productList = Jsoup.connect(URL).get().body();

            String productId = productList.getElementsByClass("cat-prod-row-body").get(1).getElementsByTag("a").first().attr("href").split("#")[0];

            Element productPage = Jsoup.connect(siteUrl + productId).get().body();

            product.setName(productPage.getElementsByClass("product-name").first().text());
            product.setRating(Float.parseFloat(productPage.getElementsByClass("product-score").first().text().split("/")[0].replace(",", ".")));

            int reviewsCount = Integer.parseInt(productPage.getElementsByClass("product-reviews-link").first().children().last().text());
            int siteCount = reviewsCount / 10 + 1;

            Element reviewList;
            for (int i = 1; i <= siteCount; i++) {
                reviewList = Jsoup.connect(siteUrl + productId + "/opinie-" + i).get().body();

                Elements reviewElements = reviewList.getElementsByClass("product-review");
                for (Element element : reviewElements) {
                    String review = element.getElementsByClass("product-review-body").text();
                    product.addReview(review);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return product;
    }


    public static void main(String[] args) {
        Product product = SiteParser.getReviews(Site.EURO, "samsung galaxy a5");

        System.out.println("Nazwa produktu: " + product.getName());
        System.out.println("Ocena: " + product.getRating());
        System.out.println("Liczba opinii: " + product.getReviews().size());
        System.out.println();

        for (String review : product.getReviews()) {
            System.out.println(review);
            System.out.println();
        }
    }

    public static class ParserException extends RuntimeException implements Supplier<ParserException> {

        private static final long serialVersionUID = -9176741477073559162L;

        @Override
        public ParserException get() {
            return this;
        }
    }
}
