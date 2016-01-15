package pl.edu.pw.elka.wedt.model;

import lombok.Getter;

/**
 * @author Rafał Wolny
 */
@Getter
public enum Site {

    CENEO("ceneo", "http://www.ceneo.pl"),
    EURO("euro", "http://www.euro.com.pl");

    private final String name;
    private final String URL;

    Site(String name, String URL) {
        this.name = name;
        this.URL = URL;
    }
}
