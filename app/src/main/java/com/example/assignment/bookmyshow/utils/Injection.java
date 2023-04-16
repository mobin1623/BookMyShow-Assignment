package com.example.assignment.bookmyshow.utils;

import android.content.Context;

import com.example.assignment.bookmyshow.data.MovieRepository;
import com.example.assignment.bookmyshow.data.local.MoviesDatabase;
import com.example.assignment.bookmyshow.data.local.MoviesLocalDataSource;
import com.example.assignment.bookmyshow.data.remote.MoviesRemoteDataSource;
import com.example.assignment.bookmyshow.data.remote.api.ApiClient;
import com.example.assignment.bookmyshow.data.remote.api.MovieService;

/**
 * Class that handles object creation.
 *
 * 
 */
public class Injection {

    /**
     * Creates an instance of MoviesRemoteDataSource
     */
    public static MoviesRemoteDataSource provideMoviesRemoteDataSource() {
        MovieService apiService = ApiClient.getInstance();
        AppExecutors executors = AppExecutors.getInstance();
        return MoviesRemoteDataSource.getInstance(apiService, executors);
    }

    /**
     * Creates an instance of MoviesRemoteDataSource
     */
    public static MoviesLocalDataSource provideMoviesLocalDataSource(Context context) {
        MoviesDatabase database = MoviesDatabase.getInstance(context.getApplicationContext());
        return MoviesLocalDataSource.getInstance(database);
    }

    /**
     * Creates an instance of MovieRepository
     */
    public static MovieRepository provideMovieRepository(Context context) {
        MoviesRemoteDataSource remoteDataSource = provideMoviesRemoteDataSource();
        MoviesLocalDataSource localDataSource = provideMoviesLocalDataSource(context);
        AppExecutors executors = AppExecutors.getInstance();
        return MovieRepository.getInstance(
                localDataSource,
                remoteDataSource,
                executors);
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        MovieRepository repository = provideMovieRepository(context);
        return ViewModelFactory.getInstance(repository);
    }
}
