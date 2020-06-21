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
    public class RegisterController : Controller
    {
        // GET: Register
        public ActionResult Index()
        {
            ViewBag.errorMessage = "";
            return View("ViewRegister");
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


        public ActionResult Register()
        {
            Debug.WriteLine("Hello");
            if (Request.HttpMethod == "POST")
            {
                string username = Request.Params["username"];
                string password = Request.Params["password"];
                string passwordrepeat = Request.Params["password-repeat"];
                Debug.WriteLine(username);
                Debug.WriteLine(password);
                Debug.WriteLine(passwordrepeat);
                if(password != passwordrepeat)
                {
                    ViewBag.errorMessage = "Passwords must be the same";
                    return View("ViewRegister");
                }
                password = MD5Hash(password);


                DAL dal = new DAL();
                User user = dal.registerNewUser(username, password);
                if (user != null)
                {
                    Session["user"] = user;
                    return RedirectToAction("Index", "Asset");
                }
                ViewBag.errorMessage = "Username already in use";
                return View("ViewRegister");

            }
            ViewBag.errorMessage = "Invalid request method";
            return View("ViewRegister");

        }
    }
}