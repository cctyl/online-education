package com.atguigu.eduservice.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneSubject {

    private String id;
    private String title;

    private List<TwoSubject> children = new ArrayList<>();

    public OneSubject (String id,String title){

        this.id=id;
        this.title=title;
    }
}
