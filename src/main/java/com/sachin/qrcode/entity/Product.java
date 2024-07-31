package com.sachin.qrcode.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString(exclude = {"image"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private String description;

    @Lob
    @Column(length = 3686400)
    private byte[] image;

    @Lob
    @Column(length = 3686400)
    private byte[] qrCode;


}
