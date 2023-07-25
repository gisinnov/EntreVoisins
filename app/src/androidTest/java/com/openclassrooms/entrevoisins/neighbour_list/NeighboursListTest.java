
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;




/**
 * Classe de test pour la liste des voisins
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // Ceci est fixe
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    // Nous définissons une règle qui indique qu'avant chaque test, l'activité ListNeighbourActivity sera lancée.

    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    // Avant chaque test, nous initialisons notre activité et nous assurons qu'elle n'est pas null.

    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * Nous nous assurons que notre recyclerview affiche au moins un élément
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {

         // On utilise Espresso pour trouver notre RecyclerView par son id,
        // et vérifie qu'il contient au moins un élément (enfant).

        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * Lorsque nous supprimons un élément, l'élément n'est plus affiché
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Étant donné que nous supprimons l'élément à la position 2,
        // nous nous assurons d'abord que notre RecyclerView contient le bon nombre d'éléments.

        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // Lorsqu'on effectue un clic sur une icône de suppression,
        // nous utilisons une action personnalisée pour cliquer sur l'élément à la position 1.

        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Ensuite : le nombre d'éléments est 11,
        // donc nous vérifions que notre RecyclerView contient maintenant un élément de moins.

        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void myNeighboursList_showProfileScreen() {
        // Clique sur le deuxième élément de la liste de voisins affichée à l'écran
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Vérifie que l'écran du profil du voisin est affiché après le clic
        onView(ViewMatchers.withId(R.id.neighbour_profile)).check(matches(isDisplayed()));
    }



    @Test
    public void myNeighboursList_showOnlyFavorite() {
        // Ajouter un voisin à la liste des favoris
        onView(withContentDescription("My neighbours")).perform(click());
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(ViewMatchers.withId(R.id.neighbour_favorite_btn)).perform(click());
        pressBack();

        // Ajouter un autre voisin sans le marquer comme favori
        onView(withContentDescription("My neighbours")).perform(click());
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        pressBack();

        // Vérifier que seul le voisin marqué comme favori est affiché dans l'onglet Favoris
        onView(withContentDescription("Favorites")).perform(click());
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(1));
    }


}