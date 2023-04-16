package com.example.assignment.bookmyshow.ui.movieslist;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment.bookmyshow.data.local.model.Movie;
import com.example.assignment.bookmyshow.databinding.ItemMovieSearchBinding;
import com.example.assignment.bookmyshow.ui.moviedetails.DetailsActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MovieSearchViewHolder extends RecyclerView.ViewHolder {

    private final ItemMovieSearchBinding binding;

    public MovieSearchViewHolder(@NonNull ItemMovieSearchBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public void bindTo(final Movie movie) {
        binding.setMovieSearch(movie);
        // movie click event
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_MOVIE_ID, movie.getId());
                view.getContext().startActivity(intent);
            }
        });

        binding.executePendingBindings();
    }

    public static MovieSearchViewHolder create(ViewGroup parent) {
        // Inflate
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Create the binding

        ItemMovieSearchBinding binding =
                ItemMovieSearchBinding.inflate(layoutInflater, parent, false);
        return new MovieSearchViewHolder(binding);
    }
}
