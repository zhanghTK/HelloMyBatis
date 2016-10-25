package tk.zhangh.mybatis.mappers;

import tk.zhangh.mybatis.domain.Address;

/**
 * Created by ZhangHao on 2016/10/23.
 */
public interface AddressMapper {
    Address findAddressById(Integer id);
}
