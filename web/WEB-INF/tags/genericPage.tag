<%@tag description="generic tag for all pages" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
        <title>Ecom();</title>
        <style type="text/css">
            body { margin:0px; }
            div.bob {
                font-family: Arial, Helvetica, sans-serif;
                font-size: x-large;
                font-weight: bold;
                font-style: normal;
                color: #336699;
                text-align: center;
                vertical-align: top;
            }
            .auto-style1 {
                font-size: large;
                font-weight: bold;
                border-color: #000000;
                padding-left: 200px;
                padding-right: 1px;
                padding-top: 1px;
                padding-bottom: 1px;
                text-align:center;
                text-transform: capitalize;
            }
            tr.itemlist:hover {
                background-color: #D5D5D5;
                cursor: pointer;
                text-transform: capitalize;
            }
            tr.itemlist {
                text-transform: capitalize;
            }
            tr.itemlist:nth-child(even) {
                background-color: #B7B7B8;
            }
            tr.itemlist:nth-child(even):hover {
                background-color: #D5D5D5;
            }
            td.itemlist:hover {
                background-color: #D5D5D5;
                cursor: pointer;
                text-transform: capitalize;
            }
            td.itemlist {
                text-transform: capitalize;
            }
            td.itemlist:nth-child(even) {
                background-color: #B7B7B8;
            }
            td.itemlist:nth-child(even):hover {
                background-color: #D5D5D5;
            }

            a.itemlist {
                text-decoration: none;
            }
        </style>

    </head>

    <body>
        <div id="container" style="width: 100%;">

            <div id="header" style="height: 30px; background-color:#D5D5D5; border-bottom: thin solid black; vertical-align: middle;">
                <div class="bob">dam_Ecom(); <c:out value="${title}"/>
                    <div style="float: right; padding-right: 50px; font-size: 60%">
                        <c:if test="${authenticated == true}">
                                        Welcome: ${customer.firstname}
                                        <a style="padding-right:5px; padding-left: 10px;" href="${webappcontext}/customer/login">Your Details</a>
                        </c:if>
                                        <a style="padding-right:5px; padding-left: 10px;" href="${webappcontext}/showbasket">Show Cart</a>
                                        [<input type="text"  size="2" disabled style="border: none; padding: none; background-color: transparent; color: black; text-align: center" value="${basketitems.size() + 0}" id="basketnumber">]
                        <c:if test="${authenticated == null || authenticated == false}">
                            <a href="${httpsloginredirect}"> Customer Login </a>
                        </c:if>
                        <c:if test="${authenticated == true}">
                            <a href="${webappcontext}/logout">Logout</a>
                        </c:if>
                    </div>
                </div>
            </div>
            <div id="itemResp" style="background-color: indianred; float: start; clear: both; text-align: center;"></div>
            <br><br>
                    <table style="border: 1px solid #000000; border-radius: 10px; background-color: #C0C0C0;" align="center" bgcolor="#C0C0C0" width="800px">
                        <tr>
                            <td></td>
                            <td style="padding: 10px">${test1}</td>
                            <td width="150px">
                            </td>

                        </tr>

                        <c:if test="${authenticated == true}">
                            <tr>
                                <td>&nbsp;</td>
                                <td>
                                        Your Last Login was: ${customer.lastlogin}
                                </td>
                                <td style="vertical-align: text-top; text-align: center;">
                                    <br>
                                        
                                </td>
                            </tr>
                        </c:if>

                        <tr>
                            <td>&nbsp;</td>
                            <td style="border-color: #000000; text-align: left; background-color: cornflowerblue" >
                                <a href="${webappcontext}/index">Home</a> > 
                                <c:out value="${toolbar}" escapeXml="false"/>
                                </td>
                                <td style="text-align: center;"></td>
                            </tr>
                            <tr>
                                <td style="padding:5px; vertical-align: top">

                                </td>
                                <td>
                                <jsp:doBody/>
                            </td>
                            <td>&nbsp;</td>
                    </table>     
                    </div>
                    </body>
                    </html>
