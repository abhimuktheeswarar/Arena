package msa.arena.base;


/**
 * Created by Abhimuktheeswarar on 20-09-2016.
 */


public interface BasePresenterInterface {

    void initializePresenter();

    void onRefresh();

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
