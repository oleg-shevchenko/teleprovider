<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.teleprovider.model.Client" %>
<%@ page import="java.util.List" %>
<%@ page import="com.teleprovider.model.Transaction" %>
<%@ page import="com.teleprovider.utils.Consts" %>

<%--
  User: olegs
  Date: 31.03.2017
--%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Transactions List</title>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="Content-Language" content="ru">
    <%@include file="headerStyles.jsp"%>
</head>
<body>
<%List<Transaction> transactionList = (List<Transaction>) request.getAttribute("transactions");%>
<%@ include file="topLine.jsp" %>

<div class="container">
    <p></p>

    <h2>Transactions List</h2>
    <div class="mb20">
        <form class="form-inline" action="/transactions/filter" method="post">
            <div class="form-group">
                <input type="text" class="form-control" id="filtername" name="filtername" placeholder="Name">
            </div>
            <div class="form-group">
                <input type="date" class="form-control" id="datestart" name="datestart" placeholder="Start date" style="margin-left: 20px">
            </div>
            <div class="form-group">
                <input type="date" class="form-control" id="dateend" name="dateend" placeholder="End date" style="margin-left: 20px">
            </div>
            <button type="submit" class="btn btn-primary" style="margin-left: 20px">Search</button>
        </form>
    </div>
    <table id="dataTable" class="table table-striped table-bordered" width="100%" cellspacing="0" >
        <thead>
        <tr>
            <th width="20" align="center">ID</th>
            <th align="center">Acc ID</th>
            <th align="center">Name</th>
            <th align="center">Time</th>
            <th align="center">Summ</th>
            <th align="center">Comment</th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <th width="20" align="center">ID</th>
            <th align="center">Acc ID</th>
            <th align="center">Name</th>
            <th align="center">Time</th>
            <th align="center">Summ</th>
            <th align="center">Comment</th>
        </tr>
        </tfoot>
        <tbody>

        <%if (transactionList.size()>0){
            for (Transaction tr : transactionList){%>
        <tr>
            <td width="20"><%out.print(tr.getId());%></td>
            <td><a href="<%out.print("/transactions/"+tr.getAccount().getClient_id());%>"><%out.print(tr.getAccount().getClient_id());%></a></td>
            <td><a href="<%out.print("/clientinfo/"+tr.getAccount().getClient_id());%>"><%out.print(tr.getAccount().getClient().getName());%></a></td>
            <td align="center"><%out.print(Consts.dateTimeFormat.format(tr.getDate()));%></td>
            <td><%out.print(tr.getSumm());%></td>
            <td><%out.print(tr.getComment());%></td>
        </tr>
        <%  }
        }%>
        </tbody>
    </table>
</div>
</body>
</html>
