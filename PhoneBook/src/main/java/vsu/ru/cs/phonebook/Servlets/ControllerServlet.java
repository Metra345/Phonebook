package vsu.ru.cs.phonebook.Servlets;

import vsu.ru.cs.phonebook.DataBase.persistence.AbonentRepository;
import vsu.ru.cs.phonebook.model.Abonent;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;

public class ControllerServlet extends HttpServlet {

    private AbonentRepository abonentRepository;

    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbc:h2:~/test");
        String jdbcUsername = getServletContext().getInitParameter("sa");
        String jdbcPassword = getServletContext().getInitParameter("");

        abonentRepository = new AbonentRepository(jdbcURL, jdbcUsername, jdbcPassword);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertAbonent(request, response);
                    break;
                case "/delete":
                    deleteAbonent(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateAbonent(request, response);
                    break;
                case "/search":
                    showSearchForm(request, response);
                    break;
                case "/results":
                    searchAbonent(request, response);
                    break;
                default:
                    newListAbonent(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listAbonent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Abonent> listAbonent = abonentRepository.list();
        request.setAttribute("listAbonent", listAbonent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
        dispatcher.forward(request, response);
    }

    private void newListAbonent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String firstName = "";
        String lastName = "";
        String number1 = "";
        String number2 = "";
        String number3 = "";
        if (request.getParameterMap().size() >= 5) {
            firstName = request.getParameter("firstName");
            lastName = request.getParameter("lastName");
            number1 = request.getParameter("number1");
            number2 = request.getParameter("number2");
            number3 = request.getParameter("number3");
        }
        Abonent pattern = new Abonent(firstName, lastName, number1, number2, number3);
        List<Abonent> listAbonent = abonentRepository.search(pattern);
        request.setAttribute("listAbonent", listAbonent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
        request.setAttribute("mode", 0);
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Abonent existingAbonent = abonentRepository.find(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
        request.setAttribute("abonent", existingAbonent);
        request.setAttribute("mode", 1);
        dispatcher.forward(request, response);
    }

    private void showSearchForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
        request.setAttribute("mode", 2);
        dispatcher.forward(request, response);
    }

    private void insertAbonent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String firstName = cast(request.getParameter("firstName"));
        String lastName = cast(request.getParameter("lastName"));
        String number1 = cast(request.getParameter("number1"));
        String number2 = cast(request.getParameter("number2"));
        String number3 = cast(request.getParameter("number3"));
        Abonent abonent = new Abonent(firstName, lastName, number1, number2, number3);
        abonentRepository.insert(abonent);
        response.sendRedirect("list");
    }

    private String cast(String in) throws UnsupportedEncodingException {
        return (new String(in.getBytes(Charset.forName("ISO-8859-1")), "UTF-8"));
    }

    private void searchAbonent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String number1 = request.getParameter("number1");
        String number2 = request.getParameter("number2");
        String number3 = request.getParameter("number3");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("list?firstName=")
                .append(firstName)
                .append("&lastName=")
                .append(lastName)
                .append("&number1=")
                .append(number1)
                .append("&number2=")
                .append(number2)
                .append("&number3=")
                .append(number3)
                .append("&page=0");
        response.sendRedirect(stringBuilder.toString());
    }

    private void updateAbonent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String firstName = cast(request.getParameter("firstName"));
        String lastName = cast(request.getParameter("lastName"));
        String number1 = cast(request.getParameter("number1"));
        String number2 = cast(request.getParameter("number2"));
        String number3 = cast(request.getParameter("number3"));
        Abonent abonent = new Abonent(id, firstName, lastName, number1, number2, number3);
        abonentRepository.update(abonent);
        response.sendRedirect("list");
    }

    private void deleteAbonent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        abonentRepository.delete(id);
        response.sendRedirect("list");
    }
}
