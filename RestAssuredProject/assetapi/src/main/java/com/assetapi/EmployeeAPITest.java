
package com.assetapi;

import java.net.URI;
import java.net.http.*;
import java.util.*;

public class EmployeeAPITest {

    public static void main(String[] args) throws Exception {

        // API Configuration
        String baseUrl = "http://localhost:8081";
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJhZG1pbk5hbWUiOiJIZW1hIiwic3ViIjoiVjAwODU3IiwibG9jYXRpb24iOiJCYW5nYWxvcmUiLCJleHAiOjE3OTQ1NjI4NzksImlhdCI6MTc5NDU0NDg3OX0.lb7yd4UzbJoxmMnkDFzl5XlJiCWi7EFCPu9yF8argDcEFo6SqKvhkkeNAAQWgwegPJvG2ZKMo7h_t-OTRyKokw";

        String endpoint = baseUrl +
                "/assetManager/v1/employee/saveemployee";

        HttpClient client = HttpClient.newHttpClient();

        // Employee details
        String firstName = "hema";
        String lastName = "chandran";

        System.out.println("EMPLOYEE CREATION RESULTS");
        System.out.println("==========================================");

        // Generate 10 employees
        for (int i = 1; i <= 10; i++) {

            String empId = "V" + (33340 + i);

            String mail = "employee1" + i +
                    "@gmail.com";

            String mobile = "96494" +
                    String.format("%05d", i);

            // JSON request body
            String json = "{"
                    + "\"empId\":\"" + empId + "\","
                    + "\"firstName\":\"" + firstName + "\","
                    + "\"lastName\":\"" + lastName + "\","
                    + "\"role\":\"Employee\","
                    + "\"mail\":\"" + mail + "\","
                    + "\"mobile\":\"" + mobile + "\","
                    + "\"location\":\"Chennai\","
                    + "\"status\":\"\","
                    + "\"department\":\"Delivery\","
                    + "\"designation\":\"QA\""
                    + "}";

            try {

                // API POST request
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endpoint))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();

                // Send request
                HttpResponse<String> response = client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

                // Print result
                if (response.statusCode() >= 200 &&
                        response.statusCode() < 300) {

                    System.out.println("SUCCESS");
                    System.out.println("Employee ID : " + empId);
                    System.out.println("First Name  : " + firstName);
                    System.out.println("Last Name   : " + lastName);
                    System.out.println("Email       : " + mail);
                    System.out.println("Mobile      : " + mobile);

                } else {

                    System.out.println("FAILED");
                    System.out.println("Employee ID : " + empId);
                    System.out.println("Status Code : " +
                            response.statusCode());
                    System.out.println("Error       : " +
                            response.body());
                }

            } catch (Exception e) {

                System.out.println("ERROR: " + empId);
                System.out.println(e.getMessage());
            }

            System.out.println("------------------------------------------");
        }
    }
}
