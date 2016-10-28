package tk.zhangh.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhangHao on 2016/10/28.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {

    private static final long serialVersionUID = -78330188381681436L;

    private Integer courseId;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Tutor tutor;
    private List<Student> students;
}
