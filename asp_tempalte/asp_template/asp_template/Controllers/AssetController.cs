using asp_template.DataAbstractionLayer;
using asp_template.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace asp_template.Controllers
{
    public class AssetController : Controller
    {
        // GET: Asset
        public ActionResult Index()
        {
            if (Session["user"] == null)
                return RedirectToAction("Index", "Login");
            return View("ViewAsset");
        }

        public ActionResult GetUserAssets()
        {
            DAL dal = new DAL();
            int uid = int.Parse(Request.Params["userId"]);
            List<Asset> assetsList = dal.getAssetsOfUser(uid);
            return Json(new { assets = assetsList }, JsonRequestBehavior.AllowGet);

        }
        
        public ActionResult AddAssets()
        {
            DAL dal = new DAL();


            List<Asset> list = JsonConvert.DeserializeObject<List<Asset>>(Request.Params["assets"]);
            foreach (Asset asset in list)
            {
                Debug.WriteLine(asset.ToString());
                dal.SaveAsset(asset);
            }
            return Json(new { });
        }

    }
}