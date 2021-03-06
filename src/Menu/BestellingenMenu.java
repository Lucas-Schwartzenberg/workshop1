package Menu;

import formatMessage.VerifyInputScanner;
import POJO.Artikel;
import DAO.MySQL.BestellingDAOMySQL;
import DAO.MySQL.BestellingArtikelDAOMySQL;
import POJO.Bestelling;
import DAO.MySQL.ArtikelDAOMySQL;
import POJO.BestellingArtikel;
import formatMessage.PrintFormat;
import java.util.ArrayList;
import java.util.Scanner;
import static DAO.MySQL.BestellingArtikelDAOMySQL.readKoppelMetBestellingID;

/**
 *
 * @author Peter
 */
public class BestellingenMenu {
    
    public static void startMenu() {
        Scanner input = new Scanner(System.in);
        while(true){
            PrintFormat.printHeader("BESTELLINGMENU");        
            System.out.println("1: Maak een nieuwe bestelling aan\n"
                    + "\n"
                + "2: Voeg een artikel aan een bestelling toe (met bestelling ID) \n"
                + "3: Pas het aantal bestelde artikelen aan (met bestelling ID en artikel ID) \n"
                    + "\n"
                + "4: Haal alle bestellingen van alle klanten op \n"
                + "5: Haal een specifieke bestelling op (met bestelling ID) \n"
                + "6: Haal alle bestellingen van een specifieke klant op (met klant ID) \n"
                + "7: Haal alle artikelen van een specifieke bestelling op (met bestelling ID) \n"
                    + "\n"
                + "8: Verwijder één artikel uit een bestelling (met bestelling ID en artikel ID) \n"
                + "9: Verwijder een bestaande bestelling (met bestelling ID) \n"
                    + "\n"
                + "0: Keer terug naar het Hoofdmenu");
            int select = input.nextInt();
            
            try {
                switch (select) {
                  case 1:
                        createNieuweBestellingMenu();
                        startMenu();
                        break;
                    case 2:
                        createBestellingMetArtikelMenu();
                        break;
                    case 3:
                        updateBestellingAantalMenu();
                        break;
                    case 4:
                        readAllBestellingenMenu();
                        break;
                    case 5:
                        readBestellingByIdMenu();
                        break;
                    case 6:
                        readBestellingByKlantIdMenu();
                        break;
                    case 7:
                        readArtikelenInBestellingMenu();
                        break;
                    case 8:
                        deleteArtikelUitBestellingMenu();
                        break;
                    case 9:
                        deleteBestellingByIdMenu();
                        break;
                    case 0:
                        HoofdMenu.startMenu();
                        break;
                    default:
                        System.out.println("Maak een keuze: 1, 2, 3, 4, 5, "
                            + "6, 7, 8, 9 of 0");
                        break;
                    }
                System.out.println("Druk op enter om door te gaan.");
            // iets invoegen zodat het programma even stopt
                 }   
            
            catch(Exception ex) {
                System.out.println("Probeer opnieuw.");
                ex.printStackTrace();
            }
        }
    }
    
    /**
     *  1) createMenu maakt een nieuwe bestelling aan en roept de methode XXXX aan die
     * een bestaand artikel toevoegt aan deze bestelling
     * 
     * samenvatting: Maak een nieuwe bestelling + voeg een artikel toe aan de bestelling
     */
    public static void createNieuweBestellingMenu(){
        Scanner input = new Scanner(System.in);
        //maak nieuwe bestelling aan
        Bestelling bestelling = new Bestelling();   
        //vul klant id in
        System.out.print("Voer het klant ID in: ");    
        bestelling.setKlantID(input.nextInt());
        //Verstuur de bestelling naar de database
        Bestelling newBestelling = BestellingDAOMySQL.createBestelling(bestelling);
        
        System.out.println("Log het bestelling ID: " + newBestelling.getBestellingID());
        //voegt bestelling en artikel samen
        createBestellingMetArtikelMenu(newBestelling.getBestellingID()); 
    }
    
    /**
     * 2) createBestelArtikelMenu voegt een artikel toe aan een bestaande bestelling
     */
    public static void createBestellingMetArtikelMenu() {
        Scanner input = new Scanner(System.in);
        BestellingArtikel bestellingArtikel = new BestellingArtikel();
        System.out.print("Voer het bestelling ID in: ");
        bestellingArtikel.setBestelling_id(input.nextInt());
        System.out.print("Voer het artikel ID in: ");
        bestellingArtikel.setArtikel_id(input.nextInt());
        System.out.print("Voer het aantal artikelen in: ");
        bestellingArtikel.setAantal(input.nextInt());
        new BestellingArtikelDAOMySQL().createKoppelBestellingArtikel(bestellingArtikel);
    }
    
     //3) updateBestellingAantalMenu update het aantal te bestellen artikelen 
    //    van een bestelling
    public static void updateBestellingAantalMenu(){
        System.out.println("Voer het bestelling ID in: ");
        int bestellingId = VerifyInputScanner.verifyInt();
        System.out.println("Voer het artikel ID in: ");
        int artikelId = VerifyInputScanner.verifyInt();
        BestellingArtikel koppel = new BestellingArtikelDAOMySQL().readKoppel
            (bestellingId, artikelId);
        
        System.out.println("Voer het aantal dat u wilt bestellen in: ");
        koppel.setAantal(VerifyInputScanner.verifyInt());
        
        BestellingArtikelDAOMySQL.updateKoppel(koppel);
    }
    
    //4. readAllBestellingenMenu overzicht van alle bestellingen van alle bestelling van alle klanten
    public static void readAllBestellingenMenu(){
        ArrayList<Bestelling> list = BestellingDAOMySQL.getAllBestelling();
        
        System.out.printf("%15s %15s\n", "Bestelling ID", "Klant ID");
        for(Bestelling e : list){
            System.out.printf("%15d %15d\n",e.getBestellingID(), e.getKlantID());
        }
    }
    
    //5. readBestellingByIdMenu geeft voor een gegeven bestelling de hele bestelling terug 
    //(Klant ID, Totaalprijs, etc)
    public static void readBestellingByIdMenu(){
        Scanner input = new Scanner(System.in);
        
        //verkrijg data uit de commandline
        System.out.println("Voer het bestelling ID in: ");
        int BestellingId = input.nextInt();
        
        Bestelling bestelling = BestellingDAOMySQL.getBestellingById(BestellingId);
        System.out.println("Bestelling ID: " + bestelling.getBestellingID() + " " + 
                "Klant ID: " + bestelling.getKlantID());
        
    }
    
    //6. readBestellingByKlantIdMenu Geeft een lijst van bestellingen terug 
    //   van een klant, op basis van klantID
    public static void readBestellingByKlantIdMenu(){
        Scanner input = new Scanner(System.in);
        
        //verkrijg data uit de commandline
        System.out.println("Voer het klant ID in: ");
        int klantId = input.nextInt();
        ArrayList<Bestelling> list = BestellingDAOMySQL.getBestellingByKlantId(klantId);
        System.out.println("\n"
                + "LIJST MET BESTELLINGEN VAN KLANT " + klantId + "\n");
        for(Bestelling e : list){
            System.out.println("Bestelling ID: " + e.getBestellingID() + 
                    "; Klant ID: " + e.getKlantID());
        }
    }   
   
    //7. readArtikelenInBestellingMenu geeft een lijst terug met bestelde 
    //   artikelen van 1 bestelling op basis van bestelling ID
    public static void readArtikelenInBestellingMenu(){
        Scanner input = new Scanner(System.in);
        System.out.println("Voer het bestelling ID in: ");
        ArrayList<BestellingArtikel> lijst = readKoppelMetBestellingID(input.nextInt());
        System.out.printf("%15s %15s %15s %15s %15s\n", "Koppel ID", "Artikel ID", 
                "Aantal", "Artikelnaam", "Artikelprijs");
        for(BestellingArtikel e : lijst){
             Artikel artikel = ArtikelDAOMySQL.readArtikel(e.getArtikel_id());
             System.out.printf("%15s %15d %15s %15s %15s\n",e.getKoppel_id(), 
                     e.getArtikel_id(), e.getAantal(), artikel.getArtikel_naam(), 
                     artikel.getArtikel_prijs());
             
        }
    } 
    
    //8. deleteArtikelUitBestellingMenu verwijdert een artikel uit een bestelling
    public static void deleteArtikelUitBestellingMenu(){
        System.out.println("Voer het bestelling ID in: ");
        int bestellingId = VerifyInputScanner.verifyInt();
        System.out.println("Voer het artikel ID in: ");
        int artikelId = VerifyInputScanner.verifyInt();
        BestellingArtikelDAOMySQL.deleteKoppel(bestellingId, artikelId);
    }

    //9. deleteBestellingByIdMenu verwijdert een bestelling op basis van bestellingID
    public static void deleteBestellingByIdMenu(){
        Scanner input = new Scanner(System.in);
        
        //verkrijg data uit de commandline
        System.out.println("Voer het bestelling ID in: ");
        int bestellingId = input.nextInt();
        
        //verwijder alle artikelen die bij deze bestelling horen
        BestellingArtikelDAOMySQL.deleteKoppelMetBestellingID(bestellingId);
        
        //verwijder de bestelling
        BestellingDAOMySQL.deleteBestelling(bestellingId);
        
        System.out.println("De volgende bestelling is verwijderd: " +
                bestellingId);
    }
    
        
    /**
     * Alleen automatisch 
     * createBestelArtikelMenu koppelt artikelen en bestellingen aan elkaar
     * en 1 methode met argumenten voor automatische invoer
     */
    public static void createBestellingMetArtikelMenu(int bestellingID){
        Scanner input = new Scanner(System.in);
        BestellingArtikel bestellingArtikel = new BestellingArtikel();
        bestellingArtikel.setBestelling_id(bestellingID);
        System.out.print("Voer het artikel ID in: ");
        bestellingArtikel.setArtikel_id(input.nextInt());
        System.out.print("Voer het aantal in: ");
        bestellingArtikel.setAantal(input.nextInt());
        BestellingArtikelDAOMySQL.createKoppelBestellingArtikel(bestellingArtikel);
        
    }
    
    
}

    

