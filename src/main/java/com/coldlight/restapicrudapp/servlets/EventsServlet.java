package com.coldlight.restapicrudapp.servlets;

import com.coldlight.restapicrudapp.model.EventEntity;
import com.coldlight.restapicrudapp.model.FileEntity;
import com.coldlight.restapicrudapp.service.EventEntityService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/events")
public class EventsServlet extends HttpServlet {
    private final EventEntityService eventEntityService =new EventEntityService();
    private final Gson gson = new Gson();

    //Get All Events Method
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<EventEntity> allEvents = eventEntityService.getAllEvents();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if(allEvents != null){
            String eventsJsonString = this.gson.toJson(allEvents);
            out.print(eventsJsonString);
            out.flush();
        }else{
            System.out.println("error");
            resp.setStatus(400);
        }
    }
}
