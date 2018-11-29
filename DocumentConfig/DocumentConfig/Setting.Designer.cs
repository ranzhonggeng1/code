namespace DocumentConfig
{
    partial class Setting
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
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.ip_box = new System.Windows.Forms.TextBox();
            this.userName_box = new System.Windows.Forms.TextBox();
            this.pwd_box = new System.Windows.Forms.TextBox();
            this.IsViewPwd = new System.Windows.Forms.CheckBox();
            this.moreSetting = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            this.setting_ok = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.ConfigPath_textbox = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(51, 40);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(65, 12);
            this.label1.TabIndex = 0;
            this.label1.Text = "服务器IP：";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(53, 108);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(53, 12);
            this.label2.TabIndex = 1;
            this.label2.Text = "用户名：";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(53, 141);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(41, 12);
            this.label3.TabIndex = 2;
            this.label3.Text = "密码：";
            // 
            // ip_box
            // 
            this.ip_box.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.ip_box.Location = new System.Drawing.Point(123, 40);
            this.ip_box.Name = "ip_box";
            this.ip_box.Size = new System.Drawing.Size(152, 21);
            this.ip_box.TabIndex = 3;
            // 
            // userName_box
            // 
            this.userName_box.Location = new System.Drawing.Point(123, 105);
            this.userName_box.Name = "userName_box";
            this.userName_box.Size = new System.Drawing.Size(152, 21);
            this.userName_box.TabIndex = 4;
            // 
            // pwd_box
            // 
            this.pwd_box.Location = new System.Drawing.Point(123, 138);
            this.pwd_box.Name = "pwd_box";
            this.pwd_box.Size = new System.Drawing.Size(152, 21);
            this.pwd_box.TabIndex = 5;
            this.pwd_box.UseSystemPasswordChar = true;
            // 
            // IsViewPwd
            // 
            this.IsViewPwd.AutoSize = true;
            this.IsViewPwd.Location = new System.Drawing.Point(291, 141);
            this.IsViewPwd.Name = "IsViewPwd";
            this.IsViewPwd.Size = new System.Drawing.Size(72, 16);
            this.IsViewPwd.TabIndex = 6;
            this.IsViewPwd.Text = "显示密码";
            this.IsViewPwd.UseVisualStyleBackColor = true;
            this.IsViewPwd.CheckedChanged += new System.EventHandler(this.IsViewPwd_CheckedChanged);
            // 
            // moreSetting
            // 
            this.moreSetting.Location = new System.Drawing.Point(231, 169);
            this.moreSetting.Name = "moreSetting";
            this.moreSetting.Size = new System.Drawing.Size(75, 23);
            this.moreSetting.TabIndex = 7;
            this.moreSetting.Text = "更多";
            this.moreSetting.UseVisualStyleBackColor = true;
            this.moreSetting.Click += new System.EventHandler(this.moreSetting_Click);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(75, 233);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 8;
            this.button1.Text = "取消";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // setting_ok
            // 
            this.setting_ok.Location = new System.Drawing.Point(251, 233);
            this.setting_ok.Name = "setting_ok";
            this.setting_ok.Size = new System.Drawing.Size(75, 23);
            this.setting_ok.TabIndex = 9;
            this.setting_ok.Text = "确定";
            this.setting_ok.UseVisualStyleBackColor = true;
            this.setting_ok.Click += new System.EventHandler(this.setting_ok_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(51, 71);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(65, 12);
            this.label4.TabIndex = 10;
            this.label4.Text = "配置地址：";
            // 
            // ConfigPath_textbox
            // 
            this.ConfigPath_textbox.Location = new System.Drawing.Point(123, 68);
            this.ConfigPath_textbox.Name = "ConfigPath_textbox";
            this.ConfigPath_textbox.Size = new System.Drawing.Size(152, 21);
            this.ConfigPath_textbox.TabIndex = 11;
            // 
            // Setting
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(458, 284);
            this.Controls.Add(this.ConfigPath_textbox);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.setting_ok);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.moreSetting);
            this.Controls.Add(this.IsViewPwd);
            this.Controls.Add(this.pwd_box);
            this.Controls.Add(this.userName_box);
            this.Controls.Add(this.ip_box);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Name = "Setting";
            this.Text = "设置";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox ip_box;
        private System.Windows.Forms.TextBox userName_box;
        private System.Windows.Forms.TextBox pwd_box;
        private System.Windows.Forms.CheckBox IsViewPwd;
        private System.Windows.Forms.Button moreSetting;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button setting_ok;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox ConfigPath_textbox;
    }
}