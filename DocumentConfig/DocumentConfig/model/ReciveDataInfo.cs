using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentConfig.model
{
    /// <summary>
    /// 接收服务器返回数据的一些通用实体
    /// </summary>
    public class ReciveDataInfo
    {
        private int code;
        private string msg;
        private Dictionary<string, object> data;
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
        public Dictionary<string, object> Data
        {
            get { return data; }
            set { data = value; }
        }
    }
}
