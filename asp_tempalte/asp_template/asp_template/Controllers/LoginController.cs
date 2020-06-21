using asp_template.DataAbstractionLayer;
using asp_template.Models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Web;
using System.Web.Mvc;

namespace asp_template.Controllers
{
    public class LoginController : Controller
    {
        // GET: Login
        public ActionResult Index()
        {
            ViewBag.errorMessage = "";
            return View("ViewLogin");
        }

        public string Test()
        {
            return "Hello";
        }


        public static string MD5Hash(string text)
        {
            MD5 md5 = new MD5CryptoServiceProvider();

            //compute hash from the bytes of text  
            md5.ComputeHash(ASCIIEncoding.ASCII.GetBytes(text));

            //get hash result after compute it  
            byte[] result = md5.Hash;

            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < result.Length; i++)
            {
                //change it into 2 hexadecimal digits  
                //for each byte  
                strBuilder.Append(result[i].ToString("x2"));
            }

            return strBuilder.ToString();
        }

        public ActionResult Login()
        {
            Debug.WriteLine("Login");
            if(Request.HttpMethod == "POST")
            {
                string username = Request.Params["username"];
                string password = Request.Params["password"];
                Debug.WriteLine(username);
                Debug.WriteLine(password);

                password = MD5Hash(password);


                DAL dal = new DAL();
                User user = dal.isValidLogin(username, password);
                if(user != null)
                {
                    Session["user"] = user;
                    return RedirectToAction("Index", "Asset");
                }

            }
            ViewBag.errorMessage = "Invalid user";
            return View("ViewLogin");
        }

        public ActionResult Logout()
        {
            Debug.WriteLine("Logout");
            Session["user"] = null;
            Session.Abandon();
            ViewBag.errorMessage = "";
            return View("ViewLogin");
        }

    }
}