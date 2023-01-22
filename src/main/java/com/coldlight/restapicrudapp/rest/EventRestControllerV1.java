package com.coldlight.restapicrudapp.rest;

import com.coldlight.restapicrudapp.entity.EventEntity;
import com.coldlight.restapicrudapp.repository.hibernate.HibernateEventRepositoryImpl;
import com.coldlight.restapicrudapp.service.EventService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/api/v1/events", "/api/v1/events/*"})
public class EventRestControllerV1 extends HttpServlet {

    private final EventService eventService = new EventService(new HibernateEventRepositoryImpl());
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String eventID = Optional.ofNullable(req.getPathInfo()).map(path -> path.replace("/", "")).orElse(null);
        if (eventID == null) {
            List<EventEntity> allEvents = eventService.getAllEvents();
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            if (allEvents != null) {
                String usersJsonString = this.gson.toJson(allEvents);
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
            EventEntity eventByID = getEventByID(eventID);
            String eventByIDJsonString = this.gson.toJson(eventByID);
            out.print(eventByIDJsonString);
            out.flush();
        }
    }

    private EventEntity getEventByID(String eventIDString) {
        Long eventID = Long.valueOf(eventIDString);
        return eventService.getEventByID(eventID);
    }
}
