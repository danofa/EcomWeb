<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" value="View Products" scope="session"/>
<c:set var="toolbar" value='<a href="${webappcontext}/gettypes?type=${item.type}">${item.type}</a>' scope="session"/>
<t:genericPage>
    <script type="text/javascript">function sendBasket(item) {
            var xmlhttp;
            if (window.XMLHttpRequest) {
                xmlhttp = new XMLHttpRequest();
            }
            else {
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.open("POST", "addbasket", true);
            xmlhttp.onreadystatechange = function()
            {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
                {
                    document.getElementById("itemResp").innerHTML = xmlhttp.responseText;
                }
            }
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            var bcount = Number(document.getElementById("basketnumber").value) + 1;
            document.getElementById("basketnumber").value = bcount;
            xmlhttp.send("add=" + item);
        }
    </script>
    <table>
        <tr>
            <td>

                <span class="auto-style1">${item.desc_short}</span>

                ${item.sell_price}

            </td>
        </tr>
        <tr>
            <td>
                ${item.desc_long}
            </td>
        </tr>
        <tr><td>
                <input type="button" onClick="sendBasket('${item._id}')" value="Add to Basket">
            </td></tr>

    </table>
</t:genericPage>     
