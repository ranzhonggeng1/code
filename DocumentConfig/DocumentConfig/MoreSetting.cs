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
    public partial class MoreSetting : Form
    {
        private Configuration config;
        public MoreSetting()
        {
            InitializeComponent();
            Init();
        }
        private void Init()
        {
            config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            string createMedicine = config.AppSettings.Settings["CreateMedicineUrl"].Value;
            string getFirst = config.AppSettings.Settings["GetFirstClassUrl"].Value;
            string getMedicine = config.AppSettings.Settings["GetAllMedicineUrl"].Value;
            string createFirst = config.AppSettings.Settings["CreateFirstClass"].Value;
            string createSecord = config.AppSettings.Settings["CreateSecordClass"].Value;
            string getSecord = config.AppSettings.Settings["GetAllSecordClass"].Value;
            string uplond = config.AppSettings.Settings["Uplond"].Value;
            this.textBox_addMedicine.Text = createMedicine;
            this.textBox_getFirst.Text = getFirst;
            this.textBox_addfirst.Text = createFirst;
            this.textBox_getMedicine.Text = getMedicine;
            this.textBox_getSecord.Text = getSecord;
            this.textBox_uplond.Text = uplond;
            this.textBox_addSwcord.Text = createSecord;
        }
        private void button1_Click(object sender, EventArgs e)
        {
            this.Dispose();
            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            config.AppSettings.Settings["CreateMedicineUrl"].Value = this.textBox_addMedicine.Text.Trim();
            config.AppSettings.Settings["GetFirstClassUrl"].Value = this.textBox_getFirst.Text.Trim();
            config.AppSettings.Settings["GetAllMedicineUrl"].Value = this.textBox_getMedicine.Text.Trim();
            config.AppSettings.Settings["CreateFirstClass"].Value = this.textBox_addfirst.Text.Trim();
            config.AppSettings.Settings["CreateSecordClass"].Value = this.textBox_addSwcord.Text.Trim();
            config.AppSettings.Settings["GetAllSecordClass"].Value = this.textBox_getSecord.Text.Trim();
            config.AppSettings.Settings["Uplond"].Value = this.textBox_uplond.Text.Trim();
            config.Save(ConfigurationSaveMode.Modified);
            ConfigurationManager.RefreshSection("appSettings");
            MessageBox.Show("设置成功，重启软件后生效");
            this.Dispose();
            this.Close();
        }
    }
}
