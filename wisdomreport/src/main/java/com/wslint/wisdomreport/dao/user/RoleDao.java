package com.wslint.wisdomreport.dao.user;

import com.wslint.wisdomreport.domain.dto.permission.Permission;
import com.wslint.wisdomreport.domain.dto.role.Role;
import com.wslint.wisdomreport.domain.dto.role.RolePermission;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.domain.dto.user.UserRole;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * 角色管理Dao
 *
 * @author yuxr
 * @since 2018/8/21 16:09
 */
public interface RoleDao {

  /**
   * 新增角色信息
   *
   * @param role 角色
   * @return 是否成功添加
   */
  @Insert("insert into role(id, role_name) values(#{id}, #{roleName})")
  boolean addRole(Role role);

  /**
   * 新增角色权限信息
   *
   * @param rolePermission 角色权限信息
   * @return 是否成功添加
   */
  @Insert(
      "<script> insert into role_permission(role_id,permission_id) values "
          + "<foreach collection='permissionIdList' item='item' index='index' separator=','>"
          + " (#{roleId}, #{item}) </foreach> </script>")
  boolean addRolePermissions(RolePermission rolePermission);

  /**
   * 新增用户角色信息
   *
   * @param userRole 用户角色信息
   * @return 是否成功添加
   */
  @Insert(
      "<script> insert into user_role(user_id,role_id) values "
          + "<foreach collection='userIdList' item='item' index='index' separator=','>"
          + " (#{item}, #{roleId}) </foreach> </script>")
  boolean addUserRoles(UserRole userRole);

  /**
   * 根据角色id删除角色信息
   *
   * @param id 角色id信息
   * @return 是否成功删除
   */
  @Delete("delete from role where id = #{id}")
  boolean deleteRoleById(Long id);

  /**
   * 根据角色id删除用户角色关联数据
   *
   * @param id 角色id
   */
  @Delete("delete from user_role where role_id = #{id}")
  void deleteUserRolesByRoleId(Long id);

  /**
   * 根据角色id删除角色权限关联数据
   *
   * @param id 角色id
   */
  @Delete("delete from role_permission where role_id = #{id}")
  void deleteRolePermissionsByRoleId(Long id);

  /**
   * 删除角色权限信息
   *
   * @param rolePermission 角色权限信息
   * @return 是否成功删除
   */
  @Delete(
      "<script>delete from role_permission where role_id = #{roleId} and permission_id in "
          + "<foreach collection='permissionIdList' item='item' index='index' open='(' close=')' separator=','>"
          + " #{item} </foreach> </script>")
  boolean deleteRolePermissions(RolePermission rolePermission);

  /**
   * 删除用户角色信息
   *
   * @param userRole 用户角色信息
   * @return 是否成功删除
   */
  @Delete(
      "<script>delete from user_role where role_id = #{roleId} and user_id in "
          + "<foreach collection='userIdList' item='item' index='index' open='(' close=')' separator=','>"
          + " #{item} </foreach> </script>")
  boolean deleteUserRoles(UserRole userRole);

  /**
   * 查询所有角色信息
   *
   * @return 所有角色信息
   */
  @Select("select * from role where id != 0 order by id")
  List<Role> getAllRoles();

  /**
   * 根据角色id查询角色信息
   *
   * @param id 角色id
   * @return 角色信息
   */
  @Select("select * from role where id = #{id}")
  Role getRoleById(Long id);

  /**
   * 根据角色名称查询角色信息
   *
   * @param roleName 角色名称
   * @return 角色信息
   */
  @Select("select * from role where role_name = #{roleName}")
  Role getRoleByRoleName(String roleName);

  /**
   * 判断角色是否存在
   *
   * @param role 角色信息
   * @return 角色是否存在
   */
  @Select("select if(count(1) = 0 ,0, 1) from role where role_name = #{roleName} ")
  boolean isRoleExisted(Role role);

  /**
   * 根据角色id查询拥有角色的用户信息
   *
   * @param id 角色id
   * @return 用户列表
   */
  @Select("select u.* from user u,user_role ur where u.id = ur.user_id and ur.role_id = #{id}")
  List<User> getUsersByRoleId(Long id);

  /**
   * 根据角色Id查询权限信息
   *
   * @param id 权限ID
   * @return 权限信息
   */
  @Select(
      "select p.* from permission p, role_permission rp where p.id = rp.permission_id and rp.role_id = #{id}")
  List<Permission> getPermissionsByRoleId(long id);
}
