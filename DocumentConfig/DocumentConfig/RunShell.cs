using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DocumentFormat.OpenXml.Wordprocessing;

namespace DocumentConfig
{
    public class RunShell
    {
        public List<Run> runs;
        public string text;
        public RunShell()
        {
            runs = new List<Run>();
            text = "";
        }
    }
}
