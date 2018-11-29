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
using System.Diagnostics;

namespace DocumentConfig.ServerHelper
{
    public class FilesHelper
    {
        /// <summary>
        /// 上传小类文档模板
        /// </summary>
        /// <param name="medicineID">药品ID</param>
        /// <param name="firstID">大类ID</param>
        /// <param name="secordID">小类ID</param>
        /// <param name="fileName">文件绝对路径,html文件</param>
        /// <returns></returns>
        public static string UplondSecord(string medicineID, string firstID, string secordID, string fileName,string type="html")
        {
            string contentType = "multipart/form-data";
            //待请求参数数组
            FileStream Pic = new FileStream(fileName, FileMode.Open);
            byte[] PicByte = new byte[Pic.Length];
            Pic.Read(PicByte, 0, PicByte.Length);
            int lengthFile = PicByte.Length;
            Dictionary<string, string> dicPara = new Dictionary<string, string>();
            dicPara.Add("medicineId", medicineID);
            dicPara.Add("firstClassId", firstID);
            dicPara.Add("secondClassId", secordID);
            //构造请求地址
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string uplondurl = "";
            if(type=="html")
                uplondurl=config.AppSettings.Settings["Uplond"].Value;
            else
                uplondurl = config.AppSettings.Settings["UplondSecordWord"].Value;
            string url = "http://" + StaticString.serverIp + uplondurl;
            //设置HttpWebRequest基本信息
            HttpWebRequest request = (HttpWebRequest)HttpWebRequest.Create(url);
            //设置请求方式：get、post
            request.Method = "POST";
            //设置boundaryValue
            string boundaryValue = DateTime.Now.Ticks.ToString("x");
            string boundary = "--" + boundaryValue;
            request.ContentType = "\r\nmultipart/form-data; boundary=" + boundaryValue;
            //设置KeepAlive
            request.KeepAlive = true;
            //设置请求数据，拼接成字符串
            StringBuilder sbHtml = new StringBuilder();
            foreach (KeyValuePair<string, string> key in dicPara)
            {
                sbHtml.Append(boundary + "\r\nContent-Disposition: form-data; name=\"" + key.Key + "\"\r\n\r\n" + key.Value + "\r\n");
            }
            sbHtml.Append(boundary + "\r\nContent-Disposition: form-data; name=\"file\"; filename=\"");
            sbHtml.Append(Path.GetFileName(fileName));
            sbHtml.Append("\"\r\nContent-Type: " + contentType + "\r\n\r\n");
            string postHeader = sbHtml.ToString();
            //将请求数据字符串类型根据编码格式转换成字节流
            Encoding code = Encoding.UTF8;
            byte[] postHeaderBytes = code.GetBytes(postHeader);
            byte[] boundayBytes = Encoding.ASCII.GetBytes("\r\n" + boundary + "--\r\n");
            //设置长度
            long length = postHeaderBytes.Length + lengthFile + boundayBytes.Length;
            request.ContentLength = length;

            //请求远程HTTP
            Stream requestStream = request.GetRequestStream();
            Stream myStream = null;
            try
            {
                //发送数据请求服务器
                requestStream.Write(postHeaderBytes, 0, postHeaderBytes.Length);
                requestStream.Write(PicByte, 0, lengthFile);
                requestStream.Write(boundayBytes, 0, boundayBytes.Length);
                HttpWebResponse HttpWResp = (HttpWebResponse)request.GetResponse();
                myStream = HttpWResp.GetResponseStream();
            }
            catch (WebException e)
            {
                //LogResult(e.Message);
                return "";
            }
            finally
            {
                if (requestStream != null)
                {
                    requestStream.Close();
                }
            }

            //读取处理结果
            StreamReader reader = new StreamReader(myStream, code);
            StringBuilder responseData = new StringBuilder();

            String line;
            while ((line = reader.ReadLine()) != null)
            {
                responseData.Append(line);
            }
            myStream.Close();
            Pic.Close();
            string res = "";
            string result= responseData.ToString();
            ReciveDataInfo recive = JsonConvert.DeserializeObject<ReciveDataInfo>(result);
            if (recive.Code == 200)
            {
                res = "文件上传成功";
            }
            return res;
        }
        public static string UplondMedicineEffectWord(string medicineID, string fileName, string description, string idmax)
        {
            return UplondMedicieWord(medicineID,fileName,description,idmax,true);
        }
        /// <summary>
        /// 上传文档到工作区
        /// </summary>
        /// <param name="medicineID"></param>
        /// <param name="fileName"></param>
        /// <returns></returns>
        public static string UplondMedicieWord(string medicineID, string fileName, string description,string idmax,bool isEffect=false)
        {
            string contentType = "multipart/form-data";
            //待请求参数数组
            FileStream Pic = new FileStream(fileName, FileMode.Open);
            byte[] PicByte = new byte[Pic.Length];
            Pic.Read(PicByte, 0, PicByte.Length);
            int lengthFile = PicByte.Length;
            Dictionary<string, string> dicPara = new Dictionary<string, string>();
            dicPara.Add("medicineId", medicineID);
            dicPara.Add("description", description);
            dicPara.Add("idMax",idmax);
            //构造请求地址
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string uplondurl = "";
            if (!isEffect)
                uplondurl = config.AppSettings.Settings["UplondMedicineWord"].Value;
            else
                uplondurl = config.AppSettings.Settings["UplondMedicineEffectWord"].Value;
            string url = "http://" + StaticString.serverIp + uplondurl;
            //设置HttpWebRequest基本信息
            HttpWebRequest request = (HttpWebRequest)HttpWebRequest.Create(url);
            //设置请求方式：get、post
            request.Method = "POST";
            //设置boundaryValue
            string boundaryValue = DateTime.Now.Ticks.ToString("x");
            string boundary = "--" + boundaryValue;
            request.ContentType = "\r\nmultipart/form-data; boundary=" + boundaryValue;
            //设置KeepAlive
            request.KeepAlive = true;
            //设置请求数据，拼接成字符串
            StringBuilder sbHtml = new StringBuilder();
            foreach (KeyValuePair<string, string> key in dicPara)
            {
                sbHtml.Append(boundary + "\r\nContent-Disposition: form-data; name=\"" + key.Key + "\"\r\n\r\n" + key.Value + "\r\n");
            }
            sbHtml.Append(boundary + "\r\nContent-Disposition: form-data; name=\"file\"; filename=\"");
            sbHtml.Append(Path.GetFileName(fileName));
            sbHtml.Append("\"\r\nContent-Type: " + contentType + "\r\n\r\n");
            string postHeader = sbHtml.ToString();
            //将请求数据字符串类型根据编码格式转换成字节流
            Encoding code = Encoding.UTF8;
            byte[] postHeaderBytes = code.GetBytes(postHeader);
            byte[] boundayBytes = Encoding.ASCII.GetBytes("\r\n" + boundary + "--\r\n");
            //设置长度
            long length = postHeaderBytes.Length + lengthFile + boundayBytes.Length;
            request.ContentLength = length;

            //请求远程HTTP
            Stream requestStream = request.GetRequestStream();
            Stream myStream = null;
            try
            {
                //发送数据请求服务器
                requestStream.Write(postHeaderBytes, 0, postHeaderBytes.Length);
                requestStream.Write(PicByte, 0, lengthFile);
                requestStream.Write(boundayBytes, 0, boundayBytes.Length);
                HttpWebResponse HttpWResp = (HttpWebResponse)request.GetResponse();
                myStream = HttpWResp.GetResponseStream();
            }
            catch (WebException e)
            {
                //LogResult(e.Message);
                return "";
            }
            finally
            {
                if (requestStream != null)
                {
                    requestStream.Close();
                }
            }

            //读取处理结果
            StreamReader reader = new StreamReader(myStream, code);
            StringBuilder responseData = new StringBuilder();

            String line;
            while ((line = reader.ReadLine()) != null)
            {
                responseData.Append(line);
            }
            myStream.Close();
            Pic.Close();
            string res = "";
            string result = responseData.ToString();
            ReciveDataInfo recive = JsonConvert.DeserializeObject<ReciveDataInfo>(result);
            if (recive.Code == 200)
            {
                res = "文件上传成功";
            }
            return res;
        }
        /// <summary>
        /// 上传小类药品的word文档
        /// </summary>
        /// <param name="medicineID">药品ID</param>
        /// <param name="firstID">大类ID</param>
        /// <param name="secordID">小类ID</param>
        /// <param name="fileName">文件名绝对路径</param>
        /// <returns></returns>
        public static string UplondSecordWord(string medicineID, string firstID, string secordID, string fileName)
        {
            return UplondSecord(medicineID,firstID,secordID,fileName,"docx");
        }
        /// <summary>
        /// 下载小类的word文档对象
        /// </summary>
        /// <param name="medicineID"></param>
        /// <param name="firstID"></param>
        /// <param name="secordID"></param>
        /// <returns></returns>
        public static string DownlondSecordWord(string medicineID, string firstID, string secordID)
        {
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string getfirstclass = config.AppSettings.Settings["DownSecordWord"].Value;
            string url = "http://" + StaticString.serverIp + getfirstclass;
            string argument = "?medicineId=" + medicineID + "&firstClassId=" + firstID + "&secondClassId="+secordID;
            WebRequest httpRquest = System.Net.HttpWebRequest.Create(url + argument);
            httpRquest.Method = "GET";
            httpRquest.ContentType = "application/json";
            string tempFolder =System.Windows.Forms.Application.StartupPath + "//temp";
            string FileName =tempFolder+"//"+ medicineID + firstID + secordID + ".docx";
            if (Directory.Exists(tempFolder))
                DeleteDirectory(tempFolder);
            Directory.CreateDirectory(tempFolder);
            var response = (HttpWebResponse)httpRquest.GetResponse();
            Stream responseStream = response.GetResponseStream();
            FileStream fs = new FileStream(FileName, FileMode.Create, FileAccess.Write, FileShare.ReadWrite);
            byte[] bArr = new byte[1024];
            int size = responseStream.Read(bArr, 0, (int)bArr.Length);
            while (size > 0)
            {
                //stream.Write(bArr, 0, size);
                fs.Write(bArr, 0, size);
                size = responseStream.Read(bArr, 0, (int)bArr.Length);
            }
            //stream.Close();
            fs.Close();
            responseStream.Close();
            return FileName;
        }
        /// <summary>
        /// 根据版本以及药品，下载原始的word模板（此处的word模板必须是带ID的）
        /// </summary>
        /// <param name="medicineID"></param>
        /// <param name="versionID"></param>
        /// <returns></returns>
        public static string DownlondMedicineWord(string medicineID, string versionID)
        {
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string getfirstclass = config.AppSettings.Settings["DownMedicineWord"].Value;
            string url = "http://" + StaticString.serverIp + getfirstclass;
            string argument = "?medicineId=" + medicineID + "&versionId=" + versionID;
            WebRequest httpRquest = System.Net.HttpWebRequest.Create(url + argument);
            httpRquest.Method = "GET";
            httpRquest.ContentType = "application/json";
            string tempFolder = System.Windows.Forms.Application.StartupPath + "//temp";
            string FileName = tempFolder + "//" + medicineID +versionID + ".docx";
            if (Directory.Exists(tempFolder))
                DeleteDirectory(tempFolder);
            Directory.CreateDirectory(tempFolder);
            var response = (HttpWebResponse)httpRquest.GetResponse();
            Stream responseStream = response.GetResponseStream();
            FileStream fs = new FileStream(FileName, FileMode.Create, FileAccess.Write, FileShare.ReadWrite);
            byte[] bArr = new byte[1024];
            int size = responseStream.Read(bArr, 0, (int)bArr.Length);
            while (size > 0)
            {
                //stream.Write(bArr, 0, size);
                fs.Write(bArr, 0, size);
                size = responseStream.Read(bArr, 0, (int)bArr.Length);
            }
            //stream.Close();
            fs.Close();
            responseStream.Close();
            return FileName;
        }


        public static void  DeleteDirectory(string path)
        {
            Process[] current = Process.GetProcesses();

            foreach (Process process in current)
            {

                if (process.ProcessName.ToUpper().Equals("WINWORD"))
                {
                    process.Kill(); break;
                }
            }
            System.Threading.Thread.Sleep(500);
            DirectoryInfo dir = new DirectoryInfo(path); 
            if (dir.Exists)
            {
                DirectoryInfo[] childs = dir.GetDirectories();
                foreach (DirectoryInfo child in childs)
                {
                    child.Delete(true);
                }
                dir.Delete(true);
            }
        }
    }
}
