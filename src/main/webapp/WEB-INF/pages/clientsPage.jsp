<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.teleprovider.model.Client" %>
<%@ page import="java.util.List" %>

<%--
  User: olegs
  Date: 29.03.2017
--%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Clients List</title>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="Content-Language" content="ru">
    <%@include file="headerStyles.jsp"%>
</head>
<body>
    <%List<Client> clientsList = (List<Client>) request.getAttribute("clients");%>
    <%@ include file="topLine.jsp" %>

    <div class="container">
        <p></p>

        <h2>Clients list</h2>

        <div class="mb20">
            <form class="form-inline" action="/clients/filter" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" id="filtername" name="filtername" placeholder="Name">
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" id="filteraddress" name="filteraddress" placeholder="Address" style="margin-left: 20px">
                </div>
                <button type="submit" class="btn btn-primary" style="margin-left: 20px">Search</button>
            </form>
        </div>

        <p>Click on client name to open account information</p>
        <table id="dataTable" class="table table-striped table-bordered" width="100%" cellspacing="0" >
            <thead>
            <tr>
                <th width="20">ID</th>
                <th>Name</th>
                <th>Address</th>
                <th>E-mail</th>
                <th>Info</th>
            </tr>
            </thead>
            <tfoot>
            <tr>
                <th width="20">ID</th>
                <th>Name</th>
                <th>Address</th>
                <th>E-mail</th>
                <th>Info</th>
            </tr>
            </tfoot>
            <tbody>

            <%if (clientsList.size()>0){
                for (Client client : clientsList){%>
            <tr>
                <td width="20" align="center"><%out.print(client.getId());%></td>
                <td><a href="<%out.print("/clientinfo/"+client.getId());%>"><%out.print(client.getName());%></a></td>
                <td><address><%out.print(client.getAddress());%></address></td>
                <td><%out.print(client.getEmail());%></td>
                <td><%out.print(client.getInfo());%></td>
            </tr>
            <%  }
            }%>
            </tbody>
        </table>


        <form action="/saveclient" method="post" accept-charset="utf-8">
            <div class="row">
                <div class="col-md-6">
                    <%--<input type="hidden" name="id" value="">--%>
                    <h4>Add new client</h4>
                    <div class="form-group delimiter mt10">
                        <input type="text" name="name" id="name" class="form-control" placeholder="Name" required/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="address" id="address" class="form-control" placeholder="Address" required/>
                    </div>
                    <div class="form-group">
                        <%--TODO add validator--%>
                        <input type="email" name="email" id="email" class="form-control" placeholder="E-mail"/>
                    </div>
                    <div class="form-group">
                        <textarea name="info" id="info" class="form-control" rows="3" maxlength="1000" placeholder="Information"></textarea>
                    </div>
                    <input type="submit" value="Create" />
                </div>
            </div>
        </form>

    </div>
</body>
</html>
