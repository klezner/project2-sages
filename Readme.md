Podmiana danych do GitHub:
ctr + alt + r
GH_TOKEN_GH_TOKEN
GH_USERNAME_GH_USERNAME

Dodatkowo można w pliku SpotifyDockerApiClient podmienić PATH_TO_LOCAL_M2_FOLDER
aby nie ściągać zależności do mavena przy każdym testowaniu questa
(zależności muszą być jednak lokalnie ściągnięte - czyli trzeba zrobić mvn clean w repo questa, który jest klonowany do kontenera w SpotifyDockerApiClient).