package com.paripa.by.action.common;

import com.paripa.by.action.Action;
import com.paripa.by.action.exception.ActionException;
import com.paripa.by.constants.Const;
import com.paripa.by.dao.LabelsDao;
import com.paripa.by.dao.exception.DaoException;

import java.util.Map;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ChangeLanguageAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String languageNew = req.getParameter(Const.PARAM_LANGUAGE);
        String languageOld = (String) req.getSession().getAttribute(Const.LANGUAGE);
        if (languageNew != null && !languageNew.equals(languageOld)) {
            req.getSession().setAttribute(Const.LANGUAGE, languageNew);
            Map<String, String> labels;
            try {
                labels = new LabelsDao().initLabelData(new Locale(languageNew));
            } catch (DaoException e) {
                throw new ActionException();
            }
            req.getSession().setAttribute(Const.LABELS, labels);
        }
        return Const.REDIRECT_PROFILE_EDIT_FORM;
    }

}