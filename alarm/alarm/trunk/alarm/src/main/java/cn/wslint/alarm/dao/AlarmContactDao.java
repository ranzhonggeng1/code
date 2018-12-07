package cn.wslint.alarm.dao;

import cn.wslint.alarm.resources.Contact;
import cn.wslint.alarm.resources.ContactGroup;
import cn.wslint.alarm.resources.GroupRelContact;
import java.sql.SQLException;
import java.util.List;

/**
 * 联系人基础操作
 *
 * @author ranzhonggeng
 */
public interface AlarmContactDao {

  /**
   * 获取所有联系人
   *
   * @return Contact联系人列表
   */
  List<Contact> getContactAll();

  /**
   * 添加联系人
   *
   * @return boolean
   */
  boolean addContact(Contact contactCase);

  /**
   * 删除联系人
   *
   * @return boolean
   */
  boolean deleteContact(String id);

  /**
   * 修改联系人信息
   *
   * @return boolean
   */
  boolean updateContact(Contact contactCase);

  /**
   * 获取企业群组列表
   *
   * @return ContactGroup List
   */
  List<ContactGroup> getGroupAll();

  /**
   * 获取联系人-企业群组关联
   *
   * @return group_rel_contact List
   */
  List<GroupRelContact> getGroupRelContact();

  /**
   * 同时添加了联系人和组群，需要将他们关联
   *
   * @param contact 联系人
   * @param contactGroup 联系人组
   * @return 处理结果
   */
  boolean relateContactAndGroup(Contact contact, ContactGroup contactGroup);

  /**
   * 更新联系人-企业群组关联
   *
   * @return boolean
   */
  boolean updateRelation(Contact contact, ContactGroup contactGroup) throws SQLException;

  /**
   * 删除联系人-企业群组关联
   *
   * @return boolean
   */
  boolean deleteRelation(String contactId);

  /**
   * 根据微信Id模糊查询
   *
   * @return Contact List
   */
  List<Contact> getContactSearch(String contact_name);
}
