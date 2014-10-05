<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="Show Basket" scope="session"/>
<c:set var="toolbar" value="" scope="session"/>

<t:genericPage>
    <script type="text/javascript">
        function delBasket(item) {
            var xmlhttp;
            if (window.XMLHttpRequest) {
                xmlhttp = new XMLHttpRequest();
            } else {
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.open("POST", "delbasket", true);
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    window.location.reload();
                    document.getElementById("itemResp").innerHTML = xmlhttp.responseText;
                }
            };
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send("del=" + item);

        }
    </script>
    <table align="center" style="border-collapse: collapse; width: 100%">
        <c:forEach items="${basketitems}" var="item">
            <tr>
                <td class="itemlist" onclick="location.href = '${webappcontext}/getProds?id=${item._id}'" style="text-align: left">${item.desc_short}</td>
                <td style="text-align: right">${item.sell_price}</td>
                <td><input onClick="delBasket('${item.basketid}')" type="button" name="del" value="Del"></td>
            </tr>
        </c:forEach>
            <tr>
                <td>
                    <c:if test="${authenticated == null || authenticated == false}">
                            <a href="${httpsloginredirect}">Please Login to place Order.</a>
                        </c:if>
                        <c:if test="${authenticated == true}">
                            <form action="${webappcontext}/customer/placeorder" method="POST">
                            <input type="submit" name="doorder" value="Place Order!">
                            </form>
                        </c:if>
                    
                </td>
            </tr>    
    </table>
</t:genericPage>
