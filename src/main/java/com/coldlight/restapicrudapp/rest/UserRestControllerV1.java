package com.coldlight.restapicrudapp.rest;

import com.coldlight.restapicrudapp.entity.UserEntity;
import com.coldlight.restapicrudapp.repository.hibernate.HibernateUserRepositoryImpl;
import com.coldlight.restapicrudapp.service.UserService;
import com.coldlight.restapicrudapp.util.RequestParserUtils;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/api/v1/users", "/api/v1/users/*"})
public class UserRestControllerV1 extends HttpServlet {

    private final UserService userService = new UserService(new HibernateUserRepositoryImpl());
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = RequestParserUtils.parseHttpRequest(req);
        String firstName = (String) jsonObject.get("firstName");
        String lastName = (String) jsonObject.get("lastName");
        userService.createUser(firstName, lastName);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = RequestParserUtils.parseHttpRequest(req);
        Long userIDString = (Long) jsonObject.get("userID");
        if (userIDString != null) {
            String firstName = (String) jsonObject.get("firstName");
            String lastName = (String) jsonObject.get("lastName");
            userService.updateUser(userIDString, firstName, lastName);
        } else {
            System.out.println("ID is not found");
            resp.setStatus(400);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userID = Optional.ofNullable(req.getPathInfo()).map(path -> path.replace("/", "")).orElse(null);
        if (userID == null) {
            List<UserEntity> allUsers = userService.getAllUsers();
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            if (allUsers != null) {
                String usersJsonString = this.gson.toJson(allUsers);
                out.print(usersJsonString);
                out.flush();
            } else {
                System.out.println("error");
                resp.setStatus(400);
            }
        } else {
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            UserEntity userByID = getUserByID(userID);
            String userByIDJsonString = this.gson.toJson(userByID);
            out.print(userByIDJsonString);
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String userID = req.getParameter("userID");
        if (userID != null) {
            userService.deleteUserByID(Long.parseLong(userID));
        } else {
            System.out.println("ID is not found");
            resp.setStatus(400);
        }
    }

    private UserEntity getUserByID(String userIDString) {
        Long userID = Long.valueOf(userIDString);
        return userService.getUserWithEventsByID(userID);
    }
}
