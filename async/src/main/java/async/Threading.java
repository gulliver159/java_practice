package async;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.concurrent.Callable;

public class Threading {

    public static void main(String[] args) throws InterruptedException {
        Observable.fromCallable(thatReturnsNumberOne())     // the Observable
                .subscribeOn(Schedulers.newThread())
                .map(numberToString())                      // the Operator
                .subscribe(printResult());                  // the Subscriber

        Thread.sleep(500);
    }


    private static Callable<Integer> thatReturnsNumberOne() {
        return () -> {
            System.out.println("Observable thread: " + Thread.currentThread().getName());
            return 1;
        };
    }

    private static Func1<Integer, String> numberToString() {
        return number -> {
            System.out.println("Operator thread: " + Thread.currentThread().getName());
            return String.valueOf(number);
        };
    }

    private static Action1<String> printResult() {
        return result -> {
            System.out.println("Subscriber thread: " + Thread.currentThread().getName());
            System.out.println("Result: " + result);
        };
    }
}
