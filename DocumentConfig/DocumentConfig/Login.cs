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
using DocumentConfig.ServerHelper;
using Newtonsoft.Json;
using DocumentConfig.model;
namespace DocumentConfig
{
    public partial class Login : Form
    {
        private User user;
        private ConfigTools parent;
        public User User
        {
            get { return user; }
        }
        public Login(ConfigTools parentfrom)
        {
            InitializeComponent();
            this.ControlBox = false;
            this.parent = parentfrom;
            Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string serverip = config.AppSettings.Settings["serverIp"].Value;
            this.textBox_ip.Text = serverip;
        }
        bool IsEditIp = false;
        private void button1_Click(object sender, EventArgs e)
        {
            parent.Visible = true;
            this.Close();
        }

        private void login_but_Click(object sender, EventArgs e)
        {
            string userName = this.textBox_userName.Text.Trim();
            string password = this.textBox_pwd.Text.Trim();
            string serverIp = this.textBox_ip.Text.Trim();
            if (IsEditIp)
            {
                Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
                config.AppSettings.Settings["serverIp"].Value = serverIp;
                config.Save(ConfigurationSaveMode.Modified);
                ConfigurationManager.RefreshSection("appSettings");
            }
            string login = UserHelper.Login(userName,password);
            if (login == "serverErr")
            {
                MessageBox.Show("服务器连接失败，请检查IP是否正确");
                return;
            }
            else
            {
                ReciveDataInfo recive = JsonConvert.DeserializeObject<ReciveDataInfo>(login);
                if (recive.Code == 200)
                {
                    user = new User();
                    user.Authorization = recive.Data["Authorization"].ToString();
                    user.Name = recive.Data["userName"].ToString();
                    user.UserID = recive.Data["id"].ToString();
                    parent.User = user;
                    this.Close();
                }
                else
                    MessageBox.Show(recive.Msg);
            }

        }

        private void ChangeIp_Click(object sender, EventArgs e)
        {
            this.textBox_ip.Enabled = true;       
        }

        private void textBox_ip_TextChanged(object sender, EventArgs e)
        {
            IsEditIp = true;
        }
    }
}
