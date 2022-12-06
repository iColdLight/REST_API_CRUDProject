package com.coldlight.restapicrudapp.servlets;

import com.coldlight.restapicrudapp.model.FileEntity;
import com.coldlight.restapicrudapp.service.FileEntityService;
import com.coldlight.restapicrudapp.util.RequestParserUtils;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/file")
public class FileServlet extends HttpServlet {
    private final FileEntityService fileEntityService = new FileEntityService();
    private final Gson gson = new Gson();


    //Add File Method & Update File Method
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = RequestParserUtils.parseHttpRequest(req);
        String action = (String) jsonObject.get("action");
        if("CREATE".equalsIgnoreCase(action)){
            String fileName = (String) jsonObject.get("name");
            fileEntityService.createFile(fileName);
        }
        else if("UPDATE".equalsIgnoreCase(action)){
            Long fileIDString = (Long) jsonObject.get("fileID");
            if(fileIDString != null){
                String fileName = (String) jsonObject.get("name");
                fileEntityService.updateFile(fileIDString, fileName);
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

    //Get File by ID Method
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileIDString = req.getParameter("fileID");
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if(fileIDString != null){
            String fileByIDJsonString = this.gson.toJson(getFileByID(fileIDString));
            out.print(fileByIDJsonString);
            out.flush();
        }else{
            System.out.println("error");
            resp.setStatus(400);
        }
    }

    //Delete File Method
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String fileID = req.getParameter("fileID");
        fileEntityService.deleteFileByID(Long.parseLong(fileID));
    }

    private FileEntity getFileByID(String fileIDString){
        Long fileID = Long.valueOf(fileIDString);
        return fileEntityService.getFileByID(fileID);
    }
}
