package com.lab6.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Employee entity")
public class Employee {
    @Schema(description = "Employee ID", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @Schema(description = "Username", required = true, example = "john_doe")
    private String username;
    
    @Schema(description = "Email address", required = true, example = "john@example.com")
    private String email;
    
    @Schema(description = "First name", example = "John")
    private String firstName;
    
    @Schema(description = "Last name", example = "Doe")
    private String lastName;
    
    @Schema(description = "Creation timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @Schema(description = "Last update timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    public Employee(Long id, String username, String email, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}

