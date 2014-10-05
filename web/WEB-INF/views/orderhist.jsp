<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" value="Orders" scope="session"/>
<c:set var="toolbar" value='<a href="${webappcontext}/customer/orderhist">Order History</a>' scope="session"/>

<t:genericPage>
    <table>
        <c:if test="${authenticated == true && orderinfo != null}">

            <c:if test="${param.oc == 't'}">
                ORDER SUCCESSFUL! Here are the details:
            </c:if>
            <tr>
                <td>
                    Order ID: ${orderinfo.orderid}
                </td>
                <td>
                    Date Placed : ${orderinfo.orderdate}
                </td>
            </tr>
            <tr style="background-color: pink;">
                <td>
                    <c:forEach items="${orderinfo.order}" var="orditem">
                        ${orditem.desc_short} ( ${orditem.sell_price} ) <br>
                    </c:forEach>
                </td>
            </tr>            

        </c:if>
        
    </table>
</t:genericPage>
