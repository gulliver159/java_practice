package async;

import rx.Observable;
import rx.Subscriber;


public class HelloWorld {


    public static void main(String[] args) {

        Observable<Integer> myObservable = Observable.create(

                sub ->  {
                        for (int i = 0; i < 10; i++) {
                            int j = i % 2 == 0 ? -i : i;
                            sub.onNext(j);
                            sleep(500);
                            if (i == 7) {
                                sub.onError(new IllegalStateException());
                            }
                        }
                        sub.onCompleted();
                }
        );

        myObservable.subscribe(
                System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("finish")
        );


        //============================================================================================


        Observable<String> myObservable1 = Observable.just("Hello", "world!");

        myObservable1
                .map(String::hashCode)
                .subscribe(System.out::println);
        myObservable1.subscribe(System.out::println);


        //============================================================================================


        Observable.just("Hello", "world!")
                .map(String::hashCode)
                .subscribe(System.out::println);


        //============================================================================================


    }


    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
