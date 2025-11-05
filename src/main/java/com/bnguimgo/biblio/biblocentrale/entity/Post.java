package com.bnguimgo.biblio.biblocentrale.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Post")
@Table(name = "post")
public class Post {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String title;
 
/*    @OneToMany(
        mappedBy = "post",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )*/
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "POST_ID")
    private Set<PostComment> comments;
 
    //Constructors, getters and setters removed for brevity
 
    public void addComment(PostComment comment) {
        comments.add(comment);
        comment.setPost(this);
    }
 
    public void removeComment(PostComment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
}