package de.altenerding.biber.pinkie.presentation;

import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class TestTabBean {
    private String text;
    @Inject
    private Logger logger;

    public String doIt() {
        logger.info(text);
        return "startpage";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
