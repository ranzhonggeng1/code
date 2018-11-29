using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentConfig.model
{
    /// <summary>
    /// 药品对象
    /// </summary>
    public class MedicineInfo
    {
        /// <summary>
        /// 
        /// </summary>
        private string medicineName;
        /// <summary>
        /// 
        /// </summary>
        private List<FirstInfo> firstClass;
        /// <summary>
        /// 
        /// </summary>
        private List<SecordInfo> secondClass;
        private UplondInfo uplondInfo;
        private bool isalreadyCreate=true;
        private List<MedicineVersion> medicineVersion;
        private MedicineVersion nowVersion;
        public void SetNowVersion(string description)
        {
            foreach (var ver in medicineVersion)
            {
                if (description == ver.Message)
                {
                    nowVersion = ver;
                    break;
                }
            }
        }
        public MedicineVersion NowVersion
        {
            get { return nowVersion; }
        }
        public List<MedicineVersion> MedicineVersion
        {
            get
            {
                if (medicineVersion == null)
                    medicineVersion = new List<MedicineVersion>();
                return medicineVersion;
            }
            set { medicineVersion = value; }
        }
        public bool IsAlreadyCreate
        {
            get { return isalreadyCreate; }
            set { isalreadyCreate = value; }
        }
        public UplondInfo UplondInfo
        {
            get {
                if (uplondInfo == null)
                {
                    uplondInfo = new UplondInfo();
                    uplondInfo.Type = UplondType.Medicine;
                }
                return uplondInfo; }
        }
        private string medicineId;
        private string floder;
        /// <summary>
        /// 药品文档的本地位置
        /// </summary>
        public string Floder
        {
            get { return floder; }
            set { floder = value; }
        }
        /// <summary>
        /// 药品名
        /// </summary>
        public string MedicineName
        {
            get { return medicineName; }
            set { medicineName = value; }
        }
        /// <summary>
        /// 大类
        /// </summary>
        public List<FirstInfo> FirstClass
        {
            get {
                if (firstClass == null)
                    firstClass = new List<FirstInfo>();
                return firstClass;
            }
            set { firstClass = value; }
        }
        /// <summary>
        /// 小类
        /// </summary>
        public List<SecordInfo> SecondClass
        {
            get {
                if (secondClass == null)
                    secondClass = new List<SecordInfo>();
                return secondClass;
            }
            set { secondClass = value; }
        }
        /// <summary>
        /// 药品ID
        /// </summary>
        public string MedicineID
        {
            get { return medicineId; }
            set { medicineId = value; }
        }
    }
    public class UplondInfo
    {
        private UplondType type;
        public UplondType Type
        {
            get { return type; }
            set { type = value; }
        }
        private string upfirstId;
        public string UplondFirstID
        {
            get { return upfirstId; }
            set { upfirstId = value; }
        }
        private string upSecordID;
        public string UpSecordID
        {
            get { return upSecordID; }
            set { upSecordID = value; }
        }
    }
    public enum UplondType
    {
        Medicine,
        Secord
    }
}
