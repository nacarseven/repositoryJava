package com.nacarseven.desafioconcrete.presentation.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nacarseven.desafioconcrete.R;
import com.nacarseven.desafioconcrete.presentation.data.entities.Repository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JavaRepositoryActivity extends AppCompatActivity implements JavaRepositoryView {


    //region FIELDS
    private boolean pagingLoading;
    private JavaRepositoryPresenter presenter;

    @BindView(R.id.activity_java_repository_rcv_items)
    RecyclerView rcvItems;
    @BindView(R.id.activity_java_repository_pgb)
    ProgressBar pgbLoading;
    @BindView(R.id.activity_java_repository_tvw_no_data)
    TextView tvwNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_repository);
        ButterKnife.bind(this);

        presenter = new JavaRepositoryPresenter(this);
        presenter.getRepositories(false);
        setupList();

    }

    @Override
    public void showLoading(boolean show) {
        pgbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
        rcvItems.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showPagingLoading(boolean show) {
        pagingLoading = show;

        if (show) {
            rcvItems.post(new Runnable() {
                @Override
                public void run() {
//                    adapter.showLoading();
                }
            });
        } else {
//            adapter.hideLoading();
        }
    }

    @Override
    public void showMessageError() {

    }

    @Override
    public void showTextNoData(boolean show) {
       tvwNoData.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void loadRepositories(List<Repository> repositories) {

    }

    //region PRIVATE METHODS
    private void setupList() {
//        adapter = new RadarSchoolAdapter(new RadarSchoolAdapter.Listener() {
//            @Override
//            public void onAwareAction(int position, RadarGeneric generic) {
//                presenter.updateAwareStatus(position, generic);
//            }
//
//            @Override
//            public void onChatAction() {
//
//            }
//
//            @Override
//            public void onSeeMooreAction(String type, int genericId, int planningId, Year year) {
//                presenter.openDetail(type, genericId, planningId, getContext(), year);
//            }
//        });

        rcvItems.setLayoutManager(new LinearLayoutManager(this));
//        rcvItems.setAdapter(adapter);

        rcvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int visibleThreshold = 5;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) rcvItems.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                if (!pagingLoading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                    presenter.getRepositories(true);
                }
            }
        });
    }
}
