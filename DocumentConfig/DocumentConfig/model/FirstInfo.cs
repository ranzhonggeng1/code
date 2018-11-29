using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentConfig.model
{
    /// <summary>
    /// 大类对象的实体
    /// </summary>
    public class FirstInfo
    {
        private string id;
        private string name;
        /// <summary>
        /// ID
        /// </summary>
        public string ID
        {
            get { return id; }
            set { id = value; }
        }
        /// <summary>
        /// 名字
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }
        private string floder;
        /// <summary>
        /// 大类文件夹地址
        /// </summary>
        public string Floder
        {
            get { return floder; }
            set { floder = value; }
        }
        private List<SecordInfo> secords;
        /// <summary>
        /// 大类下面所包含的小类
        /// </summary>
        public List<SecordInfo> Secords
        {
            get {
                if (secords == null)
                    secords = new List<SecordInfo>();
                return secords;
            }
            set { secords = value; }
        }
        private MedicineInfo parentmdicine;
        public MedicineInfo ParentMedicine
        {
            get { return parentmdicine; }
            set { parentmdicine = value; }
        }
    }
}
