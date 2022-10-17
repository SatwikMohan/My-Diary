package com.gigawattstechnology.mydiary.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.gigawattstechnology.mydiary.QutationApiInterface;
import com.gigawattstechnology.mydiary.QutationModal;
import com.gigawattstechnology.mydiary.RetrofitInstance;
import com.gigawattstechnology.mydiary.databinding.FragmentSlideshowBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlideshowFragment extends Fragment {
//Quotations
    private FragmentSlideshowBinding binding;
    QutationApiInterface qutationApiInterface;
    List<QutationModal> list;
    RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        recyclerView= binding.QutationRecyclerView;
        View root = binding.getRoot();

        qutationApiInterface= RetrofitInstance.getRetrofit().create(QutationApiInterface.class);
        qutationApiInterface.getPosts().enqueue(new Callback<List<QutationModal>>() {
            @Override
            public void onResponse(Call<List<QutationModal>> call, Response<List<QutationModal>> response) {

                list=response.body();
                Toast.makeText(root.getContext(), list.get(1).getA(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<QutationModal>> call, Throwable t) {
                Toast.makeText(root.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}