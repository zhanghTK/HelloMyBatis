package tk.zhangh.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZhangHao on 2016/10/23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tutor implements Serializable {
    private static final long serialVersionUID = 1283761325432124712L;

    private Integer tutorId;
    private String name;
    private String email;
    private Address address;
    private List<Course> courses;
}
