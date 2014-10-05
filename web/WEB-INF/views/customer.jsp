<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" value="Customer Details" scope="session"/>
<t:genericPage>
<c:if test="${authenticated == true}">
          
          
    <table>
        <tr>
            <td>First Name:</td>
            <td>${customer.firstname}</td>
        </tr>
        <tr>
            <td>Last Name:</td>
            <td>${customer.lastname}</td>
        </tr>
        <tr>
            <td>Email Address:</td>
            <td>${customer.email}</td>
        </tr>
        <tr>
            <td>Web Login ID:</td>
            <td>${customer.weblogin}</td>
        </tr>
        <tr>
            <td>Street Address:</td>
            <td>${customer.address}</td>
        </tr>
        <tr>
            <td>
                <a href="${webappcontext}/customer/orderhist">View Order History</a>
            </td>
        </tr>
      </c:if>
        <c:if test="${authenticated == null || authenticated == false}">
            <tr><td colspan="2">
                    <form action="${webappcontext}/customer/login" method="POST" >
                        <table style="border: 1px solid #000000; border-radius: 5px; background-color: #C0C0C0;" align="right" bgcolor="#C0C0C0; width: 100%">
                            <tr>
                                <td style="">Login:</td>
                                <td><input name="login_name" type="text"/></td>
                            </tr>
                            <tr>
                                <td>Pass:</td>
                                <td><input name="login_pass" type="password"/></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><input name="login_go" type="submit" value="Login" /></td>
                            </tr>
                        </table>
                    </form>
                </td></tr>
            </c:if>
    </table>

</t:genericPage>
