
/**
 * This class represents the solution to exrecise 14 question 1
 * This class will represent one city in a country.
 *
 * @author (Salome Dadouche)
 * @version (31/05/2023)
 */
public class CityNode
{
    private City _city;       //The city
    private CityNode _next;   //Pointer to the next member

    /**
     * A constructor recives a City, and the _next field will be initialized to null.
     * 
     * @param c - a new city 
     */
    public CityNode(City c){
        _city = new City(c);    
        _next = null;            
    }

    /**
     * A constructor that receives a city and an additional member of the CityNode type, and initializes the attributes according to the parameters.
     * 
     * @param c A new city 
     * @param next A CityNode type
     */
    public CityNode (City c, CityNode next){
        _city = new City(c);
        _next = next;
    }

    /** 
     * Copy constructor
     * 
     * @param c A CityNode type
     */
    public CityNode (CityNode c) 
    {
        _city = new City(c._city);
        _next = c._next;
    }

    /**
     * A method that returns a copy of the city in the node.
     * 
     * @return a copy of the city in the node.
     */
    public City getCity(){
        return new City(_city);
    }

    /**
     * A method that returns a pointer to the next node.
     * 
     * @return the next node
     */
    public CityNode getNext(){
        return _next;
    }

    /**
     * A method that receives a city and updates the city attribute in the node.
     * 
     * @param c The city that needs to be updated
     */
    public void setCity(City c){
        _city = new City(c);
    }

    /**
     * A method that receives a pointer and updates the pointer properties to the next node.
     * 
     * @param next The pointer that needs to be updated
     */
    public void setNext(CityNode next){
        _next = next;
    }
}
