package com.manish.excel_import_export.controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.manish.excel_import_export.entity.Address;
import com.manish.excel_import_export.entity.User;
import com.manish.excel_import_export.repository.UserRepository;
import com.manish.excel_import_export.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importUsersFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            userService.importUsersFromExcel(file);
            return ResponseEntity.ok("Users imported successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error importing users: " + e.getMessage());
        }
    }

   @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportUsersToExcel() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "users.xlsx");

            return new ResponseEntity<>(new InputStreamResource(userService.exportUsersToExcel()), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/set-data")
    public String setupDummyData(User user) {

        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setMobileNumber("1234567890");

        Address address1 = new Address();
        address1.setStreet("123 Main St");
        address1.setCity("Anytown");
        address1.setState("CA");
        address1.setZipCode("12345");
        user1.setAddress(address1);
        users.add(user1);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setMobileNumber("9876543210");

        Address address2 = new Address();
        address2.setStreet("456 Elm St");
        address2.setCity("Othertown");
        address2.setState("NY");
        address2.setZipCode("67890");
        user2.setAddress(address2);
        users.add(user2);

        userRepository.saveAll(users);

        return "Set data for user completed successfully";
    }
}
