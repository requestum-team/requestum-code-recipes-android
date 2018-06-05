
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.collect.Lists;
import com.requestum.android.motoguy.data.rest.IApiRequest;
import com.requestum.android.motoguy.presentation.base.BasePresenter;
import com.requestum.android.motoguy.presentation.dagger.AppComponent;
import com.requestum.android.motoguy.presentation.interfaces.contracts.PictureChoosingContract;
import com.requestum.android.motoguy.tools.ImageUploadExecutor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class PictureChoosingPresenter extends BasePresenter<PictureChoosingContract.View>
        implements PictureChoosingContract.Presenter {

    private static final String TAG = "PICTURES_PRESENTER";

    @NonNull
    private final Context context;
    @Nullable
    private final List<Uri> temporaryImageFiles;
    @NonNull
    private final ImageUploadExecutor executor;

    @Inject
    public PictureChoosingPresenter(Context context, IApiRequest api) {
        this.context = context;
        temporaryImageFiles = new ArrayList<>();
        executor = new ImageUploadExecutor(api);
    }

    @Override
    public void onImageChosen(Uri photo) {
        temporaryImageFiles.add(photo);
    }

    @Override
    public void setImages(Uri... uris) {
        temporaryImageFiles.clear();
        temporaryImageFiles.addAll(Lists.newArrayList(uris));
    }

    @NonNull
    public void uploadSingleImage(Uri uri) {
        executor.uploadImages(context, uri);
    }

    @Override
    public void removeUri(int index) {
        temporaryImageFiles.remove(index);
    }

    @Override
    public void sendPictures() {
        Uri[] uris = temporaryImageFiles.toArray(new Uri[temporaryImageFiles.size()]);
        executor.uploadImages(context, uris);
    }

    @Override
    protected void injectComponent(AppComponent component) {
        /* IGNORED */
    }

    public void addOnImagesUploadedListener(ImageUploadExecutor.OnImagesUploadedListener listener) {
        executor.addOnImagesUploadedListener(listener);
    }

    public void removeOnImagesUploadedListener(ImageUploadExecutor.OnImagesUploadedListener listener) {
        executor.removeOnImagesUploadedListener(listener);
    }

    public void removeAllListeners() {
        executor.removeAllOnImagesUploadedListener();
    }

    public void putPhotosToBundle(@NonNull String key, @NonNull Bundle bundle) {
        bundle.putParcelableArray(key, temporaryImageFiles.toArray(new Uri[temporaryImageFiles.size()]));
    }
}

//use presenter:
//    picturePresenter.setImages((Uri[]) infoToSend.getParcelableArray(KEY_CAR_SERVICE_PHOTO_LIST));
//    picturePresenter.sendPictures();
//
//@Override
//    protected void onResume() {
//        super.onResume();
//
//        ((PictureChoosingPresenter)picturePresenter).addOnImagesUploadedListener(this);
//    }
//
//    @Override
//    protected void onPause() {
//        ((PictureChoosingPresenter)picturePresenter).removeOnImagesUploadedListener(this);
//        super.onPause();
//    }
