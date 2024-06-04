package com.apocaly.apocaly_path_private.user.model.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputUser {
    private String email, password, loginType;

    public InputUser(String username) {
        this.email = username;
    }
}
