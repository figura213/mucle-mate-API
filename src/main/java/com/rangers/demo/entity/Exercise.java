package com.rangers.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise {
    @Id
    @GeneratedValue
    private UUID id;

    private String imageSrc;
    private String title;
    @Column(length = 1000)
    private String description;

    private String type;
    private String difficulty;

//    @ElementCollection
//    @CollectionTable(name = "exercise_targets", joinColumns = @JoinColumn(name = "exercise_id"))
//    @Column(name = "target")
//    private List<String> target;

    private String dateAdded;

//    @ElementCollection
//    @CollectionTable(name = "exercise_equipment", joinColumns = @JoinColumn(name = "exercise_id"))
//    @Column(name = "equipment")
//    private List<String> equipment;

//    @ElementCollection
//    @CollectionTable(name = "exercise_media_links", joinColumns = @JoinColumn(name = "exercise_id"))
//    @Column(name = "media_link")
//    private List<String> mediaLinks;
//
//    @ElementCollection
//    @CollectionTable(name = "exercise_tags", joinColumns = @JoinColumn(name = "exercise_id"))
//    @Column(name = "tag")
//    private List<String> tags;
}