using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentConfig.model
{
    /// <summary>
    /// 服务器返回数据的，对所有药品的接收的对象
    /// </summary>
    class AllMedicineInfo
    {
        private int code;
        private string msg;
        private List<Dictionary<string, string>> data;
        public int Code
        {
            get { return code; }
            set { code = value; }
        }
        public string Msg
        {
            get { return msg; }
            set { msg = value; }
        }
        public List<Dictionary<string, string>> Data
        {
            get { return data; }
            set { data = value; }
        }
    }
}
