package com.paripa.by.action.profile;


import com.paripa.by.action.Action;
import com.paripa.by.constants.Const;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowRegistrationFormAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
    {
        return Const.FORWARD_NEW_READER_FORM;
    }
}
