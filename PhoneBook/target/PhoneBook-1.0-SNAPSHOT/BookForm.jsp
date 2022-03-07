<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Приложение мои контакты</title>
    <style type="text/css">
        a {
            color: #0060B6;
            text-decoration: none;
        }

        a:hover
        {
            color:#00A0C6;
            text-decoration:none;
            cursor:pointer;
        }
    </style>
</head>
<body style="font-family: 'Roboto', sans-serif;">
<center>
    <h1>Мои контакты</h1>
    <h2>
        <a href="/new">Добавить контакт</a>
        &nbsp;&nbsp;&nbsp;
        <a href="/list">Отобразить все контакты</a>
    </h2>
</center>
<div align="center">
    <c:if test="${mode == 2}">
    <form action="results" method="post">
    </c:if>
    <c:if test="${mode == 1}">
    <form action="update" method="post">
    </c:if>
    <c:if test="${mode == 0}">
    <form action="insert" method="post">
    </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${mode == 2}">
                        Поиск контактов
                    </c:if>
                    <c:if test="${mode == 1}">
                        Изменить контакт
                    </c:if>
                    <c:if test="${mode == 0}">
                        Добавить контакт
                    </c:if>
                </h2>
            </caption>
            <c:if test="${mode == 1}">
                <input type="hidden" name="id" value="<c:out value='${abonent.getID()}' />"/>
            </c:if>
            <tr>
                <th>Имя:</th>
                <td>
                    <input type="text" name="firstName" size="45" pattern="^[A-Za-zА-Яа-яЁё\s]+$"
                           value="<c:out value='${abonent.getFirstName()}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Фамилия:</th>
                <td>
                    <input type="text" name="lastName" size="45" pattern="^[A-Za-zА-Яа-яЁё\s]+$""
                           value="<c:out value='${abonent.getLastName()}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Телефон 1:</th>
                <td>
                    <input type="text" name="number1" size="45" pattern="[0-9]{0,15}"
                           value="<c:out value='${abonent.getPhoneNumber(1)}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Телефон 2:</th>
                <td>
                    <input type="text" name="number2" size="45" pattern="[0-9]{0,15}"
                           value="<c:out value='${abonent.getPhoneNumber(2)}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Телефон 3:</th>
                <td>
                    <input type="text" name="number3" size="45" pattern="[0-9]{0,15}"
                           value="<c:out value='${abonent.getPhoneNumber(3)}' />"
                    />
                </td>
            </tr>
            <tr>
                <c:if test="${mode == 2}">
                    <td colspan="2" align="center">
                        <input type="submit" value="Искать"/>
                    </td>
                </c:if>
                <c:if test="${mode != 2}">
                    <td colspan="2" align="center">
                        <input type="submit" value="Сохранить"/>
                    </td>
                </c:if>
            </tr>
        </table>
    </form>
</div>
</body>
</html>