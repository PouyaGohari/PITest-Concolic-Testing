package edu.stevens.ssw555;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.*;
import java.util.*;


public class Gedcom_ServiceTest {

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        Gedcom_Service.families.clear();
        Gedcom_Service.individuals.clear();
        Gedcom_Service.dupInd.clear();
        Gedcom_Service.dupFam.clear();
        Gedcom_Service.fileName = tempDir.resolve("GedcomService_output.txt").toString();
    }

    @Test
    public void readAndParseFileEmptyFile() throws Exception{
        String path = "src/test/java/edu/stevens/ssw555/test1.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertTrue(gcd.dupInd.isEmpty(), "Expected dupInd to be empty");
        assertTrue(gcd.dupFam.isEmpty(), "Expected dupFam to be empty");
    }

    @Test
    public void readAndParseFileNoExistanceFIle(){
        Gedcom_Service gcd = new Gedcom_Service();
        String nonExistentFilePath = "path/to/nonexistent/file.ged";
        assertThrows(IOException.class, () -> {
            gcd.readAndParseFile(nonExistentFilePath);
        }, "Expected readAndParseFile to throw IOException due to non-existent file");
    }

    @Test
    public void readAndParseFileWithSingleInd() throws Exception{
        String path = "src/test/java/edu/stevens/ssw555/test2.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertTrue(gcd.dupInd.isEmpty(), "dupInd should be empty");
        assertEquals(1, gcd.individuals.size(), "dupInd should have one individual");
        Individual individual = gcd.individuals.get("@I1@");
        assertNotNull(individual, "The individual entry should not be null");
        assertEquals("@I1@", individual.getId(), "Check the individual's ID");
        assertEquals("John Doe", individual.getName(), "Check the individual's name");
        assertEquals("M", individual.getSex(), "Check the individual's sex");
        assertEquals("@F1@", individual.spouseOf, "Check the individual's sex");
        assertEquals("@F2@", individual.getChildOf(), "Check the individual's sex");

        assertEquals("06/15/1975", individual.getBirth(), "Check the individual's birth date and place");
        assertEquals("08/20/2050", individual.getDeath(), "Check the individual's death date and place");
        assertEquals(1, gcd.individuals.size(), "The size of individuals should be 1");
    }

    @Test
    public void readAndParseFileWithSingleFamily() throws Exception{
        String path = "src/test/java/edu/stevens/ssw555/test3.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertTrue(gcd.dupFam.isEmpty(), "dupFam should be empty");
        assertEquals(1, gcd.families.size(), "dupInd should have one individual");
        Family family = gcd.families.get("@F1@");
        assertNotNull(family, "The individual entry should not be null");
        assertEquals("@F1@", family.getId(), "Check the family's ID");
        assertEquals("@I1@", family.getHusb(), "Chekc the family's Husband");
        assertEquals("@I2@", family.getWife(), "Chekc the family's Wife");
        ArrayList<String> children = new ArrayList<>();
        children.add("@I3@");
        children.add("@I4@");
        assertEquals(children, family.child, "check the child array");
        assertEquals("06/12/1995", family.getMarriage(), "Check the marriages date");
        assertEquals("08/15/2020", family.getDivorce(), "check the divorce date");
    }

    @Test
    public void readAndParseFileWrongIND() throws Exception {
        String path = "src/test/java/edu/stevens/ssw555/test5.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertEquals(gcd.individuals.size(), 0, "Size of individuals is 0");
    }

    @Test
    public void readAndParseFileWrongFAM() throws Exception {
        String path = "src/test/java/edu/stevens/ssw555/test6.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertEquals(gcd.families.size(), 0, "Size of individuals is 0");
    }

    @Test
    public void readAndParseFileWithNullName() throws Exception {
        String path = "src/test/java/edu/stevens/ssw555/test7.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertEquals(gcd.individuals.size(), 1, "Size of individuals is 0");
        Individual ind = gcd.individuals.get("@I1@");
        assertNull(ind.name, "Expecting to be null");
    }

    @Test
    public void readAndParseFileWithNullSex() throws Exception {
        String path = "src/test/java/edu/stevens/ssw555/test8.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertEquals(gcd.individuals.size(), 1, "Size of individuals is 0");
        Individual ind = gcd.individuals.get("@I1@");
        assertNull(ind.sex, "Expecting to be null");
    }

    @Test
    public void readAndParseFileWithNullFAMS() throws Exception {
        String path = "src/test/java/edu/stevens/ssw555/test9.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertEquals(gcd.individuals.size(), 1, "Size of individuals is 0");
        Individual ind = gcd.individuals.get("@I1@");
        assertNull(ind.spouseOf, "Expecting to be null");
    }
    
    @Test
    public void readAndParseFileWithNullFAMC() throws Exception {
        String path = "src/test/java/edu/stevens/ssw555/test10.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertEquals(gcd.individuals.size(), 1, "Size of individuals is 0");
        Individual ind = gcd.individuals.get("@I1@");
        assertNull(ind.getChildOf(), "Expecting to be null");
    }

    @Test
    public void readAndParseFileWithNullBIRT() throws Exception {
        String path = "src/test/java/edu/stevens/ssw555/test11.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertEquals(gcd.individuals.size(), 1, "Size of individuals is 0");
        Individual ind = gcd.individuals.get("@I1@");
        assertNull(ind.getBirth(), "Expecting to be null");
    }

    @Test
    public void readAndParseFileWithNullDEAT() throws Exception {
        String path = "src/test/java/edu/stevens/ssw555/test12.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertEquals(gcd.individuals.size(), 1, "Size of individuals is 0");
        Individual ind = gcd.individuals.get("@I1@");
        assertNull(ind.getDeath(), "Expecting to be null");
    }
    
    
    
    @Test
    public void readAndParseFileDupInd() throws Exception{
        String path = "src/test/java/edu/stevens/ssw555/test4.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertFalse(gcd.dupInd.isEmpty(), "dupInd should not be empty");
        assertTrue(gcd.dupFam.isEmpty(), "dupFam should be empty");
        assertEquals(gcd.individuals.size(), 1, "Size of individuals is 1");
        assertEquals(gcd.dupInd.size(), 1 , "dupInd size should be 1");
        assertFalse(gcd.individuals.isEmpty(), "Individuals must be consist one person");
        Individual ind = gcd.individuals.get("@I1@");
        assertNotNull(ind, "ind must not be NULL");
        assertEquals(ind.getName(), "John Doe", "Name must be matched");
        assertEquals(ind.getBirth(), "01/1/1990", "Date of birth must be matched");
        assertEquals(ind.getSex(), "M", "Sex would be matched");
        ind = gcd.dupInd.get(0);
        assertNotNull(ind, "Must not be null");
        assertEquals(ind.getName(), "Jane Smith", "Second Person with duplicate ID's name must matched");
        assertEquals(ind.getSex(), "F", "Second Person must be female");
        assertEquals(ind.getBirth(), "02/1/1990", "Second person's birth day must matched");
    }

    @Test
    public void readAndParseFileWrongHUSB() throws Exception {
        String path = "src/test/java/edu/stevens/ssw555/test13.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertFalse(gcd.families.isEmpty(), "Families is not empty");
        assertEquals(gcd.families.size(), 1, "Families have 1 in the list");
        Family fam = gcd.families.get("@F1@");
        assertEquals(fam.getId(), "@F1@", "Id must matched");
        assertNull(fam.getHusb(), "Husband must be null");
        assertEquals(fam.getMarriage(), "06/12/1995", "mariage data must be matched");
        assertEquals(fam.getDivorce(), "08/" +  "15" + "/" + "2020", "divord data must match");
        assertEquals(fam.getWife(), "@I2@", "Wife must match");
        ArrayList<String> children = new ArrayList<String>();
        children.add("@I3@");
        children.add("@I4@");
        assertEquals(fam.getChild(), children, "Childeren must matched");
    }

    @Test
    public void readAndParseFileWrongWIFE() throws Exception {
        String path = "src/test/java/edu/stevens/ssw555/test14.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertFalse(gcd.families.isEmpty(), "Families is not empty");
        assertEquals(gcd.families.size(), 1, "Families have 1 in the list");
        Family fam = gcd.families.get("@F1@");
        assertEquals(fam.getId(), "@F1@", "Id must matched");
        assertEquals(fam.getHusb(), "@I1@", "Husband must match");
        assertEquals(fam.getMarriage(), "06/12/1995", "mariage data must be matched");
        assertEquals(fam.getDivorce(), "08/" +  "15" + "/" + "2020", "divord data must match");
        assertNull(fam.getWife(), "Wife must be null");
        ArrayList<String> children = new ArrayList<String>();
        children.add("@I3@");
        children.add("@I4@");
        assertEquals(fam.getChild(), children, "Childeren must matched");
    }

    @Test
    public void readAndParseFileWrongCHILD() throws Exception {
        String path = "src/test/java/edu/stevens/ssw555/test15.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertFalse(gcd.families.isEmpty(), "Families is not empty");
        assertEquals(gcd.families.size(), 1, "Families have 1 in the list");
        Family fam = gcd.families.get("@F1@");
        assertEquals(fam.getId(), "@F1@", "Id must matched");
        assertEquals(fam.getHusb(), "@I1@", "Husband must match");
        assertEquals(fam.getMarriage(), "06/12/1995", "mariage data must be matched");
        assertEquals(fam.getDivorce(), "08/" +  "15" + "/" + "2020", "divord data must match");
        assertEquals(fam.getWife(), "@I2@", "Wife must match");
        ArrayList<String> children = new ArrayList<String>();
        children.add("@I4@");
        assertEquals(fam.getChild(), children, "Childeren must matched");
    }
    @Test
    public void readAndParseFileWrongMARR() throws Exception {
        String path = "src/test/java/edu/stevens/ssw555/test16.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertFalse(gcd.families.isEmpty(), "Families is not empty");
        assertEquals(gcd.families.size(), 1, "Families have 1 in the list");
        Family fam = gcd.families.get("@F1@");
        assertEquals(fam.getId(), "@F1@", "Id must matched");
        assertEquals(fam.getHusb(), "@I1@", "Husband must match");
        assertNull(fam.getMarriage(), "mariage data must be null");
        assertEquals(fam.getDivorce(), "08/" +  "15" + "/" + "2020", "divord data must match");
        assertEquals(fam.getWife(), "@I2@", "Wife must match");
        ArrayList<String> children = new ArrayList<String>();
        children.add("@I3@");
        children.add("@I4@");
        assertEquals(fam.getChild(), children, "Childeren must matched");
    }

    @Test
    public void readAndParseFileWrongDIV() throws Exception {
        String path = "src/test/java/edu/stevens/ssw555/test17.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);
        assertFalse(gcd.families.isEmpty(), "Families is not empty");
        assertEquals(gcd.families.size(), 1, "Families have 1 in the list");
        Family fam = gcd.families.get("@F1@");
        assertEquals(fam.getId(), "@F1@", "Id must matched");
        assertEquals(fam.getHusb(), "@I1@", "Husband must match");
        assertEquals(fam.getMarriage(), "06/12/1995", "mariage data must be matched");
        assertNull(fam.getDivorce(), "divord data must be null");
        assertEquals(fam.getWife(), "@I2@", "Wife must match");
        ArrayList<String> children = new ArrayList<String>();
        children.add("@I3@");
        children.add("@I4@");
        assertEquals(fam.getChild(), children, "Childeren must matched");
    }

    @Test
    public void readAndParseFileDupFam() throws Exception{
        String path = "src/test/java/edu/stevens/ssw555/test18.ged";
        Gedcom_Service gcd = new Gedcom_Service();
        gcd.readAndParseFile(path);

        assertFalse(gcd.families.isEmpty(), "Families is not empty");
        assertEquals(gcd.families.size(), 1, "Families have 1 in the list");
        assertEquals(gcd.dupFam.size(), 1, "One duplicate");

        Family fam = gcd.families.get("@F1@");
        assertEquals(fam.getId(), "@F1@", "Id must matched");
        assertEquals(fam.getHusb(), "@I1@", "Husband must match");
        assertEquals(fam.getMarriage(), "06/12/1995", "mariage data must be matched");
        assertEquals(fam.getDivorce(), "08/" +  "15" + "/" + "2020" ,"divord data must match");
        assertEquals(fam.getWife(), "@I2@", "Wife must match");
        ArrayList<String> children = new ArrayList<String>();
        children.add("@I3@");
        children.add("@I4@");
        assertEquals(fam.getChild(), children, "Childeren must matched");

        fam = gcd.dupFam.get(0);
        assertEquals(fam.getId(), "@F1@", "Id must matched");
        assertEquals(fam.getHusb(), "@I4@", "Husband must match");
        assertEquals(fam.getMarriage(), "06/15/1995", "mariage data must be matched");
        assertEquals(fam.getDivorce(), "08/" +  "20" + "/" + "2020" ,"divord data must match");
        assertEquals(fam.getWife(), "@I5@", "Wife must match");
        children = new ArrayList<String>();
        children.add("@I6@");
        children.add("@I7@");
        assertEquals(fam.getChild(), children, "Childeren must matched");
    }


    // One test needed for Create Ouptut File
   @Test
    public void testCreateOutputFileWithValidPath() throws Exception {
        String validPath = tempDir.toString();
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream(validPath.getBytes());
        System.setIn(in);
        Gedcom_Service.createOutputFile();
        System.setIn(sysInBackup);
        Path outputPath = Paths.get(Gedcom_Service.fileName);
        assertTrue(Files.exists(outputPath), "Expected output file to be created");
        Files.deleteIfExists(outputPath);
    }

    //Write some tests for wirte to file
    @Test
    public void testWriteToFile() throws Exception {
        Gedcom_Service.fileName = tempDir.resolve("GedcomService_output.txt").toString();
        String content = "Test output content";
        Gedcom_Service.writeToFile(content);
        Path outputPath = Paths.get(Gedcom_Service.fileName);
        assertTrue(Files.exists(outputPath), "Expected output file to be created");
        String fileContent = new String(Files.readAllBytes(outputPath));
        assertTrue(fileContent.contains(content), "Expected content to be written to the file");
    }

    @Test
    public void testWriteMultipleLinesToFile() throws Exception {
        String content1 = "First line of content";
        String content2 = "Second line of content";
        Gedcom_Service.fileName = tempDir.resolve("GedcomService_output.txt").toString();
        Gedcom_Service.writeToFile(content1);
        Gedcom_Service.writeToFile(content2);
        Path outputPath = Paths.get(Gedcom_Service.fileName);
        assertTrue(Files.exists(outputPath), "Expected output file to be created");
        String fileContent = new String(Files.readAllBytes(outputPath));
        assertTrue(fileContent.contains(content1), "Expected first line of content to be written to the file");
        assertTrue(fileContent.contains(content2), "Expected second line of content to be written to the file");
    }

    @Test
    public void testWriteToFileWithEmptyContent() throws Exception {
        String content = "";
        Gedcom_Service.fileName = tempDir.resolve("GedcomService_output.txt").toString();
        Gedcom_Service.writeToFile(content);
        Path outputPath = Paths.get(Gedcom_Service.fileName);
        assertTrue(Files.exists(outputPath), "Expected output file to be created");
        String fileContent = new String(Files.readAllBytes(outputPath));
        assertTrue(fileContent.contains(content), "Expected empty content to be written to the file");
    }

    @Test
    public void testWriteToFileWithNullFileName() {
        Gedcom_Service.fileName = null;
        assertThrows(NullPointerException.class, () -> {
            Gedcom_Service.writeToFile("This should fail");
        });
    }

    // Wirte Some tests for birth Before Death
    @Test
    public void testBirthBeforeDeathValidDates() throws Exception {
        Individual indi = new Individual("@I1@");
        indi.setBirth("01/01/1980");
        indi.setDeath("01/01/2000");
        indi.setId("@I1@");
        indi.setName("John Doe");
        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(indi.getId(), indi);
        Gedcom_Service.birthBeforeDeath(individuals);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for valid dates");
        }
    }

    @Test
    public void testBirthBeforeDeathInvalidDates() throws Exception {
        Individual indi = new Individual("@I1@");
        indi.setBirth("01/01/2000");
        indi.setDeath("01/01/1980");
        indi.setId("@I1@");
        indi.setName("John Doe");
        Path outputPath = Paths.get(Gedcom_Service.fileName);
        Files.createFile(outputPath);
        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(indi.getId(), indi);

        Gedcom_Service.birthBeforeDeath(individuals);
        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR:INDIVIDUAL: User Story US03: Birth Before Death";
        assertTrue(fileContent.contains(expectedOutput), "Expected error for invalid dates");
    }
    @Test
    public void testBirthBeforeDeathNullDeathDate() throws Exception {
        Individual indi = new Individual("@I1@");
        indi.setBirth("01/01/1980");
        indi.setId("@I1@");
        indi.setName("John Doe");
        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(indi.getId(), indi);

        Gedcom_Service.birthBeforeDeath(individuals);
        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for valid dates");
        }
    }
    @Test
    public void testBirthBeforeDeathFileOutput() throws Exception {
        Individual indi = new Individual("@I1@");
        indi.setBirth("01/01/2000");
        indi.setDeath("01/01/1980");
        indi.setId("@I1@");
        indi.setName("John Doe");
        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(indi.getId(), indi);
        Gedcom_Service.birthBeforeDeath(individuals);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        assertTrue(Files.exists(outputPath), "Expected output file to be created");
        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR:INDIVIDUAL: User Story US03: Birth Before Death";
        assertTrue(fileContent.contains(expectedOutput), "Expected error message to be written to the file");
    }

    // Wite Some tests for Marriage Before Divorce
    @Test
    public void testMarriageBeforeDivorceValidDates() throws Exception {
        Individual husb = new Individual("@I1@");
        husb.setId("@I1@");
        husb.setName("John Doe");

        Individual wife = new Individual("@I2@");
        wife.setId("@I2@");
        wife.setName("Jane Smith");

        Family fam = new Family("@F1@");
        fam.setHusb("@I1@");
        fam.setWife("@I2@");
        fam.setMarriage("01/01/1980");
        fam.setDivorce("01/01/2000");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husb.getId(), husb);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        Files.createFile(outputPath);

        Gedcom_Service.Marriagebeforedivorce(individuals, families);
        
        String fileContent = new String(Files.readAllBytes(outputPath));
        assertFalse(fileContent.contains("ERROR"), "Expected no error for valid dates");
    }

    @Test
    public void testMarriageBeforeDivorceInvalidDates() throws Exception {
        Individual husb = new Individual("@I1@");
        husb.setId("@I1@");
        husb.setName("John Doe");

        Individual wife = new Individual("@I2@");
        wife.setId("@I2@");
        wife.setName("Jane Smith");

        Family fam = new Family("@F1@");
        fam.setHusb("@I1@");
        fam.setWife("@I2@");
        fam.setMarriage("01/01/2000");
        fam.setDivorce("01/01/1980");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husb.getId(), husb);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        Path outputPath = Paths.get(Gedcom_Service.fileName);

        Gedcom_Service.Marriagebeforedivorce(individuals, families);

        assertTrue(Files.exists(outputPath), "Expected output file to be created");

        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR:FAMILY: User Story US04: Marriage Before Divorce";
        assertTrue(fileContent.contains(expectedOutput), "Expected error for invalid dates");
    }


    @Test
    public void testMarriageBeforeDivorceNullDivorceDate() throws Exception {
        Individual husb = new Individual("@I1@");
        husb.setId("@I1@");
        husb.setName("John Doe");

        Individual wife = new Individual("@I2@");
        wife.setId("@I2@");
        wife.setName("Jane Smith");

        Family fam = new Family("@F1@");
        fam.setHusb("@I1@");
        fam.setWife("@I2@");
        fam.setMarriage("01/01/1980");
        fam.setDivorce(null);

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husb.getId(), husb);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        Files.createFile(outputPath);

        Gedcom_Service.Marriagebeforedivorce(individuals, families);

        String fileContent = new String(Files.readAllBytes(outputPath));
        assertFalse(fileContent.contains("ERROR"), "Expected no error for null divorce date");
    }    

    @Test
    public void testMarriageBeforeDivorceFileOutput() throws Exception {
        Individual husb = new Individual("@I1@");
        husb.setId("@I1@");
        husb.setName("John Doe");

        Individual wife = new Individual("@I2@");
        wife.setId("@I2@");
        wife.setName("Jane Smith");

        Family fam = new Family("@F1@");
        fam.setHusb("@I1@");
        fam.setWife("@I2@");
        fam.setMarriage("01/01/2000");
        fam.setDivorce("01/01/1980");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husb.getId(), husb);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        Path outputPath = Paths.get(Gedcom_Service.fileName);

        Gedcom_Service.Marriagebeforedivorce(individuals, families);

        assertTrue(Files.exists(outputPath), "Expected output file to be created");

        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR:FAMILY: User Story US04: Marriage Before Divorce";
        assertTrue(fileContent.contains(expectedOutput), "Expected error message to be written to the file");
    }

    @Test
    public void testMarriageBeforeDivorceParseException() throws Exception {
        Individual husb = new Individual("@I1@");
        husb.setId("@I1@");
        husb.setName("John Doe");

        Individual wife = new Individual("@I2@");
        wife.setId("@I2@");
        wife.setName("Jane Smith");

        Family fam = new Family("@F1@");
        fam.setHusb("@I1@");
        fam.setWife("@I2@");
        fam.setMarriage("invalid-date");
        fam.setDivorce("01/01/2000");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husb.getId(), husb);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        Path outputPath = Paths.get(Gedcom_Service.fileName);

        Gedcom_Service.Marriagebeforedivorce(individuals, families);

        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("java.text.ParseException"), "Expected ParseException to be printed to stderr");
        System.setErr(System.err);
    }    

    // Write some tests for birth before marriage of parrent
    @Test
    public void testBirthBeforeMarriageOfParentValidDates() throws Exception {
        Individual child = new Individual("@I1@");
        child.setId("@I1@");
        child.setName("Child Doe");
        child.setBirth("01/01/1985");

        Individual husb = new Individual("@I2@");
        husb.setId("@I2@");
        husb.setName("John Doe");

        Individual wife = new Individual("@I3@");
        wife.setId("@I3@");
        wife.setName("Jane Smith");

        Family fam = new Family("@F1@");
        fam.setHusb("@I2@");
        fam.setWife("@I3@");
        fam.setMarriage("01/01/1980");
        fam.setDivorce("01/01/1990");
        fam.setChild(new ArrayList<>(Arrays.asList("@I1@")));

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(child.getId(), child);
        individuals.put(husb.getId(), husb);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        Gedcom_Service.birthbeforemarriageofparent(individuals, families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for valid dates");
        }
    }

   @Test
    public void testBirthBeforeMarriageOfParentBirthBeforeMarriage() throws Exception {
        Individual child = new Individual("@I1@");
        child.setId("@I1@");
        child.setName("Child Doe");
        child.setBirth("01/01/1975");

        Individual husb = new Individual("@I2@");
        husb.setId("@I2@");
        husb.setName("John Doe");

        Individual wife = new Individual("@I3@");
        wife.setId("@I3@");
        wife.setName("Jane Smith");

        Family fam = new Family("@F1@");
        fam.setHusb("@I2@");
        fam.setWife("@I3@");
        fam.setMarriage("01/01/1980");
        ArrayList<String> children = new ArrayList<String>();
        children.add("@I1@");
        fam.setChild(children);

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(child.getId(), child);
        individuals.put(husb.getId(), husb);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        Gedcom_Service.birthbeforemarriageofparent(individuals, families);

        assertTrue(Files.exists(outputPath), "Expected output file to be created");
        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR: User Story US08: Birth Before Marriage Date";
        assertTrue(fileContent.contains(expectedOutput), "Expected error for birth before marriage date");
    }

    @Test
    public void testBirthBeforeMarriageOfParentBirthAfterDivorce() throws Exception {
        Individual child = new Individual("@I1@");
        child.setId("@I1@");
        child.setName("Child Doe");
        child.setBirth("01/01/1995");

        Individual husb = new Individual("@I2@");
        husb.setId("@I2@");
        husb.setName("John Doe");

        Individual wife = new Individual("@I3@");
        wife.setId("@I3@");
        wife.setName("Jane Smith");

        Family fam = new Family("@F1@");
        fam.setHusb("@I2@");
        fam.setWife("@I3@");
        fam.setMarriage("01/01/1980");
        fam.setDivorce("01/01/1990");
        ArrayList<String> children = new ArrayList<String>();
        children.add("@I1@");
        fam.setChild(children);

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(child.getId(), child);
        individuals.put(husb.getId(), husb);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        Path outputPath = Paths.get(Gedcom_Service.fileName);

        Gedcom_Service.birthbeforemarriageofparent(individuals, families);

        assertTrue(Files.exists(outputPath), "Expected output file to be created");

        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR: User Story US08: Birth After Divorce Date";
        assertTrue(fileContent.contains(expectedOutput), "Expected error for birth after divorce date");
    }  

   @Test
    public void testBirthBeforeMarriageOfParentFileOutput() throws Exception {
        Individual child = new Individual("@I1@");
        child.setId("@I1@");
        child.setName("Child Doe");
        child.setBirth("01/01/1975");

        Individual husb = new Individual("@I2@");
        husb.setId("@I2@");
        husb.setName("John Doe");

        Individual wife = new Individual("@I3@");
        wife.setId("@I3@");
        wife.setName("Jane Smith");

        Family fam = new Family("@F1@");
        fam.setHusb("@I2@");
        fam.setWife("@I3@");
        fam.setMarriage("01/01/1980");
        fam.setDivorce("01/01/1990");
        ArrayList<String> children = new ArrayList<String>();
        children.add("@I1@");
        fam.setChild(children);

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(child.getId(), child);
        individuals.put(husb.getId(), husb);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        Path outputPath = Paths.get(Gedcom_Service.fileName);

        Gedcom_Service.birthbeforemarriageofparent(individuals, families);

        assertTrue(Files.exists(outputPath), "Expected output file to be created");

        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR: User Story US08: Birth Before Marriage Date";
        assertTrue(fileContent.contains(expectedOutput), "Expected error message to be written to the file for birth before marriage date");
    } 

    @Test
    public void testBirthBeforeMarriageOfParentParseException() throws Exception {
        Individual child = new Individual("@I1@");
        child.setId("@I1@");
        child.setName("Child Doe");
        child.setBirth("invalid-date");

        Individual husb = new Individual("@I2@");
        husb.setId("@I2@");
        husb.setName("John Doe");

        Individual wife = new Individual("@I3@");
        wife.setId("@I3@");
        wife.setName("Jane Smith");

        Family fam = new Family("@F1@");
        fam.setHusb("@I2@");
        fam.setWife("@I3@");
        fam.setMarriage("01/01/1980");
        ArrayList<String> children = new ArrayList<String>();
        children.add("@I1@");
        fam.setChild(children);
        
        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(child.getId(), child);
        individuals.put(husb.getId(), husb);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        Gedcom_Service.birthbeforemarriageofparent(individuals, families);

        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("java.text.ParseException"), "Expected ParseException to be printed to stderr");

        System.setErr(System.err);
    }
    // Write Some Tests For Male last name method
    @Test
    public void testMaleLastNameAllSame() throws Exception {
        Individual child1 = new Individual("@I1@");
        child1.setId("@I1@");
        child1.setName("John Doe");
        child1.setSex("male");

        Individual child2 = new Individual("@I2@");
        child2.setId("@I2@");
        child2.setName("Mike Doe");
        child2.setSex("male");

        Family fam = new Family("@F1@");
        fam.setChild(new ArrayList<>(Arrays.asList("@I1@", "@I2@")));

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(child1.getId(), child1);
        individuals.put(child2.getId(), child2);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        Gedcom_Service.Malelastname(families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for same last names");
        }
    }

    @Test
    public void testMaleLastNameDifferent() throws Exception {
        Individual child1 = new Individual("@I1@");
        child1.setId("@I1@");
        child1.setName("John Doe");
        child1.setSex("male");

        Individual child2 = new Individual("@I2@");
        child2.setId("@I2@");
        child2.setName("Mike Smith");
        child2.setSex("male");

        Family fam = new Family("@F1@");
        fam.setChild(new ArrayList<>(Arrays.asList("@I1@", "@I2@")));

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(child1.getId(), child1);
        individuals.put(child2.getId(), child2);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        Gedcom_Service.Malelastname(families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        assertTrue(Files.exists(outputPath), "Expected output file to be created");

        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR: User Story US16:Male last name";
        assertTrue(fileContent.contains(expectedOutput), "Expected error for different last names");
    }
    @Test
    public void testNoMaleMembers() throws Exception {
        Individual child1 = new Individual("@I1@");
        child1.setId("@I1@");
        child1.setName("Jane Doe");
        child1.setSex("female");

        Individual child2 = new Individual("@I2@");
        child2.setId("@I2@");
        child2.setName("Emily Doe");
        child2.setSex("female");

        Family fam = new Family("@F1@");
        fam.setChild(new ArrayList<>(Arrays.asList("@I1@", "@I2@")));

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(child1.getId(), child1);
        individuals.put(child2.getId(), child2);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        Gedcom_Service.Malelastname(families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for no male members");
        }
    }
    @Test
    public void testMaleLastNameFileOutput() throws Exception {
        Individual child1 = new Individual("@I1@");
        child1.setId("@I1@");
        child1.setName("John Doe");
        child1.setSex("male");

        Individual child2 = new Individual("@I2@");
        child2.setId("@I2@");
        child2.setName("Mike Smith");
        child2.setSex("male");

        Family fam = new Family("@F1@");
        fam.setChild(new ArrayList<>(Arrays.asList("@I1@", "@I2@")));

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(child1.getId(), child1);
        individuals.put(child2.getId(), child2);

        HashMap<String, Family> families = new HashMap<>();
        families.put(fam.getId(), fam);

        Gedcom_Service.Malelastname(families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        assertTrue(Files.exists(outputPath), "Expected output file to be created");

        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR: User Story US16:Male last name";
        assertTrue(fileContent.contains(expectedOutput), "Expected error message to be written to the file for different last names");
    }

    // Write some tests for Aunts and Uncles last name 
    @Test
    public void testAuntsAndUnclesNameNoIncest() throws Exception {
        Individual father = new Individual("@I1@");
        father.setId("@I1@");
        father.setName("John Doe");
        father.setChildOf("@F3@");

        Individual mother = new Individual("@I2@");
        mother.setId("@I2@");
        mother.setName("Jane Smith");
        mother.setChildOf("@F2@");

        Individual child = new Individual("@I3@");
        child.setId("@I3@");
        child.setName("Child Doe");
        child.setChildOf("@F1@");
        child.setSpouseOf("@F4@");

        Individual aunt = new Individual("@I4@");
        aunt.setId("@I4@");
        aunt.setName("Aunt Doe");

        Individual uncle = new Individual("@I5@");
        uncle.setId("@I5@");
        uncle.setName("Uncle Smith");

        Individual grandmother = new Individual("@I6@");
        grandmother.setId("@I6@");
        grandmother.setName("Grandmother Smith");

        Individual grandfather = new Individual("@I7@");
        grandfather.setId("@I7@");
        grandfather.setName("Grandfather Smith");

        Individual grandmother2 = new Individual("@I8@");
        grandmother2.setId("@I8@");
        grandmother2.setName("Grandmother Doe");

        Individual grandfather2 = new Individual("@I9@");
        grandfather2.setId("@I9@");
        grandfather2.setName("Grandfather Doe");

        Family family = new Family("@F1@");
        family.setHusb("@I1@");
        family.setWife("@I2@");
        family.setChild(new ArrayList<>(Arrays.asList("@I3@")));

        Family motherFamily = new Family("@F2@");
        motherFamily.setHusb("@I9@");
        motherFamily.setWife("@I8@");
        motherFamily.setChild(new ArrayList<>(Arrays.asList("@I2@", "@I4@")));

        Family fatherFamily = new Family("@F3@");
        fatherFamily.setHusb("@I7@");
        fatherFamily.setWife("@I6@");
        fatherFamily.setChild(new ArrayList<>(Arrays.asList("@I1@", "@I5@")));

        Family childFamily = new Family("@F4@");
        childFamily.setHusb("@I3@");
        childFamily.setWife("@I6@");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(father.getId(), father);
        individuals.put(mother.getId(), mother);
        individuals.put(child.getId(), child);
        individuals.put(aunt.getId(), aunt);
        individuals.put(uncle.getId(), uncle);
        individuals.put(grandmother.getId(), grandmother);
        individuals.put(grandfather.getId(), grandfather);
        individuals.put(grandmother2.getId(), grandmother2);
        individuals.put(grandfather2.getId(), grandfather2);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family.getId(), family);
        families.put(motherFamily.getId(), motherFamily);
        families.put(fatherFamily.getId(), fatherFamily);
        families.put(childFamily.getId(), childFamily);

        Gedcom_Service.individuals = individuals;

        assertEquals(Gedcom_Service.individuals, individuals);

        Gedcom_Service.AuntsandUnclesname(families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for no incest");
        }
    }

    @Test
    public void testAuntsAndUnclesNameIncest() throws Exception {
        Individual father = new Individual("@I1@");
        father.setId("@I1@");
        father.setName("John Doe");
        father.setChildOf("@F3@");

        Individual mother = new Individual("@I2@");
        mother.setId("@I2@");
        mother.setName("Jane Smith");
        mother.setChildOf("@F2@");

        Individual child = new Individual("@I3@");
        child.setId("@I3@");
        child.setName("Child Doe");
        child.setChildOf("@F1@");
        child.setSpouseOf("@F4@");

        Individual aunt = new Individual("@I4@");
        aunt.setId("@I4@");
        aunt.setName("Aunt Doe");

        Individual grandmother = new Individual("@I6@");
        grandmother.setId("@I6@");
        grandmother.setName("Grandmother Smith");

        Individual grandfather = new Individual("@I7@");
        grandfather.setId("@I7@");
        grandfather.setName("Grandfather Smith");

        Individual grandmother2 = new Individual("@I8@");
        grandmother2.setId("@I8@");
        grandmother2.setName("Grandmother Doe");

        Individual grandfather2 = new Individual("@I9@");
        grandfather2.setId("@I9@");
        grandfather2.setName("Grandfather Doe");

        Family family = new Family("@F1@");
        family.setHusb("@I1@");
        family.setWife("@I2@");
        family.setChild(new ArrayList<>(Arrays.asList("@I3@")));

        Family motherFamily = new Family("@F2@");
        motherFamily.setHusb("@I9@");
        motherFamily.setWife("@I8@");
        motherFamily.setChild(new ArrayList<>(Arrays.asList("@I2@", "@I4@")));

        Family fatherFamily = new Family("@F3@");
        fatherFamily.setHusb("@I7@");
        fatherFamily.setWife("@I6@");
        fatherFamily.setChild(new ArrayList<>(Arrays.asList("@I1@")));

        Family childFamily = new Family("@F4@");
        childFamily.setHusb("@I3@");
        childFamily.setWife("@I4@"); // Child is married to Aunt

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(father.getId(), father);
        individuals.put(mother.getId(), mother);
        individuals.put(child.getId(), child);
        individuals.put(aunt.getId(), aunt);
        individuals.put(grandmother.getId(), grandmother);
        individuals.put(grandfather.getId(), grandfather);
        individuals.put(grandmother2.getId(), grandmother2);
        individuals.put(grandfather2.getId(), grandfather2);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family.getId(), family);
        families.put(motherFamily.getId(), motherFamily);
        families.put(fatherFamily.getId(), fatherFamily);
        families.put(childFamily.getId(), childFamily);

        Gedcom_Service.individuals = individuals;
        Gedcom_Service.families = families;

        Gedcom_Service.AuntsandUnclesname(families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        assertTrue(Files.exists(outputPath), "Expected output file to be created");

        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR: User Story US20: Aunts and Uncles";
        assertTrue(fileContent.contains(expectedOutput), "Expected error for incest");
    }
    
    @Test
    public void testAuntsAndUnclesNameFileOutput() throws Exception {
        Individual father = new Individual("@I1@");
        father.setId("@I1@");
        father.setName("John Doe");
        father.setChildOf("@F3@");

        Individual mother = new Individual("@I2@");
        mother.setId("@I2@");
        mother.setName("Jane Smith");
        mother.setChildOf("@F2@");

        Individual child = new Individual("@I3@");
        child.setId("@I3@");
        child.setName("Child Doe");
        child.setChildOf("@F1@");
        child.setSpouseOf("@F4@");

        Individual aunt = new Individual("@I4@");
        aunt.setId("@I4@");
        aunt.setName("Aunt Doe");

        Individual grandmother = new Individual("@I6@");
        grandmother.setId("@I6@");
        grandmother.setName("Grandmother Smith");

        Individual grandfather = new Individual("@I7@");
        grandfather.setId("@I7@");
        grandfather.setName("Grandfather Smith");

        Individual grandmother2 = new Individual("@I8@");
        grandmother2.setId("@I8@");
        grandmother2.setName("Grandmother Doe");

        Individual grandfather2 = new Individual("@I9@");
        grandfather2.setId("@I9@");
        grandfather2.setName("Grandfather Doe");

        Family family = new Family("@F1@");
        family.setHusb("@I1@");
        family.setWife("@I2@");
        family.setChild(new ArrayList<>(Arrays.asList("@I3@")));

        Family motherFamily = new Family("@F2@");
        motherFamily.setHusb("@I9@");
        motherFamily.setWife("@I8@");
        motherFamily.setChild(new ArrayList<>(Arrays.asList("@I2@", "@I4@")));

        Family fatherFamily = new Family("@F3@");
        fatherFamily.setHusb("@I7@");
        fatherFamily.setWife("@I6@");
        fatherFamily.setChild(new ArrayList<>(Arrays.asList("@I1@")));

        Family childFamily = new Family("@F4@");
        childFamily.setHusb("@I3@");
        childFamily.setWife("@I4@"); // Child is married to Aunt

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(father.getId(), father);
        individuals.put(mother.getId(), mother);
        individuals.put(child.getId(), child);
        individuals.put(aunt.getId(), aunt);
        individuals.put(grandmother.getId(), grandmother);
        individuals.put(grandfather.getId(), grandfather);
        individuals.put(grandmother2.getId(), grandmother2);
        individuals.put(grandfather2.getId(), grandfather2);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family.getId(), family);
        families.put(motherFamily.getId(), motherFamily);
        families.put(fatherFamily.getId(), fatherFamily);
        families.put(childFamily.getId(), childFamily);

        Gedcom_Service.individuals = individuals;
        Gedcom_Service.families = families;

        Gedcom_Service.AuntsandUnclesname(families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        assertTrue(Files.exists(outputPath), "Expected output file to be created");

        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR: User Story US20: Aunts and Uncles";
        assertTrue(fileContent.contains(expectedOutput), "Expected error message to be written to the file for incest");
    }

    @Test
    public void testAuntsAndUnclesNameParseException() throws Exception {
        Individual father = new Individual("@I1@");
        father.setId("@I1@");
        father.setName("John Doe");
        father.setChildOf("@F3@");

        Individual mother = new Individual("@I2@");
        mother.setId("@I2@");
        mother.setName("Jane Smith");
        mother.setChildOf("@F2@");

        Individual child = new Individual("@I3@");
        child.setId("@I3@");
        child.setName("Child Doe");
        child.setChildOf("@F1@");
        child.setSpouseOf("@F4@");

        Family family = new Family("@F1@");
        family.setHusb("@I1@");
        family.setWife("@I2@");
        family.setChild(new ArrayList<>(Arrays.asList("@I3@")));

        Family spouseFamily = new Family("@F4@");
        spouseFamily.setHusb("@I3@");
        spouseFamily.setWife("@I5@"); // @I5@ does not exist

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(father.getId(), father);
        individuals.put(mother.getId(), mother);
        individuals.put(child.getId(), child);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family.getId(), family);
        families.put(spouseFamily.getId(), spouseFamily);

        Gedcom_Service.individuals = individuals;
        Gedcom_Service.families = families;

        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));

        try {
            Gedcom_Service.AuntsandUnclesname(families);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("NullPointerException"), "Expected NullPointerException to be printed to stderr");
        System.setErr(originalErr);
    }
    // Write some tests for unique family names by spouses
    @Test
    public void testUniqueFamilyNameBySpousesNoDuplicate() throws Exception {
        Individual husband1 = new Individual("@I1@");
        husband1.setId("@I1@");
        husband1.setName("John Doe");

        Individual wife1 = new Individual("@I2@");
        wife1.setId("@I2@");
        wife1.setName("Jane Smith");

        Individual husband2 = new Individual("@I3@");
        husband2.setId("@I3@");
        husband2.setName("Michael Johnson");

        Individual wife2 = new Individual("@I4@");
        wife2.setId("@I4@");
        wife2.setName("Emily Davis");

        Family family1 = new Family("@F1@");
        family1.setHusb("@I1@");
        family1.setWife("@I2@");
        family1.setMarriage("01/01/2000");

        Family family2 = new Family("@F2@");
        family2.setHusb("@I3@");
        family2.setWife("@I4@");
        family2.setMarriage("02/02/2010");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husband1.getId(), husband1);
        individuals.put(wife1.getId(), wife1);
        individuals.put(husband2.getId(), husband2);
        individuals.put(wife2.getId(), wife2);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family1.getId(), family1);
        families.put(family2.getId(), family2);

        Gedcom_Service.uniqueFamilynameBySpouses(individuals, families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for unique families by spouses");
        }
    }

    @Test
    public void testUniqueFamilyNameBySpousesDuplicate() throws Exception {
        Individual husband = new Individual("@I1@");
        husband.setId("@I1@");
        husband.setName("John Doe");

        Individual wife = new Individual("@I2@");
        wife.setId("@I2@");
        wife.setName("Jane Smith");

        Family family1 = new Family("@F1@");
        family1.setHusb("@I1@");
        family1.setWife("@I2@");
        family1.setMarriage("01/01/2000");

        Family family2 = new Family("@F2@");
        family2.setHusb("@I1@");
        family2.setWife("@I2@");
        family2.setMarriage("01/01/2000");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husband.getId(), husband);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family1.getId(), family1);
        families.put(family2.getId(), family2);

        Gedcom_Service.uniqueFamilynameBySpouses(individuals, families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        assertTrue(Files.exists(outputPath), "Expected output file to be created");

        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR: User Story US24: Unique Families By Spouse";
        assertTrue(fileContent.contains(expectedOutput), "Expected error for duplicate families by spouses");
    }

    @Test
    public void testUniqueFamilyNameBySpousesDifferentDates() throws Exception {
        Individual husband = new Individual("@I1@");
        husband.setId("@I1@");
        husband.setName("John Doe");

        Individual wife = new Individual("@I2@");
        wife.setId("@I2@");
        wife.setName("Jane Smith");

        Family family1 = new Family("@F1@");
        family1.setHusb("@I1@");
        family1.setWife("@I2@");
        family1.setMarriage("01/01/2000");

        Family family2 = new Family("@F2@");
        family2.setHusb("@I1@");
        family2.setWife("@I2@");
        family2.setMarriage("02/02/2001");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husband.getId(), husband);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family1.getId(), family1);
        families.put(family2.getId(), family2);

        Gedcom_Service.uniqueFamilynameBySpouses(individuals, families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for families with different marriage dates");
        }
    }

    @Test
    public void testUniqueFamilyNameBySpousesDifferentSpouses() throws Exception {
        Individual husband1 = new Individual("@I1@");
        husband1.setId("@I1@");
        husband1.setName("John Doe");

        Individual wife1 = new Individual("@I2@");
        wife1.setId("@I2@");
        wife1.setName("Jane Smith");

        Individual wife2 = new Individual("@I3@");
        wife2.setId("@I3@");
        wife2.setName("Emily Davis");

        Family family1 = new Family("@F1@");
        family1.setHusb("@I1@");
        family1.setWife("@I2@");
        family1.setMarriage("01/01/2000");

        Family family2 = new Family("@F2@");
        family2.setHusb("@I1@");
        family2.setWife("@I3@");
        family2.setMarriage("01/01/2000");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husband1.getId(), husband1);
        individuals.put(wife1.getId(), wife1);
        individuals.put(wife2.getId(), wife2);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family1.getId(), family1);
        families.put(family2.getId(), family2);

        Gedcom_Service.uniqueFamilynameBySpouses(individuals, families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for families with different spouses");
        }
    }

    // Write some tests for get month valid
    @Test
    public void testGetMonthValid() {
        assertEquals("01", Gedcom_Service.getMonth("JAN"), "Expected month number for JAN is 01");
        assertEquals("02", Gedcom_Service.getMonth("FEB"), "Expected month number for FEB is 02");
        assertEquals("03", Gedcom_Service.getMonth("MAR"), "Expected month number for MAR is 03");
        assertEquals("04", Gedcom_Service.getMonth("APR"), "Expected month number for APR is 04");
        assertEquals("05", Gedcom_Service.getMonth("MAY"), "Expected month number for MAY is 05");
        assertEquals("06", Gedcom_Service.getMonth("JUN"), "Expected month number for JUN is 06");
        assertEquals("07", Gedcom_Service.getMonth("JUL"), "Expected month number for JUL is 07");
        assertEquals("08", Gedcom_Service.getMonth("AUG"), "Expected month number for AUG is 08");
        assertEquals("09", Gedcom_Service.getMonth("SEP"), "Expected month number for SEP is 09");
        assertEquals("10", Gedcom_Service.getMonth("OCT"), "Expected month number for OCT is 10");
        assertEquals("11", Gedcom_Service.getMonth("NOV"), "Expected month number for NOV is 11");
        assertEquals("12", Gedcom_Service.getMonth("DEC"), "Expected month number for DEC is 12");
    }

    @Test
    public void testGetMonthNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            Gedcom_Service.getMonth(null);
        }, "Expected NullPointerException for null input");
    }

    @Test
    public void testGetMonthInvalidInput() {
        assertNull(Gedcom_Service.getMonth("ABC"), "Expected null for invalid month abbreviation ABC");
        assertNull(Gedcom_Service.getMonth(""), "Expected null for empty string");  
    }
    // Kill Live Mutants
    @Test
    public void testUniqueFamilyNameBySpousesNullHusbandOrWife() throws Exception {
        Individual husband = new Individual("@I1@");
        husband.setId("@I1@");
        husband.setName("John Doe");

        Individual wife = new Individual("@I2@");
        wife.setId("@I2@");
        wife.setName("Jane Smith");

        Family family1 = new Family("@F1@");
        family1.setHusb(null);
        family1.setWife("@I2@");
        family1.setMarriage("01/01/2000");

        Family family2 = new Family("@F2@");
        family2.setHusb("@I1@");
        family2.setWife(null);
        family2.setMarriage("01/01/2000");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husband.getId(), husband);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family1.getId(), family1);
        families.put(family2.getId(), family2);

        Gedcom_Service.uniqueFamilynameBySpouses(individuals, families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for families with null husband or wife IDs");
        }
    }
    @Test
    public void testUniqueFamilyNameBySpousesSameSpousesDifferentIDs() throws Exception {
        Individual husband = new Individual("@I1@");
        husband.setId("@I1@");
        husband.setName("John Doe");

        Individual wife = new Individual("@I2@");
        wife.setId("@I2@");
        wife.setName("Jane Smith");

        Family family1 = new Family("@F1@");
        family1.setHusb("@I1@");
        family1.setWife("@I2@");
        family1.setMarriage("01/01/2000");

        Family family2 = new Family("@F3@");
        family2.setHusb("@I1@");
        family2.setWife("@I2@");
        family2.setMarriage("01/01/2000");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husband.getId(), husband);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family1.getId(), family1);
        families.put(family2.getId(), family2);

        Gedcom_Service.uniqueFamilynameBySpouses(individuals, families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        assertTrue(Files.exists(outputPath), "Expected output file to be created");

        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR: User Story US24: Unique Families By Spouse";
        assertTrue(fileContent.contains(expectedOutput), "Expected error for duplicate families by spouses with different IDs");
    }

    @Test
    public void testUniqueFamilyNameBySpousesDifferentSpousesSameDate() throws Exception {
        Individual husband1 = new Individual("@I1@");
        husband1.setId("@I1@");
        husband1.setName("John Doe");

        Individual wife1 = new Individual("@I2@");
        wife1.setId("@I2@");
        wife1.setName("Jane Smith");

        Individual husband2 = new Individual("@I3@");
        husband2.setId("@I3@");
        husband2.setName("Michael Johnson");

        Individual wife2 = new Individual("@I4@");
        wife2.setId("@I4@");
        wife2.setName("Emily Davis");

        Family family1 = new Family("@F1@");
        family1.setHusb("@I1@");
        family1.setWife("@I2@");
        family1.setMarriage("01/01/2000");

        Family family2 = new Family("@F2@");
        family2.setHusb("@I3@");
        family2.setWife("@I4@");
        family2.setMarriage("01/01/2000");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husband1.getId(), husband1);
        individuals.put(wife1.getId(), wife1);
        individuals.put(husband2.getId(), husband2);
        individuals.put(wife2.getId(), wife2);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family1.getId(), family1);
        families.put(family2.getId(), family2);

        Gedcom_Service.uniqueFamilynameBySpouses(individuals, families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for families with different spouses and same marriage date");
        }
    }


    @Test
    public void testUniqueFamilyNameBySpousesNoDuplicate2() throws Exception {
        Individual husband1 = new Individual("@I1@");
        husband1.setId("@I1@");
        husband1.setName("John Doe");

        Individual wife1 = new Individual("@I2@");
        wife1.setId("@I2@");
        wife1.setName("Jane Smith");

        Individual husband2 = new Individual("@I3@");
        husband2.setId("@I3@");
        husband2.setName("Michael Johnson");

        Individual wife2 = new Individual("@I4@");
        wife2.setId("@I4@");
        wife2.setName("Emily Davis");

        Family family1 = new Family("@F1@");
        family1.setHusb("@I1@");
        family1.setWife("@I2@");
        family1.setMarriage("01/01/2000");

        Family family2 = new Family("@F2@");
        family2.setHusb("@I3@");
        family2.setWife("@I4@");
        family2.setMarriage("02/02/2010");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husband1.getId(), husband1);
        individuals.put(wife1.getId(), wife1);
        individuals.put(husband2.getId(), husband2);
        individuals.put(wife2.getId(), wife2);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family1.getId(), family1);
        families.put(family2.getId(), family2);

        Gedcom_Service.uniqueFamilynameBySpouses(individuals, families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for unique families by spouses");
        }
    }

    @Test
    public void testUniqueFamilyNameBySpousesDuplicate2() throws Exception {
        Individual husband = new Individual("@I1@");
        husband.setId("@I1@");
        husband.setName("John Doe");

        Individual wife = new Individual("@I2@");
        wife.setId("@I2@");
        wife.setName("Jane Smith");

        Family family1 = new Family("@F1@");
        family1.setHusb("@I1@");
        family1.setWife("@I2@");
        family1.setMarriage("01/01/2000");

        Family family2 = new Family("@F2@");
        family2.setHusb("@I1@");
        family2.setWife("@I2@");
        family2.setMarriage("01/01/2000");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husband.getId(), husband);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family1.getId(), family1);
        families.put(family2.getId(), family2);

        Gedcom_Service.uniqueFamilynameBySpouses(individuals, families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        assertTrue(Files.exists(outputPath), "Expected output file to be created");

        String fileContent = new String(Files.readAllBytes(outputPath));
        String expectedOutput = "ERROR: User Story US24: Unique Families By Spouse :\n"
                + "@F2@: Husbund Name: John Doe,Wife Name: Jane Smith and @F1@: Husbund Name: John Doe,Wife Name: Jane Smith\n"
                + " have same spouses and marriage dates :01/01/2000\n\n"
                + "ERROR: User Story US24: Unique Families By Spouse :\n"
                + "@F1@: Husbund Name: John Doe,Wife Name: Jane Smith and @F2@: Husbund Name: John Doe,Wife Name: Jane Smith\n"
                + " have same spouses and marriage dates :01/01/2000\n\n";

        assertEquals(expectedOutput, fileContent, "The file content is not as expected");
    }

    @Test
    public void testUniqueFamilyNameBySpousesDifferentDates2() throws Exception {
        Individual husband = new Individual("@I1@");
        husband.setId("@I1@");
        husband.setName("John Doe");

        Individual wife = new Individual("@I2@");
        wife.setId("@I2@");
        wife.setName("Jane Smith");

        Family family1 = new Family("@F1@");
        family1.setHusb("@I1@");
        family1.setWife("@I2@");
        family1.setMarriage("01/01/2000");

        Family family2 = new Family("@F2@");
        family2.setHusb("@I1@");
        family2.setWife("@I2@");
        family2.setMarriage("02/02/2001");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husband.getId(), husband);
        individuals.put(wife.getId(), wife);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family1.getId(), family1);
        families.put(family2.getId(), family2);

        Gedcom_Service.uniqueFamilynameBySpouses(individuals, families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for families with different marriage dates");
        }
    }

    @Test
    public void testUniqueFamilyNameBySpousesDifferentSpouses2() throws Exception {
        Individual husband1 = new Individual("@I1@");
        husband1.setId("@I1@");
        husband1.setName("John Doe");

        Individual wife1 = new Individual("@I2@");
        wife1.setId("@I2@");
        wife1.setName("Jane Smith");

        Individual wife2 = new Individual("@I3@");
        wife2.setId("@I3@");
        wife2.setName("Emily Davis");

        Family family1 = new Family("@F1@");
        family1.setHusb("@I1@");
        family1.setWife("@I2@");
        family1.setMarriage("01/01/2000");

        Family family2 = new Family("@F2@");
        family2.setHusb("@I1@");
        family2.setWife("@I3@");
        family2.setMarriage("01/01/2000");

        HashMap<String, Individual> individuals = new HashMap<>();
        individuals.put(husband1.getId(), husband1);
        individuals.put(wife1.getId(), wife1);
        individuals.put(wife2.getId(), wife2);

        HashMap<String, Family> families = new HashMap<>();
        families.put(family1.getId(), family1);
        families.put(family2.getId(), family2);

        Gedcom_Service.uniqueFamilynameBySpouses(individuals, families);

        Path outputPath = Paths.get(Gedcom_Service.fileName);
        if (Files.exists(outputPath)) {
            String fileContent = new String(Files.readAllBytes(outputPath));
            assertFalse(fileContent.contains("ERROR"), "Expected no error for families with different spouses");
        }
    }
}


