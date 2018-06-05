
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.requestum.android.motoguy.presentation.view.interfaces.IOnNextScrollListener;

public class PaginationScrollListener extends RecyclerView.OnScrollListener {

    @NonNull
    private final IOnNextScrollListener listener;
    @NonNull
    private int pageSize;

    public PaginationScrollListener(IOnNextScrollListener listener, int pageSize) {
        this.listener = listener;
        this.pageSize = pageSize;
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= pageSize) {
                listener.onGetNext();
            }
        }
    }


}
