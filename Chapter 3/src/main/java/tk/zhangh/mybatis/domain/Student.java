package tk.zhangh.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by ZhangHao on 2016/10/23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

    private static final long serialVersionUID = 1723118354285350978L;

    private Integer studId;
    private String name;
    private String email;
    private PhoneNumber phone;
    private Address address;
}
