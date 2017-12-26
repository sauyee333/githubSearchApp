package za.co.riggaroo.gus.presentation.base;

/**
 * Created by sauyee on 25/12/17.
 */

public interface MvpPresenter<V extends MvpView> {
    void attachView(V mvpView);

    void detachView();
}
