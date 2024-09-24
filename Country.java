package edu.uga.cs1302.quiz;

public class Country {
    private String name;
    private String continent;

    public Country()
    {
        this.name = null;
        this.continent = null;
    }

    public Country( String name, String contient)
    {
        this.name = name;
        this.continent = contient;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }


    @Override
    public String toString()
    {
        return name;
    }
}
