package com.coldlight.restapicrudapp.servlets;


import com.coldlight.restapicrudapp.model.UserEntity;
import com.coldlight.restapicrudapp.service.UserEntityService;
import com.coldlight.restapicrudapp.util.RequestParserUtils;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/user")
public class UserServlet extends HttpServlet {
    private final UserEntityService userEntityService = new UserEntityService();
    private final Gson gson = new Gson();

    //Add User Method & Update User Method
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = RequestParserUtils.parseHttpRequest(req);
        String action = (String) jsonObject.get("action");
        if("CREATE".equalsIgnoreCase(action)){
            String firstName = (String) jsonObject.get("firstName");
            String lastName = (String) jsonObject.get("lastName");
            userEntityService.createUser(firstName,lastName);
        }
        else if("UPDATE".equalsIgnoreCase(action)){
            Long userIDString = (Long) jsonObject.get("userID");
            if(userIDString != null){
                String firstName = (String) jsonObject.get("firstName");
                String lastName = (String) jsonObject.get("lastName");
                userEntityService.updateUser(userIDString, firstName,lastName);
            }
            else{
                System.out.println("error");
                resp.setStatus(400);
            }
        } else {
            System.out.println("Unsupported operation!");
            resp.setStatus(400);
        }
    }


    //Get User by ID Method
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userIDString = req.getParameter("userID");
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if(userIDString != null){
            String userByIDJsonString = this.gson.toJson(getUserByID(userIDString));
            out.print(userByIDJsonString);
            out.flush();
        }else{
            System.out.println("error");
            resp.setStatus(400);
        }
    }


    //Delete User Method
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String userID = req.getParameter("userID");
        userEntityService.deleteUserByID(Long.parseLong(userID));
    }


    private UserEntity getUserByID(String userIDString){
        Long userID = Long.valueOf(userIDString);
        return userEntityService.getEverythingUserByID(userID);
    }
}
