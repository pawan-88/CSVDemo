package com.csv.demo.service;

import com.csv.demo.model.User;
import com.csv.demo.repository.UserRepository;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class FileService {

    private final UserRepository userRepository;

    public FileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void writeEmpDetails(Writer writer) {
        List<User> users = userRepository.findAll();
        try (CSVWriter csvWriter = new CSVWriter(writer)) {

            String[] header = {"ID", "First Name", "Last Name", "Email", "Department"};
            csvWriter.writeNext(header);

            for (User user : users) {
                String[] dataRow = {
                        user.getId().toString(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getDepartment()
                };
                csvWriter.writeNext(dataRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
