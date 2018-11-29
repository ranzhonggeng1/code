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
using System.Net;
using System.Configuration;
using System.Web.Script.Serialization;
using DocumentConfig.model;
using Newtonsoft.Json;
using System.Threading;
namespace DocumentConfig
{
    public partial class AddMedicineFrom : Form
    {
        string filePath = "";
        private ConfigTools parent;
        private bool createSeccuss = false;
        
        public AddMedicineFrom(ConfigTools parentFrom)
        {
            InitializeComponent();
            parent = parentFrom;
        }

        private void select_path_Click(object sender, EventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Multiselect = true;
            openFileDialog.Title = "选择word文件";
           
            openFileDialog.Filter = "word文件(*.docx)|*.docx";
            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                this.documnet_pathbox.Text = openFileDialog.FileName;
            }
        }

        private void close_but_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void cretae_but_Click(object sender, EventArgs e)
        {
            string mecdicineName = this.name_box.Text.Trim();
            string factoryName = this.func_box.Text.Trim();
            filePath = this.documnet_pathbox.Text.Trim();
            if (string.IsNullOrEmpty(mecdicineName))
            {
                MessageBox.Show("请输入药品名称");
                return;
            }
            else if (string.IsNullOrEmpty(factoryName))
            {
                MessageBox.Show("请输入工厂信息");
                return;
            }
            else if (!File.Exists(filePath))
            {
                MessageBox.Show("模板不存在，请选择正确的路径");
                return;
            }
            else
            {
                Configuration config = System.Configuration.ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
                string addUrl = config.AppSettings.Settings["CreateMedicineUrl"].Value;
                string url = "http://" + StaticString.serverIp + addUrl;
                WebRequest httpRquest = System.Net.HttpWebRequest.Create(url);
                httpRquest.Method = "POST";
                httpRquest.ContentType = "application/json";
                string jsonText = "{\"factory\":"+"\""+factoryName+ "\",\"name\": \""+mecdicineName+"\"}";
                string postData = "factory=" + factoryName+"&"+"name="+mecdicineName;
                using (var streamWriter = new StreamWriter(httpRquest.GetRequestStream()))
                {
                    string json = new JavaScriptSerializer().Serialize(new
                    {
                        factory =factoryName,
                        name = mecdicineName
                    });

                    streamWriter.Write(json);
                }
                var response = (HttpWebResponse)httpRquest.GetResponse();
                using (var streamReader = new StreamReader(response.GetResponseStream()))
                {
                    string result = streamReader.ReadToEnd();
                    MedicineInfo medinice = new MedicineInfo();
                    ReciveDataInfo recive = JsonConvert.DeserializeObject<ReciveDataInfo>(result);
                    if (recive.Code == 200)
                    {
                        createSeccuss = true;
                        medinice.MedicineName = mecdicineName;
                        medinice.MedicineID = recive.Data["id"].ToString();
                        medinice.IsAlreadyCreate = false;
                        parent.Medicine = medinice;
                    }
                }
                if (createSeccuss)
                {
                    parent.FilePath = filePath;
                    Thread thread = new Thread(new ThreadStart(parent.OpenFile));
                    thread.SetApartmentState(ApartmentState.STA);
                    thread.Start();
                    this.Close();
                }
            }
        }
    }
}
