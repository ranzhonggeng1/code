using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Configuration;
using System.Web.Script.Serialization;
using DocumentConfig.model;
using Newtonsoft.Json;
using System.Net;
using System.IO;

namespace DocumentConfig.ServerHelper
{
    public class CrateHelper
    {
        /// <summary>
        /// 创建大类
        /// </summary>
        /// <param name="mediniceID">药品ID</param>
        /// <param name="firstClassName">大类名字</param>
        /// <returns></returns>
        public static string CreateFirstClass(string mediniceID, string firstClassName)
        {
            string res = string.Empty;
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string createfirsturl = config.AppSettings.Settings["CreateFirstClass"].Value;
            string url = "http://" + StaticString.serverIp + createfirsturl;
            WebRequest httpRquest = System.Net.HttpWebRequest.Create(url);
            httpRquest.Method = "POST";
            httpRquest.ContentType = "application/json";
            using (var streamWriter = new StreamWriter(httpRquest.GetRequestStream()))
            {
                string json = new JavaScriptSerializer().Serialize(new
                {
                    medicineId = mediniceID,
                    name = firstClassName
                });

                streamWriter.Write(json);
            }
            var response = (HttpWebResponse)httpRquest.GetResponse();
            using (var streamReader = new StreamReader(response.GetResponseStream()))
            {
                string result = streamReader.ReadToEnd();
                MedicineInfo medinice = new MedicineInfo();
                ReciveDataInfo recive = JsonConvert.DeserializeObject<ReciveDataInfo>(result);
                if (recive.Code == 200)
                {
                    res = recive.Data["id"].ToString();
                }
            }
            return res;
        }
        /// <summary>
        /// 创建小类
        /// </summary>
        /// <param name="medicineID">药品ID</param>
        /// <param name="firstID">大类ID</param>
        /// <param name="secordName">小类名字</param>
        /// <returns></returns>
        public static string CreateSecordClass(string medicineID,string firstID,string secordName)
        {
            string res = string.Empty;
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string createfirsturl = config.AppSettings.Settings["CreateSecordClass"].Value;
            string url = "http://" + StaticString.serverIp + createfirsturl;
            WebRequest httpRquest = System.Net.HttpWebRequest.Create(url);
            httpRquest.Method = "POST";
            httpRquest.ContentType = "application/json";
            using (var streamWriter = new StreamWriter(httpRquest.GetRequestStream()))
            {
                string json = new JavaScriptSerializer().Serialize(new
                {
                    medicineId = medicineID,
                    firstClassId=firstID,
                    name = secordName
                });

                streamWriter.Write(json);
            }
            var response = (HttpWebResponse)httpRquest.GetResponse();
            using (var streamReader = new StreamReader(response.GetResponseStream()))
            {
                string result = streamReader.ReadToEnd();
                MedicineInfo medinice = new MedicineInfo();
                ReciveDataInfo recive = JsonConvert.DeserializeObject<ReciveDataInfo>(result);
                if (recive.Code == 200)
                {
                    res = recive.Data["id"].ToString();
                }
            }
            return res;
        }
        /// <summary>
        /// 修改小类名字
        /// </summary>
        /// <param name="medicineID"></param>
        /// <param name="firstID"></param>
        /// <param name="secordName"></param>
        /// <returns></returns>
        public static string ChangeSecordName(string medicineID, string firstID, string secordid,string secordName)
        {
            string res = string.Empty;
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string createfirsturl = config.AppSettings.Settings["ChangeSecordName"].Value;
            string url = "http://" + StaticString.serverIp + createfirsturl;
            WebRequest httpRquest = System.Net.HttpWebRequest.Create(url);
            httpRquest.Method = "POST";
            httpRquest.ContentType = "application/json";
            using (var streamWriter = new StreamWriter(httpRquest.GetRequestStream()))
            {
                string json = new JavaScriptSerializer().Serialize(new
                {
                    medicineId = medicineID,
                    firstClassId = firstID,
                    id=secordid,
                    name = secordName
                });

                streamWriter.Write(json);
            }
            var response = (HttpWebResponse)httpRquest.GetResponse();
            using (var streamReader = new StreamReader(response.GetResponseStream()))
            {
                string result = streamReader.ReadToEnd();
                MedicineInfo medinice = new MedicineInfo();
                ReciveDataInfo recive = JsonConvert.DeserializeObject<ReciveDataInfo>(result);
                if (recive.Code == 200)
                {
                    res = recive.Msg;
                }
            }
            return res;
        }
    }
}
