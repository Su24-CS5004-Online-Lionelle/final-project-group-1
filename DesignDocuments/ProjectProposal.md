`Intial Ideas`:

`Adoption Center Main Model`

Dogs as objects with name, breed, age, sex as main fields. Hypoallergenic will be a boolean field that is dependent on breed.

Collection will be a list of Dog objects up for adoption.

https://dogapi.dog/docs/api-v2
We will use this API to get a list of breeds and their details to assign to our dog objects

It will need addDog, removeDog, changeDogAge, methods.
Add setPrice method()

Ready for adoption field, in order to be true, needs to have a price field set.
Image field tied to dog object that is the dog’s picture/optional field.

Main Model Responsibilities:

Philip - API integration and Dog object mapping

Joel -  API integration and Dog object mapping

Dina -  Altering the list to include these methods addDog, removeDog, changeDogAge methods.

Emily - Altering the list to include these methods addDog, removeDog, changeDogAge methods. 

`Adoption Center Controller`:

Parses user input:

Filters and returns inputted field to match what user wants to search whether its breed, age greaterthan/lessthan/equal, sex, hypoallergenic true/false
Sort by breed in alphabetical order or by age or by sex, maybe even name in alphabetical order

Search can search for name, age, breed, male and applies those filters
Only returns dogs ready for adoption

Controller Responsibilities:

Joel & Philip - MainController that reads user input

Dina & Emily - FilterPlanner that filters information from a filter command

`Adoption Center View`:

Website

Home page displays all the fields that can be filtered by. Maybe have the most populated breeds be displayed

Then have a search bar that can take arguments such as Breed: Terrier, Age: >3 that will return a list that links to the pages for all the dogs that match the criteria and those pages will have all the details of the dog, a larger picture, the age, the name, etc.

Everyone will work together on all features.

`Features`: 

Initial: 
1. Graphical User Interface (Can be a website or java swing/awt)
2. Be able to view all items in the collection - in a logical order
3. Be able to build a list of items from the collection
4. Be able to save out that list using a file format we covered in the course such as .xml, .json, or .csv.

Minimum: 
1. Be able to search for items in the collection
2. Be able to sort items in the collection
3. Be able to filter items in the collection
4. Include images for your items


`MERMAID DIAGRAM`
```mermaid
classDiagram
class Dog {
    -id: String
    -name: String
    -breed: Breed
    -age: int
    -sex: char
    -price: double
    -ready: boolean
    -weight: double
    +Dog(id: String, name: String, breed: Breed, age: int, sex: char, price: double, ready: boolean, weight: double)
    +getID(): String
    +getName(): String
    +getBreed(): Breed
    +getAge(): int
    +getSex(): char
    +getPrice(): int
    +getReady(): boolean
    +getWeight(): double
    +changeID(id: String): void
    +changeReady(ready: boolean): void
    +changePrice(price: double): void
    +changeAge(age: int): void
    +changeWeight(weight: double): void
}
class Breed {
    <<Record>>
    -id: String
    -minLifespan: int
    -maxLifespan: int
    -minWeightMale: int
    -maxWeightMale: int
    -minWeightFemale: int
    -maxWeightFemale: int
    -description: String
    -hypoallergenic: boolean
}
class apiUtil {
    -apiUtil()
    +createJsonMap(string: String, object: Breed): void
    +serialize(breed: Breed): String
    +deserialize(string: String): Breed 
}
class AdoptionCenterModel {
    -dogs: Collection~Dog~
    +addDog(dog: Dog): void 
    +removeDog(dog: Dog): void
}
class ACController {
-filtered: Collection~Dog~
-filterType: String
-filterInput: String
+ACController(model: AdoptionCenterModel)
+getFilterType(): String
+getFilterInput(): String
+getFiltered(filter: String, filterType: String): Collection~Dog~
+searchByName(name: String): Collection~Dog~
}
class Website
class ACFilterPlanner {
	-filtered: Stream~Dogs~
	+Planner(dogs: set~Dogs~)
	+filter(filter: String): Stream~Dogs~
	+filter(filter: String, sortOn: String): Stream~Dogs~
	+filter(filter: String, sortOn: String, ascending: boolean): Stream~Dogs~
	+reset(): void
	+filterHelper(string: String): Comparator
}
Dog --> Breed
Breed --> apiUtil
AdoptionCenterModel --> Dog 
ACController --> AdoptionCenterModel
Website --> ACController
ACController --> ACFilterPlanner
ACFilterPlanner --> Dog
```

`Timeline`:

`Model`

July 27th -  API Integration done Philip and Joel
July 28th -  Framework (Dina, Emily) Completed by Sunday July 28
July 29th - Model status meetup.  Is the model complete?

`Controller` 

Completed by July 31  
July 31st - Controller status meetup, do we need to use the flexday for more time on the controller or we can move onto website

`Website`

Homepage 2nd-3rd 
August 2nd - Homepage meetup, is the homepage done, what is the progress

Dog Page details 4-5th
Done by August 5th 
August 6th - Finished website meetup, do we need to use the 7th to finish everything

`Presentation`

August 7th - Build Presentation for website 
August 8th - Build Presentation for website 

August 9th - Give Presentation 
