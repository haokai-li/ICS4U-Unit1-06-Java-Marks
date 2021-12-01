/*
* This is a program generates marks
* after reading in 2 text files.
*
* @author  Haokai
* @version 1.0
* @since   2020-11-29
*/

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
* This is the marks program.
*/
final class Marks {

    /**
    * File ./variable.
    */
    public static final String FILEPLANCE = "./";

    /**
    * Prevent instantiation
    * Throw an exception IllegalStateException.
    * if this ever is called
    *
    * @throws IllegalStateException
    *
    */
    private Marks() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    /**
    * The generateMarks() function.
    *
    * @param arrayOfStudents the collection of students
    * @param arrayOfAssignments the collection of assignments
    * @return the generated marks
    */
    public static String[][] generateMarks(final String[] arrayOfStudents,
                                       final String[] arrayOfAssignments) {
        final int stuLong = arrayOfStudents.length;
        final int assLong = arrayOfAssignments.length;
        final String[][] markArray = new String[stuLong + 1][assLong + 1];

        // calculate
        for (int iLoop = 0; iLoop < stuLong; iLoop++) {
            markArray[iLoop + 1][0] = arrayOfStudents[iLoop];
        }

        for (int eLoop = 0; eLoop < assLong; eLoop++) {
            markArray[0][eLoop + 1] = arrayOfAssignments[eLoop];
        }

        for (int sLoop = 0; sLoop < stuLong; sLoop++) {
            for (int hLoop = 0; hLoop < assLong; hLoop++) {
                final Random randomNumber = new Random();
                final int mark = (int) Math.floor(
                    randomNumber.nextGaussian() * 10 + 75);
                markArray[sLoop + 1][hLoop + 1] = mark + "% ";
            }
        }

        return markArray;
    }

    /**
    * The starting main() function.
    *
    * @param args No args will be used
    */
    public static void main(final String[] args) {
        final ArrayList<String> listOfStudents = new ArrayList<String>();
        final ArrayList<String> listOfAssingments = new ArrayList<String>();
        final Path studentFilePath = Paths.get(FILEPLANCE, args[0]);
        final Path assignmentFilePath = Paths.get(FILEPLANCE, args[1]);
        final Charset charset = Charset.forName("UTF-8");

        try (BufferedReader readerStudent = Files.newBufferedReader(
                                     studentFilePath, charset)) {
            String lineStudent = null;
            while ((lineStudent = readerStudent.readLine()) != null) {
                listOfStudents.add(lineStudent);
            }
        } catch (IOException errorCode) {
            System.err.println(errorCode);
        }

        try (BufferedReader readerAssignment = Files.newBufferedReader(
                                     assignmentFilePath, charset)) {
            String lineAssignment = null;
            while ((lineAssignment = readerAssignment.readLine()) != null) {
                listOfAssingments.add(lineAssignment);
            }
        } catch (IOException errorCode) {
            System.err.println(errorCode);
        }

        final String[] arrayOfStudents = listOfStudents.toArray(
            new String[listOfStudents.size()]);
        System.out.println(Arrays.toString(arrayOfStudents));

        final String[] arrayOfAssignments = listOfAssingments.toArray(
            new String[listOfAssingments.size()]);
        System.out.println(Arrays.toString(arrayOfAssignments));

        System.out.println("\nCalculating stats...");
        final String[][] generateMarks = generateMarks(
            arrayOfStudents, arrayOfAssignments);
        try {
            final FileWriter writer = new FileWriter("../marks.csv");

            for (int iLoop = 0; iLoop < arrayOfStudents.length + 1; iLoop++) {
                for (int eLoop = 0;
                    eLoop < arrayOfAssignments.length + 1; eLoop++) {
                    writer.append(generateMarks[iLoop][eLoop]);
                    writer.append(", ");
                }
                writer.append("\n");
            }
            writer.close();
        } catch (IOException errorCode) {
            System.err.println(errorCode);
        }

        System.out.println("\nDone.");
    }
}
