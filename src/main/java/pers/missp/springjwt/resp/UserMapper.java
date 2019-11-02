package pers.missp.springjwt.resp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import pers.missp.springjwt.domin.User;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User selectByUsername(@Param("username") String username);
}
