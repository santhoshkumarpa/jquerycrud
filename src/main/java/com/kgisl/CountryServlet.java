package com.kgisl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Country
 */
@WebServlet("/country")
public class CountryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);

        String query = "SELECT * from datas";
        List<Object> list;
        try {
            list = MySqlConnect.getDbCon().resultSetToArrayList(query);

            List<Country> countryList = (List<Country>) (List<?>) list;
            String countryJsonString = new Gson().toJson(countryList);
            System.out.println(countryJsonString);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(countryJsonString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("put data ");
        String requestData = req.getReader().lines().collect(Collectors.joining());
        System.out.println("????? requestData ?????\n\n" + requestData + "\n\n");

        Gson gson = new Gson();
        Country updateCountry = gson.fromJson(requestData, Country.class);

        System.out.println("$$$$$$$$$$$$$$");
        System.out.println(updateCountry.getId() + " ---->" + updateCountry.getName());

        String query = "UPDATE datas SET id=" + updateCountry.getId() + ",name='" + updateCountry.getName()
                + "' where id=" + updateCountry.getId();
        try {

            int rs = MySqlConnect.getDbCon().insert(query);
            System.out.println("the no of rows edited   " + rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // String requestData = req.getReader().lines().collect(Collectors.joining());

        System.out.println("save method called");
        String mode = req.getParameter("mode");
        System.out.println("mode value called::" + mode);
        String requestData = req.getReader().lines().collect(Collectors.joining());
        System.out.println("????? requestData ?????\n\n" + requestData + "\n\n");

        Gson gson = new Gson();
        Country newCountry = gson.fromJson(requestData, Country.class);

        System.out.println("$$$$$$$$$$$$$$");
        System.out.println(newCountry.getId() + " ---->" + newCountry.getName());

        String query = "INSERT INTO datas (id, name) VALUES (" + newCountry.getId() + ",'" + newCountry.getName()
                + "')";
        try {
            int rs = MySqlConnect.getDbCon().insert(query);
            System.out.println("the no of rows inserted   " + rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestData = req.getReader().lines().collect(Collectors.joining());
       System.out.println("????? requestData ?????\n\n" + requestData + "\n\n");
       Gson gson = new Gson();
       Country removeCountry = gson.fromJson(requestData, Country.class);

       System.out.println("$$$$$$$$$$$$$$");
       System.out.println(removeCountry.getId()+" ---->"+removeCountry.getName());
        String query = "DELETE from datas where id=" + removeCountry.getId();
        try {
            int rs = MySqlConnect.getDbCon().delete(query);
            System.out.println("the no of rows deleted   " + rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:9090/");
        resp.setHeader("Access-Control-Allow-Methods", "GET");
    }

}