using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using DocumentFormat.OpenXml;
using DocumentFormat.OpenXml.Packaging;
using DocumentFormat.OpenXml.Wordprocessing;
using DocumentConfig.model;
namespace DocumentConfig
{
    /// <summary>
    /// 一些页面上需要调用到的方法，后面按结构进行分类
    /// </summary>
    class WordControlHelper
    {
        private static char[] replaceChar = { '_', (char)(32) };
        /// <summary>
        /// 将'_'字符替换成空格
        /// </summary>
        /// <param name="inText"></param>
        /// <returns></returns>
        public static List<string> Replace_ToSpace(string inText)
        {
            List<string> result = new List<string>();
            string s = string.Empty;
            foreach (char c in inText)
            {
                if (replaceChar.ToList().Contains(c))
                {
                    if (s.IndexOf("_") == -1 && s != string.Empty)
                    {
                        result.Add(s);
                        s = string.Empty;
                    }
                    s += c.ToString();
                }
                else
                {
                    if (s != string.Empty && s.IndexOf("_") > -1)
                    {
                        result.Add(s);
                        s = string.Empty;
                        s += c.ToString();
                    }
                    else
                        s += c.ToString();
                }
            }
            if (s != string.Empty)
                result.Add(s);
            return result;
        }
        /// <summary>
        /// 替换word里面的下划线成配置点
        /// </summary>
        /// <param name="body">文档的body对象</param>
        /// <param name="idcount">当前Id的最大值</param>
        /// <returns></returns>
        public static int ReplaceUnderLine(ref Body body,ref int idcount)
        {
            Underline underlineStyle = new Underline(); ;
            List<Text> textList = new List<Text>();
            int id = idcount;
            foreach (Paragraph par in body.Elements<Paragraph>())
            {
                if (par.InnerText.IndexOf("_") > -1)
                {
                    foreach (Run originrun in par.Elements<Run>())
                    {

                        List<string> texts = Replace_ToSpace(originrun.InnerText);
                        for (int i = 0; i < texts.Count; i++)
                        {
                            Run run = new Run();
                            string text = texts[i].Replace("_", " ");
                            run.Append(new Text(text));

                            if (text.Trim() == string.Empty)
                            {
                                if (run.RunProperties == null)
                                {
                                    run.RunProperties = new RunProperties();
                                    run.RunProperties.Underline = new Underline();
                                    run.RunProperties.Underline.Val = UnderlineValues.Single;
                                }
                                else if (run.RunProperties.Underline == null)
                                {
                                    run.RunProperties.Underline = new Underline();
                                    run.RunProperties.Underline.Val = UnderlineValues.Single;
                                }
                            }
                            par.InsertBefore(run, originrun);
                            // par.Append(run);
                        }
                        originrun.Remove();
                    }
                }
                List<RunShell> runshells = GetReplaceRun(par);

                bool isAredlyRepalce = false;
                int replaceCount = 0;
                foreach (Run r in par.Elements<Run>())
                {
                    if (r.InnerText.Trim() != "" && !IsNeedReplace(r.InnerText))
                        isAredlyRepalce = false;
                    if (((r.RunProperties != null && r.RunProperties.Underline != null) || IsNeedReplace(r.InnerText)))
                    {

                        if (r.RunProperties != null && r.RunProperties.Underline != null)
                            underlineStyle = r.RunProperties.Underline;
                        foreach (Text runText in r.Elements<Text>())
                        {
                            if ((runText.InnerText.Trim() == string.Empty && runshells.Count > replaceCount || IsNeedReplace(runText.InnerText)) && !isAredlyRepalce)
                            {
                                id++;
                                runText.Remove();
                                r.Append(new Text("\rID" + id));
                                if (r.RunProperties == null)
                                {
                                    r.RunProperties = new RunProperties();
                                    r.RunProperties.Underline = new Underline();
                                    r.RunProperties.Underline.Color = underlineStyle.Color;
                                    if (underlineStyle.Val != null)
                                        r.RunProperties.Underline.Val = underlineStyle.Val;
                                    r.RunProperties.Underline.Val = UnderlineValues.Single;
                                }
                                else if (r.RunProperties.Underline == null)
                                {
                                    r.RunProperties.Underline = new Underline();
                                    r.RunProperties.Underline.Color = underlineStyle.Color;
                                    if (underlineStyle.Val != null)
                                        r.RunProperties.Underline.Val = underlineStyle.Val;
                                    r.RunProperties.Underline.Val = UnderlineValues.Single;
                                }
                                isAredlyRepalce = true;
                                replaceCount++;
                            }
                            else if (runText.InnerText.Trim() == string.Empty && runshells.Count > 0 || IsNeedReplace(runText.InnerText))
                            {
                                runText.Remove();
                                r.Append(new Text(""));
                            }
                        }
                    }

                }
            }
            //this.richTextBox1.Text = text;
            return id;
        }
        /// <summary>
        /// 判断此段落是有几个需要替换的点
        /// </summary>
        /// <param name="paragraph"></param>
        /// <returns></returns>
        public static List<RunShell> GetReplaceRun(Paragraph paragraph)
        {
            int index = 0;
            int underLinecount = 0;
            List<RunShell> runs = new List<RunShell>();
            RunShell runshell = new RunShell();
            foreach (Run run in paragraph.Elements<Run>())
            {

                if (run.RunProperties != null && run.RunProperties.Underline != null || IsNeedReplace(run.InnerText))
                {
                    if (underLinecount == 0)
                        index = 0;
                    if (index == underLinecount)
                    {
                        runshell.runs.Add(run);
                        runshell.text += run.InnerText.Replace("_", "");
                    }
                    else
                    {
                        underLinecount = 0;
                        runshell = new RunShell();
                    }
                    underLinecount++;
                }
                else
                {
                    underLinecount = 0;
                    if (runshell.runs.Count > 0 && runshell.text.Trim() == "")
                    {
                        runs.Add(runshell);
                        runshell = new RunShell();
                    }
                    else if (runshell.runs.Count > 0)
                        runshell = new RunShell();
                }

                index++;

            }
            if (runshell.runs.Count > 0 && runshell.text.Trim() == "")
                runs.Add(runshell);
            return runs;
        }
        /// <summary>
        /// 替换表格里面的输入位置为输入点
        /// </summary>
        /// <param name="body"></param>
        /// <param name="id">当前ID的最大值</param>
        /// <returns></returns>
        public static int ReplaceTableSpace(ref Body body, int id)
        {
            foreach (Table table in body.Elements<Table>())
            {
                foreach (TableRow tableRow in table.Elements<TableRow>())
                {
                    foreach (TableCell tableCell in tableRow.Elements<TableCell>())
                    {
                        if (tableCell.InnerText.Trim() == string.Empty)
                        {
                            bool isAredlyReplace = false;
                            if (!(tableCell.TableCellProperties.VerticalMerge != null && tableCell.TableCellProperties.VerticalMerge.Val == null))
                            {
                                foreach (Paragraph paragraph in tableCell.Elements<Paragraph>())
                                {

                                    if (!isAredlyReplace)
                                    {
                                        if (paragraph.Elements<Run>().Count() > 0)
                                        {
                                            foreach (Run run in paragraph.Elements<Run>())
                                            {
                                                id++;
                                                run.Remove();
                                                run.Append(new Text("      ID" + id));
                                            }
                                        }
                                        else
                                        {
                                            id++;
                                            Run run = new Run();
                                            run.Append(new Text("      ID" + id));
                                            paragraph.Append(run);
                                        }
                                        isAredlyReplace = true;
                                    }
                                }
                            }
                        }
                        else if (TableCellIsNeedRelpace(tableCell))//此单元格的文字前后存在空格的时候
                        {
                            bool reaplce = true;
                            bool isAredlyReplace = false;
                            foreach (Paragraph paragraph in tableCell.Elements<Paragraph>())
                            {
                                if (paragraph.InnerText.IndexOf("_") > -1)
                                {

                                    foreach (Run originrun in paragraph.Elements<Run>())
                                    {

                                        List<string> texts = Replace_ToSpace(originrun.InnerText);
                                        for (int i = 0; i < texts.Count; i++)
                                        {
                                            Run run = new Run();
                                            string text = texts[i].Replace("_", " ");
                                            run.Append(new Text(text));

                                            if (text.Trim() == string.Empty && texts[i].Trim() != string.Empty)
                                            {
                                                if (run.RunProperties == null)
                                                {
                                                    run.RunProperties = new RunProperties();
                                                    run.RunProperties.Underline = new Underline();
                                                    run.RunProperties.Underline.Val = UnderlineValues.Single;
                                                }
                                                else if (run.RunProperties.Underline == null)
                                                {
                                                    run.RunProperties.Underline = new Underline();
                                                    run.RunProperties.Underline.Val = UnderlineValues.Single;
                                                }
                                            }
                                            paragraph.InsertBefore(run, originrun);
                                            //addList.Add(run);
                                        }
                                        //removeList.Add(originrun);
                                        if (texts.Count > 0)
                                            originrun.Remove();
                                    }

                                }
                                foreach (Run run in paragraph.Elements<Run>())
                                {
                                    if ((run.RunProperties != null && run.RunProperties.Underline != null || run.InnerText.IndexOf("_") > -1) && run.InnerText.Trim(replaceChar) == string.Empty)
                                    {
                                        if (tableCell.InnerText.Trim() != string.Empty)
                                            isAredlyReplace = false;
                                        if (!isAredlyReplace)
                                        {
                                            foreach (Text runText in run.Elements<Text>())
                                            {
                                                if (reaplce)
                                                {
                                                    id++;
                                                    run.Append(new Text("      ID" + id));
                                                    if (run.RunProperties == null)
                                                    {
                                                        run.RunProperties = new RunProperties();
                                                        run.RunProperties.Underline = new Underline();
                                                        run.RunProperties.Underline.Val = UnderlineValues.Single;
                                                    }
                                                    else if (run.RunProperties.Underline == null)
                                                    {
                                                        run.RunProperties.Underline = new Underline();

                                                        run.RunProperties.Underline.Val = UnderlineValues.Single;
                                                    }
                                                    reaplce = false;
                                                    runText.Remove();
                                                }
                                                else
                                                    runText.Remove();
                                            }
                                            isAredlyReplace = true;
                                        }
                                        else
                                            run.Remove();
                                    }
                                    else
                                        reaplce = true;
                                }
                            }
                        }
                    }
                }
            }
            return id;
        }
        /// <summary>
        /// 得到当前文档的所有ID和段落的对应关系，仅仅在修改已有模板的时候调用
        /// </summary>
        /// <param name="word"></param>
        /// <returns></returns>
        public static List<XmlNodeAndID> GetAllDocumentID(WordprocessingDocument word)
        {
            List<XmlNodeAndID> result = new List<XmlNodeAndID>();
            int startInedx = -1;
            Body body = word.MainDocumentPart.Document.Body;
            List<OpenXmlElement> nodes = new List<OpenXmlElement>();
            foreach (OpenXmlElement node in body.Elements<OpenXmlElement>())
            {
                nodes.Add(node);
            }
            for (int i = 0; i < nodes.Count; i++)
            {
                 OpenXmlElement node = nodes[i];
                 if (node is Paragraph)
                 {
                     Paragraph paragrph = node as Paragraph;
                     int psStyle = -1;
                     if (paragrph.ParagraphProperties != null && paragrph.ParagraphProperties.ParagraphStyleId != null)
                     {
                        if (int.TryParse(paragrph.ParagraphProperties.ParagraphStyleId.Val, out psStyle) && psStyle > 1)
                         {
                             if (startInedx == -1)
                                 startInedx = i;
                             else if (startInedx < i)
                             {
                                 XmlNodeAndID xmlnode = new XmlNodeAndID();
                                 xmlnode.StartID=startInedx;
                                 xmlnode.EndID=i;
                                 xmlnode.StartElement=nodes[startInedx];
                                 xmlnode.EndElement=node;
                                 xmlnode.IDList = GetIdList(startInedx,i,nodes);
                                 result.Add(xmlnode);
                                 startInedx = i;
                             }
                         }
                     }
                 }
                
            }
            if (startInedx > -1 && startInedx < nodes.Count)
            {
                XmlNodeAndID xmlnode = new XmlNodeAndID();
                xmlnode.StartID = startInedx;
                xmlnode.EndID = nodes.Count;
                xmlnode.StartElement = nodes[startInedx];
                xmlnode.EndElement = nodes[nodes.Count-1];
                xmlnode.IDList = GetIdList(startInedx, nodes.Count, nodes);
                result.Add(xmlnode);
            }
            word.Dispose();
            return result;
        }
        /// <summary>
        /// 通过小类名字，得到当前小类所属的ID对应列表
        /// </summary>
        /// <param name="secordname"></param>
        /// <param name="xmlnodes"></param>
        /// <returns></returns>
        internal static XmlNodeAndID GetIdNodeByName(string secordname,List<XmlNodeAndID> xmlnodes)
        {
            foreach (var item in xmlnodes)
            {
                if (item.StartElement.InnerText.IndexOf(secordname)>-1)
                {
                    return item;
                }
            }
            return null;
        }
        /// <summary>
        /// 根据新的小类，找出原本对应的应该是那个小类
        /// </summary>
        /// <param name="idnode">当前小类ID列表对象</param>
        /// <param name="oldnodes">从服务器获取的文档ID对应的集合</param>
        /// <param name="newAddid">本次新增的id</param>
        /// <returns></returns>
        internal static string GetSecordNameByIdNode(XmlNodeAndID idnode,List<XmlNodeAndID> oldnodes,List<string> newAddid)
        {
            string res=string.Empty;
            foreach (var item in oldnodes)
            {
                double rat = CompareNodeId(idnode,item,newAddid);
                if (rat==1)
                {
                    res = item.StartElement.InnerText;
                    return res;
                }
                else if (rat > 0)
                {
                    res="error";
                }
            }
            return res;
        }
        /// <summary>
        /// 比较ID所属
        /// </summary>
        /// <param name="newNode"></param>
        /// <param name="oldNode"></param>
        /// <param name="newAddid"></param>
        /// <returns></returns>
        internal static double CompareNodeId(XmlNodeAndID newNode, XmlNodeAndID oldNode, List<string> newAddid)
        {
            int sum = 0;
            int count = newNode.IDList.Count;
            foreach (string id in newNode.IDList)
            {
                if (oldNode.IDList.Exists(o => o == id))
                    sum++;
                else if (newAddid.Exists(o => o == id))
                {
                    count--;
                }
            }
            return Convert.ToDouble(sum.ToString()) / count;
        }
        /// <summary>
        /// 判断得到的老名字是不是在新的文档里面已经有了
        /// </summary>
        /// <param name="secordName"></param>
        /// <param name="secords"></param>
        /// <returns></returns>
        internal static bool IsHasOldName(string secordName,List<SecordInfo> secords)
        {
            foreach (var item in secords)
            {
                if (item.Name == secordName)
                    return true;
            }
            return false;
        }
        /// <summary>
        /// 当前小类所有的ID
        /// </summary>
        /// <param name="startindex"></param>
        /// <param name="endindex"></param>
        /// <param name="nodes"></param>
        /// <returns></returns>
        internal static List<string> GetIdList(int startindex, int endindex, List<OpenXmlElement> nodes)
        {
            List<string> result = new List<string>();
            for (int i = startindex; i < endindex; i++)
            {
                OpenXmlElement node = nodes[i];
                string text = node.InnerText;
                while (text.IndexOf("ID") > 0)
                {
                    int numIndex = text.IndexOf("ID") + 2;
                    string numStr = HtmlHelper.GetIDStr(text);
                    result.Add(numStr);
                    text = text.Substring(numIndex+numStr.Length);
                }
            }
            return result;
        }
        /// <summary>
        /// 判断表格的单元格是都需要进行空格替换
        /// </summary>
        /// <param name="cell"></param>
        /// <returns></returns>
        public static bool TableCellIsNeedRelpace(TableCell cell)
        {
            string innerText = cell.InnerText;
            if (innerText.TrimStart(replaceChar) != innerText || innerText.TrimEnd(replaceChar) != innerText || innerText.IndexOf("_") > -1)
                return true;
            else if (innerText.IndexOf(" ") > -1)
            {
                foreach (Paragraph paragraph in cell.Elements<Paragraph>())
                {
                    foreach (Run run in paragraph.Elements<Run>())
                    {
                        if ((run.RunProperties != null && run.RunProperties.Underline != null && run.InnerText.Trim() == string.Empty))
                        {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        /// <summary>
        /// 判断文本内容是否属于替换内容
        /// </summary>
        /// <param name="text"></param>
        /// <returns></returns>
        public static bool IsNeedReplace(string text)
        {
            if (text.Trim() == string.Empty)
                return false;
            foreach (char c in text)
            {
                if (!replaceChar.ToList().Contains(c))
                    return false;
            }
            return true;
        }
        /// <summary>
        /// 根据药品名字，返回药品对象
        /// </summary>
        /// <param name="name"></param>
        /// <param name="medicines"></param>
        /// <returns></returns>
        public static MedicineInfo GetMedinice(string name,List<MedicineInfo>medicines)
        {
            MedicineInfo info = new MedicineInfo();
            foreach (var item in medicines)
            {
                if (name == item.MedicineName)
                {
                    return item;
                }
            }
            return info;
        }
        /// <summary>
        /// 根据大类名字返回大类对象
        /// </summary>
        /// <param name="name"></param>
        /// <param name="firsts"></param>
        /// <returns></returns>
        public static FirstInfo GetFirst(string name, List<FirstInfo> firsts)
        {
            FirstInfo info = new FirstInfo();
            foreach (var item in firsts)
            {
                if (name == item.Name)
                    return item;
            }
            return info;
        }
        /// <summary>
        /// 根据小类名字，返回小类对象
        /// </summary>
        /// <param name="name"></param>
        /// <param name="secords"></param>
        /// <returns></returns>
        public static SecordInfo GetSecord(string name, List<SecordInfo> secords)
        {
            SecordInfo info = new SecordInfo();
            foreach (var item in secords)
            {
                if (name == item.Name)
                    return item;
            }
            return info;
        }
    }
}
