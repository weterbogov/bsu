/**
 * Created by Drapegnik on 23.03.17.
 */
package app.backend;

import app.config.Options;
import app.models.Mark;
import app.models.Student;
import app.models.Subject;
import app.models.wrappers.Students;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class xmlDriver extends dbDriver {
    /**
     * Get all {@link Student}'s objects from storage
     *
     * @return <pre>{@code List<Student>}</pre> {@link Student}'s objects
     */
    @Override
    public List<Student> getStudents() {
        return null;
    }

    /**
     * Get all {@link Student}'s objects from storage
     * that have 3 and more bad (1, 2, 3) marks
     *
     * @return <pre>{@code List<String>} students_ids</pre>
     */
    @Override
    public List<String> getBadStudentsIds() {
        return null;
    }

    /**
     * Delete {@link Student} and all his {@link Mark}s from storage
     *
     * @param id {@link Student#id}
     */
    @Override
    public void deleteStudent(String id) {

    }

    /**
     * Create and save {@link Student} object in storage
     *
     * @param student {@link Student} instance
     */
    @Override
    public void createStudent(Student student) {

    }

    /**
     * Create {@link Mark} object in storage
     *
     * @param mark {@link Mark} instance
     */
    @Override
    public void createMark(Mark mark) {
    }

    private void writeData(ArrayList<Student> data) {
        try {
            File file = new File(Options.XML_DIR + Student.class.getSimpleName() + "s.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Students.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(new Students(data), file);
            jaxbMarshaller.marshal(new Students(data), System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetch {@link Student}s data from {@link Options#STUDENTS_FILE_NAME}
     * Generate random {@link Mark}s with {@link Subject}s
     * Save all data into xml-file
     *
     * @see xmlDriver#writeData(ArrayList)
     * @see Options#STUDENTS_FILE_NAME
     */
    private void initStorage() {
        System.out.println("Init storage...");
        ArrayList<Student> data = Student.readFromFile(Options.STUDENTS_FILE_NAME);
        Random random = new Random();

        for (Student student : data) {
            ArrayList<Mark> marks = new ArrayList<>();
            for (Subject subject : Subject.values()) {
                marks.add(new Mark(subject, random.nextInt(10) + 1, student.getId()));
            }
            student.setMarks(marks);
        }

        writeData(data);
    }

    public static void main(String[] args) {
        xmlDriver xml = new xmlDriver();
        Student.writeInFile(app.config.Options.STUDENTS_FILE_NAME, Student.generateFakeData());
        xml.initStorage();
    }
}