using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentConfig.model
{
    /// <summary>
    /// 药品版本
    /// </summary>
    public class MedicineVersion
    {
        private string id;
        /// <summary>
        /// 版本ID
        /// </summary>
        public string ID
        {
            get { return id; }
            set { id = value; }
        }
        private string message;
        /// <summary>
        /// 修改原因 
        /// </summary>
        public string Message
        {
            get { return message; }
            set { message = value; }
        }
    }
}
