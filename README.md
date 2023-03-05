# Tema2-POO-RequestsManager

Aceasta tema presupune dezvoltarea unui sistem ce ajuta la digitalizarea proceselor din instituțiile publice, mai exact un
sistem de gestionare si rezolvare a cererilor din primarii. Aceste cereri sunt solutionate de functionarii publici ce lucreaza in cadrul primariei.
Fiecare dintre acestia este specializat pe un anumit tip de utilizator care trimite cererea (clasele Angajat, Elev, Pensionar, EntitateJuridica sau Persoana) si apartine unui birou care se ocupa numai de gestionarea cererilor acelui utilizator.

Pentru a stoca in mod eficient datele despre utilizatori am folosit urmatoarele colectii de date:
- lista de utilizatori este salvata sub forma de ArrayList<>
- fiecare utilizator are doua cozi implementate ca PriorityQueue<>, o coada a cererilor initiate (inca sunt in asteptare) si cea a celor finalizate (solutionate). Am ales aceasta abordare intrucat la fiecare adaugare a unei cereri in sistem, coada cererilor initiate este actualizata, pastrandu-se ierarhia elementelor in functie de data la care au fost trimise cererile si, in cazul datelor egale, in functie de prioritatea lor (am putea spune ca are loc o "inserare ordonata").

Biroul se ocupa cu rezolvarea unui singur tip de cereri si in cadrul lui lucreaza mai multi functionari publici. De aceea, am utilizat
urmatoarele colectii de date:
- lista de functionari publici este implementata ca ArrayList<>
- cererile in asteptare sunt salvate sub forma de PriorityQueue<>. Pentru ca aceste cereri sa fie sortate in functie de prioritatea lor si, in cazul prioritatilor egale in functie de datele lor, am creat o noua clasa ce implementeaza interfata Comparator.

In cadrul acestui program, fiecare cerere este adaugata in sistem dupa ce sunt verificate permisiunile utilizatorului. Spre exemplu, un elev nu poate cere sa ii fie inregistrat venitul salarial, iar un pensionar nu isi poate inlocui carnetul de elev. Daca se doreste retragerea cererii, aceasta este stearsa din coada de cereri initiate ale utilizatorului si, implicit, din memoria biroului care se ocupa cu solutionarea ei. Pentru rezolvarea unei cereri, 
functionarul public care este insarcinat cu aceasta activitate o scoate din coada de cereri initiate si a cererilor din birou, adaugand-o in coada cererilor finalizate.

Toate comenzile primite in program genereaza un feedback care este scris intr-un fisier. De exemplu, se pot afisa cererile care se afla intr-un anumit tip de birou (care se ocupa de cererile angajatilor, elevilor, pensionarilor, persoanelor sau entitatilor juridice), cererile aflate in asteptare (initiate) si cele finalizate (rezolvate).
