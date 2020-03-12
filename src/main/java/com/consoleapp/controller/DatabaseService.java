package com.consoleapp.controller;

import com.consoleapp.model.Fields;
import com.consoleapp.model.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    // Параметры соединения
    private String driver;
    private String url;
    private String user;
    private String password;
    private int count = 1;

    // SQL
    private static final String SQL_DELETE = "DELETE FROM test";
    private static final String SQL_SELECT = "SELECT * FROM test";
    private static final String SQL_INSERT = "INSERT INTO test (id, field) VALUES (nextval('testseq'),?)";

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addFields() {
        try {

            // Регистрируем драйвер
            Class.forName(getDriver());

            //Получаем соединение с БД
            Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPassword());
            //Отключаем автокоммит
            connection.setAutoCommit(false);

            //Готовим запросы
            Statement statement = null;
            PreparedStatement preparedStatement = null;


            System.out.println("Deleting rows..");
            statement = connection.createStatement();
            statement.executeUpdate(SQL_DELETE);

            System.out.println("Creating statement..");
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            for (int i = 1; i <= count; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            connection.commit();
            connection.close();

        } catch (SQLException se) {

            se.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public Fields getFields() {
        List<Test> tests = new ArrayList<>();
        Fields fields = new Fields();
        try {
            // Регистрируем драйвер
            Class.forName(getDriver());

            //Получаем соединение с БД
            Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPassword());
            //Отключаем автокоммит
            connection.setAutoCommit(false);

            //Готовим запросы
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SQL_SELECT);
            while (rs.next()) {
                Test test = new Test();
                test.setId(rs.getInt("id"));
                test.setField(rs.getInt("field"));
                tests.add(test);
                tests.toString();
            }
            System.out.println(tests.toString());

            fields.setTests(tests);

        } catch (SQLException se) {

            se.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();

        }
        return fields;
    }

    public void parseAndCreateXML(Fields fields) {

        try {

            File file = new File("C:\\inputfolder\\1.xml");
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(Fields.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Print XML String to Console
            StringWriter sw = new StringWriter();

            //Write XML to StringWriter
            jaxbMarshaller.marshal(fields, file);

            //Verify XML Content
            String xmlContent = sw.toString();
            System.out.println(xmlContent);

        } catch (JAXBException e) {
            e.printStackTrace();
        }


    }

    public void transformToXsl(String xslt, String xmlfrom, String xmlto) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source template = new StreamSource(new File(xslt));
            Transformer transformer = factory.newTransformer(template);
            Source sourcefrom = new StreamSource(new File(xmlfrom));
            transformer.transform(sourcefrom, new StreamResult(new File(xmlto)));
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    public void unmarshallXSDtoModel(String xsd, String xml) {
        try {

           File file = new File(xml);
            JAXBContext jc = JAXBContext.newInstance(Fields.class);

            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Fields fields = (Fields) unmarshaller.unmarshal(file);
            List<Test> tests = new ArrayList<>();
            tests = fields.getTests();
            System.out.println(fields);

            int sum = 0;

            for (int i = 0; i < tests.size(); i++) {
                sum = sum + tests.get(i).getField();
            }

            System.out.println("Sum of Field's value is: " + sum);


        /*} catch (SAXException e) {
            e.printStackTrace();*/
        } catch (JAXBException je) {
            je.printStackTrace();
       /* } catch (TransformerConfigurationException te) {
            te.printStackTrace();
        } catch (TransformerException tre) {
            tre.printStackTrace();*/
        }
    }
}
