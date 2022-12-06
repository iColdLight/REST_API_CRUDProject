package com.coldlight.restapicrudapp.servlets;

import com.coldlight.restapicrudapp.model.FileEntity;
import com.coldlight.restapicrudapp.service.FileEntityService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/files")
public class FilesServlet extends HttpServlet {
    private final FileEntityService fileEntityService = new FileEntityService();
    private final Gson gson = new Gson();

    //Get All Files Method
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<FileEntity> allFiles = fileEntityService.getAllFiles();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if(allFiles != null){
            String filesJsonString = this.gson.toJson(allFiles);
            out.print(filesJsonString);
            out.flush();
        }else{
            System.out.println("error");
            resp.setStatus(400);
        }
    }

}
