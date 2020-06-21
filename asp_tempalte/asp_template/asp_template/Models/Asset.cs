using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace asp_template.Models
{
    public class Asset
    {
        public int id { get; set; }
        public int user_id { get; set; }
        public string name { get; set; }
        public string description { get; set; }

        public int value { get; set; }

    }
}