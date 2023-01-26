package com.devmountain.earmark.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private String date;

    public Log(String action){
        this.action = action;
        date = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
    }
}
