
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageUploadExecutor {

    private static final String TAG = ImageUploadExecutor.class.getName();
    public static final int MAX_SIZE = 1024;
    public static final int QUALITY = 60;

    private final Executor background;
    private final Executor foreground;
    private final IApiRequest api;
    private final List<OnImagesUploadedListener> listeners;

    public ImageUploadExecutor(IApiRequest api) {
        this(api, new UIExecutor(), Executors.newSingleThreadExecutor());
    }

    public ImageUploadExecutor(IApiRequest api, Executor foreground, Executor background) {
        this.api = api;
        this.foreground = foreground;
        this.background = background;
        listeners = Collections.synchronizedList(new ArrayList<OnImagesUploadedListener>());
    }

    public void addOnImagesUploadedListener(OnImagesUploadedListener listener) {
        listeners.add(listener);
    }

    public void removeOnImagesUploadedListener(OnImagesUploadedListener listener) {
        listeners.remove(listener);
    }

    public void removeAllOnImagesUploadedListener() {
        listeners.clear();
    }

    private void executeOnUI(Runnable runnable) {
        foreground.execute(runnable);
    }


    public Pair<String, String> getFileName(Context context, Uri uri) {
        String prefix = String.valueOf(System.currentTimeMillis() % 10000000);
        String suffix = getFileExtension(context, uri);
        return new Pair<>(prefix, suffix);
    }

    public String getFileExtension(Context context, Uri uri) {
        String extension;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
        return extension;
    }

    private File createTemporaryFile(Context context, Uri uri) {
        OutputStream outputStream = null;
        try(InputStream inputStream = context.getContentResolver().openInputStream(uri)) {
            Pair<String, String> fileName = getFileName(context, uri);
            File file = File.createTempFile(fileName.first, fileName.second, context.getCacheDir());

            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[inputStream.available()];

            inputStream.read(buffer);
            outputStream.write(buffer);

            return new Compressor(context)
                    .setMaxHeight(MAX_SIZE)
                    .setMaxWidth(MAX_SIZE)
                    .setQuality(QUALITY)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .compressToFile(file);
        } catch (IOException e) {
            Logger.e(TAG, e.getMessage());
            return null;
        } finally {
            if (outputStream != null) try {
                outputStream.close();
                outputStream.flush();
            } catch (IOException e) {
                Logger.e(TAG, e.getMessage());
            }
        }
    }

    private PhotoEntity uploadPhoto(Context context, Uri uri) throws IOException {
        File file = createTemporaryFile(context, uri);
        if (file == null) {
            throw new IOException("Temporary file creation failed");
        }
        // creating multipart file
        ContentResolver resolver = context.getContentResolver();
        MediaType fileType = MediaType.parse(resolver.getType(uri));
        RequestBody requestFile = RequestBody.create(fileType, file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData(
                "file",
                file.getName(),
                requestFile
        );
        try {
            return api.uploadFile(body).execute().body();
        } finally {
            file.delete();
        }
    }

    private PhotoEntity[] uploadPhotosToServer(Context context, Uri... uris) throws IOException {
        final List<PhotoEntity> idList = new ArrayList<>();
        for (Uri uri : uris) {
            PhotoEntity entity = uploadPhoto(context, uri);
            if (entity != null) {
                idList.add(entity);
            }
        }
        return idList.toArray(new PhotoEntity[idList.size()]);
    }

    public void uploadImages(final Context context, final Uri... uris) {
        background.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final PhotoEntity[] uploadedPhotos = uploadPhotosToServer(context, uris);
                    executeOnUI(new Runnable() {
                        @Override
                        public void run() {
                            for (OnImagesUploadedListener listener : listeners) {
                                listener.onImagesUploaded(uploadedPhotos);
                            }
                        }
                    });
                } catch (final IOException e) {
                    Logger.e(TAG, e.getMessage());
                    executeOnUI(new Runnable() {
                        @Override
                        public void run() {
                            for (OnImagesUploadedListener listener : listeners) {
                                listener.onUploadingError(e);
                            }
                        }
                    });
                }
            }
        });
    }

    static class UIExecutor implements Executor {

        final Handler uiHandler;

        UIExecutor() {
            uiHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void execute(@NonNull Runnable command) {
            uiHandler.post(command);
        }
    }

    public interface OnImagesUploadedListener {

        void onImagesUploaded(PhotoEntity... ids);

        void onUploadingError(Throwable t);
    }
}
