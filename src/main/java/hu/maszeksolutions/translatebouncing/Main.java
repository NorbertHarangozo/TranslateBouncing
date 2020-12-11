package hu.maszeksolutions.translatebouncing;

import hu.maszeksolutions.translatebouncing.exceptions.InvalidConfiguration;
import java.net.UnknownHostException;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            Configuration configuration = new Configuration("configuration.json");
            Translator translator = new Translator(configuration.getApikey(), configuration.getServiceUrl());

            String translatedText = translator.translateBouncing(configuration.getText(), configuration.getLanguage(), configuration.getNumberOfTranslations());
            System.out.println(translatedText);
        }
        catch (InvalidConfiguration invalidConfiguration)
        {
            System.out.println(invalidConfiguration.getMessage());
        }
        catch (RuntimeException ex)
        {
            String reason;

            if (ex.getCause() instanceof UnknownHostException)
            {
                reason = "The server doesn't seem to be reachable. Check your internet connection or the serviceUrl property.";
            }
            else
            {
                reason = ex.getMessage();
            }

            System.out.println(reason);
        }
    }
}
