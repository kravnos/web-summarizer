package com.websummarizer.Web.Summarizer.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Domain object for Affiliate (represents a row in table "history")
 */
@Entity
@Table(name = "Custom_URLs")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Custom_URLs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UID")
    private User user;


    @Setter
    @Column(name = "Destination_Link", length = 100000)
    private String destinationLink;


    @Setter
    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

}

