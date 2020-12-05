package hu.maszeksolutions.translatebouncing;

public class Main
{
    public static void main(String[] args)
    {
        JsonHandler handler = new JsonHandler();
        Configuration configuration = handler.configure("configuration.json");

        // Definitely not the greatest way to handle potential errors, but it's better than nothing.
        // I should have started working on this project earlier, and then I wouldn't have to make code like this at 2AM...
        try
        {
            Translator translator = new Translator(configuration.getApikey(), configuration.getServiceUrl());
            System.out.println(translator.translateBouncing(configuration.getText(), configuration.getLanguage(), configuration.getNumberOfTranslations(), translator.getSupportedLanguages()));
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }
}
