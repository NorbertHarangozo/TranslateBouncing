package hu.maszeksolutions.translatebouncing;

public class Configuration
{
    String apikey;
    String serviceUrl;
    String text;
    String language;
    int numberOfTranslations;

    public Configuration()
    { }

    public String getApikey() {
        return apikey;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public String getText() {
        return text;
    }

    public String getLanguage() {
        return language;
    }

    public int getNumberOfTranslations() {
        return numberOfTranslations;
    }
}
