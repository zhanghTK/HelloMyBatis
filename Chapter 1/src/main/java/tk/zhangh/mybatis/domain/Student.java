package tk.zhangh.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Student model
 * Created by ZhangHao on 2016/10/18.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer studId;
    private String name;
    private String email;
    private Date dob;
}
