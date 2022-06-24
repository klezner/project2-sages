Podmiana danych do GitHub:
ctr + shift + r
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 oraz 
bartmj

G#H_TOKEN_GH_TOKEN
G#H_USERNAME_GH_USERNAME
Dodatkowo można w pliku SpotifyDockerApiClient podmienić PATH_TO_LOCAL_M2_FOLDER
aby nie ściągać zależności do mavena przy każdym testowaniu rozwiązania questa
(zależności muszą być jednak lokalnie ściągnięte - czyli trzeba zrobić mvn clean w repo questa, który jest klonowany do kontenera w SpotifyDockerApiClient).
