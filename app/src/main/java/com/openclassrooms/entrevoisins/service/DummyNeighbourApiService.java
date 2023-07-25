package com.openclassrooms.entrevoisins.service;

import android.support.annotation.Nullable;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    @Override
    @Nullable
    public Neighbour getNeighbourById(Long id){
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getId() == id) {
             return neighbour;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    // Liste favoris

    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        List<Neighbour> favoriteList = new ArrayList<>();
        for (Neighbour list : neighbours) {
            if (list.isFavoris()) {
                favoriteList.add(list);
            }
        }
        return favoriteList;
    }

    /**
     * methode de services pour changer le statut favoris
     * @param neighbour to update
     */
    @Override
    public void toggleFavoriteNeighbour(Neighbour neighbour) {
        boolean favoriteToSet = !neighbour.isFavoris();
        neighbours.get(neighbours.indexOf(neighbour)).setFavoris(favoriteToSet);
        neighbour.setFavoris(favoriteToSet);

    }
}
