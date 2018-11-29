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
    public class UserHelper
    {
        /// <summary>
        /// 用户登陆
        /// </summary>
        /// <param name="username"></param>
        /// <param name="password"></param>
        /// <returns></returns>
        public static string Login(string username,string password)
        {
            string res = string.Empty;
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string createfirsturl = config.AppSettings.Settings["Login"].Value;
            string url = "http://" + StaticString.serverIp + createfirsturl;
            WebRequest httpRquest = System.Net.HttpWebRequest.Create(url);
            httpRquest.Method = "POST";
            httpRquest.ContentType = "application/json";
            using (var streamWriter = new StreamWriter(httpRquest.GetRequestStream()))
            {
                string json = new JavaScriptSerializer().Serialize(new
                {
                    userCode = username,
                    password = password
                });

                streamWriter.Write(json);
            }
            var response = (HttpWebResponse)httpRquest.GetResponse();
            using (var streamReader = new StreamReader(response.GetResponseStream()))
            {
                string result = streamReader.ReadToEnd();
                res = result;
            }
            return res;
        }
    }
}
