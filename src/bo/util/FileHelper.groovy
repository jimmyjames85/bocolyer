package bo.util

import org.apache.commons.io.IOUtils

/**
 * Created by jim on 7/14/15.
 */
class FileHelper
{
    public static final String FS = System.getProperty("file.separator");

    //File should be in WEB-INF folder
    public static Properties loadPropertiesFile(String fileLoc)
    {
        Properties p = new Properties()
        InputStream is = null;
        try
        {
            String thisDir = "";
            URI uri = FileHelper.class.getClassLoader().getResource(thisDir).toURI();
            File propFile = new File(uri);
            propFile = propFile.getParentFile();
            propFile = new File(propFile,fileLoc);

            is = propFile.newInputStream()
            if(is!=null)
                p.load(is);
        }
        catch (Exception e)
        {
            e.printStackTrace()
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
        return p;
    }


}
