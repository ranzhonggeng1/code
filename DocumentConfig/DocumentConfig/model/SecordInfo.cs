using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentConfig.model
{
    /// <summary>
    /// 小类的实体对象
    /// </summary>
    public class SecordInfo
    {
        private string id;
        private string name;
        private string docxPath;
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
        private string filepath;
        /// <summary>
        /// 文件所在本地的位置
        /// </summary>
        public string FilePath
        {
            get { return filepath; }
            set { filepath = value; }
        }
        private FirstInfo parentfirst;
        public FirstInfo ParentFirst
        {
            get { return parentfirst; }
            set { parentfirst = value; }
        }
        private MedicineInfo parentmedicine;
        public MedicineInfo ParentMedicine
        {
            get { return parentmedicine; }
            set { parentmedicine = value; }
        }
        public string DocxPath
        {
            get { return docxPath; }
            set { docxPath = value; }
        }
    }
}
