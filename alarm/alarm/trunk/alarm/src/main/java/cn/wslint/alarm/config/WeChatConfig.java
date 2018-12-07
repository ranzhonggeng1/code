package cn.wslint.alarm.config;

/**
 * 微信相关配置类
 *
 * @author ranzhonggeng
 */
@PropertiesConfig(prefix = "alarm.wechat")
public class WeChatConfig {

  private String corpid;
  private String corpsecret;
  private int agentid;
  private String partyid;

  public String getCorpid() {
    return corpid;
  }

  public void setCorpid(String corpid) {
    this.corpid = corpid;
  }

  public String getCorpsecret() {
    return corpsecret;
  }

  public void setCorpsecret(String corpsecret) {
    this.corpsecret = corpsecret;
  }

  public int getAgentid() {
    return agentid;
  }

  public void setAgentid(int agentid) {
    this.agentid = agentid;
  }

  public String getPartyid() {
    return partyid;
  }

  public void setPartyid(String partyid) {
    this.partyid = partyid;
  }
}
