package tk.zhangh.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 电话号码 model
 * Student的字段
 * <p>
 * Created by ZhangHao on 2016/10/20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber {
    private String countryCode;
    private String stateCode;
    private String number;

    public PhoneNumber(String string) {
        if (string != null) {
            String[] parts = string.split("-");
            if (parts.length > 0) {
                this.countryCode = parts[0];
            }
            if (parts.length > 1) {
                this.stateCode = parts[1];
            }
            if (parts.length > 2) {
                this.number = parts[2];
            }
        }
    }

    public String getAsString() {
        return countryCode + "-" + stateCode + "-" + number;
    }
}
