package com.manish.excel_import_export.service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.DataFormatter;
import com.manish.excel_import_export.entity.Address;
import com.manish.excel_import_export.entity.User;
import com.manish.excel_import_export.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void saveUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    public ByteArrayInputStream exportUsersToExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        Cell firstNameCell = headerRow.createCell(0);
        firstNameCell.setCellValue("First Name");
        firstNameCell.setCellStyle(headerStyle);

        Cell lastNameCell = headerRow.createCell(1);
        lastNameCell.setCellValue("Last Name");
        lastNameCell.setCellStyle(headerStyle);

        Cell emailCell = headerRow.createCell(2);
        emailCell.setCellValue("Email");
        emailCell.setCellStyle(headerStyle);

        Cell mobileNumberCell = headerRow.createCell(3);
        mobileNumberCell.setCellValue("Mobile Number");
        mobileNumberCell.setCellStyle(headerStyle);

        Cell streetCell = headerRow.createCell(4);
        streetCell.setCellValue("Street");
        streetCell.setCellStyle(headerStyle);

        Cell cityCell = headerRow.createCell(5);
        cityCell.setCellValue("City");
        cityCell.setCellStyle(headerStyle);

        Cell stateCell = headerRow.createCell(6);
        stateCell.setCellValue("State");
        stateCell.setCellStyle(headerStyle);

        Cell zipCodeCell = headerRow.createCell(7);
        zipCodeCell.setCellValue("Zip Code");
        zipCodeCell.setCellStyle(headerStyle);

        List<User> users = userRepository.findAll();
        int rowIndex = 1;
        for (User user : users) {
            Row userRow = sheet.createRow(rowIndex);

            Cell firstNameCell1 = userRow.createCell(0);
            firstNameCell1.setCellValue(user.getFirstName());

            Cell lastNameCell1 = userRow.createCell(1);
            lastNameCell1.setCellValue(user.getLastName());

            Cell emailCell1 = userRow.createCell(2);
            emailCell1.setCellValue(user.getEmail());

            Cell mobileNumberCell1 = userRow.createCell(3);
            mobileNumberCell1.setCellValue(user.getMobileNumber());

            Cell streetCell1 = userRow.createCell(4);
            streetCell1.setCellValue(user.getAddress().getStreet());

            Cell cityCell1 = userRow.createCell(5);
            cityCell1.setCellValue(user.getAddress().getCity());

            Cell stateCell1 = userRow.createCell(6);
            stateCell1.setCellValue(user.getAddress().getState());

            Cell zipCodeCell1 = userRow.createCell(7);
            zipCodeCell1.setCellValue(user.getAddress().getZipCode());

            rowIndex++;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    
    
public void importUsersFromExcel(MultipartFile file) throws IOException {
    if (!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
        throw new IllegalArgumentException("Invalid file type. Only Excel files are allowed.");
    }
    
    Workbook workbook = new XSSFWorkbook(file.getInputStream());
    Sheet sheet = workbook.getSheetAt(0);

    DataFormatter dataFormatter = new DataFormatter();
    
    List<User> users = new ArrayList<>();
    for (Row row : sheet) {
        if (row.getRowNum() == 0) {
            continue; // Skip the header row
        }

        User user = new User();
        Address address = new Address();

        // Using DataFormatter to get string values from cells
        String firstNameValue = dataFormatter.formatCellValue(row.getCell(0));
        user.setFirstName(firstNameValue);

        String lastNameValue = dataFormatter.formatCellValue(row.getCell(1));
        user.setLastName(lastNameValue);

        String emailValue = dataFormatter.formatCellValue(row.getCell(2));
        user.setEmail(emailValue);

        String mobileNumberValue = dataFormatter.formatCellValue(row.getCell(3));
        user.setMobileNumber(mobileNumberValue);

        String streetValue = dataFormatter.formatCellValue(row.getCell(4));
        address.setStreet(streetValue);

        String cityValue = dataFormatter.formatCellValue(row.getCell(5));
        address.setCity(cityValue);

        String stateValue = dataFormatter.formatCellValue(row.getCell(6));
        address.setState(stateValue);

        String zipCodeValue = dataFormatter.formatCellValue(row.getCell(7));
        address.setZipCode(zipCodeValue);

        user.setAddress(address);
        users.add(user);
    }

    saveUsers(users);
}

        
        


}