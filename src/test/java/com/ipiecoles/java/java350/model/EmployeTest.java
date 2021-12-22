package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

class EmployeTest {

    @Test
    void getNombreAnneeAnciennete() {
        //Given
        Employe employe = new Employe("Test", "Test", "TTGGTT", LocalDate.now(),2500d, 1, 1.0 );
        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    void getNombreAnneeAncienneteWithPastDateEmbauche() {
        //Given
        Employe employe = new Employe("Test2", "Test2", "TTGGTT2", LocalDate.now().minusYears(5),2000d, 2, 0.75 );
        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(5);
    }

    @Test
    void getNombreAnneeAncienneteWithFutureDateEmbauche() {
        //Given
        Employe employe = new Employe("Test3", "Test3", "TTGGTT3", LocalDate.now().plusYears(5),1500d, 3, 0.50 );
        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    void getNombreAnneeAncienneteWithDateEmbaucheNull() {
        //Given
        Employe employe = new Employe("Test3", "Test3", "TTGGTT3", null,1500d, 3, 0.50 );
        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(nbAnnees).isNull();
    }

    @Test
    void getPrimeAnnuelle() {
        //Given
        Employe employe = new Employe("Manager", "Manager", "MPRTT", LocalDate.now().minusYears(5),3500d, 1, 1.0 );
        //When
        Double prime = employe.getPrimeAnnuelle();
        //Then
        Assertions.assertThat(prime).isEqualTo(2200);
    }

    @ParameterizedTest(name= "Prime pour un employé de matricule {0}, ancienneté {1}, performance {2}, temps partiel {3} = {4}" )
    @CsvSource({
            "'MPOTR', 0, 1, 1.0, 1700",
            "'MTEST', 5, 1, 1.0, 2200",
            "'MTEST', 5, 1, 0.5, 1100"
    })
    void getPrimeAnnuelle(String matricule, Integer nbAnneesAnciennete, Integer performance, Double tempsPartiel, Double primeCalculee) {
        //Given
        Employe employe = new Employe("Manager", "Manager", matricule, LocalDate.now().minusYears(nbAnneesAnciennete),3500d, performance, tempsPartiel );
        // When
        Double prime = employe.getPrimeAnnuelle();
        // Then
        Assertions.assertThat(prime).isEqualTo(primeCalculee);
    }

    @Test
    public void testAugmenterSalaireManager(){
        //Given
        Poste poste = Poste.MANAGER;
        Double salaire = 4000.0;
        LocalDate dateEmbauche = LocalDate.now().minusYears(6);

        //Initialise l'employé à partir des données d'entrée
        Employe employe = new Employe("Test", "Manager", "M1100",
                dateEmbauche, salaire, 1, 1.0);

        //When

        Double newSalaire = employe.augmenterSalaire(10.0);

        //Then
        // 4000 + 20%
        Assertions.assertThat(newSalaire).isEqualTo(4400);
    }

    @Test
    public void testAugmenterSalaireTechnicien(){
        //Given
        Poste poste = Poste.TECHNICIEN;
        Double salaire = 1500.0;
        LocalDate dateEmbauche = LocalDate.now().minusYears(5);

        //Initialise l'employé à partir des données d'entrée
        Employe employe = new Employe("Test", "Technicien", "T111000",
                dateEmbauche, salaire, 1, 1.0);

        //When
        Double newSalaire = employe.augmenterSalaire(5.0);

        //Then
        // 1500 + 10%
        Assertions.assertThat(newSalaire).isEqualTo(1575.0);
    }

    @Test
    public void testAugmenterSalaireCommercial(){
        //Given
        Poste poste = Poste.COMMERCIAL;
        Double salaire = 2500.0;
        LocalDate dateEmbauche = LocalDate.now().minusYears(1);

        //Initialise l'employé à partir des données d'entrée
        Employe employe = new Employe("Test", "Commercial", "C000111",
                dateEmbauche, salaire, 1, 1.0);

        //When
        Double newSalaire = employe.augmenterSalaire(8.0);

        //Then
        // 2500 + 15%
        Assertions.assertThat(newSalaire).isEqualTo(2700);
    }

    @Test
    public void testAugmenterSalaireNouvelEmploye(){
        //Given
        Poste poste = Poste.COMMERCIAL;
        Double salaire = 2100.0;
        LocalDate dateEmbauche = LocalDate.now();

        //Initialise l'employé à partir des données d'entrée
        Employe employe = new Employe("Test", "EmployeNew", "E000111",
                dateEmbauche, salaire, 1, 1.0);

        //When
        Double newSalaire = employe.augmenterSalaire(0.0);

        //Then
        // 2100 + 0%
        Assertions.assertThat(newSalaire).isEqualTo(2100);
    }

    @ParameterizedTest(name = "matricule {0}, salaire {1}, pourcentage d'augmentation {2}, Integer nbAnneeAnciennete {3} => Salaire final {4}")
    @CsvSource({
            "'M112002',4000,10.0,5,4400.0", // Manager avec ancienneté
            "'T112002',1000,5.0,10,1050.0", // Technicien avec ancienneté
            "'C112002',2000,8.0,1,2160.0",  // Commercial avec ancienneté
            "'C112002',2000,8.0,0,2000.0",  // Commercial sans ancienneté
            "'E112002',2000,8.0,0,2000.0",  // Employé autre sans ancienneté
            ",2000,10.0,0,2000.0",          // Matricule null

    })
    public void testAugmenterSalaire(String matricule, Double salaire, Double pourcentage, Integer nbAnneesAnciennete, Double newSalaire) {

        //Given
        // 4 données d'entrée => remplacer par les paramètres
        LocalDate dateEmbauche = LocalDate.now().minusYears(nbAnneesAnciennete);
        //Initialise l'employé à partir des données d'entrée
        Employe employe = new Employe("Test", "Test", matricule,
                dateEmbauche, salaire, 1, 1.0);
        ///When
        Double salaireEstime = employe.augmenterSalaire(pourcentage);

        //Then
        Assertions.assertThat(salaireEstime).isEqualTo(newSalaire);
    }
}