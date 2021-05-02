# Ckonsoru

## git
https://github.com/Anthony-Jhoiro/ckonsoru

## Architecture

```
├── README.md
├── ckonsoru.iml
├── class.puml
├── nbactions.xml
├── notes.txt
├── pom.xml
└── src
    └── main
        ├── java
        │   └── com
        │       └── fges
        │           └── ckonsoru
        │               ├── App.java
        │               ├── ConfigLoader.java
        │               ├── data
        │               │   ├── AppointmentRepository.java
        │               │   ├── AvailabilityRepository.java
        │               │   ├── psql
        │               │   │   ├── BDDAppointmentRepository.java
        │               │   │   └── BDDAvaibilityRepository.java
        │               │   └── xml
        │               │       ├── XMLAdapter.java
        │               │       ├── XMLAppointmentRepository.java
        │               │       └── XMLAvailabilityRepository.java
        │               ├── menu
        │               │   └── Menu.java
        │               ├── models
        │               │   ├── Appointment.java
        │               │   ├── Availability.java
        │               │   └── xml
        │               │       ├── XMLAppointment.java
        │               │       ├── XMLAvailability.java
        │               │       └── XMLFriendly.java
        │               ├── usecase
        │               │   ├── ListAppointments.java
        │               │   ├── ListFreeTimeslotsByDate.java
        │               │   ├── RemoveAppointment.java
        │               │   ├── TakeAppointment.java
        │               │   └── UseCase.java
        │               └── xmlbdd
        │                   └── ckonsoru.xml
        └── resources
            └── config.properties
```

Notre architecture se décompose en différents modules comme suit :

1. data : il s'agit des repositories (découpés en 2 dossiers : les repos pour le xml, et les repos pour le postgre) : c'est à dire des classes qui permettent d'effectuer des actions directement sur la base de données

2. usecase : il s'agit de classes permettant chacune d'effectuer une et une seule action. Pour cela, elle utilise les répositories pour manipuler les données, mais effectue les actions plus complexes (un peu comme des controllers). Elle récupères aussi les données de l'utilisateurs (quel jour charger, quel client manipuler...)

3. menu : le module contient une unique classe Menu. Elle gère les choix du joueurs. Lorsque le joueur effectue un choix, la classe menu va appeler la méthode trigger du usecase associé au choix

4. App : la classe App est la classe principale de l'application. C'est elle qui choisis entre postgre et xml en fonction des properties et c'est elle qui crée le menu pour que l'utilisateur puisse effectuer les actions

5. models : dans le module "models" sont rangée toutes les classes correspondant aux informations que l'on manipule. Cela permet de manipuler plus facilement ces données (par exemple pour formater des dates).

## Notre volonté de SOLID
A la base nous voulions créer une application très propre avec le code le plus extensible et lisible possible. Mais avec le temps impartit et la méthode de traitement des données dans le sujet, cela s'est révélé impossible.

Cependant, nous avons tout de même essayé de rendre cette application la plus solide possible :

### Single Responsability

Chacune des classes que nous avons créée ne gère qu'un tout petit périmètre  
On a créé un usecase par action, et une action ne peut être effectué que par son usecase associé

### Open/Closed

Dans le module models, on a créé une classe XMLAppointment et une classe XMLAvaibility qui extends respectivement Appointment et Avaibility afin de rajouter un attribut collectionName pour les base de données en XML au lieu de rajouter cet attribut directement dans les classes de base

### Liskov Substitution

Dans le module data, les classes AvaibilityRepository et AppointmentRepository sont extends par les repository des deux types de persistence des données, c'est un bon exemple de classes qui respecte ce principe.

### Interface Segregation

N'ayant qu'une interface, nous n'avons pas pu mettre en évidence ce principe dans le projet

### Dependency Inversion

Ce principe est géré tout le long du projet. Dans chaque classe, l'ensemble des dependences est passé en paramètre du constructeur de la classe

## Pistes de réfléxion

Les répository pourraient peut être être retravaillés pour être plus petit et mieux respecter le principe de single responsability (en créant plus de petite classe plutôt qu'une classe par table dans la bdd)

Utiliser l'injection de dépendence dans le projet serait une super idée



