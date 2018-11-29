using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using DocumentFormat.OpenXml;
using DocumentFormat.OpenXml.Packaging;
using DocumentFormat.OpenXml.Wordprocessing;
using DocumentConfig.model;
using DocumentConfig.ServerHelper;
using System.Threading;
using System.Configuration;
using Microsoft.VisualBasic;
namespace DocumentConfig
{
    public partial class ConfigTools : Form
    {
        private string filePath=string.Empty;
        string tempDocName = Application.StartupPath + @"\temp.docx";
        private int idcount = 0;
        private MedicineInfo medicine;
        private List<MedicineInfo> medicines;
        private Login loginFrom;
        private User user;
        private int startID = 0;
        private List<XmlNodeAndID> old_IdInfo;
        private List<string> newaddID=new List<string>();
        public ConfigTools()
        {
            InitializeComponent();
            this.IsEnable(false);
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string configpath = config.AppSettings.Settings["ConfigPath"].Value;
            StaticString.serverIp = config.AppSettings.Settings["serverIp"].Value;
            StaticString.ConfigUrl = configpath;
            this.skinEngine1.SkinFile = System.Environment.CurrentDirectory + "\\Skins\\WaveColor1.ssk";
            
        }
        /// <summary>
        /// 用户属性，表示当前登陆用户
        /// </summary>
        public User User
        {
            get { return user; }
            set 
            {
                user = value;
                if (user != null)
                {
                    this.Visible = true;
                    this.IsEnable(true);
                    this.EditWord.Enabled = false;
                    if (medicine != null)
                        this.Text = "当前用户: " + user.Name + " 药品：" + medicine.MedicineName;
                    else
                        this.Text = "当前用户: " + user.Name;
                    this.Login_button.Text = "注销";
                }
                else
                {
                    this.Text = "请登录";
                    this.Login_button.Text = "登陆";
                }
            }
        }
        /// <summary>
        /// 文件路径，当前选择文件的地址
        /// </summary>
        public string FilePath
        {
            get { return filePath; }
            set { filePath = value; }
        }
        public MedicineInfo Medicine
        {
            get { return medicine; }
            set 
            {
                medicine = value;
                this.Text = "当前用户:"+user.Name+" 药品：" + medicine.MedicineName;
            }
        }
        private void AddMedicine_Click(object sender, EventArgs e)
        {
            AddMedicineFrom from = new AddMedicineFrom(this);
            from.Show();
        }
        /// <summary>
        /// 打开一个文件
        /// </summary>
        public void OpenFile()
        {
            try
            {
                this.BeginInvoke(new MethodInvoker(delegate()
                {
                    this.loadPrcoss1.Visible = true;
                    this.loadPrcoss1.Start("文件加载中");
                }));
                if (File.Exists(tempDocName))
                    File.Delete(tempDocName);
                File.Copy(filePath, tempDocName);
                View(tempDocName);
                this.BeginInvoke(new MethodInvoker(delegate()
                {
                    this.loadPrcoss1.Visible = false;
                    this.loadPrcoss1.End();
                    //this.IsEnableButton(true);
                    this.IsEnable(true);
                }));
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        
        private void ShowHtml(string path)
        {
            //this.htmlBrowser.Navigate(path);
            this.htmlBrowser.Load(path);
          
        }

        private void EditWord_Click(object sender, EventArgs e)
        {
            IsEnable(false);
            this.htmlBrowser.Visible = false;
            this.wordFrameBox.Visible = true;
            this.SaveWord.Enabled = true;
            EditWordFunction();
        }
        public void EditWordFunction()
        {
            try
            {
                this.wordFrameBox.Open(filePath, false, "Word.Document", "", "");
                this.wordFrameBox.SetMenuDisplay(1);
                this.wordFrameBox.Titlebar = false;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void OpenPathFile_Click(object sender, EventArgs e)
        {
            Thread thread = new Thread(new ThreadStart(SelectFile));
            thread.SetApartmentState(ApartmentState.STA);
            thread.Start();
           
        }
        private void SelectFile()
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Multiselect = true;
            openFileDialog.Title = "选择word文件";

            openFileDialog.Filter = "word文件(*.docx)|*.docx";
            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                filePath = openFileDialog.FileName;
                if (File.Exists(tempDocName))
                    File.Delete(tempDocName);
                File.Copy(filePath,tempDocName);
                View(tempDocName);
            }
        }

        private void SaveWord_Click(object sender, EventArgs e)
        {
            this.wordFrameBox.Save();
            idcount = startID;
            Thread thread = new Thread(new ThreadStart(delegate()
            {
                OpenFile();
            }));
            thread.SetApartmentState(ApartmentState.STA);
            thread.Start();
            this.htmlBrowser.Visible = true;
            this.wordFrameBox.Visible = false;
            this.SaveWord.Enabled = false;
        }

        private void ViewDoc_Click(object sender, EventArgs e)
        {
            Thread thread = new Thread(new ThreadStart(delegate()
            {
                View(filePath);
            }));
            thread.SetApartmentState(ApartmentState.STA);
            thread.Start();
        }
        /// <summary>
        /// 替换ID并显示html文件内容
        /// </summary>
        /// <param name="tempFilePath"></param>
        private void View(string tempFilePath)
        {
            this.BeginInvoke(new MethodInvoker(delegate()
            {
                this.loadPrcoss1.Visible = true;
                this.loadPrcoss1.Start("正在转换文件");
            }));
            RepalceWord(tempFilePath);
            string tempName = Application.StartupPath + @"\temp2.html";
            if (File.Exists(tempName))
                File.Delete(tempName);
            HtmlHelper.SaveFile(tempFilePath, tempName);
            HtmlHelper htmlhelper = new HtmlHelper(tempName, idcount);
            ShowHtml(tempName);
            //idcount = startID;
            this.BeginInvoke(new MethodInvoker(delegate()
            {
                this.loadPrcoss1.Visible = false;
                this.loadPrcoss1.End();
            }));
        }
        private void RepalceWord()
        {
            RepalceWord("");
        }
        /// <summary>
        /// 替换空格输入点
        /// </summary>
        /// <param name="fileName"></param>
        private void RepalceWord(string fileName)
        {
            if (string.IsNullOrEmpty(fileName))
                fileName = tempDocName;
            WordprocessingDocument doc = WordprocessingDocument.Open(fileName, true);
            Body body = doc.MainDocumentPart.Document.Body;
            int id = WordControlHelper.ReplaceUnderLine(ref body, ref idcount);
            id = WordControlHelper.ReplaceTableSpace(ref body, id);
            idcount = id;
            doc.MainDocumentPart.Document.Save();
            doc.Close();
        }
    
        private void Canle_Create_first_box_Click(object sender, EventArgs e)
        {
            this.panel_createFirst.Visible = false;
        }

        private void First_create_box_Click(object sender, EventArgs e)
        {
            string medicinename = this.Medicine_name_box.SelectedItem.ToString();
            string description = this.Select_ver_box.SelectedItem.ToString();
            if (string.IsNullOrEmpty(medicinename))
            {
                MessageBox.Show("请选择药品");
                return;
            }
            else
            {
                this.Medicine = WordControlHelper.GetMedinice(medicinename, medicines);
                this.medicine.SetNowVersion(description);
                filePath = FilesHelper.DownlondMedicineWord(medicine.MedicineID,medicine.NowVersion.ID);
                startID = SerarchHelper.GetMaxID(medicine.MedicineID);
                WordprocessingDocument doc = WordprocessingDocument.Open(filePath, true);
                old_IdInfo = WordControlHelper.GetAllDocumentID(doc);
                Thread thread = new Thread(new ThreadStart(OpenFile));
                thread.SetApartmentState(ApartmentState.STA);
                thread.Start();
               
                this.panel_createFirst.Visible = false;
            }
        }
        /// <summary>
        /// 大类下拉框选择改变，更新小类下拉框
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void select_first_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.select_secord.Items.Clear();
            string medicinename = this.selcet_medicine.SelectedItem.ToString();
            string firstClassName = this.select_first.SelectedItem.ToString();
            var selectmedicine = WordControlHelper.GetMedinice(medicinename, medicines);
            string mediniceID = selectmedicine.MedicineID;
            string firstID = WordControlHelper.GetFirst(firstClassName, selectmedicine.FirstClass).ID;
            var res = SerarchHelper.GetAllSecord(mediniceID,firstID);
            foreach (var item in res)
            {
                selectmedicine.SecondClass.Add(item);
                this.select_secord.Items.Add(item.Name);
            }
            if (res.Count > 0)
                this.select_secord.SelectedIndex = 0;
            else
                this.select_secord.Text = "";
            this.EditWord.Enabled = true;
        }
      
        private void selcet_medicine_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.select_first.Items.Clear();
            var medicineinfo = WordControlHelper.GetMedinice(this.selcet_medicine.SelectedItem.ToString(), medicines);
            var firstInfos = SerarchHelper.GetAllFirstClassByMedicine(medicineinfo.MedicineID);
            foreach (var item in firstInfos)
            {
                medicineinfo.FirstClass.Add(item);
                this.select_first.Items.Add(item.Name);
            }
            if (firstInfos.Count > 0)
                this.select_first.SelectedIndex = 0;
            else
                this.select_first.Text = "";

        }
        /// <summary>
        /// 编辑小类的点击事件
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void uplond_secord_Click(object sender, EventArgs e)
        {
            string medicinename = this.selcet_medicine.SelectedItem.ToString();
            string firstClassName = this.select_first.SelectedItem.ToString();
            string secordName = this.select_secord.Text.Trim();
            if (string.IsNullOrEmpty(medicinename))
            {
                MessageBox.Show("请选择药品");
                return;
            }
            else if (string.IsNullOrEmpty(firstClassName))
            {
                MessageBox.Show("请选择大类");
                return;
            }
            else if (string.IsNullOrEmpty(secordName))
            {
                MessageBox.Show("请选择小类");
                return;
            }
            var selectmedicine = WordControlHelper.GetMedinice(medicinename, medicines);
            string mediniceID = selectmedicine.MedicineID;
            string firstID = WordControlHelper.GetFirst(firstClassName, selectmedicine.FirstClass).ID;
            string secordID = WordControlHelper.GetSecord(secordName, selectmedicine.SecondClass).ID;
            string downFileName = FilesHelper.DownlondSecordWord(mediniceID,firstID,secordID);
            startID = SerarchHelper.GetMaxID(mediniceID);
            this.Medicine = selectmedicine;
            this.Medicine.UplondInfo.Type = UplondType.Secord;
            this.Medicine.UplondInfo.UplondFirstID = firstID;
            this.Medicine.UplondInfo.UpSecordID = secordID;
            filePath = downFileName;
            Thread thread = new Thread(new ThreadStart(OpenFile));
            thread.SetApartmentState(ApartmentState.STA);
            thread.Start();
            this.panel_uplond_secord.Visible = false;
        }

        private void splitAndUplond_Click(object sender, EventArgs e)
        {
            Thread thread = new Thread(new ThreadStart(SplitAndUplond));
            thread.SetApartmentState(ApartmentState.STA);
            thread.Start();
        }
        /// <summary>
        /// 文档拆分，转化，上传
        /// </summary>
        private void SplitAndUplond()
        {
            try
            {
                //拆分word生成html
                IsEnable(false);

                if (!medicine.IsAlreadyCreate || SerarchHelper.GetAllFirstClassByMedicine(medicine.MedicineID).Count < 1)
                {

                    string tempFloder = Application.StartupPath + "\\splitFiles";
                    if (Directory.Exists(tempFloder))
                    {
                        FilesHelper.DeleteDirectory(tempFloder);
                    }
                    Directory.CreateDirectory(tempFloder);
                    SplitWord(tempFloder);
                    WordprocessingDocument doc = WordprocessingDocument.Open(medicine.Floder, true);
                    old_IdInfo = WordControlHelper.GetAllDocumentID(doc);
                    FilesHelper.UplondMedicineEffectWord(medicine.MedicineID, medicine.Floder, "首次生成", idcount.ToString());
                    FilesHelper.UplondMedicieWord(medicine.MedicineID, medicine.Floder, "首次生成", idcount.ToString());
                    //创建大类，小类，上传。
                    foreach (FirstInfo first in medicine.FirstClass)
                    {
                        string serverid = CrateHelper.CreateFirstClass(medicine.MedicineID, first.Name);
                        int firstid = -1;
                        if (int.TryParse(serverid, out firstid))
                        {
                            first.ID = firstid.ToString();
                        }
                        else
                            first.ID = SerarchHelper.GetFirstID(medicine.MedicineID, first.Name);
                        foreach (SecordInfo secord in first.Secords)
                        {
                            serverid = CrateHelper.CreateSecordClass(medicine.MedicineID, first.ID, secord.Name);
                            int secordid = -1;
                            if (int.TryParse(serverid, out secordid))
                            {
                                secord.ID = secordid.ToString();
                            }
                            else
                                secord.ID = SerarchHelper.GetSecordID(medicine.MedicineID, first.ID, secord.Name);
                            FilesHelper.UplondSecord(medicine.MedicineID, first.ID, secord.ID, secord.FilePath);
                            FilesHelper.UplondSecordWord(medicine.MedicineID, first.ID, secord.ID, secord.DocxPath);

                        }
                    }
                    medicine.IsAlreadyCreate = true;
                }
                else
                {
                    string message = Interaction.InputBox("请输入修改原因", "输入原因", "", this.Width / 2, this.Height / 2);
                    if (message.Trim().Length < 5)
                    {
                        MessageBox.Show("修改原因，不能低于5个字");
                        IsEnable(true);
                        return;
                    }
                    newaddID.Clear();
                    for (int id = startID; id <= idcount; id++)
                    {
                        newaddID.Add(id.ToString());
                    }
                    string tempFloder = Application.StartupPath + "\\splitFiles";
                    medicine.FirstClass.Clear();
                    medicine.SecondClass.Clear();
                    if (Directory.Exists(tempFloder))
                    {
                        FilesHelper.DeleteDirectory(tempFloder);
                    }
                    Directory.CreateDirectory(tempFloder);
                    SplitWord(tempFloder);
                    this.BeginInvoke(new MethodInvoker(delegate()
                    {
                        this.loadPrcoss1.Visible = true;
                        this.loadPrcoss1.Start("正在校验模板");
                    }));
                    WordprocessingDocument doc = WordprocessingDocument.Open(medicine.Floder, true);
                    var new_idinfo = WordControlHelper.GetAllDocumentID(doc);
                    //先做检查，若发现大类出现新的大类，提示用户不支持,新增或者小类修改出现ID交叉，提示不支持
                    foreach (FirstInfo first in medicine.FirstClass)
                    {
                        first.ID = SerarchHelper.GetFirstID(medicine.MedicineID, first.Name);
                        if (first.ID == string.Empty)
                        {
                            MessageBox.Show("不允许出现新的大类，请检查");
                            IsEnable(true);
                            return;
                        }
                        foreach (SecordInfo secord in first.Secords)
                        {
                            secord.ID = SerarchHelper.GetSecordID(medicine.MedicineID, first.ID, secord.Name);
                            //出现新的小类名字
                            if (secord.ID == string.Empty)
                            {
                                var idnode = WordControlHelper.GetIdNodeByName(secord.Name, new_idinfo);
                                if (idnode != null)
                                {
                                    string secordname = WordControlHelper.GetSecordNameByIdNode(idnode, old_IdInfo, newaddID);
                                    if (secordname == string.Empty)
                                    {
                                        //属于新增的小类
                                        secord.ID = CrateHelper.CreateSecordClass(medicine.MedicineID, first.ID, secord.Name);

                                    }
                                    else if (secordname == "error"||WordControlHelper.IsHasOldName(secordname,first.Secords))
                                    {
                                        MessageBox.Show("小类:" + secord.Name + "与其它小类ID有冲突，请修改");
                                        IsEnable(true);
                                        this.BeginInvoke(new MethodInvoker(delegate()
                                        {
                                            this.loadPrcoss1.Visible = false;
                                            this.loadPrcoss1.End();

                                        }));
                                        return;
                                    }
                                    else
                                    {
                                        secord.ID = SerarchHelper.GetSecordID(medicine.MedicineID, first.ID, secordname);
                                        CrateHelper.ChangeSecordName(medicine.MedicineID, first.ID, secord.ID, secord.Name);
                                    }
                                }
                            }
                        }
                    }
                    this.BeginInvoke(new MethodInvoker(delegate()
                    {
                        this.loadPrcoss1.Start("正在同步模板");
                    }));
                    FilesHelper.UplondMedicineEffectWord(medicine.MedicineID, medicine.Floder, message, idcount.ToString());
                    FilesHelper.UplondMedicieWord(medicine.MedicineID, medicine.Floder, message, idcount.ToString());
                    foreach (FirstInfo first in medicine.FirstClass)
                    {
                        first.ID = SerarchHelper.GetFirstID(medicine.MedicineID, first.Name);
                        foreach (SecordInfo secord in first.Secords)
                        {
                            secord.ID = SerarchHelper.GetSecordID(medicine.MedicineID, first.ID, secord.Name);
                          
                            FilesHelper.UplondSecord(medicine.MedicineID, first.ID, secord.ID, secord.FilePath);
                            FilesHelper.UplondSecordWord(medicine.MedicineID, first.ID, secord.ID, secord.DocxPath);
                        }
                    }
                }
                IsEnable(true);
                this.BeginInvoke(new MethodInvoker(delegate()
                {
                    this.loadPrcoss1.Visible = false;
                    this.loadPrcoss1.End();
                    this.htmlBrowser.Load(StaticString.ConfigUrl);
                }));
            }
            catch (Exception ex)
            {
                IsEnable(true);
                this.BeginInvoke(new MethodInvoker(delegate()
                {
                    this.progressBar1.Visible = false;
                    this.loadPrcoss1.Visible = false;
                    this.loadPrcoss1.End();
                }));
                MessageBox.Show(ex.Message);
            }
        }
        private void splitAndSave_Click(object sender, EventArgs e)
        {
           
            Thread thread = new Thread(new ThreadStart( SelectPathSplit));
            thread.SetApartmentState(ApartmentState.STA);
            thread.Start();
        }
        private void SelectPathSplit()
        {
            try
            {
                FolderBrowserDialog dialog = new FolderBrowserDialog();
                dialog.Description = "请选择保存路径";
                if (dialog.ShowDialog() == DialogResult.OK)
                {

                    SplitWord(dialog.SelectedPath);

                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        /// <summary>
        /// word拆分成小类，并生成html文档
        /// </summary>
        /// <param name="savePath"></param>
        private void SplitWord(string savePath)
        {
            this.BeginInvoke(new MethodInvoker(delegate()
            {
                this.loadPrcoss1.Visible = true;
                this.loadPrcoss1.Start("正在生成模板");
                this.progressBar1.Visible = true;
            }));
            string originFileName = Path.GetFileName(tempDocName);
            if (File.Exists(savePath + "\\" + originFileName))
                File.Delete(savePath + "\\" + originFileName);
            File.Copy(tempDocName, savePath + "\\" + originFileName);
            RepalceWord(tempDocName);
            WordprocessingDocument doc = WordprocessingDocument.Open(tempDocName, true);
            SplitWord splitWord = new SplitWord(doc, idcount, medicine,this);
            
            Medicine = splitWord.Split(savePath, originFileName);
           
            this.BeginInvoke(new MethodInvoker(delegate()
            {
                this.progressBar1.Visible = false;
                this.loadPrcoss1.Visible = false;
                this.loadPrcoss1.End();
                
            }));
        }
        public ProgressBar Bar
        {
            get { return this.progressBar1; }
        }
        private void SettingTool_Click(object sender, EventArgs e)
        {
            Setting set = new Setting();
            set.Show();
        }
        /// <summary>
        /// 帮助按钮
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Help_Click(object sender, EventArgs e)
        {
            string helpPath = Application.StartupPath + "\\toolsHelp.html";
            //this.htmlBrowser.Navigate(helpPath);
            this.htmlBrowser.Load(helpPath);
        }
        /// <summary>
        /// 生成配置并进入配置界面事件
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Create_But_Click(object sender, EventArgs e)
        {
            if (this.medicine == null)
            {
                MessageBox.Show("当前为选择任何药品，请先选择药品或某个小类！");
                return;
            }
            if (this.medicine.UplondInfo.Type == UplondType.Secord)
            {
                Thread thread = new Thread(new ThreadStart(UplondSecord));
                thread.SetApartmentState(ApartmentState.STA);
                thread.Start();
            }
            else
            {
                Thread thread = new Thread(new ThreadStart(SplitAndUplond));
                thread.SetApartmentState(ApartmentState.STA);
                thread.Start();
            }
           
        }
        /// <summary>
        /// 更新小类
        /// </summary>
        private void UplondSecord()
        {
            try
            {
                this.BeginInvoke(new MethodInvoker(delegate()
                {
                    this.loadPrcoss1.Visible = true;
                    this.loadPrcoss1.Start("正在生成文件");
                }));
                RepalceWord(filePath);
                string tempName = Application.StartupPath + @"\temp2.html";
                if (File.Exists(tempName))
                    File.Delete(tempName);
                HtmlHelper.SaveFile(filePath, tempName);
                HtmlHelper htmlhelper = new HtmlHelper(tempName, idcount);
                idcount = startID;
                FilesHelper.UplondSecord(Medicine.MedicineID, medicine.UplondInfo.UplondFirstID, medicine.UplondInfo.UpSecordID, tempName);
                FilesHelper.UplondSecordWord(Medicine.MedicineID, medicine.UplondInfo.UplondFirstID, medicine.UplondInfo.UpSecordID, filePath);
                this.htmlBrowser.Load(StaticString.ConfigUrl);
                this.BeginInvoke(new MethodInvoker(delegate()
                {
                    this.loadPrcoss1.Visible = false;
                    this.loadPrcoss1.End();
                }));
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private void StartConfig_Click(object sender, EventArgs e)
        {
            Thread thread = new Thread(new ThreadStart(LoadConfig));
            thread.SetApartmentState(ApartmentState.STA);
            thread.Start();
        }
        /// <summary>
        /// 进入配置html页面
        /// </summary>
        private void LoadConfig()
        {
            this.BeginInvoke(new MethodInvoker(delegate()
            {
                this.htmlBrowser.Load(StaticString.ConfigUrl);
            }));
           
        }
        /// <summary>
        /// 按钮可用/不可用控制
        /// </summary>
        /// <param name="enable"></param>
        private void IsEnable(bool enable)
        {
            this.AddMedicine.Enabled = enable;
            this.EditWord.Enabled = enable;
            this.Create_But.Enabled = enable;
            this.StartConfig.Enabled = enable;
            this.EditMedicine.Enabled = enable;
            this.TempSave.Enabled = enable;
            this.selectSecord.Enabled = enable;
            this.selectMedicine.Enabled = enable;
            if (this.medicine != null && this.medicine.UplondInfo.Type == UplondType.Secord)
                this.TempSave.Enabled = false;
        }
        /// <summary>
        /// 窗体大小改变事件，保证一些控件一直处于窗体中间的作用
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ConfigTools_SizeChanged(object sender, EventArgs e)
        {
            int loadx = Width / 2 - 150;
            int loady = (Height - 200) / 2;
            this.loadPrcoss1.Location = new Point(loadx,loady);
            this.panel_createFirst.Location=new Point(loadx,loady);
            this.panel_uplond_secord.Location = new Point(loadx, loady);
            int probary = Height - 70;
            this.progressBar1.Location = new Point(0,probary);
            
        }
        /// <summary>
        /// 药品名称下拉框选项改变事件
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Medicine_name_box_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.Select_ver_box.Items.Clear();
            string medicineName = this.Medicine_name_box.SelectedItem.ToString();
            var medicineinfo = WordControlHelper.GetMedinice(this.Medicine_name_box.SelectedItem.ToString(), medicines);
            var verlist=SerarchHelper.GetVersion(medicineinfo.MedicineID);
            medicineinfo.MedicineVersion = verlist;
            foreach (var ver in verlist)
            {
                this.Select_ver_box.Items.Add(ver.Message);
            }
            if (verlist.Count > 0)
                this.Select_ver_box.SelectedIndex = 0;
        }

        private void EditMedicine_Click(object sender, EventArgs e)
        {
        }
        /// <summary>
        /// 临时保存事件，现已经改为生效按钮，内部实现需重写
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void TempSave_Click(object sender, EventArgs e)
        {
            if (this.medicine == null)
            {
                MessageBox.Show("当前为选择任何药品，请先选择药品或某个小类！");
                return;
            }
            string message = Interaction.InputBox("请输入临时保存原因", "输入原因", "", this.Width / 2, this.Height / 2);
            if (message.Trim().Length < 5)
            {
                MessageBox.Show("修改原因，不能低于5个字");
                return;
            }
            FilesHelper.UplondMedicieWord(medicine.MedicineID,tempDocName,message,idcount.ToString());
            MessageBox.Show("文件已经保存到临时工作区");
        }

        private void ConfigTools_Load(object sender, EventArgs e)
        {

        }
        /// <summary>
        /// 登陆/注销事件
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Login_button_Click(object sender, EventArgs e)
        {
            if (this.Login_button.Text == "登陆")
            {
                Login login = new Login(this);
                login.Show();
                this.Visible = false;
            }
            else
            {
                MessageBoxButtons messButton = MessageBoxButtons.OKCancel;
                DialogResult dr = MessageBox.Show("确定要注销吗?", "注销登陆",messButton);
                if (dr == DialogResult.OK)
                {
                    this.User = null;
                    this.IsEnable(false);
                }
            }
        }

        private void selectSecord_Click(object sender, EventArgs e)
        {
            this.panel_uplond_secord.Visible = true;
            this.selcet_medicine.Items.Clear();
            this.select_first.Items.Clear();
            this.select_secord.Items.Clear();
            medicines = SerarchHelper.GetAllMedicine();
            for (int i = 0; i < medicines.Count; i++)
            {
                this.selcet_medicine.Items.Add(medicines[i].MedicineName);
            }
            this.selcet_medicine.SelectedIndex = 0;
        }

        private void selectMedicine_Click(object sender, EventArgs e)
        {

            this.panel_uplond_secord.Visible = false;
            this.MedicineName_lab.Text = "请选择药品:";
            this.First_create_box.Text = "确认选择";
            this.panel_createFirst.Visible = true;
            this.Medicine_name_box.Items.Clear();
            medicines = SerarchHelper.GetAllMedicine();
            for (int i = 0; i < medicines.Count; i++)
            {
                this.Medicine_name_box.Items.Add(medicines[i].MedicineName);
            }
            this.Medicine_name_box.SelectedIndex = 0;
        }

        private void cancel_uplond_Click(object sender, EventArgs e)
        {
            this.panel_uplond_secord.Visible = false;
        }

      
    }
}
