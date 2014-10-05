package org.damcode.damecom;

import org.damcode.damecom.dbao.ProdDbao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.bson.types.ObjectId;

/**
 *
 * @author dm
 */
@WebServlet(urlPatterns = {"/", "/index"}, loadOnStartup = 1)
public class DamViewController extends HttpServlet {

    private final ProdDbao prodDbao = new ProdDbao();
    public String servletAppName;
    
    @Override
    public void init() throws ServletException {
        System.out.println("Servlet created : ViewController");
        servletAppName = getServletContext().getInitParameter("servletappname");
        getServletContext().setAttribute("webappcontext", servletAppName);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("httpsloginredirect", "https://" + request.getServerName() + servletAppName + "/customer/login");

        HttpSession sess = request.getSession();

        if (request.getServletPath().equals("/") || request.getServletPath().equals("/index")) {

            System.out.println("forward to index");
            request.setAttribute("itemsAll", prodDbao.getProducts());
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
            view.forward(request, response);

        } else if (request.getServletPath().toLowerCase().equals("/logout")) {

            request.getSession().invalidate();
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
            view.forward(request, response);

        } else if (request.getServletPath().toLowerCase().equals("/getprods")) {

            System.out.println("getprods");
            request.setAttribute("item", prodDbao.getProductById(request.getParameter("id")));
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/getprods.jsp");
            view.forward(request, response);

        } else if (request.getServletPath().equals("/gettypes")) {
            System.out.println("types");
            request.setAttribute("itemsAll", prodDbao.getProductsByType(request.getParameter("type")));
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/gettypes.jsp");
            view.forward(request, response);

        } else if (request.getServletPath().equals("/showbasket")) {
            System.out.println("show basket");
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/showbasket.jsp");
            view.forward(request, response);

        } else {

            System.out.println("catchall index");
            request.setAttribute("itemsAll", prodDbao.getProducts());
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
            view.forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession mySess = request.getSession();
        List<HashMap> myBasketItems;

        if (mySess.getAttribute("basketitems") == null) {
            mySess.setAttribute("basketitems", new ArrayList<HashMap>());
        }

        myBasketItems = (List<HashMap>) mySess.getAttribute("basketitems");

        if (request.getServletPath().equals("/addbasket")) {

            HashMap temp = prodDbao.getProductById(request.getParameter("add"));
            temp.put("basketid", new ObjectId().toString());
            myBasketItems.add(temp);
            mySess.setAttribute("basketitems", myBasketItems);

            response.getWriter().print("ITEM ADDED");
            response.getWriter().close();

        } else if (request.getServletPath().equals("/delbasket")) {
            String delItem = request.getParameter("del");

            for (HashMap hmap : myBasketItems) {
                if (hmap.get("basketid").equals(delItem)) {
                    myBasketItems.remove(hmap);
                    break;
                }
            }

            mySess.setAttribute("basketitems", myBasketItems);
            response.getWriter().print("ITEM DELETED");
            response.getWriter().close();
        }

    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("Servlet Destroy : ViewController");

        prodDbao.closeAll();
    }

    @Override
    public String getServletInfo() {
        return "damcode DamEcomWeb";
    }

}
