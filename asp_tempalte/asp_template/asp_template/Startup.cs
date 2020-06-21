using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(asp_template.Startup))]
namespace asp_template
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
