# dag2
- problemen gehad met mijn gradle files door het implementeren van firebase database en firebase authentication. Nu werkend maar nieuwe error

# dag 3
- Eerste gebruiker geregistreerd! Woohoo
- gek iets met logoutknop, nu beetje ghetto gerepareerd (gefixt)
- Nog doen: errors teruggeven als registreren niet lukt
- Besloten om te wachten met de aanbiedingen te filteren op dieet, het zal niet heel moeilijk zijn maar onhandig om vanaf het begin te implementeren. Ook zal het sorteren van de lijst van aanbiedingen gewoon op afstand zijn in eerste geval.
- Toch gekozen om wel direct te werken met dieet en dit te bewerkstelligen door middel van een abstract met dieetwensen.
- Diet toch een losse class gemaakt, moet nog even de database structuur aanpassen

# dag 4
IDEE: maak het mogelijk om een aanbod te maken voor over een paar dagen en ook te filteren daarop.
- Besloten om de offer database te laten werken met push(). Wel daarom User toegevoegd aan de offer.
- TODO overal: eerst checken of er iets in mAuth staat

# dag 5
- Telefoon kapot. Wordt dus lastig debuggen :(
- Address Class gemaakt. Zit in de Offer class.
- Coordinaten aanvragen werkt! via de Geocoding api
- Moet er een soort geschiedenis in?
- OPLETTEN: dat ik de offers filter op dat de datum nog wel moet komen.
- TOEVOEGEN: dat je kan zien wie er mee eet.
- TODO: zorgen dat mensen zich niet op zichzelf kunnen inschrijven
- Ik begin nu wel heel veel classes te krijgen. Is hier iets aan te doen?
- gefixt met klein dingetje. Veel beter.
- TODO: nog even details scherm fixen

# dag 6
- nieuwe telefoon :)
- coordinaten uit de JSON gekregen! 
- na lang zoeken current location gekregen.

# dag 7
- de map in DetaiActivity werkt! (maps voor android api)
- de coordinaten die ik krijg uit geolocations api zijn niet nauwkeurig genoeg, ik woon schijnbaar op het spoor! Ik moet uitzoeken hoe ik het adress zo kan meegeven dat ik nauwkeurigere coordinaten krijg.
-Het lukt nu om de afstand te bereken.
- Moet ik realtime bijhouden of er nog plek is? als iemand op een offer klikt zegmaar
- Er gaat iets mis met de eaters updaten in de firebase.
