package com.mysite.travelo.hyo.place;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import com.mysite.travelo.yeon.user.SiteUser;

@Getter
@Setter
@Entity
public class PlaceBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int placeBookmarkSeq;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private SiteUser user;

    private String contentId;
//    @ManyToOne
//    @JoinColumn(name = "placeSeq")
//    private Place place;
}
