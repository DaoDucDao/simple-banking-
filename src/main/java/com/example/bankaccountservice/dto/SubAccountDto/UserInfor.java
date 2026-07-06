package com.example.bankaccountservice.dto.SubAccountDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfor {
    private Long id;
    private String email;
    private String username;

    public UserInfor(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }
}
