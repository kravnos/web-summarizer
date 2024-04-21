package com.websummarizer.Web.Summarizer.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "history_content")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HCID")
    private Long hcid;

    @ManyToOne
    @JoinColumn(name = "HID")
    private History hid;

    @Column(name = "content", length = 100000)
    private String content;
}
