package com.paripa.by.action;

import com.paripa.by.action.book.*;
import com.paripa.by.action.common.ChangeLanguageAction;
import com.paripa.by.action.common.ShowStartPageAction;
import com.paripa.by.action.order.CancelOrderAction;
import com.paripa.by.action.order.ChangeOrderStatusAction;
import com.paripa.by.action.order.OrderBookAction;
import com.paripa.by.action.profile.*;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {

    private static final Map<String, Action> actionsMap = new HashMap<>();

    static {
        actionsMap.put("/login", new LoginAction());
        actionsMap.put("/profile", new ShowProfileAction());
        actionsMap.put("/logout", new LogoutAction());
        actionsMap.put("/start", new ShowStartPageAction());
        actionsMap.put("/show-catalogue", new ShowCatalogueAction());
        actionsMap.put("/edit-profile", new ShowEditProfileFormAction());
        actionsMap.put("/show-book", new ShowBookInfoAction());
        actionsMap.put("/change-password", new ChangePasswordAction());
        actionsMap.put("/change-profile-info", new ChangeProfileInfoAction());
        actionsMap.put("/order-book", new OrderBookAction());
        actionsMap.put("/cancel-order", new CancelOrderAction());
        actionsMap.put("/change-status", new ChangeOrderStatusAction());
        actionsMap.put("/add-book", new AddBookAction());
        actionsMap.put("/add-author", new AddAuthorAction());
        actionsMap.put("/new-author", new ShowAuthorFormAction());
        actionsMap.put("/new-reader", new ShowRegistrationFormAction());
        actionsMap.put("/register", new RegisterReaderAction());
        actionsMap.put("/change-lang", new ChangeLanguageAction());
        actionsMap.put("/edit-book", new ShowBookFormAction());
        actionsMap.put("/change-book-info", new ChangeBookInfoAction());
    }

    public static Action defineAction(HttpServletRequest req) {
        String actionPath = req.getPathInfo();
        return actionsMap.get(actionPath);
    }
}
