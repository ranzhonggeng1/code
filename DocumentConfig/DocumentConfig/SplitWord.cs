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
    public class SplitWord
    {
        private WordprocessingDocument baseWord;
        int[] index = new int[2];
        int idCount = 0;
        private MedicineInfo medicine;
        private ConfigTools conftool;
        /// <summary>
        /// 构造
        /// </summary>
        /// <param name="doc">需要进行拆分的word对象</param>
        public SplitWord(WordprocessingDocument doc,int id,MedicineInfo nowMedicine,ConfigTools from)
        {
            medicine = nowMedicine;
            this.baseWord = doc;
            idCount = id;
            conftool = from;
        }
        /// <summary>
        /// 拆分小类
        /// </summary>
        /// <param name="filePath">保存拆分文件的位置</param>
        /// <param name="originName">选择的文件名</param>
        public MedicineInfo Split(string filePath, string originName, string filetype=".html")
        {
           
            int startInedx = -1, endIndex = 0;
            Body body = baseWord.MainDocumentPart.Document.Body;
            List<OpenXmlElement> nodes = new List<OpenXmlElement>();
            int splitCount = 0;
            string nowFileFolder = filePath + "\\";
            foreach (OpenXmlElement node in body.Elements<OpenXmlElement>())
            {
                nodes.Add(node);
            }
            medicine.Floder = filePath + "\\" + originName;
            for (int i = 0; i < nodes.Count; i++)
            {
                conftool.BeginInvoke(new MethodInvoker(delegate()
                {
                    double rete=Convert.ToDouble(i)/Convert.ToDouble(nodes.Count);
                    conftool.Bar.Value=Convert.ToInt32(rete*100);

                }));
                OpenXmlElement node = nodes[i];
                if (node is Paragraph)
                {
                    Paragraph paragrph = node as Paragraph;
                    int psStyle=-1;
                    if (paragrph.ParagraphProperties != null && paragrph.ParagraphProperties.ParagraphStyleId != null )
                    {
                        if (paragrph.ParagraphProperties.ParagraphStyleId.Val == "1")
                        {
                            if (startInedx > -1 && i > startInedx)
                            {
                                string fileName = originName.Replace(".docx", "_") + nodes[startInedx].InnerText + ".docx";
                                File.Copy(filePath + "\\" + originName, nowFileFolder + fileName);
                                CreateWord(nowFileFolder + fileName, startInedx, i, nodes);
                                string htmlpath = nowFileFolder + fileName.Replace(".docx", filetype);
                                HtmlHelper.SaveFile(nowFileFolder + fileName, htmlpath);
                                HtmlHelper htmlhelper = new HtmlHelper(htmlpath, idCount);
                                //if (File.Exists(nowFileFolder + fileName))
                                //    File.Delete(nowFileFolder + fileName);

                                int firstIndex = GetFirstIndex(nowFileFolder);
                                SecordInfo secord = new SecordInfo();
                                secord.DocxPath = nowFileFolder + fileName;
                                secord.Name = nodes[startInedx].InnerText;
                                secord.FilePath = htmlpath;
                                secord.ParentFirst = medicine.FirstClass[firstIndex];
                                secord.ParentMedicine = medicine;
                                medicine.FirstClass[firstIndex].Secords.Add(secord);
                                startInedx = i + 1;
                            }
                            string folderName = paragrph.InnerText;
                            string fileFolder = filePath + "\\" + folderName;
                            if (!Directory.Exists(fileFolder))
                                Directory.CreateDirectory(fileFolder);
                            nowFileFolder = fileFolder + "\\";
                            FirstInfo first = new FirstInfo();
                            first.Name = folderName;
                            first.Floder = nowFileFolder;
                            first.ParentMedicine = medicine;
                            medicine.FirstClass.Add(first);
                        }
                        else if (int.TryParse(paragrph.ParagraphProperties.ParagraphStyleId.Val, out psStyle) && psStyle > 1 && medicine.FirstClass.Count>0)
                        {
                            if (startInedx == -1)
                                startInedx = i;
                            else if (startInedx < i)
                            {
                                endIndex = i;
                                splitCount++;
                                string fileName = originName.Replace(".docx", "_") + nodes[startInedx].InnerText + ".docx";
                                File.Copy(filePath + "\\" + originName,nowFileFolder + fileName);
                                CreateWord(nowFileFolder + fileName, startInedx, endIndex, nodes);
                                string htmlpath = nowFileFolder + fileName.Replace(".docx", filetype);
                                HtmlHelper.SaveFile(nowFileFolder + fileName, htmlpath);
                                HtmlHelper htmlhelper = new HtmlHelper(htmlpath, idCount);
                                //if (File.Exists(nowFileFolder + fileName))
                                //    File.Delete(nowFileFolder + fileName);
                                int firstIndex = GetFirstIndex(nowFileFolder);
                                SecordInfo secord = new SecordInfo();
                                secord.DocxPath = nowFileFolder + fileName;
                                secord.Name = nodes[startInedx].InnerText;
                                secord.FilePath = htmlpath;
                                secord.ParentFirst = medicine.FirstClass[firstIndex];
                                secord.ParentMedicine = medicine;
                                medicine.FirstClass[firstIndex].Secords.Add(secord);
                                startInedx = i;
                                //endIndex = 0;
                            }
                        }
                    }
                }
            }
            if (startInedx>-1&&startInedx < nodes.Count)
            {
                splitCount++;
                endIndex = nodes.Count;
                string fileName = originName.Replace(".docx", "_") + nodes[startInedx].InnerText + ".docx";
                File.Copy(filePath + "\\" + originName, nowFileFolder + fileName);
                CreateWord(nowFileFolder + fileName, startInedx, endIndex, nodes);
                string htmlpath = nowFileFolder + fileName.Replace(".docx", filetype);
                HtmlHelper.SaveFile(nowFileFolder + fileName, htmlpath);
                HtmlHelper htmlhelper = new HtmlHelper(htmlpath, idCount);
                //if (File.Exists(nowFileFolder + fileName))
                //    File.Delete(nowFileFolder + fileName);
                int firstIndex = GetFirstIndex(nowFileFolder);
                SecordInfo secord = new SecordInfo();
                secord.DocxPath = nowFileFolder + fileName;
                secord.Name = nodes[startInedx].InnerText;
                secord.FilePath = htmlpath;
                secord.ParentFirst = medicine.FirstClass[firstIndex];
                secord.ParentMedicine = medicine;
                medicine.FirstClass[firstIndex].Secords.Add(secord);
            }
            baseWord.Dispose();
            return medicine;
        }
        private int GetFirstIndex(string floderName)
        {
            int index = 0;
            for (int i = 0; i < medicine.FirstClass.Count; i++)
            {
                if (medicine.FirstClass[i].Floder == floderName)
                    return i;
            }
            return index;
        }
      
        /// <summary>
        /// 创建拆分的子文件，执行文件生成操作
        /// </summary>
        /// <param name="filepath">小类文件名</param>
        /// <param name="startIndex">起始的node</param>
        /// <param name="endIndex">结束的node</param>
        /// <param name="nodes">原文档的node节点集</param>
        public void CreateWord(string filepath,int startIndex,int endIndex,List<OpenXmlElement> nodes)
        {
            WordprocessingDocument newWord = WordprocessingDocument.Open(filepath,true);
            
            MainDocumentPart objMainDocumentPart = newWord.MainDocumentPart;
            var oldbody = objMainDocumentPart.Document.Body;
            oldbody.Remove();
            objMainDocumentPart.Document = new Document(new Body());
            Body objBody = objMainDocumentPart.Document.Body;
            for (int i = startIndex; i < endIndex; i++)
            {
                OpenXmlElement node = nodes[i].Clone() as OpenXmlElement;
                if(node!=null)
                    objBody.Append(node);
            }
            objMainDocumentPart.Document.Save();
            newWord.Dispose();
        }
    }
}
