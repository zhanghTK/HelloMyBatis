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
public class Address implements Serializable {

    private static final long serialVersionUID = 58239322894015821L;

    private Integer addrId;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}
