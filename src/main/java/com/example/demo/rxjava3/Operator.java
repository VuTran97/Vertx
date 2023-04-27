package com.example.demo.rxjava3;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/**
 * vì số lượng operator lớn nên ở class này em chỉ đi qua những operator core, được dùng nhiều trong đa số trường hợp
 */
public class Operator {

  /**
   * filtering and conditional operator
   */
  public static void filterCondition(){
    //filter
    System.out.println("---------filter------------");
    Observable.just("MetaMed", "YouMed", "Hedima")
        .filter(item -> item.length() > 6)
        .subscribe(item -> System.out.println(item));
    System.out.println("---------filter------------");
    //take
    System.out.println("---------take------------");
    Observable.just("MetaMed", "YouMed", "Hedima")
        .take(2)
        .subscribe(item -> System.out.println(item));
    System.out.println("---------take------------");
    //skip
    System.out.println("---------skip------------");
    Observable.just("MetaMed", "YouMed", "Hedima")
        .skip(2)
        .subscribe(item -> System.out.println(item));

    System.out.println("---------skip------------");
    //distinct
    System.out.println("--------distinct-------------");
    Observable.just("MetaMed", "MetaMed", "Hedima")
        .distinct()
        .subscribe(item -> System.out.println(item));

    System.out.println("--------distinct-------------");
    //first
    System.out.println("--------first-------------");
    Observable.just("MetaMed", "YouMed", "Hedima")
        .first("")
        .subscribe(item -> System.out.println(item));

    System.out.println("--------first-------------");
    //last
    System.out.println("----------last-----------");
    Observable.just("MetaMed", "YouMed", "Hedima")
        .last("")
        .subscribe(item -> System.out.println(item));

    System.out.println("----------last-----------");
    //takeWhile
    System.out.println("----------takeWhile-----------");
    Observable.just("MetaMed", "YouMed", "Hedima")
        .takeWhile(item -> !item.equals("YouMed"))
        .subscribe(item -> System.out.println(item));

    System.out.println("----------takeWhile-----------");
    //skipWhile
    System.out.println("----------skipWhile-----------");
    Observable.just("MetaMed", "YouMed", "Hedima")
        .skipWhile(item -> !item.equals("YouMed"))
        .subscribe(item -> System.out.println(item));
    System.out.println("----------skipWhile-----------");
    //all
    System.out.println("---------all------------");
    Observable.just("MetaMed", "YouMed", "Hedima")
        .all(item -> item.contains("e"))
        .subscribe(item -> System.out.println(item));

    System.out.println("---------all------------");
    //any
    System.out.println("----------any-----------");
    Observable.just("MetaMed", "YouMed", "Hedima")
        .any(item -> item.contains("a"))
        .subscribe(item -> System.out.println(item));
    System.out.println("----------any-----------");
    //defaultIfEmpty
    System.out.println("----------defaultIfEmpty-----------");
    Observable.just("MetaMed", "YouMed", "Hedima")
        .filter(item -> item.equals("YouMed"))
        .defaultIfEmpty("ABC")
        .subscribe(item -> System.out.println(item));
    System.out.println("----------defaultIfEmpty-----------");
    //switchIfEmpty
    System.out.println("--------switchIfEmpty-------------");
    Observable.just("MetaMed", "YouMed", "Hedima")
        .filter(item -> item.equals("YouMed1"))
        .switchIfEmpty(Observable.just("Hello"))
        .subscribe(item -> System.out.println(item));
    System.out.println("--------switchIfEmpty-------------");
  }

  /**
   * transforming and combining operator
   */
  public static void transformAndCombine(){
    //map
    System.out.println("----------map-----------");
    Observable.just(1,2,3)
        .map(item -> String.valueOf(item))
        .subscribe(item -> System.out.println(item));
    System.out.println("----------map-----------");

    //sorted
    System.out.println("---------sorted------------");
    Observable.just(3,1,2)
        .sorted()
        .subscribe(item -> System.out.println(item));
    System.out.println("---------sorted------------");

    //scan
    System.out.println("----------scan-----------");
    Observable.just(1,2,3)
        .scan((x,y) -> x+y)
        .subscribe(item -> System.out.println(item));
    System.out.println("----------scan-----------");

    //buffer
    System.out.println("----------buffer-----------");
    Observable.range(0, 10)
        .buffer(3)
        .subscribe(item -> System.out.println(item));
    System.out.println("----------buffer-----------");

    //groupBy
    System.out.println("---------groupBy------------");
    Observable.just("a", "a", "bb", "bb", "bb", "ccc", "ccc")
        .groupBy(item -> item.length())
        .flatMapSingle(group -> group.toList())
        .subscribe(item -> System.out.println(item));
    System.out.println("---------groupBy------------");

    //flatMap
    System.out.println("----------flatMap-----------");
    Observable.just(1,2,3)
        .flatMap(item -> Observable.just(item*2))
        .subscribe(item -> System.out.println(item));
    System.out.println("----------flatMap-----------");

    //toList
    System.out.println("--------toList-------------");
    Observable.just(1,2,3)
        .toList()
        .subscribe(item -> System.out.println(item));
    System.out.println("--------toList-------------");

    //mergeWith
    System.out.println("--------mergeWith-------------");
    Observable.just(1,4,5)
        .mergeWith(Observable.just(2,3))
        .subscribe(item -> System.out.println(item));
    System.out.println("--------mergeWith-------------");

    //zipWith
    System.out.println("--------zipWith-------------");
    Observable.just("A","B")
        .zipWith(Observable.just("C","D"), (x,y) -> {
          return String.format("%s%s", x, y); //output AC BD
        })
        .subscribe(item -> System.out.println(item));
    System.out.println("--------zipWith-------------");

  }


  /**
   * utility and error handling operator
   */
  public static void utilityAndErrorHandling() throws InterruptedException {

    //delay
    System.out.println("--------delay-------------");
    Observable.just("YouMed MetaMed")
        .delay(2, TimeUnit.SECONDS, Schedulers.io())
        .subscribe(item -> System.out.println(item));
    Thread.sleep(3000);
    System.out.println("--------delay-------------");

    //timeout
    System.out.println("----------timeout-----------");
    Observable.just("Hello World")
        .timeout(2, TimeUnit.SECONDS, Schedulers.io())
        .subscribe(item -> System.out.println(item));
    System.out.println("----------timeout-----------");

    //observerOn
    System.out.println("---------observerOn------------");
    System.out.println(Thread.currentThread().getName());
    Observable.just("Hello World")
        .observeOn(Schedulers.io())
        .subscribe(item -> {
          System.out.println(Thread.currentThread().getName());
          System.out.println(item);
        });
    Thread.sleep(1000);
    System.out.println("---------observerOn------------");

    //subscribeOn
    System.out.println("---------subscribeOn------------");
    System.out.println(Thread.currentThread().getName());
    Observable.just("Hello World")
        .subscribeOn(Schedulers.newThread())
        .subscribe(item -> {
          System.out.println(Thread.currentThread().getName());
          System.out.println(item);
        });
    Thread.sleep(1000);
    System.out.println("---------subscribeOn------------");

    //doOnNext
    System.out.println("---------doOnNext------------");
    Observable.just(1,2)
        .doOnNext(item -> System.out.println("log some info: "+item))
        .filter(item -> item == 2)
        .subscribe(item -> {
          System.out.println(item);
        });
    System.out.println("---------doOnNext------------");

    //retry
    System.out.println("---------retry------------");
    Observable.just(2,1,0)
        .map(item -> 2/item)
        .retry(1)
        .subscribe(item -> {
          System.out.println(item);
        }, throwable -> System.out.println(throwable.getMessage()));
    System.out.println("---------retry------------");

    //onErrorReturnItem
    System.out.println("---------onErrorReturnItem------------");
    Observable.just(2,1,0)
        .map(item -> 2/item)
        .onErrorReturnItem(-1)
        .subscribe(item -> {
          System.out.println(item);
        });
    System.out.println("---------onErrorReturnItem------------");

    //onErrorResumeWith
    System.out.println("---------onErrorResumeWith------------");
    Observable.just(2,1,0)
        .map(item -> 2/item)
        .onErrorResumeWith(Observable.just(2,1,0))
        .subscribe(item -> {
          System.out.println(item);
        });
    System.out.println("---------onErrorResumeWith------------");

  }
  public static void main(String[] args) throws InterruptedException {
    //filterCondition();
    //transformAndCombine();
    utilityAndErrorHandling();
  }
}
