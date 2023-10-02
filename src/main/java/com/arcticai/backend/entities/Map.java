package com.arcticai.backend.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "map")
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne  // Many maps can belong to one user
    @JoinColumn(name = "user_id", nullable = false) // The column in this table that is a foreign key to user.
    private User user;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private Integer saveSlot;

    @Column(nullable = false)
    private Integer zoom;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Marker> markers;

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Layer> layers;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

}
