package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Cette classe est utilisée pour tester le service Neighbour
 */
@RunWith(JUnit4.class)  // On dit à JUnit de courir avec JUnit4
public class NeighbourServiceTest {

    private NeighbourApiService service;  // Nous définissons le service que nous allons tester

    @Before  // Cette annotation indique que cette méthode doit être exécutée avant chaque test
    public void setup() {
        service = DI.getNewInstanceApiService();  // Nous initialisons le service avant chaque test
    }


    @Test  // Cette annotation indique que la méthode suivante est un test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();  // Nous obtenons la liste des voisins du service
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;  // Nous définissons les voisins que nous attendons
        // Nous vérifions que les voisins que nous avons obtenus correspondent à ceux que nous attendions
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test  // Un test pour vérifier la création d'un voisin
    public void createNeighbourWithSuccess() {
        Neighbour newNeighbour = new Neighbour(999L, "New Neighbour", "http://newneighbour.com", "Location", "+999999999", "About me");
        service.createNeighbour(newNeighbour); // Nous créons un nouveau voisin
        Neighbour addedNeighbour = service.getNeighbourById(999L); // Nous récupérons le voisin à partir de son ID
        assertEquals(newNeighbour, addedNeighbour); // Nous vérifions que le voisin ajouté est bien le nouveau voisin
    }

    @Test  // Un test pour vérifier l'obtention d'un voisin à partir de son ID
    public void getNeighbourByIdWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);  // Nous obtenons le premier voisin de la liste
        Long id = neighbour.getId(); // Nous gardons une trace de son ID
        Neighbour neighbourById = service.getNeighbourById(id); // Nous récupérons le voisin à partir de son ID
        assertEquals(neighbour, neighbourById); // Nous vérifions que le voisin récupéré est bien le même que le voisin initial
    }


    @Test  // Un test pour vérifier que la suppression a été effectuée correctement.
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);  // Nous obtenons le premier voisin de la liste
        service.deleteNeighbour(neighbourToDelete);  // Nous supprimons ce voisin
        // Nous vérifions que le voisin n'est plus dans la liste
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test  // Un test pour vérifier l'obtention des voisins favoris
    public void getFavoriteNeighboursWithSuccess() {
        List<Neighbour> favouriteNeighbours = service.getFavoriteNeighbours(); // Nous récupérons la liste des voisins favoris
        for (Neighbour neighbour : favouriteNeighbours) {
            assertTrue(neighbour.isFavoris());

        }
    }

}
