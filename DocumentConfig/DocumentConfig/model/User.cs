using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentConfig.model
{
    /// <summary>
    /// 用户实体
    /// </summary>
    public class User
    {
        private string name;
        public string Name
        {
            get { return name; }
            set { name = value; }
        }
        private string userid;
        public string UserID
        {
            get { return userid; }
            set { userid = value; }
        }
        private string authorization;
        public string Authorization
        {
            get { return authorization; }
            set { authorization = value; }
        }
    }
}
