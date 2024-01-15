package com.expeditors.exercise;
import lombok.*;

@Data

public class Address {
    private String address;
    private String city;
    private String state;

    public Address(String address, String city, String state) {
        this.address = capitalizeWords(address);
        this.city = capitalizeWords(city);
        this.state = state.toUpperCase();
    }

    private String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder result = new StringBuilder();
        String[] words = input.split("\\s+"); //splits the input into words

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return result.toString().trim();
    }

    @Override
    public String toString() {
        return "Address: " + address + ", " + city + ", " + state ;
    }
}

