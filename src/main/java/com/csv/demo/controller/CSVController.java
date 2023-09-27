package com.csv.demo.controller;

import com.csv.demo.model.User;
import com.csv.demo.service.FileService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/files")
public class CSVController {


    private final FileService fileService;

    public CSVController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/add")
    public User addEmployee(@RequestBody User user) {
        // Save the employee to the database
        return fileService.save(user);
    }

    @GetMapping("/generateCsv")
    public void getAllEmpDetails(HttpServletResponse servletResponse) throws Exception{
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"user.csv\"");
        fileService.writeEmpDetails(servletResponse.getWriter());

    }

}
