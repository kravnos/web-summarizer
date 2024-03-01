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

    //ID of the custom URL created
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CID")
    private Long id;

    //User ID of the person who created it
    @ManyToOne
    @JoinColumn(name = "UID")
    private User user;

    //The custom link created by the user
    @Setter
    @Column(name = "Custom_Link", length = 100000)
    private String customLink;

    //THe destination the custom link goes to
    @Setter
    @Column(name = "Destination_Link", length = 100000)
    private String destinationLink;

    //Time which the URL was uploaded
    @Setter
    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

}

