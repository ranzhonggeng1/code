using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Net;
using System.Configuration;
using System.Web.Script.Serialization;
using DocumentConfig.model;
using Newtonsoft.Json;

namespace DocumentConfig.ServerHelper
{
    public class SerarchHelper
    {
        /// <summary>
        /// 获取药品的所有大类
        /// </summary>
        /// <param name="medicineId"></param>
        /// <returns></returns>
        public static List<FirstInfo> GetAllFirstClassByMedicine(string medicineId)
        {
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string getfirstclass = config.AppSettings.Settings["GetFirstClassUrl"].Value;
            string url = "http://" + StaticString.serverIp + getfirstclass;
            string argument = "?medicineId=" + medicineId;
            WebRequest httpRquest = System.Net.HttpWebRequest.Create(url+argument);
            httpRquest.Method = "GET";
            httpRquest.ContentType = "application/json";
            List<FirstInfo> res = new List<FirstInfo>();
         
            var response = (HttpWebResponse)httpRquest.GetResponse();
            using (var streamReader = new StreamReader(response.GetResponseStream()))
            {
                string result = streamReader.ReadToEnd();
                MedicineInfo medinice = new MedicineInfo();
                AllMedicineInfo recive = JsonConvert.DeserializeObject<AllMedicineInfo>(result);
                if (recive.Code == 200)
                {
                    for (int i = 0; i < recive.Data.Count; i++)
                    {
                        var item = recive.Data[i];
                        FirstInfo first = new FirstInfo();
                        first.ID = item["id"];
                        first.Name = item["name"];
                        res.Add(first);
                    }       
                }
            }
            return res;
        }
        /// <summary>
        /// 获取所有药品
        /// </summary>
        /// <returns></returns>
        public static List<MedicineInfo>  GetAllMedicine()
        {
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string getmeidcine= config.AppSettings.Settings["GetAllMedicineUrl"].Value;
            string url = "http://" + StaticString.serverIp + getmeidcine;
            WebRequest httpRquest = System.Net.HttpWebRequest.Create(url);
            httpRquest.Method = "GET";
            httpRquest.ContentType = "application/json";
            List<MedicineInfo> medicines = new List<MedicineInfo>();
            var response = (HttpWebResponse)httpRquest.GetResponse();
            using (var streamReader = new StreamReader(response.GetResponseStream()))
            {
                string result = streamReader.ReadToEnd();
                AllMedicineInfo recive = JsonConvert.DeserializeObject<AllMedicineInfo>(result);
                if (recive.Code == 200)
                {
                    for (int i = 0; i < recive.Data.Count; i++)
                    {
                        var item = recive.Data[i];
                        MedicineInfo medicine = new MedicineInfo();
                        medicine.MedicineID = item["id"];
                        medicine.MedicineName = item["name"];
                        medicines.Add(medicine);
                    }             
                }
            }
            return medicines;
        }
        /// <summary>
        /// 获取大类下面的所有小类
        /// </summary>
        /// <param name="medicineId"></param>
        /// <param name="firstid"></param>
        /// <returns></returns>
        public static List<SecordInfo> GetAllSecord(string medicineId,string firstid)
        {
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string getfirstclass = config.AppSettings.Settings["GetAllSecordClass"].Value;
            string url = "http://" + StaticString.serverIp + getfirstclass;
            string argument = "?medicineId=" + medicineId + "&firstClassId=" + firstid;
            WebRequest httpRquest = System.Net.HttpWebRequest.Create(url + argument);
            httpRquest.Method = "GET";
            httpRquest.ContentType = "application/json";
            List<SecordInfo> res = new List<SecordInfo>();

            var response = (HttpWebResponse)httpRquest.GetResponse();
            using (var streamReader = new StreamReader(response.GetResponseStream()))
            {
                string result = streamReader.ReadToEnd();
                AllMedicineInfo recive = JsonConvert.DeserializeObject<AllMedicineInfo>(result);
                if (recive.Code == 200)
                {
                    for (int i = 0; i < recive.Data.Count; i++)
                    {
                        var item = recive.Data[i];
                        SecordInfo first = new SecordInfo();
                        first.ID = item["id"];
                        first.Name = item["name"];
                        res.Add(first);
                    }
                }
            }
            return res;
        }
        /// <summary>
        /// 获取药品的版本信息
        /// </summary>
        /// <param name="medicineId"></param>
        /// <returns></returns>
        public static List<MedicineVersion> GetVersion(string medicineId)
        {
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string getfirstclass = config.AppSettings.Settings["MedicineVersion"].Value;
            string url = "http://" + StaticString.serverIp + getfirstclass;
            string argument = "?medicineId=" + medicineId;
            WebRequest httpRquest = System.Net.HttpWebRequest.Create(url + argument);
            httpRquest.Method = "GET";
            httpRquest.ContentType = "application/json";
            List<MedicineVersion> res = new List<MedicineVersion>();

            var response = (HttpWebResponse)httpRquest.GetResponse();
            using (var streamReader = new StreamReader(response.GetResponseStream()))
            {
                string result = streamReader.ReadToEnd();
                try
                {
                    MedicineVersionInfo recive = JsonConvert.DeserializeObject<MedicineVersionInfo>(result);
                    if (recive.Code == 200)
                    {
                        for (int i = 0; i < recive.Data.Count; i++)
                        {
                            MedicineVersion ver = new MedicineVersion();
                            ver.ID = recive.Data[i]["id"];
                            ver.Message = recive.Data[i]["description"];
                            res.Add(ver);
                        }
                    }
                }
                catch(Exception ex)
                {
                }
            }
            return res;
        }
        /// <summary>
        /// 获取当前编辑药品的ID最大值
        /// </summary>
        /// <param name="medicineId"></param>
        /// <returns></returns>
        public static int GetMaxID(string medicineId)
        {
            int res = 0;
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string getfirstclass = config.AppSettings.Settings["GetMaxID"].Value;
            string url = "http://" + StaticString.serverIp + getfirstclass;
            string argument = "?medicineId=" + medicineId;
            WebRequest httpRquest = System.Net.HttpWebRequest.Create(url + argument);
            httpRquest.Method = "GET";
            httpRquest.ContentType = "application/json";
            var response = (HttpWebResponse)httpRquest.GetResponse();
            using (var streamReader = new StreamReader(response.GetResponseStream()))
            {
                string result = streamReader.ReadToEnd();
                try
                {
                    ReciveDataInfo recive = JsonConvert.DeserializeObject<ReciveDataInfo>(result);
                    if (recive.Code == 200)
                    {
                        int.TryParse(recive.Data["idmax"].ToString(),out res);
                    }
                }
                catch (Exception ex)
                {
                }
            }
            return res;
        }
        /// <summary>
        /// 获取大类ID
        /// </summary>
        /// <param name="medicineID"></param>
        /// <param name="firstName"></param>
        /// <returns></returns>
        public static string GetFirstID(string medicineID,string firstName)
        {
            var firsts = GetAllFirstClassByMedicine(medicineID);
            foreach (var item in firsts)
                if (item.Name == firstName)
                    return item.ID;
            return string.Empty;
        }
        /// <summary>
        /// 获取小类ID
        /// </summary>
        /// <param name="medicineID"></param>
        /// <param name="firstID"></param>
        /// <param name="secordName"></param>
        /// <returns></returns>
        public static string GetSecordID(string medicineID,string firstID,string secordName)
        {
            var secords = GetAllSecord(medicineID,firstID);
            foreach (var item in secords)
                if (item.Name == secordName)
                    return item.ID;
            return string.Empty;
        }
    }
}
