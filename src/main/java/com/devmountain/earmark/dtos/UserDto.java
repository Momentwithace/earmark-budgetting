package com.devmountain.earmark.dtos;

import com.devmountain.earmark.entities.AppUser;
import com.devmountain.earmark.entities.BudgetItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String email;

    private List<BudgetItem> budgetItems;

    public UserDto(AppUser appUser) {
        if(appUser.getId() != null){
            this.id = appUser.getId();
        }
        if(appUser.getUsername() != null){
            this.username = appUser.getUsername();
        }
        if(appUser.getPassword() != null){
            this.password = appUser.getPassword();
        }
    }
}
