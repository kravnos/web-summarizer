package com.websummarizer.Web.Summarizer.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Domain object for Affiliate (represents a row in table "history")
 */
@Entity
@Table(name = "history")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UID")
    private User user;

    @Setter
    @Column(name = "history_content", length = 100000)
    private String historyContent;

    @Setter
    @Column(name = "short_link", unique = true, nullable = false)
    private String shortLink;

    @Setter
    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

}
