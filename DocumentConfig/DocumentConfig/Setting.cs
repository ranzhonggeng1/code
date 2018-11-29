using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Configuration;
namespace DocumentConfig
{
    public partial class Setting : Form
    {
        private  Configuration config;
        public Setting()
        {
            InitializeComponent();
            Init();
        }
        private void Init()
        {
            config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string username = config.AppSettings.Settings["UserName"].Value;
            string password = config.AppSettings.Settings["Password"].Value;
            string configpath = config.AppSettings.Settings["ConfigPath"].Value;
            this.ip_box.Text = StaticString.serverIp;
            this.userName_box.Text = username;
            this.pwd_box.Text = password;
            this.ConfigPath_textbox.Text = configpath;
        }
        private void IsViewPwd_CheckedChanged(object sender, EventArgs e)
        {
            if (this.IsViewPwd.Checked)
                this.pwd_box.UseSystemPasswordChar = false;
            else
                this.pwd_box.UseSystemPasswordChar = true;
        }

        private void moreSetting_Click(object sender, EventArgs e)
        {
            MoreSetting more = new MoreSetting();
            more.Show();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void setting_ok_Click(object sender, EventArgs e)
        {
            string ip = this.ip_box.Text.Trim();
            string username = this.userName_box.Text.Trim();
            string password = this.pwd_box.Text.Trim();
            config.AppSettings.Settings["serverIp"].Value = ip;
            config.AppSettings.Settings["UserName"].Value=username;
            config.AppSettings.Settings["Password"].Value=password;
            config.AppSettings.Settings["ConfigPath"].Value = this.ConfigPath_textbox.Text.Trim();
            config.Save(ConfigurationSaveMode.Modified);
            ConfigurationManager.RefreshSection("appSettings");
            MessageBox.Show("设置成功，重启软件后生效");
            this.Close();
        }
    }
}
