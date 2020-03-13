package priv.csh.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.csh.pojo.User;

@Repository
@Mapper
public interface UserMapper {

    public User queryUserByName(String name);
}
