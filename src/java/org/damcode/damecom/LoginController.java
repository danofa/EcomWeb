package org.damcode.damecom;

import org.damcode.damecom.dbao.AuthDbao;
import org.damcode.damecom.dbao.DamEcomDbSingleton;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.damcode.damecom.dbao.CustomerDbao;

/**
 *
 * @author dm
 */
@WebServlet(urlPatterns = "/customer/login")
@ServletSecurity(
        @HttpConstraint(transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL))

public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("login doget");
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/customer.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("login doPost();");
        HttpSession sess = req.getSession(false);

        if (sess == null) {
            RequestDispatcher rd = req.getRequestDispatcher("/index");
            rd.forward(req, resp);

        } else {
            CustomerDbao custDbao = new CustomerDbao();

            ObjectId custId = new AuthDbao().authUser(req.getParameter("login_name"), req.getParameter("login_pass"));
            DamECustomer customer = custDbao.getCustomerData(custId);

            if (isSuccessfulLogin(customer, custId)) {
                DB db = DamEcomDbSingleton.INSTANCE.getDb();
                DBCollection dbCollectionProds;
                DBCursor curs;

                dbCollectionProds = db.getCollection("products");

                sess.setAttribute("customer", customer);
                sess.setAttribute("authenticated", true);

                List<BasicDBObject> temp = new ArrayList<BasicDBObject>();
                List<HashMap> reBasket = new ArrayList<HashMap>();

                if (sess.getAttribute("basketitems") != null) {
                    for (HashMap hm : (ArrayList<HashMap>) sess.getAttribute("basketitems")) {
                        reBasket.add(hm);
                        System.out.println("added current session item ->" + hm.toString());
                    }
                }

               try {
                    for (HashMap hmap : customer.getBasket()) {
                        temp.add(new BasicDBObject("_id", hmap.get("_id")));
                    }
                } catch (Exception e) {
                    System.out.println("exception in basket get: " + e);
                }
                try {
                    curs = dbCollectionProds.find(new BasicDBObject("$or", temp));
                    while (curs.hasNext()) {
                        HashMap hm = (HashMap) curs.next().toMap();
                        hm.put("basketid", new ObjectId().toString());
                        reBasket.add(hm);
                    }
                } catch (Exception ex) {
                    System.out.println("empty basket" + ex);
                }

                sess.setAttribute("basketitems", reBasket);

                new CustomerDbao().updateLastLogin(custId);

                req.getRequestDispatcher("/WEB-INF/views/customer.jsp").forward(req, resp);

            } else {
                // password check failed.
                req.getRequestDispatcher("/WEB-INF/views/autherror.jsp").forward(req, resp);
            }
        }

    }

    private boolean isSuccessfulLogin(DamECustomer customer, ObjectId custId) {
        return customer != null && custId != null;
    }
}
