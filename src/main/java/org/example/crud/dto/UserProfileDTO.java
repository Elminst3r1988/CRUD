package org.example.crud.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class UserProfileDTO {
    private Long id;
    @NotBlank(message = "Поле должно быть заполнено")
    @Size(min = 3, max = 50, message = "Имя должно содержать от 3 до 50 симовлов")
    private String username;
    @NotNull(message = "Поле должно быть заполнено")
    private String lastname;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 18, message = "Возраст должен быть не меньше 18")
    private Short age;
    @NotBlank(message = "Поле должно быть заполнено")
    @Email(message = "Почта должны быть типа: user@example.com")
    private String email;
    @Size(min = 3, message = "Пароль должен быть минимум из 3-х символов")
    private String password;
    private Set<String> roles;


}
