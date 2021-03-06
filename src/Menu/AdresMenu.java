package Menu;
/**
 *
 * @author Sonja
 */

import formatMessage.VerifyInputScanner;
import DAO.MySQL.AdresDAOMySQL;
import POJO.Adres;
import formatMessage.PrintFormat;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AdresMenu {
    public static void startMenu() {
        Scanner input = new Scanner(System.in);
        while (true) {
            
            PrintFormat.printHeader("ADRESMENU");
            System.out.println("1: Maak een nieuw adres aan\n"
                    + "\n"
                + "2: Pas een bestaand adres aan (met adres ID)\n"
                    + "\n"
                + "3: Haal alle adresgegevens op\n"
                + "4: Haal een specifiek adres op (met adres ID)\n"
                    + "\n"
                + "5: Verwijder een bestaand adres\n"
                    + "\n"
                + "0: Keer terug naar het Hoofdmenu");
            int select = input.nextInt();
            try {
                switch (select) {
                    case 1:
                        createAdresMenu();
                        startMenu();
                        break;
                    case 2:
                        updateAdresMenu();
                        break;
                    case 3:
                        readAdresMenu();
                        break;
                    case 4:
                        readAdresByIDMenu();
                        break;
                    case 5:
                        deleteAdresMenu();
                        break;
                    case 0:
                        HoofdMenu.startMenu();
                        break;
                    default:
                        System.out.println("Maak een keuze: 1, 2, 3, 4, 5, of 0");
                        break;
                }
            }
            catch(SQLException | ClassNotFoundException ex){
                System.out.println(ex);
            }
        }
    }
    
    /* 
    1. createAdresMenu vraagt de gebruiker om een adres in te voeren
    */
    
    private static void createAdresMenu() throws SQLException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Voer het adres ID in:");
        int adres_id = input.nextInt();
        System.out.println("Voer de straatnaam in:");
        String straatnaam = input.next();
        System.out.println("Voer het huisnummer in:");
        int huisnummer = input.nextInt();
        System.out.println("Voer de toevoeging in:");
        String toevoeging = input.next();
        System.out.println("Voer de postcode in:");
        String postcode = input.next();
        System.out.println("Voer de woonplaats in:");
        String woonplaats = input.next();
        
        Adres adres = new Adres(); //nieuw Adres Object
        
        adres.setAdres_id(adres_id);  //fill'er up
        adres.setStraatnaam(straatnaam);
        adres.setHuisnummer(huisnummer);
        adres.setToevoeging(toevoeging);
        adres.setPostcode(postcode);
        adres.setWoonplaats(woonplaats);
       
        AdresDAOMySQL.createAdres(adres);
    }
    
    /*
    2. readAdresMenu geeft een ArrayList met alle adressen in de database terug
    */
    
    private static void readAdresMenu() throws SQLException {
        
        AdresDAOMySQL aDAO = new AdresDAOMySQL();
        ArrayList<Adres> adresgegevens = AdresDAOMySQL.readAdres();
        System.out.println("ADRESGEGEVENS \n"
            + "----------------");
        System.out.printf("%15s %15s %15s %15s %15s %15s \n", 
                "Adres ID", "Straatnaam", "Huisnummer", "Toevoeging",
                "Postcode", "Woonplaats");
        for (Adres a : adresgegevens) {
        /*    System.out.println("Adres ID: " + a.getAdres_id()
            + "Straatnaam: " + a.getStraatnaam()
            + "Huisnummer: " + a.getHuisnummer()
            + "Toevoeging: " + a.getToevoeging()
            + "Postcode: " + a.getPostcode()
            + "Woonplaats: " + a.getWoonplaats());
        */
            System.out.printf("%15d %15s %15d %15s %15s %15s\n",
                    a.getAdres_id(), a.getStraatnaam(), a.getHuisnummer(), 
                    a.getToevoeging(), a.getPostcode(), a.getWoonplaats());
        }
    }
    
    /*
    3. readAdresByIDMenu geeft een specifiek adres terug op basis van adres_id
    */
    
    private static void readAdresByIDMenu() throws SQLException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Voer het adres ID in: ");
        int adresID = input.nextInt();
        
        AdresDAOMySQL aDAO = new AdresDAOMySQL();
        Adres adresGegevens = AdresDAOMySQL.readAdresByID(adresID);
            System.out.println("Adres ID: " + adresGegevens.getAdres_id() + "\n"
            + "Straatnaam: " + adresGegevens.getStraatnaam() + "\n"
            + "Huisnummer: " + adresGegevens.getHuisnummer() + "\n"
            + "Toevoeging: " + adresGegevens.getToevoeging() + "\n"
            + "Postcode: " + adresGegevens.getPostcode() + "\n"
            + "Woonplaats: " + adresGegevens.getWoonplaats() + "\n");
    }

    /*
    4. updateAdresMenu stelt de gebruiker in staat om een bestaand adres aan te passen
    */
    
    private static void updateAdresMenu() throws SQLException {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Welk adres wilt u aanpassen? \n"
                + "Voer het adres ID in: ");
        int adres_id = input.nextInt();
        
        Adres adres = AdresDAOMySQL.readAdresByID(adres_id);  

        System.out.println("Huidige straatnaam: " + adres.getStraatnaam() + "\n"
                + "Voer een nieuwe straatnaam in: ");
        String straatnaam = VerifyInputScanner.verifyString();
        System.out.println("Huidig huisnummer: " + adres.getHuisnummer() + "\n"
                + "Voer een nieuw huisnummer in: ");
        int huisnummer = input.nextInt();
        System.out.println("Huidige toevoeging: " + adres.getToevoeging() + "\n"
                + "Voer een nieuwe toevoeging in: ");
        String toevoeging = input.next();
        System.out.println("Huidige postcode: " + adres.getPostcode() + "\n"
                + "Voer een nieuwe postcode in: ");
        String postcode = input.next();
        System.out.println("Huidige woonplaats: " + adres.getWoonplaats() + "\n"
                + "Voer een nieuwe woonplaats in: ");
        String woonplaats = input.next();

        adres.setStraatnaam(straatnaam); 
        adres.setHuisnummer(huisnummer);
        adres.setToevoeging(toevoeging);
        adres.setPostcode(postcode);
        adres.setWoonplaats(woonplaats);
        
        AdresDAOMySQL.updateAdres(adres);
        
        //terugkoppeling gebruiker
        System.out.println("Het adres is aangepast.");  
    }
    /*
    private static void updateAdresMenuMetBlancoVelden() throws SQLException {
        Scanner input = new Scanner(System.in);

        System.out.println("Welk adres wilt u updaten? \n"
                + "Voer het adres ID in: ");
        Adres outputAdres = AdresDAO.readAdres(input.nextLine());
    //in bovenstaand zit nog een error vandaar dit in comments
        
        System.out.println("Voer de nieuwe gegevens in "
                + " (laat het veld leeg als er geen verandering nodig is): ");
        System.out.print("Straatnaam: ");
        String nieuweStraatnaam = input.nextLine();
        if ( !nieuweStraatnaam.equals("") ) {
            outputAdres.setStraatnaam(nieuweStraatnaam);
                }
        System.out.print("Huisnummer: ");
        int nieuwHuisnummer = input.nextInt();
        if ( !nieuwHuisnummer.equals("") ) {
            outputAdres.setHuisnummer(nieuwHuisnummer);
                }
        System.out.print("Toevoeging: ");
        String nieuweToevoeging = input.nextLine();
        if ( !nieuweToevoeging.equals("") ) {
            outputAdres.setToevoeging(nieuweToevoeging);
                }
        System.out.print("Postcode: ");
        String nieuwePostcode = input.nextLine();
        if ( !nieuwePostcode.equals("") ) {
            outputAdres.setPostcode(nieuwePostcode);
                }
        System.out.print("Woonplaats: ");
        String nieuweWoonplaats = input.nextLine();
        if ( !nieuweWoonplaats.equals("") ) {
            outputAdres.setWoonplaats(nieuweWoonplaats);
                }

        AdresDAO.updateAdres(outputAdres);
    
        System.out.println("Het adres is aangepast.");  
    */
    
    /*
    deleteAdresMenu verwijdert een adres uit de database
    */
    
    private static void deleteAdresMenu() {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Welk adres wilt u verwijderen? \n"
                + "Voer het adres ID in: ");
        int adres_id = input.nextInt();
        
        AdresDAOMySQL aDAO = new AdresDAOMySQL();
        AdresDAOMySQL.deleteAdres(adres_id);
        
        //terugkoppeling gebruiker 
        System.out.println("Het volgende adres is verwijderd: " + adres_id); 
    }
}
