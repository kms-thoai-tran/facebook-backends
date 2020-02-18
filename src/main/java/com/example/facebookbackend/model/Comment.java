package com.example.facebookbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    UUID id;

    @NotBlank(message = "text is required")
    String text;

    @Temporal(TemporalType.TIMESTAMP)
    Date time;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "like_id", referencedColumnName = "Id")
    List<FacebookLike> likes;
//    Set<Comment> comments;
}
