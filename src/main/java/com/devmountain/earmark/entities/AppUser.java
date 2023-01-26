package com.devmountain.earmark.entities;

import com.devmountain.earmark.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;
    @Column
    private String password;


    @OneToMany(fetch = FetchType.EAGER)
    private List<BudgetItem> budgetItems;


    @OneToMany(fetch = FetchType.LAZY)
    private Set<Income> incomeHistory;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Log> logs;

    public AppUser(UserDto userDto){
        if(userDto.getUsername() != null){
            this.username = userDto.getUsername();
        }
        if(userDto.getPassword() != null){
            this.password = userDto.getPassword();
        }
        if(userDto.getEmail() != null){
            this.email = userDto.getEmail();
        }
    }
}
