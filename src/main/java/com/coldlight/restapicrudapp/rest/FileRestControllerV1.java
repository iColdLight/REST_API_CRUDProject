package com.coldlight.restapicrudapp.rest;

import com.coldlight.restapicrudapp.entity.FileEntity;
import com.coldlight.restapicrudapp.repository.hibernate.HibernateEventRepositoryImpl;
import com.coldlight.restapicrudapp.repository.hibernate.HibernateFileRepositoryImpl;
import com.coldlight.restapicrudapp.repository.hibernate.HibernateUserRepositoryImpl;
import com.coldlight.restapicrudapp.service.EventService;
import com.coldlight.restapicrudapp.service.FileService;
import com.coldlight.restapicrudapp.service.UserService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = "/api/v1/files")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class FileRestControllerV1 extends HttpServlet {

    private final FileService fileService = new FileService(
            new HibernateFileRepositoryImpl(),
            new UserService(new HibernateUserRepositoryImpl()),
            new EventService(new HibernateEventRepositoryImpl())) {};

    private final Gson gson = new Gson();
    private static final String MY_AWS_S3_BUCKET = "us-east-1/mys3-bucket/";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String fileName = req.getPart("file").getSubmittedFileName();
        String filePath = MY_AWS_S3_BUCKET+fileName;
        String userID = req.getParameter("userID");
        fileService.createFile(fileName, filePath, Long.parseLong(userID));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileID = Optional.ofNullable(req.getPathInfo()).map(path -> path.replace("/", "")).orElse(null);
        if(fileID ==null){
            List<FileEntity> allFiles = fileService.getAllFiles();
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            if(fileID != null){
                String usersJsonString = this.gson.toJson(allFiles);
                out.print(usersJsonString);
                out.flush();
            }else{
                System.out.println("error");
                resp.setStatus(400);
            }
        }else{
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            FileEntity fileByID = getFileByID(fileID);
            String fileByIDJsonString = this.gson.toJson(fileByID);
            out.print(fileByIDJsonString);
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileID = req.getParameter("fileID");
        fileService.deleteFileByID(Long.parseLong(fileID));
    }

    private FileEntity getFileByID(String fileIDString){
        Long fileID = Long.valueOf(fileIDString);
        return fileService.getFileByID(fileID);
    }
}
