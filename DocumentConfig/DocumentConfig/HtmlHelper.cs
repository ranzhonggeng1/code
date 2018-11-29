using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Office.Interop.Word;
using System.IO;
using HtmlAgilityPack;
using System.Configuration;
using System.Text.RegularExpressions;
using System.Drawing;
namespace DocumentConfig
{
    public class HtmlHelper
    {
        string strhtml;
        private static string[] imageFromat = { ".jpg",".png",".ico",".emf",".wmf",".jpeg",".bmp" };
        private HtmlDocument html = new HtmlDocument();
        private string underLineRepalceType = "";
        private string underLineRepalceStyle = "";
        private string underLineRepalceOnclick = "";
        private string tableSpaceCellRepalceType = "";
        private string tableSpaceCellRepalceStyle = "";
        private string tableSpaceCellRepalceOnClick = "";
        private string checkboxReplaceType = "";
        private string checkboxReplaceStyle = "";
        private string checkboxReplaceOnclick = "";
        private int startid;
        string filoder = string.Empty;
        /// <summary>
        /// 保存html文件
        /// </summary>
        /// <param name="documentPath"></param>
        /// <param name="outFilePath"></param>
        public static void SaveFile(string documentPath,string outFilePath)
        {
            object missingType = Type.Missing;
            object readOnly = true;
            object isVisible = false;
            object documentFormat = 8;
            string randomName = DateTime.Now.Ticks.ToString();
            object htmlFilePath = outFilePath;
            object filePath =documentPath;
            //object encode = Microsoft.Office.Core.MsoEncoding.msoEncodingUTF8;
            ApplicationClass applicationclass = new ApplicationClass();
            applicationclass.Documents.Open(ref filePath,
                                            ref readOnly,
                                            ref missingType, ref missingType, ref missingType,
                                            ref missingType, ref missingType, ref  missingType,
                                            ref missingType, ref missingType, ref isVisible,
                                            ref missingType, ref missingType, ref missingType,
                                            ref missingType, ref missingType);
            applicationclass.Visible = false;
            Document document = applicationclass.ActiveDocument;

            //Save the word document as HTML file
            document.SaveAs(ref htmlFilePath, ref documentFormat, ref missingType,
                            ref missingType, ref missingType, ref missingType,
                            ref missingType, ref missingType, ref missingType,
                            ref missingType, ref missingType, ref missingType,
                            ref missingType, ref missingType, ref missingType,
                            ref missingType);

            //Close the word document
            document.Close(ref missingType, ref missingType, ref missingType);
            applicationclass.Application.Quit();

        }
        /// <summary>
        /// 构造+实现，这个地方代码结构不好，后面需要调整
        /// </summary>
        /// <param name="filePath"></param>
        /// <param name="startID"></param>
        public HtmlHelper(string filePath,int startID)
        {
            //读取配置文件
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            underLineRepalceType = config.AppSettings.Settings["underLineRepalceType"].Value;
            underLineRepalceStyle = config.AppSettings.Settings["underLineRepalceStyle"].Value;
            //underLineRepalceOnclick = config.AppSettings.Settings["underLineRepalceOnclick"].Value;
            tableSpaceCellRepalceType = config.AppSettings.Settings["tableSpaceCellRepalceType"].Value;
            tableSpaceCellRepalceStyle = config.AppSettings.Settings["tableSpaceCellRepalceStyle"].Value;
            //tableSpaceCellRepalceOnClick = config.AppSettings.Settings["tableSpaceCellRepalceOnClick"].Value;
            checkboxReplaceType = config.AppSettings.Settings["checkboxReplaceType"].Value;
            checkboxReplaceStyle = config.AppSettings.Settings["checkboxReplaceStyle"].Value;
            //checkboxReplaceOnclick = config.AppSettings.Settings["checkboxReplaceOnclick"].Value;
            filoder = Path.GetDirectoryName(filePath);
            Stream myStream = new FileStream(filePath,FileMode.Open);
            Encoding encode = System.Text.Encoding.GetEncoding("gb2312");
            StreamReader myStreamReader = new StreamReader(myStream, encode);
            startid = startID;
            strhtml = myStreamReader.ReadToEnd();
            myStream.Dispose();
            html.LoadHtml(strhtml);
            HtmlNodeCollection nodeCollection = html.DocumentNode.ChildNodes;
            Paresr(nodeCollection);
            html.Save(filePath,Encoding.UTF8);
        }
        public string StrHtml
        {
            get { return strhtml; }
        }
        /// <summary>
        /// 解析html
        /// </summary>
        /// <param name="nodeCollection"></param>
        public void Paresr(HtmlNodeCollection nodeCollection)
        {
           
            foreach (var node in nodeCollection)
            {
                ReplaceNode(node);
                if (node.HasChildNodes)
                    Paresr(node.ChildNodes);
                if (node.Name == "html")
                {
                    HtmlNode jsnode = CreateJsNode();
                    node.AppendChild(jsnode);
                }
                if (node.Name == "style")
                {
                    HtmlNode cssnode = CreateCssNode();
                    node.AppendChild(cssnode);
                }
            }
           

        }
        /// <summary>
        /// 创建需要用到的JS节点
        /// </summary>
        /// <returns></returns>
        private HtmlNode CreateJsNode()
        {
            HtmlNode jsnode = html.CreateElement("script");
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            var sets=config.AppSettings;
            foreach (string key in sets.Settings.AllKeys)
            {
                if (key.IndexOf("js") > -1)
                {
                    string jsFunction = sets.Settings[key].Value;
                    jsnode.InnerHtml += jsFunction + "\n";
                }
            }
            
            return jsnode;
            
        }
        /// <summary>
        /// 创建需要用到的css节点
        /// </summary>
        /// <returns></returns>
        private HtmlNode CreateCssNode()
        {
            HtmlNode Cssnode = html.CreateTextNode();
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            var sets = config.AppSettings;
            foreach (string key in sets.Settings.AllKeys)
            {
                if (key.IndexOf("css") > -1)
                {
                    string css = sets.Settings[key].Value;
                    Cssnode.InnerHtml += css + "\n";
                }
            }
            return Cssnode;
        }
        /// <summary>
        /// html标签
        /// </summary>
        /// <param name="node"></param>
        private void ReplaceNode(HtmlNode node)
        {
            if (node.Name == "u"&&node.InnerText.IndexOf("ID")>-1)
            {
                string id = GetIDStr(node.InnerText);
                //node.InnerHtml = "<input type=\"text\"  value=\"配置点" + id + "\"" + "  style=\"border:0px;border-bottom-width:1px;border-bottom-style:inset;color:blue; \"" + "id=ID" + "\"" + id + "\"" + "/>";
                node.Name = "span";
                node.InnerHtml = CreateReplaceStr(underLineRepalceType, underLineRepalceStyle, id);
            }
            else if (node.Name == "td" && node.InnerText.IndexOf("ID") > -1&&(!HasUnderLine(node)||!node.HasChildNodes))
            {
                string id = GetIDStr(node.InnerText);
                //node.InnerHtml = "<input type=\"text\" value=\"配置点" + id + "\"" + "  style=\"border:0px;width:100%; height:100%;color:blue;\"" + "id=ID" + "\"" + id + "\"" + "/>";
                node.InnerHtml = CreateReplaceStr(tableSpaceCellRepalceType,tableSpaceCellRepalceStyle,id);
            }
            if (node.Name == "span" && (node.InnerText.IndexOf("□") > -1))
            {
                RepalceCheckBox(node);
            }
            if (node.Name == "head" && node.InnerHtml.IndexOf("charset=") > -1)
            {
                string innterhtml = node.InnerHtml;
                int start_index = innterhtml.IndexOf("charset=") + 8;
                int end_inedx = innterhtml.Substring(start_index).IndexOf("\"");
                string charset = innterhtml.Substring(start_index,end_inedx);
                innterhtml = innterhtml.Replace(charset,"UTF-8");
                node.InnerHtml = innterhtml;
            }
            if (IsImageNode(node))
            {

                node.InnerHtml = ReplaceImagePath(node.InnerHtml);
            }
        }
        /// <summary>
        /// 替换html文件中生成的图片为base64编码
        /// </summary>
        /// <param name="htmlstr"></param>
        /// <returns></returns>
        private string ReplaceImagePath(string htmlstr)
        {
            string result = string.Empty;
            while (htmlstr.IndexOf("src=") > -1)
            {
                int startIndex = htmlstr.IndexOf("src=") + 4;
                int length = htmlstr.Substring(startIndex).IndexOf(">");
                string imagePath = htmlstr.Substring(startIndex, length);
                string type;
                imagePath = GetImagePath(imagePath, out type);
                string base64img = "";
                result += htmlstr.Substring(0, startIndex + length);
                if (File.Exists(filoder + "\\" + imagePath))
                {
                    base64img = ImgToBase64String(imagePath, type);
                    result = result.Replace(imagePath, base64img);
                }
                htmlstr = htmlstr.Substring(startIndex+length);
            }
            result += htmlstr;
            return result;
        }
        /// <summary>
        /// html代码中得到图片地址
        /// </summary>
        /// <param name="htmlstr"></param>
        /// <param name="type"></param>
        /// <returns></returns>
        private string GetImagePath(string htmlstr,out string type)
        {
            foreach (string fromat in imageFromat)
            {
                if (htmlstr.IndexOf(fromat) > -1)
                {
                    int index = htmlstr.IndexOf(fromat) + 4;
                    type = fromat;
                    return htmlstr.Substring(0,index).Replace("\"","");
                }
            }
            type = ".png";
            return "";
        }
        /// <summary>
        /// 判定当前节点是否是html的节点
        /// </summary>
        /// <param name="node"></param>
        /// <returns></returns>
        private bool IsImageNode(HtmlNode node)
        {
            foreach (var item in node.ChildNodes)
            {
                if (item.Name == "img" && item.OuterHtml.IndexOf("src=") > -1)
                    return true;
            }
            return false;
        }
        /// <summary>
        /// 图片转化为base64编码
        /// </summary>
        /// <param name="ImagePath">图片地址</param>
        /// <returns></returns>
       public string ImgToBase64String(string ImagePath,string type)
        {
            Bitmap bmp = new Bitmap(filoder+"\\"+ImagePath);
            MemoryStream ms = new MemoryStream();
            bmp.Save(ms, System.Drawing.Imaging.ImageFormat.Jpeg);
            byte[] arr = new byte[ms.Length];
            ms.Position = 0;
            ms.Read(arr, 0, (int)ms.Length);
            ms.Close();
            return "data:image/"+type+";base64," + Convert.ToBase64String(arr);
        }
        private string CreateReplaceStr(string typeStr,string styleStr,string ID)
        {
            return "<input type=\"" + typeStr + "\" value=\"配置点" + ID + "\" style=" + styleStr + "id=\"" + ID + "\"" +"readonly=\"readonly\" onclick=\"conClick_f(this)\" ondblclick=\"ondblclick_f(this)\""+ "/>";
        }
        /// <summary>
        /// 替换复选框
        /// </summary>
        /// <param name="node"></param>
        private void RepalceCheckBox(HtmlNode node)
        {
            int replaceCount = Regex.Matches(node.InnerText, "□").Count;
            string originText = node.InnerText;
            for (int i = 0; i < replaceCount; i++)
            {
                startid++;
                int index = originText.IndexOf("□") + 1;
                string checkText = string.Empty;
                if (node.InnerText.Length > index)
                {
                    checkText = node.InnerText.Substring(index);
                    originText = checkText;
                    if (checkText.IndexOf("□") > -1)
                    {
                        int endindex = checkText.IndexOf("□");
                        checkText = checkText.Substring(0,endindex);
                    }
                }
                if (checkText == string.Empty)
                {
                    string parentText = node.ParentNode.InnerText;
                    index = parentText.IndexOf("□");
                    checkText = parentText.Substring(index + 1);
                    int endindex = checkText.IndexOf("□");
                    if(endindex>0)
                        checkText = checkText.Substring(0, endindex);
                }
                if(i==0)
                    node.InnerHtml = string.Empty;
                node.InnerHtml += CreateReplaceStr(checkboxReplaceType, checkboxReplaceStyle,startid.ToString()) + checkText;
            }
        }
        /// <summary>
        /// 判定是否含有下划线标签
        /// </summary>
        /// <param name="node"></param>
        /// <returns></returns>
        private bool HasUnderLine(HtmlNode node)
        {
            bool result=false;
            foreach (HtmlNode childNode in node.ChildNodes)
            {
                if (childNode.Name == "u")
                    return true;
                else if (childNode.HasChildNodes)
                {
                    result = HasUnderLine(childNode);
                    if (result)
                        return result;
                }
            }
            return result;
        }
        /// <summary>
        /// 得到ID后面的数字字符串
        /// </summary>
        /// <param name="text"></param>
        /// <returns></returns>
        public static string GetIDStr(string text)
        {
            int numIndex = text.IndexOf("ID")+2;
            string numstr = text.Substring(numIndex);
            string result = string.Empty;
            foreach (char c in numstr)
            {
                int num = 0;
                if (int.TryParse(c.ToString(), out num))
                {
                    result += c.ToString();
                }
                else
                    break;
            }
            return result;
        }
    }
}
