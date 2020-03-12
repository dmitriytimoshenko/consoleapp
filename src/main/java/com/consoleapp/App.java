package com.consoleapp;


import com.consoleapp.controller.DatabaseService;



public class App
{

    public static void main( String[] args ) {

        DatabaseService dbservice = new DatabaseService();
        dbservice.setDriver("org.postgresql.Driver");
        dbservice.setUrl("jdbc:postgresql://localhost:5432/TestDB");
        dbservice.setUser("postgres");
        dbservice.setPassword("postgres");
        dbservice.setCount(5);
        dbservice.addFields();
        System.out.println("Rows added!");

        System.out.println("Parse to XML..");
        dbservice.parseAndCreateXML(dbservice.getFields());

        dbservice.transformToXsl("res\\template.xsl", "C:\\inputfolder\\1.xml", "C:\\inputfolder\\2.xml");
        System.out.println("2.xml added!");
        dbservice.unmarshallXSDtoModel("res\\template.xsl", "C:\\inputfolder\\2.xml");
        System.out.println("Calculated!");

    }
}
