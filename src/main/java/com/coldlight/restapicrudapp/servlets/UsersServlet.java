package com.coldlight.restapicrudapp.servlets;

import com.coldlight.restapicrudapp.model.UserEntity;
import com.coldlight.restapicrudapp.service.UserEntityService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/users")
public class UsersServlet extends HttpServlet {
    private final UserEntityService userEntityService = new UserEntityService();
    private final Gson gson = new Gson();


    //Get All Users Method
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<UserEntity> allUsers = userEntityService.getAllUsers();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if(allUsers != null){
            String usersJsonString = this.gson.toJson(allUsers);
            out.print(usersJsonString);
            out.flush();
        }else{
            System.out.println("error");
            resp.setStatus(400);
        }
    }
}
