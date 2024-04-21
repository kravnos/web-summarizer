package com.websummarizer.Web.Summarizer.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
    private List<HistoryContent> historyContents;

    @Column(name = "linkURL", length = 1000)
    private String linkURL;

    @Column(name = "short_link", unique = true, nullable = false)
    private String shortLink;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;
}