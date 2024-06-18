package demo.service;

/**
 * @Author
 * @Date 2024/6/6 21:35
 * @Description:
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import demo.mapper.userMapper;
import demo.pojo.user;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private userMapper userMapper;


    public List<user> getUsersPage(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return userMapper.selectUsers(offset, pageSize);
    }

    public int getTotalUserCount() {
        return userMapper.countUsers();
    }
}