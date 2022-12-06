package com.coldlight.restapicrudapp.servlets;

import com.coldlight.restapicrudapp.model.EventEntity;
import com.coldlight.restapicrudapp.service.EventEntityService;
import com.coldlight.restapicrudapp.util.RequestParserUtils;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/event")
public class EventServlet extends HttpServlet {
    private final EventEntityService eventEntityService =new EventEntityService();
    private final Gson gson = new Gson();

    //Add Event Method & Update Event Method
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = RequestParserUtils.parseHttpRequest(req);
        String action = (String) jsonObject.get("action");
        if("CREATE".equalsIgnoreCase(action)){
            Long eventDate = (Long) jsonObject.get("date");
            eventEntityService.createEvent(eventDate);
        }
        else if("UPDATE".equalsIgnoreCase(action)){
            Long eventIDString = (Long) jsonObject.get("eventID");
            if(eventIDString != null){
                Long eventDate = (Long) jsonObject.get("date");
                eventEntityService.updateEvent(eventIDString, eventDate);
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

    //Get Event by ID Method
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String eventIDString = req.getParameter("eventID");
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if(eventIDString != null){
            String eventByIDJsonString = this.gson.toJson(getEventByID(eventIDString));
            out.print(eventByIDJsonString);
            out.flush();
        }else{
            System.out.println("error");
            resp.setStatus(400);
        }
    }

    //Delete Event Method
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String eventID = req.getParameter("eventID");
        eventEntityService.deleteEventByID(Long.parseLong(eventID));
    }

    private EventEntity getEventByID(String eventIDString){
        Long eventID = Long.valueOf(eventIDString);
        return eventEntityService.getEventByID(eventID);
    }
}
