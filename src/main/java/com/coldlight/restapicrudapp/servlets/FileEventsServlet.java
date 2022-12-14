package com.coldlight.restapicrudapp.servlets;

import com.coldlight.restapicrudapp.model.EventEntity;
import com.coldlight.restapicrudapp.model.FileEntity;
import com.coldlight.restapicrudapp.service.EventEntityService;
import com.coldlight.restapicrudapp.service.FileEntityService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/file/events")
public class FileEventsServlet extends HttpServlet {
    private final FileEntityService fileEntityService = new FileEntityService();
    private final Gson gson = new Gson();

   //Get Event by FileID Method
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileIDString = req.getParameter("fileID");
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if(fileIDString != null){
            List<EventEntity> allEventsFileID = fileEntityService.getAllEventsByFileID(Long.valueOf(fileIDString));
            String events = this.gson.toJson(allEventsFileID);
            out.print(events);
            out.print(events);
            out.flush();
        }else{
            System.out.println("error");
            resp.setStatus(400);
        }
    }
}
