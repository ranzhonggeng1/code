using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DocumentFormat.OpenXml;
using DocumentFormat.OpenXml.Packaging;
using DocumentFormat.OpenXml.Wordprocessing;

namespace DocumentConfig.model
{
    internal class XmlNodeAndID
    {
        private OpenXmlElement startElement;
        public OpenXmlElement StartElement
        {
            get { return startElement; }
            set { startElement = value; }
        }
        private OpenXmlElement endElement;
        public OpenXmlElement EndElement
        {
            get { return endElement; }
            set { endElement = value; }
        }
        private int startID;
        public int StartID
        {
            get { return startID; }
            set { startID = value; }
        }
        private int endID;
        public int EndID
        {
            get { return endID; }
            set { endID = value; }
        }
        private List<string> Idlist;
        public List<string> IDList
        {
            get
            {
                if (Idlist == null)
                    Idlist = new List<string>();
                return Idlist;
            }
            set { Idlist = value; }
        }

    }
}
