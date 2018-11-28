package com.wslint.wisdomreport.dao.user;

import com.wslint.wisdomreport.domain.dto.role.Role;
import com.wslint.wisdomreport.domain.dto.user.User;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 用户管理持久层
 *
 * @author yuxr
 */
public interface UserDao {

  /**
   * 新增用户
   *
   * @param user 用户信息
   * @return 是否保存成功
   */
  @Insert(
      "insert into user(id, user_code, user_name, password, sign, complete) values(#{id}, #{userCode}, #{userName}, #{password}, #{sign}, #{complete})")
  boolean addUser(User user);

  /**
   * 根据id删除用户信息
   *
   * @param id 用户id
   * @return 是否成功删除
   */
  @Delete("delete from user where id = #{id}")
  boolean deleteUserById(Long id);

  /**
   * 根据用户id删除用户角色信息
   *
   * @param id 用户id
   */
  @Delete("delete from user_role where user_id = #{id}")
  void deleteUserRolesByUserId(Long id);

  /**
   * 更新用户信息
   *
   * @param inputUser 输入信息，必须包含要更新的字段和用户密码
   * @return 是否成功更新
   */
  @Update("update user set sign = #{sign}, complete = #{complete} where id = #{id}")
  Boolean updateUser(User inputUser);

  /**
   * 修改密码
   *
   * @param user 用户信息
   * @return 修改结果
   */
  @Update("update user set password = #{password}, complete= #{complete} where id = #{id} ")
  boolean passwordUpdate(User user);

  /**
   * 查询所有用户数据
   *
   * @return 所有用户数据
   */
  @Select("select * from user where id != 0 order by id")
  List<User> getAllUsers();

  /**
   * 根据用户id获取用户信息
   *
   * @param id 用户id
   * @return 用户信息
   */
  @Select("select * from user where id = #{id}")
  User getUserById(Long id);

  /**
   * 根据用户账户查询用户信息
   *
   * @param userCode 用户账户
   * @return 返回用户对象
   */
  @Select("select * from user where user_code = #{userCode}")
  User gerUserByCode(String userCode);

  /**
   * 用户是否存在
   *
   * @param user 用户账户
   * @return 用户是否存在
   */
  @Select(
      "select if(count(1) = 0 ,0, 1) from user where user_code = #{userCode} or user_name = #{userName}")
  Boolean isUserExisted(User user);

  /**
   * 根据用户ID查询角色信息
   *
   * @param id 用户ID
   * @return 角色信息
   */
  @Select("select r.* from role r, user_role ur where r.id = ur.role_id and ur.user_id = #{id}")
  List<Role> getRolesByUserId(long id);
}
