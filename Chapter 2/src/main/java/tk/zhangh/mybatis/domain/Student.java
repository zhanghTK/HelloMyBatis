package tk.zhangh.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Student model
 * <p>
 * Created by ZhangHao on 2016/10/20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer studId;
    private String name;
    private String email;
    private PhoneNumber phone;
}
