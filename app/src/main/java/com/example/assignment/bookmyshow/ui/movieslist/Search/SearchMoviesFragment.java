package com.example.assignment.bookmyshow.ui.movieslist.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment.bookmyshow.R;
import com.example.assignment.bookmyshow.data.local.model.Movie;
import com.example.assignment.bookmyshow.data.local.model.Resource;
import com.example.assignment.bookmyshow.ui.movieslist.MoviesActivity;
import com.example.assignment.bookmyshow.ui.movieslist.MoviesFilterType;
import com.example.assignment.bookmyshow.ui.movieslist.discover.DiscoverMoviesViewModel;
import com.example.assignment.bookmyshow.utils.Injection;
import com.example.assignment.bookmyshow.utils.ItemOffsetDecoration;
import com.example.assignment.bookmyshow.utils.UiUtils;
import com.example.assignment.bookmyshow.utils.ViewModelFactory;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchMoviesFragment extends Fragment {

    private DiscoverMoviesViewModel viewModel;

    public static SearchMoviesFragment newInstance() {
        return new SearchMoviesFragment();
    }
    public PagedList<Movie> pagedList;
    SearchMoviesAdapter discoverMoviesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_search_movies, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = obtainViewModel(getActivity());
        setupListAdapter();


        ((MoviesActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.search));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main, menu);
        UiUtils.tintMenuIcon(getActivity(), menu.findItem(R.id.action_sort_by), R.color.md_white_1000);

        menu.findItem(R.id.app_bar_search).setVisible(true);

        if (viewModel.getCurrentSorting() == MoviesFilterType.POPULAR) {
            menu.findItem(R.id.action_popular_movies).setChecked(true);
        } else if (viewModel.getCurrentSorting() == MoviesFilterType.TOP_RATED) {
            menu.findItem(R.id.action_top_rated).setChecked(true);
        } else {
            menu.findItem(R.id.action_now_playing).setChecked(true);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getGroupId() == R.id.menu_sort_group) {
            viewModel.setSortMoviesBy(item.getItemId());
            item.setChecked(true);
        }

        return super.onOptionsItemSelected(item);
    }

    public static DiscoverMoviesViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = Injection.provideViewModelFactory(activity);
        return ViewModelProviders.of(activity, factory).get(DiscoverMoviesViewModel.class);
    }

    private void setupListAdapter() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.rv_movie_list);

        discoverMoviesAdapter = new SearchMoviesAdapter(viewModel);
        final GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.span_count));

        // draw network status and errors messages to fit the whole row(3 spans)
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (discoverMoviesAdapter.getItemViewType(position)) {
                    case R.layout.item_network_state:
                        return layoutManager.getSpanCount();
                    default:
                        return 1;
                }
            }
        });

        // setup recyclerView
        recyclerView.setAdapter(discoverMoviesAdapter);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

        // observe paged list
        viewModel.getPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                discoverMoviesAdapter.submitList(movies);

                pagedList = movies;
            }

        });

        // observe network state
        viewModel.getNetworkState().observe(getViewLifecycleOwner(), new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {
                discoverMoviesAdapter.setNetworkState(resource);
            }
        });
    }





}
