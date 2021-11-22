package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
}