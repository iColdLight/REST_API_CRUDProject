package com.coldlight.restapicrudapp.servlets;

import com.coldlight.restapicrudapp.model.EventEntity;
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

@WebServlet(urlPatterns = "/user/events")
public class UserEventsServlet extends HttpServlet {
    private final UserEntityService userEntityService = new UserEntityService();
    private final Gson gson = new Gson();


    //Get Event by UserID Method
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userIDString = req.getParameter("userID");
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if(userIDString != null){
            List<EventEntity> allEventsByUserID = userEntityService.getAllEventsByUserID(Long.valueOf(userIDString));
            String events = this.gson.toJson(allEventsByUserID);
            out.print(events);
            out.print(events);
            out.flush();
        }else{
            System.out.println("error");
            resp.setStatus(400);
        }
    }
}
