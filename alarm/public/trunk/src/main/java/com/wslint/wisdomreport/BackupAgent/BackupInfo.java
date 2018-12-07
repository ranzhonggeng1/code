package com.wslint.wisdomreport.BackupAgent;

import java.util.Map;

/**
 * 备份实体类
 *
 * @author yangmindong
 *
 * 2018年12月6日
 */
public class BackupInfo {
	/**
	 *  * 备份id
   */
  private String id;
  /**
   * 备份状态
   */
  private String state;

  /**
   * 备份源地址
   */
  private String srcPath;
  /**
   * 备份目标地址
   */
  private String dstPath;
  /**
   * 备份操作类型
   */
  private String backType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public String getDstPath() {
		return dstPath;
	}
	public void setDstPath(String dstPath) {
		this.dstPath = dstPath;
	}
	public String getBackType() {
		return backType;
	}
	public void setBackType(String backType) {
		this.backType = backType;
	}
	@Override
	public String toString() {
		return "BackupInfo [id=" + id + ", state=" + state + ", srcPath=" + srcPath + ", dstPath=" + dstPath + ", backType="
				+ backType + "]";
	}
  
  
}
