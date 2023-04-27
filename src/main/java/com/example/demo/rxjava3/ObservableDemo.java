package com.example.demo.rxjava3;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.operators.single.SingleToObservable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

public class ObservableDemo {

  //có 5 loại observable là: Observable, Single, Maybe, Flowable, Completable
  //mỗi loại observable sẽ tương ứng với observer như sau:
  // Observable-Observer, Single-SingleObserver, Maybe-MaybeObserver, Flowable-Observer, Completable-CompletableObserver

  /**
   * Observable-Observer demo: có thể phát ra một hoặc nhiều item
   */
  public static void observable() {
    //khởi tạo list string
    List<String> list = new ArrayList<>();
    list.add("MetaMed");
    list.add("YouMed");
    list.add("Hedima");

    //dùng onNext để phát ra mỗi item, onComplete để biết khi nào quá trình hoàn thành
    Observable<String> observable = Observable.create(emitter -> {
      for (String s : list) {
        emitter.onNext(s);
      }
      emitter.onComplete();
    });

    //dùng Observer để handle các item đc phát ra
    Observer<String> observer = new Observer<String>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        System.out.println("onSubscribe");
      }

      @Override
      public void onNext(@NonNull String s) {
        System.out.println("onNext: " + s);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        System.out.println("onError: " + e.getMessage());
      }

      @Override
      public void onComplete() {
        System.out.println("onComplete");
      }
    };

    //đăng ký Observer với Observable
    observable.subscribe(observer);
  }

  /**
   * Single-SingleObserver: phát ra 1 item duy nhất
   */
  public static void single() {
    String s = "MetaMed";
    Single<String> single = Single.create(emitter -> {
      emitter.onSuccess(s);
    });
    SingleObserver<String> singleObserver = new SingleObserver<String>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        System.out.println("onSubscribe");
      }

      @Override
      public void onSuccess(@NonNull String s) {
        System.out.println("onSuccess: " + s);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        System.out.println("onError: " + e.getMessage());
      }
    };
    single.subscribe(singleObserver);
  }

  /**
   * Maybe-MaybeObserver: có thể phát ra 1 item (onSuccess) hoặc không item nào (onComplete)
   */
  public static void maybe() {
    Maybe<String> maybe = Maybe.create(emitter -> {
      //emitter.onSuccess("MetaMed");
      emitter.onComplete();
    });
    MaybeObserver<String> maybeObserver = new MaybeObserver<String>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        System.out.println("onSubscribe");
      }

      @Override
      public void onSuccess(@NonNull String s) {
        System.out.println("onSuccess: " + s);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        System.out.println("onError: " + e.getMessage());
      }

      @Override
      public void onComplete() {
        System.out.println("onComplete");
      }
    };
    maybe.subscribe(maybeObserver);
  }

  /**
   * Completable-CompletableObserver: không phát ra item nào, chỉ thực thi 1 nhiệm vụ nào đó và
   * thông báo nhiệm vụ đã hoàn thành hay chưa
   */
  public static void completable() {
    Completable completable = Completable.create(emitter -> {
      System.out.println("mission success");
      emitter.onComplete();
    });
    CompletableObserver completableObserver = new CompletableObserver() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        System.out.println("onSubscribe");
      }

      @Override
      public void onComplete() {
        System.out.println("onComplete");
      }

      @Override
      public void onError(@NonNull Throwable e) {
        System.out.println("onError: " + e.getMessage());
      }
    };
    completable.subscribe(completableObserver);
  }


  /**
   * Flowable-Observer: cơ bản nó giống với Observer, nhưng nếu số lượng > 10k record thì nên sử
   * dụng Flowable thay cho Observer
   */
  public static void flowable() {
    Flowable<Integer> flowable = Flowable.range(1, 10000);
    SingleObserver<Integer> singleObserver = new SingleObserver<Integer>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        System.out.println("onSubscribe");
      }

      @Override
      public void onSuccess(@NonNull Integer integer) {
        System.out.println("onSuccess: " + integer);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        System.out.println("onError: " + e.getMessage());
      }
    };
    flowable.reduce(0, (sum, item) -> sum += item).subscribe(singleObserver);
  }



  public static void main(String[] args) {
    observable();
    //single();
    //maybe();
    //completable();
    //flowable();
  }
}
