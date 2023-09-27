package com.csv.demo.service;

import com.csv.demo.model.User;
import com.csv.demo.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    private FileService fileServiceUnderTest;

    @Before
    public void setUp() {
        fileServiceUnderTest = new FileService(mockUserRepository);
    }

    @Test
    public void testWriteEmpDetails() {

        final Writer writer = new StringWriter();

        final User user = new User();
        user.setId(1234);
        user.setFirstName("Pawan");
        user.setLastName("Sanap");
        user.setEmail("abc@gmail.com");
        user.setDepartment("Comp");
        final List<User> users = List.of(user);
        when(mockUserRepository.findAll()).thenReturn(users);

        // Run the test
        fileServiceUnderTest.writeEmpDetails(writer);

    }

    @Test
    public void testWriteEmpDetails_BrokenWriter() {
        final Writer writer = new Writer() {
            private final IOException exception = new IOException("Error");
            @Override
            public void write(final char[] cbuf, final int off, final int len) throws IOException {
                throw exception;
            }
            @Override
            public void flush() throws IOException {
                throw exception;
            }
            @Override
            public void close() throws IOException {
                throw exception;
            }
        };
        final User user = new User();
        user.setId(1234);
        user.setFirstName("Pawan");
        user.setLastName("Sanap");
        user.setEmail("abc@gmail.com");
        user.setDepartment("Comp");
        final List<User> users = List.of(user);
        when(mockUserRepository.findAll()).thenReturn(users);

        // Run the test
        fileServiceUnderTest.writeEmpDetails(writer);
    }

    @Test
    public void testWriteEmpDetails_UserRepositoryReturnsNoItems() {

        final Writer writer = new StringWriter();
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());

        fileServiceUnderTest.writeEmpDetails(writer);

    }

    @Test
    public void testSave() {

        final User user = new User();
        user.setId(1234);
        user.setFirstName("Pawan");
        user.setLastName("Sanap");
        user.setEmail("abc@gmail.com");
        user.setDepartment("Comp");

        final User user1 = new User();
        user1.setId(1234);
        user1.setFirstName("Pawan");
        user1.setLastName("Sanap");
        user1.setEmail("abc@gmail.com");
        user1.setDepartment("Comp");
        when(mockUserRepository.save(any(User.class))).thenReturn(user1);

        final User result = fileServiceUnderTest.save(user);
    }
}
