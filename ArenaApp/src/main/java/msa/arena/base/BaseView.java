
package msa.arena.base;

import android.content.Context;

/**
 * Interface representing a View that will use to load data.
 */
public interface BaseView {
    /** Show a view with a progress bar indicating a loading process. */
  /*
  void showLoading();

  */
    /** Hide a loading view. */
  /*
  void hideLoading();

  */
    /** Show a retry view in case of an error when retrieving data. */
  /*
  void showRetry();

  */

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
  /*
  void hideRetry();*/

    void onError(String message);

    void hideError();

    /**
     * Get a {@link Context}.
     */
    Context context();

    //Activity getCurrentActivity();
}
