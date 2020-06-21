using asp_template.Models;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Web;

namespace asp_template.DataAbstractionLayer
{
    public class DAL
    {
        public MySqlConnection getConnection()
        {
            string myConnectionString;
            myConnectionString = "server=localhost;uid=ubbstudent;pwd=forclasspurposes;database=asp_db;";
            return new MySqlConnection(myConnectionString);

        }

        public User isValidLogin(String username, String password)
        {
            User user = null;


            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();
                MySqlCommand command = new MySqlCommand();

                command.Connection = connection;
                command.CommandText = "select * from users where username=@usernameParam AND password=@passwordParam";
                command.Parameters.AddWithValue("@usernameParam", username);
                command.Parameters.AddWithValue("@passwordParam", password);
                MySqlDataReader myreader = command.ExecuteReader();

                while (myreader.Read())
                {
                    user = new User();
                    user.id = myreader.GetInt32("id");
                    user.username = myreader.GetString("username");
                    user.password = myreader.GetString("password");
                }

            }
            catch (MySqlException ex)
            {
                Debug.WriteLine(ex.Message);
            }

            return user;
        }

        public void SaveAsset(Asset asset)
        {
            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();
                MySqlCommand command = new MySqlCommand();

                command.Connection = connection;
                command.CommandText = "INSERT INTO assets (user_id,name,description,value) VALUES (@userid,@name,@descirption,@value)";
                command.Parameters.AddWithValue("@userid", asset.user_id);
                command.Parameters.AddWithValue("@name", asset.name);
                command.Parameters.AddWithValue("@descirption", asset.description);
                command.Parameters.AddWithValue("@value", asset.value);

                command.ExecuteNonQuery();

            }
            catch (MySqlException ex)
            {
                Debug.WriteLine(ex.Message);
            }
        }

        public List<Asset> getAssetsOfUser(int userId)
        {
            List<Asset> assets = new List<Asset>();
            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();
                MySqlCommand command = new MySqlCommand();

                command.Connection = connection;
                command.CommandText = "select * from assets where user_id=@userid";
                command.Parameters.AddWithValue("@userid", userId);
                MySqlDataReader myreader = command.ExecuteReader();

                while (myreader.Read())
                {
                    Asset asset = new Asset();
                    asset.id = myreader.GetInt32("id");
                    asset.user_id = myreader.GetInt32("user_id");
                    asset.name = myreader.GetString("name");
                    asset.description = myreader.GetString("description");
                    asset.value = myreader.GetInt32("value");
                    assets.Add(asset);
                }

            }
            catch (MySqlException ex)
            {
                Debug.WriteLine(ex.Message);
            }


            return assets;
        }

        public User registerNewUser(string username, string password)
        {
            User user = null;


            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();
                MySqlCommand command = new MySqlCommand();

                command.Connection = connection;
                command.CommandText = "INSERT INTO users (username,password) VALUES (@usernameParam, @passwordParam)";
                command.Parameters.AddWithValue("@usernameParam", username);
                command.Parameters.AddWithValue("@passwordParam", password);
                int result = command.ExecuteNonQuery();
                if(result != 0)
                {
                    command.CommandText = "select * from users where username=@usernameParam AND password=@passwordParam";
                    MySqlDataReader myreader = command.ExecuteReader();

                    while (myreader.Read())
                    {
                        user = new User();
                        user.id = myreader.GetInt32("id");
                        user.username = myreader.GetString("username");
                        user.password = myreader.GetString("password");
                    }

                }

            }
            catch (MySqlException ex)
            {
                Debug.WriteLine(ex.Message);
            }

            return user;
        }
    }
}