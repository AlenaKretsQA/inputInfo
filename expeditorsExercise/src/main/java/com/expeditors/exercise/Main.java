package com.expeditors.exercise;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    private static final String FILEPATH = "/Users/alenakhrenouskaya/Downloads/expeditorsExercise/src/main/resources/input/";
    private static final String INPUT_FILE_NAME = "INPUT.txt";
    private static final String OUTPUT_FILE_NAME = "OUTPUT.txt";
    private static final int ELIGIBLE_AGE = 18;


    public static void main(String[] args) {

        //reading data from the file
        Map <Address, List<Person>> households = readHouseholdsFromFile(FILEPATH + INPUT_FILE_NAME);

        // out of information to console AND to file
        //delete output file if exist
        try {
            Files.deleteIfExists(Path.of(FILEPATH + OUTPUT_FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Address address: households.keySet()) {
            List<Person> personsOlderThen = new ArrayList<>();
            for (Person person : households.get(address)) {
                if (person.ageEligible(ELIGIBLE_AGE)) {
                    personsOlderThen.add(person);
                }
            }
            if (!personsOlderThen.isEmpty()) {

                // Sort each list of personsOlderThen by Last Name then First Name
                sortPersonList(personsOlderThen);

                // Print to console
                printInformationToConsole(address, personsOlderThen);

                // Write to file
                writeOutputToFile(address, personsOlderThen, FILEPATH + OUTPUT_FILE_NAME);
            }
        }

    }

    /**
     * Prints information to the console.
     *
     * @param address The address for which information is printed.
     * @param persons The list of persons older than a specified age.
     */
    private static void printInformationToConsole(Address address, List<Person> persons) {
        System.out.println(address + " has " + persons.size() + " occupants older than " + ELIGIBLE_AGE + " years:");
        int i = 0;
        for (Person person : persons) {
            System.out.println(++i + ") " + person);
        }
        System.out.println();
    }

    /**
     * Writes information to a file.
     *
     * @param address   The address for which information is written.
     * @param persons   The list of persons older than a specified age.
     * @param filePath  The path to the output file.
     */
    private static void writeOutputToFile(Address address, List<Person> persons, String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(address + " has " + persons.size() + " occupants older than " + ELIGIBLE_AGE + " years:");
            writer.newLine();
            int i = 0;
            for (Person person : persons) {
                writer.write(++i + ") " + person.toString());
                writer.newLine();
            }
            writer.newLine(); // Separate entries for each address
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reads households' data from a file.
     *
     * @param filePath The path to the input file.
     * @return A map containing addresses and corresponding lists of persons.
     */
    private static Map<Address, List<Person>> readHouseholdsFromFile(String filePath) {
//     
        Map<Address, List<Person>> houseHold = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\",\"");
                String firstName = data[0].replaceAll("\"", "").trim();
                String lastName = data[1].trim();
                String addressStr = data[2].replaceAll("[^a-zA-Z0-9\\s+]", "").trim();
                String cityStr = data[3].trim();
                String stateStr = data[4].trim().toUpperCase();
                int age = Integer.parseInt(data[5].replaceAll("\"", "").trim());

                Address address = new Address(addressStr, cityStr, stateStr);
                Person person = new Person(firstName, lastName, age);

                if (!houseHold.containsKey(address)) {
                    List<Person> firstPerson = new ArrayList<>();
                    firstPerson.add(person);
                    houseHold.put(address, firstPerson);
                } else {
                    List<Person> existList = houseHold.get(address);
                    existList.add(person);
                    houseHold.put(address, existList);
                }

//

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return houseHold;
    }


    /**
     * Sorts a list of persons by Last Name and then First Name.
     *
     * @param personList The list of persons to be sorted.
     */
    private static void sortPersonList(List<Person> personList) {
        Collections.sort(personList, Comparator
                .comparing(Person::getLastName)
                .thenComparing(Person::getFirstName));
    }

}