package bo.servlets

import groovy.xml.MarkupBuilder

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by jtappe on 7/21/2015.
 */
class TimerServlet extends HttpServlet
{
    private static final long startTime = System.currentTimeMillis();

    private static boolean keepGoing = true;

    protected String getHeadersAsHTMLTable(HttpServletRequest request)
    {
        def msg = "";
        try
        {
            Writer writer = new StringWriter();
            MarkupBuilder builder = new MarkupBuilder(writer);

            builder.table {
                request?.headerNames.each {  header ->
                    tr {
                        td("$header")
                        def val = request?.getHeader(header)
                        td("$val")
                    }
                }
            }
            msg += writer.toString()
        }
        catch (Exception e)
        {}
        return msg
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.headerNames

        long diff = (System.currentTimeMillis() - startTime) / 1000
        def msg = "$diff uptime secconds<br>";

        if(keepGoing)
            msg += getHeadersAsHTMLTable(request)
        else msg="COMPLETE"
        response.outputStream.print(msg)
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        keepGoing = !(Boolean.parseBoolean(request.getHeader("stop")))
    }
}
