package bo.util

import org.apache.commons.io.IOUtils

/**
 * Created by jim on 7/14/15.
 */
class FileHelper
{
    private static Logger LOG = new Logger(DBAccessor.class);

    public static int getCount()
    {
        return 34;
    }

    public static Properties getDatabaseProperties(String fileLoc)
    {
        Properties p = new Properties()
        InputStream is = null;
        try
        {
            p.load(FileHelper.class.getClassLoader().getResourceAsStream(fileLoc));
        }
        catch (Exception e)
        {
            LOG.debug(e);
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
        return p;
    }


}
