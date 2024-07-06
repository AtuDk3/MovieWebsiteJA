package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Entity
@Table(name = "tab_manager_storage_rate")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerStorageRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "last_delete")
    private LocalDate lastDelete;
}
