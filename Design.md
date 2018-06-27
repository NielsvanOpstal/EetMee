# more detailed sketch
![Alt text](https://github.com/nielske31/EetMee/blob/master/doc/design.png)

Google's Firebase zal aan de basis staan van de app. De authentication functie van Firebase zal worden gebruikt om gebruikers
in te laten loggen en te laten registreren. Daarna zal dit account gekoppeld worden aan een profiel wat in een firebase database
zal staan, met daarin een naam, korten bio, rating en reviews. Ook zullen de aanbiedingen van mensen op Firebase komen te staan.
Voor Firebase is gekozen omdat het het mogelijk maakt om online de andere profielen en aanbiedingen te zien en daarop te reageren.

Ook zal de app gebruik maken van google maps. Hiermee is te zien waar het eten op te halen is.

Ook zal ik gebruik maken van de Geocoding API (https://developers.google.com/maps/documentation/geocoding/start) om te bepalen 
wat de coordinaten zijn van de aanbieders zodat de mee-eeters op afstand kunnen sorteren. De response hiervan is in een JSON dus niets bijzonders.

De data zal ook komen vanuit firebase en zal er als volgt uit zien:
![Alt text](https://github.com/nielske31/EetMee/blob/master/doc/Firebasestructuur.png)
