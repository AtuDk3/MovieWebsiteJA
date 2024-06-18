package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Entity
@Table(name = "tab_manager_storage_view")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerStorageView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "last_delete")
    private LocalDate lastDelete;
}