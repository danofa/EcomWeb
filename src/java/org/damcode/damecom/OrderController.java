package org.damcode.damecom;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.damcode.damecom.dbao.OrderDbao;

/**
 *
 * @author dm
 */
@WebServlet(urlPatterns = {"/customer/placeorder", "/customer/orderhist"})
@ServletSecurity(
        @HttpConstraint(transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL))

public class OrderController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        System.out.println(Thread.currentThread().getStackTrace()[1] + " @ " + new Date());
        HttpSession sess = req.getSession(false);
        DamECustomer customer = (DamECustomer) sess.getAttribute("customer");
        OrderDbao orderDbao;
        
        if (sess != null) {
            
            orderDbao = new OrderDbao();
            ObjectId id = orderDbao.placeOrder((ArrayList<HashMap>) sess.getAttribute("basketitems"), customer.getId());
            if (id != null) {
                
                sess.setAttribute("basketitems", new ArrayList<HashMap>());
                customer.setBasket(null);
                
            }
            
            resp.sendRedirect(getServletContext().getAttribute("webappcontext") + "/customer/orderhist?orderid=" + id + "&oc=t");
            
        } else {
            System.out.println(Thread.currentThread().getStackTrace()[1] + " (session == null) @ " + new Date());
            req.getRequestDispatcher("/index").forward(req, resp);;
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(Thread.currentThread().getStackTrace()[1] + " @ " + new Date());
        HttpSession sess = req.getSession(false);
        
        if (sess != null) {
            DamECustomer customer = (DamECustomer) sess.getAttribute("customer");
            
            if (req.getParameter("orderid") != null) {
                DBObject order = new OrderDbao().getSpecificOrder(new ObjectId(req.getParameter("orderid")), customer.getId());
                
                
                sess.setAttribute("orderinfo", ((BasicDBList) order.get("purchases")).get(0));
                req.getRequestDispatcher("/WEB-INF/views/orderhist.jsp").forward(req, resp);
                
            } else {
                try {
                    sess.setAttribute("orderlist", new OrderDbao().getOrderList(customer.getId()));

                } catch (MongoException ex) {
                    System.out.println("order query exception: " + ex);
                }
                req.getRequestDispatcher("/WEB-INF/views/orderlist.jsp").forward(req, resp);
            }
            
        } else {
            System.out.println(this.getClass() + " session null ");
            req.getRequestDispatcher("/index").forward(req, resp);
        }
        
    }
    
}
