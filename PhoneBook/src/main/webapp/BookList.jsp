<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
    <a href="/search">Поиск контакта</a>
    &nbsp;&nbsp;&nbsp;
    <a href="/list">Вывести всех абонентов</a>

  </h2>

  <script type="text/javascript">
    var lastResFind=""; // последний удачный результат
    var copy_page=""; // копия страницы в ихсодном виде
    function TrimStr(s) {
      s = s.replace( /^\s+/g, '');
      return s.replace( /\s+$/g, '');
    }
    function FindOnPage(inputId) {//ищет текст на странице, в параметр передается ID поля для ввода
      var obj = window.document.getElementById(inputId);
      var textToFind;

      if (obj) {
        textToFind = TrimStr(obj.value);//обрезаем пробелы
      } else {
        alert("Введенная фраза не найдена");
        return;
      }
      if (textToFind == "") {
        alert("Вы ничего не ввели");
        return;
      }

      if(document.body.innerHTML.indexOf(textToFind)=="-1")
        alert("Ничего не найдено, проверьте правильность ввода!");

      if(copy_page.length>0)
        document.body.innerHTML=copy_page;
      else copy_page=document.body.innerHTML;


      document.body.innerHTML = document.body.innerHTML.replace(eval("/name="+lastResFind+"/gi")," ");//стираем предыдущие якори для скрола
      document.body.innerHTML = document.body.innerHTML.replace(eval("/"+textToFind+"/gi"),"<a name="+textToFind+" style='background:red'>"+textToFind+"</a>"); //Заменяем найденный текст ссылками с якорем;
      lastResFind=textToFind; // сохраняем фразу для поиска, чтобы в дальнейшем по ней стереть все ссылки
      window.location = '#'+textToFind;//перемещаем скрол к последнему найденному совпадению
    }
  </script>
  <input type="text" id="text-to-find" value="">
  <input type="button" onclick="javascript: FindOnPage('text-to-find'); return false;" value="Искать"/>
</center>

<div align="center">
  <table border="1" cellpadding="5">
    <caption><h2>Список абонентов</h2></caption>

    <tr>
      <th>ID</th>
      <th>Имя</th>
      <th>Фамилия</th>
      <th>Телефон 1</th>
      <th>Телефон 2</th>
      <th>Телефон 3</th>
      <th>Действия</th>
    </tr>
    <c:forEach var="abonent" items="${listAbonent}">
      <tr>
        <td><c:out value="${abonent.getID()}" /></td>
        <td><c:out value="${abonent.getFirstName()}" /></td>
        <td><c:out value="${abonent.getLastName()}" /></td>
        <td><c:out value="${abonent.getPhoneNumber(1)}" /></td>
        <td><c:out value="${abonent.getPhoneNumber(2)}" /></td>
        <td><c:out value="${abonent.getPhoneNumber(3)}" /></td>
        <td>
          <a href="/edit?id=<c:out value='${abonent.getID()}' />">Изменить</a>
          &nbsp;&nbsp;&nbsp;&nbsp;
          <a href="/delete?id=<c:out value='${abonent.getID()}' />">Удалить</a>
        </td>
      </tr>
    </c:forEach>
  </table>

  <!--
  <ul class="pagination">
    <a href="&direction=-1"> Предыдущая </a>
    <a href="&direction=1"> Следующая </a>
  </ul>
  -->

</div>
</body>
</html>