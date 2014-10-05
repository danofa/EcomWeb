<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="title" value="Products" scope="session"/>
<c:set var="toolbar" value="" scope="session"/>
<t:genericPage>

    <table align="center" style="border-collapse: collapse;">
        <c:forEach items="${itemsAll}" var="item">
            <tr class="itemlist" onclick="location.href='${webappcontext}/getProds?id=${item._id}'">
                <td><b>${item.desc_short}</b> : <i>${item.desc_long}</i></td>
                <td style="text-align: right;">${item.type}</td>
            </tr>            
        </c:forEach>
    </table>
</t:genericPage>
