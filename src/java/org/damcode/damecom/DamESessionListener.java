package org.damcode.damecom;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.damcode.damecom.dbao.CustomerDbao;

@WebListener
public class DamESessionListener implements HttpSessionListener {

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
        try {
            if (hse.getSession().getAttribute("customer") != null) {
                System.out.println("destroyed authed session: " + hse.getSession().getId());
                new CustomerDbao().saveBasketToDb(
                        (DamECustomer) hse.getSession().getAttribute("customer"), 
                        (ArrayList<HashMap>) hse.getSession().getAttribute("basketitems"));
            }
        } catch (Exception ex) {
            System.out.println("exception :" + getClass() + " sessiondestroyed : " + ex);
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent hse) {
        
    }
}
