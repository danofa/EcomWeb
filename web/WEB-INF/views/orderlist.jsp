<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" value="Order History" scope="session"/>
<c:set var="toolbar" value='<a href="${webappcontext}/customer/orderhist">Order History</a>' scope="session"/>

<t:genericPage>
    <table>
        <c:if test="${authenticated == true && orderlist != null}">

        <c:forEach items="${orderlist}" var="ol">
            <tr>
                <td><a href="${webappcontext}/customer/orderhist?orderid=${ol.orderid}">${ol.orderdate}</a></td>
            </tr>            
        </c:forEach>

            
        </c:if>

    </table>
</t:genericPage>
