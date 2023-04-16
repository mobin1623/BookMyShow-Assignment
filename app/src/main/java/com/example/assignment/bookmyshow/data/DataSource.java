package com.example.assignment.bookmyshow.data;

import com.example.assignment.bookmyshow.data.local.model.Movie;
import com.example.assignment.bookmyshow.data.local.model.MovieDetails;
import com.example.assignment.bookmyshow.data.local.model.RepoMoviesResult;
import com.example.assignment.bookmyshow.data.local.model.Resource;
import com.example.assignment.bookmyshow.ui.movieslist.MoviesFilterType;

import java.util.List;

import androidx.lifecycle.LiveData;


public interface DataSource {

    LiveData<Resource<MovieDetails>> loadMovie(long movieId);

    RepoMoviesResult loadMoviesFilteredBy(MoviesFilterType sortBy);

    LiveData<List<Movie>> getAllFavoriteMovies();

    void favoriteMovie(Movie movie);

    void unfavoriteMovie(Movie movie);
}
