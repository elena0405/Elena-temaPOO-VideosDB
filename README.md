# Elena-temaPOO-VideosDB

Pentru fiecare actiune pe care a trebuit sa o implementez la aceasta 
tema am creat o clasa noua. Am mai modificat putin si clasele date, ca sa mai 
adaug campuri pentru a retine date suplimentare si pentru sortari. voi descrie
mai jos cum am implementat fiecare actiune ceruta in cerinta.

COMENZILE

Prima comanda este favorite, pe care am implementat-o in clasa Favorite.
Metoda "de baza" din aceasta clasa este favorite, in care am gasit, mai intai, 
utilizatorul care are numele username dat in input. Ca sa pot adauga un 
videoclip in lisat de videoclipuri favorite a unui utilizator, trebuie ca 
utilizatorul sa fi vizionat acesl videoclip. Daca l-a vizionat, verific daca
a fost deja adaugat in lista de video-uri favorite; daca da, atunci afisez
un mesaj de eroare, iar in caz contrar il adaug si afisez un mesaj de succes.
Daca videoclipul nu a fost vizionat, afisez un mesaj de eroare.

A doua comanda este view, pe care am implementat-o in clasa View. In metoda
viewVideo, identific utilizatorul dat in input. Daca videoclipul a fost deja
vizionat, incrementez numarul de vizionari, iar in caz contrar il adaug in 
istoric. La final afisez un mesaj care specifica numarul de vizionari pentru 
acel videoclip.

A treia comanda este rating, pe care am implementat-o in clasa Rating. In
metoda rating, am identificat utilizatorul dupa nume si am verificat daca 
videoclipul a fost deja vizionat de utilizatorul curent. Daca nu, afisez un mesaj
de eroare, iar in caz contrar verific daca este film sau serial. In fiecare din
cele doua cazuri, verific daca videoclipul a primit rating. Daca da, afisez un
mesaj de eroare, iar in caz contrar pun rating in campul getRatings asociat 
fiecarui videoclip si in campul getRatingsHistory pe care l-am adaugat in clasa
UserInputData.

QUERY-URILE

La comenzile din query pentru videoclipuri am folosit acelasi principiu de
 filtrare in functie de valorile din campul filters din input: in primul if am testat
 daca cele doua campuri aferente videoclipurilor din filters, anume 0 si 1, sunt
 nenule si, daca da, atunci trebuie ca anul aparitiei filmului din filters sa coincida
cu anul aparitiei video-ului, iar lista de genuri din filters trebuie sa fie inclusa
 in lista de genuri a videoclipului. Daca doar unul dintre cele doua campuri este
nenul, atunci trebuie ca acest camp nenul sa fie egal cu campul corespunzator 
din video-ul curent, daca este vorba despre anul aparitiei, sau sa fie continut in 
campul aferent din videoclipul respectiv, daca este vorba despre lista de genuri. 
Daca ambele campuri sunt nule, videoclipul va fi adaugat in lista noua de videoclipuri
 la fel ca in celelalte cazuri. Daca cumva niciunul dintre cele 4 cazuri enumerate 
mai sus nu este indeplinit, atunci videoclipul nu va fi adaugat. Dupa selectarea 
videoclipurilor ce indeplinesc conditiile de filtrare din input, se va face sortarea
listei noi in functie de tipul sortarii cerut tot in input. Apoi, din acesta lista 
sortata se vor alege 1 sau n videoclipuri in functie de cerinta pentru a fi copiate 
titlurile lor in mesajul final. Acesta este scheletul de baza pentru Query videos.

In cadrul actiunii rating, inainte de a aplica acest algoritm, am calculat
rating-ul pentru fiecare videoclip, cu mentiunea faptului ca modul de calcul a 
rating-ului pentru filme este diferit de modul de calcul a rating-ului pentru seriale,
dupa cum este mentionat si in cerinta. Am implementat aceasta actiune in clasa 
RatingQuery.

In cadrul actiunii favorite, a trebuit sa determin numarul de aparitii al unui
videoclip in lista de videoclipuri favorite a tuturor utilizatorilor, dupa care am
adaugat aceste videoclipuri in functie de conditiile din filters si dupa ce le-am sortat
 cu Collections.sort() (metoda implementata in MovieInputData si in SerialInputData)
le-am ales pe primele n. Daca n este mai mare decat numarul de videoclipuri din lista
sortata, il setez la dimensiunea acesteia si afisez toate vieoclipurile din lista.
Aceasta actiune a fost implementata in clasa FavoriteQuery.

La longest am calculat mai intai durata unui serial, care este suma duratelor
sezoanelor sale si am aplicat codul descris mai sus, in clasa LongestQuery.

A patra actiune in cadrul acestui "capitol" este MostViewed, pe care am 
implementat-o in clasa MostViewed. In metoda getNrOfViews am determinat numarul de
vizualizari pentru video-ul cu titlul title si am sortat noua lista de videoclipuri
in functie de campul getNrOfViews adaugat in MoviesInputData si in SerialInputData.
	
Pentru actori, cele 3 actiuni care trebuiau implementate sunt: average, 
awards si filter description. 

Pentru awards, in clasa AwardsQuery, fac o metoda in care verific daca un
actor are toate premiile mentionate in input. In metoda urmatoare calculez numarul
total de premii pentru fiecare actor, iar in metoda awards, care este "nucleul" 
acestei clase, adaug in campul aferent din MovieInputData numarul de premii pentru
actorul curent si sortez noua lista de actori in ordinea precizata in input.

Pentru average, in clasa AverageQuery, calculez rating-ul fiecarui actor, iar
pe actorii care au rating-ul nenul ii adaug intr-o lista noua, pe care o sortez dupa
rating-ul calculat. La final, adaug numele primilor n actori in message.

Pentru filter description, in clasa FilterDescriptionQuery, am implementat
 o metoda care determina daca actorul curent contine toate cuvintele din filters. 
Astfel, mai intai, am transformat toate literele mari din careerDescription in 
litere mici si am folosit o variabila de tipul scanner, ca sa pot desparti mai usor
 cuvintele de restul caracterelor si ca sa pot itera prin ele in acelasi timp. Am 
luat fiecare cuvant din cele date in imput si am verificat daca au un corespondent
 in careerDescription. Daca exista minim un cuvant care nu are corespondent, atunci
 returnez 0; in caz contrar, returnez 1. Dupa ce am selectat astfel actorii, i-am
sortat conform criteriului de sortare precizat in input si i-am afisat.

Pentru users, am avut de implementat o actiune, lucru pe care l-am realizat
in clasa UserQuery. Astfel, am sortat primii n utilizatori in functie de numarul de
rating-uri.

RECOMANDARILE

In clasa StandardRecommendation am implementat actiunea standard, care 
intoarce primul videoclip nevizualizat de utilizator. Astfel, iterez mai intai prin
lista de filme si daca un film a fost vizionat de cel putin un utilizator si
utilizatorul curent nu l-a vazut, returnez titlul acelui film. Daca nu am gasit niciun 
film care sa indeplineasca cele doua conditii spuse anterior, am cautat in 
lisat de seriale pe baza aceluiasi criteriu. Am iterat mai intai in lisat de 
filme deoarece, in baza de date, filmele sunt inaintea serialelor.

In clasa BestUnseenRecommendation am implementat actiunea best unseen, in care
am creat o noua lista de videoclipuri in care adaug video-urile care nu au fost
 vazute de utilizator. Sortez lista descrescator si aleg primul element din lista.
 
In clasa PopularRecommendation, adaug intr-un hashMap numele genului si 
popularitatea acestuia, dupa care sortez acest hashMap folosind o lista numita linked.
Dupa aceea, parcurg genurile in ordinea descrescatoare a popularitatii si il afisez 
pe primul videoclip din cel mai popular gen. Daca utilizatorul a vazut toate video-urile
din acel gen, se trece la urmatorul gen si tot asa.

In clasa FavoriteRecommendation, am determinat numarul de aparitii in 
video-urile favorite ale utilizatorilor pentru fiecare video si l-am adaugat intr-o
lista noua in functie de acest numar de aparitii si de conditia ca utilizatorul sa
nu fi vazut acel video. Am sortat lisat descrescator si am retinut primul video.

In SearchRecommendation, am selectat toate videoclipurile care nu au fost
vizualizate de utilizator si care au genul precizat in input, iar dupa sortare le-am
adaugat in message.

MAIN

In clasa Main, in metoda main, am parcurs cu un for fiecare comanda din input si,
in functie de tipul actiunii am creat cate un obiect din fiecare clasa descrisa mai
sus si am apelat metoda de baza a fiecarei clase, iar rezultatul l-am pus in arrayResult.
