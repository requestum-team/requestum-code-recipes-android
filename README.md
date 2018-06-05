# requestum-code-recipes-android
### Application
[App](App/App.java) - base class extends Application
<br>

### Managers

[BaseUseCase](Managers/BaseUseCase.java) - abstract base class for inject components in use cases
<br>
[PermissionRequestUseCase](Managers/PermissionRequestUseCase.java) - check and request permissions
<br>
[ResourceManager](Managers/ResourceManager.java) - create resources instance
<br>
[ThreadExecutor](Managers/ThreadExecutor.java) - executor
<br>

#### Dagger
[ActivityScope](Managers/Dagger/ActivityScope.java) - interface for Activity scope
<br>
[ApplicationScope](Managers/Dagger/ApplicationScope.java) - interface for Application scope
<br>
[AppComponent](Managers/Dagger/AppComponent.java) - interface for root app component
<br>
[AppModule](Managers/Dagger/AppModule.java) - module for app component
<br>

#### Image
[ImagePickerNavigator](Managers/Image/ImagePickerNavigator.java) - navigate to camera, gallery
<br>
[ImageUploadExecutor](Managers/Image/ImageUploadExecutor.java) - creating multipart file, compressing image, upload to server
<br>
[PictureChoosingPresenter](Managers/Image/PictureChoosingPresenter.java) - presenter for ImageUploadExecutor
<br>

#### Repository
[BaseSharePrefs](Managers/Repository/BaseSharePrefs.java) - base class with creating sharedPreferences
<br>
[SharedPrefsManager](Managers/Repository/SharedPrefsManager.java) - class extends BaseSharePrefs, for custom settings
<br>

#### Retrofit
[ApiLogManager](Managers/Retrofit/ApiLogManager.java) - for logging request time
<br>
[RESTInterceptor](Managers/Retrofit/RESTInterceptor.java) - for logging request body, ApiLogManager is used 
<br>
[RESTClient](Managers/Retrofit/RESTClient.java) - call requests to server here
<br>
[RetrofitManager](Managers/Retrofit/RetrofitManager.java) - init Retrofit client
<br>


### Service

#### Location service
[LocationUpdateService](Service/Location/LocationUpdateService.java) - get current location
<br>


### Tools
[CircleTransform](Tools/CircleTransform.java) - for Glide transformations
<br>
[Converter](Tools/Converter.java) - convert px, dp
<br>
[DateConverter](Tools/DateConverter.java) - working with Date, Calendar
<br>
[KeyboardUtils](Tools/KeyboardUtils.java) - show/hide keyboard
<br>
[Logger](Tools/Logger.java) - logger class
<br>
[PaginationScrollListener](Tools/PaginationScrollListener.java) - listener with pagination for recycler view
<br>
[ValidationHelper](Tools/ValidationHelper.java) - validate EditText fields
<br>

### View

#### Dialog
[DialogInfo](Tools/DialogInfo.java) - message dialog
<br>
