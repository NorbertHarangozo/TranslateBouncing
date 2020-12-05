package hu.maszeksolutions.translatebouncing;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Paths;

public class JsonHandler
{
    public JsonHandler()
    { }

    public Configuration configure(String fileName)
    {
        Configuration c = new Configuration();

        try
        {
            ObjectMapper mapper = new ObjectMapper();
            c = mapper.readValue(Paths.get(fileName).toFile(), Configuration.class);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return c;
    }
}
