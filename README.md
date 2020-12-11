# TranslateBouncing

This application is using IBM's Watson cloud service to translate text from a given language to random languages a set amount
of times, and then translate it back to the original language. The API seems to have some limitations when it comes to translating
from one language to another. For this reason, we're translating the text to English (in case it's not in English already), and then
translate it to the target language.

This project serves as my assignment for the *Multiparadigm programming languages* class in *Eszterházy Károly University*.

To get started, clone the repo and open it in your favorite IDE.  
**Rename configuration-example.json** to **configuration.json**.  
**Make sure to enter your apikey and serviceUrl**, if you don't recall having any of these things, you can sign up for free [here](https://www.ibm.com/watson/services/language-translator/).
