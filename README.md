ANLEITUNG ZUM START DER APPLIKATION:

1. Alle SubServerApplication in verschiedenen Shells starten. Warten bis sie gestartet sind (SubServer ready!)

2. Wenn alle SubServer ready sind, MasterServerApplication in einer weiterer Shell starten. Warten bis der MasterServer gestartet ist (MasterServer ready!)

3. Nun können der Client oder das Experiment in einer weiteren Shell gestartet werden.
- Der Client führt nacheinander einen Bloom Filter Join mit der Filterlänge m=5Mio und einen naiven Join durch
-Das Experiment führt 10x für die Filterlängen m in {2^n : n=1,...,27} den Bloom Filter Join durch, sowie 10x den naiven Join. Die Resultate werden als MeasuresDDBS.csv abgespeichert. Ein bestehendes File mit dem Namen wir überschrieben.


In den Shells der jeweiligen Servern kann gesehen werden, was gerade wo ausgeführt wird.
Alternativ zu den Schritten 1+2 kann auch nur  der ApplicationServer gestartet werden. Auch hier muss gewartet werden, bis die Nachricht "Application started!" kommt, bevor der Client oder das Experiment gestartet werden können. Wenn man die Applikation auf diese Weise startet, sieht man alle ausgeprinteten Nachrichten in derselben Shell und sieht nicht, von welchem Server sie kommen.