package com.wslint.wisdomreport.service.user;

import com.wslint.wisdomreport.domain.dto.user.User;
import java.util.Map;

/**
 * 用户相关service
 *
 * @author yuxr
 * @date 20118/09/03
 */
public interface IUserService {

  /**
   * 新增用户
   *
   * @param user 用户信息
   * @return 处理结果
   */
  Map<String, Object> addUser(User user);

  /**
   * 删除用户
   *
   * @param user 用户信息
   * @return 处理结果
   */
  Map<String, Object> deleteUserById(User user);

  /**
   * 根据用户id和密码修改用户信息
   *
   * @param user 输入用户信息
   * @return 处理结果
   */
  Map<String, Object> updateUser(User user);

  /**
   * 根据用户id和密码修改用户信息
   *
   * @param user 输入用户信息
   * @return 处理结果
   */
  Map<String, Object> passwordUpdate(User user);

  /**
   * 重置用户密码
   *
   * @param userId 被重置用户id
   * @return 处理结果
   */
  Map<String, Object> passwordReset(Long userId);

  /**
   * 查询所有用户
   *
   * @return 查询数据
   */
  Map<String, Object> getAllUsers();

  /**
   * 根据用户id查询用户信息
   *
   * @param id id
   * @return 用户信息
   */
  User getUserById(Long id);

  /**
   * 根据用户名查询用户信息
   *
   * @param userCode 用户名
   * @return 返回用户对象
   */
  User getUserByCode(String userCode);
}
