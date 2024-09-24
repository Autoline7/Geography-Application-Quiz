package edu.uga.cs1302.quiz;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CountryCollection {
    private List<Country> countryList;
    private List<Country> countryContinent;

    private int size;

    public CountryCollection() {
        this.countryList = new ArrayList<Country>();
        size = 0;
            String filePath = "country_continent.csv";
            try {
                InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
                if (is == null) {
                    throw new IOException("Resource not found: " + filePath);
                }
                Reader in = new BufferedReader(new InputStreamReader( is ));
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);

                for (CSVRecord record : records) {
                    if (size > 0) { // skip row 0 -- headings
                        String name = record.get(0);
                        String continent = record.get(1);
                        Country country = new Country(name, continent);
                        countryList.add(country);
                    }
                    size++;
                }
                //System.out.println("CountryCollection: number of Countries: " + countryList.size());
            } catch (IOException e) {
                System.out.println(e);
            }
            countryContinent = new ArrayList<Country>();
        }

        public List<Country> getCountryList() {
            return countryList;
        }

        public void setCountryList(List<Country> countries) {
            this.countryList = countries;
        }

        public void addCountry(Country country) {
            if (country == null)
                return;
            countryList.add(country);
        }

        public List<Country> getType(String continent) {
            countryContinent.clear();
            for (Country country : countryList)
                if (country.getContinent().equals(continent))
                    countryContinent.add(country);
            return countryContinent;
        }



        @Override
        public String toString() {
            return "Country Collection [country=" + this.countryList + "]";
        }

}

