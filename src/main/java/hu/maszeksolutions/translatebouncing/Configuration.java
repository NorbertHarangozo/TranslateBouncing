package hu.maszeksolutions.translatebouncing;

import hu.maszeksolutions.translatebouncing.exceptions.InvalidConfiguration;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

public class Configuration
{
    String apikey;
    String serviceUrl;
    String text;
    String language;
    int numberOfTranslations;

    public Configuration(String fileName) throws InvalidConfiguration
    {
        JSONParser parser = new JSONParser();

        try
        {
            Object json = parser.parse(new FileReader(fileName));
            JSONObject configuration = new JSONObject(json.toString());

            String apikey = configuration.getString("apikey");
            String serviceUrl = configuration.getString("serviceUrl");
            String text = configuration.getString("text");
            String language = configuration.getString("language");
            int numberOfTranslations = configuration.getInt("numberOfTranslations");

            this.setApikey(apikey);
            this.setServiceUrl(serviceUrl);
            this.setText(text);
            this.setLanguage(language);
            this.setNumberOfTranslations(numberOfTranslations);
        }
        catch (IOException | ParseException ex)
        {
            throw new InvalidConfiguration("The configuration file cannot be found or it's not a valid json file.");
        }
        catch (JSONException jsonException)
        {
            throw new InvalidConfiguration("Some of the configuration keys are missing or they're set to invalid data types.");
        }
    }

    public String getApikey()
    {
        return apikey;
    }

    public void setApikey(String apikey) throws InvalidConfiguration
    {
        if (apikey.isEmpty() || apikey.trim().length()==0)
            throw new InvalidConfiguration("The apikey property cannot be null or empty.");
        else
            this.apikey = apikey;
    }

    public String getServiceUrl()
    {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) throws InvalidConfiguration
    {
        if (serviceUrl.isEmpty() || serviceUrl.trim().length()==0)
            throw new InvalidConfiguration("The serviceUrl property cannot be null or empty.");
        else
            this.serviceUrl = serviceUrl;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text) throws InvalidConfiguration
    {
        if (text.isEmpty() || text.trim().length()==0)
            throw new InvalidConfiguration("The text property cannot be null or empty.");
        else
            this.text = text;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language) throws InvalidConfiguration
    {
        if (language.isEmpty() || language.trim().length()==0)
            throw new InvalidConfiguration("The language property cannot be null or empty.");
        else
            this.language = language;
    }

    public int getNumberOfTranslations()
    {
        return numberOfTranslations;
    }

    public void setNumberOfTranslations(int numberOfTranslations) throws InvalidConfiguration
    {
        if (numberOfTranslations < 1)
            throw new InvalidConfiguration("The numberOfTranslations property must be 1 or higher.");
        else
            this.numberOfTranslations = numberOfTranslations;
    }
}
