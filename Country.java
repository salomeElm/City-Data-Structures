/**
 * This class represents the solution to exrecise 14 question 2.
 * This class represents a country. The representation is done by a list that keeps the list of cities in the country.
 *
 * @author (Salome Dadouche)
 * @version (31/05/2023)
 */
public class Country
{
    CityNode _head;      //the top of the list
    String _countryName; //the county name

    /**
     * A constructor that receives a string of the country name and creates an empty country - 
     * initializes an empty list, and the country name according to what it received as the parameter.
     * 
     * @param countryName The country name
     */
    public Country(String countryName){
        _head = null;
        _countryName = countryName;
    }

    /**
     * Boolean method that adds a city to the country.
     * It recives as parameters all the attributes of the City class. Invalid parameters will be handled according to the rules of the City department.
     * The new city will be entered so that the list is always sorted according to the city's founding date:
     * the oldest city is at the top of the list, the youngest at the end.
     * The method returns true on success and false on failure.
     * It is impossible to assume that the new city is not already in the country, so it should be checked.
     * If it does not exist - it will be added to the list in the appropriate place (as mentioned, according to the date of establishment) and true will be returned.
     * If the city already exists, false will be returned
     * If there is a city in the list with the same founding date as the new city, the city that will be closer to the top of the list
     * is the one whose name will appear in the dictionary before the name of the second city.
     * It can be assumed that there will not be two cities with the same name.
     * 
     * @param name The city name
     * @param day An integer that represent the day of the established date.
     * @param month An integer that represent the month of the established date.
     * @param year An integer that represent the year of the established date.
     * @param xCenter An integer that represent the position across the X-axis of the city center.
     * @param yCenter An integer that represent the position across the Y-axis of the city center.
     * @param xStation An integer that represent the position across the X-axis of the central station.
     * @param yStation An integer that represent the position across the Y-axis of the central station.
     * @param numOfResidents An integer that represent the num of residents in the city.
     * @param numOfNeighborhoods An integer that represent the num of neighborhoods in the city.
     * 
     * @return true if the addition was successful and false otherwise.
     */
    public boolean addCity(String name, int day, int month, int year, int xCenter, int yCenter, int xStation,
    int yStation, long numOfResidents, int numOfNeighborhoods)
    {
        //initialize the new city that we want to add to the list:
        City c = new City(name, day, month, year, xCenter, yCenter, xStation, yStation, numOfResidents, numOfNeighborhoods); //
        CityNode cNode = new CityNode(c, null); //initialize a new CityNode object with the new city that we want to add to the list.
        CityNode current = _head;
        if(current == null) //If the list is empty
        {
            _head = cNode;
            return true;
        }
        //We will check if the new city is in the country:                  
        while(current != null)
        {
            if(current.getCity().equals(cNode.getCity()))
                return false;
            current = current.getNext();
        }
        current = _head;
        //We will add the city in a sorted form                  
        while(current != null)
        { 
            //If the established date of 'cNode' is before the established date of 'current':            
            if(cNode.getCity().getDateEstablished().before(current.getCity().getDateEstablished()))
            {
                addBefore(c, current); //Auxiliary method that adds the city to the list before 'current'.
                return true;
            }
            //If the established date of cNode is after the established date of current and we have reached the last node in the list:                           
            else if(cNode.getCity().getDateEstablished().after(current.getCity().getDateEstablished()) && current.getNext() == null)
            {
                current.setNext(cNode); //initialization the next node of the last node to the new CityNode object with the new city
                return true;
            }
            //If the established date of the 2 cities is the same:                          
            if(current.getCity().getDateEstablished().equals(cNode.getCity().getDateEstablished()))
            {
                if(current.getCity().getCityName().compareTo(cNode.getCity().getCityName()) < 0) //If current is in the dictionary before cNode
                {
                    current.setNext(new CityNode(c , current.getNext())); //Add the new city to the next node of current.
                    return true;
                }
                else //If current is in the dictionary after cNode
                {
                    addBefore(c, current);
                    return true;
                }
            }            
            current = current.getNext();
        }
        return false;
    }

    //Auxiliary method - adds a city before a certain intersection. Assume that the node received as a parameter exists in the list.         
    private void addBefore(City c, CityNode cN){
        if(_head == cN) //if cN is at the top of the list, we will add the city before the top of the list and we will update _head in accordance 
        {
            CityNode before = new CityNode(c, null);
            before.setNext(_head);
            _head = before;
        }
        else
        {
            CityNode current = _head;
            while(current.getNext() != cN)
            {
                current = current.getNext();
            }
            current.setNext(new CityNode(c, current.getNext())); //adds the city before cN (=current.getNext())
        }
    }

    /**
     * A method that returns the number of residents in a country
     * 
     * return the number of residents in a country.
     */        
    public long getNumOfResidents(){
        CityNode current = _head;
        long result = 0; //the number of residents                  
        if(current == null) //If the list is empty
            return 0;
        while(current != null)
        {
            result += current.getCity().getNumOfResidents();
            current = current.getNext();
        }
        return result;
    }

    /**
     * A method that returns the greatest distance between 2 cities in a country.
     * If the number of cities in the country is less than 2 - it will be returned 0.
     * 
     * return the greatest distance between 2 cities in a country.
     */        
    public double longestDistance(){        
        if(getNumOfCities() < 2) //Checks if the number of cities is less than 2
            return 0;
        CityNode current = _head;
        CityNode currentNext = current.getNext();
        double dis, longestDis = 0;        
        //Checks the greatest distance between 2 cities:                  
        while(current.getNext() != null)
        {
            while(currentNext != null)
            {
                dis = current.getCity().getCityCenter().distance(currentNext.getCity().getCityCenter()); // the distance between 2 cities
                if(dis > longestDis)
                    longestDis = dis;
                currentNext = currentNext.getNext();  
            }
            current = current.getNext();
            currentNext = current.getNext();
        }
        return longestDis;
    }

    /**
     * A method that returns the number of cities north of the city received as a parameter.
     * If the name of the city is not found in the country, it will be returned -1.
     * If there are no cities north of the requested city, it will be returned 0.
     * 
     * @param cityName City name
     * @return the number of cities north of the city received as a parameter
     */
    public int numCitiesNorthOf(String cityName){
        CityNode current = _head;                 
        if(current == null) //If the list is empty, -1 will be returned because the city name is not found in the country
            return -1;
        Point thisCityCenter = new Point(current.getCity().getCityCenter()); //initialization the point to be currently the city center of the head of the list
        int count = 0; //The number of cities north of the city received as a parameter
        boolean b = false; //a flag
        //Checks if the name of the city is found in the country (if not returns -1)
        //and if found then initializes the point thisCityCenter to be the point of the center of the city whose name was received as a parameter                  
        while(current != null && b == false)
        {
            if(current.getCity().getCityName().equals(cityName))
            {
                b = true;
                thisCityCenter = current.getCity().getCityCenter();
            }
            if(current.getNext() == null &&  b == false)
                return -1;
            current = current.getNext();
        }
        current = _head;
        //Count the number of cities whose center is north of thisCityCenter:        
        while(current != null)
        {
            if(current.getCity().getCityCenter().isAbove(thisCityCenter))
                count++;
            current = current.getNext();
        }
        return count;
    }

    /**
     * A method that returns the southernmost city in the country (considered to their city center). 
     * if there is none states in the country it will return null.
     * If there is more than one city that is the southernmost, this one will be returned whose founding date is earlier.
     * 
     * @return the southernmost city in the country (considered to their city center). 
     */         
    public City southernmostCity(){
        CityNode current = _head;                  
        if(current == null) //If there are no cities in the country
            return null;
        City souther = new City(current.getCity()); //initializes the southernmost city to be currently the top of the list
        while(current != null) 
        {
            //If the city center of 'current' is further south than the city center of 'souther' then we initialize 'current' to be the southernmost city                            
            if(current.getCity().getCityCenter().isUnder(souther.getCityCenter()))
            {
                souther = current.getCity();
            }
            current = current.getNext();
        }
        return souther;
    }

    /**
     * A method that returns a String with the name of the country
     * 
     * @return the name of the country.
     */         
    public String getCountryName(){
        return _countryName;
    }

    /**
     * A method that returns the number of cities in the country
     * 
     * @return the number of cities in the country
     */         
    public int getNumOfCities(){
        CityNode current = _head;
        int count = 0; //the number of cities in the country
        if(current == null) //If there are no cities in the country
            return 0;
        while(current != null)
        {
            count++;
            current = current.getNext();
        }
        return count;
    }

    /**
     * The method recives two dates (it is not known which one is earlier) and returns true if:
     * one of the cities was founded just before the earlier of the dates or just after the later
     * 
     * @param date1 Date object 
     * @param date2 Date object
     * @return true if one of the cities was founded just before the earlier of the dates or just after the later
     */
    public boolean wereCitiesEstablishedBeforeOrAfter(Date date1, Date date2){
        CityNode current = _head;
        if(current == null) //If there are no cities in the country
            return false;
        //For now we assume that date2 is earlier than date1        
        Date earlier = new Date (date2); 
        Date later = new Date (date1);
        //But if date1 is earlier than date2:        
        if(date1.before(date2))
        {
            earlier = new Date (date1);
            later = new Date (date2);
        }
        //else If the 2 dates are equal:                 
        else if(date2.equals(date1))
        {
            earlier = new Date (later);
            later = new Date (earlier);
        }
        while(current != null) //Goes through all the cities on the list and checks if one of them meets the conditions
        {
            if(current.getCity().getDateEstablished().before(earlier) || current.getCity().getDateEstablished().after(later))
                return true;
            current = current.getNext();
        }
        return false;
    }

    /**
     * The method recives two city names, merges them into one city and returns the merged city.
     * The name of the united city will be "city1-city2",
     * The date of the establishment of the city will be the earlier of the two dates of the establishment of the two cities,
     * the number of residents in the united city is the sum of the numbers of residents,
     * the number of neighborhoods in the united city is the sum of the numbers of the neighborhoods,
     * the location of the center of the new city is halfway between the two city centers,
     * and the location of the united central station is at the western station more of the two,
     * if the two central stations have the same x, the younger cityw will be chose.
     * The method will remove the younger city from the list and update the older city to the unified city.
     * 
     * @param cityName1 A string that represent the name of one of the city that need to be unified.
     * @param cityName2 A string that represent the name of the second city that need to be unified.
     * @return the merged city.
     * 
     */
    public City unifyCities(String cityName1, String cityName2){
        CityNode current = _head;
        //We will currently define 2 new cities to be the city at the top of the list                 
        City city1 = new City(current.getCity());
        City city2 = new City(current.getCity());
        while(current!=null) 
        {
            if(current.getCity().getCityName().equals(cityName1))
                city1 = new City(current.getCity()); //Defines 'city1' with the attributes of the city whose name is the same as its own
            if(current.getCity().getCityName().equals(cityName2))
                city2 = new City(current.getCity()); //Defines 'city2' with the attributes of the city whose name is the same as its own
            current = current.getNext();
        }

        //Define the United City features:                 
        String newCityName = cityName1 + "-" + cityName2;
        Date newDateEstablished = new Date(city1.getDateEstablished()); //Defining the date of the unified city to be the date of establishment of 'city1'
        Date youngDateEstablished = new Date(city2.getDateEstablished()); //and defining the date of the younger city to be the date of the establishment of 'city2' 
        //If the founding date of 'city2' is earlier than 'city1',
        // then we will set the new date to be that of 'city2' and the date of the younger city to be that of 'city1'                  
        if(city2.getDateEstablished().before(city1.getDateEstablished()))
        {
            newDateEstablished = new Date(city2.getDateEstablished());
            youngDateEstablished = new Date(city1.getDateEstablished());
        }
        long newNumOfResidents = city1.getNumOfResidents() + city2.getNumOfResidents();
        int newNumOfNeighborhoods = city1.getNumOfNeighborhoods() + city2.getNumOfNeighborhoods();
        Point city1Center = new Point(city1.getCityCenter());
        Point city2Center = new Point(city2.getCityCenter());
        Point newCityCenter = new Point (city1Center.middle(city2Center));
        Point city1Station = new Point(city1.getCentralStation());
        Point city2Station = new Point(city2.getCentralStation());
        Point newStation = new Point(city1Station);//Setting now the location of the new central station to be the location of city1
        //If the central station location of 'city2' is more left than 'city1' then we initialize the new central station location to be the location of 'city2'                
        if(city2Station.isLeft(city1Station))
            newStation = new Point(city2Station);
        //If both central stations have the same X the new station will be that of the youngest city                  
        else if(city2Station.getX() == city1Station.getX())
        {
            if(youngDateEstablished == city1.getDateEstablished())
                newStation = new Point(city1Station);
            else 
                newStation = new Point(city2Station);
        }

        ////Define the United City:                  
        City newCity = new City(newCityName , newDateEstablished.getDay(),newDateEstablished.getMonth(),newDateEstablished.getYear(),
                newCityCenter.getX(), newCityCenter.getY(), newStation.getX(), newStation.getY(), newNumOfResidents, newNumOfNeighborhoods);   

        current = _head;
        //Replacing the values​of the older city with the values of the unified city and removing the younger city from the list.                  
        //We will note that the list is sorted by the city's founding date,
        //so first we will change the values of the old city to the values of the united city and then we will delete the younger city from the list.                  
        while(current.getNext() != null)
        {
            //If the date of 'current' is the same as the date of the older city, we will replace the values of the old city with the values of the unified city                          
            if(current.getCity().getDateEstablished().equals(newDateEstablished))
                current.setCity(newCity);
            //If the date of 'current' is the same as the date of the youngest city, we will remove this city from the list                           
            if(current.getNext().getCity().getDateEstablished().equals(youngDateEstablished))
                current.setNext(current.getNext().getNext());
            else    
                current = current.getNext();
        }                 

        return newCity;
    }

    /**
     * The method returns the largest difference in days between the founding dates of two cities in the country.
     * If the country has no cities, the value will be returned is -1.
     * If the country has one city, the value will be returned is 0;
     * 
     * @return the largest difference in days between the founding dates of two cities in the country.
     */        
    public int establishMaxDiff(){
        CityNode current = _head;
        //if there are no cities in the country                  
        if(current == null)
            return -1;
        //If there is one city                  
        if(current.getNext() == null)
            return 0;
        //Advances 'current' until it is the last element in the list                  
        while(current.getNext() != null)
        {
            current = current.getNext();
        }
        //The list is sorted, so we will return the difference in days between the first city (head_) and the last city (current)        
        return current.getCity().getDateEstablished().difference(_head.getCity().getDateEstablished());
    }

    /**
     * The method returns a string containing the information about all the cities in the country.
     * 
     * return a string containing the information about all the cities in the country.
     */
    public String toString(){
        CityNode current = _head;
        //if there are no cities in the country                 
        if(current == null)
            return "There are no cities in this country.";
        String s = "";
        while(current != null)
        {
            s += " \n" + current.getCity().toString() + " \n";
            current = current.getNext();
        }
        return "Cities of " + getCountryName() + " :\n" + s;
    }
}

