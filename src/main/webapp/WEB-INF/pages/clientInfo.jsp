<%@ page import="com.teleprovider.model.Client" %>
<%@ page import="com.teleprovider.model.Account" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.teleprovider.model.Tariff" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
<head>
    <title>Client information</title>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="Content-Language" content="ru">
    <%@include file="headerStyles.jsp"%>
</head>

<body>
<%
    Client client = (Client) request.getAttribute("client");
    Account account = client.getAccount();
    List<Tariff> tariffList = (List<Tariff>) request.getAttribute("tariffs");
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
%>

<%@ include file="topLine.jsp" %>

<div class="container">
    <h1><%= client.getName()%></h1>
    <hr color="black">
    <h3>Client information:</h3>
    <li><strong>Address: </strong><%= client.getAddress()%></li>
    <li><strong>E-mail: </strong><%= client.getEmail() != null ? client.getEmail() : ""%></li>
    <li><strong>Info: </strong><%= client.getInfo() != null ? client.getInfo() : ""%></li>
    <hr color="black">
    <h3>Account and tariff information:</h3>
    <li><strong>Funds: </strong>$<%= account.getFunds()%></li>
    <li><strong>Tariff: </strong><%= account.getTariff() != null
            ? account.getTariff().getName() + " - $" + account.getTariff().getCost()
            : ""%></li>
    <li><strong>Activation date: </strong><%= account.getActivationDate() != null
            ? sdf.format(account.getActivationDate()) : ""%></li>

    <hr color="black">
    <hr color="black">
    <form action="/saveclient" method="post" accept-charset="utf-8">
        <div class="row">
            <div class="col-md-6">
                <input type="hidden" name="id" value="<%=client.getId()%>">
                <h4>Change client info</h4>
                <div class="form-group delimiter mt10">
                    <label for="name">Name</label>
                    <input type="text" name="name" id="name" class="form-control" value="<%=client.getName()%>" required/>
                </div>
                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" name="address" id="address" class="form-control" value="<%=client.getAddress()%>" required/>
                </div>
                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" name="email" id="email" class="form-control" value="<%=client.getEmail()%>" required/>
                </div>
                <div class="form-group">
                    <label for="info">Information</label>
                    <textarea name="info" id="info" class="form-control" rows="3" maxlength="255"
                              ><%=client.getInfo() != null ? client.getInfo() : ""%></textarea>
                </div>
                <input type="submit" value="Save" />
            </div>
        </div>
    </form>

    <hr color="black">
    <hr color="black">
    <h4>Change account values</h4>
    <p></p>
    <div class="dropdown">
        <p>Change account tariff</p>
        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown" id="btntariff">
            Current tariff: <%=account.getTariff() != null
                ? account.getTariff().getName() + " - $" + account.getTariff().getCost()
                : "none"%><b class="caret"></b></button>
        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
            <%for (Tariff tariff : tariffList) {%>
                <li><a href="/changetariff/<%=account.getClient_id()%>/<%=tariff.getId()%>"><%=tariff.getName() + " - $" + tariff.getCost()%></a></li>
            <%}%>
            <li><a href="/changetariff/<%=account.getClient_id()%>/0">none</a></li>
        </ul>
    </div>

    <p></p>

    <%--Add funds to client account--%>
    <form action="/transaction" method="post" accept-charset="utf-8">
        <div class="row">
            <div class="col-md-6">
                <input type="hidden" name="client_id" value="<%=client.getId()%>">
                <div class="form-group delimiter mt10">
                    <label for="funds">Add funds to this account</label>
                    <input type="number" name="funds" id="funds" class="form-control" value="0" min="0" required/>
                </div>
                <input type="hidden" name="comment" value="Manual add funds to client account">
                <input type="submit" value="Add funds" />
            </div>
        </div>
    </form>

</div>
</body>
</html>