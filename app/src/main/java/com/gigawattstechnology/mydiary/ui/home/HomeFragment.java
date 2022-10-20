package com.gigawattstechnology.mydiary.ui.home;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gigawattstechnology.mydiary.AppDatabase;
import com.gigawattstechnology.mydiary.PlanAdapter;
import com.gigawattstechnology.mydiary.PlanDao;
import com.gigawattstechnology.mydiary.PlanData;
import com.gigawattstechnology.mydiary.R;
import com.gigawattstechnology.mydiary.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
// My plans
    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    PlanAdapter planAdapter;
    List<PlanData> list;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        recyclerView= binding.planRecyclerView;

        View root = binding.getRoot();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        AppDatabase appDatabase=AppDatabase.getDbInstance(root.getContext());
        list=appDatabase.planDao().getAllPlans();


        RequestQueue requestQueue;
        requestQueue= Volley.newRequestQueue(root.getContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, "https://zenquotes.io/api/today",
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject=response.getJSONObject(0);
                    String quote=jsonObject.getString("q");
                    String by=jsonObject.getString("a");
                    binding.quoteToday.setText(quote);
                    binding.quoteTodayBy.setText(by);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);

        binding.createPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView=LayoutInflater.from(root.getContext()).inflate(R.layout.plan_dialog,null);
                TextView date=dialogView.findViewById(R.id.plan_date);
                EditText text=dialogView.findViewById(R.id.plan_text);
                Date today=new Date(System.currentTimeMillis());
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                date.setText(format.format(today));
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(requireContext());
                alertDialog.setView(dialogView).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String plan=text.getText().toString();
                        String planDate=date.getText().toString();
                        AppDatabase appDatabase=AppDatabase.getDbInstance(root.getContext());
                        PlanData planData=new PlanData();
                        planData.Date=planDate;
                        planData.plan=plan;
                        appDatabase.planDao().InsertPlan(planData);
                        list.clear();
                        list=appDatabase.planDao().getAllPlans();
                        planAdapter=new PlanAdapter(root.getContext(),list);
                        recyclerView.setAdapter(planAdapter);


                    }
                }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

        if(list.size()>0){
            planAdapter=new PlanAdapter(root.getContext(),list);
            recyclerView.setAdapter(planAdapter);
        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}