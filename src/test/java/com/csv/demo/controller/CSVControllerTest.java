package com.csv.demo.controller;

import com.csv.demo.model.User;
import com.csv.demo.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.Writer;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(CSVController.class)
public class CSVControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService mockFileService;

    @Test
    public void testAddEmployee() throws Exception {

        User user = new User();
        user.setId(1234);
        user.setFirstName("Pawan");
        user.setLastName("Sanap");
        user.setEmail("abc@gmail.com");
        user.setDepartment("Comp");
        when(mockFileService.save(any(User.class))).thenReturn(user);

        MockHttpServletResponse response = mockMvc.perform(post("/files/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1234,\"firstName\":\"Pawan\",\"lastName\":\"Sanap\",\"email\":\"abc@gmail.com\",\"department\":\"Comp\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testGetAllEmpDetails() throws Exception {

        doAnswer(invocation -> {
            Writer out = (Writer) invocation.getArguments()[0];
            out.write("employee data");

            return null;

        }).when(mockFileService).writeEmpDetails(any(Writer.class));

        // Perform the GET request
        MockHttpServletResponse response = mockMvc.perform(get("/files/generateCsv")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("employee data", response.getContentAsString());

        verify(mockFileService).writeEmpDetails(any(Writer.class));
    }}
