package com.mysite.travelo.hyo.customcourse;

import com.mysite.travelo.gil.course.Course;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomCourseRequest {

    private Course course;

    private String title;

    private String description;

//    private int authorSeq;

    private String privateYn;

    // 데이터 전송량을 줄이기 위해서 순차번호만 전송.
    private List<String> contentIdList;
    
    private String areaCode;

}