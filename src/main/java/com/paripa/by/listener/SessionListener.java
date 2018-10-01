package com.paripa.by.listener;

import java.util.Map;
import java.util.Locale;

import com.paripa.by.constants.Const;
import com.paripa.by.dao.LabelsDao;
import com.paripa.by.dao.exception.DaoException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class SessionListener implements HttpSessionListener {

    private final static Logger logger = Logger.getLogger(SessionListener.class);
    private static final String LANGUAGE_EN = "en";

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        String defaultLanguage = LANGUAGE_EN;
        event.getSession().setAttribute(Const.LANGUAGE, defaultLanguage);
        Map<String, String> labels = null;
        try {
            labels = new LabelsDao().initLabelData(new Locale(defaultLanguage));
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to load labels", e);
        }
        event.getSession().setAttribute(Const.LABELS, labels);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {}
}
