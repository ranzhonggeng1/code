namespace DocumentConfig
{
    partial class ConfigTools
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ConfigTools));
            this.ribbon1 = new System.Windows.Forms.Ribbon();
            this.MedicineManger = new System.Windows.Forms.RibbonTab();
            this.ribbonPanel1 = new System.Windows.Forms.RibbonPanel();
            this.AddMedicine = new System.Windows.Forms.RibbonButton();
            this.selectMedicine = new System.Windows.Forms.RibbonButton();
            this.selectSecord = new System.Windows.Forms.RibbonButton();
            this.OpenPathFile = new System.Windows.Forms.RibbonButton();
            this.StartConfig = new System.Windows.Forms.RibbonButton();
            this.Editdocx = new System.Windows.Forms.RibbonTab();
            this.ribbonPanel2 = new System.Windows.Forms.RibbonPanel();
            this.EditWord = new System.Windows.Forms.RibbonButton();
            this.SaveWord = new System.Windows.Forms.RibbonButton();
            this.Create_But = new System.Windows.Forms.RibbonButton();
            this.TempSave = new System.Windows.Forms.RibbonButton();
            this.documnetControl = new System.Windows.Forms.RibbonTab();
            this.ribbonPanel3 = new System.Windows.Forms.RibbonPanel();
            this.ViewDoc = new System.Windows.Forms.RibbonButton();
            this.splitAndUplond = new System.Windows.Forms.RibbonButton();
            this.splitAndSave = new System.Windows.Forms.RibbonButton();
            this.EditMedicine = new System.Windows.Forms.RibbonButton();
            this.Setting = new System.Windows.Forms.RibbonTab();
            this.ribbonPanel4 = new System.Windows.Forms.RibbonPanel();
            this.SettingTool = new System.Windows.Forms.RibbonButton();
            this.Help = new System.Windows.Forms.RibbonButton();
            this.htmlBrowser = new CefSharp.WinForms.ChromiumWebBrowser("",null);
            this.MedicineName_lab = new System.Windows.Forms.Label();
            this.panel_createFirst = new System.Windows.Forms.Panel();
            this.Select_ver_box = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.Medicine_name_box = new System.Windows.Forms.ComboBox();
            this.First_create_box = new System.Windows.Forms.Button();
            this.Canle_Create_first_box = new System.Windows.Forms.Button();
            this.panel_uplond_secord = new System.Windows.Forms.Panel();
            this.uplond_secord = new System.Windows.Forms.Button();
            this.cancel_uplond = new System.Windows.Forms.Button();
            this.select_secord = new System.Windows.Forms.ComboBox();
            this.select_first = new System.Windows.Forms.ComboBox();
            this.selcet_medicine = new System.Windows.Forms.ComboBox();
            this.label7 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.ribbonComboBox1 = new System.Windows.Forms.RibbonComboBox();
            this.skinEngine1 = new Sunisoft.IrisSkin.SkinEngine();
            this.wordFrameBox = new AxDSOFramer.AxFramerControl();
            this.progressBar1 = new System.Windows.Forms.ProgressBar();
            this.loadPrcoss1 = new WordTools.LoadPrcoss();
            this.Login_button = new DocumentConfig.RoundButton();
            this.panel_createFirst.SuspendLayout();
            this.panel_uplond_secord.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.wordFrameBox)).BeginInit();
            this.SuspendLayout();
            // 
            // ribbon1
            // 
            this.ribbon1.Font = new System.Drawing.Font("微软雅黑", 9F);
            this.ribbon1.Location = new System.Drawing.Point(0, 0);
            this.ribbon1.Minimized = false;
            this.ribbon1.Name = "ribbon1";
            // 
            // 
            // 
            this.ribbon1.OrbDropDown.BorderRoundness = 8;
            this.ribbon1.OrbDropDown.Location = new System.Drawing.Point(0, 0);
            this.ribbon1.OrbDropDown.Name = "";
            this.ribbon1.OrbDropDown.Size = new System.Drawing.Size(527, 93);
            this.ribbon1.OrbDropDown.TabIndex = 0;
            this.ribbon1.OrbImage = null;
            this.ribbon1.RibbonTabFont = new System.Drawing.Font("Trebuchet MS", 9F);
            this.ribbon1.Size = new System.Drawing.Size(784, 113);
            this.ribbon1.TabIndex = 0;
            this.ribbon1.Tabs.Add(this.MedicineManger);
            this.ribbon1.Tabs.Add(this.Editdocx);
            this.ribbon1.Tabs.Add(this.documnetControl);
            this.ribbon1.Tabs.Add(this.Setting);
            this.ribbon1.TabsMargin = new System.Windows.Forms.Padding(12, 26, 20, 0);
            this.ribbon1.Text = "ribbon1";
            this.ribbon1.ThemeColor = System.Windows.Forms.RibbonTheme.Blue;
            // 
            // MedicineManger
            // 
            this.MedicineManger.Panels.Add(this.ribbonPanel1);
            this.MedicineManger.Text = "药品管理";
            // 
            // ribbonPanel1
            // 
            this.ribbonPanel1.Items.Add(this.AddMedicine);
            this.ribbonPanel1.Items.Add(this.selectMedicine);
            this.ribbonPanel1.Items.Add(this.selectSecord);
            this.ribbonPanel1.Items.Add(this.OpenPathFile);
            this.ribbonPanel1.Items.Add(this.StartConfig);
            this.ribbonPanel1.Text = "";
            // 
            // AddMedicine
            // 
            this.AddMedicine.Image = ((System.Drawing.Image)(resources.GetObject("AddMedicine.Image")));
            this.AddMedicine.SmallImage = ((System.Drawing.Image)(resources.GetObject("AddMedicine.SmallImage")));
            this.AddMedicine.Text = "新增药品";
            this.AddMedicine.Click += new System.EventHandler(this.AddMedicine_Click);
            // 
            // selectMedicine
            // 
            this.selectMedicine.Image = ((System.Drawing.Image)(resources.GetObject("selectMedicine.Image")));
            this.selectMedicine.SmallImage = ((System.Drawing.Image)(resources.GetObject("selectMedicine.SmallImage")));
            this.selectMedicine.Text = "选择药品";
            this.selectMedicine.Click += new System.EventHandler(this.selectMedicine_Click);
            // 
            // selectSecord
            // 
            this.selectSecord.Image = ((System.Drawing.Image)(resources.GetObject("selectSecord.Image")));
            this.selectSecord.SmallImage = ((System.Drawing.Image)(resources.GetObject("selectSecord.SmallImage")));
            this.selectSecord.Text = "选择小类";
            this.selectSecord.Click += new System.EventHandler(this.selectSecord_Click);
            // 
            // OpenPathFile
            // 
            this.OpenPathFile.Image = ((System.Drawing.Image)(resources.GetObject("OpenPathFile.Image")));
            this.OpenPathFile.SmallImage = ((System.Drawing.Image)(resources.GetObject("OpenPathFile.SmallImage")));
            this.OpenPathFile.Text = "打开文件";
            this.OpenPathFile.Visible = false;
            this.OpenPathFile.Click += new System.EventHandler(this.OpenPathFile_Click);
            // 
            // StartConfig
            // 
            this.StartConfig.Image = ((System.Drawing.Image)(resources.GetObject("StartConfig.Image")));
            this.StartConfig.SmallImage = ((System.Drawing.Image)(resources.GetObject("StartConfig.SmallImage")));
            this.StartConfig.Text = "配置药品";
            this.StartConfig.Click += new System.EventHandler(this.StartConfig_Click);
            // 
            // Editdocx
            // 
            this.Editdocx.Panels.Add(this.ribbonPanel2);
            this.Editdocx.Text = "编辑模板";
            // 
            // ribbonPanel2
            // 
            this.ribbonPanel2.Items.Add(this.EditWord);
            this.ribbonPanel2.Items.Add(this.SaveWord);
            this.ribbonPanel2.Items.Add(this.Create_But);
            this.ribbonPanel2.Items.Add(this.TempSave);
            this.ribbonPanel2.Text = null;
            // 
            // EditWord
            // 
            this.EditWord.Image = ((System.Drawing.Image)(resources.GetObject("EditWord.Image")));
            this.EditWord.SmallImage = ((System.Drawing.Image)(resources.GetObject("EditWord.SmallImage")));
            this.EditWord.Text = "编辑";
            this.EditWord.Click += new System.EventHandler(this.EditWord_Click);
            // 
            // SaveWord
            // 
            this.SaveWord.Enabled = false;
            this.SaveWord.Image = ((System.Drawing.Image)(resources.GetObject("SaveWord.Image")));
            this.SaveWord.SmallImage = ((System.Drawing.Image)(resources.GetObject("SaveWord.SmallImage")));
            this.SaveWord.Text = "预览";
            this.SaveWord.Click += new System.EventHandler(this.SaveWord_Click);
            // 
            // Create_But
            // 
            this.Create_But.Image = ((System.Drawing.Image)(resources.GetObject("Create_But.Image")));
            this.Create_But.SmallImage = ((System.Drawing.Image)(resources.GetObject("Create_But.SmallImage")));
            this.Create_But.Text = "配置";
            this.Create_But.Click += new System.EventHandler(this.Create_But_Click);
            // 
            // TempSave
            // 
            this.TempSave.Image = ((System.Drawing.Image)(resources.GetObject("TempSave.Image")));
            this.TempSave.SmallImage = ((System.Drawing.Image)(resources.GetObject("TempSave.SmallImage")));
            this.TempSave.Text = "生效";
            this.TempSave.Click += new System.EventHandler(this.TempSave_Click);
            // 
            // documnetControl
            // 
            this.documnetControl.Panels.Add(this.ribbonPanel3);
            this.documnetControl.Text = "模板管理";
            this.documnetControl.Visible = false;
            // 
            // ribbonPanel3
            // 
            this.ribbonPanel3.Items.Add(this.ViewDoc);
            this.ribbonPanel3.Items.Add(this.splitAndUplond);
            this.ribbonPanel3.Items.Add(this.splitAndSave);
            this.ribbonPanel3.Items.Add(this.EditMedicine);
            this.ribbonPanel3.Text = "";
            // 
            // ViewDoc
            // 
            this.ViewDoc.Image = ((System.Drawing.Image)(resources.GetObject("ViewDoc.Image")));
            this.ViewDoc.SmallImage = ((System.Drawing.Image)(resources.GetObject("ViewDoc.SmallImage")));
            this.ViewDoc.Text = "模板预览";
            this.ViewDoc.Visible = false;
            this.ViewDoc.Click += new System.EventHandler(this.ViewDoc_Click);
            // 
            // splitAndUplond
            // 
            this.splitAndUplond.Image = ((System.Drawing.Image)(resources.GetObject("splitAndUplond.Image")));
            this.splitAndUplond.SmallImage = ((System.Drawing.Image)(resources.GetObject("splitAndUplond.SmallImage")));
            this.splitAndUplond.Text = "一键生成上传";
            this.splitAndUplond.Visible = false;
            this.splitAndUplond.Click += new System.EventHandler(this.splitAndUplond_Click);
            // 
            // splitAndSave
            // 
            this.splitAndSave.Image = ((System.Drawing.Image)(resources.GetObject("splitAndSave.Image")));
            this.splitAndSave.SmallImage = ((System.Drawing.Image)(resources.GetObject("splitAndSave.SmallImage")));
            this.splitAndSave.Text = "一键拆分保存";
            this.splitAndSave.Visible = false;
            this.splitAndSave.Click += new System.EventHandler(this.splitAndSave_Click);
            // 
            // EditMedicine
            // 
            this.EditMedicine.Image = ((System.Drawing.Image)(resources.GetObject("EditMedicine.Image")));
            this.EditMedicine.SmallImage = ((System.Drawing.Image)(resources.GetObject("EditMedicine.SmallImage")));
            this.EditMedicine.Text = "编辑药品";
            this.EditMedicine.Click += new System.EventHandler(this.EditMedicine_Click);
            // 
            // Setting
            // 
            this.Setting.Panels.Add(this.ribbonPanel4);
            this.Setting.Text = "系统设置";
            // 
            // ribbonPanel4
            // 
            this.ribbonPanel4.Items.Add(this.SettingTool);
            this.ribbonPanel4.Items.Add(this.Help);
            this.ribbonPanel4.Text = "";
            // 
            // SettingTool
            // 
            this.SettingTool.Image = ((System.Drawing.Image)(resources.GetObject("SettingTool.Image")));
            this.SettingTool.SmallImage = ((System.Drawing.Image)(resources.GetObject("SettingTool.SmallImage")));
            this.SettingTool.Text = "常用设置";
            this.SettingTool.Click += new System.EventHandler(this.SettingTool_Click);
            // 
            // Help
            // 
            this.Help.Image = ((System.Drawing.Image)(resources.GetObject("Help.Image")));
            this.Help.SmallImage = ((System.Drawing.Image)(resources.GetObject("Help.SmallImage")));
            this.Help.Text = "帮助";
            this.Help.Click += new System.EventHandler(this.Help_Click);
            // 
            // htmlBrowser
            // 
            this.htmlBrowser.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.htmlBrowser.Location = new System.Drawing.Point(0, 100);
            this.htmlBrowser.MinimumSize = new System.Drawing.Size(20, 20);
            this.htmlBrowser.Name = "htmlBrowser";
            this.htmlBrowser.Size = new System.Drawing.Size(784, 462);
            this.htmlBrowser.TabIndex = 1;
            // 
            // MedicineName_lab
            // 
            this.MedicineName_lab.AutoSize = true;
            this.MedicineName_lab.Location = new System.Drawing.Point(16, 15);
            this.MedicineName_lab.Name = "MedicineName_lab";
            this.MedicineName_lab.Size = new System.Drawing.Size(71, 12);
            this.MedicineName_lab.TabIndex = 3;
            this.MedicineName_lab.Text = "当前药品名:";
            // 
            // panel_createFirst
            // 
            this.panel_createFirst.Controls.Add(this.Select_ver_box);
            this.panel_createFirst.Controls.Add(this.label1);
            this.panel_createFirst.Controls.Add(this.Medicine_name_box);
            this.panel_createFirst.Controls.Add(this.First_create_box);
            this.panel_createFirst.Controls.Add(this.Canle_Create_first_box);
            this.panel_createFirst.Controls.Add(this.MedicineName_lab);
            this.panel_createFirst.Location = new System.Drawing.Point(178, 231);
            this.panel_createFirst.Name = "panel_createFirst";
            this.panel_createFirst.Size = new System.Drawing.Size(332, 136);
            this.panel_createFirst.TabIndex = 6;
            this.panel_createFirst.Visible = false;
            // 
            // Select_ver_box
            // 
            this.Select_ver_box.FormattingEnabled = true;
            this.Select_ver_box.Location = new System.Drawing.Point(102, 58);
            this.Select_ver_box.Name = "Select_ver_box";
            this.Select_ver_box.Size = new System.Drawing.Size(146, 20);
            this.Select_ver_box.TabIndex = 11;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(20, 58);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(65, 12);
            this.label1.TabIndex = 10;
            this.label1.Text = "选择版本：";
            // 
            // Medicine_name_box
            // 
            this.Medicine_name_box.FormattingEnabled = true;
            this.Medicine_name_box.Location = new System.Drawing.Point(102, 15);
            this.Medicine_name_box.Name = "Medicine_name_box";
            this.Medicine_name_box.Size = new System.Drawing.Size(146, 20);
            this.Medicine_name_box.TabIndex = 9;
            this.Medicine_name_box.SelectedIndexChanged += new System.EventHandler(this.Medicine_name_box_SelectedIndexChanged);
            // 
            // First_create_box
            // 
            this.First_create_box.Location = new System.Drawing.Point(189, 100);
            this.First_create_box.Name = "First_create_box";
            this.First_create_box.Size = new System.Drawing.Size(75, 23);
            this.First_create_box.TabIndex = 8;
            this.First_create_box.Text = "确定";
            this.First_create_box.UseVisualStyleBackColor = true;
            this.First_create_box.Click += new System.EventHandler(this.First_create_box_Click);
            // 
            // Canle_Create_first_box
            // 
            this.Canle_Create_first_box.Location = new System.Drawing.Point(43, 100);
            this.Canle_Create_first_box.Name = "Canle_Create_first_box";
            this.Canle_Create_first_box.Size = new System.Drawing.Size(75, 23);
            this.Canle_Create_first_box.TabIndex = 7;
            this.Canle_Create_first_box.Text = "取消";
            this.Canle_Create_first_box.UseVisualStyleBackColor = true;
            this.Canle_Create_first_box.Click += new System.EventHandler(this.Canle_Create_first_box_Click);
            // 
            // panel_uplond_secord
            // 
            this.panel_uplond_secord.Controls.Add(this.uplond_secord);
            this.panel_uplond_secord.Controls.Add(this.cancel_uplond);
            this.panel_uplond_secord.Controls.Add(this.select_secord);
            this.panel_uplond_secord.Controls.Add(this.select_first);
            this.panel_uplond_secord.Controls.Add(this.selcet_medicine);
            this.panel_uplond_secord.Controls.Add(this.label7);
            this.panel_uplond_secord.Controls.Add(this.label6);
            this.panel_uplond_secord.Controls.Add(this.label5);
            this.panel_uplond_secord.Location = new System.Drawing.Point(158, 209);
            this.panel_uplond_secord.Name = "panel_uplond_secord";
            this.panel_uplond_secord.Size = new System.Drawing.Size(378, 226);
            this.panel_uplond_secord.TabIndex = 9;
            this.panel_uplond_secord.Visible = false;
            // 
            // uplond_secord
            // 
            this.uplond_secord.Location = new System.Drawing.Point(225, 169);
            this.uplond_secord.Name = "uplond_secord";
            this.uplond_secord.Size = new System.Drawing.Size(75, 23);
            this.uplond_secord.TabIndex = 9;
            this.uplond_secord.Text = "确定";
            this.uplond_secord.UseVisualStyleBackColor = true;
            this.uplond_secord.Click += new System.EventHandler(this.uplond_secord_Click);
            // 
            // cancel_uplond
            // 
            this.cancel_uplond.Location = new System.Drawing.Point(55, 169);
            this.cancel_uplond.Name = "cancel_uplond";
            this.cancel_uplond.Size = new System.Drawing.Size(75, 23);
            this.cancel_uplond.TabIndex = 8;
            this.cancel_uplond.Text = "取消";
            this.cancel_uplond.UseVisualStyleBackColor = true;
            this.cancel_uplond.Click += new System.EventHandler(this.cancel_uplond_Click);
            // 
            // select_secord
            // 
            this.select_secord.FormattingEnabled = true;
            this.select_secord.Location = new System.Drawing.Point(129, 100);
            this.select_secord.Name = "select_secord";
            this.select_secord.Size = new System.Drawing.Size(157, 20);
            this.select_secord.TabIndex = 6;
            // 
            // select_first
            // 
            this.select_first.FormattingEnabled = true;
            this.select_first.Location = new System.Drawing.Point(129, 68);
            this.select_first.Name = "select_first";
            this.select_first.Size = new System.Drawing.Size(157, 20);
            this.select_first.TabIndex = 5;
            this.select_first.SelectedIndexChanged += new System.EventHandler(this.select_first_SelectedIndexChanged);
            // 
            // selcet_medicine
            // 
            this.selcet_medicine.FormattingEnabled = true;
            this.selcet_medicine.Location = new System.Drawing.Point(129, 33);
            this.selcet_medicine.Name = "selcet_medicine";
            this.selcet_medicine.Size = new System.Drawing.Size(157, 20);
            this.selcet_medicine.TabIndex = 4;
            this.selcet_medicine.SelectedIndexChanged += new System.EventHandler(this.selcet_medicine_SelectedIndexChanged);
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(58, 103);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(77, 12);
            this.label7.TabIndex = 2;
            this.label7.Text = "请选择小类：";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(56, 68);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(77, 12);
            this.label6.TabIndex = 1;
            this.label6.Text = "请选择大类：";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(58, 33);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(77, 12);
            this.label5.TabIndex = 0;
            this.label5.Text = "请选择药品：";
            // 
            // ribbonComboBox1
            // 
            this.ribbonComboBox1.TextBoxText = "";
            // 
            // skinEngine1
            // 
            this.skinEngine1.@__DrawButtonFocusRectangle = true;
            this.skinEngine1.DisabledButtonTextColor = System.Drawing.Color.Gray;
            this.skinEngine1.DisabledMenuFontColor = System.Drawing.SystemColors.GrayText;
            this.skinEngine1.InactiveCaptionColor = System.Drawing.SystemColors.InactiveCaptionText;
            this.skinEngine1.SerialNumber = "";
            this.skinEngine1.SkinFile = null;
            // 
            // wordFrameBox
            // 
            this.wordFrameBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.wordFrameBox.Enabled = true;
            this.wordFrameBox.Location = new System.Drawing.Point(0, 100);
            this.wordFrameBox.Name = "wordFrameBox";
            this.wordFrameBox.OcxState = ((System.Windows.Forms.AxHost.State)(resources.GetObject("wordFrameBox.OcxState")));
            this.wordFrameBox.Size = new System.Drawing.Size(784, 462);
            this.wordFrameBox.TabIndex = 2;
            this.wordFrameBox.Visible = false;
            // 
            // progressBar1
            // 
            this.progressBar1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.progressBar1.Location = new System.Drawing.Point(0, 539);
            this.progressBar1.Name = "progressBar1";
            this.progressBar1.Size = new System.Drawing.Size(784, 23);
            this.progressBar1.TabIndex = 11;
            this.progressBar1.Visible = false;
            // 
            // loadPrcoss1
            // 
            this.loadPrcoss1.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.loadPrcoss1.BackColor = System.Drawing.Color.White;
            this.loadPrcoss1.CircleColor = System.Drawing.Color.Red;
            this.loadPrcoss1.CircleSize = 0.8F;
            this.loadPrcoss1.ForeColor = System.Drawing.Color.White;
            this.loadPrcoss1.Location = new System.Drawing.Point(200, 200);
            this.loadPrcoss1.Name = "loadPrcoss1";
            this.loadPrcoss1.Size = new System.Drawing.Size(300, 300);
            this.loadPrcoss1.TabIndex = 10;
            this.loadPrcoss1.Text = "loadPrcoss1";
            this.loadPrcoss1.Visible = false;
            // 
            // Login_button
            // 
            this.Login_button.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(128)))), ((int)(((byte)(128)))), ((int)(((byte)(255)))));
            this.Login_button.FlatAppearance.BorderSize = 0;
            this.Login_button.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.Login_button.Location = new System.Drawing.Point(2, 2);
            this.Login_button.Name = "Login_button";
            this.Login_button.Size = new System.Drawing.Size(40, 40);
            this.Login_button.TabIndex = 13;
            this.Login_button.Text = "登陆";
            this.Login_button.UseVisualStyleBackColor = false;
            this.Login_button.Circle = true;
            this.Login_button.Click += new System.EventHandler(this.Login_button_Click);
            // 
            // ConfigTools
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.ClientSize = new System.Drawing.Size(784, 562);
            this.Controls.Add(this.Login_button);
            this.Controls.Add(this.progressBar1);
            this.Controls.Add(this.ribbon1);
            this.Controls.Add(this.loadPrcoss1);
            this.Controls.Add(this.panel_uplond_secord);
            this.Controls.Add(this.panel_createFirst);
            this.Controls.Add(this.htmlBrowser);
            this.Controls.Add(this.wordFrameBox);
            this.Name = "ConfigTools";
            this.Text = "请登录";
            this.Load += new System.EventHandler(this.ConfigTools_Load);
            this.SizeChanged += new System.EventHandler(this.ConfigTools_SizeChanged);
            this.panel_createFirst.ResumeLayout(false);
            this.panel_createFirst.PerformLayout();
            this.panel_uplond_secord.ResumeLayout(false);
            this.panel_uplond_secord.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.wordFrameBox)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Ribbon ribbon1;
        private System.Windows.Forms.RibbonTab MedicineManger;
        private System.Windows.Forms.RibbonPanel ribbonPanel1;
        private System.Windows.Forms.RibbonButton AddMedicine;
        //private System.Windows.Forms.WebBrowser htmlBrowser;
        private CefSharp.WinForms.ChromiumWebBrowser htmlBrowser;
        private System.Windows.Forms.RibbonButton OpenPathFile;
        private System.Windows.Forms.RibbonTab Editdocx;
        private System.Windows.Forms.RibbonPanel ribbonPanel2;
        private System.Windows.Forms.RibbonButton EditWord;
        private System.Windows.Forms.RibbonTab documnetControl;
        private System.Windows.Forms.RibbonPanel ribbonPanel3;
        private System.Windows.Forms.RibbonButton ViewDoc;
        private System.Windows.Forms.RibbonButton SaveWord;
        private AxDSOFramer.AxFramerControl wordFrameBox;
        private System.Windows.Forms.Label MedicineName_lab;
        private System.Windows.Forms.Panel panel_createFirst;
        private System.Windows.Forms.ComboBox Medicine_name_box;
        private System.Windows.Forms.Button First_create_box;
        private System.Windows.Forms.Button Canle_Create_first_box;
        private System.Windows.Forms.Panel panel_uplond_secord;
        private System.Windows.Forms.Button uplond_secord;
        private System.Windows.Forms.Button cancel_uplond;
        private System.Windows.Forms.ComboBox select_secord;
        private System.Windows.Forms.ComboBox select_first;
        private System.Windows.Forms.ComboBox selcet_medicine;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.RibbonButton splitAndUplond;
        private System.Windows.Forms.RibbonButton splitAndSave;
        private System.Windows.Forms.RibbonComboBox ribbonComboBox1;
        private WordTools.LoadPrcoss loadPrcoss1;
        private Sunisoft.IrisSkin.SkinEngine skinEngine1;
        private System.Windows.Forms.RibbonTab Setting;
        private System.Windows.Forms.RibbonPanel ribbonPanel4;
        private System.Windows.Forms.RibbonButton SettingTool;
        private System.Windows.Forms.RibbonButton Help;
        private System.Windows.Forms.RibbonButton Create_But;
        private System.Windows.Forms.RibbonButton StartConfig;
        private System.Windows.Forms.ProgressBar progressBar1;
        private System.Windows.Forms.ComboBox Select_ver_box;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.RibbonButton EditMedicine;
        private System.Windows.Forms.RibbonButton TempSave;
        private System.Windows.Forms.RibbonButton selectSecord;
        private System.Windows.Forms.RibbonButton selectMedicine;
        private RoundButton Login_button;
    }
}