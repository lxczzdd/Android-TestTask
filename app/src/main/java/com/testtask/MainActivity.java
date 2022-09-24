package com.testtask;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.testtask.api.ApiUtilities;
import com.testtask.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private ArrayList<ImageModel> imageModels;
    private RecyclerViewAdapter imageRVAdapter;
    private GridLayoutManager manager;
    private ProgressDialog progressDialog;
    private RecyclerView imagesRV;

    private int page = 1;
    private int pageSize = 30;
    private boolean isLoading;
    private boolean isLastPage;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imagesRV = findViewById(R.id.recyclerView);
        imageModels = new ArrayList<>();
        imageRVAdapter = new RecyclerViewAdapter(this, imageModels);
        manager = new GridLayoutManager(this, 1);
        imagesRV.setLayoutManager(manager);
        imagesRV.setHasFixedSize(true);
        imagesRV.setAdapter(imageRVAdapter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();



        imagesRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItem = manager.getChildCount();
                int totalItem = manager.getItemCount();
                int firstVisibleItemPos = manager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItem + firstVisibleItemPos >= totalItem)
                            && firstVisibleItemPos >= 0
                            && totalItem >= pageSize) {
                        page++;
                        getData();
                    }
                }
            }
        });

        getData();
    }





    private void getData() {
        ApiUtilities.getApiInterface()
                .getImages(page, 30)
                .enqueue(new Callback<List<ImageModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ImageModel>> call, @NonNull Response<List<ImageModel>> response) {
                        if (response.body() != null) {
                            imageModels.addAll(response.body());
                            imageRVAdapter.notifyDataSetChanged();
                        }
                        isLoading = false;
                        progressDialog.dismiss();

                        if (imageModels.size() > 0) {
                            isLastPage = imageModels.size() < pageSize;
                        } else
                            isLastPage = true;
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ImageModel>> call, @NonNull Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }

}
